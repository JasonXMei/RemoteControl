<style type="text/css">
#left-nav-div .panel-collapse li{
	margin-left:-18px;
}
#left-nav-div .leftMenu{
	cursor: pointer;
	border-radius: 0px;
}
</style>
<script type="text/javascript">
$(function() {
	$('#datetimepickerRegister').datetimepicker({
		format : 'YYYY-MM-DD',
		locale : moment.locale('zh-cn')
	});
	$('#registerForm').bootstrapValidator({
	    feedbackIcons: {
	        valid: 'glyphicon glyphicon-ok',
	        invalid: 'glyphicon glyphicon-remove',
	        validating: 'glyphicon glyphicon-refresh'
	    },
	    fields: {
	    	userName: {
	            message: '用户名无效',
	            validators: {
	                notEmpty: {
	                    message: '用户名不能为空'
	                }
	            }
	        },
	    	password: {
	            validators: {
	                notEmpty: {
	                    message: '密码不能为空'
	                }
	            }
	        },
	        mobile: {
	            message: '手机号无效',
	            validators: {
	                notEmpty: {
	                    message: '手机号不能为空'
	                },
	                regexp: {
	                	regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
	                	message: '手机号格式不正确'
	                }
	            }
	        },
	        qqNumber: {
	            message: 'QQ号无效',
	            validators: {
	                notEmpty: {
	                    message: 'QQ号不能为空'
	                },
	                regexp: {
	                	regexp: /[1-9][0-9]{4,14}/,
	                	message: 'QQ号格式不正确'
	                }
	            }
	        },
	        allowOrderTimes: {
	            message: '日补单次数无效',
	            validators: {
	                regexp: {
	                	regexp: /^[1-9]\d*|0$/,
	                	message: '日补单次数应为非负整数'
	                }
	            }
	        },
	        validTime: {
	            validators: {
	            	notEmpty: {
	                    message: '有效时间不能为空'
	                },
	            	regexp: {
	                	regexp: /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/,
	                	message: '有效时间格式应为: 2019-04-17(yyyy-MM-dd)'
	                }
	            }
	        },
	        age: {
	            validators: {
	                regexp: {
	                	regexp: /^[1-9]\d*|0$/,
	                	message: '年龄应为非负整数'
	                }
	            }
	        }
	    }
	}).on('success.form.bv', function(e) {
	    // Prevent form submission
	    e.preventDefault();
	
	    // Get the form instance
	    var $form = $(e.target);
	
	    // Get the BootstrapValidator instance
	    var bv = $form.data('bootstrapValidator');
	
	    // Use Ajax to submit form data
	    $.post($form.attr('action'), $form.serialize(), function(result) {
	        console.log(result);
	    }, 'json');
	});
});
</script>
<div class="col-md-2" id="left-nav-div">
    <div class="panel-group table-responsive" role="tablist">
        <div class="panel panel-primary leftMenu" onclick="return false;">
            <div class="panel-heading main-radius"  data-toggle="collapse" data-target="#collapseListGroup" role="tab" >
                <h4 class="panel-title user-left">
                    <span class="glyphicon glyphicon-th-large"></span> 控制台
                </h4>
            </div>
        </div>
        
        <div class="panel panel-primary leftMenu"">
            <!-- 利用data-target指定要折叠的分组列表 -->
            <div class="panel-heading main-radius" id="collapseListGroupHeading1" data-toggle="collapse" data-target="#collapseListGroup1" role="tab">
                <h4 class="panel-title user-left">
                    <span class="glyphicon glyphicon-user right"></span> 账号管理                    
                </h4>
            </div>
            <!-- .panel-collapse和.collapse标明折叠元素 .in表示要显示出来 -->
            <div id="collapseListGroup1" class="panel-collapse collapse" role="tabpanel" aria-labelledby="collapseListGroupHeading1">
                <ul class="list-group">
                    <li class="list-group-item">
                        <a href="#">
		                    <button class="menu-item-left user-button-left">
	                     		<span class="glyphicon glyphicon-list"></span> 账号列表
		                    </button>
                        </a>
                    </li>
                    <li class="list-group-item">
                        <a href="#">
		                    <button class="menu-item-left user-button-left" data-toggle="modal" data-target="#registerModal">
	                     		<span class="glyphicon glyphicon-plus"></span> 账号注册
		                    </button>
                        </a>
                    </li>
                </ul>
            </div>
        </div><!--panel end-->
        
        <div class="panel panel-primary leftMenu">
            <!-- 利用data-target指定要折叠的分组列表 -->
            <div class="panel-heading main-radius" id="collapseListGroupHeading2" data-toggle="collapse" data-target="#collapseListGroup2" role="tab" >
                <h4 class="panel-title user-left">
                    <span  class="glyphicon glyphicon-tasks right"></span> 任务管理
                </h4>
            </div>
            <!-- .panel-collapse和.collapse标明折叠元素 .in表示要显示出来 -->
            <div id="collapseListGroup2" class="panel-collapse collapse" role="tabpanel" aria-labelledby="collapseListGroupHeading2">
                <ul class="list-group">
                    <li class="list-group-item">
                        <a href="#">
	                        <button class="menu-item-left user-button-left">
	                      		<span class="glyphicon glyphicon-list"></span> 补单列表
	                        </button>
                        </a> 
                    </li>              
                </ul>
            </div>
        </div><!--panel end-->
    </div>
