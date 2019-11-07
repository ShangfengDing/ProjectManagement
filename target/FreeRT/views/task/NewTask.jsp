<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <title>新建任务 - 轻项目</title>
	<%@include file="../../statics/head.html"%>
	<link rel="stylesheet" href="statics/simditor/styles/simditor.css">
	<style>
		.simditor .simditor-wrapper .simditor-placeholder{
			display:block;
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
					<label class="col-lg-1 col-md-2 control-label">任务描述</label>
					<div class="col-lg-11">
						<%--<input id="adddescription" class="form-control front-no-radius front-no-box-shadow"  type="text" maxlength="100" placeholder="任务描述最大限制为100字">--%>
							<%--<script id="adddescription" name="content" type="text/plain"></script>--%>
							<textarea class="form-control" id="adddescription"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-1 col-md-2 control-label">所属项目</label>
					<span class="col-md-4 control-label front-label">
					<select id="addpid" onchange="clearMem()" class="form-control front-no-radius front-no-box-shadow">
						<s:iterator value="aliveProListByManager" var="p">
							<s:if test="#p.id==projectId">
								<option selected="selected" value=<s:property value="id"/>><s:property value="name"/></option>
							</s:if>
							<s:else>
								<option value=<s:property value="id"/>><s:property value="name"/></option>
							</s:else>
						</s:iterator>
					</select>
				</span>
					<span class="col-md-2"></span>
					<label class="col-lg-1 col-md-2 control-label">任务状态</label>
					<span class="col-md-4 control-label front-label">
					<select id="addstate" class="form-control front-no-radius front-no-box-shadow">
						<option value="1">待处理</option>
						<option value="2">进行中</option>
						<option value="3">待验收</option>
						<option value="4">已完成</option>
					</select>
				</span>
				</div>
				<div class="form-group">
					<label class="col-lg-1 col-md-2 control-label">任务量</label>
					<span class="col-md-4 control-label front-label">
					<select id="addamount" class="form-control front-no-radius front-no-box-shadow">
						<option>5</option>
						<option>4</option>
						<option selected="selected">3</option>
						<option>2</option>
						<option>1</option>
					</select>
				</span>
					<span class="col-md-2"></span>
					<label class="col-lg-1 col-md-2 control-label">重要性</label>
					<div class="col-md-4">
						<input type="text" id="addpriority" value="" class="form-control front-no-radius front-no-box-shadow" placeholder="请填写一百以内的整数">
					</div>
				</div>

				<div class="form-group">
					<label class="col-lg-1 col-md-2 control-label">参与人员</label>
					<div class="col-md-4">
                    <span>
						<span id="TaskMem" data-clicked="false">
						</span>
						<input type="hidden" id="SelectedMemId" class="form-control" value="">
                        <div class="pull-right">
                            <span onclick="openMem()" class="btn btn-primary">添加人员</span>
                        </div>
                    </span>
					</div>
				</div>
			</form>

			<form action="task/uploadTaskPic" method="post" class="form-group fr-file-upload" enctype="multipart/form-data">
				<div class="form-group">
					<label class="col-lg-1 col-md-2 control-label">添加图片</label>
					<%--<a class="col-lg-1 btn btn-default">--%>
						<%--<span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;&nbsp;添加图片--%>
					<%--</a>--%>
					<div class="col-lg-11" style="padding-left: 0px;padding-right: 0px;">
						<div style="height: 64px; background: #fff; border: 1px solid #ddd">
							<span style="position: absolute; left: 40%; line-height: 64px;">点击此处或拖拽文件到此处</span>
							<input type="file" name="file" id="triggerFile" style="opacity: 0; cursor: pointer; width: 100%; height: 64px;"/>
						</div>
						<table data-file-info></table>
						<div id="TaskPic-list" style="display: none"><label>已添加：</label></div>
						<%--<s:if test="attachmentList && attachmentList.size() > 0">--%>
							<%--<div id="TaskPic-list"><label>已添加：</label>--%>
								<%--<s:iterator value="attachmentList">--%>
									<%--<span title="<s:property value="uuid"/>"><br><s:property value="name"/>&nbsp;<a href="javascript:void(0)" onclick="deleteAttachment(this,'<s:property value="uuid"/>')">[删除]</a></span>--%>
								<%--</s:iterator>--%>
							<%--</div>--%>
						<%--</s:if>--%>
						<%--<s:else>--%>

						<%--</s:else>--%>
					</div>
				</div>
			</form>

			<form class="form-horizontal" style="padding-top: 25px">
				<div class="form-group">
					<div class="col-lg-offset-1 col-lg-11 text-right" style="padding-top: 10px">
						<a class="btn btn-default" href="home">取消</a>
						<a onclick="addTask()" class="btn btn-primary">完成</a>
					</div>
				</div>
			</form>
        </div>
		<%@include file="../footer.jsp"%>
		<%@include file="../modal/MemberModal.jsp" %>
    </div>
	<%@include file="../../statics/script.html"%>
	<script src="statics/simditor/scripts/module.js"></script>
	<script src="statics/simditor/scripts/hotkeys.js"></script>
	<script src="statics/simditor/scripts/uploader.js"></script>
	<script src="statics/simditor/scripts/simditor.js"></script>
	<script src="statics/frFileUpload/fileupload.min.js"></script>
	<script src="task/TaskCommon.js"></script>
	<%--<script type="text/javascript" src="ueditor/ueditor.config.js"></script>--%>
	<%--<script type="text/javascript" src="ueditor/ueditor.all.min.js"></script>--%>
	<%--<script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>--%>
	<script>
//        var ue=UE.getEditor('adddescription',{
//            toolbars:[
//                [ 'fullscreen', 'source', '|', 'undo', 'redo', '|',
//                    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
//                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
//                    'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
//                    'directionalityltr', 'directionalityrtl', 'indent', '|',
//                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
//                    'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
//                    'simpleupload', 'insertimage', 'emotion',  'map',  'insertcode', '|',
//                    'horizontal', 'date', 'time', 'spechars', '|',
//                    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
//                    'help']
//            ],
//			autoHeightEnabled:false,
//			autoFloatEnabled:false,
//			zIndex:900,
//            elementPathEnabled:false
//        });
//        ue.ready(function(){
//            ue.setHeight(150);
//		})

(function() {
    $('#adddescription').val("");
    Simditor.locale = 'zh-CN';
    adddescription = new Simditor({
        textarea: $('#adddescription'),
        placeholder:'请在此输入任务描述...',
        toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', 'link', 'hr', '|', 'indent', 'outdent', 'alignment'],
        toolbarFloat: false,
        toolbarFloatOffset: 50,
        autofocus:true

    });
})();
$('#triggerFile').click(function(){
    var ifexist=$('#TaskPic-list').children('span').length;
    if(ifexist>0) {
        $.fillTipBox({type: 'warning', icon: 'glyphicon-exclamation-sign', content: '只能添加一张图片！'});
        return false;
    }
});
	</script>
</body>
</html>