package com.cobb.api;

import com.cobb.core.application.handler.command.CreateProudctHandler;
import com.cobb.core.dto.ProductDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StoreApplicationListener {

  private final CreateProudctHandler createProudctHandler;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @EventListener(ApplicationStartedEvent.class)
  public void setUp() {
    try {
      Resource resource = new ClassPathResource("db/products.json");
      InputStream jsonFile = resource.getInputStream();
      List<ProductDto> products = objectMapper.readValue(jsonFile, new TypeReference<List<ProductDto>>() {
      });

      createProudctHandler.execute(products);

      log.info("Products have been successfully added to /product");
    } catch (IOException e) {
      System.err.println("Failed to read JSON file: " + e.getMessage());
    }
  }
}
