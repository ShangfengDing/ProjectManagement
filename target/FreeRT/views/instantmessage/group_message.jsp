<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>

<style>
    /* for Chrome & Safari*/
    ::-webkit-scrollbar{width:0px}
</style>

<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/"/>
    <%@include file="/statics/head.html" %>

    <title><s:property value="projectName"/> - 项目会话</title>
</head>
<body class="front-body" onload="chatMsgAjax()" style="height: 100%">
<div id="msgCount" class="hidden">0</div>
<div class="front-inner front-inner-media" style="padding-bottom: 0px;padding-top: 0px">
    <div class="container" style="padding-left: 0px;padding-right: 0px;padding-bottom:135px">
        <input type="hidden" id="projectId" value="<s:property value="projectId"/>"/>
        <input type="hidden" id="chatId" value="<s:property value="chatId"/>"/>
        <div id="show-message-area"></div>

    </div>

    <div style="position: fixed;bottom: 0;width: 100%;z-index: 100;background-color: #e7e7e7">
        <div class="container" style="padding-left: 6px; padding-right: 6px">
            <div class="text-right" style="padding-top: 10px">
                <form id="link-picture" action="task/uploadTaskPic" method="post" class="fr-file-upload" enctype="multipart/form-data" style="position: absolute;margin-left: 1085px;">
                    <a><span id="picture" class="glyphicon glyphicon-picture" style="color: #777;font-size: 17px"></span></a>
                    <%--<span style="position: absolute; left: 40%; line-height: 64px;">点击此处</span>--%>
                    <input type="file" name="file" id="triggerpictureFile" style="opacity: 0; cursor: pointer; width: 100%; height: 17px;margin-top: -20px"/>
                    <table data-file-info style="display:none"></table>
                </form>
                <a href="info?id=<s:property value="chatId"/>&&type=user"><span class="glyphicon glyphicon-user" style="color: #777;font-size: 17px"></span></a>&nbsp;
                <a href="javascript:void(0);" onclick="historyMsgAjax()"><span id="history" class="glyphicon glyphicon-time" style="color: #777;font-size: 17px"></span></a>&nbsp;
            </div>

            <form class="form-horizontal" style="padding-top: 10px">
                <div class="form-group" style="position: relative;margin-bottom: 6px">
                    <div class="col-md-12" style="margin-bottom: 6px">
                        <%--<textarea class="form-control" style="border-style:none;" id="message-normal-textarea" rows="1" placeholder="按Enter发出消息"></textarea>--%>
                        <textarea class="form-control" style="border-style:none;" id="message-normal-textarea" rows="1" placeholder="按Enter发出消息"></textarea>
                    </div>

                    <div class="clearfix col-md-12">
                        <a id="message-submit-button" onclick="preSubmitMessage()" class="btn btn-primary" style="float: right; border:none; background-color:  #4e89f0"><span class="glyphicon glyphicon-send"></span> &nbsp;发送</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="/statics/script.html"%>
<script src="http://newfront.free4inno.com/js/plugin/front.js"></script>
<script src="statics/frFileUpload/fileupload.min.js"></script>
<script src="message/group_message_model.js">
</script>
<%--<script src="task/TaskCommon.js"></script>--%>
<%--<script>--%>
    <%--var messageSubmitButton = $('#message-submit-button');--%>
    <%--var messageNormalTextarea = $('#message-normal-textarea');--%>
    <%--var isSubmitted = false;--%>
    <%--// $('.fr-file-upload').frFileUpload({--%>
    <%--//     maxFileSize:10*1024*1024,//10MB--%>
    <%--//--%>
    <%--//     uploadCallback:function (json) {--%>
    <%--//         if(json.uuid == null || json.uuid == "") {--%>
    <%--//             return false;--%>
    <%--//         } else {--%>
    <%--//             console.log(json.uuid);--%>
    <%--//             var pictureuuid=json.uuid;--%>
    <%--//             var des;--%>
    <%--//             des="<img src=\"http://freedisk.free4inno.com/download?uuid=" + pictureuuid + "\""+"style=\"max-width: 100%;"+"\">";--%>
    <%--//             // console.log(des);--%>
    <%--//             $.post("message/saveMessage", {--%>
    <%--//                 "conversation.chatId": chatId,--%>
    <%--//                 "conversation.context": des,--%>
    <%--//             }, function (json) {--%>
    <%--//                 messageSubmitButton.removeClass("disabled");--%>
    <%--//                 messageSubmitButton.html('<span class="glyphicon glyphicon-send"></span> &nbsp;发送');--%>
    <%--//                 if(json.saveResult==true) {--%>
    <%--//                     messageNormalTextarea.val("");--%>
    <%--//                     isSubmitted = true;--%>
    <%--//                     chatMsgAjax();--%>
    <%--//                 } else {--%>
    <%--//                     $.tipModal('alert', 'danger', '发送失败');--%>
    <%--//                     $('#resubmit-button').html('<a href="javascript:submitMessage()">重新发送</a>');--%>
    <%--//                 }--%>
    <%--//             })--%>
    <%--//             return true;--%>
    <%--//         }--%>
    <%--//     }--%>
    <%--// });--%>
<%--</script>--%>
</body>
</html>