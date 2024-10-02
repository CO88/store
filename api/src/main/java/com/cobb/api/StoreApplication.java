package com.cobb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.cobb.base", "com.cobb.core", "com.cobb.api"})
@ConfigurationPropertiesScan(basePackages = {"com.cobb.base", "com.cobb.api"})
@EntityScan(basePackages = {"com.cobb.core"})
@EnableJpaRepositories(basePackages = {"com.cobb.core"})
public class StoreApplication {
  public static void main(String[] args) {
    SpringApplication.run(StoreApplication.class, args);
  }
}