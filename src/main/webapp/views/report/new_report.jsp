<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html style="overflow-y:scroll">
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/"/>
    <%@include file="/statics/head.html" %>
    <link rel="stylesheet" href="statics/datepicker/css/jquery-ui.min.css">
    <link rel="stylesheet" href="statics/datepicker/css/jquery-ui.structure.min.css">
    <link rel="stylesheet" href="statics/datepicker/css/jquery-ui.theme.min.css">
    <link rel="stylesheet" href="statics/simditor/styles/simditor.css">
    <style>
        .model-project-item {
            padding: 3% 5% 0 5%;
        }
        .model-project-item span {
            cursor: pointer;
        }
        #model-selected-project {
            list-style-type: none;
            cursor: default;
            border-top: 1px solid #ddd;
        }
        #model-selected-project li {
            margin: 2% 3% 3% -12%;
        }
        #model-project-num {
            margin-bottom: 2%;
        }
    </style>
    <s:if test="report == null">
        <title>新建报告 - 轻项目</title>
    </s:if>
    <s:else>
        <title>编辑 - <s:property value="report.name"/> - Free项目</title>
    </s:else>
</head>

<body class="front-body">
    <s:if test="report == null">
        <s:include value="../nav.jsp?act=new"/>
    </s:if>
    <s:else>
        <s:include value="../nav.jsp"/>
    </s:else>
    <div class="front-inner front-inner-media">
        <div class="container">
            <form class="form-horizontal">
            <s:if test="report == null">
                <div class="form-group">
                    <div class="front-toolbar other" style="padding-bottom: 0px">
                        <div class="front-toolbar-header clearfix">
                            <button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#report-type-buttons">
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                        </div><div id="report-type-buttons" class="front-btn-group collapse" data-toggle="buttons">
                            <label class="btn btn-default front-no-box-shadow" data-group="day">
                                <input type="radio" name="options" autocomplete="off">日报
                            </label>
                            <label class="btn btn-default front-no-box-shadow active" data-group="week">
                                <input type="radio" name="options" autocomplete="off">周报
                            </label>
                            <label class="btn btn-default front-no-box-shadow" data-group="month">
                                <input type="radio" name="options" autocomplete="off">月报
                            </label>
                            <label class="btn btn-default front-no-box-shadow" data-group="year">
                                <input type="radio" name="options" autocomplete="off">年报
                            </label>
                            <label class="btn btn-default front-no-box-shadow" data-group="other">
                                <input type="radio" name="options" autocomplete="off">其他
                            </label>
                        </div>
                    </div>
                </div>

                <div class="form-group" id="select-day-div" style="display: none">
                    <a class="col-lg-1 btn btn-default">
                        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;&nbsp;选择日期
                    </a>
                    <div class="col-lg-3">
                        <input type="text" id="datepicker" class="form-control" placeholder="">
                    </div>
                </div>

                <div class="form-group" id="select-week-div">
                    <a class="col-lg-1 btn btn-default">
                        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;&nbsp;选择周次
                    </a>
                    <div class="col-lg-3">
                        <select class="form-control front-no-radius front-no-box-shadow" id="selectTime">
                            <s:iterator value="weekList">
                                <option value="<s:property/>"><s:property/></option>
                            </s:iterator>
                        </select>
                    </div>
                    <a class="btn btn-primary pull-right" href="javascript:void(0)" onclick="getFinishTask()">获取本周完成任务</a>
                </div>

                <div class="form-group" id="select-month-div" style="display: none">
                    <a class="col-lg-1 btn btn-default">
                        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;&nbsp;选择月份
                    </a>
                    <div class="col-lg-3">
                        <select class="form-control front-no-radius front-no-box-shadow">
                            <s:iterator value="monthList">
                                <option value="<s:property/>"><s:property/></option>
                            </s:iterator>
                        </select>
                    </div>
                </div>

                <div class="form-group" id="select-year-div" style="display: none">
                    <a class="col-lg-1 btn btn-default">
                        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;&nbsp;选择年份
                    </a>
                    <div class="col-lg-3">
                        <select class="form-control front-no-radius front-no-box-shadow">
                            <s:iterator value="yearList">
                                <option value="<s:property/>"><s:property/></option>
                            </s:iterator>
                        </select>
                    </div>
                </div>

                <input type="hidden" id="user-name" value="<s:property value='#session.userName'/>">
            </s:if>

                <div class="form-group">
                    <input type="text" id="name" name="name" placeholder="请输入报告标题" class="col-lg-12 form-control front-no-box-shadow" disabled>
                </div>

                <div class="form-group">
                    <textarea id="simditor" class="col-lg-12 form-control"></textarea>
                </div>
            </form>

            <table id='finishTask'></table>

            <form action="report/uploadAttachment" method="post" class="form-horizontal fr-file-upload" enctype="multipart/form-data">
                <div class="form-group">
                    <a class="col-lg-1 btn btn-default">
                        <span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;&nbsp;添加附件
                    </a>
                    <div class="col-lg-11" style="padding-right: 0">
                        <div style="height: 64px; background: #fff; border: 1px solid #ddd">
                            <span style="position: absolute; left: 40%; line-height: 64px;">点击此处或拖拽文件到此处</span>
                            <input type="file" name="file" style="opacity: 0; cursor: pointer; width: 100%; height: 64px;"/>
                        </div>
                        <table data-file-info></table>
                        <s:if test="attachmentList && attachmentList.size() > 0">
                            <div id="attachment-list"><label>已添加：</label>
                                <s:iterator value="attachmentList">
                                <span title="<s:property value="uuid"/>"><br><s:property value="name"/>&nbsp;<a href="javascript:void(0)" onclick="deleteAttachment(this,'<s:property value="uuid"/>')">[删除]</a></span>
                                </s:iterator>
                            </div>
                        </s:if>
                        <s:else>
                            <div id="attachment-list" style="display: none"><label>已添加：</label></div>
                        </s:else>
                    </div>
                </div>
            </form>

            <form class="form-horizontal">
                <div class="form-group">
                    <a class="col-lg-1 btn btn-default" data-toggle="front-modal" data-size="modal-lg" data-href="views/modal/select_project_model.jsp">
                        <span class="glyphicon glyphicon-th-large" aria-hidden="true"></span>&nbsp;&nbsp;选择项目
                    </a>
                    <div class="col-lg-11">
                        <p id="selected-project-name" class="form-control-static">
                            <s:if test="report!=null">
                                <label>已选择：</label>
                                <s:iterator value="report.reportPermissions">
                                    <s:property value="project.name"/>&nbsp;
                                </s:iterator>
                            </s:if>
                        </p>
                    </div>
                    <s:if test="report!=null">
                        <input type="hidden" id="selected-project-id" class="form-control" value="<s:iterator value="report.reportPermissions"><s:property value="project.id"/>,</s:iterator>">
                    </s:if>
                    <s:else>
                        <input type="hidden" id="selected-project-id" class="form-control" value="">
                    </s:else>
                </div>
            </form>

            <form class="form-horizontal">
                <div class="form-group ">
                    <div class="col-lg-offset-8 col-lg-4 text-right" style="padding-right: 0px;">
                        <a class="btn btn-default" onclick="cancel()">取消</a> |
                        <s:if test="report != null">
                            <s:if test="report.state == 0">
                                <button type="button" onclick="updateReport(<s:property value="report.id"/>)" class="btn btn-primary">提交编辑</button>
                            </s:if>
                            <s:elseif test="report.state == 2">
                                <button type="button" onclick="updateReport(<s:property value="report.id"/>)" class="btn btn-default">保存编辑</button>
                                <button type="button" onclick="submitDraftReport(<s:property value="report.id"/>)" class="btn btn-primary">提交报告</button>
                            </s:elseif>
                        </s:if>
                        <s:else>
                            <button type="button" onclick="saveReport()" class="btn btn-default">保存草稿</button>
                            <button type="button" onclick="submitReport()" class="btn btn-primary">提交报告</button>
                        </s:else>
                    </div>
                </div>
            </form>

        </div>
        <%@include file="../footer.jsp" %>
    </div>
    <%@include file="/statics/script.html"%>
    <script src="statics/datepicker/js/jquery-ui.min.js"></script>
    <script src="statics/datepicker/js/datepicker-zh-CN.min.js"></script>
    <script src="statics/simditor/scripts/module.js"></script>
    <script src="statics/simditor/scripts/hotkeys.js"></script>
    <script src="statics/simditor/scripts/uploader.js"></script>
    <script src="statics/simditor/scripts/simditor.js"></script>
    <script src="statics/frFileUpload/fileupload.min.js"></script>
    <script src="report/new_report.js"></script>
    <s:if test="report != null">
    <script>
        $('#name').val("<s:property value="report.name"/>");
        $('#name').attr("disabled", false);
        simditor.setValue(HTMLDecode("<s:property value="report.description"/>"));
        function HTMLDecode(text) {
            var temp = document.createElement("div");
            temp.innerHTML = text;
            var output = temp.innerText || temp.textContent;
            temp = null;
            return output;
        }
    </script>
    </s:if>
