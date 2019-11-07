<%--
  Created by IntelliJ IDEA.
  User: benlotelli
  Date: 2019/4/7
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

    <div class="fixed-table-container" style="">
        <div class="fixed-table-header" style="display: block;">
            <table  class="table table-hover table-bordered" style="margin-top: -81px; background-color: white">
                 <%--<thead class="row">--%>
                     <tr>
                         <th style="text-align: center; vertical-align: middle; " class="col-md-1 col-lg-1">
                             <div class="th-inner sortable both ">星标</div>
                         </th>
                        <th style="text-align: center; vertical-align: middle; " class="col-md-5 col-lg-5">
                            <div class="th-inner sortable both ">文件说明</div>
                         </th>
                        <%--<th style="text-align: center; vertical-align: middle;" class="col-md-4 col-lg-4">--%>
                            <%--<div class="th-inner sortable both">描述</div>--%>
                         <%--</th>--%>
                         <th style="text-align: center; vertical-align: middle;" class="col-md-2 col-lg-2">
                             <div class="th-inner sortable both">时间</div>
                         </th>
                        <th style="text-align: center; vertical-align: middle;" class="col-md-2 col-lg-2">
                            <div class="th-inner sortable both">创建人</div>
                         </th>
                        <th style="text-align: center; vertical-align: middle;" class="col-md-2 col-lg-2">
                         <div class="th-inner">操作</div>
                         </th>
                    </tr>
                <%--</thead>--%>
<%--<script>--%>
    <%--alert(fileList.size());--%>
<%--</script>--%>
<s:if test="fileList.size() > 0">
    <tbody>
                <s:iterator value="fileList">
                    <tr>
                    <%--<td style="text-align: center; vertical-align: middle; ">--%>
                        <%--<a href="javascript:return false;" ><s:property value="realname.replace(\"\n\", \"<br>\")" escape="false"/></a>--%>
                    <%--</td>--%>
                    <td style="text-align: center; ">
                            <s:if test="star ==1">
                                <a class="blankspace" style="cursor: pointer" onclick="starFile(<s:property value="id"/>)">
                                    <span class="glyphicon glyphicon-star" style="font-size: 20px; vertical-align: middle"></span>
                                </a>
                            </s:if>
                            <s:elseif test="star != 1">
                                <a class="blankspace" style="cursor: pointer" onclick="starFile(<s:property value="id"/>)">
                                    <span class="glyphicon glyphicon-star-empty" style="font-size: 20px; vertical-align: middle"></span>
                                </a>
                            </s:elseif>

                    </td>
                    <td style="text-align: center;vertical-align: middle ">
                        <s:property value="description.replace(\"\n\", \"<br>\")" escape="false"/>
                    </td>
                    <td style="text-align: center;vertical-align: middle ">
                        <s:date name="time" format="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td style="text-align: center;vertical-align: middle ">
                        <s:property value="user.name"/>
                    </td>
                    <td style="text-align: center; vertical-align: middle">
                        <s:if test="source == 1">
                            <a class="download" href="<s:property value="url"/>" title="download">下载</a>
                            <%--<a class="download" href="javascript:downloadFile('<s:property value="source"/>','<s:property value='url'/>')" title="donwload">下载</a>--%>
                        </s:if>
                        <a class="edit" href="javascript:fileEdit('<s:property value="id"/>','<s:property value="description"/>','<s:property value="url.substring(44)"/>')" title="edit">编辑</a>
                        <a class="remove" href="javascript:deleteFile(<s:property value="id"/> )" title="Remove">删除</a>
                    </td>
                    </tr>
                </s:iterator>
                </tbody>
             </table>
            <div id="file-page" class="lineheight"></div>
         </div>
    </div>
</s:if>
<script>
    var divHtml = $.getDivPageHtml(<s:property value="page"/>, <s:property value="pageSum"/>, 'fileDivPage');
    $('#file-page').html(divHtml);
</script>