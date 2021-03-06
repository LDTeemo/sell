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
                    <form role="form" method="post" action="/sell/seller/product/doUpdate">
                        <div class="form-group" >
                            <label >产品ID</label>
                            <input name="productId" type="text" class="form-control"
                                   value="${(productInfo.productId)!''}" readOnly="true" />
                        </div>
                        <div class="form-group">
                            <label >产品名称</label>
                            <input name="productName" type="text" class="form-control"
                                   value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label >单价</label>
                            <input name="productPrice" type="number" class="form-control"
                                   value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label >库存</label>
                            <input name="productStock" type="number" class="form-control"
                                   value="${(productInfo.productStock)!''}" />
                        </div>
                        <div class="form-group">
                            <label >产品描述</label>
                            <input name="productDescription" type="text" class="form-control"
                                   value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label >图样链接</label>
                            <input name="productIcon" type="text" class="form-control"
                                   value="${(productInfo.productIcon)!''}"/>
                        </div>
                        <div class="form-group">
                            <label >商品类别</label>
                            <select name="categoryType"  class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                                selected
                                        </#if>

                                >
                                        ${category.categoryName}
                                    </option>
                                </#list>


                            </select>
                        </div>
                        <div class="form-group">
                            <label >在售状态</label>
                            <select name="productStatus"  class="form-control">
                                <#list status as e >
                                    <option value="${e.code}"
                                            <#if (productInfo.categoryType)?? && productInfo.productStatus == e.code>
                                            selected
                                            </#if>
                                    >
                                        ${e.msg}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <button  type="submit" class="btn btn-default btn-primary btn-block">提交</button>
                    </form>

                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>