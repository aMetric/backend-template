package com.coderwhs.init.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Bean("ttlExecutor")
    public Executor ttlExecutor() {
        // 包装线程池，保证 traceId 透传
        return TtlExecutors.getTtlExecutor(Executors.newFixedThreadPool(5));
    }
}