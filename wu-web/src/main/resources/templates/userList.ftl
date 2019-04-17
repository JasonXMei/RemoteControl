<!DOCTYPE HTML>
<html>
<head>
<title>用户列表</title>
<#include "headerSource.ftl">
</head>
<body>
<!-- include nav-->
<#include "nav.ftl">
<div class="row main-top">
	<!-- include left-nav-->
	<#include "left-nav.ftl">
	<div class="col-md-10">
		<form id="userinfo" action="#" method="post" class="form-inline" role="form" style="margin: 10px 0px;">
			<div class="form-group">
				<a href="#">
					<button class="btn btn-default">
						<span class="glyphicon glyphicon-user"></span> 
						<span class="button-left">用户列表</span>
					</button>
				</a>
			</div>
			<div class="form-group" style="float: right;">
				<label>内容查询:</label> 
				<input type="text" class="form-control" id="search" name="searchName" value="" placeholder="用户名/手机号/地址" />
				<select class="form-control" data-live-search="true" name="searchUserType" id="usertype">
					<option value="">=请选择类型=</option>
					<option value="1">业务员</option>
					<option value="2">财务员</option>
					<option value="10">天使村民</option>
					<option value="11">分享村民</option>
					<option value="12">副卡村民</option>
					<option value="13">体验村民</option>
				</select>
				<button class="btn btn-default" onclick="#">
					<span class="glyphicon glyphicon-search"></span>
				</button>
				<button class="btn btn-default" onclick="#">
					<span class="glyphicon glyphicon-repeat"></span>
				</button>
			</div>
		</form>
		<table class="table table-striped">
			<tr class="active" style="font-weight: bold;">
				<td><label class="">姓名</label></td>
				<td><label class="">类型</label></td>
				<td><label class="">性别</label></td>
				<td><label class="">手机号码</label></td>
				<td><label class="">积分</label></td>
				<td><label class="button-left">余额</label></td>
				<td><label class="button-left">累计消费</label></td>
				<td><label>地址</label></td>
				<td><label>操作</label></td>
			</tr>
		</table>
		<nav>
			<ul class="pager">
				<li><a class="btn btn-default" style="background-color:#428bca;" href="javascript:void(0);"><span aria-hidden="true">&larr;</span> 上一页</a></li>
				<li><a class="btn btn-default" style="background-color:#428bca;" href="javascript:void(0);">下一页 <span aria-hidden="true">&rarr;</span></a></li>
			</ul>
		</nav>
	</div>
</div>
</body>
</html>
