package com.javagpt.back.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author JavaEdge
 * @date 2024/8/24
 */
public class RequestProcessTrace {

    private static final InheritableThreadLocal<FullLinkContext> FULL_LINK_CONTEXT_INHERITABLE_THREAD_LOCAL
            = new InheritableThreadLocal<FullLinkContext>();

    public static FullLinkContext getContext() {
        FullLinkContext fullLinkContext = FULL_LINK_CONTEXT_INHERITABLE_THREAD_LOCAL.get();
        if (fullLinkContext == null) {
            FULL_LINK_CONTEXT_INHERITABLE_THREAD_LOCAL.set(new FullLinkContext());
            fullLinkContext = FULL_LINK_CONTEXT_INHERITABLE_THREAD_LOCAL.get();
        }
        return fullLinkContext;
    }

    private static class FullLinkContext {
        private String traceId;

        public String getTraceId() {
            if (StringUtils.isEmpty(traceId)) {
                FrameWork.startTrace(null, "JavaEdge");
                traceId = FrameWork.getTraceId();
            }
            return traceId;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }
    }

}