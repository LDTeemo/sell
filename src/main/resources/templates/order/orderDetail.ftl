<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
    <div class="row clearfix">
        <div class="col-md-6 column">
            <h3>
                订单
            </h3>
            <table class="table table-condensed">
                <thead>
                    <tr>
                        <th>订单编号</th>
                        <th>金额</th>
                        <th>下单时间</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="info">
                        <td>${orderDto.orderId}</td>
                        <td>${orderDto.orderAmount}</td>
                        <td>${orderDto.createTime}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 column">

            <table class="table">
                <thead>
                <tr>
                    <th>商品编号</th>
                    <th>商品名称</th>
                    <th>数量</th>
                    <th>单价</th>
                    <th>总价</th>
                </tr>
                </thead>
                <tbody>
                    <#list orderDto.orderDetailList as orderDetail>
                        <tr class="info">
                            <td>${orderDetail.productId}</td>
                            <td>${orderDetail.productName}</td>
                            <td>${orderDetail.productQuantity}</td>
                            <td>${orderDetail.productPrice}</td>
                            <td>${orderDetail.productPrice*orderDetail.productQuantity}</td>
                        </tr>
                    </#list>
                </tbody>
            </table>
            <div class="row clearfix">

                    <div class="col-md-12 column">
                        <#if (orderDto.getOrderStatusEnum().code == 0 && orderDto.getPayStatusEnum().code  == 1)>
                        <a href="/sell/seller/order/toFinish?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                        </#if>
                        <#if (orderDto.getOrderStatusEnum().code == 0 && orderDto.getPayStatusEnum().code  == 0)>
                            <a href="/sell/seller/order/cancel?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
                        </#if>
                    </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>