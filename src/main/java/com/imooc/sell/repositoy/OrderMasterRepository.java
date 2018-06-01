package com.imooc.sell.repositoy;

import com.imooc.sell.dataObject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /**通过openid查询所有的订单，需要使用分页*/
    Page<OrderMaster> findByBuyerOpenid(String openid , Pageable pageable);
}
