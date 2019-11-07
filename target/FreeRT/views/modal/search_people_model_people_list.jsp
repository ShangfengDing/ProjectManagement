<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--添加人员或管理员时，有一个查找人员，列出搜索所得到的人员的名单--%>
<s:iterator value="resultList">
    <div class="model-people-item">
        <span data-id="<s:property value="id"/>" style="cursor:pointer"><s:property value="name"/></span>
    </div>
</s:iterator>