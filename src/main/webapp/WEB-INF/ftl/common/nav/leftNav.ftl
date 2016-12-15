<#macro left index>
<div class="tpl-left-nav tpl-left-nav-hover">
    <div class="tpl-left-nav-title">
        Amaze UI 列表
    </div>
    <div class="tpl-left-nav-list">
        <ul class="tpl-left-nav-menu">
            <!--个人中心-->
            <li class="tpl-left-nav-item">
            	<!-- 打开状态 a 标签添加 active 即可   -->
                <a href="javascript:;" class="nav-link tpl-left-nav-link-list ${(index==1.1||index==1.2||index==1.3||index==1.4)?string('active','')}">
                    <i class="am-icon-user"></i>
                    <span>个人中心</span>
                    <!-- 列表打开状态的i标签添加 tpl-left-nav-more-ico-rotate 图表即90°旋转  -->
                    <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right ${(index==1.1||index==1.2||index==1.3||index==1.4)?string('tpl-left-nav-more-ico-rotate','')}"></i>
                </a>
                <ul class="tpl-left-nav-sub-menu" style="display: ${(index==1.1||index==1.2||index==1.3||index==1.4)?string('block','none')}">
                    <li>
                    	<!-- 打开状态 a 标签添加 active 即可   -->
                        <a href="${basePath}/user/index.shtml" class="${(index==1.1)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>个人资料</span>
                            <i class="am-icon-star tpl-left-nav-content-ico am-fr am-margin-right"></i>
                        </a>
                        <a href="${basePath}/user/updateSelf.shtml" class="${(index==1.2)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>资料修改</span>
                        </a>
                        <a href="${basePath}/user/updatePswd.shtml" class="${(index==1.3)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>密码修改</span>
                        </a>
                        <a href="${basePath}/role/mypermission.shtml" class="${(index==1.4)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>我的权限</span>
                        </a>
                    </li>
                </ul>
            </li>
            
            <!--用户中心-->
            <@shiro.hasAnyRoles name='888888,100002'> 
            <li class="tpl-left-nav-item">
                <a href="javascript:;" class="nav-link tpl-left-nav-link-list  ${(index==2.1||index==2.2)?string('active','')}">
                    <i class="am-icon-user-md"></i>
                    <span>用户中心</span>
                    <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right  ${(index==2.1||index==2.2)?string('active','')}"></i>
                </a>
                <ul class="tpl-left-nav-sub-menu" style="display: ${(index==2.1||index==2.2)?string('block','none')}">
                    <li>
                    	<@shiro.hasPermission name="/member/list.shtml">
                        <a href="${basePath}/member/list.shtml"  class="${(index==2.1)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>用户列表</span>
                            <i class="am-icon-star tpl-left-nav-content-ico am-fr am-margin-right"></i>
                        </a>
                        </@shiro.hasPermission>
						<@shiro.hasPermission name="/member/online.shtml">
                        <a href="${basePath}/member/online.shtml"  class="${(index==2.2)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>在线用户</span>
                        </a>
                        </@shiro.hasPermission>
                    </li>
                </ul>
            </li>
            </@shiro.hasAnyRoles>
            
            <!--权限管理-->
            <#--拥有 角色888888（管理员） ||  100003（权限频道）-->
			<@shiro.hasAnyRoles name='888888,100003'>
            <li class="tpl-left-nav-item">
                <a href="javascript:;" class="nav-link tpl-left-nav-link-list  ${(index==3.1||index==3.2||index==3.3||index==3.4)?string('active','')}">
                    <i class="am-icon-key"></i>
                    <span>权限管理</span>
                    <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right ${(index==3.1||index==3.2||index==3.3||index==3.4)?string('active','')}"></i>
                </a>
                <ul class="tpl-left-nav-sub-menu" style="display: ${(index==3.1||index==3.2||index==3.3||index==3.4)?string('block','none')}">
                    <li>
                    	<@shiro.hasPermission name="/role/index.shtml">
                        <a href="${basePath}/role/index.shtml"  class="${(index==3.1)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>角色列表</span>
                            <i class="am-icon-star tpl-left-nav-content-ico am-fr am-margin-right"></i>
                        </a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/role/allocation.shtml">
                        <a href="${basePath}/role/allocation.shtml" class="${(index==3.2)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>角色分配</span>
                            <i class="tpl-left-nav-content tpl-badge-success">18</i>
						</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/permission/index.shtml">
                        <a href="${basePath}/permission/index.shtml" class="${(index==3.3)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>权限列表</span>
                            <i class="tpl-left-nav-content tpl-badge-primary"> 5</i>
						</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/permission/allocation.shtml">
                        <a href="${basePath}/permission/allocation.shtml" class="${(index==3.4)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>权限分配</span>
                        </a>
                        </@shiro.hasPermission>
                    </li>
                </ul>
            </li>
            </@shiro.hasAnyRoles>   
            
           	<!-- 账号信息 -->
            <@shiro.hasAnyRoles name='888888,100004'> 
            <li class="tpl-left-nav-item">
                <a href="javascript:;" class="nav-link tpl-left-nav-link-list  ${(index==4.1||index==4.2)?string('active','')}">
                    <i class="am-icon-users"></i>
                    <span>账号信息</span>
                    <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right  ${(index==4.1||index==4.2)?string('active','')}"></i>
                </a>
                <ul class="tpl-left-nav-sub-menu" style="display: ${(index==4.1||index==4.2)?string('block','none')}">
                    <li>
                    	<@shiro.hasPermission name="/pwmana/index.shtml">
                        <a href="${basePath}/pwmana/index.shtml"  class="${(index==4.1)?string('active','')}">
                            <i class="am-icon-angle-right"></i>
                            <span>账号列表</span>
                            <i class="am-icon-star tpl-left-nav-content-ico am-fr am-margin-right"></i>
                        </a>
                        </@shiro.hasPermission>
                    </li>
                </ul>
            </li>
            </@shiro.hasAnyRoles>
            
            
        </ul>
    </div>
 </div>
</#macro>