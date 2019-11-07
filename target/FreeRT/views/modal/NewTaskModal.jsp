<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <%--<link trl="stylesheet" type="text/css" href="statics/simditor/styles/simditor.scss"/>--%>
    <%--<%@include file="/statics/head.html" %>--%>
    <link rel="stylesheet" href="statics/simditor/styles/simditor.css">
    <%--<link rel="stylesheet" href="statics/css/bootstrap.min.css">--%>
    <link rel="stylesheet" href="statics/css/bootstrap-datetimepicker.min.css">
    <style>
        .simditor{
            border:0;
        }
        .simditor .simditor-wrapper .simditor-placeholder{
            display:block;
        }
    </style>
</head>

<div class="modal-header">
    <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">新建任务 - <s:property value="pro.name"/></h4>
    <input class="hidden" id="addpid" value="<s:property value="projectId"/>">
</div>

<div class="modal-body" style="padding-top:0px">
    <%--<input type="button" value="<%request.getParameter("taskId");%>">--%>
        <form class="form-horizontal">
            <div class="form-group" style="margin-bottom: 20px">
                <div class=" col-md-12" style="padding-left:0;padding-right:0;border-bottom:1px solid #e5e5e5 ">
                    <%--<label class="col-sm-2 control-label" style="text-align: center;">任务描述</label>--%>


                    <%--<input id="adddescription" class="form-control front-no-radius front-no-box-shadow" type="text"--%>
                    <%--maxlength=100 placeholder="任务描述最大限制为100字">--%>
                    <%--<script id="adddescription" name="content" type="text/plain"></script>--%>
                    <div class="pre-scrollable"  style="height:180px">
                        <textarea class="form-control" id="adddescription"></textarea>
                    </div>
                </div>
            </div>
            <div class="form-group" id="hiddenpart1" style="margin-bottom: 20px;display: none;">
                <span class="col-sm-3 control-label front-label hide" style="padding-top:0px">
                    <label class="pull-left" style="font-weight:normal"><s:property value="pro.name"/></label>
                </span>

                <label class="col-sm-1 control-label" style="text-align: left;padding-top:7.5px">状态</label>
                <span class="col-sm-3 control-label front-label" style="text-align: left;padding-top:0px;padding-left: 0px">
					<select id="addstate" class="form-control front-no-radius front-no-box-shadow">
						<option value="1">待处理</option>
						<option value="2">进行中</option>
						<option value="3">待验收</option>
						<option value="4">已完成</option>
					</select>
				</span>
                <label class="col-sm-1 control-label" style="text-align: left;padding-top:7.5px">任务量</label>
                <span class=" col-sm-3 control-label front-label" style="text-align: left;padding-top:0px;padding-left: 0px">
					<select id="addamount" class="form-control front-no-radius front-no-box-shadow">
						<option>5</option>
						<option>4</option>
						<option>3</option>
						<option>2</option>
						<option selected="selected">1</option>
					</select>
				</span>
                <label class="col-sm-1 control-label" style="text-align: left;padding-top:7.5px">重要性</label>
                <div class=" col-sm-3 control-label front-label" style="text-align: left;padding-top:0px;padding-left: 0px">
                    <input type="text" id="addpriority" class="form-control front-no-radius front-no-box-shadow" placeholder="请填写一百以内的整数" value="60">
                </div>

                <label class="col-sm-2 control-label" style="text-align: left;padding-top:27.5px">计划开始时间</label>
                <div class="input-group date col-md-3" id="datetimepicker_begin" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startDate" data-link-format="yyyy-mm-dd" style="text-align: left;padding-top:20px;margin-left: -25px;float: left">
                    <input class="form-control" id="planbegintime" size="16" type="text" value="" readonly="" style="background-color: white">
                    <span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-calendar"></span></span>

                </div>
                <input type="hidden" id="startDate" value="" />

                <label class="col-sm-2 control-label" style="text-align: left;padding-top:27.5px;margin-left: 15px">计划结束时间</label>
                <div class="input-group date col-md-3" id="datetimepicker_end" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endDate" data-link-format="yyyy-mm-dd" style="text-align: left;padding-top:20px;margin-left: -25px">
                    <input class="form-control" id="planendtime" size="16" type="text" value="" readonly="" style="background-color: white">
                    <span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
                <input type="hidden" id="endDate" value="" />
            </div>
        </form>
        <%--<form action="task/uploadTaskPic" method="post" class="form-group fr-file-upload" enctype="multipart/form-data">--%>
            <%--<div class="form-group">--%>
                <%--<label class="col-sm-2 control-label" style="text-align: center" style="padding-left: 0px">添加图片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>--%>
                <%--<div class="col-sm-10" style="padding-left:4px;padding-right: 0px">--%>
                    <%--<div style="height: 40px; background: #fff; border: 1px solid #ddd">--%>
                        <%--<span style="position: absolute; left: 40%; line-height: 40px;">点击此处或拖拽文件到此处</span>--%>
                        <%--<input type="file" name="file" style="opacity: 0; cursor: pointer; width: 100%; height: 64px;"/>--%>
                    <%--</div>--%>
                    <%--<table data-file-info></table>--%>
                    <%--<div id="TaskPic-list" style="display: none;"><label>已添加：</label></div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</form>--%>
        <form class="form-horizontal" id="hiddenpart2" style="display: none;">
        <div class="form-group" >
            <label class="col-sm-2 control-label" style="text-align: left;padding-top: 2px;padding-bottom: 2px;">参与人员</label>
            <div class=" col-sm-10 ">
                    <span>
						<span id="TaskMem" data-clicked="false">
                            <s:iterator value="memberList">
                                <span class="front-badge-xlg badge-name" style="font-size: 13px" id=<s:property value="id"/> data-id=<s:property value="id"/>><s:property value="name"/></span>
                            </s:iterator>
						</span>
                        <input type="hidden" id="SelectedMemId" class="form-control" value="<s:iterator value="memberList"><s:property value="id"/>,</s:iterator>">
                    </span>
            </div>

        </div>
        <div id="UserList" class="panel panel-default front-panel pre-scrollable" style="margin-bottom:5px;display: block; height:100px">
            <div class="panel-body front-news-panel ">
                <%--<table class="table table-striped front-table" style="margin-bottom: 0px">--%>
                    <%--<tbody>--%>
                    <%--<s:iterator value="userListOnProject" id="userNotInTask">--%>
                        <%--<tr><td><div class="media">--%>
                            <%--<div class="media-left">--%>
                                <%--<img class="media-object img-circle  img-avatar-50" src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#userNotInTask.avatar" />" alt="avatar" onerror="this.src='statics/images/user.png'"/>--%>
                            <%--</div>--%>
                            <%--<div class="media-body">--%>
                            <%--<h5 class="media-heading"><s:property value="#userNotInTask.name" />--%>
                                <%--<span class="pull-right">--%>
                                    <%--<a id="add<s:property value='#userNotInTask.id '/>" href="javascript:void(0);" onclick="addUserToTask(<s:property value='#userNotInTask.id '/>,'<s:property value='#userNotInTask.name'/>')">添加</a>--%>
                                    <%--<a id="del<s:property value='#userNotInTask.id '/>" style="display:none" href="javascript:void(0);" onclick="deleteUserInTask(<s:property value='#userNotInTask.id '/>)">删除</a>--%>
                                <%--</span>--%>
                            <%--</h5>   --%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--</td>--%>
                        <%--</tr>--%>
                    <%--</s:iterator>--%>
                    <%--</tbody>--%>
                <%--</table>--%>
                        <s:iterator value="userListOnProject" id="userNotInTask">
                            <div class="media col-sm-4" style="margin-bottom:15px;margin-top:0">
                                <%--<div class="media-left">--%>
                                    <%--<img class="media-object img-circle  img-avatar-50" src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#userNotInTask.avatar" />" alt="avatar" onerror="this.src='statics/images/user.png'"/>--%>
                                <%--</div>--%>
                                <div class="media-body">
                                    <h5 class="media-heading"><s:property value="#userNotInTask.name" />
                                        <span class="pull-right">
                                    <a id="add<s:property value='#userNotInTask.id '/>" href="javascript:void(0);" onclick="addUserToTask(<s:property value='#userNotInTask.id '/>,'<s:property value='#userNotInTask.name'/>')">添加</a>
                                    <a id="del<s:property value='#userNotInTask.id '/>" style="display:none" href="javascript:void(0);" onclick="deleteUserInTask(<s:property value='#userNotInTask.id '/>)">删除</a>
                                </span>
                                    </h5>
                                </div>
                            </div>
                        </s:iterator>

            </div>
        </div>
    </form>

