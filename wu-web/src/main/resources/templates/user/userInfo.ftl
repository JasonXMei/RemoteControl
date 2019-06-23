<!DOCTYPE HTML>
<html>
<head>
<title>用户管理-详情</title>
<#include "../headerSource.ftl">
<style type="text/css">
#userDetailDiv{
	margin: 10px 0;
    width: 100%;
}
#userDetailTable td label{
	width: 80px;
	text-align: left;
}
#userDetailTable .form-control{
    width: 200px;
}
#paymentCodeImg{
	display: inline-block;
 }
.panel-body div{
	margin: 5px 0;
}
#defaultSubUserDiv{
    display: none;
}
#defaultShopDiv{
    display: none;
}
#sexTd{
    vertical-align: middle;
}
#saveInfoBtn{
    margin-left: 5px;
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
        <form id="userForm" class="form-inline" method="post" action="/user/saveOrUpdate/" enctype="multipart/form-data">
            <input type="hidden" name="subUserList" id="subUserList"/>
            <input type="hidden" name="shopList" id="shopList"/>
			<div class="form-group" id="userDetailDiv">
				<button class="btn btn-default" type="button">
					<span class="glyphicon glyphicon-user"></span>
					<#if (userDetail.userVO.id>0)>
						<span>用户详情</span>
                        <input type="hidden" name="id" value="${userDetail.userVO.id}"/>
                        <input type="hidden" name="permissionInt" value="${userDetail.userVO.permissionInt}"/>
					<#else>
						<span>用户注册</span>
                        <input type="hidden" name="id" value="0"/>
                    </#if>
				</button>

                <#--保存按钮：1)推荐人邀请来注册；2)登陆用户本人的详情页;3)超管-->
                <#if ((userDetail.userVO.id == 0))
                        || ((user??) && (userDetail.userVO.id > 0) && (user.id == userDetail.userVO.id))
                        || ((user.permission)?? && (user.permission.type == 0))>
                    <div class="form-group pull-right" id="saveInfoBtn">
                        <button class="btn btn-success" type="button">
                            <span class="glyphicon glyphicon-ok"></span>
                            <span class="button-left">保存</span>
                        </button>
                    </div>
                </#if>
                <#--审核按钮：超管身份且用户状态为待审核-->
                <#if ((user.permission.type)?? && (user.permission.type == 0)) && ((userDetail.userVO.statusStr)?? && (userDetail.userVO.statusStr == "待审核"))>
                    <div class="form-group pull-right" id="saveInfoBtn">
                        <button class="btn btn-warning" type="button" onclick="auditAllow(${userDetail.userVO.id}, 0)">
                            <span class="glyphicon glyphicon-ok"></span>
                            <span class="button-left">审核通过</span>
                        </button>
                    </div>
                </#if>
                <#--超管才能返回账号列表-->
                <#if ((user.permission.type)?? && (user.permission.type == 0))>
                    <div class="form-group pull-right">
                        <a href="/user/list/0">
                            <button class="btn btn-default" type="button">
                                <span class="glyphicon glyphicon-arrow-left"></span>
                                <span class="button-left">返回</span>
                            </button>
                        </a>
                    </div>
                </#if>
			</div>
			<table class="table" id="userDetailTable">
				<tr>
					<td>
						<div class="form-group">
							<label for="userName">用户名:</label>
							<input type="text" class="form-control" id="userName" name="userName" value="${userDetail.userVO.userName!''}"  placeholder="请输入用户名"/>
						</div>
					</td>
                    <td>
                        <div class="form-group">
                            <label for="mobile">手机号:</label>
                            <input type="text" class="form-control" id="mobile" name="mobile" value="${userDetail.userVO.mobile!''}"  placeholder="请输入手机号">
                        </div>
                    </td>
                    <#--<td id="sexTd">
                        <div class="form-group">
                            <label>性别:</label>
                            <label class="radio-inline">
                                <input type="radio" name="sexStr" value="0" checked> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="sexStr" value="1"> 女
                            </label>
                        </div>
                    </td>-->
				</tr>
				<tr>
					<td>
						<div class="form-group">
							<label for="qqNumber">QQ号:</label>
							<input type="text" class="form-control" id="qqNumber" name="qqNumber" value="${userDetail.userVO.qqNumber!''}"  placeholder="请输入QQ号">
						</div>
					</td>
                    <td>
                        <div class="form-group">
                            <label for="location">位置:</label>
                            <input type="text" class="form-control" id="location" name="location" value="${userDetail.userVO.location!''}"  placeholder="请输入位置">
                        </div>
                    </td>
				</tr>
				<tr>
                    <#if ((userDetail.userVO.id == 0))
                    || ((user.permission)?? && (user.permission.type == 0))>
                        <td>
                            <div class="form-group">
                                <label for="allowOrderTimes">日补单次数:</label>
                                <input type="text" class="form-control" id="allowOrderTimes" name="allowOrderTimes" value="${userDetail.userVO.allowOrderTimes!2}"  placeholder="请设置账号日补单次数">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label>有效时间:</label>
                                <div class='input-group date' id='datetimepickerValid'>
                                    <input type='text' class="form-control" name="validTimeStr" id="validTimeStr" value="${userDetail.userVO.validTimeStr!''}" placeholder="请设置账号有效时间"/>
                                    <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar">
                                </div>
                            </div>
                        </td>
                    <#else>
                        <td>
                            <div class="form-group">
                                <label for="allowOrderTimes">日补单次数:</label>
                                <input type="text" class="form-control" id="allowOrderTimes" name="allowOrderTimes" readonly value="${userDetail.userVO.allowOrderTimes!2}"  placeholder="请设置账号日补单次数">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label>有效时间:</label>
                                <div class='input-group date' id='datetimepickerValid'>
                                    <input type='text' class="form-control" name="validTimeStr" id="validTimeStr" readonly value="${userDetail.userVO.validTimeStr!''}" placeholder="请设置账号有效时间"/>
                                    <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar">
                                </div>
                            </div>
                        </td>
                    </#if>
				</tr>
                <#if (referrerUserInviteCode)?? && (referrerUserInviteCode != '')>
                    <tr>
                        <td>
                            <div class="form-group">
                                <label for="referrerUserInviteCode">邀请码:</label>
                                <input type="text" class="form-control"  id="referrerUserInviteCode" name="referrerUserInviteCode" value="${referrerUserInviteCode}" readonly>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="password">密码:</label>
                                <input type="text" class="form-control"  id="password" name="password" value="${userDetail.userVO.password!'123456'}"/>
                            </div>
                        </td>
                    </tr>
                <#else>
                    <tr>
                        <td colspan="2">
                            <div class="form-group">
                                <label for="password">密码:</label>
                                <input type="text" class="form-control"  id="password" name="password" value="${userDetail.userVO.password!'123456'}"/>
                            </div>
                        </td>
                    </tr>
                </#if>
                <tr>
                    <td colspan="2">
                        <div class="form-group">
                            <label for="paymentCodeImg">返款码图片:</label>
                                <#if (userDetail.userVO.paymentCodeImg)?? && (userDetail.userVO.paymentCodeImg != '')>
                                    <button type="button" class="btn btn-info" onclick="showPaymentCodeImg('${userDetail.userVO.paymentCodeImg}', 1)">查看图片</button>
                                </#if>
                            <input type="file" name="file" id="paymentCodeImg">
                        </div>
                    </td>
                </tr>
                <tr>
					<td colspan="2">
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="form-group" id="userDetailDiv">
									<button class="btn btn-default" type="button">
										<span>小号列表</span>
									</button>
									<div class="form-group pull-right">

                                        <#--保存按钮：1)推荐人邀请来注册；2)登陆用户本人的详情页;3)超管-->
                                        <#if ((referrerUserInviteCode)?? && (userDetail.userVO.id == 0))
                                        || ((user??) && (userDetail.userVO.id > 0) && (user.id == userDetail.userVO.id))
                                        || ((user.permission)?? && (user.permission.type == 0))>
                                            <button class="btn btn-success" type="button" onclick="addElement('#defaultSubUserDiv','#subUserPanelBody')">
                                                <span class="glyphicon glyphicon-plus"></span>
                                            </button>
                                        </#if>
									</div>
								</div>
							</div>
							<div class="panel-body" id="subUserPanelBody">
								<#if (userDetail.subUserCount)?? && (userDetail.subUserCount > 0)>
									<#list userDetail.subUserList as subUserItem>
										<div>
                                            <input type="text" class="form-control" name="subUserName" value="${subUserItem.subUserName}"  placeholder="请输入小号名">
                                            <input type="text" class="form-control" name="age" value="${subUserItem.age}"  placeholder="请输入年龄">
                                            <#if (subUserItem.sexStr == '男')>
                                                <select class="form-control">
                                                    <option value="0" selected>男</option>
                                                    <option value="1">女</option>
                                                </select>
                                            <#else>
                                                <select class="form-control">
                                                    <option value="0">男</option>
                                                    <option value="1" selected>女</option>
                                                </select>
                                            </#if>

                                            <input type="hidden" value="${subUserItem.id}" name="subUserId">

                                            <#--删除按钮：1)推荐人邀请来注册；2)登陆用户本人的详情页;3)超管-->
                                            <#if ((userDetail.userVO.id == 0))
                                            || ((user??) && (userDetail.userVO.id > 0) && (user.id == userDetail.userVO.id))
                                            || ((user.permission)?? && (user.permission.type == 0))>
                                                <button class="btn btn-warning" type="button" onclick="trashElement(this)">
                                                    <span class="glyphicon glyphicon-trash"></span>
                                                </button>
                                            </#if>
                                        </div>
									</#list>
								<#else>
								</#if>
                                <div id="defaultSubUserDiv">
                                    <input type="text" class="form-control" name="subUserName" placeholder="请输入小号名">
                                    <input type="text" class="form-control" name="age" placeholder="请输入年龄">
                                    <select class="form-control">
                                        <option value="0" selected>男</option>
                                        <option value="1">女</option>
                                    </select>
                                    <input type="hidden" value="0" name="subUserId">
                                    <button class="btn btn-warning" type="button" onclick="trashElement(this)">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                </div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="form-group" id="userDetailDiv">
									<button class="btn btn-default" type="button">
										<span>店铺列表</span>
									</button>

                                    <#--新增店铺功能只有超管才有权限-->
                                    <#if (user.permission)?? && (user.permission.type == 0)>
                                        <div class="form-group pull-right">
                                            <button class="btn btn-success" type="button" onclick="addElement('#defaultShopDiv','#shopPanelBody')">
                                                <span class="glyphicon glyphicon-plus"></span>
                                            </button>
                                        </div>
                                    </#if>
								</div>
							</div>
							<div class="panel-body" id="shopPanelBody">
								<#if (userDetail.shopCount)?? && (userDetail.shopCount > 0)>
									<#list userDetail.shopList as shopItem>
										<div>
                                            <input type="text" class="form-control" name="shopName" value="${shopItem.shopName}"  placeholder="请输入店铺名">
                                            <input type="hidden" value="${shopItem.id}" name="shopId">

                                            <#--超管权限：删除店铺-->
                                            <#if (user.permission)?? && (user.permission.type == 0)>
                                                <button class="btn btn-warning" type="button" onclick="trashElement(this)">
                                                    <span class="glyphicon glyphicon-trash" ></span>
                                                </button>
                                            </#if>
                                        </div>
									</#list>
								</#if>
                                <div id="defaultShopDiv">
                                    <input type="text" class="form-control" name="shopName" placeholder="请输入店铺名">
                                    <input type="hidden" value="0" name="shopId">
                                    <button class="btn btn-warning" type="button" onclick="trashElement(this)">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                </div>
							</div>
						</div>
					</td>
				</tr>
			</table>
        </form>
	 </div>
