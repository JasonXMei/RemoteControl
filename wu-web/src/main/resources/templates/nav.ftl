<style type="text/css">
#nav-container{
padding-left: 28px;
}
</style>
<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid" id="nav-container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#" style="color: white;">智慧联盟后台管理系统</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
						<#if (user.userName)??>
							<span class="glyphicon glyphicon-user"></span> 欢迎您，${user.userName} <span class="caret"></span>
							<ul class="dropdown-menu" role="menu">
								<li>
									<a href="#"> 
										<span class="glyphicon glyphicon-cog"></span> 个人信息设置
									</a>
								</li>
							</ul>
						<#else>
							<span class="glyphicon glyphicon-user"></span> 欢迎您，游客  <span class="caret"></span>
						</#if>
					</a>
				</li>
			</ul>
		</div> <!-- /.navbar-collapse -->
	</div> <!-- /.container-fluid -->
</nav>