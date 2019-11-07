<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <title>发生错误 - Free项目</title>
    <s:include value="/statics/head.html" />
</head>
<body class="front-body">
<s:include value="/views/nav.jsp"/>
<div class="front-inner">
    <div class="container">
        <div class="container">
            <br />
            <br />
            <br />
            <h2 style="font-size: xx-large">对不起，您不在该项目组无法查看 ⊙﹏⊙! 有问题请联系项目管理员!</h2>
        </div>
    </div>
    <s:include value="/statics/footer.html"/>
</div>
<s:include value="/statics/script.html"/>
</body>
</html>

