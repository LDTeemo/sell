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
                    <form role="form" method="post" action="/sell/seller/category/doAdd">
                        <div class="form-group">
                            <label >类目名称</label>
                            <input name="categoryName" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label >类目码</label>
                            <input name="categoryType" type="number" class="form-control"/>
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