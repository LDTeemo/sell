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

                        <h3>类目列表</h3>

                        <table class="table">
                            <thead>
                            <tr>
                                <th> 类目ID </th>
                                <th> 类目名 </th>
                                <th> 类目码 </th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list categoryList as category>
                                    <tr>
                                        <td>
                                            ${category.categoryId}
                                        </td>
                                        <td>
                                            ${category.categoryName}
                                        </td>
                                        <td>
                                            ${category.categoryType}
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
                                        <a href="/sell/seller/category/list?page=${currentPage - 1}&pageSize=${pageSize}">
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
                                            <a href="/sell/seller/category/list?page=${index}&pageSize=${pageSize}">
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
                                        <a href="/sell/seller/category/list?page=${currentPage + 1}&pageSize=${pageSize}">
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