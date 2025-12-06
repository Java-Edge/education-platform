package com.javaedge.back;

import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.core.task.TaskDecorator;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示两种方式把 ThreadLocal（这里用 MDC + 自定义 TenantId）从“调用线程”
 * 正确传递到线程池工作线程。
 *
 * 运行后会在控制台看到：
 *   1. 不包装时的错误结果（只能看到第一次创建时的租户）
 *   2. 使用手动装饰器后的正确结果
 *   3. 使用 Spring TaskDecorator 的正确结果
 * @author javaedge
 */
@Configuration
public class DemoConfig {

    /** ------------------- 业务上下文 ------------------- **/
    // 假设我们把租户 ID 放在 ThreadLocal 里（实际项目中往往是 SecurityContext、MDC 等）
    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();

    /** ------------------- 1. 原始线程池（不做任何包装） ------------------- */
    @Bean
    public ExecutorService rawExecutor() {
        return new ThreadPoolExecutor(
                2, 5,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new NamedThreadFactory("raw"));
    }

    /** ------------------- 2. 手动装饰的线程池（Runnable 包装） ------------------- */
    @Bean
    public ExecutorService decoratedExecutor() {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(
                2, 5,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new NamedThreadFactory("decorated"));
        // 把 submit/execute 方法包装一层，内部自动使用 ContextAwareRunnable
        return new ExecutorService() {
            @Override public void execute(Runnable command) {
                exec.execute(ContextAwareRunnable.wrap(command));
            }
            // 下面几个方法只要把 Runnable 包装下
            @Override public <T> Future<T> submit(Callable<T> task) {
                return exec.submit(() -> {
                    ContextAwareRunnable.wrap(() -> {}).run();   // 触发上下文复制
                    return task.call();
                });
            }
            @Override public <T> Future<T> submit(Runnable task, T result) {
                return exec.submit(ContextAwareRunnable.wrap(task), result);
            }
            @Override public Future<?> submit(Runnable task) {
                return exec.submit(ContextAwareRunnable.wrap(task));
            }

            @Override
            public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
                return List.of();
            }

            @Override
            public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
                return List.of();
            }

