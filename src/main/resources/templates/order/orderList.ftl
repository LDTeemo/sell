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
                            <#list orderDTOPage.content as orderDto>
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
                                        ${orderDto.orderStatus}
                                    </td>
                                    <td>
                                        ${orderDto.payStatus}
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
                    <ul class="pagination">
                        <li>
                            <a href="#">前一页</a>
                        </li>
                        <li>
                            <a href="#">1</a>
                        </li>
                        <li>
                            <a href="#">2</a>
                        </li>
                        <li>
                            <a href="#">3</a>
                        </li>
                        <li>
                            <a href="#">4</a>
                        </li>
                        <li>
                            <a href="#">5</a>
                        </li>
                        <li>
                            <a href="#">后一页</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>