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
    <form id="searchForm" action="/tag/list" method="post" class="form-inline" role="form">
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
                       value="${(tagList.searchName)}" placeholder="用户名/小号名/QQ号/店铺名"/>
                <select class="form-control" data-live-search="true" name="searchStatus" id="searchStatus">
                    <option value="-1">==请选择标签类型==</option>
                    <option value="0">收藏</option>
                    <option value="1">加购</option>
                    <option value="2">已拍</option>
                    <option value="3">聊天</option>
                    <option value="4">足迹</option>
                </select>
                <input type="hidden" id="searchCurrent" name="searchCurrent"/>
            </div>
            <div class="form-group" id="timeForm">
                <label>开始时间:</label>
                <div class='input-group date' id='startTimepicker'>
                    <input type='text' class="form-control" name="searchStartDateStr" id="searchStartDateStr" value="${tagList.searchStartDateStr}"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar">
                </div>
                <label>结束时间:</label>
                <div class='input-group date' id='endTimepicker'>
                    <input type='text' class="form-control" name="searchEndDateStr" id="searchEndDateStr" value="${tagList.searchEndDateStr}"/>
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
                <td>标签类型</td>
                <td>创建日期</td>
                <td>备注描述</td>
            </tr>
			<#list tagList.records as taskTag>
				<tr>
                    <td>${taskTag.userName}</td>
                    <td>${taskTag.subUserName}</td>
                    <td>${taskTag.QQNumber}</td>
                    <td>${taskTag.shopName}</td>
                    <td>${taskTag.tagTypeStr}</td>
                    <td>${taskTag.createTimeStr}</td>
                    <td>${taskTag.description}</td>
                </tr>
            </#list>
        </table>
		<#include "../footer.ftl">
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        var searchStatus = ${tagList.searchStatus};
        $("#searchStatus").val(searchStatus);

        $("#searchCurrent").val(${tagList.current});
        $("#currentPage").val(${tagList.current});
        $("#total").text(${tagList.total});
        $("#pages").text(${tagList.pages});
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
</script>
</html>
