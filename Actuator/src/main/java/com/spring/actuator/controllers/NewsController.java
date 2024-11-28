package com.spring.actuator.controllers;

import com.spring.actuator.domains.News;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsController {

    private Counter counter;

    @Autowired
    public NewsController(MeterRegistry registry) {
        // counter
        this.counter = Counter.builder("news_fetch_request_total").
                tag("version", "v1").
                description("News Fetch Count").
                register(registry);
    }

    @GetMapping("/news")
    public List<News> getNews() {
        counter.increment();
        return List.of(new News("Good News!"), new News("Bad News!"));
    }

}
