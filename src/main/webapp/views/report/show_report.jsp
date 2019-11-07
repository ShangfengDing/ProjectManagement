<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <%@include file="/statics/head.html"%>
    <link rel="stylesheet" href="statics/simditor/styles/simditor.css">
    <style>
        #report-description-panel table {
            table-layout: fixed;
            word-break: break-all;
        }
        #report-description-panel table tbody tr td:first-child {
            border-top: 1px solid #ddd;
        }
        #report-description-panel table tbody tr:last-child td {
            border-bottom: 1px solid #ddd;
        }
    </style>
    <title><s:property value="formatReportName"/>

    </title>
</head>

<body class="front-body">
    <s:include value="../nav.jsp"/>
    <s:if test="auth == true">
    <div class="front-inner front-inner-media">
        <div class="container">

            <input type="hidden" id="reportId" value="<s:property value="report.id"/>"/>
            <input type="hidden" id="replyToUserId" value=""/>

            <div class="panel panel-default front-panel">
                <div class="panel-body" style="padding-top: 20px">
                    <s:iterator value="report.reportPermissions">
                        <a style="text-decoration: none" href="task/viewtask?projectId=<s:property value="project.id"/>">
                            <span class="label label-info" style="font-size: 14px"><s:property value="project.name"/></span>
                        </a>

                    </s:iterator>
                    <h2><s:property value="report.name"/></h2>
                </div>
            </div>

            <div>
                <span>&nbsp;<s:date name="report.time" format="yyyy-MM-dd HH:mm:ss"/></span>
            </div>
            <br>

            <div class="panel panel-default front-panel">
                <div id="report-description-panel" class="panel-body">
                    <s:property value="report.description" escape="false"/>
                    <s:if test="attachmentList && attachmentList.size() > 0">
                        <s:include value="show_report_attachment.jsp"/>
                    </s:if>
                </div>
            </div>

            <s:if test="report.state == 0">
            <div style="padding-bottom: 15px">
                <a id="previous-button" href="javascript:void(0)" onclick="previous()">上一篇</a>
                <a id="next-button" href="javascript:void(0)" onclick="next()" style="float: right">下一篇</a>
            </div>

            <div class="front-toolbar other" style="padding-bottom: 20px">
                <div class="front-toolbar-header clearfix">
                    <button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#report-buttons">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div id="report-buttons" class="front-btn-group collapse">
                    <a class="btn btn-default front-no-box-shadow active" href="javascript:normalComment();" id="comment-normal-button">
                        <span class="glyphicon glyphicon-align-justify"></span> 普通评论
                    </a>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:highComment();" id="comment-high-button">
                        <span class="glyphicon glyphicon-picture"></span> 高级评论
                    </a>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:showOperationComment();" id="comment-operation-button">
                        <span class="glyphicon glyphicon-eye-open"></span> 显示操作记录
                    </a>
                </div>
                <s:if test="report.user.userid == #session.uid">
                    <a class="btn btn-primary" href="report/edit?id=<s:property value="report.id"/>"><span class="glyphicon glyphicon-edit"></span> 编辑</a>
                    <a class="btn btn-default" href="javascript:void(0);" onclick="trashReport(<s:property value="report.id"/>)" style="margin-right: 5px"><span class="glyphicon glyphicon-trash"></span> 删除</a>
                </s:if>
            </div>

            <form class="form-horizontal">
                <div class="form-group freeshare-bottom-20" style="position: relative">
                    <div class="col-md-12" style="margin-bottom: 10px">
                        <textarea class="form-control" id="comment-normal-textarea" rows="1" placeholder="按Ctrl+Enter发表评论"></textarea>
                        <textarea class="form-control hidden" id="comment-high-textarea"></textarea>
                    </div>

                    <div class="clearfix col-md-12" style="padding-top: 10px">
                        <%--<div id="commentTip" style="float:left;line-height: 34px"></div>--%>
                        <a id="comment-submit-button" onclick="preSubmitComment()" class="btn btn-primary" style="float: right;"><span class="glyphicon glyphicon-pencil"></span> 评论</a>
                    </div>
                </div>
            </form>
            </s:if>

            <s:elseif test="report.state == 1 || report.state == 3">
            <div class="front-toolbar other" style="padding-bottom: 53px">
                <s:if test="report.user.userid == #session.uid">
                    <a class="btn btn-primary" href="javascript:void(0);" onclick="revertReport(<s:property value="report.id"/>)"><span class="glyphicon glyphicon-share-alt"></span> 撤销删除</a>
                    <a class="btn btn-default" href="javascript:void(0);" onclick="deleteReport(<s:property value="report.id"/>)" style="margin-right: 5px"><span class="glyphicon glyphicon-remove"></span> 彻底删除</a>
                </s:if>
            </div>
            </s:elseif>

            <s:elseif test="report.state == 2">
                <div class="front-toolbar other" style="padding-bottom: 53px">
                    <s:if test="report.user.userid == #session.uid">
                        <a class="btn btn-primary" href="javascript:void(0);" onclick="submitDraftReport(<s:property value="report.id"/>)"><span class="glyphicon glyphicon-check"></span> 提交</a>
                        <a class="btn btn-primary" href="report/edit?id=<s:property value="report.id"/>" style="margin-right: 5px"><span class="glyphicon glyphicon-edit"></span> 编辑</a>
                        <a class="btn btn-default" href="javascript:void(0);" onclick="trashReport(<s:property value="report.id"/>)" style="margin-right: 5px"><span class="glyphicon glyphicon-remove"></span> 删除</a>
                    </s:if>
                </div>
            </s:elseif>

            <div id="show-comment-area"></div>

        </div>
        <%@include file="../footer.jsp"%>
    </div>
    </s:if>
    <s:else>
        <s:include value="/error/member.jsp"/>
    </s:else>
    <%@include file="/statics/script.html"%>
    <script src="statics/simditor/scripts/module.js"></script>
    <script src="statics/simditor/scripts/hotkeys.js"></script>
    <script src="statics/simditor/scripts/uploader.js"></script>
    <script src="statics/simditor/scripts/simditor.js"></script>
    <script src="report/show_report.js"></script>
</body>
</html>
