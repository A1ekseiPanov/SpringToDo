package com.emobile.springtodo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestConfig {
    @Bean(initMethod = "start", destroyMethod = "stop")
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer(DynamicPropertyRegistry registry) {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16");
        return container;
    }

    @Bean
    public CacheManager cacheManager() {
        return new NoOpCacheManager();
    }
}