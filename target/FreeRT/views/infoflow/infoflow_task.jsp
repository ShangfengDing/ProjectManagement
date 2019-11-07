<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


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
            margin-top:5%;
            margin-left:auto;
            margin-right:auto;
        }
        .bootBoxStyle {
            /* 略去展示原有内容，此处只显示追加内容 */
            display:block;
            width: 298px;
            margin:0 auto;
            margin-top: 2%;
        }
    </style>
    <input id="auth" class="hidden" value="<s:property value="auth"/>">
    <input id="selectedProject" class="hidden" value="<s:property value="projectId"/>">
    <s:if test="auth == true">

    <div id="taskcontainer"  style="display:block;">
        <div class="col-sm-3" style="padding-left: 0px;padding-right: 0px;">
            <div class="panel panel-default front-panel" style="margin-bottom:10px;">
                <div class="panel-heading" style="padding-bottom:10px;">待处理
                    <a href="task/newtask?projectId=<s:property value="projectId"/>" class="  pull-right" style="text-decoration:none;color:black;padding-top: 2px"><span class="glyphicon glyphicon-plus" id="addTask" style="display:none;text-decoration:none"></span></a>
                    <a class="pull-right" id="mailRemind"  style="display:none;float: left;padding-right: 5px;cursor:pointer;text-decoration:none">一键邮箱提醒</a>
                </div>
                <div style="margin-bottom:10px; overflow-y:scroll; overflow-x:hidden;" id="containState1">
                    <div class="panel-body front-no-padding task-box" id="state1" >
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-3" style="padding-left: 0px;padding-right: 0px;">
            <div class="panel panel-default front-panel " style="margin-bottom:10px;">
                <div class="panel-heading">进行中</div>
                <div style="margin-bottom:10px; overflow-y:scroll; overflow-x:hidden;:" id="containState2">
                    <div class="panel-body front-no-padding task-box" id="state2">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-3" style="padding-left: 0px;padding-right: 0px;">
            <div class="panel panel-default front-panel" style="margin-bottom:10px;">
                <div class="panel-heading">待验收</div>
                <div style="margin-bottom:10px; overflow-y:scroll; overflow-x:hidden;" id="containState3">
                    <div class="panel-body front-no-padding task-box" id="state3">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-3" style="padding-left: 0px;padding-right: 0px;">
            <div class="panel panel-default front-panel " style="margin-bottom:10px;">
                <div class="panel-heading">已完成</div>
                <div style="margin-bottom:10px; overflow-y:scroll; overflow-x:hidden;" id="containState4">
                    <div class="panel-body front-no-padding task-box" id="state4">
                    </div>
                </div>
            </div>
        </div>
    </div>

</s:if>
<s:else>
    <s:include value="/error/member.jsp"/>
</s:else>
<div class="hidden" id="modal-task" >
    <div class='task'>
        <input type="hidden" value="{taskid}">
        <div class='taskcontent'>{description}    <a id="hasPic{taskid}"><span id="hasPic1{taskid}" class="taskPic glyphicon glyphicon-picture" data-toggle="modal" data-target="#taskPicture{taskid}"></span></a></div>
        <div class="modal  fade" id="taskPicture{taskid}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div>
                <div class="modal-dialog " >
                    <div class="modal-content"  >
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
				<%--<span class='dropdown' ><button class='btn btn-xs btn-default dropdown-toggle' type='button'  data-toggle='dropdown'> 移动<span class='caret'></span></button>
					<ul class='dropdown-menu' role='menu' aria-labelledby='dropdownMenu1'>
						<li role='presentation' id="change-state1"><a role='menuitem' tabindex='-1' onclick='changeState({taskid},1,this)' href='javascript:void(0)'>待处理</a></li>
						<li role='presentation' id="change-state2"><a role='menuitem' tabindex='-1' onclick='changeState({taskid},2,this)' href='javascript:void(0)'>进行中</a></li>
						<li role='presentation' id="change-state3"><a role='menuitem' tabindex='-1' onclick='changeState({taskid},3,this)' href='javascript:void(0)'>待验收</a></li>
						<li role='presentation' id="change-state4{taskid}" style="display:none;list-style-type:none;"><a role='menuitem' tabindex='-1' onclick='changeState({taskid},4,this)' href='javascript:void(0)'>已完成</a></li>
					</ul>
				</span>--%>
				<a  data-toggle="front-modal" data-size="modal-lg" data-href="task/edittask?taskId={taskid}" id="modifyTask{taskid}" style="display: none;text-decoration: none;color: black;">
                        <span class="glyphicon glyphicon-cog"></span>
				</a>
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
<script src="statics/jqueryUI/jquery-ui.js"></script>
<script src="statics/frFileUpload/fileupload.min.js"></script>
<script src="task/TaskCommon.js"></script>
<script src="task/ProjectTask.js"></script>
