<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<s:iterator value="projectList">
<div class="model-project-item">
	<span data-id="<s:property value="id"/>"><s:property value="name"/></span>
</div>
</s:iterator>
