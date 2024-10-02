package com.cobb.api.integration;

import com.cobb.api.integration.base.AbastractIntegrationTest;
import com.cobb.core.dto.BrandDto;
import com.cobb.core.dto.CategoryLowestPriceDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class BrandControllerIntegrationTest extends AbastractIntegrationTest {

  @Test
  @DisplayName("브랜드를 생성")
  public void successCase_createBrand() throws Exception {
    BrandDto brandDto = generateBrandDto();

    ResultActions result = mockMvc.perform(post(BRAND_API_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(brandDto))
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

    result.andExpect(status().isOk());
  }

  @Test
  @DisplayName("중복된 브랜드를 생성")
  public void exceptionCase_createBrand() throws Exception {
    BrandDto brandDto = generateAbnormalBrandDto();

    ResultActions result = mockMvc.perform(post(BRAND_API_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(brandDto))
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

    result.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("브랜드를 삭제 (브랜드별 최소가격에서 조회할 수 없어야함)")
  public void successCase_getNotFoundBrandLowest_deleteBrand() throws Exception {
    performDelete(BRAND_API_ENDPOINT + "/1").andExpect(status().isOk());

    performGet(BRAND_API_ENDPOINT + "/A/lowest-price").andExpect(status().isNotFound()).andReturn();
  }

  @Test
  @DisplayName("브랜드를 삭제 (전체 최소가격에서 삭제된 브랜드는 다른 제품으로 대체되어야함)")
  public void successCase_deleteBrand() throws Exception {
    performDelete(BRAND_API_ENDPOINT + "/1").andExpect(status().isOk());

    MvcResult result = performGet(LOWEST_PRICE_API_ENDPOINT).andExpect(status().isOk()).andReturn();
    CategoryLowestPriceDto response = convertStringToClass(result.getResponse().getContentAsString(StandardCharsets.UTF_8), CategoryLowestPriceDto.class);
    boolean isFoundBrand = response.productDtos().stream().anyMatch(productDto -> "A".equals(productDto.brand()));

    assertFalse(isFoundBrand);
  }
}
