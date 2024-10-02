package com.cobb.core.domain;

import com.cobb.base.error.exception.NotFoundException;
import com.cobb.core.domain.base.TestFactory;
import com.cobb.core.domain.entity.Category;
import com.cobb.core.domain.entity.CategoryPriceSummary;
import com.cobb.core.domain.entity.Product;
import com.cobb.core.domain.port.CategoryPriceSummaryPort;
import com.cobb.core.domain.port.ProductPort;
import com.cobb.core.domain.service.CategoryPriceSummaryUpdator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryPriceSummaryUpdatorTest extends TestFactory {

  @InjectMocks
  private CategoryPriceSummaryUpdator categoryPriceSummaryUpdator;

  @Mock
  private CategoryPriceSummaryPort categoryPriceSummaryPort;
  @Mock
  private ProductPort productPort;

  @Test
  @DisplayName("카테고리별 최저, 최대가격을 추가함")
  void createAndUpdate_NewCategoryPriceSummary() {
    Product product = generateProduct("A", "상의", 10000);
    CategoryPriceSummary newSummary = CategoryPriceSummary.create(product.getCategory(), product);

    when(categoryPriceSummaryPort.findByCategory_Id(product.getCategory().getId())).thenReturn(Optional.of(newSummary));

    categoryPriceSummaryUpdator.createAndUpdate(product);

    verify(categoryPriceSummaryPort).save(newSummary);
  }

  @Test
  @DisplayName("새로 추가된 상품이 최소가격에 업데이트됨")
  void removeAndUpdate_UpdateMinProduct() {
    Product product = generateProduct("A", "상의", 10000);
    CategoryPriceSummary existingSummary = mock(CategoryPriceSummary.class);

    when(categoryPriceSummaryPort.findByCategory_Id(product.getCategory().getId())).thenReturn(Optional.of(existingSummary));
    when(existingSummary.containsMinProduct(product)).thenReturn(true);

    categoryPriceSummaryUpdator.removeAndUpdate(product);

    verify(existingSummary).setMinProduct(null);
    verify(existingSummary, never()).setMaxProduct(null);
    verify(categoryPriceSummaryPort).save(existingSummary);
  }

  @Test
  @DisplayName("새로 추가된 상품이 최대가격에 업데이트됨")
  void removeAndUpdate_UpdateMaxProduct() {
    Product product = generateProduct("A", "상의", 10000);
    CategoryPriceSummary existingSummary = mock(CategoryPriceSummary.class);

    when(categoryPriceSummaryPort.findByCategory_Id(product.getCategory().getId())).thenReturn(Optional.of(existingSummary));
    when(existingSummary.containsMaxProduct(product)).thenReturn(true);

    categoryPriceSummaryUpdator.removeAndUpdate(product);

    verify(existingSummary).setMaxProduct(null);
    verify(existingSummary, never()).setMinProduct(null);
    verify(categoryPriceSummaryPort).save(existingSummary);
  }

  @Test
  @DisplayName("카테고리별 최소, 최대가격이 업데이트 로직에는 없을때는 예외를 발생함")
  void removeAndUpdate_NotFound() {
    Product product = generateProduct("A", "상의", 10000);

    when(categoryPriceSummaryPort.findByCategory_Id(product.getCategory().getId())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> categoryPriceSummaryUpdator.removeAndUpdate(product));
  }

  @Test
  @DisplayName("카테고리별 최소가격이 제거된 후 다시 업데이트함")
  void update_EmptyMinProduct() {
    Long categoryId = 1L;
    CategoryPriceSummary summary = mock(CategoryPriceSummary.class);
    Category category = mock(Category.class);
    Product product = generateProduct("A", "상의", 10000);

    when(summary.isMinEmpty()).thenReturn(true);
    when(summary.getCategory()).thenReturn(category);
    when(category.getId()).thenReturn(1L);
    when(productPort.findTopByCategory_IdOrderByPriceAsc(categoryId)).thenReturn(product);

    categoryPriceSummaryUpdator.update(summary);

    verify(productPort, times(1)).findTopByCategory_IdOrderByPriceAsc(categoryId);
    verify(productPort, never()).findTopByCategory_IdOrderByPriceDesc(categoryId);
    verify(summary).update(product);
  }

  @Test
  @DisplayName("카테고리별 최대가격이 제거된 후 다시 업데이트함")
  void update_EmptyMaxProduct() {
    Long categoryId = 1L;
    CategoryPriceSummary summary = mock(CategoryPriceSummary.class);
    Category category = mock(Category.class);
    Product product = generateProduct("A", "상의", 10000);

    when(summary.isMaxEmpty()).thenReturn(true);
    when(summary.getCategory()).thenReturn(category);
    when(category.getId()).thenReturn(1L);
    when(productPort.findTopByCategory_IdOrderByPriceDesc(categoryId)).thenReturn(product);

    categoryPriceSummaryUpdator.update(summary);

    verify(productPort, times(1)).findTopByCategory_IdOrderByPriceDesc(categoryId);
    verify(productPort, never()).findTopByCategory_IdOrderByPriceAsc(categoryId);
    verify(summary).update(product);
  }

  @Test
  @DisplayName("카테고리별 최소, 최대가격을 제거시에는 null값으로 지정해줌")
  void remove_Success() {
    CategoryPriceSummary summary = mock(CategoryPriceSummary.class);

    categoryPriceSummaryUpdator.remove(Collections.singletonList(summary));

    verify(summary).setMinProduct(null);
    verify(summary).setMaxProduct(null);
  }
}
