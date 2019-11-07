<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--添加人员或管理员时，返回的所有人的名字--%>
<s:iterator value="userList">
    <div class="model-people-item">
        <span data-id="<s:property value="id"/>" style="cursor:pointer"><s:property value="name"/></span>
    </div>
</s:iterator>