<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
            <div id="promem" class="col-md-12 " style="padding-left:0px;padding-right: 0px;">
                <div class="front-toolbar other">
                    <div class="front-toolbar-header clearfix">
                        <button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#freeshare-group" aria-expanded="false" aria-controls="freeshare-group">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div id="projectUser" class="front-btn-group collapse" data-toggle="buttons">
                        <label class="btn btn-default active" data-group="all" style="box-shadow: none">
                            <input type="radio" name="options" autocomplete="off" checked=""><span class="glyphicon glyphicon-user"></span>&nbsp;全部
                        </label>
                        <label class="btn btn-default" data-group="manager" style="box-shadow: none">
                            <input type="radio" name="options" autocomplete="off" checked=""><span class="glyphicon glyphicon-king"></span>&nbsp;负责人
                        </label>
                        <label class="btn btn-default" data-group="member" style="box-shadow: none">
                            <input type="radio" name="options" autocomplete="off" checked=""><span class="glyphicon glyphicon-pawn"></span>&nbsp;项目成员
                        </label>
                        <label class="btn btn-default" data-group="leave" style="box-shadow: none">
                            <input type="radio" name="options" autocomplete="off" checked=""><span class="glyphicon glyphicon-remove-circle"></span>&nbsp;离职人员
                        </label>
                    </div>
                    <s:if test="isAdmin == true">
                        <a class="btn btn-primary pull-right" data-toggle="front-modal" data-size="modal-lg" data-href="views/modal/select_people_model.jsp" id="addProjectMember" >添加成员</a>
                        <input type="hidden" id="selected-people-id" class="form-control" value="">
                    </s:if>

                </div>
                <div id="allList" class="panel panel-default front-panel" style="margin-bottom:20px;margin-top: 10px;">
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
                            <s:iterator value="userList" id="user">
                                <tr><td><div class="media"><div class="media-left">
                                    <img class="media-object img-circle  img-avatar-50" src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#user.avatar" />" alt="avatar" onerror="this.src='statics/images/user.png'"/>
                                </div><div class="media-body">
                                    <h5 class="media-heading"><s:property value="#user.name" />

                                        <span class="pull-right">
												 <s:if test=" isAdmin == true">
                                                     <a href="javascript:addAdmin(<s:property value="#user.userid" />)">设为负责人</a>
                                                     <a href="javascript:setLeave(<s:property value="#user.userid" />)">设为离职</a>
                                                     <a href="javascript:deleteUser(<s:property value="#user.userid" />)">删除</a>
                                                 </s:if>
														</span>
                                    </h5></div></div></td></tr>
                            </s:iterator>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div id="ManagerList" class="panel panel-default front-panel" style="margin-bottom:20px;margin-top: 10px;display: none;">
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
                <div id="UserList" class="panel panel-default front-panel" style="margin-bottom:20px;margin-top: 10px;display: none;">
                    <s:if test="userList.size==0"> <%--用于判断项目成员是否为空--%>
                        <div class="panel-body">
                            <h4>这个项目还没有人员⊙﹏⊙</h4>
                        </div>
                    </s:if>
                    <s:else>
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
                                                     <a href="javascript:setLeave(<s:property value="#user.userid" />)">设为离职</a>
                                                     <a href="javascript:deleteUser(<s:property value="#user.userid" />)">删除</a>
                                                 </s:if>
														</span>
                                    </h5></div></div></td></tr>
                            </s:iterator>
                            </tbody>
                        </table>
                    </div>
                    </s:else>
                </div>
                <div id="LeaveList" class="panel panel-default front-panel" style="margin-bottom:20px;margin-top: 10px;display: none;">
                    <s:if test="leaveList.size==0"> <%--用于判断离职成员是否为空--%>
                        <div class="panel-body">
                            <h4>这个项目还没有离职人员⊙﹏⊙</h4>
                        </div>
                    </s:if>
                    <s:else>
                        <div class="panel-body front-no-padding">
                            <table class="table table-striped front-table" style="margin-bottom: 0px">
                                <tbody>
                                <s:iterator value="leaveList" id="user">
                                    <tr><td><div class="media"><div class="media-left">
                                        <img class="media-object img-circle  img-avatar-50" src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#user.avatar" />" alt="avatar" onerror="this.src='statics/images/user.png'"/>
                                    </div><div class="media-body">
                                        <h5 class="media-heading"><s:property value="#user.name" />

                                            <span class="pull-right">
												 <s:if test=" isAdmin == true">
                                                     <a href="javascript:setOnProject(<s:property value="#user.userid" />)">上岗</a>
                                                     <a href="javascript:deleteUser(<s:property value="#user.userid" />)">删除</a>
                                                 </s:if>
														</span>
                                        </h5></div></div></td></tr>
                                </s:iterator>
                                </tbody>
                            </table>
                        </div>
                    </s:else>
                </div>
            </div>
            </div>



</div>
<script src="http://newfront.free4inno.com/plugin/fileupload/fileupload.min.js"></script>
<script src="http://newfront.free4inno.com/plugin/imgupload/imgupload.js"></script>
<script src="project/ProjectEdit.js"></script>
<script type="text/javascript">


    $('#projectUser').click(function (event) {
        var target = $(event.target).is('span') ? $(event.target).parent() : $(event.target);
        switch (target.attr('data-group')) {
            case 'all':
                $("#allList").css("display", "block");
                $("#ManagerList").css("display", "none");
                $("#UserList").css("display", "none");
                $("#LeaveList").css("display","none");
                break;
            case 'manager' :
                $("#allList").css("display", "none");
                $("#ManagerList").css("display", "block");
                $("#UserList").css("display", "none");
                $("#LeaveList").css("display","none");
                break;
            case "member":
                $("#allList").css("display", "none");
                $("#ManagerList").css("display", "none");
                $("#UserList").css("display", "block");
                $("#LeaveList").css("display","none");
                break;
            case "leave":
                $("#allList").css("display", "none");
                $("#ManagerList").css("display", "none");
                $("#UserList").css("display", "none");
                $("#LeaveList").css("display","block");
        }
    })
</script>
