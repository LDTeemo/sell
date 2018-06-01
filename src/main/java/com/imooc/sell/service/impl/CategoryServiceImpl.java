package com.imooc.sell.service.impl;

import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.repositoy.ProductCategoryRepository;
import com.imooc.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findOne(Integer id) {
        ProductCategory productCategoryResult = productCategoryRepository.findById(id).get();
        return productCategoryResult;
    }

    @Override
    public List<ProductCategory> findAll() {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        return productCategoryList;
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList) {
        List<ProductCategory> productCategoryList = productCategoryRepository.findByCategoryTypeIn(typeList);
        return productCategoryList;
    }

    @Override
    public ProductCategory toSave(ProductCategory productCategory) {
        ProductCategory productCategoryResult = productCategoryRepository.save(productCategory);

        return productCategoryResult;
    }
}
