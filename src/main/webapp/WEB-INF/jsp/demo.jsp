<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>首页</title>

<!-- Bootstrap -->
<link href="static/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
h1 {
	text-align: center;
}
</style>
<script type="text/javascript" src="static/components/jquery/3.0.0/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		// 绑定form1提交事件
		$("#form1").submit(function(event) {
			var form = new FormData($("#form1")[0]);
			$.ajax({
				url: 'upload',
				type: 'post',
				data: form,
				xhr : function() { 
					//获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数  
					myXhr = $.ajaxSettings.xhr();
					if (myXhr.upload) { //检查upload属性是否存在  
						//绑定progress事件的回调函数  
						myXhr.upload.addEventListener('progress', onprogress, false);
					}
					return myXhr; //xhr对象返回给jQuery使用  
				},
				cache: false,
			    processData: false,
			    contentType: false,
			    success: function(data){
			    	alert(data);
			    },error: function(data){
			    	alert(data);
			    }
			})
			//清空输入的文件框
			$("#exampleInputFile").val('');
			// 阻止默认行为
			event.preventDefault();
		})
		
		// 校验文件大小
		$("#exampleInputFile").on("change",checkSize);
		
		function checkSize(event){
			var file = $(this)[0].files[0];
			var maxSize = $(this).data("max_size");
			if(file.size > maxSize) {
				alert("文件过大");
				//清空输入的文件框
				$(this).val('');
			}
		}
		
		// 侦查附件上传情况
		function onprogress(event) {
			var loaded = event.loaded;                  //已经上传大小情况 
	        var total = event.total;                      //附件总大小 
	        var per = Math.floor(100*loaded/total);      //已经上传的百分比  
	        $("#proges").text( per +"%" );
	        $("#proges").css("width",per + "%");
		}
	})
	
</script>
</head>
<body>
	<h1>上传文件Demo ${username}</h1>
	<hr />
	<form action="upload" method="post"  enctype="multipart/form-data" id="form1">
		<div class="form-group" style="text-align: center;">
			<input type="file" id="exampleInputFile" style="display: inline;" name="file1" data-max_size="1024000">
			<button type="submit" class="btn btn-primary">提交</button>
		</div>
		<div class="progress">
			<div id="proges" class="progress-bar" role="progressbar" aria-valuenow="0"
				aria-valuemin="0" aria-valuemax="100" >0%
			</div>
		</div>
	</form>
</body>
</html>