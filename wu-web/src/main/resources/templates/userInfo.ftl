<!DOCTYPE HTML>
<html>
<head>
<title>用户管理-详情</title>
<meta http-equiv="content-Type" content="text/html; charset=utf-8">
<link type="text/css" href="css/bootstrap.css" rel="stylesheet"/>
<link type="text/css" href="css/bootstrap-select.min.css" rel="stylesheet"/>
<link type="text/css" href="css/bootstrap-datetimepicker.css" rel="stylesheet"/>
<link rel="icon" type="image/x-icon" href="/images/logo.jpg">

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="js/moment-with-locales.js"></script> 
<script type="text/javascript" src="js/bootstrap-datetimepicker.js"></script> 
<style type="text/css">
</style>
</head>
<body>
<!-- include nav-->
<#include "nav.ftl">
<div class="row main-top">
	<!-- include left-nav-->
	<#include "left-nav.ftl">
	<div class="col-md-10" style="margin-right: -15px;margin-top: 21px;">          
		<form class="form-inline" role="form" style="margin-right: 10px">
		  <div class="form-group" style="margin-top: -13px;">
			  <a href="javascript:void(0);" onclick="returnUserList();return false;">
			   <button class="btn btn-default">
				   <span class="glyphicon glyphicon-circle-arrow-left"></span>
				   <span>用户列表</span>
			   </button>
			  </a>	
		  </div>               			         
		  <div class="form-group" style="float: right;margin-top: 31px;">	
			<div style="padding-bottom: 10px;">				        
				<a href="javascript:void(0);" onclick="window.location.href='>web/user/userList.do';return false;">				            
					<button class="btn btn-default"> 
						 <span class="glyphicon glyphicon-arrow-left"></span>
						 <span class="button-left">返回</span>
					</button>
				</a>			        				        						    	  			      						   				    
			</div>	                                					    
		</div>				  				   				
		<div class="user-border" style="margin-top: 2px;"></div>
		<div class="form-group">
			<button class="btn btn-default" style="margin-top: 8px;" onclick="return false;">                      
				   <span class="glyphicon glyphicon-user"></span>
				   <span>个人信息详情</span>
			</button>
		</div>
		<table class="table">
			  <tr>
				  <td>
					  <label style="width:80px;">用户姓名:</label>
					  <input type="text" class="form-control"  value=""/>
				  </td>	
				  <td>
					  <label style="width:80px;">用户类型:</label>		          	     
					  <input type="text" class="form-control"  value="超级管理员"/>		          	     
				  </td>             
			  </tr>		         
			  <tr>
				  <td>
					  <label style="width:80px;">用户性别:</label>		          	      
					  <label class="radio-inline">
						   <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"> 男
					  </label>
					  <label class="radio-inline">
						   <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2"> 女
					  </label>
					  <label class="radio-inline">
						   <input type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3"> 保密
					  </label>
				  </td>
				  <td>
					  <label style="width:80px;">用户生日:</label>
					  <input type='text' class="form-control"  value=""/>
				  </td>
			  </tr>
			  <tr>
				  <td>
					  <label style="width:80px;">用户地址:</label>
					  <input type="text" class="form-control"  value=""/>
				  </td>	
				  <td>
					  <label style="width:80px;">用户电话:</label>
					  <input type="text" class="form-control"  value=""/>
				  </td>	              
			  </tr>		       
			  <tr>
				  <td>
					  <label style="width:80px;">当前余额:</label>
					  <input type="text" class="form-control"  value=""/>
				  </td>	 
				  <td>
					  <label style="width:80px;">当前积分:</label>
					  <input type="text" class="form-control"  value=""/>
				  </td>		             		              
			  </tr>		          
			  <tr>
				  <td>
					  <label style="width:80px;">消费金额:</label>
					  <input type="text" class="form-control"  value=""/>
				  </td>		              
				  <td>
					  <label style="width:80px;">推荐人电话:</label>
					  <input type="text" class="form-control"  value=""/>
				  </td>		              			            		              		             		                          
			  </tr>		          
		</table> 
		</form>  
	 </div>
</div>	
</body>
</html>
