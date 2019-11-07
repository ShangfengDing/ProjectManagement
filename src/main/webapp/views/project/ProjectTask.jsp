<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <title>项目任务 - 轻项目</title>
	<%@include file="/statics/head.html"%>
	<style type="text/css">
		.modal>div{/* 追加此行 */
			display:table;
			width:100%;
			height:100%;
		}

		.modal-dialog {
			/* 略去展示原有内容，此处只显示追加内容 */
			width: 1000px;
			display:table-cell;
			vertical-align:middle;
			margin: 0px 20px;
		}

		.modal-content {
			/* 略去展示原有内容，此处只显示追加内容 */
			display:block;
			width: 1000px;
			/*margin-left: 0 auto;*/
			/*margin-right:0 auto;*/
			margin:0 auto;
			margin-top: 4%;
		}
        .bootBoxStyle {
            /* 略去展示原有内容，此处只显示追加内容 */
            display:block;
            width: 298px;
            margin:0 auto;
            margin-top: 2%;
        }
	</style>
</head>
<body class="front-body">
<div class="front-inner front-inner-media">
<s:include value="../nav.jsp?act=project"/>
	<input id="auth" class="hidden" value="<s:property value="auth"/>"><%--判断是否是项目成员--%>
	<input id="judge" class="hidden" value="<s:property value="judge"/>"><%--判断是否是项目管理员--%>
	<s:if test="auth == true">
		<input type="hidden" id="selectedProject" value="<s:property value='id'/>">
		<%--旧的项目展示方式，展示5个剩下的下拉框展示
		<div class="front-toolbar other"  style="padding-left: 15px;padding-right: 15px;">
			<div class="front-toolbar-header clearfix">
				<button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#project-buttons">
					<span class="icon-bar"></span>
					&lt;%&ndash;<s:iterator value="aliveProList">
						<span class="icon-bar"></span>
					</s:iterator>&ndash;%&gt;
				</button>

			</div>
			<div id="project-buttons" class="front-btn-group collapse" data-toggle="buttons" >
				<s:iterator value="aliveProList" var="p">
					<s:if test="#p.id==projectId">
						<label class="btn btn-default front-no-box-shadow active" data-group="<s:property value="id"/>">
							<input type="radio" name="options" autocomplete="off"><s:property value="name"/>
						</label>
					</s:if>
					<s:else>
						<label class="btn btn-default front-no-box-shadow" data-group="<s:property value="id"/>">
							<input type="radio" name="options" autocomplete="off"><s:property value="name"/>
						</label>
					</s:else>
				</s:iterator>
			</div>
			<s:if test="otherAliveProList.size!=0">
				<div class="btn-group">
					<button type="button" class="btn btn-default dropdown-toggle"
							data-toggle="dropdown">
						... <span class="caret"></span>
					</button>

					<ul class="dropdown-menu" role="menu">
						<s:iterator value="otherAliveProList">
							<li><a onclick="window.location.href='task/viewtask?projectId=<s:property value="id"/>'"><s:property value="name"/></a></li>
						</s:iterator>
					</ul>
				</div>
			</s:if>
			<a class="btn btn-sm btn-primary pull-right" id="mailRemind"  style="display:none">一键邮箱提醒</a>

		</div>--%>

		<div style="margin-left: 15px;margin-bottom: 5px">
				<ul class="breadcrumb" style="margin-bottom: 0px">
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
								<li><a data-group="<s:property value="id"/>" href="task/ProjectTask?id=<s:property value="id" />"><s:property value="name"/></a></li>
							</s:iterator>
						</ul>
					</li>
					<li id="title" class="active" style="position: relative;">
						<a type="button" id="projectDetail" class="btn-small btn-default dropdown-toggle" style="background-color: #f5f5f5;border:0;cursor: pointer;" data-toggle="dropdown">
							任务&nbsp;<span class="caret"></span>
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


		<%--<div id="taskloading" style="margin-top:200px;"><div class="front-loading">
			<div class="front-loading-block"></div>
			<div class="front-loading-block"></div>
			<div class="front-loading-block"></div>
		</div><div class="panel-body text-center">正在加载请稍候</div></div>--%>
		<div id="taskcontainer" style="display:block;padding-top: 5px;">
        		<div class="col-sm-3">
        			<div class="panel panel-default front-panel" style="margin-bottom:10px;">
					<div class="panel-heading" id="addTaskContainer" style="padding-bottom:10px;">待处理
						<s:if test="judge == true">
						<a class="pull-right" title="新建任务" data-toggle="front-modal" data-size="modal-lg" data-href="task/newtaskModal?projectId=<s:property value="id"/>" style="cursor:pointer;text-decoration:none;color:black;padding-top: 2px">
							<span class="glyphicon glyphicon-plus" id="addTask"></span>
						</a>

						<a class="pull-right" title="一键邮箱提醒" style="cursor:pointer;text-decoration:none;color:black;padding-top: 3px; margin-right: 16px">
							<span class="glyphicon glyphicon-envelope" id="mailRemind"></span>
						</a>


						<%--<a class="pull-right" id="mailRemind"  style="float: left;padding-right: 5px;cursor:pointer">--%>
							<%--<span class="glyphicon glyphicon-envelope" id="remindTask"></span>--%>
						<%--</a>--%>
						</s:if>
					</div>
						<div style="margin-bottom:10px; overflow-y:scroll; overflow-x:hidden;" id="containState1">
							<div class="panel-body front-no-padding task-box" id="state1">
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-3">
        			<div class="panel panel-default front-panel " style="margin-bottom:10px;">
						<div class="panel-heading">进行中</div>
						<div style="margin-bottom:10px; overflow-y:scroll; overflow-x:hidden;:" id="containState2">
							<div class="panel-body front-no-padding task-box" id="state2">
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-3">
        			<div class="panel panel-default front-panel" style="margin-bottom:10px;">
					<div class="panel-heading">待验收</div>
						<div style="margin-bottom:10px; overflow-y:scroll; overflow-x:hidden;" id="containState3">
							<div class="panel-body front-no-padding task-box" id="state3">
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-3">
        			<div class="panel panel-default front-panel " style="margin-bottom:10px;">
					<div class="panel-heading">已完成</div>
						<div style="margin-bottom:10px; overflow-y:scroll; overflow-x:hidden;" id="containState4">
							<div class="panel-body front-no-padding task-box" id="state4">
							</div>
						</div>
					</div>
					</div>
				</div>
	    </div>
    </s:if>
