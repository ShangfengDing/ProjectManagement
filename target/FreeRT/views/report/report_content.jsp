<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="reportList && reportList.size() > 0">
<div class="panel panel-default front-panel">
    <div class="panel-body front-no-padding">
        <table class="table table-striped front-table" style="margin-bottom: 0px">
            <tbody>
            <s:iterator value="reportList">
                <tr>
                    <td>
                        <div class="media">
                            <div class="media-left">
                                <s:if test="state == 0 || state == 1 || state == 3">
                                    <a href="report/show?id=<s:property value="id"/>">
                                        <img class="media-object img-circle img-avatar-50" style="width: 50px;height: 50px;" src="<%=com.free4lab.freeRT.utils.Constants.APIPrefix_AvatarAccount%>/download?uuid=<s:property value="user.avatar"/>" onerror="this.src='statics/images/user.png'">
                                    </a>
                                </s:if>
                                <s:elseif test="state == 2">
                                    <a href="report/edit?id=<s:property value="id"/>">
                                        <img class="media-object img-circle img-avatar-50" style="width: 50px;height: 50px;" src="<%=com.free4lab.freeRT.utils.Constants.APIPrefix_AvatarAccount%>/download?uuid=<s:property value="user.avatar"/>" onerror="this.src='statics/images/user.png'">
                                    </a>
                                </s:elseif>

                            </div>
                            <div class="media-body">
                                <h5 class="media-heading" style="position: relative">
                                    <span class="front-text-title">
                                        <s:if test="state == 0 || state == 1 || state == 3">
                                            <a href="report/show?id=<s:property value="id"/>" style="text-decoration: none"><s:property value="name"/></a>
                                        </s:if>
                                        <s:elseif test="state == 2">
                                            <a href="report/edit?id=<s:property value="id"/>" style="text-decoration: none"><s:property value="name"/></a>
                                        </s:elseif>
                                        <s:iterator value="reportPermissions">
                                            <a href="task/viewtask?projectId=<s:property value="project.id"/>" style="text-decoration: none">
                                                <span class="badge"><s:property value="project.name"/></span>
                                            </a>
                                        </s:iterator>
                                    </span>
                                    <span class="front-top-right pull-right">
                                        <s:if test="user.userid == #session.uid">
                                            <s:if test="state == 0">
                                                <a href="report/show?id=<s:property value="id"/>">查看</a>
                                                <a href="report/edit?id=<s:property value="id"/>">编辑</a>
                                                <a href="javascript:void(0);" onclick="trashReport(<s:property value="id"/>)">删除</a>
                                            </s:if>
                                            <s:elseif test="state == 1 || state == 3">
                                                <a href="report/show?id=<s:property value="id"/>">查看</a>
                                                <a href="javascript:void(0);" onclick="revertReport(<s:property value="id"/>)">撤销删除</a>
                                                <a href="javascript:void(0);" onclick="deleteReport(<s:property value="id"/>)">彻底删除</a>
                                            </s:elseif>
                                            <s:elseif test="state == 2">
                                                <a href="report/edit?id=<s:property value="id"/>">编辑</a>
                                                <a href="javascript:void(0);" onclick="submitDraftReport(<s:property value="id"/>)">提交</a>
                                                <a href="javascript:void(0);" onclick="trashReport(<s:property value="id"/>)">删除</a>
                                            </s:elseif>
                                        </s:if>
                                    </span>
                                </h5>
                                <div class="front-text-break">
                                    <s:date name="time" format="yyyy-MM-dd HH:mm:ss"/>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
        <div id="report-page" class="lineheight"></div>
    </div>
</div>
</s:if>
<s:else>
<p class="text-left">
<p>目前没有报告</p>
</s:else>
<script>
    $('#report-page').html($.getDivPageHtml(<s:property value="page"/>, <s:property value="pageSum"/>, 'reportDivPage'));
</script>
<script src="report/report_content.js"></script>
