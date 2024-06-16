package com.javagpt.back.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@Slf4j
@RestController
public class AsyncController {

    @GetMapping("/async")
    public DeferredResult<String> handleAsyncRequest() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                deferredResult.setResult("Async response");
            } catch (InterruptedException e) {
                deferredResult.setErrorResult("Error in async processing");
            }
        }).start();
        return deferredResult;
    }

    @RequestMapping(value = "/t2")
    public Callable<String> t2() {
        log.info("controller#handler called. Thread: " + Thread.currentThread().getName());

        Callable<String> callable = () -> {
            log.info("controller-callable#async task started. Thread: " + Thread.currentThread().getName());
            Thread.sleep(300);
            log.info("controller-callable#async task finished");
            return "async result";
        };

        log.info("controller#handler finished");
        return callable;
    }
}
