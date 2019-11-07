<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
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
<%--用于项目管理员修改任务内容--%>
<div class="modal-header">
    <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">编辑任务 - <s:iterator value="aliveProList" var="p">
        <s:if test="#p.id==projectId"><s:property value="name"/></s:if>
    </s:iterator></h4>
    <input type="hidden" id="editpid" value=<s:property value="projectId"/>>
</div>

<div class="modal-body" style="padding-top:0px">
    <%--<input type="button" value="<%request.getParameter("taskId");%>">--%>
    <form class="form-horizontal">
        <div class="form-group" style="margin-bottom: 20px">
            <div class=" col-md-12" style="padding-left:0;padding-right:0;border-bottom:1px solid #e5e5e5 ">
                <div class="pre-scrollable"  style="height:180px">
            <%--<label class="col-sm-2 control-label" style="text-align: center;">任务描述</label>--%>

                <input type="hidden" id="hiddenarea" value="<s:property value='description'/>">
                <%--<script id="editdescription" name="content" type="text/plain"></script>--%>
                <textarea class="col-lg-12 form-control" id="editdescription"></textarea>
                </div>
            </div>
        </div>
        <div class="form-group" id="hiddenpart1" style="margin-bottom: 20px;display: none;">
                <span class="col-sm-3 control-label front-label hide" style="padding-top:0px">
                    <label class="pull-left" style="font-weight:normal"><s:iterator value="aliveProList" var="p">
                    <s:if test="#p.id==projectId"><s:property value="name"/></s:if>
                    </s:iterator></label>
                </span>
            <%--<span class="col-md-2"></span>--%>
            <label class="col-sm-1 control-label" style="text-align: left;padding-top:7.5px">状态</label>
            <span class="col-sm-3 control-label front-label" style="text-align: left;padding-top:0px;padding-left: 0px">
               <select id="editstate"  class="form-control front-no-radius front-no-box-shadow" autocomplete="off">
                        <s:if test="state == 1">
                            <option value="1" selected="selected">待处理</option><option value="2">进行中</option>
                            <option value="3">待验收</option><option value="4">已完成</option>
                        </s:if>
                        <s:elseif test="state == 2">
                            <option  value="1">待处理</option><option value="2" selected="selected">进行中</option>
                            <option value="3">待验收</option><option value="4">已完成</option>
                        </s:elseif>
                        <s:elseif test="state == 3">
                            <option  value="1">待处理</option><option  value="2">进行中</option>
                            <option value="3" selected="selected">待验收</option><option value="4">已完成</option>
                        </s:elseif>
                        <s:else>
                            <option  value="1">待处理</option><option  value="2">进行中</option>
                            <option  value="3">待验收</option><option value="4" selected="selected">已完成</option>
                        </s:else>
                    </select>
            </span>

            <label class="col-sm-1 control-label" style="text-align: left;padding-top:7.5px">任务量</label>
            <span class=" col-sm-3 control-label front-label" style="text-align: left;padding-top:0px;padding-left: 0px">
               <select id="editamount" class="form-control front-no-radius front-no-box-shadow">
                        <s:if test="amount == 1">
                            <option>5</option><option>4</option><option>3</option>
                            <option>2</option><option selected="selected">1</option>
                        </s:if>
                        <s:elseif test="amount == 2">
                            <option>5</option><option>4</option><option>3</option>
                            <option selected="selected">2</option><option>1</option>
                        </s:elseif>
                         <s:elseif test="amount == 3">
                             <option>5</option><option>4</option><option selected="selected">3</option>
                             <option>2</option><option>1</option>
                         </s:elseif>
                         <s:elseif test="amount == 4">
                             <option>5</option><option selected="selected">4</option><option>3</option>
                             <option>2</option><option>1</option>
                         </s:elseif>
                        <s:else>
                            <option selected="selected">5</option><option>4</option><option>3</option>
                            <option>2</option><option>1</option>
                        </s:else>
               </select>
            </span>
            <%--<span class="col-md-2"></span>--%>
            <label class="col-sm-1 control-label" style="text-align: left;padding-top:7.5px">重要性</label>
            <div class=" col-sm-3 control-label front-label" style="text-align: left;padding-top:0px;padding-left: 0px">
                <input type="text" id="editpriority" class="form-control front-no-radius front-no-box-shadow" placeholder="请填写一百以内的整数" value=<s:property value="priority"/>>
            </div>

            <label class="col-sm-2 control-label" style="text-align: left;padding-top:27.5px">计划开始时间</label>
            <div class="input-group date col-md-3" id="datetimepicker_begin" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startDate" data-link-format="yyyy-mm-dd" style="text-align: left;padding-top:20px;margin-left: -25px;float: left">
                <input class="form-control" id="planbegintime" size="16" type="text" value="<s:date name="plan_bengintime" format="yyyy-MM-dd"/>" readonly="" style="background-color: white">
                <span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-calendar"></span></span>

            </div>
            <input type="hidden" id="startDate" value="" />

            <label class="col-sm-2 control-label" style="text-align: left;padding-top:27.5px;margin-left: 15px">计划结束时间</label>
            <div class="input-group date col-md-3" id="datetimepicker_end" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endDate" data-link-format="yyyy-mm-dd" style="text-align: left;padding-top:20px;margin-left: -25px">
                <input class="form-control" id="planendtime" size="16" type="text" value="<s:date name="plan_endtime" format="yyyy-MM-dd"/>" readonly="" style="background-color: white">
                <span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon" style="background-color: white"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
            <input type="hidden" id="endDate" value="" />
            <%--<label class="col-sm-1 control-label" style="text-align: left;">重要性</label>--%>
            <%--<div class=" col-sm-3 control-label front-label">--%>
                <%--<input type="text" id="editpriority" class="form-control front-no-radius front-no-box-shadow" placeholder="请填写一百以内的整数" value=<s:property value="priority"/>>--%>
            <%--</div>--%>
        </div>
    </form>
    <%--<form action="task/uploadTaskPic" method="post" class="form-group fr-file-upload" enctype="multipart/form-data">--%>
    <%--<div class="form-group">--%>
    <%--<label class="col-sm-2 control-label" style="text-align: center" style="padding-left: 0px">修改图片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>--%>
    <%--<div class="col-sm-10" style="padding-left:4px;padding-right: 0px">--%>
    <%--<div style="height: 40px; background: #fff; border: 1px solid #ddd">--%>
    <%--<span style="position: absolute; left: 40%; line-height: 40px;">点击此处或拖拽文件到此处</span>--%>
    <%--<input type="file" name="file" style="opacity: 0; cursor: pointer; width: 100%; height: 64px;"/>--%>
    <%--</div>--%>
    <%--<table data-file-info></table>--%>
    <%--<s:if test="taskPicList.size()>0">--%>
    <%--<div id="TaskPic-list"><label>已添加：</label><br>--%>
    <%--<s:iterator value="taskPicList">--%>
    <%--<s:property value="name"></s:property>--%>
    <%--<span title="<s:property value='uuid'/>"><a href="javascript:void(0);" onclick="deleteTaskPic(this,'<s:property value="uuid"/>')">[删除]</a></span><br>--%>
    <%--</s:iterator>--%>
    <%--</div>--%>
    <%--</s:if>--%>
    <%--<s:else>--%>
    <%--<div id="TaskPic-list" style="display:none"><label>已添加：</label>--%>
    <%--</div>--%>
    <%--</s:else>--%>

    <%--</div>--%>
    <%--</div>--%>
    <%--</form>--%>
        <form class="form-horizontal" id="hiddenpart2" style="display: none;">
        <div class="form-group">
            <label class="col-sm-2 control-label" style="text-align: left;padding-top: 2px;padding-bottom: 2px;">参与人员</label>
            <div class=" col-sm-4 ">
                    <span>
                  <span id="TaskMem" data-clicked="false">
                            <s:iterator value="memberList">
                                <span class="front-badge-xlg badge-name" style="font-size: 13px" id=<s:property value="id"/> data-id=<s:property value="id"/>><s:property value="name"/></span>
                            </s:iterator>
                  </span>
                        <input type="hidden" id="SelectedMemId" class="form-control" value="<s:iterator value="memberList"><s:property value="id"/>,</s:iterator>">
                    </span>
            </div>

            <label class="col-sm-2  control-label" style="text-align: center;padding-top: 0px">创建时间</label>
            <span class=" col-sm-4  control-label front-label" style="text-align: left;padding-top: 0px;font-weight: 500">
                    <s:date name="time" format="yyyy-MM-dd HH:mm:ss"  />
            </span>

        </div>
            <div id="UserList" class="panel panel-default front-panel pre-scrollable" style="margin-bottom:5px;display: block; height:100px">
            <div class="panel-body front-no-padding ">
                <%--<table class="table table-striped front-table" style="margin-bottom: 0px">--%>
                <%--<tbody>--%>
                <s:iterator value="memberList" id="userInTask">
                    <%--<div class="media col-sm-4" style="margin-bottom:5px;margin-top:10px"><div class="media-left">--%>
                        <%--<img class="media-object img-circle  img-avatar-50" src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#userInTask.avatar" />" alt="avatar" onerror="this.src='statics/images/user.png'"/>--%>
                    <%--</div>--%>
                        <div class="media-body">
                        <h5 class="media-heading"><s:property value="#userInTask.name" />
                            <span class="pull-right">
                                    <a id="add<s:property value='#userInTask.id '/>" style="display:none" href="javascript:void(0);" onclick="addUserToTask(<s:property value='#userInTask.id '/>,'<s:property value='#userInTask.name'/>')">添加</a>
                                    <a id="del<s:property value='#userInTask.id '/>" href="javascript:void(0);" onclick="deleteUserInTask(<s:property value='#userInTask.id '/>)">删除</a>
                                </span>
                        </h5></div></div>
                </s:iterator>
                <s:iterator value="otherUserList" id="userNotInTask">
                    <div class="media col-sm-4" style="margin-bottom:5px;margin-top:10px"><div class="media-left">
                        <img class="media-object img-circle  img-avatar-50" src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#userNotInTask.avatar" />" alt="avatar" onerror="this.src='statics/images/user.png'"/>
                    </div><div class="media-body">
                        <h5 class="media-heading"><s:property value="#userNotInTask.name" />
                            <span class="pull-right">
                                    <a id="add<s:property value='#userNotInTask.id '/>" href="javascript:void(0);" onclick="addUserToTask(<s:property value='#userNotInTask.id '/>,'<s:property value='#userNotInTask.name'/>')">添加</a>
                                    <a id="del<s:property value='#userNotInTask.id '/>" style="display:none" href="javascript:void(0);" onclick="deleteUserInTask(<s:property value='#userNotInTask.id '/>)">删除</a>
                                </span>
                        </h5></div></div>
                </s:iterator>
                <%--</tbody>--%>
                <%--</table>--%>
            </div>
        </form>
        </div>
    </form>
