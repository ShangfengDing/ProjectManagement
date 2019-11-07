<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="hidden" id="modal-task">
    <div class='task'>
        <input type="hidden" value="{taskid}">
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