package com.tikam.simple_admin_v2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);     // Min threads to keep alive
        executor.setMaxPoolSize(10);     // Max threads to create
        executor.setQueueCapacity(25);   // Queue size before creating new threads
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

}
