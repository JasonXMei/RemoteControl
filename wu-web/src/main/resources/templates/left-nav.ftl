<style type="text/css">
#left-nav-div .panel-collapse li{
	margin-left:-18px;
}
#left-nav-div .leftMenu{
	cursor: pointer;
	border-radius: 0;
}
</style>
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
                        <a href="/user/list/0">
		                    <button class="menu-item-left user-button-left">
	                     		<span class="glyphicon glyphicon-list"></span> 账号列表
		                    </button>
                        </a>
                    </li>
                    <li class="list-group-item">
                        <a href="/user/list/1">
		                    <button class="menu-item-left user-button-left">
	                     		<span class="glyphicon glyphicon-list"></span> 我的推荐列表
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