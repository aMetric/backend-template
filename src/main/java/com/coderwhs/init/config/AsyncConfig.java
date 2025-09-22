package com.coderwhs.init.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        // Spring @Async 默认线程池也包装成 TTL
        return TtlExecutors.getTtlExecutor(Executors.newFixedThreadPool(5));
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> ex.printStackTrace();
    }
}