</div>
<script type="text/javascript">
$(function () {
    var message = "${message!''}";
    if(message){
        showAlertModal("温馨提示", message);
    }

    var sex = "${(userDetail.userVO.sexStr)!''}";
    if(sex == "男"){
        $("input[type=radio][name=sexStr][value=0]").attr("checked", "checked");
    }else if(sex == "女"){
        $("input[type=radio][name=sexStr][value=1]").attr("checked", "checked");
    }else{}

    $('#datetimepickerValid').datetimepicker({
        format : 'YYYY-MM-DD',
        locale : moment.locale('zh-cn')
    });
    $('#userForm').bootstrapValidator({
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
                        regexp: /^[1][34578][0-9]{9}$/,
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
                        regexp: /^\d+(\.{0,1}\d+){0,1}$/,
                        message: '日补单次数应为非负整数'
                    }
                }
            },
            validTimeStr: {
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
            file: {
                validators: {
                    file: {
                        extension: 'png,jpg,jpeg',
                        type: 'image/png,image/jpg,image/jpeg',
                        message: '请重新选择图片'
                    }
                }
            }
        }
    });

    $("#saveInfoBtn").on("click",function() {
        //获取表单对象
        var bootstrapValidator = $("#userForm").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            var subUserList = [];
            $.each($("input[name='subUserName']"), function (index, val) {
                var obj = $(val);
                if(obj.val()){
                    var subUser = {};
                    subUser.subUserName = obj.val();
                    subUser.age = obj.next().val();
                    subUser.sex = obj.next().next().val();
                    subUser.id = obj.next().next().next().val();
                    subUserList[index] = subUser;
                }
            });
            subUserList = $.grep(subUserList, function (n) {
                return $.trim(n).length > 0;
            });
            $("#subUserList").val(JSON.stringify(subUserList));

            var shopList = [];
            $.each($("input[name='shopName']"), function (index, val) {
                var obj = $(val);
                if(obj.val()){
                    var shop = {};
                    shop.shopName = obj.val();
                    shop.id = obj.next().val();
                    shopList[index] = shop;
                }
            });
            shopList = $.grep(shopList, function (n) {
                return $.trim(n).length > 0;
            });
            $("#shopList").val(JSON.stringify(shopList));

            document.getElementById("userForm").submit();
        }
    });
})
function addElement(defaultElement, appendElement) {
    var html = $(defaultElement).clone();
    $(appendElement).append(html);
    html.show();
}
function trashElement(obj) {
    $(obj).parent().remove();
}
function auditAllow(userId, status) {
    showConfirmModal("温馨提示", "请确认用户信息无误才审核通过,审核通过后此账号享有智慧联盟普通账号权限!");
    $("#confirmModalBtn").click(function () {
        handleUser(userId, status)
    });
}
function handleUser(userId, status) {
    window.location.href = "/user/handle/" + userId + "/" + status + "/";
}
</script>
</body>
</html>
