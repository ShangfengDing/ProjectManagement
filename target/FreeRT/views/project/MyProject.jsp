<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
	<%@include file="../../statics/head.html"%>
	<style>
		@media (min-width: 1024px){
			#xiangyingshi {
				height: 0px;
				padding-bottom: 40%;
			}
			.specialset {width: 190px}
		}
		@media (min-width: 768px) and (max-width: 1024px){
			#xiangyingshi {
				height: 0px;
				padding-bottom: 45%;
			}
			.specialset {width: 140px}
		}
		@media (min-width: 501px) and (max-width: 768px){
			#xiangyingshi {
				height: 0px;
				padding-bottom: 50%;
			}
			.specialset {width: 580px}
		}
		@media (max-width: 500px){
			#xiangyingshi {
				height: 0px;
				padding-bottom: 50%;
			}
			.specialset {width: 210px}
		}

	</style>
	<style type="text/css">
		.box2{}
		.box2 a{color:#333; text-decoration:none;border:2px solid #FFF; float:left; cursor:hand;}
		.box2 a span{display:none; color:#2b2b2b;font-family:"微软雅黑";
			font-size: 4px;}
		.box2 a:hover{color: #ffffff; border:2px solid #ffffff; }
		.box2 a:hover span{display:inline; position:absolute;}
	</style>
	<title>我的项目 - 轻项目</title>
	<script>
        function autoIcon(name){
            name = name.replace("/","'/'");
            var imgName = "imgIcon"+name;
            var iconName = "glIcon"+name;
            var circle = "circle"+name;

            var A = name.charCodeAt(0);
            var B = name.charCodeAt(1);
            var resultIcon = (A + B) % 15;
            var resultColo = (A + B) % 10;
            var iconType = "cloud";
            var iconColo = "rgb(255,145,0)";
            switch(resultIcon){
                case 0:
                    iconType = "asterisk";
                    break;
                case 1:
                    iconType = "cloud";
                    break;
                case 2:
                    iconType = "inbox";
                    break;
                case 3:
                    iconType = "book";
                    break;
                case 4:
                    iconType = "gift";
                    break;
                case 5:
                    iconType = "leaf";
                    break;
                case 6:
                    iconType = "fire";
                    break;
                case 7:
                    iconType = "road";
                    break;
                case 8:
                    iconType = "eye-open";
                    break;
                case 9:
                    iconType = "piggy-bank";
                    break;
                case 10:
                    iconType = "tower";
                    break;
                case 11:
                    iconType = "king";
                    break;
                case 12:
                    iconType = "queen";
                    break;
                case 13:
                    iconType = "grain";
                    break;
                case 14:
                    iconType = "sunglasses";
                    break;
            }
            switch(resultColo){
                case 0:
                    iconColo = "#b7191d"
                    break;
                case 1:
                    iconColo = "#890e4f"
                    break;
                case 2:
                    iconColo = "#4a148c"
                    break;
                case 3:
                    iconColo = "#0e47a1"
                    break;
                case 4:
                    iconColo = "#1c5e20"
                    break;
                case 5:
                    iconColo = "#1d88e6"
                    break;
                case 6:
                    iconColo = "#3e50b4"
                    break;
                case 7:
                    iconColo = "#2f7d32"
                    break;
                case 8:
                    iconColo = "#ff6f00"
                    break;
                case 9:
                    iconColo = "#459f47"
                    break;
            }
            iconType = "glyphicon glyphicon-"+iconType;
            $("."+imgName).css({"display":"none"});
            $("."+circle).css({"display": "block","backgroundColor":iconColo});
            $("."+iconName).css({"display": "block"});
            $("."+iconName).attr("class", iconType)


            // $("#"+iconName).replaceWith("<span style=\"display:block; color: "+iconColo+";\" class=\"glyphicon glyphicon-"+iconType+"\"></span>");
            //$("#"+iconName).replaceWith("<span style=\"display:block; color: "+iconColo+";\" class=\"glyphicon glyphicon-"+iconType+"\"></span>");
        }
	</script>
</head>
<body class="front-body">
<s:include value="../nav.jsp?act=project">
</s:include>
<div class="front-inner front-inner-media">
	<div class="container">
		<div class="front-toolbar other" style="padding-left: 10px;padding-right: 0px">
			<div class="front-toolbar-header clearfix">
				<button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#project-buttons">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
			</div>
			<div id="my_project" class="front-btn-group collapse" data-toggle="buttons">
				<label class="btn btn-default front-no-box-shadow active" data-group="onging">
					<input type="radio" name="options" autocomplete="off">进行中
				</label>
				<label class="btn btn-default front-no-box-shadow"  data-group="suspend">
					<input type="radio" name="options" autocomplete="off">已暂停
				</label>
				<label class="btn btn-default front-no-box-shadow" data-group="complete">
					<input type="radio" name="options" autocomplete="off">已完成
				</label>
				<label class="btn btn-default front-no-box-shadow" data-group="manage">
					<input type="radio" name="options" autocomplete="off">我管理的
				</label>
				<label class="btn btn-default front-no-box-shadow" data-group="all">
					<input type="radio" name="options" autocomplete="off">全部
				</label>
				<label class="btn btn-default front-no-box-shadow" data-group="search">
					<input type="radio" name="options" autocomplete="off"><span class="glyphicon glyphicon-search"></span> 搜索
				</label>
			</div>
			<a type="button" class="btn btn-primary" href="project/new">新建项目</a>
		</div>
		<!-- 搜索框 -->
		<div id="project-search" style="display:none;padding-top: 8px">
			<div style="padding-left: 15px">
				<div class="panel panel-default front-panel" style="margin-bottom: 10px;">
					<div class="panel-heading">项目搜索</div>
					<div class="panel-body">
						<div class="form-horizontal">
							<div class="" >
								<div class="col-md-9" >
									<input id="key" placeholder="搜索项目名称和项目描述" onkeydown="EnterPress(event)" class="form-control front-no-radius front-no-box-shadow ">
								</div>
								<div class="pull-right" >
									<button type="button" id="reset-button" class="btn btn-default" onclick="cancelSearch()">重置</button>
									<button type="button" id="search-button" type="text" class="btn btn-primary" onclick="searchProject()">查找</button>
								</div>

							</div>

						</div>
					</div>
				</div>
				<div id="loadingstyle" style="display: none;">
					<div class="front-loading">
						<div class="front-loading-block"></div>
						<div class="front-loading-block"></div>
						<div class="front-loading-block"></div>
					</div>
					<div class="panel-body text-center">正在加载请稍候</div>
				</div>
			</div>

			<div id="showresult" style="display: none;padding-left: 0px;padding-right: 0px">

			</div>
		</div>
		<div class="row">
			<div class="col-md-10">
				<div id="project-all" style="display:none">
					<s:iterator value="AllHotValueList" var="plist">
						<div class="col-md-4" style="padding-left: 10px;padding-right: 0px">
							<div class="panel panel-default front-panel">
								<div class="panel-body xiangyingshi">
									<div class="media">
										<div class="media-left">
											<div class="imgIcon<s:property value="#plist.key.id"/>" style="display:block">
												<img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.key.avatar"/>"
													  onerror="autoIcon('<s:property value="#plist.key.id"/>')" onclick="window.location.href='info?id=<s:property value="#plist.key.id" />&&type=survey'">
											</div>

											<div class="circle<s:property value="#plist.key.id"/>" style="width: 50px; height: 50px; border-radius: 25px; display: none; background-color: white;position:relative;">
												<span class="glIcon<s:property value="#plist.key.id"/>" style="display:block; font-size: 25px; width: 25px; height: 25px; color: white;margin: auto;  position: absolute;  top: 5px; left: 0; bottom: 0; right: 0;" class="glyphicon glyphicon-asterisk"></span>
											</div>

												<%--<div style="font-size: 50px">--%>
												<%--<span id="glIcon<s:property value="#plist.key.name"/>" style="display:none" class="glyphicon glyphicon-asterisk"></span>--%>
												<%--</div>--%>

										</div>

										<div class="media-body specialset">
											<h4 class="media-heading specialset">
												<span class="front-text-title" style="height: 20px">
	                                    		<a herf="#" class="textlen-control"  href="info?id=<s:property value="#plist.key.id" />&&type=survey"><s:property value="#plist.key.name" /></a>
	                                    		<div>
												<s:if test='%{#plist.key.state == "1"}'><span class="badge">进行中</span>
												</s:if>
												<s:if test='%{#plist.key.state == "2"}'><span class="badge">已暂停</span>
												</s:if>
												<s:if test='%{#plist.key.state == "0"}'><span class="badge">已完成</span>
												</s:if>
													<%--<span>--%>
													<%--<img src="statics/images/hotpoint.png" title="热力值：项目本周活跃程度&#10;热力值计算规则如下：热力值按百分制计算&#10;每提交一份周报加10分&#10;每完成一项任务加3分&#10;每新建一个任务加1分&#10;每增加一条周报评论加1分"/>--%>
													<%--</span>--%>
													<%--<span class="showHotValue"><s:property value="#plist.value"/></span>--%>
                                                    <span class="badge" style="background-color: #8a6d3b">热度<s:property value="#plist.value"/></span>
												</div>
												</span>
											</h4>
											<div class="as-desc">
												<div style="display: inline-block;height:0px;padding-bottom:30%">
													<s:property value="#plist.key.description.substring(0,35) + '......'" />
												</div>
											</div>
										</div>
										<div class="media-left">
											<a  class="blankspace pull-right" href="message/group?projectId=<s:property value="#plist.key.id" />" target="_blank"><span class="glyphicon glyphicon-comment"></span></a>
										</div>
										<div align="right">
											<a  class="blankspace" id="showdetail-all<s:property value="#plist.key.id" />" href="info?id=<s:property value="#plist.key.id" />&&type=survey"> 详情</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=user"> 成员</a>&nbsp;
											<a  class="blankspace" href="task/viewtask?projectId=<s:property value="#plist.key.id" />"> 任务</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=report"> 报告</a>&nbsp;
											<a  class="blankspace" href="file/viewfile?id=<s:property value="#plist.key.id" />"> 文件</a>&nbsp;
										</div>
									</div>
								</div>
							</div>
						</div>
					</s:iterator>
				</div>
				<div id="project-onging" style="display:block">
					<s:iterator value="LivingHotValueList" var="plist" >
						<div class="col-md-4" style="padding-left: 10px;padding-right: 0px">
							<div class="panel panel-default front-panel" style="margin-bottom: 10px;">
								<div class="panel-body xiangyingshi">
									<div class="media">
										<div class="media-left">
											<div class="imgIcon<s:property value="#plist.key.id"/>" style="display:block">
												<img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.key.avatar"/>"
													  onerror="autoIcon('<s:property value="#plist.key.id"/>')" onclick="window.location.href='info?id=<s:property value="#plist.key.id" />&&type=survey'">
											</div>

											<div class="circle<s:property value="#plist.key.id"/>" style="width: 50px; height: 50px; border-radius: 25px; display: none; background-color: white;position:relative;">
												<span class="glIcon<s:property value="#plist.key.id"/>" style="display:block; font-size: 25px; width: 25px; height: 25px; color: white;margin: auto;  position: absolute;  top: 5px; left: 0; bottom: 0; right: 0;" class="glyphicon glyphicon-asterisk"></span>
											</div>

												<%--<div style="font-size: 50px">--%>
												<%--<span id="glIcon<s:property value="#plist.key.name"/>" style="display:none" class="glyphicon glyphicon-asterisk"></span>--%>
												<%--</div>--%>

										</div>

										<div class="media-body specialset">
											<h4 class="media-heading specialset">
												<span class="front-text-title" style="height: 20px">
	                                    		<a herf="#" class="textlen-control" href="info?id=<s:property value="#plist.key.id" />&&type=survey"><s:property value="#plist.key.name" /></a>
												<div>
	                                    		<s:if test='%{#plist.key.state == "1"}'><span class="badge" style="display: inline-block">进行中</span>
												</s:if>
												<s:if test='%{#plist.key.state == "2"}'><span class="badge">已暂停</span>
												</s:if>
												<s:if test='%{#plist.key.state == "0"}'><span class="badge">已完成</span>
												</s:if>
														<%--<span>--%>
														<%--<img src="statics/images/hotpoint.png" title="热力值：项目本周活跃程度&#10;热力值计算规则如下：热力值按百分制计算&#10;每提交一份周报加10分&#10;每完成一项任务加3分&#10;每新建一个任务加1分&#10;每增加一条周报评论加1分"/>--%>
														<%--</span>--%>
														<%--<span class="showHotValue1"><s:property value="#plist.value"/></span>--%>
                                                        <span class="badge" style="background-color: #8a6d3b">热度<s:property value="#plist.value"/></span>
												</div>
	                                			</span>
											</h4>
											<div class="as-desc">
												<div style="display: inline-block" style="height:0px;padding-bottom:30%">
													<s:property value="#plist.key.description.substring(0,35) + '......'" />
												</div>
											</div>
										</div>
										<div class="media-left">
											<a  class="blankspace pull-right" href="message/group?projectId=<s:property value="#plist.key.id" />" target="_blank"><span class="glyphicon glyphicon-comment"></span></a>
										</div>
										<div align="right">
											<a  class="blankspace" id="showdetail-all<s:property value="#plist.key.id" />" href="info?id=<s:property value="#plist.key.id" />&&type=survey"> 详情</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=user"> 成员</a>&nbsp;
											<a  class="blankspace" href="task/viewtask?projectId=<s:property value="#plist.key.id" />"> 任务</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=report"> 报告</a>&nbsp;
											<a  class="blankspace" href="file/viewfile?id=<s:property value="#plist.key.id" />"> 文件</a>&nbsp;
										</div>
									</div>
								</div>
							</div>
						</div>
					</s:iterator>
				</div>
				<div id="project-suspend" style="display:none">
					<s:iterator value="SuspendHotValueList" var="plist">
						<div class="col-md-4" style="padding-left: 10px;padding-right: 0px">
							<div class="panel panel-default front-panel">
								<div class="panel-body xiangyingshi">
									<div class="media">
										<div class="media-left">
											<div class="imgIcon<s:property value="#plist.key.id"/>" style="display:block">
												<img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.key.avatar"/>"
													  onerror="autoIcon('<s:property value="#plist.key.id"/>')" onclick="window.location.href='info?id=<s:property value="#plist.key.id" />&&type=survey'">
											</div>

											<div class="circle<s:property value="#plist.key.id"/>" style="width: 50px; height: 50px; border-radius: 25px; display: none; background-color: white;position:relative;">
												<span class="glIcon<s:property value="#plist.key.id"/>" style="display:block; font-size: 25px; width: 25px; height: 25px; color: white;margin: auto;  position: absolute;  top: 5px; left: 0; bottom: 0; right: 0;" class="glyphicon glyphicon-asterisk"></span>
											</div>

												<%--<div style="font-size: 50px">--%>
												<%--<span id="glIcon<s:property value="#plist.key.name"/>" style="display:none" class="glyphicon glyphicon-asterisk"></span>--%>
												<%--</div>--%>

										</div>

										<div class="media-body specialset">
											<h4 class="media-heading specialset">
												<span class="front-text-title" style="height: 20px">
	                                    		<a herf="#" class="textlen-control" href="info?id=<s:property value="#plist.key.id" />&&type=survey"><s:property value="#plist.key.name" /></a>
												<div>
												<s:if test='%{#plist.key.state == "1"}'><span class="badge" >进行中</span>
												</s:if>
												<s:if test='%{#plist.key.state == "2"}'><span class="badge">已暂停</span>
												</s:if>
												<s:if test='%{#plist.key.state == "0"}'><span class="badge">已完成</span>
												</s:if>
													<%--<span>--%>
													<%--<img src="statics/images/hotpoint.png" title="热力值：项目本周活跃程度&#10;热力值计算规则如下：热力值按百分制计算&#10;每提交一份周报加10分&#10;每完成一项任务加3分&#10;每新建一个任务加1分&#10;每增加一条周报评论加1分"/>--%>
												    <%--</span>--%>
												    <%--<span class="showHotValue2"><s:property value="#plist.value"/></span>--%>
                                                    <span class="badge" style="background-color: #8a6d3b">热度<s:property value="#plist.value"/></span>
												</div>
												</span>
											</h4>
											<div class="as-desc" style="height:0px;padding-bottom:30%">
												<div style="display: inline-block">
													<s:property value="#plist.key.description.substring(0,35) + '......'"  />
												</div>
											</div>
										</div>
										<div class="media-left">
											<a  class="blankspace pull-right" href="message/group?projectId=<s:property value="#plist.key.id" />" target="_blank"><span class="glyphicon glyphicon-comment"></span></a>
										</div>
										<div align="right">
											<a  class="blankspace" id="showdetail-all<s:property value="#plist.key.id" />" href="info?id=<s:property value="#plist.key.id" />&&type=survey"> 详情</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=user"> 成员</a>&nbsp;
											<a  class="blankspace" href="task/viewtask?projectId=<s:property value="#plist.key.id" />"> 任务</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=report"> 报告</a>&nbsp;
											<a  class="blankspace" href="file/viewfile?id=<s:property value="#plist.key.id" />"> 文件</a>&nbsp;
										</div>
									</div>
								</div>
							</div>
						</div>
					</s:iterator>
				</div>
				<div id="project-complete" style="display:none">
					<s:iterator value="CompleteHotValueList" var="plist">
						<div class="col-md-4" style="padding-left: 10px;padding-right: 0px">
							<div class="panel panel-default front-panel">
								<div class="panel-body xiangyingshi">
									<div class="media">
										<div class="media-left">
											<div class="imgIcon<s:property value="#plist.key.id"/>" style="display:block">
												<img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.key.avatar"/>"
													  onerror="autoIcon('<s:property value="#plist.key.id"/>')" onclick="window.location.href='info?id=<s:property value="#plist.key.id" />&&type=survey'">
											</div>

											<div class="circle<s:property value="#plist.key.id"/>" style="width: 50px; height: 50px; border-radius: 25px; display: none; background-color: white;position:relative;">
												<span class="glIcon<s:property value="#plist.key.id"/>" style="display:block; font-size: 25px; width: 25px; height: 25px; color: white;margin: auto;  position: absolute;  top: 5px; left: 0; bottom: 0; right: 0;" class="glyphicon glyphicon-asterisk"></span>
											</div>

												<%--<div style="font-size: 50px">--%>
												<%--<span id="glIcon<s:property value="#plist.key.name"/>" style="display:none" class="glyphicon glyphicon-asterisk"></span>--%>
												<%--</div>--%>

										</div>

										<div class="media-body specialset">
											<h4 class="media-heading specialset">
												<span class="front-text-title" style="height: 20px">
	                                    		<a herf="#" class="textlen-control" href="info?id=<s:property value="#plist.key.id" />&&type=survey" ><s:property value="#plist.key.name" /></a>
												<div>
	                                    		<s:if test='%{#plist.key.state == "1"}'><span class="badge" >进行中</span>
												</s:if>
												<s:if test='%{#plist.key.state == "2"}'><span class="badge">已暂停</span>
												</s:if>
												<s:if test='%{#plist.key.state == "0"}'><span class="badge">已完成</span>
												</s:if>
													<%--<span>--%>
                                                    <%--<img src="statics/images/hotpoint.png" title="热力值：项目本周活跃程度&#10;热力值计算规则如下：热力值按百分制计算&#10;每提交一份周报加10分&#10;每完成一项任务加3分&#10;每新建一个任务加1分&#10;每增加一条周报评论加1分"/>--%>
	                                			    <%--</span>--%>
													<%--<span class="showHotValue3"><s:property value="#plist.value"/></span>--%>
                                                    <span class="badge" style="background-color: #8a6d3b">热度<s:property value="#plist.value"/></span>
												</div>
												</span>
											</h4>
											<div class="as-desc">
												<div style="display: inline-block" style="height:0px;padding-bottom:30%">
													<s:property value="#plist.key.description.substring(0,35) + '......'" />
												</div>
											</div>
										</div>
										<div class="media-left">
											<a  class="blankspace pull-right" href="message/group?projectId=<s:property value="#plist.key.id" />" target="_blank"><span class="glyphicon glyphicon-comment"></span></a>
										</div>
										<div align="right">
											<a  class="blankspace" id="showdetail-all<s:property value="#plist.key.id" />" href="info?id=<s:property value="#plist.key.id" />&&type=survey"> 详情</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=user"> 成员</a>&nbsp;
											<a  class="blankspace" href="task/viewtask?projectId=<s:property value="#plist.key.id" />"> 任务</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=report"> 报告</a>&nbsp;
											<a  class="blankspace" href="file/viewfile?id=<s:property value="#plist.key.id" />"> 文件</a>&nbsp;
										</div>
									</div>
								</div>
							</div>
						</div>
					</s:iterator>
				</div>
				<div id="project-manage" style="display:none">
					<s:iterator value="ManageHotValueList" var="plist">
						<div class="col-md-4" style="padding-left: 10px;padding-right: 0px">
							<div class="panel panel-default front-panel">
								<div class="panel-body xiangyingshi">
									<div class="media">
										<div class="media-left">
											<div class="imgIcon<s:property value="#plist.key.id"/>" style="display:block">
												<img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.key.avatar"/>"
													  onerror="autoIcon('<s:property value="#plist.key.id"/>')" onclick="window.location.href='info?id=<s:property value="#plist.key.id" />&&type=survey'">
											</div>

											<div class="circle<s:property value="#plist.key.id"/>" style="width: 50px; height: 50px; border-radius: 25px; display: none; background-color: white;position:relative;">
												<span class="glIcon<s:property value="#plist.key.id"/>" style="display:block; font-size: 25px; width: 25px; height: 25px; color: white;margin: auto;  position: absolute;  top: 5px; left: 0; bottom: 0; right: 0;" class="glyphicon glyphicon-asterisk"></span>
											</div>

												<%--<div style="font-size: 50px">--%>
												<%--<span id="glIcon<s:property value="#plist.key.name"/>" style="display:none" class="glyphicon glyphicon-asterisk"></span>--%>
												<%--</div>--%>

										</div>

										<div class="media-body specialset">
											<h4 class="media-heading specialset">
												<span class="front-text-title" style="height: 20px">
	                                    		<a herf="#" class="textlen-control" href="info?id=<s:property value="#plist.key.id" />&&type=survey"><s:property value="#plist.key.name" /></a>
												<div>
	                                    		<s:if test='%{#plist.key.state == "1"}'><span class="badge" >进行中</span>
												</s:if>
												<s:if test='%{#plist.key.state == "2"}'><span class="badge">已暂停</span>
												</s:if>
												<s:if test='%{#plist.key.state == "0"}'><span class="badge">已完成</span>
												</s:if>
													<%--<span>--%>
                                                    <%--<img src="statics/images/hotpoint.png" title="热力值：项目本周活跃程度&#10;热力值计算规则如下：热力值按百分制计算&#10;每提交一份周报加10分&#10;每完成一项任务加3分&#10;每新建一个任务加1分&#10;每增加一条周报评论加1分"/>--%>
										            <%--</span>--%>
													<%--<span class="showHotValue4"><s:property value="#plist.value"/></span>--%>
                                                    <span class="badge" style="background-color: #8a6d3b">热度<s:property value="#plist.value"/></span>
												</div>
												</span>
											</h4>
											<div class="as-desc" >
												<div style="display: inline-block" style="height:0px;padding-bottom:30%">
													<s:property value="#plist.key.description.substring(0,35) + '......'" />
												</div>
											</div>
										</div>
										<div class="media-left">
											<a  class="blankspace pull-right" href="message/group?projectId=<s:property value="#plist.key.id" />" target="_blank"><span class="glyphicon glyphicon-comment"></span></a>
										</div>
										<div align="right">
											<a  class="blankspace" id="showdetail-all<s:property value="#plist.key.id" />" href="info?id=<s:property value="#plist.key.id" />&&type=survey"> 详情</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=user"> 成员</a>&nbsp;
											<a  class="blankspace" href="task/viewtask?projectId=<s:property value="#plist.key.id" />"> 任务</a>&nbsp;
											<a  class="blankspace" href="info?id=<s:property value="#plist.key.id" />&&type=report"> 报告</a>&nbsp;
											<a  class="blankspace" href="file/viewfile?id=<s:property value="#plist.key.id" />"> 文件</a>&nbsp;
										</div>
									</div>
								</div>
							</div>
						</div>
					</s:iterator>
				</div>
			</div>
			<div class="col-md-2">
				<div id="project-sort" style="display:block">
					<div class="panel panel-default front-panel" >
						<div class="panel-body" >
							<div style="font-weight: bolder;text-align: center;padding-bottom: 5px">本周项目热度排行</div>
							<s:iterator value="AllProjectList" var="plist" status="status">
								<div>
									 <span>
										 <s:property value="%{#status.count}"/>:<a herf="#" style="font-size: smaller;" href="info?id=<s:property value="#plist.key.id" />&&type=survey"><s:property value="#plist.key.name" /></a>
										 <s:if test='%{#status.count <= "3"}'>
											 (<s:property value="#plist.value"/>分）
										 </s:if>
									 </span>
								</div>
							</s:iterator>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<s:include value="../footer.jsp"/>
</div>

<%@include file="../../statics/script.html"%>
<script src="project/MyProject.js"></script>
<script>
    function EnterPress(e) {
        var e = e || window.event;
        if(e.keyCode == 13) {
            document.getElementById("search-button").focus();
        }
    }
    //    function showHotValue(pid, show) {
    //        $.post("project/findProjectHotValue",{
    //            pid : pid,
    //        },function (data) {
    //            show.html(data);
    //        });
    //    }
    //    for (var i = 0; i < $('.ongoing').length; i++) {
    //        var show = $($('.ongoing')[i]).find('.showHotValue1');
    //        showHotValue($($('.ongoing')[i]).data('projectid'), show);
    //	}
    //    for (var i = 0; i < $('.suspend').length; i++) {
    //        var show = $($('.suspend')[i]).find('.showHotValue2');
    //        showHotValue($($('.suspend')[i]).data('projectid'), show);
    //    }
    //    for (var i = 0; i < $('.complete').length; i++) {
    //        var show = $($('.complete')[i]).find('.showHotValue3');
    //        showHotValue($($('.complete')[i]).data('projectid'), show);
    //    }
    //    for (var i = 0; i < $('.manage').length; i++) {
    //        var show = $($('.manage')[i]).find('.showHotValue4');
    //        showHotValue($($('.manage')[i]).data('projectid'), show);
    //    }
</script>
</body>
</html>