</div>

<div class="modal-footer">
    <div class="form-group">
    <div class="col-lg-10 text-left" style="display:inline; padding-left:0;white-space:nowrap;">
        <button type="button" class="btn btn-primary" id="advancemode" href="javascript:void(0);" onclick="changeadvance()" style="margin-right: 5px">高级模式</button>
        <button type="button" class="btn btn-primary" id="simplemode" href="javascript:void(0);" onclick="changesimple()" style="display:none;margin-right: 5px;margin-left: 0px">简洁模式</button>

        <form action="task/uploadTaskPic" method="post" id="uploadForm" class="form-group fr-file-upload" enctype="multipart/form-data" style="display:inline">

            <button type="button" class="btn btn-primary" id="triggerFile" style="display:inline">添加图片</button>
            <input type="file" name="file" id="fileUpload" style="display:none"/>
           <span style="dispaly:inline"> <table data-file-info style="display:none;"></table></span>
            <span id="TaskPic-list" style="display: none;"><label style="display:inline">已添加：</label></span>
        </form>
    </div>
    <div class="col-lg-2 text-right" style="display:inline; padding-right:0 ">
        <a class="btn btn-default" class="close modal-close" data-dismiss="modal" aria-hidden="true">取消</a>
        <a id="success" onclick="addTask()" class="btn btn-primary">完成</a>
    </div>
    </div>
    </div>
