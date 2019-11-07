<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/"/>
    <%@include file="/statics/head.html" %>
    <title>我的报告 - 轻项目</title>
    <link href='statics/calendar/fullcalendar.min.css' rel='stylesheet'/>
    <link href='statics/calendar/fullcalendar.print.min.css' rel='stylesheet' media='print'/>
    <style>
        #calendar {
            max-width: 1140px;
            background: #f5f5f5;
            padding-top: 30px;
            margin: 0 auto;
        }

        /*让bootstrap 按钮组选中之后没有蓝框*/
        .btn:focus,
        .btn:active:focus,
        .btn.active:focus,
        .btn.focus,
        .btn:active.focus,
        .btn.active.focus {
            outline: none;
        }
    </style>
</head>

<body class="front-body">
<s:include value="../nav.jsp?act=report"/>
<div class="front-inner front-inner-media">
    <div class="container">

        <%--    <div class="front-toolbar other">
                <div class="front-toolbar-header clearfix">
                    <button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#type-buttons">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div id="type-buttons" class="front-btn-group collapse" data-toggle="buttons">
                    <button class="btn btn-default front-no-box-shadow active" data-group="user">
                        <input type="radio" name="options" autocomplete="off">我提交的 
                    </button>
                    <label class="btn btn-default front-no-box-shadow" data-group="manager">
                        <input type="radio" name="options" autocomplete="off">我收到的
                    </label>
                </div>
                <div id="project-buttons" class="front-btn-group collapse" data-toggle="buttons"></div>
                <a class="btn btn-primary" href="report/new">新建报告</a>
            </div>--%>
        <div class="front-toolbar-header clearfix">
            <div class="btn-toolbar" role="toolbar" aria-label="show-report-button" data-toggle="buttons">
                <div id="type-buttons" class="btn-group" style="margin-right: 4px" role="group" aria-label="show-report-button">
                    <button class="btn btn-default front-no-box-shadow" data-group="user"><span class="glyphicon glyphicon-send"></span> 已发送</button>
                    <button class="btn btn-default front-no-box-shadow active" data-group="manager"><span class="glyphicon glyphicon-envelope"></span> 收件箱</button>
                    <button id="draftBox" class="btn btn-default front-no-box-shadow" data-group="draftBox"><span class="glyphicon glyphicon-edit"></span> 草稿箱</button>
                    <button id="trashCan" class="btn btn-default front-no-box-shadow" data-group="trashCan"><span class="glyphicon glyphicon-trash"></span> 回收站</button>
                </div>
                <%--<s:if test="projectList.size() > 0">--%>
                <div id="all-report" class="btn-group" role="group">

                </div>
                <%--<div id="user-buttons" class="btn-group" role="group" aria-label="show-report-button">--%>
                    <%--<button type="button" class="btn btn-default front-no-box-shadow" data-group="draftBox">草稿箱--%>
                    <%--</button>--%>
                    <%--<button type="button" class="btn btn-default front-no-box-shadow" data-group="trashCan">回收站--%>
                    <%--</button>--%>
                <%--</div>--%>
                <div id="manage-buttons" class="btn-group" role="group">
                    <button type="button" class="btn btn-default dropdown-toggle front-no-box-shadow active" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="margin-right: 4px">
                        <span id="reportshow-buttons-dropdown-title"><span class="glyphicon glyphicon-list"></span> 列表 <span class="caret"></span></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a data-group="list" href=""><span class="glyphicon glyphicon-list"></span> 列表 </a></li>
                        <li><a data-group="calendar" href=""><span class="glyphicon glyphicon-calendar"></span> 日历 </a></li>
                    </ul>
                </div>
                <%--<div id="manage-buttons" class="btn-group" role="group">--%>
                    <%--<button id="list-btn" type="button" class="btn btn-default front-no-box-shadow active hidden" data-group="list">--%>
                        <%--<span id="reportshow-buttons-dropdown-title-list"><span class="glyphicon glyphicon-list"></span> 列表</span>--%>
                    <%--</button>--%>
                    <%--<button id="calendar-btn" type="button" class="btn btn-default front-no-box-shadow active" data-group="calendar">--%>
                        <%--<span id="reportshow-buttons-dropdown-title-calendar"><span class="glyphicon glyphicon-calendar"></span> 日历</span></span>--%>
                    <%--</button>--%>
                <%--</div>--%>
                <%--<div id="manage-buttons" class="btn-group" role="group" aria-label="show-report-button">--%>
                    <%--<button type="button" class="btn btn-default front-no-box-shadow" data-group="list"><span--%>
                            <%--class="glyphicon glyphicon-list"></span>列表--%>
                    <%--</button>--%>
                    <%--<button type="button" class="btn btn-default front-no-box-shadow" data-group="calendar"><span--%>
                            <%--class="glyphicon glyphicon-calendar"></span>日历--%>
                    <%--</button>--%>
                <%--</div>--%>
                <div id="new-report" class="btn-group pull-right" role="group"  aria-label="show-report-button">
                    <a  class="btn btn-primary front-no-box-shadow " data-group="new/report"  >
                        新建报告
                    </a>
                </div>
                    <%--<a class="btn btn-primary pull-right" href="report/new">新建报告</a>--%>
                <%--</s:if>--%>
            </div>
        </div>


        <%--
                        <div id="search-area" class="panel panel-default front-panel hidden" style="margin-bottom: 10px">
                            <div class="panel-heading"><strong>报告搜索</strong></div>
                            <div class="panel-body">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-md-1 control-label front-label" for="year">年份</label>
                                        <div class="col-md-2">
                                            <select id="year" class="form-control front-no-radius front-no-box-shadow">
                                                <option value="0">--全部--</option>
                                                <option value="2017">2017</option>
                                                <option value="2016">2016</option>
                                            </select>
                                        </div>
                                        <label class="col-md-1 control-label front-label" for="month">月份</label>
                                        <div class="col-md-2">
                                            <select id="month" class="form-control front-no-radius front-no-box-shadow">
                                                <option value="0">--全部--</option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="6">6</option>
                                                <option value="7">7</option>
                                                <option value="8">8</option>
                                                <option value="9">9</option>
                                                <option value="10">10</option>
                                                <option value="11">11</option>
                                                <option value="12">12</option>
                                            </select>
                                        </div>
                                        <label class="col-md-1 control-label front-label" for="week">周次</label>
                                        <div class="col-md-5">
                                            <select id="week" class="form-control front-no-radius front-no-box-shadow">
                                                <option value="0">--全部--</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-1 control-label front-label" for="projectId">项目</label>
                                        <div class="col-md-11">
                                            <select id="projectId" class="form-control front-no-radius front-no-box-shadow">
                                                <option value="0">--全部--</option>
                                                <s:iterator value="projectList">
                                                    <option value="<s:property value="id"/>"><s:property value="name"/></option>
                                                </s:iterator>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <button type="button" id="reset-button" class="btn btn-default">重置</button>
                                        <button type="button" id="search-button" class="btn btn-primary">查找</button>
                                    </div>
                                </div>
                            </div>
                        </div>
        --%>

        <div id="list-area" style="padding-top: 20px"></div>

    </div>
    <%@include file="../footer.jsp" %>
</div>
<%@include file="/statics/script.html" %>
<script src='statics/calendar/moment.min.js'></script>
<script src='statics/calendar/fullcalendar.min.js'></script>
<script src='statics/calendar/locale-all.js'></script>
<script src="report/my_report.js"></script>
</body>
</html>
