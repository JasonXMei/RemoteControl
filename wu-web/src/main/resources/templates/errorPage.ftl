<!DOCTYPE HTML>
<html>
<head>
<title>错误界面</title>
<#include "/headerSource.ftl">
<style type="text/css">
</style>
</head>
<body>
<!-- include nav-->
<#include "/nav.ftl">
<div class="row main-top">
	<!-- include left-nav-->
	<#include "/left-nav.ftl">
	<div class="col-md-10">
		<div class="alert alert-danger" role="alert">${message!"出错啦！请稍后重试！"}</div>
	</div>
</div>
</body>
</html>
