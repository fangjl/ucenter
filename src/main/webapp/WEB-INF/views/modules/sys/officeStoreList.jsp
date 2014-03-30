<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门店管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
	
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/storeList">门店列表</a></li>
 		<li><a href="${ctx}/sys/office/storeForm">门店添加</a></li>
		</ul>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>门店名称</th><th>归属区域</th><th>门店编码</th><th>门店类型</th><th>备注</th><th>操作</th></tr>
		<c:forEach items="${page.list}" var="office">
			<tr id="${office.id}" >
				<td><a href="${ctx}/sys/office/storeForm?id=${office.id}">${office.name}</a></td>
				<td>${office.area.name}</td>
				<td>${office.code}</td>
				<td>${fns:getDictLabel(office.type, 'sys_office_type', '无')}</td>
				<td>${office.remarks}</td>
				<td>
					<a href="${ctx}/sys/office/storeForm?id=${office.id}">修改</a>
					<a href="${ctx}/sys/office/storeDelete?id=${office.id}" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>