</div>

<div class="modal-footer">
    <div class="col-lg-9 text-left" style="display:inline; padding-left:0">
        <button type="button" class="btn btn-primary" id="advancemode" href="javascript:void(0);" onclick="changeadvance()" style="margin-right: 5px">高级模式</button>
        <button type="button" class="btn btn-primary" id="simplemode" href="javascript:void(0);" onclick="changesimple()" style="display:none;margin-right: 5px;margin-left: 0px">简洁模式</button>

        <button class="btn btn-primary" id="triggerFile" style="display:inline">添加图片</button>
        <form action="task/uploadTaskPic" method="post" id="uploadForm" class="form-group fr-file-upload" enctype="multipart/form-data" style="display:inline">

            <input type="file" name="file" id="fileUpload" style="display:none"/>
            <table data-file-info style="display:none"></table>
            <s:if test="taskPicList.size()>0">
        <span id="TaskPic-list"><label style="display:inline">已添加：</label>
            <s:iterator value="taskPicList">
                <%--<s:property value="name"/>--%>
                <span title="<s:property value='uuid'/>">  <s:property value="name"/><a href="javascript:void(0);" onclick="deleteTaskPic(this,'<s:property value="uuid"/>')">[删除]</a></span>
            </s:iterator>
        </span>
            </s:if>
            <s:else>
        <span id="TaskPic-list" style="display:none"><label>已添加：</label>
        </span>
            </s:else>
        </form>
    </div>

    <div class="col-lg-3 text-right" style="padding-right:0">
        <a id="delete"  class="btn btn-default">删除</a>
        <a class="btn btn-default" class="close modal-close" data-dismiss="modal" aria-hidden="true">取消</a>
        <a id="success" onclick="updateTask()" class="btn btn-primary">完成</a>
    </div>
