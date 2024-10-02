package com.cobb.api.integration;

import com.cobb.api.integration.base.AbastractIntegrationTest;
import com.cobb.core.dto.CategoryLowestPriceDto;
import com.cobb.core.dto.LowestTotalDto;
import com.cobb.core.dto.ProductDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class ProductControllerIntegrationTest extends AbastractIntegrationTest {

  @Test
  @DisplayName("상품을 생성")
  public void successCase_createProduct() throws Exception {
    List<ProductDto> productDtos = List.of(generateProductDto("A", "상의", 900));

    ResultActions result = performPost(PRODUCT_API_ENDPOINT, productDtos).andDo(print());

    result.andExpect(status().isOk());
  }

  @Test
  @DisplayName("상의의 최저가를 갱신되는 상품을 추가")
  public void successCase_renewalCategoryTop_createProduct() throws Exception {
    List<ProductDto> productDtos = List.of(generateProductDto("A", "상의", 900));

    performPost(PRODUCT_API_ENDPOINT, productDtos).andExpect(status().isOk());

    MvcResult result = performGet(LOWEST_PRICE_API_ENDPOINT).andExpect(status().isOk()).andReturn();

    CategoryLowestPriceDto response = convertStringToClass(result.getResponse().getContentAsString(StandardCharsets.UTF_8), CategoryLowestPriceDto.class);
    ProductDto resultProduct = response.productDtos().stream()
            .filter(productDto -> "상의".equals(productDto.category()))
            .findFirst().orElse(null);

    assertNotNull(resultProduct);
    assertEquals(900, resultProduct.price());

  }

  @Test
  @DisplayName("브랜드명이 잘못된 상품을 생성")
  public void exceptionCase_createProduct() throws Exception {
    List<ProductDto> productDtos = List.of(generateAbnormalProductDto());

    ResultActions result = performPost(PRODUCT_API_ENDPOINT, productDtos).andDo(print());

    result.andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("특정 상품을 업데이트 (가격만 변경)")
  public void successCase_updateProductPrice() throws Exception {
    ProductDto productDtos = generateProductDto(null, null, 900);
    performPatch(PRODUCT_API_ENDPOINT + "/1", productDtos).andExpect(status().isOk());

    MvcResult result = performGet(LOWEST_PRICE_API_ENDPOINT).andExpect(status().isOk()).andReturn();

    CategoryLowestPriceDto response = convertStringToClass(result.getResponse().getContentAsString(StandardCharsets.UTF_8), CategoryLowestPriceDto.class);
    ProductDto resultProduct = response.productDtos().stream()
            .filter(productDto -> "상의".equals(productDto.category()))
            .findFirst().orElse(null);

    assertNotNull(resultProduct);
    assertEquals(900, resultProduct.price());
  }


  /**
   * input : { "id": 17, "brand": "C", "category": "상의", "price": 10000 }
   * expect:  { "id": 25, "brand": "D", "category": "상의", "price": 10100 }
   */
  @Test
  @DisplayName("특정 상품을 업데이트 (카테고리만 변경 - 최소가격에 속하는 카테고리를 다른 카테고리로 변경)")
  public void successCase_updateProductCategory() throws Exception {
    ProductDto productDtos = generateProductDto(null, "바지", null);
    performPatch(PRODUCT_API_ENDPOINT + "/17", productDtos).andExpect(status().isOk());

    MvcResult result = performGet(LOWEST_PRICE_API_ENDPOINT).andExpect(status().isOk()).andReturn();

    CategoryLowestPriceDto response = convertStringToClass(result.getResponse().getContentAsString(StandardCharsets.UTF_8), CategoryLowestPriceDto.class);
    ProductDto resultProduct = response.productDtos().stream()
            .filter(productDto -> "상의".equals(productDto.category()))
            .findFirst().orElse(null);

    assertNotNull(resultProduct);
    assertEquals(10100, resultProduct.price());
  }

  /**
   * input : { "id": 17, "brand": "C", "category": "상의", "price": 10000 }
   * expect:  { "brand": "B", "category": "상의", "price": 10500 }
   */
  @Test
  @DisplayName("특정 상품을 업데이트 (브랜드만 변경 - 최소가격에 속하는 브랜드의 특정 카테고리의 상품을 다른 브랜드로 변경)")
  public void successCase_updateProductBrand() throws Exception {
    ProductDto productDtos = generateProductDto("B", null, null);
    performPatch(PRODUCT_API_ENDPOINT + "/17", productDtos).andExpect(status().isOk());

    MvcResult result = performGet(BRAND_API_ENDPOINT + "/B/lowest-price").andExpect(status().isOk()).andReturn();
    LowestTotalDto response = convertStringToClass(result.getResponse().getContentAsString(StandardCharsets.UTF_8), LowestTotalDto.class);
    ProductDto resultProduct = response.products().stream()
            .filter(productDto -> "상의".equals(productDto.category()))
            .findFirst().orElse(null);

    assertNotNull(resultProduct);
    assertEquals(10000, resultProduct.price());
  }

  /**
   * input : { "id": 17, "brand": "C", "category": "상의", "price": 10000 }
   * expect:  { "id": 25, "brand": "D", "category": "상의", "price": 10100 }
   */
  @Test
  @DisplayName("특정 상품을 삭제 (전체 최소가격에서 삭제된 상품의 카테고리는 다른 제품으로 대체되어야함)")
  public void successCase_deleteProduct() throws Exception {
    performDelete(PRODUCT_API_ENDPOINT + "/17").andExpect(status().isOk());

    MvcResult lowestResult = performGet(LOWEST_PRICE_API_ENDPOINT).andExpect(status().isOk()).andReturn();
    CategoryLowestPriceDto lowestResponse = convertStringToClass(lowestResult.getResponse().getContentAsString(StandardCharsets.UTF_8), CategoryLowestPriceDto.class);
    ProductDto lowestProduct = lowestResponse.productDtos().stream()
            .filter(productDto -> "상의".equals(productDto.category()))
            .findFirst().orElse(null);

    assertNotNull(lowestProduct);
    assertEquals(10100, lowestProduct.price());
    assertEquals("D", lowestProduct.brand());
  }

  /**
   * input : { "id": 17, "brand": "C", "category": "상의", "price": 10000 }
   * expect:  null
   */
  @Test
  @DisplayName("특정 상품을 삭제 (브랜드별 카테고리 최소값에서 상의 카테고리가 삭제되어 비어있어야함)")
  public void successCase_beEmptyDeletedProduct_deleteProduct() throws Exception {
    performDelete(PRODUCT_API_ENDPOINT + "/17").andExpect(status().isOk());

    MvcResult brandLowestResult = performGet(BRAND_API_ENDPOINT + "/C/lowest-price").andExpect(status().isOk()).andReturn();
    LowestTotalDto brandLowestResponse = convertStringToClass(brandLowestResult.getResponse().getContentAsString(StandardCharsets.UTF_8), LowestTotalDto.class);
    ProductDto brandLowestProduct = brandLowestResponse.products().stream()
            .filter(productDto -> "상의".equals(productDto.category()))
            .findFirst().orElse(null);

    assertNull(brandLowestProduct);
  }
}
