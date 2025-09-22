package com.coderwhs.init.config;

import com.coderwhs.init.trace.TraceContext;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();

        // 添加拦截器：传递 traceId
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            String traceId = TraceContext.generateTraceIdIfAbsent();
            request.getHeaders().add("traceId", traceId);
            return execution.execute(request, body);
        };

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(interceptor);
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}