<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <%@include file="../statics/head.html"%>
    <title>首页 - Free项目</title>
</head>

<body class="front-body">
<s:include value="nav.jsp?act=home">
</s:include>
<div class="front-inner front-inner-media">
    <div class="container">
    </div>
    <%@include file="footer.jsp"%>
</div>
<%@include file="../statics/script.html"%>
</body>
</html>