</div>
<%--<%@include file="/statics/script.html" %>--%>
<script>
    var taskid = <s:property value="taskId"/>;
</script>
<script src="statics/simditor/scripts/module.js"></script>
<script src="statics/simditor/scripts/hotkeys.js"></script>
<script src="statics/simditor/scripts/uploader.js"></script>
<script src="statics/simditor/scripts/simditor.js"></script>
<script src="statics/frFileUpload/fileupload.min.js"></script>
<script type="text/javascript" src="statics/js/bootstrap.min.js"></script>
<script type="text/javascript" src="statics/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="statics/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="task/TaskCommon.js"></script>
<script src="task/TaskEditModal.js"></script>
<%--<script type="text/javascript" src="ueditor/ueditor.config.js"></script>--%>
<%--<script type="text/javascript" src="ueditor/ueditor.all.min.js"></script>--%>
<%--<script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>--%>
<script type="text/javascript" src="statics/js/jquery.form.js"></script>
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
        $('#editdescription').val("");
        Simditor.locale = 'zh-CN';
        editdescription = new Simditor({
            textarea: $('#editdescription'),
            toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', 'link', 'hr', '|', 'indent', 'outdent', 'alignment'],
            toolbarFloat: true,
            toolbarFloatOffset: 50,
            autofocus:true

        });
        var value = $("#hiddenarea").val();
        editdescription.setValue(value);
    })();

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
    })
//    var ue=UE.getEditor('editdescription',{
//        toolbars:[
//            [ 'fullscreen', 'source', '|', 'undo', 'redo', '|',
//                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
//                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
//                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
//                'directionalityltr', 'directionalityrtl', 'indent', '|',
//                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
//                'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
//                'simpleupload', 'insertimage', 'emotion',  'map', 'insertcode', '|',
//                'horizontal', 'date', 'time', 'spechars', '|',
//                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
//                'help']
//        ],
//       elementPathEnabled:false
//    });
//    ue.ready(function(){
//        var value = $("#hiddenarea").val();
//        ue.setContent(value,false);
//    })
</script>

