package com.coderwhs.init.controller;

import com.coderwhs.init.trace.TraceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    private final RestTemplate restTemplate;
    private final Executor ttlExecutor;

    @GetMapping("/test")
    public String test() {
        log.info("主线程打印 traceId={}", TraceContext.generateTraceIdIfAbsent());

        // RestTemplate 跨服务调用
        String response = restTemplate.getForObject("http://httpbin.org/get", String.class);
        log.info("跨服务调用结果: {}", response.substring(0, 50));

        // 在线程池里执行
        ttlExecutor.execute(() -> {
            log.info("线程池任务 traceId={}", TraceContext.getTraceId());
        });

        // @Async 方法调用
        asyncMethod();

        return "OK";
    }

    @Async
    public void asyncMethod() {
        log.info("@Async 方法 traceId={}", TraceContext.getTraceId());
    }
}