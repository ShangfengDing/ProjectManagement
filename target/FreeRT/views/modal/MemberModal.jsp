<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal" id="MemModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true" style="z-index:9999">×</button>
                <h4 class="modal-title">选择</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-5">
                        <ul class="nav nav-tabs" id="tab_title">
                            <li class="active">
                                <a href="#ProMember" id="ProName" data-toggle="tab"></a>
                            </li>
                        </ul>
                        <div class="tab-content front-tab-content">
                            <div class="tab-pane active" id="ProMember">
                                <div class="front-selectitem">
                                    无成员
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <img class="center-block front-center-block" src="statics/images/select_arrow.png">
                    </div>
                    <div class="col-md-5">
                        <a href="javascript:void(0)" class="btn btn-default" id="MemNum" style="margin-bottom: 2%">
                            已选：
                            <span>0</span>
                            个
                        </a>
                        <a href="javascript:void(0)" onclick="clearList()" class="btn btn-link pull-right"
                           role="clearselected">清空</a>
                        <div class="tab-content front-tab-content">
                            <ul  id="userselected" style="padding-left:0px;border-top: 1px solid #dddddd;">
                            </ul>
                            <div id="numalert" class="front-selectitem" style="color:#a94442;display:none;">最多可选择两人</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span id="freeshare-groupInfo"></span>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button onclick="submitMem();" class="btn btn-primary">确定</button>
            </div>
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>
    <script src="http://cdn.bootcss.com/bootstrap/2.3.1/js/bootstrap-transition.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/2.3.1/js/bootstrap-modal.js"></script>
    <script src="task/MemberModal.js"></script>

