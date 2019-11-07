<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <title>项目编辑 - Free项目</title>
	<%@include file="/statics/head.html"%>
    <style type="text/css">
    	label{
    		font-weight:normal;
    		}
		.model-people-item span {
			cursor: pointer;
		}
		#model-selected-people li {
			margin: 2% 3% 3% -12%;
		}
    </style>
</head>
<body class="front-body">
<s:include value="/views/nav.jsp?act=project"/>
	<div class="front-inner front-inner-media">
		<div class="container">
			 <div class="row front-canvas" id="front-canvas">
	            <div class="col-md-3 nav-left-offcanvas" id="nav-left-offcanvas">
	                <div class="list-group nav-left">
	                	 <div id="menu1" class="list-group-item active" onclick="editproject()">项目设置<span class="glyphicon glyphicon-chevron-right"></span></div>
	                	 <div id="menu2" class="list-group-item" onclick="projectmem()">成员管理<span class="glyphicon glyphicon-chevron-right"></span></div>
					</div>
				</div>
				<div id="proedit" class="col-md-9">
					<div class="panel panel-default front-panel" style="margin-bottom:20px;">
		        	<div class="panel-heading">基本资料</div>
                    <div class="panel-body" style="position:relative;'">
                    	<div class="col-sm-2">
	                    	<div>
	                    		<img  id="projectimg" class="img-circle img-lg-avatar" src="http://freedisk.free4inno.com/download?uuid=<s:property value="pro.avatar"/>"
									  onerror="javascript:this.src='statics/images/group.png'">
	                    	</div>
	                    	<div style="padding-top:8px;">
			                     <a style="padding-left: 21px;cursor:pointer;" id="upload-avatar">修改图片</a>
								 <input type="hidden" name="hiddenUuid" id="hiddenuuid" value="<s:property value="pro.avatar" />">
			                </div>
		                </div>
                    	<div id="basicinfo">
				    		<div class="col-sm-10" style=" padding-left: 0px; padding-bottom: 40px;">
								<input type="hidden"  id="projectid" value="<s:property value='pro.id'/>">
								<input type="hidden"   id="projectState" value="<s:property value='pro.state'/>">

								<div class="col-sm-12" style=" padding-top: 15px;">
    								<label class="col-md-2 control-label front-label">项目名称<span class="redletter">*</span></label>	
     								<label class="col-md-10 control-label front-label"><s:property value="pro.name" /></label>
								</div>
								<div class="col-sm-12" style=" padding-top: 15px;">
	    							<label class="col-md-2 control-label front-label">项目简介</label>	
	     							<label class="col-md-10 control-label front-label"><s:property value="pro.description" /></label>
								</div>
								<div class="col-sm-12" style=" padding-top: 15px;">
	    							<label class="col-md-2 control-label front-label">状态</label>	
	     							<label class="col-md-10 control-label front-label">
										<s:if test="pro.state==2">已暂停</s:if>
										<s:if test="pro.state==1">进行中</s:if>
										<s:if test="pro.state==0">已完成</s:if>
									</label>
								</div>
								<div class="col-sm-12" style=" padding-top: 15px;">
	    							<label class="col-md-2 control-label front-label">目标</label>
	     							<label class="col-md-10 control-label front-label"><s:property value="pro.target" /></label>
								</div>
							</div>
							<s:if test="isAdmin == true">
							<a class="front-checkboxes front-checkboxes-bottom">
								<button  class="btn btn-default" data-toggle="modal" data-size="modal-lg" data-target="#editProjectMod" id="modifyProject" ><span class="glyphicon glyphicon-edit"></span> 编辑</button>
							</a>
							<a class="front-checkboxes front-checkboxes-bottom pull-left">
								<button  class="btn btn-default" data-toggle="modal" data-size="modal-lg" id="deleteProject" onclick="deleteProject()" >删除项目</button>
							</a>
							</s:if>
						</div>            
                    </div>
                </div>
				</div>
				 <div id="promem" class="col-md-9 hidden">
					 <div class="front-toolbar other">
						 <div class="front-toolbar-header clearfix">
							 <button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#freeshare-group" aria-expanded="false" aria-controls="freeshare-group">
								 <span class="icon-bar"></span>
								 <span class="icon-bar"></span>
							 </button>
						 </div>
						 <div id="projectUser" class="front-btn-group collapse" data-toggle="buttons">
							 <label class="btn btn-default" data-group="manager" style="box-shadow: none">
								 <input type="radio" name="options" autocomplete="off" checked=""><span class="glyphicon glyphicon-king"></span>&nbsp;负责人
							 </label>
							 <label class="btn btn-default" data-group="member" style="box-shadow: none">
								 <input type="radio" name="options" autocomplete="off" checked=""><span class="glyphicon glyphicon-pawn"></span>&nbsp;项目成员
							 </label>
						 </div>
						 <s:if test="isAdmin == true">
						 <a class="btn btn-primary pull-right" data-toggle="front-modal" data-size="modal-lg" data-href="views/select_people_model.jsp" id="addProjectMember" >添加成员</a>
                             <input type="hidden" id="selected-people-id" class="form-control" value="">
						 </s:if>

					 </div>
					 <div id="ManagerList" class="panel panel-default front-panel" style="margin-bottom:20px;">
						 <div class="panel-body front-no-padding">
							 <table class="table table-striped front-table" style="margin-bottom: 0px">
								 <tbody>
								 <s:iterator value="managerList" id="user">
									 <tr><td><div class="media"><div class="media-left">
										 <img class="media-object img-circle  img-avatar-50" src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#user.avatar" />" alt="avatar" onerror="this.src='statics/images/user.png'"/>
									 </div><div class="media-body">
										 <h5 class="media-heading"><s:property value="#user.name" />
											 <s:if test='%{isAdmin == true && AdminNum == true }'>
											 <span class="pull-right">
															<a href="javascript:cancelAdmin(<s:property value="#user.userid" />)">取消管理员</a>
															<%--<a href="javascript:deleteUser(<s:property value="#user.userid" />)">删除</a>--%>
														</span>
											 </s:if>
										 </h5></div></div></td></tr>
								 </s:iterator>
								 </tbody>
							 </table>
						 </div>
					 </div>
					 <div id="UserList" class="panel panel-default front-panel" style="margin-bottom:20px;display: none;">
						 <div class="panel-body front-no-padding">
							 <table class="table table-striped front-table" style="margin-bottom: 0px">
								 <tbody>
								 <s:iterator value="userList" id="user">
									 <tr><td><div class="media"><div class="media-left">
										 <img class="media-object img-circle  img-avatar-50" src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#user.avatar" />" alt="avatar" onerror="this.src='statics/images/user.png'"/>
									 </div><div class="media-body">
										 <h5 class="media-heading"><s:property value="#user.name" />

											 <span class="pull-right">
												 <s:if test=" isAdmin == true">
															<a href="javascript:addAdmin(<s:property value="#user.userid" />)">设为负责人</a>
															<a href="javascript:deleteUser(<s:property value="#user.userid" />)">删除</a>
												 </s:if>
														</span>
										 </h5></div></div></td></tr>
								 </s:iterator>
								 </tbody>
							 </table>
						 </div>
					 </div>
				 </div>
				 <div class="modal fade" id="editProjectMod">
					 <div class="modal-dialog">
						 <div class="modal-content">

							 <div class="modal-header">
								 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								 <h4 class="modal-title">编辑项目资料</h4>
							 </div>
							 <div class="modal-body">
								 <div class="form-horizontal">

									 <div class="form-group">
										 <label class="col-sm-2 control-label">项目名称</label>
										 <div class="col-sm-10">
											 <input type="text" class="form-control front-no-box-shadow" id="editprojectname" maxlength=50 placeholder="请填写项目名称(50字以内)" value="<s:property value='pro.name' />">
										 </div>
									 </div>

									 <div class="form-group">
										 <label  class="col-sm-2 control-label">项目简介</label>
										 <div class="col-sm-10">
											 <textarea id="editprojectdesc" class="form-control front-no-box-shadow" rows="3" maxlength=100 style="resize:none;" placeholder="可选择填写项目简介(100字以内)"><s:property value="pro.description" /></textarea>
										 </div>
									 </div>

									 <div class="form-group">
										 <label  class="col-sm-2 control-label">项目状态</label>
										 <label class="col-sm-10">
											 <div class="dropdown">
												 <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
													 <s:if test="pro.state==2">已暂停</s:if>
													 <s:if test="pro.state==1">进行中</s:if>
													 <s:if test="pro.state==0">已完成</s:if>
													 <span class="caret"></span>
												 </button>
												 <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
													 <li role="presentation" id="state2"><a role="menuitem" tabindex="-1" onclick="changeState(2)" href="javascript:void(0)">已暂停</a></li>
													 <li role="presentation" id="state1"><a role="menuitem" tabindex="-1" onclick="changeState(1)" href="javascript:void(0)">进行中</a></li>
													 <li role="presentation" id="state0"><a role="menuitem" tabindex="-1" onclick="changeState(0)" href="javascript:void(0)">已完成</a></li>
												 </ul>
											 </div>
										 </label>
									 </div>

									 <div class="form-group">
										 <label class="col-sm-2 control-label">项目目标</label>
										 <div class="col-sm-10">
											 <textarea id="editprojectgoal" class="form-control front-no-box-shadow" rows="3" style="resize:none;" maxlength=100 placeholder="可选择填写项目简介(100字以内)"><s:property value="pro.target" /></textarea>
										 </div>
									 </div>
								 </div>
							 </div>
							 <div class="modal-footer">
								 <button class="btn btn-default" data-dismiss="modal">取消</button>
								 <button class="btn btn-primary" onclick="updateProject()">完成</button>
							 </div>

						 </div>
					 </div>
				 </div>
			</div>

		</div>
		<%@include file="/views/footer.jsp"%>
	</div>


<%@include file="/statics/script.html"%>
<script src="http://newfront.free4inno.com/plugin/fileupload/fileupload.min.js"></script>
<script src="http://newfront.free4inno.com/plugin/imgupload/imgupload.js"></script>
<script src="project/ProjectEdit.js"></script>
<script type="text/javascript">

    $('#projectUser').click(function (event) {
        var target = $(event.target).is('span') ? $(event.target).parent() : $(event.target);
        switch (target.attr('data-group')) {
            case 'manager' :
                $("#ManagerList").css("display", "block");
                $("#UserList").css("display", "none");
                break;
            case "member":
                $("#ManagerList").css("display", "none");
                $("#UserList").css("display", "block");
                break;
        }
    })
    function deleteProject(){
        var id = $("#projectid").val();
        $.tipModal('confirm','info','确定删除该项目？',function(result){
            $('.modal').modal('hide');
            if(result==true){
                $.post("project/deleteProject",{id:id},function(){
                    location.href="home";
                });
            }
        })
	}
</script>
</body>
</html>