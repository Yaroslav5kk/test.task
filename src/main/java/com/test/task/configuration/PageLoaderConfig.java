package com.test.task.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class PageLoaderConfig {
    @Value("${page.load.max-threads}")
    private int maxNumberThreads;

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(maxNumberThreads);
    }
}

















