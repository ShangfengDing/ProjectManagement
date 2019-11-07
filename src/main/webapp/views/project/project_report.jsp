<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <%@include file="/statics/head.html"%>
    <title><s:property value="project.name"/> - 项目报告 - 轻项目</title>
    <link href='statics/calendar/fullcalendar.min.css' rel='stylesheet' />
    <link href='statics/calendar/fullcalendar.print.min.css' rel='stylesheet' media='print' />
    <style>
        #calendar {
            max-width: 1140px;
            background: #f5f5f5;
            padding-top: 30px;
            margin: 0 auto;
        }
    </style>
</head>

<body class="front-body">
    <s:include value="../nav.jsp?act=project"/>
    <div class="front-inner front-inner-media">
        <div class="container">

            <input type="hidden" id="projectId" value="<s:property value="project.id"/>"/>

            <div>
                <ul class="breadcrumb">
                    <li><a href="" style="text-decoration: none">我的项目</a></li>
                    <li id="project-name" class="active"><a href="task/viewtask?projectId=<s:property value="project.id"/>"><s:property value="project.name"/></a></li>
                </ul>
            </div>

            <div class="front-toolbar other" style="margin-bottom: 10px">
                <div class="front-toolbar-header clearfix">
                    <button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#display-buttons">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div><div id="display-buttons" class="front-btn-group collapse" data-toggle="buttons">
                    <label class="btn btn-default front-no-box-shadow active" data-group="list">
                        <input type="radio" name="options" autocomplete="off"><span class="glyphicon glyphicon-list"></span> 列表
                    </label>
                    <label class="btn btn-default front-no-box-shadow" data-group="calendar">
                        <input type="radio" name="options" autocomplete="off"><span class="glyphicon glyphicon-calendar"></span> 日历
                    </label>
                </div>
                <a class="btn btn-primary" href="report/new">新建报告</a>
            </div>

            <div id="list-area"></div>

            <div id="calendar-area"></div>

        </div>
        <%@include file="../footer.jsp"%>
    </div>
    <%@include file="/statics/script.html"%>
    <script src='statics/calendar/moment.min.js'></script>
    <script src='statics/calendar/fullcalendar.min.js'></script>
    <script src='statics/calendar/locale-all.js'></script>
    <script src="report/project_report.js"></script>
</body>
</html>
