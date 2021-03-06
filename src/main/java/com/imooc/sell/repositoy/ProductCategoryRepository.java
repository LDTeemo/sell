package com.imooc.sell.repositoy;

import com.imooc.sell.dataObject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
     List<ProductCategory> findByCategoryTypeIn(List<Integer> l);
     List<ProductCategory> findByCategoryType(Integer categoryType);
}
