package com.imooc.sell.service;

import com.imooc.sell.dataObject.ProductCategory;

import java.util.List;

public interface CategoryService {
    ProductCategory findOne(Integer id);

    List<ProductCategory> findAll();

    /**
     * 根据类型查类目
     */

    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);

    ProductCategory toSave(ProductCategory productCategory);

}
