package com.imooc.sell.service.impl;

import com.imooc.sell.appException.SellException;
import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.repositoy.ProductCategoryRepository;
import com.imooc.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        // 如果是新增检查这个类目码值是否存在
        if (productCategory.getCategoryId()==null){
            List<ProductCategory> productCategoryList =
                    productCategoryRepository.findByCategoryType(productCategory.getCategoryType());
            if (productCategoryList.size()>0){
                throw new SellException(ResultEnum.PRODUCT_CATEGORY_ALREADY_EXISTS);
            }
        }
        ProductCategory productCategoryResult = productCategoryRepository.save(productCategory);

        return productCategoryResult;
    }

    @Override
    public Page<ProductCategory> findAllByPage(Pageable pageable) {
        Page<ProductCategory> productCategories = productCategoryRepository.findAll(pageable);
        return productCategories;
    }
}
