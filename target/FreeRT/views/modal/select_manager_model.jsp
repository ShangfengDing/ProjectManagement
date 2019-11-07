<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="modal-header">
    <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">选择负责人</h4>
</div>

<div class="modal-body">
    <div class="row">
        <div class="form-group col-md-12" role="search">
            <div class="input-group">
                <input type="text" class="form-control front-no-box-shadow" placeholder=""
                       id="freeshare-searchcontent">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" id="freeshare-searchbtn">搜索</button>
                </span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-5">
            <ul class="nav nav-tabs" >
                <li>
                    <a href="#model-all-manager"  data-toggle="tab">全部</a>
                </li>
                <li>
                    <a href="#model-search-manager" data-toggle="tab" style="display:none"
                       id="freeshare-searchresult">搜索</a>
                </li>
            </ul>
            <div class="tab-content front-tab-content">
                <div class="tab-pane active" id="model-all-manager"></div>
                <div class="tab-pane" id="model-search-manager"></div>
            </div>
        </div>
        <div class="col-md-2">
            <img class="center-block front-center-block" src="statics/images/select_arrow.png">
        </div>
        <div class="col-md-5">
            <a class="btn btn-default" id="model-manager-num">已选：<span></span>个</a>
            <a onclick="clearSelectedManager()" class="btn btn-link pull-right">清空</a>
            <div class="tab-content front-tab-content">
                <ul id="model-selected-manager"></ul>
            </div>
        </div>
    </div>
</div>

<div class="modal-footer">
    <a class="btn btn-default modal-close" data-dismiss="modal">取消</a>
    <a href="javascript:submitSelectedManager();" class="btn btn-primary">确定</a>
</div>

<script src="project/select_manager_model.js"></script>
