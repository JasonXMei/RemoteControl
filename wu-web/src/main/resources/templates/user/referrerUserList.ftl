<!DOCTYPE HTML>
<html>
<head>
<title>推荐用户列表</title>
<#include "../headerSource.ftl">
<style type="text/css">
#searchForm{
	margin: 10px 0;
}
#paymentCodeImgDiv{
	margin-left: 0;
}
#searchName{
	width: 250px;
}
#userListTable td{
    display:table-cell;
	vertical-align:middle;
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
        <form id="searchForm" action="/user/list/1" method="post" class="form-inline" role="form">
            <div class="form-group">
                <a>
                    <button class="btn btn-default">
                        <span class="glyphicon glyphicon-user"></span>
                        <span class="button-left">我的推荐列表</span>
                    </button>
                </a>
            </div>
            <div class="form-group pull-right">
                <input type="text" class="form-control" name="searchName" id="searchName"
                       value="${(userPageList.searchName)!''}" placeholder="编号/用户名/位置/QQ号/手机号"/>
                <select class="form-control" data-live-search="true" name="searchUserStatus"
                        id="searchUserStatus">
                    <option value="-1">==请选择账号状态==</option>
                    <option value="0">正常</option>
                    <option value="1">禁用</option>
                    <option value="3">待审核</option>
                </select>
                <input type="hidden" id="searchCurrent" name="searchCurrent"/>
                <button class="btn btn-default" type="button" id="searchFormBtn">
                    搜索 <span class="glyphicon glyphicon-search"></span>
                </button>
            </div>
        </form>
		<table class="table table-striped" id="userListTable">
			<tr class="active">
				<td>编号</td>
				<td>姓名</td>
				<td>性别</td>
				<td>年龄</td>
				<td>位置</td>
				<td>QQ号</td>
				<td>手机号</td>
				<td>补单次数</td>
				<td>有效时间</td>
				<td>账号状态</td>
			</tr>
			<#list userPageList.records as userItem>
				<tr>
					<td>${userItem.id}</td>
					<td>${userItem.userName}</td>
					<td>${userItem.sexStr}</td>
					<td>${userItem.age}</td>
					<td>${userItem.location}</td>
					<td>${userItem.qqNumber}</td>
					<td>${userItem.mobile}</td>
					<td>${userItem.allowOrderTimes}</td>
					<td>${userItem.validTimeStr}</td>
					<td>${userItem.statusStr}</td>
                </tr>
			</#list>
		</table>
		<#include "../footer.ftl">
	</div>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
    var searchUserStatus = ${(userPageList.searchUserStatus)!-1};
    if(searchUserStatus != -1){
        $("#searchUserStatus").val(${(userPageList.searchUserStatus)!});
    }

	$("#searchCurrent").val(${userPageList.current});
	$("#currentPage").val(${userPageList.current});
	$("#total").text(${userPageList.total});
	$("#pages").text(${userPageList.pages});

    $("#searchFormBtn").click(function () {
        $("#searchCurrent").val(1);
        $("#searchForm").submit();
    })
});
</script>
</html>
