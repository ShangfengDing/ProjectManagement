<%--
  Created by IntelliJ IDEA.
  User: dsf74
  Date: 2017/4/25
  Time: 8:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/"/>
    <title>新建项目 - 轻项目</title>
    <%@include file="../../statics/head.html" %>
    <link rel="stylesheet" href="statics/simditor/styles/simditor.css">
    <style>
        .model-people-item {
            padding: 3% 5% 0 5%;
        }

        .model-people-item span {
            cursor: pointer;
        }

        #model-selected-people {
            list-style-type: none;
            cursor: default;
            border-top: 1px solid #ddd;
        }

        #model-selected-people li {
            margin: 2% 3% 3% -12%;
        }

        #model-people-num {
            margin-bottom: 2%;
        }

        .model-manager-item {
            padding: 3% 5% 0 5%;
        }

        .model-manager-item span {
            cursor: pointer;
        }

        #model-selected-manager {
            list-style-type: none;
            cursor: default;
            border-top: 1px solid #ddd;
        }

        #model-selected-manager li {
            margin: 2% 3% 3% -12%;
        }

        #model-manager-num {
            margin-bottom: 2%;
        }
    </style>
</head>
<body class="front-body">
<div class="front-inner front-inner-media">
    <s:include value="../nav.jsp?act=new">
    </s:include>
    <div class="container">
        <form class="form-horizontal">

            <div class="form-group">
                <label class="col-lg-1 col-md-2 control-label">项目名称</label>
                <div class="col-lg-11">
                    <input id="projectName" class="form-control front-no-radius front-no-box-shadow" type="text"
                           maxlength="15" name="title" type="text" size="10" placeholder="请输入项目名称"
                           onkeyup="javascript:setShowLength(this, 10, 'cost_tpl_title_length');">
                    <span class="red" id="cost_tpl_title_length">还可以输入10字</span>
                </div>
            </div>

            <div class="form-group">
                <label class="col-lg-1 col-md-2 control-label">项目简介</label>
                <div class="col-lg-11">
                    <textarea id="projectDescription" class="form-control front-no-radius front-no-box-shadow"
                              rows="5" style="resize:none;" placeholder="请输入项目简介" maxlength="60"
                              name="title" type="text" size="60"
                              onkeyup="javascript:setShowLength(this, 60, 'cost_tpl_content_length');"></textarea>
                    <span class="blue" id="cost_tpl_content_length">还可以输入60字</span>
                </div>
            </div>

            <div class="form-group">
                <label class="col-lg-1 col-md-2 control-label">项目目标</label>
                <div class="col-lg-11">
                    <textarea id="projectTarget" class="form-control front-no-radius front-no-box-shadow"
                              rows="5" style="resize:none;" placeholder="请输入项目目标" maxlength="100" name="title"
                              type="text" size="50" onkeyup="javascript:setShowLength(this, 100, 'cost_tpl_target_length');"></textarea>
                    <span class="yellow" id="cost_tpl_target_length">还可以输入100字</span>
                </div>
            </div>


            <div class="form-group">
                <div class="col-lg-offset-1 col-lg-11 text-right">
                    <a class="btn btn-default" href="home">取消</a>
                    <a onclick="addProject()" class="btn btn-primary">完成</a>
                </div>
            </div>
        </form>
    </div>
    <%@include file="../footer.jsp" %>
</div>
<%@include file="../../statics/script.html" %>
<script src="task/TaskCommon.js"></script>
<script type="text/javascript">
    function addProject() {
        var description = $("#projectDescription").val().trim();
        var name = $("#projectName").val().trim();
        var target = $("#projectTarget").val().trim();
        //判断是否符合提交要求
        if (name.length == 0) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请填写项目名称'});
        }
        else if (description.length == 0) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请填写项目简介'});
        }
        else if (target.length == 0) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请填写项目目标'});
        }
        //判断是否有重名项目
        else {
            $.ajax({
                url: 'project/createProject',
                type: 'post',
                data: {
                    name: name,
                    description: description,
                    target: target
                },
                dataType:"json",
                success: function (data) {
                        $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '新建项目成功！'});
                        location.href = "home";
                },
                error: function () {
                    $.fillTipBox({type: 'danger', icon: 'glyphicon-remove-sign', content: '项目名已存在!'});
                }
            });
        }
    }
    function setShowLength(obj, maxlength, id)
    {
        var rem = maxlength - obj.value.length;
        var wid = id;
        if (rem < 0){
            rem = 0;
        }
        document.getElementById(wid).innerHTML = "还可以输入" + rem + "字";
    }
</script>
</body>
</html>