            @Override
            public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }

            // 直接委托
            @Override public void shutdown() { exec.shutdown(); }
            @Override public List<Runnable> shutdownNow() { return exec.shutdownNow(); }
            @Override public boolean isShutdown() { return exec.isShutdown(); }
            @Override public boolean isTerminated() { return exec.isTerminated(); }
            @Override public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
                return exec.awaitTermination(timeout, unit);
            }
        };
    }

    /** ------------------- 3. Spring TaskDecorator 版（推荐） ------------------- */
    @Bean("springExecutor")
    public ThreadPoolTaskExecutor springExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(2);
        exec.setMaxPoolSize(5);
        exec.setQueueCapacity(0); // SynchronousQueue 效果
        exec.setThreadNamePrefix("spring-");
        exec.setTaskDecorator(new MdcAndTenantTaskDecorator());
        exec.initialize();
        return exec;
    }

    /** ------------------- 4. 用 CommandLineRunner 模拟若干“HTTP 请求” ------------------- */
    @Bean
    public CommandLineRunner demoRunner(ExecutorService rawExecutor,
                                        ExecutorService decoratedExecutor,
                                        ThreadPoolTaskExecutor springExecutor) {
        return args -> {
            System.out.println("\n=== 开始演示 ===\n");

            // 模拟 5 个请求，每个请求的租户 ID 不同
            for (int i = 1; i <= 5; i++) {
                final String tenantId = "tenant-" + i;
                // --------- ① 设置业务上下文（相当于 Tomcat 工作线程） ----------
                TENANT.set(tenantId);
                MDC.put("request-id", "req-" + i);

                // --------- ② 提交到三种不同的线程池 ----------
                rawExecutor.execute(() -> logFromWorker("RAW"));
                decoratedExecutor.execute(() -> logFromWorker("DECORATED"));
                springExecutor.execute(() -> logFromWorker("SPRING"));

                // 清理当前请求的 ThreadLocal，防止泄漏（真实环境里会在 filter/拦截器中统一清理）
                TENANT.remove();
                MDC.clear();

                // 为了让日志更易读，稍作间隔
                Thread.sleep(200);
            }

            // 等待所有任务执行完毕后关闭线程池
            rawExecutor.shutdown(); decoratedExecutor.shutdown(); springExecutor.shutdown();
            rawExecutor.awaitTermination(5, TimeUnit.SECONDS);
            decoratedExecutor.awaitTermination(5, TimeUnit.SECONDS);
            springExecutor.getThreadPoolExecutor().awaitTermination(5, TimeUnit.SECONDS);

            System.out.println("\n=== 演示结束 ===");
        };
    }

    /** ------------------- 5. 工作线程内部打印方法 ------------------- */
    private static void logFromWorker(String poolName) {
        // 此处我们读取两种上下文：自定义 TENANT + MDC
        String tenant = TENANT.get();               // 如果没有被正确传递会是 null
        String requestId = MDC.get("request-id");   // 同上

        System.out.printf("[%-10s] thread=%-15s | tenant=%-10s | requestId=%s%n",
                poolName,
                Thread.currentThread().getName(),
                tenant == null ? "null" : tenant,
                requestId == null ? "null" : requestId);
    }

    /** ------------------- 6. 手动装饰器实现（捕获‑恢复‑清理） ------------------- */
    static final class ContextAwareRunnable implements Runnable {
        private final Runnable delegate;
        private final String capturedTenant;
        private final Map<String, String> capturedMdc;

        private ContextAwareRunnable(Runnable delegate,
                                     String tenant,
                                     Map<String, String> mdc) {
            this.delegate = delegate;
            this.capturedTenant = tenant;
            this.capturedMdc = (mdc == null ? Collections.emptyMap() : new HashMap<>(mdc));
        }

        @Override
        public void run() {
            // 保存原有值，防止污染后续任务
            String previousTenant = TENANT.get();
            Map<String, String> previousMdc = MDC.getCopyOfContextMap();

            try {
                // 注入本次请求的上下文
                if (capturedTenant != null) TENANT.set(capturedTenant);
                if (!capturedMdc.isEmpty()) MDC.setContextMap(capturedMdc);

                delegate.run();
            } finally {
                // 恢复原值（或清除）
                if (previousTenant == null) TENANT.remove(); else TENANT.set(previousTenant);
                if (previousMdc == null) MDC.clear(); else MDC.setContextMap(previousMdc);
            }
        }

        /** 供外部快速使用的工厂方法 */
        static ContextAwareRunnable wrap(Runnable task) {
            return new ContextAwareRunnable(
                    task,
                    TENANT.get(),
                    MDC.getCopyOfContextMap());
        }
    }

    /** ------------------- 7. Spring TaskDecorator 实现（MDC + Tenant） ------------------- */
    static final class MdcAndTenantTaskDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {
            // 捕获当前线程的上下文
            String tenant = TENANT.get();
            Map<String, String> mdcMap = MDC.getCopyOfContextMap();

            return () -> {
                // 保存旧值
                String oldTenant = TENANT.get();
                Map<String, String> oldMdc = MDC.getCopyOfContextMap();

                try {
                    if (tenant != null) TENANT.set(tenant);
                    else TENANT.remove();

                    if (mdcMap != null) MDC.setContextMap(mdcMap);
                    else MDC.clear();

                    runnable.run();
                } finally {
                    // 恢复
                    if (oldTenant != null) TENANT.set(oldTenant);
                    else TENANT.remove();

                    if (oldMdc != null) MDC.setContextMap(oldMdc);
                    else MDC.clear();
                }
            };
        }
    }

    /** ------------------- 8. 为线程池起好名 ------------------- */
    static class NamedThreadFactory implements ThreadFactory {
        private final String prefix;
        private final AtomicInteger counter = new AtomicInteger(0);

        NamedThreadFactory(String prefix) { this.prefix = prefix; }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, prefix + "-worker-" + counter.incrementAndGet());
        }
    }
}