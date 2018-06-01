package com.imooc.sell.service;

import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.enums.ProductStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
    ProductInfo getOne(String id);

    List<ProductInfo> getUpProduct();

    Page<ProductInfo> getAllProduct(Pageable pageable);

    ProductInfo toSave(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
