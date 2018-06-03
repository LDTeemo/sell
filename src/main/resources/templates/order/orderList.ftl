<html>
    <head>
        <meta charset="UTF-8">
        <title>卖家商品列表</title>
        <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table">
                        <thead>
                        <tr>
                            <th> 订单编号 </th>
                            <th> 姓名 </th>
                            <th> 手机号 </th>
                            <th> 地址 </th>
                            <th> 金额 </th>
                            <th> 订单状态 </th>
                            <th> 支付状态 </th>
                            <th> 创建时间 </th>
                            <th colspan="2"> 操作 </th>

                        </tr>
                        </thead>
                        <tbody>
                            <#list orderDTOPage.getContent() as orderDto>
                                <tr>
                                    <td>
                                        ${orderDto.orderId}
                                    </td>
                                    <td>
                                        ${orderDto.buyerName}
                                    </td>
                                    <td>
                                        ${orderDto.buyerPhone}
                                    </td>
                                    <td>
                                        ${orderDto.buyerAddress}
                                    </td>
                                    <td>
                                        ${orderDto.orderAmount}
                                    </td>
                                    <td>
                                        ${orderDto.getOrderStatusEnum().msg}
                                    </td>
                                    <td>
                                        ${orderDto.getPayStatusEnum().msg}
                                    </td>
                                    <td>
                                        ${orderDto.createTime}
                                    </td>
                                    <td>
                                        详情
                                    </td>
                                    <td>
                                        取消
                                    </td>
                                </tr>
                            </#list>

                        </tbody>
                    </table>
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                            <#--前一页的逻辑判断设计样式-->
                            <#if currentPage lte 1>
                                <li class="disabled">
                                    <a href="#">上一页</a>
                                </li>
                            <#else>
                                <li>
                                    <a href="/sell/seller/order/list?page=${currentPage - 1}&size=${pageSize}">
                                        上一页
                                    </a>
                                </li>
                            </#if>

                            <#--当前页逻辑判断-->
                            <#list 1..totalPages as index>
                                <#if currentPage == index>
                                    <li class="disabled">
                                        <a href="#">
                                            ${index}
                                        </a>
                                    </li>
                                <#else>
                                    <li>
                                        <a href="/sell/seller/order/list?page=${index}&size=${pageSize}">
                                            ${index}
                                        </a>
                                    </li>
                                </#if>
                            </#list>

                            <#--最后一页逻辑判断设计样式-->
                            <#if currentPage gte totalPages>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li>
                                    <a href="/sell/seller/order/list?page=${currentPage + 1}&size=${pageSize}">
                                        下一页
                                    </a>
                                </li>
                            </#if>

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>