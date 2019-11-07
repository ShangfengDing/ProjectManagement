<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <%@include file="../../statics/head.html"%>
    <title> </title>
    <link href='statics/calendar/fullcalendar.min.css' rel='stylesheet' />
    <link href='statics/calendar/fullcalendar.print.min.css' rel='stylesheet' media='print' />
    <style>
        #calendar {
            max-width: 1140px;
            background: #f5f5f5;
            padding-top: 30px;
            margin: 0 auto;
        }
        #model-selected-people-id {
            list-style-type: none;
            cursor: default;
            border-top: 1px solid #ddd;
        }
        #model-selected-people-id li{
            margin: 2% 3% 3% -12%;
        }
    </style>
</head>
<body class="front-body">
<s:include value="../nav.jsp?act=project">
</s:include>
<div class="front-inner front-inner-media">
    <div class="container">
        <input type="hidden"  id="projectid" value="<s:property value='id'/>">
        <div class="hidden" id="projectType"><s:property value='type'/></div>
        <div >
            <div>
                <ul class="breadcrumb" style="margin-bottom: 0px">
                    <li >
                        <a href="home">项目</a>
                    </li>
                    <li style="position: relative;">
                        <a type="button" class="btn-small btn-default dropdown-toggle" style="background-color: #f5f5f5;border:0;cursor: pointer;" data-toggle="dropdown">
                            <s:property value="pro.name" />&nbsp;<span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu"  role="menu" style="border: 0px;">
                            <s:iterator value="projectList">
                                <li><a style="cursor:pointer" data-group="<s:property value="id"/>" onclick="projectChange(<s:property value="id"/>)"><s:property value="name"/></a></li>
                            </s:iterator>
                        </ul>
                    </li>
                    <li id="title" class="active" style="position: relative;">
                        <a type="button" id="projectDetail" class="btn-small btn-default dropdown-toggle" style="background-color: #f5f5f5;border:0;cursor: pointer;" data-toggle="dropdown">
                            详情&nbsp;<span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu" style="border: 0px;">
                                <li><a data-group="survey" onclick="detailChange(this)" style="cursor:pointer">详情</a></li>
                                <li><a data-group="member" onclick="detailChange(this)" style="cursor:pointer">成员</a></li>
                                <li><a data-group="task" onclick="detailChange(this)" style="cursor:pointer">任务</a></li>
                                <li><a data-group="report" onclick="detailChange(this)" style="cursor:pointer">报告</a></li>
                                <li><a data-group="file" onclick="detailChange(this)" style="cursor:pointer">文件</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>

        <div id="project-survey" style="display:none;padding-left:0px;padding-right: 0px;padding-top: 20px">
            <div id="loading-survey" style="display: none;">
                <div class="front-loading">
                    <div class="front-loading-block"></div>
                    <div class="front-loading-block"></div>
                    <div class="front-loading-block"></div>
                </div>
                <div class="panel-body text-center">正在加载请稍候</div>
            </div>

        </div>
        <div id="project-member" style="display:none;padding-left:0px;padding-right: 0px;padding-top: 20px">
            <div id="loading-member" style="display: none;">
                <div class="front-loading">
                    <div class="front-loading-block"></div>
                    <div class="front-loading-block"></div>
                    <div class="front-loading-block"></div>
                </div>
                <div class="panel-body text-center">正在加载请稍候</div>

            </div>

        </div>
        <div id="project-task" style="display:none;padding-left:0px;padding-right: 0px;padding-top: 20px">
            <div id="loading-task" style="display: none;">
                <div class="front-loading">
                    <div class="front-loading-block"></div>
                    <div class="front-loading-block"></div>
                    <div class="front-loading-block"></div>
                </div>
                <div class="panel-body text-center">正在加载请稍候</div>
            </div>

        </div>
        <div id="project-report" style="display:none;padding-left:0px;padding-right: 0px;padding-top: 20px">
            <div id="loading-report" style="display: none;">
                <div class="front-loading">
                    <div class="front-loading-block"></div>
                    <div class="front-loading-block"></div>
                    <div class="front-loading-block"></div>
                </div>
                <div class="panel-body text-center">正在加载请稍候</div>
            </div>
            <div id="info-report"></div>
        </div>
    </div>
    <footer class="footer-default">
        <div class="text-center">Copyright © All Right Reserved by <a href="http://freeabout.free4inno.com/" target="blank">自邮之翼</a>(2017)</div>
    </footer>
