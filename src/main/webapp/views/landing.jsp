<%@ page import="com.free4lab.freeRT.utils.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <%@include file="../statics/head.html"%>
    <title>登录首页</title>
</head>
<body class="front-body">

        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div>
                    <div class="nav-brand"><a class="nav-brand-text">
                        <img src="statics/images/logo.png" alt="轻项目"/>
                    </a></div>
                </div>
                <div class="nav-right">
                    <!-- 产品导航-->
                    <div class="area area-media">
                        <span style="font-size: 14px;">
                            <a href="http://account.free4inno.com/register/freeshare_reg_pri.jsp" class="free_reg" style="text-decoration: underline">注册</a>
                            <a href="./account/landing?redirect_url=<%=request.getParameter("redirect_url")%>" style="text-decoration:underline;padding-right:0;">登录</a>
                        </span>
                    </div>
                </div>
            </div>
        </nav>
<div>
<div class="hero jumbotron" style="background-image:url('statics/images/login_big-freeproject.png');padding-top: 160px;">
    <div class="container">
        <div class="row">
            <div style="margin-top:6%;margin-left: 4%;"><h1 style="color: white">项目之重/管理之轻</h1></div>
        </div>
        <div class="row top_margin30">
            <div style="margin-left: 4%;">
                <a class="btn btn-info btn-lg login-page-btn free_reg" style="color:#ffffff;background-color:#3a221a;border-color:#ffffff" href="<%=Constants.HTTPS_ACCOUNT%>register/freeshare_reg_pri.jsp">免费注册</a>&nbsp;
                <a class="btn btn-info btn-lg login-page-btn" style="color:#ffffff;background-color:#3a221a;border-color: #ffffff" href="./account/landing?redirect_url=<%=request.getParameter("redirect_url")%>" >立即登录</a>
            </div>
        </div>
    </div>
</div>
<div class="container" style="margin-top: -40px">
    <div class="row text-center middle-provide">
        <%--<img src="statics/images/" alt="">--%>
        <h1 style="font-family: 'Microsoft YaHei'">“轻项目”为您提供</h1>
    </div>
    <div class="row">
        <div class="col-xs-6 col-md-3 text-center feature">
            <div>
                <div>
                    <img src="statics/images/taskmanaging.png" width="50%" alt="">
                </div>
                <h4>便捷的任务管理</h4>
                <div>
                    <p>列表式任务管理，化繁为简，项目进展尽在掌握</p>
                </div>
            </div>
        </div>
        <div class="col-xs-6 col-md-3 text-center feature">
            <div>
                <div>
                    <img src="statics/images/reportmanaging.png" width="50%" alt="">
                </div>
                <h4>便捷的项目报告</h4>
                <div>
                    <p>邮件驱动项目报告流转，项目动态尽收眼底</p>
                </div>
            </div>
        </div>
        <div class="clearfix visible-xs-block"></div>
        <div class="col-xs-6 col-md-3 text-center feature">
            <div>
                <div>
                    <img src="statics/images/dataAnalysis.png" width="50%" alt="">
                </div>
                <h4>完备的数据视图</h4>
                <div>
                    <p>全面的日志采集，多维度的数据分析，多视角的数据展示</p>
                </div>
            </div>
        </div>
        <div class="col-xs-6 col-md-3 text-center feature">
            <div>
                <div>
                    <img src="statics/images/plugin.png" width="50%" alt="">
                </div>
                <h4>开放的应用接口</h4>
                <div>
                    <p>强大易用的开放接口，应用扩展和整合就是这样简单</p>
                </div>
            </div>
        </div>

    </div>
</div>

<footer class="footer text-center">
    <p>
        <img src="statics/images/logo.png" alt="轻项目">
    </p>
    <p class="footer-copyright">
        Copyright © 1996-2015 自邮之翼, All Rights Reserved
    </p>
</footer>
</div>
</body>
</html>
