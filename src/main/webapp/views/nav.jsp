<%@ page import="com.free4lab.freeRT.utils.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String access_token = (String) session.getAttribute(Constants.ACC_TOKEN);
%>
<nav class="navbar navbar-default navbar-fixed-top front-nav">
    <div class="container">
        <div>
            <!-- 左侧栏移动端触发：可选  -->
            <%--<img class="nav-toggle-left" id="front-nav-toggle-left" alt="SidebarToggle"/>--%>
            <!-- -->
            <!-- 品牌图片大小为150 * 30：宽度不定，高度固定30px -->
            <div class="nav-brand"><a href="home"><img class="img-responsive" src="statics/images/logo.png" alt="FreeProject"/></a></div>
            <!-- -->
        </div>
        <!-- 导航栏菜单 -->
        <div class="nav-collapse collapse" id="nav-collapse-demo">
            <ul class="nav navbar-nav">
                <%--<li class="${param.act == "home" ? "front-active" : ""}"><a href="home">首页</a></li>--%>
                <li class="${param.act == "project" ? "front-active" : ""}"><a href="project">项目</a></li>
                <li class="${param.act == "task" ? "front-active" : ""}"><a href="task">任务</a></li>
                <li class="${param.act == "report" ? "front-active" : ""}"><a href="report">报告</a></li>
                <li class="${param.act == "message" ? "front-active" : ""}"><a href="chat">会话</a></li>
                <!-- 下拉菜单 -->
                <li class="dropdown ${param.act == 'new' ? 'front-active' : ''}">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">新建<span class="caret"></span></a>
                    <ul class="dropdown-menu nav-dropdown">
                        <li><a href="task/newtask">任务</a></li>
                        <li><a href="report/new">报告</a></li>
                    </ul>
                </li>
                <!-- end 下拉菜单 -->
            </ul>
        </div>
        <!-- end 导航栏横向菜单 -->
        <!-- 导航栏右侧区域 -->
        <div class="nav-right">
            <!-- 搜索图标：可选 -->
            <!--<div class="area area-media"><span class="glyphicon glyphicon-search nav-search-icon"></span></div>-->
            <!-- -->
            <!-- 产品导航菜单按钮 -->
            <div class="area area-media"><span class="glyphicon glyphicon-th nav-toggle-pro" data-gen="nav-pro" data-toggle="front-popover-bottom" data-target="#nav-pro-demo"></span></div>
            <!-- -->
            <!-- 导航栏用户头像  -->
            <div class="area area-avatar area-media"> <img src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#session.profile_image_url"/>" onerror="this.src='statics/images/user.png'" class="img-circle nav-avatar" alt="avatar" data-toggle="front-popover-bottom" data-target="#nav-user-demo"/></div>
            <!-- -->
            <!-- 横向导航栏移动端触发：可选 -->
            <div class="area visible-xs visible-sm nav-toggle-down" data-toggle="collapse" data-target="#nav-collapse-demo"><span class="glyphicon glyphicon-menu-hamburger" id="front-nav-toggle-down-demo"></span></div>
            <!-- -->
            <!-- 产品导航菜单 -->
            <div id="nav-pro-demo" data-pro="6"></div>
            <!-- 用户菜单 -->
            <div id="nav-user-demo" class="bottom nav-popover nav-popover-media nav-avatar-menu">
                <div class="arrow"></div>
                <ul>
                    <li class="text-center">
                        <img class="img-circle img-lg-avatar" src="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users/users/download?uuid=<s:property value="#session.profile_image_url"/>" alt="avatar" onerror="this.src='statics/images/user.png'"/>
                        <div><span><s:property value="#session.userName" /></span></div>
                        <div><small><s:property value="#session.email" /></small></div>
                    </li>
                    <li class="divider"></li><!-- 分界线 -->
                    <li class="clearfix">
                        <div class="col-xs-4 text-center">
                            <div id="person-project-number">2</div>
                            <div class="text-primary">项目</div>
                        </div>
                        <div class="col-xs-4 text-center">
                            <div id="person-task-number">0</div>
                            <div class="text-primary">任务</div>
                        </div>
                        <div class="col-xs-4 text-center">
                            <div id="person-report-number">0</div>
                            <div class="text-primary">报告</div>
                        </div>
                    </li>
                    <li class="divider"></li><!-- 分界线 -->
                    <li><a href="<%=com.free4lab.freeRT.utils.Constants.HTTPS_ACCOUNT%>users" target="_blank"><span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;账号设置</a></li>
                    <%--<li><a href="http://freeshareadmin.free4inno.com/" target="_blank"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;管理员门户</a></li>--%>
                    <s:if test="#session.uid != null">
                        <li id="logout" data-name="<%=access_token%>" index="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"%>"><a href="javascript:logout()"><span class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;退出</a></li>
                    </s:if>
                </ul>
            </div>
            <!-- end 用户菜单 -->
        </div>
        <!-- end 导航栏右侧区域 -->
    </div>
    <script src="statics/js/jquery.min.js"></script>
    <script src="nav/Nav.js"></script>
</nav>

<script>


    function logout(){
        $.ajax({
            url : "account/loginout",
            type:"post",
            dataType:"json",
            async:false,
            data:{},
            success:function(data) {
                if(typeof(com) != "undefined"){
                    alert("sdf");
                    com.xmpp.close();
                }
                if(data.code == '200'){
                    var access_token = $('#logout').data('name');
                    var index = $('#logout').attr('index');
                    $.ajax({
                        url:"http://account.free4inno.com/api/oauth2/revokeoauth2?callback=?",
                        type:"post",
                        dataType:"json",
                        data:{'access_token':access_token},
                        complete:function(result) {
                            if(typeof(com) != "undefined"){
                                com.xmpp.close();
                            }
                            location.replace(index);
                        }
                    });
                }else
                    $.tipModal('alert', 'warning', 'logout fail:'+data.message);
            },
            error:function(data){
                $.tipModal('alert', 'warning', 'logout error:'+data);
            }
        });
    }
</script>