</div>

<%@include file="../../statics/script.html"%>
<script src='statics/calendar/moment.min.js'></script>
<script src='statics/calendar/fullcalendar.min.js'></script>
<script src='statics/calendar/locale-all.js'></script>
<script src="project/InfoFlow.js"></script>
<%--<script>
    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }

    var projectId = GetQueryString("id");

    function detailChange(obj) {
        var group = obj.getAttribute("data-group");
        switch (group) {
            case 'survey' :
                $("#projectDetail").html("概况&nbsp;<span class=\"caret\"></span>");
                $("#loading-survey").css("display","block");
                $.ajax({
                    url:"surveyflow",
                    type:"post",
                    data:{
                        id:projectId
                    },
                    success:function(html){
                        $("#project-survey").html(html);
                        getDynamic(1);
                    }
                });
                $("#loading-survey").css("display","none");
                $("#project-survey").css("display", "block");
                $("#project-member").css("display", "none");
                $("#project-task").css("display", "none");
                $("#project-report").css("display", "none");
                $("#project-dynamics").css("display", "block");
                break;
            case 'member' :
                $("#projectDetail").html("成员&nbsp;<span class=\"caret\"></span>");
                $("#loading-member").css("display","block");
                $.ajax({
                    url:"memberflow",
                    type:"post",
                    data:{
                        id:projectId
                    },
                    success:function(html){
                        $("#project-member").html(html);
                    }
                });
                $("#project-survey").css("display", "none");
                $("#loading-member").css("display","none");
                $("#project-member").css("display", "block");
                $("#project-task").css("display", "none");
                $("#project-report").css("display", "none");
                $("#project-dynamics").css("display", "none");
                break;
            case 'task' :
                $("#projectDetail").html("任务&nbsp;<span class=\"caret\"></span>");
                $("#loading-task").css("display","block");
                $.ajax({
                    url:"task/taskflow",
                    type:"post",
                    data:{
                        projectId:projectId
                    },
                    success:function(html){
                        $("#project-task").html(html);
                    }
                });
                $("#project-survey").css("display", "none");
                $("#project-member").css("display", "none");
                $("#loading-task").css("display","none");
                $("#project-task").css("display", "block");
                $("#project-report").css("display", "none");
                $("#project-dynamics").css("display", "none");
                break;
            case 'report' :
                $("#projectDetail").html("报告&nbsp;<span class=\"caret\"></span>");
                $("#loading-report").css("display","block");
                $.ajax({
                    url:"report/reportflow",
                    type:"post",
                    data:{
                        projectId:projectId
                    },
                    success:function(html){
                        $("#project-report").html(html);
                    }
                });
                $("#project-survey").css("display", "none");
                $("#project-member").css("display", "none");
                $("#project-task").css("display", "none");
                $("#loading-report").css("display","none");
                $("#project-report").css("display", "block");
                $("#project-dynamics").css("display", "none");
                break;
        }
    }
    function getDynamic(page) {
        $.get("getdynamic",  {page:page,id:projectId} ,function(data) {
            var strHTML = "";
            var blank="<h4>本项目还没有动态⊙﹏⊙!</h4>";
            var list = data.logList;
            $.each(list, function(index, info){

                strHTML += '<div class="dynamic"><div><span class="glyphicon glyphicon-pencil"></span><span style="margin-left: 10px;">'
                    + info.createTime.replace(/T/g, ' ') + '</span><span style="margin-left: 10px;">' + info.userName;
                switch (info.type){
                    case "project":
                        switch (info.behavior){
                            case "insert":
                                strHTML += '创建了' + info.pname;
                                break;
                            case "update":
                                strHTML += '修改了' + info.pname + '的项目资料';
                                break;
                            case "delete":
                                strHTML += '删除了' + info.pname;
                                break;
                            default:
                                behav_arry = info.behavior.split(":");
                                switch (behav_arry[0]){
                                    case "add admin":
                                        strHTML += '在' + info.pname + '中新增管理员'+behav_arry[1];
                                        break;
                                    case "add user":
                                        strHTML += '在' + info.pname + '中新增成员'+behav_arry[1];
                                        break;
                                    case "cancel admin":
                                        strHTML += '在' + info.pname + '中取消管理员'+behav_arry[1];
                                        break;
                                    case "delete user":
                                        strHTML += '在' + info.pname + '中删除成员'+behav_arry[1];
                                        break;
                                }
                        }
                        break;
                    case "task":
                        switch (info.behavior){
                            case "insert":
                                strHTML += '在' + info.pname + '中新增一个<a href="task/viewtask?projectId=' + info.pid +'">任务条目</a>';
                                break;
                            case "update":
                                strHTML += '在' + info.pname + '中修改一个<a href="task/viewtask?projectId=' + info.pid +'">任务条目</a>';
                                break;
                            case "delete":
                                strHTML += '在' + info.pname + '中删除一个<a href="task/viewtask?projectId=' + info.pid +'">任务条目</a>';
                                break;
                            case "delete member":
                                strHTML += '在' + info.pname + '中删除一个<a href="task/viewtask?projectId=' + info.pid +'">任务条目</a>所分配的成员';
                                break;
                            default:
                                behav_arry = info.behavior.split(":");
                                if(behav_arry[0] == "add user"){
                                    strHTML += '在' + info.pname + '中为' + behav_arry[1] + '分配一个<a href="task/viewtask?projectId=' + info.pid +'">任务条目</a>';
                                }else if(behav_arry[0] == "change state to 4"){
                                    strHTML += '在' + info.pname + '中将一个<a href="task/viewtask?projectId=' + info.pid +'">任务条目</a>的状态改为 已完成';
                                }else if(behav_arry[0] == "change state to 3"){
                                    strHTML += '在' + info.pname + '中将一个<a href="task/viewtask?projectId=' + info.pid +'">任务条目</a>的状态改为 待验收';
                                }else if(behav_arry[0] == "change state to 2"){
                                    strHTML += '在' + info.pname + '中将一个<a href="task/viewtask?projectId=' + info.pid +'">任务条目</a>的状态改为 进行中';
                                }else if(behav_arry[0] == "change state to 1"){
                                    strHTML += '在' + info.pname + '中将一个<a href="task/viewtask?projectId=' + info.pid +'">任务条目</a>的状态改为 待处理';
                                }
                        }
                        break;
                    case "report":
                        switch (info.behavior){
                            case "insert":
                                strHTML += '向' + info.pname + '中提交一份<a href="report/show?id=' + info.id +'">报告</a>';
                                break;
                            case "update":
                                strHTML += '修改了' + info.pname + '中的一份<a href="report/show?id=' + info.id +'">报告</a>';
                                break;
                        }
                        break;
                    case "reportComment":
                        behav_arry = info.behavior.split(":");
                        if(behav_arry[0] == "insert"){
                            strHTML += '回复了' +behav_arry[1]+'向'+ info.pname + '提交的<a href="report/show?id=' + info.id +'">报告</a>';
                        }
                        break;
                }
                strHTML += '</span></div></div>';
            });
            if(data.endpage > 1){
                $("#navigation").html($.getDivPageHtml(data.page,data.endpage, "getDynamic"));
                $('#navigation').css('display','block');
            }

            if (strHTML==""||strHTML==null)
            {
                $('#dynamic').html(blank);
            }
            else{
                $('#dynamic').html(strHTML);
            }
        });
    }
</script>--%>
</body>
</html>
