<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<br>
<div class="label label-info">附件</div>
<div style="margin-top: 10px;">
    <s:iterator value="attachmentList">
    <div>
        <span><s:property value="name" /></span>
        <a href="<%=com.free4lab.freeRT.utils.Constants.APIPrefix_FreeDisk%>/download?uuid=<s:property value="uuid"/>">下载</a>
    </div>
    </s:iterator>
</div>
<%--下面这个版本有批量下载的复选框--%>
<%--<div style='height: 30px; margin-top: 15px'>--%>
    <%--<input type="checkbox" id="freeshare-allselect"/>--%>
    <%--全选 <a id="freeshare-downloadbutton" class="btn btn-info hidden btn-xs" style='margin-left: 30px;'>下载</a>--%>
<%--</div>--%>
<%--<div id="docs">--%>
    <%--<s:iterator value="attachmentList" status="st">--%>
    <%--<div class="clearfix middiv">--%>
        <%--<div class="pull-left" style="padding-left: 1px">--%>
            <%--<input type="checkbox" id='freeshare-midselect<s:property value='#st.index + 1'/>' />--%>
            <%--附件<s:property value='#st.index + 1' />：&nbsp;--%>
            <%--<s:if test="name.length() > 33">--%>
                <%--<span title="<s:property value="name"/>"><s:property value="name.substring(0,33)+'......' + name.substring(name.lastIndexOf('.'),name.length())" /></span>--%>
            <%--</s:if>--%>
            <%--<s:else>--%>
                <%--<span title="<s:property value="name"/>"><s:property value="name" /></span>--%>
            <%--</s:else>--%>
        <%--</div>--%>
        <%--<div style="float:right">--%>
            <%--<a href="<%=com.free4lab.freeRT.utils.Constants.APIPrefix_FreeDisk%>/download?uuid=<s:property value="uuid"/>">下载</a>--%>
        <%--</div>--%>
    <%--</div>--%>
        <%--<s:if test="#st.last">--%>
			<%--<span id='midlength' class='hidden'><s:property value="#st.count" /></span>--%>
        <%--</s:if>--%>
    <%--</s:iterator>--%>
<%--</div>--%>