<%--<%@include file="/statics/script.html" %>--%>
<script>
    var taskid = <s:property value="taskId"/>;
</script>
<%--<script type="text/javascript" src="ueditor/ueditor.config.js"></script>--%>
<%--<script type="text/javascript" src="ueditor/ueditor.all.min.js"></script>--%>
<%--&lt;%&ndash;<script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>&ndash;%&gt;--%>

<%--<script src="statics/simditor/scripts/jquery.min.js"></script>--%>
<%--<script src="statics/datepicker/js/jquery-ui.min.js"></script>--%>
<%--<script src="statics/datepicker/js/datepicker-zh-CN.min.js"></script>--%>
<script src="statics/simditor/scripts/module.js"></script>
<script src="statics/simditor/scripts/hotkeys.js"></script>
<script src="statics/simditor/scripts/uploader.js"></script>
<script src="statics/simditor/scripts/simditor.js"></script>
<script src="statics/frFileUpload/fileupload.min.js"></script>
<script type="text/javascript" src="statics/js/jquery.form.js"></script>
<%--<script type="text/javascript" src="statics/jquery/jquery-1.8.3.min.js"></script>--%>
<script type="text/javascript" src="statics/js/bootstrap.min.js"></script>
<script type="text/javascript" src="statics/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="statics/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="task/TaskCommon.js"></script>
<script src="task/TaskEditModal.js"></script>
<script type="text/javascript">
    $("#datetimepicker_begin").datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    }).on('changeDate',function(ev){
        var startTime = $('#startDate').val();

        $("#datetimepicker_begin").datetimepicker('hide');

        $("#datetimepicker_end").datetimepicker('setStartDate',startTime);
    });

    $("#datetimepicker_end").datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    }).on('changeDate',function(ev){
        var endTime = $('#endDate').val();
        $("#datetimepicker_begin").datetimepicker('setEndDate',endTime);
        $("#datetimepicker_end").datetimepicker('hide');
    });
</script>
<script>
    (function() {
        $('#adddescription').val("");
        Simditor.locale = 'zh-CN';
        adddescription = new Simditor({
            textarea: $('#adddescription'),
            placeholder:'请在此输入任务描述...',
            toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', 'link', 'hr', '|', 'indent', 'outdent', 'alignment'],
            toolbarFloat: true,
            toolbarFloatOffset: 50,
            autofocus:true
        });
    })();
//    var adddescription;
//    (function() {
//        Simditor.locale = 'zh-CN';
//        adddescription = new Simditor({
//            textarea: $('#adddescription'),
//            toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', 'link', 'hr', '|', 'indent', 'outdent', 'alignment'],
//            toolbarFloat: true,
//            toolbarFloatOffset: 50,
//            placeholder:'请输入任务描述...',
//            upload:false
//        });
//    })();
    //放到js里就不好使了？
    $('#triggerFile').click(function(){
       var ifexist=$('#TaskPic-list').children('span').length;
       if(ifexist>0){
           $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'只能添加一张图片！'})
       }else {
           $('#fileUpload').trigger("click");
       }
    });
    $('#fileUpload').change(function(){
        $('#uploadForm').ajaxSubmit(function(message){

        });
        $('#fileUpload').val('');
    });
//    UE.Editor.prototype.placeholder = function (justPlainText) {
//        var _editor = this;
//        _editor.addListener("focus", function () {
//            var localHtml = _editor.getPlainTxt();
//            if ($.trim(localHtml) === $.trim(justPlainText)) {
//                _editor.setContent(" ");
//            }
//        });
//        _editor.addListener("blur", function () {
//            var localHtml = _editor.getContent();
//            if (!localHtml) {
//                _editor.setContent(justPlainText);
//            }
//        });
//        _editor.ready(function () {
//            _editor.fireEvent("blur");
//        });
//    };
//    var ue=UE.getEditor('adddescription',{
//        toolbars:[
//            [ 'fullscreen', 'source', '|', 'undo', 'redo', '|',
//                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
//                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
//                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
//                'directionalityltr', 'directionalityrtl', 'indent', '|',
//                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
//                'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
//                'simpleupload', 'insertimage', 'emotion',  'map',  'insertcode', '|',
//                'horizontal', 'date', 'time', 'spechars', '|',
//                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
//                'help']
//        ],
//        elementPathEnabled:false
//
//    });
//    ue.ready(function(){
//        ue.setHeight(150);
//        ue.placeholder("在此填写任务描述...");
//    })
//     $(function () {
//         //动态设置最小值
//         $('#datetimepicker_begin').on('dp.change', function (e) {
//             $('#datetimepicker_end').data('DateTimePicker').minDate(e.date);
//         });
//         //动态设置最大值
//         $('#datetimepicker_end').on('dp.change', function (e) {
//             $('#datetimepicker_begin').data('DateTimePicker').maxDate(e.date);
//         });
//     });



</script>
