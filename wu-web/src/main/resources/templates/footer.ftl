<style type="text/css">
#pager li a{
background-color:#428bca;
}
#pager li input{
display: inline-block;
width: 30px;
vertical-align: middle;
text-align: center;
font-size: 14px;
line-height: 1.42857143;
color: #555;
background:transparent;
border: 1px solid #f5f5f5;
border-radius: 30px;
outline:none
}
</style>
<script type="text/javascript">
$(function(){
	$('#currentPage').bind('keypress', function(event) {
		if (event.keyCode == "13") {
			//$("#btnSubmit").click();
			alert($("#currentPage").val());
		}
	})
})
</script>
<nav>
	<ul class="pager" id="pager">
		<li><a class="btn btn-default" href="javascript:void(0);"><span>&larr;</span> 首页</a></li>
		<li><a class="btn btn-default" href="javascript:void(0);"><span>&larr;</span> 上一页</a></li>
		<li><span>第  <input id="currentPage" value="1"/> / <span id="totalPage">10</span> 页，共 <span id="totalRecord">98</span> 条记录</span></li>
		<li><a class="btn btn-default" href="javascript:void(0);">下一页 <span>&rarr;</span></a></li>
		<li><a class="btn btn-default" href="javascript:void(0);">末页 <span>&rarr;</span></a></li>
	</ul>
</nav>