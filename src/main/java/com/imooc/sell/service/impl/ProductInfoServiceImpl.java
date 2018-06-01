package com.imooc.sell.service.impl;

import com.imooc.sell.appException.SellException;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.repositoy.ProductInfoRepository;
import com.imooc.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    /**
     * jpa2.0之后取消findOne方法，替代的是 Optinal<T> findById(id)，然后使用.get()来获取查找的对象
     * .get()方法中抛出了异常，这里需要捕获
     * @param id
     * @return
     */
    @Override
    public ProductInfo getOne(String id) {
        ProductInfo productInfo = new ProductInfo();
        try {
            productInfo = productInfoRepository.findById(id).get();
        } catch (Exception e){
            if (e instanceof NoSuchElementException) {
                productInfo = null;
            }
        }
        return productInfo;
    }

    @Override
    public List<ProductInfo> getUpProduct() {
        List<ProductInfo> resultsList = productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
        return resultsList;
    }

    @Override
    public Page<ProductInfo> getAllProduct(Pageable pageable) {
        Page<ProductInfo> resultsPage = productInfoRepository.findAll(pageable);
        return resultsPage;
    }

    @Override
    @Transactional
    public ProductInfo toSave(ProductInfo productInfo) {
        ProductInfo saveResult = productInfoRepository.save(productInfo);
        return saveResult;
    }

    /**
     * 传入参数，包含产品ID和数量
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO c: cartDTOList
             ) {
            ProductInfo productInfo = productInfoRepository.findById(c.getProductId()).get();
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }
            Integer giveBack = productInfo.getProductStock() + c.getProductQuantity();
            productInfo.setProductStock(giveBack);
            ProductInfo updateResult = productInfoRepository.save(productInfo);
            if (updateResult == null){
                throw new SellException(ResultEnum.UPDATE_PRODUCT_STOCK_ERROR);
            }
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO c:cartDTOList
             ) {
            ProductInfo productInfo = productInfoRepository.findById(c.getProductId()).get();

            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }

            Integer result = productInfo.getProductStock() - c.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_NOT_ENOUGH);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
