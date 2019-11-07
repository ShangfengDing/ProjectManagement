<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="modal-header">
	<button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h4 class="modal-title">选择项目</h4>
</div>

<div class="modal-body">
	<div class="row">
		<div class="form-group col-md-12" role="search">
			<div class="input-group">
				<input type="text" id="model-search-text" class="form-control front-no-box-shadow" placeholder="请输入项目名称"/>
				<span class="input-group-btn">
					<button id="model-search-button" class="btn btn-default" type="button">搜索</button>
				</span>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-5">
			<ul class="nav nav-tabs">
				<%--<li class="active">--%>
					<%--<a href="#model-latest-project" data-toggle="tab">最近</a>--%>
				<%--</li>--%>
				<li class="active">
					<a href="#model-all-project" data-toggle="tab">全部</a>
				</li>
				<li>
					<a href="#model-search-project" data-toggle="tab" style="display:none" id="model-search-title">搜索</a>
				</li>
			</ul>
			<div class="tab-content front-tab-content">
				<%--<div id="model-latest-project" class="tab-pane active"></div>--%>
				<div id="model-all-project" class="tab-pane active"></div>
				<div id="model-search-project" class="tab-pane"></div>
			</div>
		</div>
		<div class="col-md-2">
			<img class="center-block front-center-block" src="statics/images/select_arrow.png" />
		</div>
		<div class="col-md-5">
			<a class="btn btn-default" id="model-project-num">已选：<span></span>个</a>
			<a onclick="clearSelectedProject()" class="btn btn-link pull-right">清空</a>
			<div class="tab-content front-tab-content">
				<ul id="model-selected-project"></ul>
			</div>
		</div>
	</div>
</div>

<div class="modal-footer">
	<%--<span id="freeshare-groupInfo"></span>--%>
	<a class="btn btn-default modal-close" data-dismiss="modal">取消</a>
	<a href="javascript:submitSelectedProject();" class="btn btn-primary">确定</a>
</div>

<script src="report/select_project_model.js"></script>
