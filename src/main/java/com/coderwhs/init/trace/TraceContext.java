package com.coderwhs.init.trace;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.MDC;

import java.util.UUID;

public class TraceContext {
    private static final String TRACE_ID = "traceId";

    private static final TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();

    public static void setTraceId(String traceId) {
        context.set(traceId);
        MDC.put(TRACE_ID, traceId);
    }

    public static String getTraceId() {
        return context.get();
    }

    public static void clear() {
        context.remove();
        MDC.remove(TRACE_ID);
    }

    public static String generateTraceIdIfAbsent() {
        String traceId = getTraceId();
        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
            setTraceId(traceId);
        }
        return traceId;
    }
}