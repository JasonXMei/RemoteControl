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
		    var expectedPage = $("#currentPage").val();
		    if(!checkInputPage(expectedPage)){
                showAlertModal("温馨提示", "您输入的页码格式不正确,请输入大于0的数字!");
                return;
			}

			expectedPage = parseInt(expectedPage);
            var totalPages = parseInt($("#pages").text());
            if(expectedPage > totalPages){
		        showAlertModal("温馨提示", "您输入的页码太大了,请输入小于等于" + totalPages + "的数字!");
		        return;
			}

			submitSearchForm(expectedPage);
		}
	});

	$("#homePage").bind("click", function () {
	    homeOrLastPage(1, "温馨提示", "页码已达首页，翻不动啦！");
    });

	$("#pageUp").bind("click", function () {
		var current = parseInt($("#searchCurrent").val());
		current = current - 1;
		if(current >= 1){
		    submitSearchForm(current);
		}else{
            showAlertModal("温馨提示", "页码已达首页，翻不动啦！");
		}
    });

	$("#pageDown").bind("click", function () {
		var current = parseInt($("#searchCurrent").val());
		current = current + 1;

        var totalPages = parseInt($("#pages").text());
		if(current <= totalPages){
		    submitSearchForm(current);
		}else{
            showAlertModal("温馨提示", "页码已达末页，翻不动啦！");
		}
    });

	$("#lastPage").bind("click", function () {
        var totalPages = parseInt($("#pages").text());
        homeOrLastPage(totalPages, "温馨提示", "页码已达末页，翻不动啦！");
    });
})

function homeOrLastPage(notExpectdPage, title, message) {
    var current = parseInt($("#searchCurrent").val());
    if(current == notExpectdPage){
        showAlertModal(title, message);
    }else{
        submitSearchForm(notExpectdPage);
    }
}

function checkInputPage(s) {
    var patrn = /^[1-9]*[1-9][0-9]*$/;
    if(patrn.test(s)) return true;
    return false;
}

function submitSearchForm(expectedPage) {
    $("#searchCurrent").val(expectedPage);
    $("#searchForm").submit();
}
</script>
<nav>
	<ul class="pager" id="pager">
		<li><a class="btn btn-default" id="homePage"><span>&larr;</span> 首页</a></li>
		<li><a class="btn btn-default" id="pageUp"><span>&larr;</span> 上一页</a></li>
		<li><span>第  <input id="currentPage"/> / <span id="pages"></span> 页，共 <span id="total"></span> 条记录</span></li>
		<li><a class="btn btn-default" id="pageDown">下一页 <span>&rarr;</span></a></li>
		<li><a class="btn btn-default" id="lastPage">末页 <span>&rarr;</span></a></li>
	</ul>
</nav>