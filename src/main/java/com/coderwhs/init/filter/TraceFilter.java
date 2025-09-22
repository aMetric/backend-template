package com.coderwhs.init.filter;

import com.coderwhs.init.trace.TraceContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TraceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String traceId = httpRequest.getHeader("traceId");
            if (traceId == null || traceId.isEmpty()) {
                traceId = TraceContext.generateTraceIdIfAbsent();
            } else {
                TraceContext.setTraceId(traceId);
            }
            chain.doFilter(request, response);
        } finally {
            TraceContext.clear();
        }
    }
}