</div>
<div id="registerModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">智慧联盟账号注册</h4>
			</div>
			<div class="modal-body">
				<form id="registerForm">
					<div class="form-group">
						<label for="userName">账号名</label> 
						<input type="text" class="form-control" id="userName" name="userName" placeholder="请输入账号名">
					</div>
					<div class="form-group">
						<label for="password">密码</label> 
						<input type="text" class="form-control" id="password" name="password" placeholder="请设置账号密码">
					</div>
					<div class="form-group">
						<label for="mobile">手机号</label> 
						<input type="text" class="form-control" id="mobile" name="mobile" placeholder="请输入手机号">
					</div>
					<div class="form-group">
						<label for="qqNumber">QQ号</label> 
						<input type="text" class="form-control" id="qqNumber" name="qqNumber" placeholder="请输入QQ号">
					</div>
					<div class="form-group">
						<label for="allowOrderTimes">日补单次数</label> 
						<input type="text" class="form-control" id="allowOrderTimes" name="allowOrderTimes" placeholder="请设置账号日补单次数限制,此规则应用到该账号关联下的所有小号">
					</div>
					<div class="form-group">
						<label>有效时间</label>
						<div class='input-group date' id='datetimepickerRegister'>
							<input type='text' class="form-control" name="validTime" id="validTime" placeholder="请设置账号有效时间,此规则应用到该账号关联下的所有小号"/> 
							<span class="input-group-addon"> 
							<span class="glyphicon glyphicon-calendar">
						</div>
					</div>
					<div class="form-group">
						<label>性别</label>
						<label class="radio-inline">
					  		<input type="radio" name="sex" value="1"> 男
						</label>
						<label class="radio-inline">
					  		<input type="radio" name="sex" value="2"> 女
					  	</label>
					</div>
					<div class="form-group">
						<label for="age">年龄</label> 
						<input type="text" class="form-control" id="age" name="age" placeholder="请输入年龄">
					</div>
					<div class="form-group">
						<label for="location">位置</label> 
						<input type="text" class="form-control" id="location" name="location" placeholder="请输入位置">
					</div>
					<div class="form-group">
						<label for="paymentCodeImg">上传支付宝付款码图片</label> 
						<input type="file" id="paymentCodeImg">
					</div>
					<#-- <button type="submit" class="btn btn-default">Submit</button> -->
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-info" onclick="$('#registerForm').submit()">保存</button>
			</div>
		</div> <!-- /.modal-content -->
	</div> <!-- /.modal-dialog -->
</div> <!-- /.modal -->