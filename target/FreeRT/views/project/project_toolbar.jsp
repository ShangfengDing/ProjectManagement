<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="projectList.size() > 0">
    <button type="button" class="btn btn-default dropdown-toggle front-no-box-shadow active" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="margin-right: 4px">
        <span id="project-buttons-dropdown-title">全部项目 <span class="caret"></span></span>
    </button>
    <ul class="dropdown-menu">
        <li><a data-group="0" href="">全部项目</a></li>
        <li role="separator" class="divider"></li>
        <s:iterator value="projectList">
        <li><a id="project-buttons-dropdown<s:property value="id"/>" data-group="<s:property value="id"/>" href=""><s:property value="name"/></a></li>
        </s:iterator>
    </ul>
    <%--<s:if test="draftBox"><button type="button" class="btn btn-default front-no-box-shadow" data-group="draftBox">草稿箱</button></s:if>--%>
    <%--<s:if test="trashCan"><button type="button" class="btn btn-default front-no-box-shadow" data-group="trashCan">回收站</button></s:if>--%>
    <%--<s:if test="list"><button type="button" class="btn btn-default front-no-box-shadow" data-group="list"><span class="glyphicon glyphicon-list"></span>列表</button></s:if>--%>
    <%--<s:if test="calendar"><button type="button" class="btn btn-default front-no-box-shadow" data-group="calendar"><span class="glyphicon glyphicon-calendar"></span>日历</button></s:if>--%>
</s:if>
