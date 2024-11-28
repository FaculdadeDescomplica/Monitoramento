package com.spring.actuator.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
public class ResponseTimeFilter implements Filter {

    private final MeterRegistry meterRegistry;

    public ResponseTimeFilter(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            long startTime = System.nanoTime();
            try {
                chain.doFilter(request, response);
            } finally {
                long duration = System.nanoTime() - startTime;
                Timer.builder("http.server.response.time")
                        .description("Tempo de resposta do servidor")
                        .tags("uri", ((HttpServletRequest) request).getRequestURI())
                        .register(meterRegistry)
                        .record(Duration.ofNanos(duration));
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
