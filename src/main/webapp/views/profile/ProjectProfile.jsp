<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by ycw
  User: yangchenwei
  Date: 2018/10/25
  Time: 10:22 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <%@include file="/statics/head.html"%>
    <title><s:property value="project.name"/>项目文件-轻项目</title>
</head>
<body class="front-body" onload="">
<s:include value="../nav.jsp?project"/>
<div class="front-inner front-inner-media">
        <div class="container">
        <input type="hidden" id="projectId" value="<s:property value="id"/>"/>
        <div class="hidden" id = "projectType">profile</div>
        <div>
            <div>
                <div style="margin-bottom: 0px">
                    <ol class="breadcrumb" style="margin-bottom: 0px">
                        <li >
                            <a href="home">项目&nbsp;</a>
                        </li>
                        <%--<li id="task" class="active" style="position: relative;">
                            项目任务&nbsp;
                        </li>--%>
                        <li style="position: relative;">
                            <a type="button" class="btn-small btn-default dropdown-toggle" style="background-color: #f5f5f5;border:0;cursor: pointer;" data-toggle="dropdown">
                                <s:property value="pro.name" />&nbsp;<span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu"  role="menu" style="border: 0px;">
                                <s:iterator value="projectList">
                                    <li><a data-group="<s:property value="name"/>" href="file/viewfile?Id=<s:property value="id" />"><s:property value="name"/></a></li>
                                </s:iterator>
                            </ul>
                        </li>
                        <li id="title" class="active" style="position: relative;">
                            <a type="button" id="projectDetail" class="btn-small btn-default dropdown-toggle" style="background-color: #f5f5f5;border:0;cursor: pointer;" data-toggle="dropdown">
                                文件&nbsp;<span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu" role="menu" style="border: 0px;">
                                <li><a data-group="survey" onclick="detailChange(this)" style="cursor:pointer">详情</a></li>
                                <li><a data-group="member" onclick="detailChange(this)" style="cursor:pointer">成员</a></li>
                                <li><a data-group="task" onclick="detailChange(this)" style="cursor:pointer">任务</a></li>
                                <li><a data-group="report" onclick="detailChange(this)" style="cursor:pointer">报告</a></li>
                                <li><a data-group="file" onclick="detailChange(this)" style="cursor:pointer">文件</a></li>
                            </ul>
                        </li>
                        <li  id="bread-4" class="active" style="position: relative; display: none">
                            <a id="file-op-2" type="button" class="btn-small btn-default dropdown-toggle" style="background-color: #f5f5f5;border:0;cursor: pointer;" data-toggle="dropdown">
                            文件列表&nbsp;<span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu" role="menu" style="border: 0px;">
                                <li><a data-group="upload" onclick="fileSelect(this)" style="cursor:pointer">上传文件</a></li>
                                <li><a data-group="link" onclick="fileSelect(this)" style="cursor:pointer">分享链接</a></li>
                            </ul>
                        </li>
                    </ol>
                </div>
        </div>
        </div>
        <div class="col-md-12" style="padding-left:0px;padding-right: 0px;">
        <div class="front-toolbar other" >
            <div style="margin-bottom: 10px" >
            </div>



            <div id="file-op-0" class="btn-group pull-right" style="margin-top: auto; display: inline">
                <button id="file-op" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    新建文件 <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a data-group="upload" onclick="fileSelect(this)" style="cursor:pointer">上传文件</a></li>
                    <li><a data-group="link" onclick="fileSelect(this)" style="cursor:pointer">分享文档链接</a></li>
                    <%--<li role="separator" class="divider"></li>--%>
                    <%--<li><a data-group="list" onclick="fileSelect(this)" style="cursor:pointer">返回文件列表</a></li>--%>
                </ul>
            </div>

            <div id="file-search-box" class="pull-left searchBox">
                <input id="file-search"class="form-control" type="text" placeholder="搜索文件" oninput="searchFile()">
            </div>
            <%--<div id="file-select" class="front-btn-group collapse" data-toggle="buttons">--%>
                <%--<label class="btn btn-default front-no-box-shadow active" data-group="list">--%>
                    <%--<input type="radio" name="options" autocomplete="off">文件列表--%>
                <%--</label>--%>
                <%--<label class="btn btn-default front-no-box-shadow"  data-group="upload">--%>
                    <%--<input type="radio" name="options" autocomplete="off">文件上传--%>
                <%--</label>--%>
            <%--</div>--%>
        </div>
        <div id = "file-upload" class="panel panel-default" style="display: none; ">
            <div  class="panel-body" >
                <%--<label for="file-name" style="display:inline; font-size:18px">名称&nbsp;</label>--%>
                <%--<input id="file-name" style="display:inline;;width:300px;" class="form-control" type="text" placeholder="请输入文件名称" autocomplete="off">--%>
                <%--<br>--%>
                <%--<br>--%>
                <label for="file-description" style="display:inline; margin-top:30px">文件说明&nbsp;</label>
                <%--<textarea  id="file-description" style="display:inline;width:800px;" class="form-control" type="text" placeholder="输入文件说明（不超过50个字）" autocomplete="off"></textarea>--%>
                <input id="file-description" style="display:inline;width:800px;" class="form-control" type="text" placeholder="请输入文件说明（不超过50个字）" autocomplete="off" maxlength="50">
                <br>
                <%--<br>--%>
                <%--<label for="dropdown" style="display:inline;font-size:18px">来源&nbsp;</label>--%>
                <%--<select id="dropdown" style="display:inline;;width:300px;" class="form-control">--%>
                <%--<option value="1">FreeDisk</option>--%>
                <%--</select>--%>
                <%--<br>--%>
                <br>
                <label for="file-url" style="display:inline;" >文件链接&nbsp;</label>
                <input id="file-url" style="display:inline;width:800px;" class="form-control" type="text" placeholder="请输入文件地址（uuid)" autocomplete="off">
                <br><br>
                <a class="btn btn-primary pull-left" id="file_upload_button" onclick="preuploadFile()">新增文件</a>
            </div>
        </div>
        <div id = "file-edit" class="panel panel-default" style="display: none;">
            <div  class="panel-body" >
                <label for="file-description-edit" style="display:inline; margin-top:30px">文件说明&nbsp;</label>
                <%--<textarea  id="file-description" style="display:inline;width:800px;" class="form-control" type="text" placeholder="输入文件说明（不超过50个字）" autocomplete="off"></textarea>--%>
                <input id="file-description-edit" style="display:inline;width:800px;" class="form-control" type="text" value=" " autocomplete="off" maxlength="50">
                <br>
                <br>
                <label for="file-url-edit" style="display:inline;" >文件链接&nbsp;</label>
                <input id="file-url-edit" style="display:inline;width:800px;" class="form-control" type="text" value=" " autocomplete="off">
                <br><br>
                <a class="btn btn-primary pull-left" id="file_upload_button-edit">修改文件</a>
            </div>
        </div>
        <div id="show-file-area" style="display: block">
            </div>
        </div>

        </div>
    <%@include file="../footer.jsp"%>
</div>
<%@include file="/statics/script.html"%>
<script src="projectfile/upload_file.js"></script>
<script>
    var projectId = $("#projectId").val();
    function detailChange(obj) {
        var group = obj.getAttribute("data-group");
        switch (group) {
            case 'survey' :
                window.location.href='info?type=survey&&id='+projectId;
                break;
            case 'member' :
                window.location.href="info?type=user&&id="+projectId;
                break;
            case 'task' :
                window.location.href='task/viewtask?projectId='+projectId;
                break;
            case 'report' :
                window.location.href="info?type=report&&id="+projectId;
                break;
            case 'file' :
                window.location.href='file/viewfile?Id='+projectId;
                break;
        }
    }


</script>
</body>
</html>