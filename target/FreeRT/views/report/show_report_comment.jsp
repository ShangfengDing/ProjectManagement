<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="reportCommentList.size() > 0">
<div class="panel panel-default front-panel">
    <div class="panel-body front-no-padding">
        <table class="table table-striped front-table" style="margin-bottom: 0px">
            <tbody>
            <s:iterator value="reportCommentList">
            <tr>
                <td>
                    <div class="media">
                        <div class="media-left">
                            <a>
                                <img class="media-object img-circle img-avatar-50" style="width: 50px;height: 50px;" src="<%=com.free4lab.freeRT.utils.Constants.APIPrefix_AvatarAccount%>/download?uuid=<s:property value="user.avatar"/>" onerror="this.src='statics/images/user.png'">
                            </a>
                        </div>
                        <div class="media-body">
                            <h5 class="media-heading" style="position: relative">
                                <span class="front-text-title">
                                    <a><s:property value="user.name"/></a> <s:date name="time" format="yyyy-MM-dd HH:mm:ss"/>
                                </span>
                                <span class="front-top-right pull-right">
                                    <s:if test="user.userid == #session.uid">
                                        <s:if test="!description.startsWith('#操作#')">
                                            <a href="javascript:deleteComment(<s:property value="id"/>)">删除</a>
                                        </s:if>
                                    </s:if>
									<s:else>
                                        <a href="javascript:replyComment('<s:property value="user.userid"/>', '<s:property value="user.name"/>')">回复</a>
                                    </s:else>
                                </span>
                            </h5>
                            <div class="front-text-break">
                                <s:property value="description.replace(\"\n\", \"<br>\")" escape="false"/>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            </s:iterator>
            </tbody>
        </table>
        <div id="report-comment-page" class="lineheight"></div>
    </div>
</div>
</s:if>
<script>
    var divHtml = $.getDivPageHtml(<s:property value="page"/>, <s:property value="pageSum"/>, 'reportCommentDivPage');
    $('#report-comment-page').html(divHtml);
</script>