<script>

    function getFinishTask() {
        var selectTime =$('#selectTime').val();
        var startTime = selectTime.substring(0,10).replace(".","-").replace(".","-");
        var endTime = selectTime.substring(11,21).replace(".","-").replace(".","-");
        $.post("task/ListFinishTaskByTime",{
            startTime : startTime,
            endTime : endTime,
        },function (json) {
            var judge = 0;//用于判断这一时间段内任务有无自己完成的，0没有，1有
            var userId = json.userId;
            if(json.taskList != null){
                $(json.taskList).each(function(i, value) {
                    $(value.taskMembers).each(function (j,taskMem) {
                        if(userId == taskMem.user.id){
                            judge = 1;
                        }
                    })
                })
            }
            if(json.taskList == ""||judge == 0){
                $('#simditor').prev().children('p')[0].innerHTML = "<br>";
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'本周未完成任务！'})
            } else {
                $('#simditor').prev().children('p')[0].innerHTML = "";
                var finishTask = "<table><colgroup><col width='6%'><col width='94%'></colgroup><thead><tr><th><br></th><th>任务内容<br></th></tr></thead>"
                var taskNum = 1;
                judge = 0;
                $(json.taskList).each(function(i, value) {
                    $(value.taskMembers).each(function (j,taskMem) {
                        if(userId == taskMem.user.id){
                            judge = 1;
                        }
                    })
                    if(judge==1){
                        finishTask = finishTask + '<TR><TD>'+ taskNum +'</TD><TD>'+value.description+'</TD><TR>';
                        taskNum++;
                    }
                    judge = 0;
                });
                finishTask = finishTask + '</table>';
                $('#simditor').prev().children('p')[0].innerHTML = finishTask;
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'获取任务成功！'})
            }
        })

    }

</script>
</body>
</html>
