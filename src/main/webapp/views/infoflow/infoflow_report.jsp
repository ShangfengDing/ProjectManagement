<%--
  Created by IntelliJ IDEA.
  User: 41529
  Date: 2017/11/12
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
        <input type="hidden" id="projectId" value="<s:property value="projectId"/>"/>


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
<script src="report/project_report.js"></script>



