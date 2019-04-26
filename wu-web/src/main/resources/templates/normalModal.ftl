<div class="modal fade" id="confirmModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="confirmModalTitle"></h4>
			</div>
			<div class="modal-body">
				<p id="confirmModalMessage"></p>
			</div>
			<div class="modal-footer">
                <input type="button" class="btn btn-warning" data-dismiss="modal" value="取消" id="confirmModalCalBtn"/>
                <input type="button" class="btn btn-info" value="确定" id="confirmModalBtn"/>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="alertModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="modalTitle"></h4>
			</div>
			<div class="modal-body">
				<p id="modalMessage"></p>
			</div>
			<div class="modal-footer">
				<input type="button" class="btn btn-info" data-dismiss="modal" value="确定" id="modalBtn"/>
			</div>
		</div>
	</div>
</div>
<div id="imgModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="paymentCodeImgHeader">扫码返款</h4>
            </div>
            <div class="modal-body">
                <div class="row" id="paymentCodeImgDiv">
                    <div class="col-md-2"></div>
                    <div class="col-md-8">
                        <a class="thumbnail">
                            <img id="paymentCodeImgDisplay">
                        </a>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning" data-dismiss="modal" id="imgModalCalBtn">取消</button>
                <button type="button" class="btn btn-info" data-dismiss="modal" id="imgModalConfimBtn">确定</button>
            </div>
        </div> <!-- /.modal-content -->
    </div> <!-- /.modal-dialog -->
</div> <!-- /.modal -->
<div id="userPassModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="password">原密码</label>
                    <#if (user.password)??>
                        <input type="hidden" id="password" value="${user.password}">
                    </#if>
                    <input type="password" class="form-control" id="prePassword" placeholder="请输入原密码">
                </div>
                <div class="form-group">
                    <label for="password">新密码</label>
                    <input type="password" class="form-control" id="firstPassword" placeholder="请输入账号新密码">
                </div>
                <div class="form-group">
                    <label for="password">确认密码</label>
                    <input type="password" class="form-control" id="secondPassword" placeholder="请再次确认输入账号新密码">
                </div>
                <div class="alert alert-warning" id="userPassValid"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-info" onclick="saveUserPassSetting()">保存</button>
            </div>
        </div> <!-- /.modal-content -->
    </div> <!-- /.modal-dialog -->
</div> <!-- /.modal -->
<style type="text/css">
#userPassValid{
    display: none;
}
</style>
<script type="text/javascript">
function showPaymentCodeImg(paymentCodeImg, flag) {
    if(flag == 1){
        $("#paymentCodeImgHeader").text("查看图片");
        $("#imgModalCalBtn").hide();
	}else{
        $("#paymentCodeImgHeader").text("扫码返款");
        $("#imgModalCalBtn").show();
        /*$("#imgModalConfimBtn").click(function () {
            
        });*/
    }
	$("#paymentCodeImgDisplay").attr("src", paymentCodeImg + "?" + Math.random());
	$("#imgModal").modal("show");
}
function showAlertModal(title, message) {
    $("#modalTitle").text(title);
    $("#modalMessage").text(message);
    $("#alertModal").modal("show");
}
function showConfirmModal(title, message) {
    $("#confirmModalTitle").text(title);
    $("#confirmModalMessage").text(message);
    $("#confirmModal").modal("show");
}
function showUserPassModal() {
    $("#userPassModal").modal("show");
}
function saveUserPassSetting() {
    var userPassword = $("#password").val();
    var prePassword = $("#prePassword").val();
    if(userPassword != prePassword){
        $("#userPassValid").text("原密码输入不正确，请重新输入!").show();
        return;
    }
    var firstPassword = $("#firstPassword").val();
    if(firstPassword.length < 6){
        $("#userPassValid").text("密码设置得太简单啦，长度请设置大于等于6位!").show();
        return;
    }

    var secondPassword = $("#secondPassword").val();
    if(firstPassword != secondPassword){
        $("#userPassValid").text("两次密码输入不一致，请重新输入!").show();
        return;
    }
    $("#userPassValid").text("").hide();

    $.ajax({
        url : "/user/modifyPass",
        method : "post",
        data : {"password": firstPassword},
        success: function (data) {
            if(data.status == 200){
                $("#prePassword").val("");
                $("#firstPassword").val("");
                $("#secondPassword").val("");
                $("#userPassModal").modal("hide");
                showAlertModal("温馨提示", data.description);
            }else{
                $("#userPassValid").text(data.description).show();
            }
        }
    })
}
</script>