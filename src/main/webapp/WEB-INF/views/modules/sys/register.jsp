<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户注册</title>
		<meta name="decorator" content="default"/>
	
		<script type="text/javascript">
			$(document).ready(function() {
				/* $("#loginName").focus();
				$("#inputForm").validate({
					rules: {
						loginName: {remote: "${ctx}/sys/regsiter/checkTenantCode?tenantCode=" + encodeURIComponent('${user.loginName}')}
					},
					messages: {
						loginName: {remote: "用户登录名已存在"},
					},
					submitHandler: function(form){
						loading('正在提交，请稍等...');
						form.submit();
					},
					errorContainer: "#messageBox",
					errorPlacement: function(error, element) {
						$("#messageBox").text("输入有误，请先更正。");
						if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
							error.appendTo(element.parent().parent());
						} else {
							error.insertAfter(element);
						}
					}
				}); */
			});
		</script>
</head>
<body>
	
	<form id="inputForm" action="${ctxPath}/register" method="post" class="form-horizontal">
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">租户编号:</label>
			<div class="controls">
				<input name="tenantCode"  maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司名称:</label>
			<div class="controls">
				<input name="tenantName"  maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<input name="master"  maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<input name="email"  maxlength="100" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<input name="phone" maxlength="100"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>