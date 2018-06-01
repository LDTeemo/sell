package com.imooc.sell.repositoy;

import com.imooc.sell.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    //该jpa接口中需要一个特殊的查询，根据商品是否下架的状态来查询；
    List<ProductInfo> findByProductStatus(Integer status);
}
