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
            <div id="inner" class="content">
                <div class="errorbanner">
                    <br />
                    <br />
                    <br />
                    <h1 style="font-size: xx-large">500 ⊙﹏⊙! 服务器发生错误了！</h1>
                    <p>${exceptionStack}</p>
                </div>
            </div>
        </div>
        <s:include value="/statics/footer.html"/>
    </div>
    <s:include value="/statics/script.html"/>
</body>
</html>
