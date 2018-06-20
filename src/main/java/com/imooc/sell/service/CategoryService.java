package com.imooc.sell.service;

import com.imooc.sell.dataObject.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    ProductCategory findOne(Integer id);

    List<ProductCategory> findAll();

    Page<ProductCategory> findAllByPage(Pageable pageable);

    /**
     * 根据类型查类目
     */

    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);

    ProductCategory toSave(ProductCategory productCategory);

}
