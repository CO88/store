package com.cobb.api.integration.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public abstract class AbastractIntegrationTest extends TestFactory {

  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;

  protected <T> T convertStringToClass(String jsonString, Class<T> responseType) throws JsonProcessingException {
    return objectMapper.readValue(jsonString, responseType);
  }

  protected ResultActions performGet(String uri) throws Exception {
    return mockMvc.perform(get(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));
  }

  protected ResultActions performPost(String uri, Object data) throws Exception {
    return mockMvc.perform(post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(data))
            .accept(MediaType.APPLICATION_JSON));
  }

  protected ResultActions performPatch(String uri, Object data) throws Exception {
    return mockMvc.perform(patch(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(data))
            .accept(MediaType.APPLICATION_JSON));
  }

  protected ResultActions performDelete(String uri) throws Exception {
    return mockMvc.perform(delete(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));
  }
}
