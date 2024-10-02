package com.cobb.core.domain;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.base.TestFactory;
import com.cobb.core.domain.entity.LowestTotal;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.entity.ProductLowestTotal;
import com.cobb.core.domain.port.LowestTotalPort;
import com.cobb.core.domain.port.ProductLowestTotalPort;
import com.cobb.core.domain.port.ProductPort;
import com.cobb.core.domain.service.LowestTotalUpdator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LowestTotalUpdatorTest extends TestFactory {

  @InjectMocks
  private LowestTotalUpdator lowestTotalUpdator;
  @Mock
  private LowestTotalPort lowestTotalPort;
  @Mock
  private ProductLowestTotalPort productLowestTotalPort;
  @Mock
  private ProductPort productPort;

  @BeforeEach
  void setUp() {

  }

  @Test
  @DisplayName("새 상품을 추가")
  void createAndUpdate_NewLowestTotal() {
    Product product = generateProduct("A", "상의", 10000);
    LowestTotal lowestTotal = LowestTotal.create(product.getBrand());

    when(lowestTotalPort.findByBrand_Id(product.getBrand().getId())).thenReturn(Optional.of(lowestTotal));

    lowestTotalUpdator.createAndUpdate(product);

    verify(lowestTotalPort).save(lowestTotal);
    assertTrue(lowestTotal.contains(product));
  }

  @Test
  @DisplayName("기존 상품을 제거하고, 제거된 상품의 브랜드,카테고리를 업데이트")
  void removeAndUpdate_ProductExistsInLowestTotal() {
    Product oldProduct = generateProduct("A", "상의", 10000);
    LowestTotal lowestTotal = mock(LowestTotal.class);
    ProductLowestTotal productLowestTotal = mock(ProductLowestTotal.class);

    when(lowestTotalPort.findByBrand_Id(oldProduct.getBrand().getId())).thenReturn(Optional.of(lowestTotal));
    when(lowestTotal.contains(oldProduct)).thenReturn(true);
    when(lowestTotal.remove(oldProduct)).thenReturn(productLowestTotal);

    lowestTotalUpdator.removeAndUpdate(oldProduct);

    verify(productLowestTotalPort).delete(productLowestTotal);
    verify(lowestTotalPort).save(lowestTotal);
  }

  @Test
  @DisplayName("제거될 상품이 최저가격에 포함되지 않을 경우 예외를 반환")
  void removeAndUpdate_ProductNotFound() {
    Product oldProduct = generateProduct("A", "상의", 10000);

    when(lowestTotalPort.findByBrand_Id(oldProduct.getBrand().getId())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> lowestTotalUpdator.removeAndUpdate(oldProduct));
  }

  @Test
  @DisplayName("브랜드 ID로 최저가격에서 찾아 제거함")
  void remove() {
    Long brandId = 1L;
    LowestTotal lowestTotal = mock(LowestTotal.class);

    when(lowestTotalPort.findByBrand_Id(brandId)).thenReturn(Optional.of(lowestTotal));

    lowestTotalUpdator.remove(brandId);

    verify(lowestTotalPort).delete(lowestTotal);
  }

  @Test
  @DisplayName("브랜드 ID로 최저가격에서 찾지 못할 경우 예외를 반환")
  void remove_LowestTotalNotFound() {
    Long brandId = 1L;

    when(lowestTotalPort.findByBrand_Id(brandId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> lowestTotalUpdator.remove(brandId));
  }

}