<div class="hidden" id="modal-task">
	<div class='task'>
		<input type="hidden" value="{taskid}">
		<%--<s:if test="taskPicList&&taskPicList>0">--%>
			<%--<div class='taskcontent'>{description}    <a><span class="taskPic glyphicon glyphicon-picture" data-toggle="modal" data-target="#taskPicture{taskid}"></span></a></div>--%>
			<%--<div class="modal fade" id="taskPicture{taskid}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">--%>
				<%--<div class="modal-dialog">--%>
					<%--<div class="modal-content">--%>
						<%--<div class="modal-header">--%>
							<%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
							<%--<h4 class="modal-title" id="myModalLabel">任务图片</h4>--%>
						<%--</div>--%>
						<%--<div class="modal-body">--%>
							<%--<img  src="http://freedisk.free4inno.com/download?uuid={taskPic}"--%>
								  <%--onerror="javascript:this.src='statics/images/group.png'" onclick="window.location.href='info?id=<s:property value="#plist.id" />'">--%>
						<%--</div>--%>

					<%--</div><!-- /.modal-content -->--%>
				<%--</div><!-- /.modal -->--%>
			<%--</div>--%>
		<%--</s:if>--%>
		<%--<s:else>--%>
			<%--<div class='taskcontent'>{description}</div>--%>
		<%--</s:else>--%>
		<div class='taskcontent'>{description}    <a id="hasPic{taskid}"><span id="hasPic1{taskid}" class="taskPic glyphicon glyphicon-picture" data-toggle="modal" data-target="#taskPicture{taskid}"></span></a></div>
		<div class="modal  fade" id="taskPicture{taskid}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div>
				<div class="modal-dialog " >
					<div class="modal-content" >
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">任务图片</h4>
						</div>
						<div class="modal-body " >
							<img class="img-responsive" src="http://freedisk.free4inno.com/download?uuid={taskPic}"  style="text-align: center">
						</div>

					</div><!-- /.modal-content -->
				</div><!-- /.modal -->
			</div>

		</div>
		<div class='taskmember'>
			<span>
				<s:if test="judge == true">
				<a  data-toggle="front-modal" data-size="modal-lg" data-href="task/edittask?taskId={taskid}&projectId=<s:property value='projectId'/>" id="modifyTask{taskid}" style="text-decoration: none;color: black;">
                        <span class="glyphicon glyphicon-cog"></span>
				</a>
				</s:if>
				<span class='front-badge-lg {priorityBadge}' data-priority="{priority}">{priority}</span>
				<span class='front-badge-lg badge-normal'>任务量{amount}</span>
			</span>
			<span style='float:right;' id="name-list">

			</span>
		</div>
	</div>
</div>
<div class="hidden" id="modal-name">
	<span class='front-badge-lg badge-name'>{name}</span>
</div>
	<%@include file="/statics/script.html"%>
	<script>
		var projectid = <s:property value="projectId"/>;
	</script>
	<script src="statics/jqueryUI/jquery-ui.js"></script>
	<script src="statics/frFileUpload/fileupload.min.js"></script>
	<script src="task/TaskCommon.js"></script>
	<script src="task/ProjectTask.js"></script>
	<script src="project/InfoFlow.js"></script>
	<script>
        var projectId = $("#selectedProject").val();
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