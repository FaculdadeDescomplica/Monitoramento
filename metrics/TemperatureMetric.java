package com.spring.actuator.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TemperatureMetric {
    @Autowired
    private MeterRegistry registry;

    @PostConstruct
    public void initMetrics(){
        Gauge.builder("temperature", () -> {
            double min = 10, max = 30;
            return min + new Random().nextDouble() * (max - min);
        }).tag("version", "v1")
        .description("Temperature Record")
        .register(registry);

    }
}
