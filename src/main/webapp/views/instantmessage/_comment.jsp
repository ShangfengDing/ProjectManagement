<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<td  class="col-lg-1 pull-left text-center" style="padding: 12px 0px 0px 0px;border-top: 0px;font-size:30px; color:rgba(51,122,183,0.7)">
    <a style="border:0px;background-color: rgba(0,0,0,0);"><span onclick="comment_message(<s:property value='conversation.id'/>)" class="glyphicon glyphicon-plus"></span></a>
</td>
<s:if test="MessageList.size() > 0">
<s:iterator value="MessageList">
    <s:if test="user.userid == #session.uid">
        <td  class="col-lg-11 pull-right commentrow" style="background-color: #e4f4fe;padding:4px;border-top:0px;border-bottom: 6px solid rgb(231,231,231)">
                <div class="media">
                    <div class="media-body">
                        <h5 class="media-heading" style="position: relative">
                            <div class="front-text-break" onclick="quote_message('<s:property value="context"/>','<s:property value="user.name"/>')">
                                <s:property value="context.replace(\"\n\", \"<br>\")" escape="false"/>
                            </div>
                        </h5>

                        <div style="font-size: 13px; margin-top: 2px">
                            <span class="front-text-title" style="color: #777">
                                                                <s:date name="time" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;
                                                            </span>
                            <span class="front-text-title" style="color: #337ab7">
                                <s:property value="user.name"/>
                            </span>
                            <span class="front-top-right pull-right">
                                <a href="javascript:void(0);" onclick="edit_message('<s:property value="id"/>','<s:property value="context"/>',2)">编辑</a>&nbsp;
                                <a href="javascript:void(0);" onclick="delete_message(<s:property value="id"/>,2)">删除</a>&nbsp;
                                <a href="javascript:show_not()">赞</a>
                                <span>(<s:property value="likenumber"/>)</span>&nbsp;
                                <a href="javascript:void(0)" onclick="star_message(<s:property value="id"/>,2)">星标</a>

                                <s:if test="star==true">
                                    <span id="star<s:property value='id'/>" class="glyphicon glyphicon-star"></span>
                                </s:if>
                                <s:else>
                                    <span id="star<s:property value='id'/>"></span>
                                </s:else>
                            </span>
                        </div>
                    </div>
                </div>
            </td>
    </s:if>
    <s:else>
        <td  class="col-lg-11 pull-right commentrow" style="background-color: #f8f8f8;padding:4px;border-top:0px;border-bottom: 6px solid rgb(231,231,231)">
            <div class="media">
                <div class="media-body">
                    <h5 class="media-heading" style="position: relative">
                        <div class="front-text-break" onclick="quote_message('<s:property value="context"/>','<s:property value="user.name"/>')">
                            <s:property value="context.replace(\"\n\", \"<br>\")" escape="false"/>
                        </div>
                    </h5>

                    <div style="font-size: 13px; margin-top: 2px; color: #777">
                        <span class="front-text-title">
                            <s:date name="time" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;
                        </span>
                        <span class="front-text-title">
                            <s:property value="user.name"/>
                        </span>
                        <span class="front-top-right pull-right">
                            <s:if test="like==false">
                                <a id="likeIt<s:property value='id'/>" href="javascript:void(0)" onclick="likeIt(<s:property value='id'/>,2);">赞</a>
                            </s:if>
                            <s:else>
                                <a id="likeIt<s:property value='id'/>" href="javascript:void(0)" onclick="likeIt(<s:property value='id'/>,2);" style="color: #ff542d">赞</a>
                            </s:else>
                            <span id="liking<s:property value='id'/>">(<s:property value="likenumber"/>)</span>&nbsp;
                            <a href="javascript:void(0)" onclick="star_message(<s:property value="id"/>,2)">星标</a>

                            <s:if test="star==true">
                                <span id="star<s:property value='id'/>" class="glyphicon glyphicon-star"></span>
                            </s:if>
                            <s:else>
                                <span id="star<s:property value='id'/>"></span>
                            </s:else>
                        </span>
                    </div>
                </div>
            </div>
        </td>
    </s:else>
</s:iterator>
    </s:if>
