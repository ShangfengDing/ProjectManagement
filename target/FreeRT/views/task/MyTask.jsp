<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/"/>
	<%@include file="../../statics/head.html" %>
	<title>我的任务 - 轻项目</title>
	<style type="text/css">
		.modal > div { /* 追加此行 */
			display: table;
			width: 100%;
			height: 100%;
		}

		.modal-dialog {
			/* 略去展示原有内容，此处只显示追加内容 */
			width: 1000px;
			display: table-cell;
			vertical-align: middle;
			margin: 0px 20px;
		}

		.modal-content {
			/* 略去展示原有内容，此处只显示追加内容 */
			display: block;
			width: 1000px;
			margin: 0 auto;
		}
	</style>
</head>
<body class="front-body">

<div class="front-inner front-inner-media">
	<s:include value="../nav.jsp?act=task"/>
	<div>
		<input class="hidden" id="projectId" value="<s:property value="projectId"/>">
		<div class="front-toolbar other" style="padding-left: 15px;">
			<div class="front-toolbar-header clearfix">
				<button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse"
						data-target="#project-buttons">
					<span class="icon-bar"></span>
					<%--<s:iterator value="aliveProList">
                        <span class="icon-bar"></span>
                    </s:iterator>--%>
				</button>
			</div>
			<div id="project-buttons" class="front-btn-group collapse btn-group" data-toggle="buttons">
				<s:if test="projectId==0">
					<button class="btn btn-default front-no-box-shadow active" data-group="0"
							onclick="window.location.href='task/listMytask?projectId=0'">
						全部
					</button>
				</s:if>
				<s:else>
					<button class="btn btn-default front-no-box-shadow" data-group="0"
							onclick="window.location.href='task/listMytask?projectId=0'">
						全部
					</button>
				</s:else>
				<s:iterator value="aliveProList" var="p">
					<s:if test="#p.id==projectId">
						<button class="btn btn-default front-no-box-shadow active" data-group="<s:property value="id"/>"
								onclick="window.location.href='task/listMytask?projectId=<s:property value="id"/>'">
							<s:property value="name"/>
						</button>
					</s:if>
					<s:else>
						<button class="btn btn-default front-no-box-shadow"
								onclick="window.location.href='task/listMytask?projectId=<s:property value="id"/>'">
							<s:property value="name"/>
						</button>
					</s:else>
				</s:iterator>

				<s:if test="otherAliveProList.size!=0">
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle"
								data-toggle="dropdown">
							... <span class="caret"></span>
						</button>

						<ul class="dropdown-menu" role="menu">
							<s:iterator value="otherAliveProList">
								<li><a style="cursor: pointer" onclick="window.location.href='task/listMytask?projectId=<s:property
										value="id"/>'"><s:property value="name"/></a></li>
							</s:iterator>
						</ul>
					</div>
				</s:if>
			</div>

		</div>

		<%--<div class="btn-group" style="padding-left: 15px">
                <ul class="breadcrumb" style="margin-bottom: 0px">
                    <li >
                        <a href="home">我的项目&nbsp;</a>
                    </li>
                    <li id="task" class="active" style="position: relative;">
                        项目任务&nbsp;
                    </li>
                    <li style="position: relative;">
                        <a id="AllMyTask" type="button" class="btn btn-default dropdown-toggle" style="background-color: #f5f5f5;border:0" data-toggle="dropdown">
                            全部&nbsp;<span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu"  role="menu" style="border: 0px;">
                            <li><a href="">全部</a></li>
                            <s:iterator value="projectList">
                                <li ><a  id="ProjectId<s:property value="id"/>"data-group="<s:property value="id"/>" onclick="showMyTask(<s:property value="id"/>)"><s:property value="name"/></a></li>
                            </s:iterator>
                        </ul>
                    </li>

                </ul>
        </div>--%>


		<div id="taskloading" style="margin-top:200px;">
			<div class="front-loading">
				<div class="front-loading-block"></div>
				<div class="front-loading-block"></div>
				<div class="front-loading-block"></div>
			</div>
			<div class="panel-body text-center">正在加载请稍候</div>
		</div>
		<div id="taskcontainer" style="display:none;padding-top: 10px">
			<div class="col-sm-3">
				<div class="panel panel-default front-panel" style="margin-bottom:10px;">
					<div class="panel-heading" style="padding-bottom:10px;">待处理
						<%--<a href="task/newtask" class="btn-default btn-xs  pull-right"><span class="glyphicon glyphicon-plus"></span></a>--%>
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
</div>
</div>
<div class="hidden" id="modal-task">
	<div class='task'>
		<input type="hidden" value="{taskid}">
		<div class='taskcontent'>{description} <a id="hasPic{taskid}"><span id="hasPic1{taskid}"
																			class="taskPic glyphicon glyphicon-picture"
																			data-toggle="modal"
																			data-target="#taskPicture{taskid}"></span></a>
		</div>
		<div class="modal  fade" id="taskPicture{taskid}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			 aria-hidden="true">
			<div>
				<div class="modal-dialog ">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">任务图片</h4>
						</div>
						<div class="modal-body ">
							<img class="img-responsive" src="http://freedisk.free4inno.com/download?uuid={taskPic}"
								 style="text-align: center">
						</div>

					</div><!-- /.modal-content -->
				</div><!-- /.modal -->
			</div>

		</div>
		<div class='taskmember'>
					<span>
						<span class='front-badge-lg {priorityBadge}' data-priority="{priority}">{priority}</span>
						<span class='front-badge-lg badge-normal'>任务量{amount}</span>
					</span>
			<span style='float:right;' id="name-list">
						<span class='front-badge-lg badge-name'>{myName}</span>
					</span>
		</div>
	</div>
</div>
<div class="hidden" id="modal-name">
	<span class='front-badge-lg badge-name'>{name}</span>
</div>
<%@include file="../../statics/script.html" %>
<script src="statics/jqueryUI/jquery-ui.js"></script>
<script src="statics/frFileUpload/fileupload.min.js"></script>
<script src="task/TaskCommon.js"></script>
<script src="task/MyTask.js"></script>
</body>
</html>