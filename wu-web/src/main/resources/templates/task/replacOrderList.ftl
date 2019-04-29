<!DOCTYPE HTML>
<html>
<head>
    <title>补单列表</title>
<#include "../headerSource.ftl">
    <style type="text/css">
        #searchForm{
            margin: 10px 0;
        }
        #searchName{
            width: 400px;
        }
        #userListTable td{
            display:table-cell;
            vertical-align:middle;
        }
        #timeForm{
            display: block;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<!-- include nav-->
<#include "../nav.ftl">
<div class="row main-top">
    <!-- include left-nav-->
	<#include "../left-nav.ftl">
    <div class="col-md-10">
    <#--<button class="btn btn-default" data-toggle="modal" data-target="#imgModal">-->
    <#--<span class="glyphicon glyphicon-repeat"></span>-->
    <#--</button>-->
    <form id="searchForm" action="/replaceOrder/list" method="post" class="form-inline" role="form">
        <div class="form-group">
            <a>
                <button class="btn btn-default">
                    <span class="glyphicon glyphicon-user"></span>
                    <span class="button-left">补单列表</span>
                </button>
            </a>
        </div>
        <div class="form-group pull-right">
            <div class="form-group">
                <input type="text" class="form-control" name="searchName" id="searchName"
                       value="${(replaceOrderList.searchName)!''}" placeholder="用户名/小号名/QQ号/店铺名/订单号"/>
                <select class="form-control" data-live-search="true" name="searchStatus" id="searchStatus">
                    <option value="-1">==请选择补单状态==</option>
                    <option value="0">未支付</option>
                    <option value="1">已支付</option>
                    <option value="2">已返款</option>
                </select>
                <input type="hidden" id="searchCurrent" name="searchCurrent"/>
            </div>
            <div class="form-group" id="timeForm">
                <label>开始时间:</label>
                <div class='input-group date' id='startTimepicker'>
                    <input type='text' class="form-control" name="searchStartDateStr" id="searchStartDateStr" value="${replaceOrderList.searchStartDateStr!}"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar">
                </div>
                <label>结束时间:</label>
                <div class='input-group date' id='endTimepicker'>
                    <input type='text' class="form-control" name="searchEndDateStr" id="searchEndDateStr" value="${replaceOrderList.searchEndDateStr!}"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar">
                </div>
                <button class="btn btn-default" type="button" id="searchFormBtn">
                    搜索 <span class="glyphicon glyphicon-search"></span>
                </button>
            </div>
        </div>
    </form>
        <table class="table table-striped" id="userListTable">
            <tr class="active">
                <td>用户名</td>
                <td>小号名</td>
                <td>QQ号</td>
                <td>店铺名</td>
                <td>订单编号</td>
                <td>订单金额</td>
                <td>订单回扣</td>
                <td>订单状态</td>
                <td>创建日期</td>
                <td>备注描述</td>
                <td>操作</td>
            </tr>
			<#list replaceOrderList.records as replaceOrder>
				<tr>
                    <td>${replaceOrder.userName}</td>
                    <td>${replaceOrder.subUserName}</td>
                    <td>${replaceOrder.QQNumber}</td>
                    <td>${replaceOrder.shopName}</td>
                    <td>${replaceOrder.orderId}</td>
                    <td>${replaceOrder.orderAmount}</td>
                    <td>${replaceOrder.orderCommission}</td>
                    <td>${replaceOrder.paymentStatusStr}</td>
                    <td>${replaceOrder.createTimeStr}</td>
                    <td>${replaceOrder.description}</td>
                    <td>
                        <#if (replaceOrder.paymentStatusStr)?? && (replaceOrder.paymentStatusStr == '已支付')>
                            <button type="button" class="btn btn-primary" onclick="showPaymentCodeImg('${replaceOrder.paymentCodeImg}', 2, '${replaceOrder.id}')">返款码</button>
                        </#if>
                    </td>
                </tr>
            </#list>
        </table>
		<#include "../footer.ftl">
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        var searchStatus = ${(replaceOrderList.searchStatus)!-1};
        $("#searchStatus").val(searchStatus);

        $("#searchCurrent").val(${replaceOrderList.current});
        $("#currentPage").val(${replaceOrderList.current});
        $("#total").text(${replaceOrderList.total});
        $("#pages").text(${replaceOrderList.pages});
        $('#startTimepicker').datetimepicker({
            format : 'YYYY-MM-DD',
            locale : moment.locale('zh-cn')
        });
        $('#endTimepicker').datetimepicker({
            format : 'YYYY-MM-DD',
            locale : moment.locale('zh-cn')
        });

        $("#searchFormBtn").click(function () {
                var searchStartDateStr = $("#searchStartDateStr").val();
                if(!checkTime(searchStartDateStr)){
                    showAlertModal("操作提示", "开始时间格式应为: 2019-04-17(yyyy-MM-dd)！");
                    return;
                }

                var searchEndDateStr = $("#searchEndDateStr").val();
                if(!checkTime(searchEndDateStr)){
                    showAlertModal("操作提示", "结束时间格式应为: 2019-04-17(yyyy-MM-dd)！");
                    return;
                }
                if(searchEndDateStr && searchEndDateStr < searchStartDateStr){
                    showAlertModal("操作提示", "结束时间不能小于开始时间哦！");
                    return;
                }
                $("#searchCurrent").val(1);
                $("#searchForm").submit();
        });
    });

    function checkTime(time) {
        if(!time){
            return true;
        }
        var regexp = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
        if(regexp.exec(time)){
            return true;
        }
        return false;
    }
    function refundAllow(orderId, status) {
        showConfirmModal("温馨提示", "请确认已扫码完成返款操作，如已完成，点击确定更新补单状态!");
        $("#confirmModalBtn").click(function () {
            handleOrder(orderId, status);
        });
    }
    function handleOrder(orderId, status) {
        $.ajax({
            url : "/replaceOrder/handle/" + orderId + "/" + status + "/",
            method : "post",
            success : function (data) {
                if(data.status == 200){
                    window.location.href = "/replaceOrder/list";
                }else{
                    $("#confirmModal").modal("hide");
                    showAlertModal("操作提示", data.description);
                }
            }
        })
    }
</script>
</html>
