<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
        <#include "../common/nav.ftl">
<#--主体-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">

                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>商品编号</th>
                                <th>商品名称</th>
                                <th width="40">商品图样</th>
                                <th>单价</th>
                                <th>库存</th>
                                <th>描述</th>
                                <th>所属类别</th>
                                <th>状态</th>
                                <th>最近更新时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list productInfos as productInfo>
                                <tr >
                                    <td>${productInfo.productId}</td>
                                    <td>${productInfo.productName}</td>
                                    <td><img src="${productInfo.productIcon}" height="50" width="65"/></td>
                                    <td>${productInfo.productPrice}</td>
                                    <td>${productInfo.productStock}</td>
                                    <td>${productInfo.productDescription}</td>
                                    <td>${productInfo.categoryType}</td>
                                    <td>${productInfo.getProductStatusEnum().msg}</td>
                                    <td>${productInfo.updateTime}</td>
                                    <td><a href="/sell/seller/product/toUpdate?productId=${productInfo.productId}">更新</a></td>
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
                                <a href="/sell/seller/product/allProduct?page=${currentPage - 1}&pageSize=${pageSize}">
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
                                    <a href="/sell/seller/product/allProduct?page=${index}&pageSize=${pageSize}">
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
                                <a href="/sell/seller/product/allProduct?page=${currentPage + 1}&pageSize=${pageSize}">
                                    下一页
                                </a>
                            </li>
                        </#if>

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>