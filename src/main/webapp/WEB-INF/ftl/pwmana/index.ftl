<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<title>账号列表 - 账号信息</title>
		<@_quote.quote 1/>
		<script type="text/javascript">
			so.init(function(){
				//初始化全选。
				so.checkBoxInit('#checkAll','[check=box]');
				
				<@shiro.hasPermission name="/pwmana/deletePwmanaById.shtml">
				//全选
				so.id('deleteAll').on('click',function(){
					var checkeds = $('[check=box]:checked');
					if(!checkeds.length){
						return layer.msg('请选择要删除的选项。',so.default),!0;
					}
					var array = [];
					checkeds.each(function(){
						array.push(this.value);
					});
					return deleteById(array);
				});
				</@shiro.hasPermission>
			});
			<@shiro.hasPermission name="/pwmana/deletePwmanaById.shtml">
			<#--根据ID数组删除账号-->
			function deleteById(ids){
				var index = layer.confirm("确定这"+ ids.length +"个账号？",function(){
					var load = layer.load();
					$.post('${basePath}/pwmana/deletePwmanaById.shtml',{ids:ids.join(',')},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.default),!0;
						}else{
							layer.msg(result.resultMsg);
							setTimeout(function(){
								$('#formId').submit();
							},1000);
						}
					},'json');
					
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
			<@shiro.hasPermission name="/pwmana/addPwmana.shtml">
			<#--添加账号-->
			function addPwmana(){
				<#--校验非空-->
				var loginName = $('#loginName').val();
				var	password = $('#password').val();
				var	siteUrl = $('#siteUrl').val();
				var	phone = $('#phone').val();
				
				if($.trim(loginName) == ''){
					return layer.msg('登录名不能为空。',so.default),!1;
				}
				if($.trim(password) == ''){
					return layer.msg('登录密码不能为空。',so.default),!1;
				}
				if($.trim(siteUrl) == ''){
					return layer.msg('网址不能为空。',so.default),!1;
				}
				if($.trim(phone) != '' &&  !/^[0-9]{11}$/.test(phone)){
					return layer.msg('绑定手机号为11位数字。',so.default),!1;
				}
				<!--ajax提交新建表单-->
				var load = layer.load();
				var data = $("#boxPwmanaForm").serialize();
				$.ajax({
			        type: "POST",
			        url: '${basePath}/pwmana/addPwmana.shtml',
			        dataType: "json",
			        data: data, // 以json格式传递  
			        success: function (result) {
			        	layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.default),!1;
						}
						layer.msg('添加成功。');
						setTimeout(function(){
							$('#formId').submit();
						},1000);
			        },
			        error: function(e) { 
						console.log("e",e); 
					} 
			    });
			}
			
			/*
			*根据账号ID编辑修改。
			*/
			function selectPwmanaById(id){
				var load = layer.load();
				$.post("${basePath}/pwmana/selectPwmanaById.shtml",{id:id},function(result){
					layer.close(load);
					if(result){
						$('#id').val(result.id);
						$('#loginName').val(result.loginName);
						$('#password').val(result.password);
						$('#email').val(result.email);
						$('#phone').val(result.phone);
						$('#siteName').val(result.siteName);
						$('#siteUrl').val(result.siteUrl);
						$('#remarks').val(result.remarks);
						$('#createTime').val(result.createTime.substring(0,19));//2016-12-15 16:59:28
						//设置显示默认值
						$('#registerTime').val(result.registerTime);
						$('#registerTimeDiv').datetimepicker('update',result.registerTime);
					}else{
						return layer.msg('编辑账号信息失败，请刷新后重试',so.default);
					}
				},'json');
			}
			
			</@shiro.hasPermission>
		</script>
	</head>
	<body data-target="#one" data-spy="scroll">
		<@_topNav.top 1/>
		<div class="tpl-page-container tpl-page-header-fixed">
		<@_leftNav.left 4.1/>
		<div class="tpl-content-wrapper">
			<div class="tpl-content-page-title">账号列表</div>
            <ol class="am-breadcrumb">
                <li><a href="#" class="am-icon-home">首页</a></li>
                <li><a href="#">权限管理</a></li>
                <li class="am-active">账号列表</li>
            </ol>
            <div class="tpl-portlet-components">
	            <form method="post" action="" id="formId" class="form-inline">
					<div clss="well">
				      <div class="form-group">
				        <input type="text" class="form-control" style="width: 300px;" value="${findContent?default('')}" 
				        			name="findContent" id="findContent" placeholder="输入账号类型 / 账号名称">
				      </div>
				     <span class=""> <#--pull-right -->
			         	<a class="btn btn-primary" title="查询" type="submit"  onclick="$('#formId').submit();">
		                <i class="fa fa-search"></i></a>
			         	<@shiro.hasPermission name="/pwmana/addPwmana.shtml">
			         		<a class="btn btn-success" title="增加账号"  data-toggle='modal' data-target='#addpwmana'>
		                    <i class="fa fa-plus-square"></i></a>
			         	</@shiro.hasPermission>
			         	<@shiro.hasPermission name="/pwmana/deletePwmanaById.shtml">
			         		<a class="btn btn-default label-danger" id="deleteAll" title="删除" href="javascript:void(0);">
		                    <i class="fa fa-trash-o"></i></a>
			         	</@shiro.hasPermission>
			         </span>    
			        </div>
				<hr>
				<table class="table table-bordered">
					<tr>
						<th><input type="checkbox" id="checkAll"/></th>
						<th>登录名</th>
						<th>密码</th>
						<th>网址</th>
						<th>操作</th>
					</tr>
					<#if page?exists && page.list?size gt 0 >
						<#list page.list as it>
							<tr>
								<td><input value="${it.id}" check='box' type="checkbox" /></td>
								<td>${it.loginName?default('-')}</td>
								<td>${it.password?default('-')}</td>
								<td>${it.siteUrl?default('-')}</td>
								<td>
									<@shiro.hasPermission name="/pwmana/deletePwmanaById.shtml">
							         	<a class='btn btn-default label-info' id='detailUpdate' title='修改' 
							         	 data-toggle='modal' data-target='#addpwmana' onclick="selectPwmanaById(${it.id});">
							         	<i class='fa fa-pencil'></i></a>&nbsp;
	                    				<a class="btn btn-default label-danger" id="deleteRow" title="删除" href="javascript:deleteById([${it.id}]);">
	                    				<i class="fa fa-trash-o"></i></a>
						         	</@shiro.hasPermission>
								</td>
							</tr>
						</#list>
					<#else>
						<tr>
							<td class="text-center danger" colspan="4">没有找到账号</td>
						</tr>
					</#if>
				</table>
				<#if page?exists>
					<div class="pagination pull-right">
						${page.pageHtml}
					</div>
				</#if>
				</form>
				
				<@shiro.hasPermission name="/pwmana/addPwmana.shtml">
				<#--添加弹框-->    
				<div class="modal fade" id="addpwmana" tabindex="-1" pwmana="dialog" aria-labelledby="addpwmanaLabel" style="margin-top:70px;">
				  <div class="modal-dialog" pwmana="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addpwmanaLabel">添加账号</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxPwmanaForm"  class="form-horizontal">
				          <input type="hidden" id="id" name="id"/>
				          <input type="hidden" id="createTime" name="createTime"/>
				          <div class="form-group">
				            <label for="recipient-name" class="col-sm-3 control-label">登录名:</label>
				            <div class="col-sm-8">
				            <input type="text" class="form-control" name="loginName" id="loginName" placeholder="请输入登录名"/>
				            </div>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="col-sm-3 control-label">密码:</label>
				            <div class="col-sm-8">
				            <input type="text" class="form-control" id="password" name="password"  placeholder="请输入登录密码"/>
				            </div>
				          </div>
				          <div class="form-group">
						    <label for="registerTime"  class="col-sm-3 control-label">注册日期:</label>
						      <div id="registerTimeDiv" class="col-sm-8 input-group date form_datetime "  
						           data-date-format="yyyy-mm-dd" data-link-field="registerTime" data-link-format="yyyy-mm-dd">
			                    <input class="form-control"  type="text" value="" readonly>
			                    <span id="start-glyphicon-remove" class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			                 </div>
							 <input type="hidden" id="registerTime" name="registerTime" value="" />
						  </div>
				          <div class="form-group">
				            <label for="recipient-name" class="col-sm-3 control-label">网站名称:</label>
				            <div class="col-sm-8">
				            <input type="text" class="form-control" id="siteName" name="siteName"  placeholder="请输入网站名称"/>
				            </div>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="col-sm-3 control-label">网址:</label>
				            <div class="col-sm-8">
				            <input type="text" class="form-control" id="siteUrl" name="siteUrl"  placeholder="请输入网址"/>
				            </div>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="col-sm-3 control-label">邮箱:</label>
				            <div class="col-sm-8">
				            <input type="text" class="form-control" id="email" name="email"  placeholder="请输入绑定邮箱"/>
				            </div>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="col-sm-3 control-label">手机号:</label>
				            <div class="col-sm-8">
				            <input type="text" class="form-control" id="phone" name="phone"  placeholder="请输入角色类型  [数字] 11位"/>
				            </div>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="col-sm-3 control-label">备注:</label>
				            <div class="col-sm-8">
				            	<input type="text" class="form-control" id="remarks" name="remarks"  placeholder="请输入备注"/>
				            </div>
				          </div>
				          
				        </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				        <button type="button" onclick="addPwmana();" class="btn btn-primary">保存</button>
				      </div>
				    </div>
				  </div>
				</div>
				<#--/添加弹框-->
				</@shiro.hasPermission>
            </div>
		</div>
		</div>
		<@_quoteAmazeUi.quoteAmazeUi 1/>
		
		<script type="text/javascript">
		    $('#registerTimeDiv').datetimepicker({
		        language:  'zh-CN',
		        weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				minView : 2,
				startView: 2,
				forceParse: true,
				format:"yyyy-mm-dd",
		        showMeridian: 1
		    });
		</script>
		<style type="text/css">
			#registerTimeDiv{
			 	padding-right: 15px;
	    		padding-left: 15px;
			}
		</style>
		 
	</body>
</html>