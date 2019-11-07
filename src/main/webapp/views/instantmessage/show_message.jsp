<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="MessageList.size() > 0">
    <%--<s:if test="MessageList.size() <= 12">--%>
    <div id="msgNewCount" class="hidden"><s:property value="MessageList[MessageList.size()].id"/> </div>
    <div id="scroll_div" style="height:auto;overflow:scroll hidden; position: relative">
        <table class="table" style="margin-bottom: 0px; border-collapse: separate; border-spacing:6px">
            <tbody>
            <tr>
                <td style="border-top: 0px">
                    <div id="show-initial-time" style="text-align: center;color: #969696"><s:date name="MessageList[0].time" format="yyyy-MM-dd HH:mm:ss"/></div>
                </td>
            </tr>
            <s:iterator value="MessageList">
                <s:if test="type == 'MESSAGE'">
                    <s:if test="timeinterval == 1">
                        <tr>
                            <td style="border-top: 0px">
                                <div id="show-time-interval" style="text-align: center;color: #969696"><s:date name="time" format="yyyy-MM-dd HH:mm"/></div>
                            </td>
                        </tr>
                    </s:if>

                    <s:if test="user.userid == #session.uid">
                        <tr style="background-color: #e4f4fe">
                            <td style="padding: 4px;border-top: 0px;">
                                <div class="media">
                                    <div class="media-body">
                                        <h5 class="media-heading" style="position: relative;">
                                            <div class="front-text-break" style="cursor:pointer;" onclick="quote_message('<s:property value="context"/>','<s:property value="user.name"/>')">
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
                                                    <a href="javascript:void(0);" onclick="edit_message('<s:property value="id"/>','<s:property value="context"/>',1)">编辑</a>&nbsp;
                                                    <a href="javascript:void(0);" onclick="delete_message(<s:property value="id"/>,2)">删除</a>&nbsp;
                                                    <a href="javascript:show_not()">赞</a>
                                                    <%--<span>(<s:property value="likenumber"/>)</span>&nbsp;--%>
                                                    <%--<a title="点赞列表" data-toggle="popover" data-placement="left" data-trigger="hover" data-html="true" href="javascript:show_not()" data-content="12345"><span>(<s:property value="likenumber"/>)</span></a>&nbsp;--%>
                                                    <a href="javascript:void(0);" id="RemarkInfo<s:property value='id'/>" onmouseover="RemarkToggle('<s:property value='id'/>')"><span>(<s:property value="likenumber"/>)</span></a>&nbsp;
                                                    <a href="javascript:void(0)" onclick="star_message(<s:property value="id"/>,1)">星标</a>

                                                     <s:if test="star==true">
                                                         <span id="star<s:property value='id'/>" class="glyphicon glyphicon-star"></span>
                                                     </s:if>
                                                    <s:else>
                                                        <span id="star<s:property value='id'/>"></span>
                                                    </s:else>&nbsp;
                                                    <%--<a href="javascript:void(0);" onclick="comment_message(<s:property value='id'/>)">评论</a>&nbsp;--%>
                                                    <a id="commentCount<s:property value="id"/>" href="javascript:void(0);" onclick="setCommentDisplay(<s:property value='id'/>)">评论(<s:property value="commentNum"/>)<span style="padding-left:3px;top:3px" id="commenicon<s:property value="id"/>" class="glyphicon glyphicon-chevron-down"/></a>&nbsp;
                                                </span>
                                        </div>
                                    </div>
                                </div>

                            </td>
                        </tr>
                        <tr style="background-color:rgba(0, 0, 0, 0);padding:0px 0px 0px 0px;" class="col-lg-12 hidden" id="comment<s:property value='id'/>">
                                <%--<td  class="col-lg-1 pull-left text-center" style="padding: 12px 0px 0px 0px;border-top: 0px;font-size:30px; color:rgba(51,122,183,0.7)">--%>
                                <%--<a style="border:0px;background-color: rgba(0,0,0,0);"><span onclick="comment_message(<s:property value='id'/>)" class="glyphicon glyphicon-plus"></span></a>--%>
                                <%--</td>--%>
                        </tr>
                    </s:if>

                    <s:else>
                        <tr style="background-color: #f8f8f8">
                            <td style="padding: 4px;border-top: 0px;">
                                <div class="media">
                                    <div class="media-body">
                                        <h5 class="media-heading" style="position: relative;">
                                            <div class="front-text-break" style="cursor:pointer;" onclick="quote_message('<s:property value="context"/>','<s:property value="user.name"/>')">
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
                                                        <a id="likeIt<s:property value='id'/>" href="javascript:void(0)" onclick="likeIt(<s:property value='id'/>,'<s:property value="user.name"/>',1);">赞</a>
                                                    </s:if>
                                                    <s:else>
                                                        <a id="likeIt<s:property value='id'/>" href="javascript:void(0)" onclick="likeIt(<s:property value='id'/>,'<s:property value="user.name"/>',1);" style="color: #ff542d">赞</a>
                                                    </s:else>
                                                    <%--<span id="liking<s:property value='id'/>">(<s:property value="likenumber"/>)</span>&nbsp;--%>
                                                <a href="javascript:void(0);" id="RemarkInfo<s:property value='id'/>" onmouseover="RemarkToggle('<s:property value='id'/>')"><span id="liking<s:property value='id'/>">(<s:property value="likenumber"/>)</span></a>&nbsp;
                                                <%--<a title="点赞列表" data-toggle="popover" data-placement="left" data-trigger="hover"--%>
                                                   <%--data-html="true" href="javascript:void(0)"--%>
                                                   <%--data-content="12345"><span id="liking<s:property value='id'/>">(<s:property value="likenumber"/>)</span></a>&nbsp;--%>

                                                    <a href="javascript:void(0)" onclick="star_message(<s:property value="id"/>,1)">星标</a>
                                                    <s:if test="star==true">
                                                        <span id="star<s:property value='id'/>" class="glyphicon glyphicon-star"></span>
                                                    </s:if>
                                                    <s:else>
                                                        <span id="star<s:property value='id'/>"></span>
                                                    </s:else>&nbsp;
                                                    <%--<a href="javascript:void(0);" onclick="comment_message(<s:property value='id'/>)">评论</a>&nbsp;--%>
                                                    <a href="javascript:void(0);" id="commentCount<s:property value="id"/>" onclick="setCommentDisplay(<s:property value='id'/>)">评论(<s:property value="commentNum"/>)<span style="padding-left:3px;top:3px" id="commenicon<s:property value="id"/>" class="glyphicon glyphicon-chevron-down"/></a>&nbsp;
                                                </span>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr style="background-color:rgba(0, 0, 0, 0);padding:0px 0px 0px 0px;" class="col-lg-12 hidden" id="comment<s:property value='id'/>">
                                <%--<td  class="col-lg-1 pull-left text-center" style="padding: 12px 0px 0px 0px;border-top: 0px;font-size:30px; color:rgba(51,122,183,0.7)">--%>
                                <%--<a style="border:0px;background-color: rgba(0,0,0,0);"><span onclick="comment_message(<s:property value='id'/>)" class="glyphicon glyphicon-plus"></span></a>--%>
                                <%--</td>--%>
                        </tr>
                    </s:else>
                </s:if>
            </s:iterator>
            </tbody>
        </table>
    </div>
</s:if>
<s:else>
    <%--<div id="scroll_div" data-spy="scroll" data-offset="0" style="height:auto;overflow:auto; position: relative; margin-top: 10px">--%>
    <div id="scroll_div"  data-spy="scroll" data-offset="0" style="height:auto;overflow:auto; position: relative">
        <%--<table class="table" style="margin-bottom: 0px; border-collapse: separate; border-spacing:6px">--%>
            <%--<tbody>--%>
            <div id="emptycontent" class="text-center" style="margin-top: 40px">
                <span>目前还没有聊天消息呦~</span>
            </div>
            <%--</tbody>--%>
        <%--</table>--%>
    </div>
</s:else>

<%--<h1><s:date name="MessageList[MessageList.size()-1].time" format="yyyy-MM-dd HH:mm:ss"/></h1>--%>
<script>
    $(document).ready(function(){
        $('#scroll_div').scrollTop( $('#scroll_div')[0].scrollHeight);
        if('<s:property value="MessageList.size()"/>'!='0') {
            // console.log("10");
            chatlatestAjax('<s:date name="MessageList[MessageList.size()-1].time" format="yyyy-MM-dd HH:mm:ss"/>','<s:property value="currentlasttime"/>');
        }else{
            var date = new Date();//当前时间
            var month = zeroFill(date.getMonth() + 1);//月
            var day = zeroFill(date.getDate());//日
            var hour = zeroFill(date.getHours());//时
            var minute = zeroFill(date.getMinutes());//分
            var second = zeroFill(date.getSeconds());//秒

            //当前时间
            var curTime = date.getFullYear() + "-" + month + "-" + day
                + " " + hour + ":" + minute + ":" + second;
            // console.log("01");
            chatlatestAjax(curTime,curTime);
        }
    });

    function zeroFill(i){
        if (i >= 0 && i <= 9) {
            return "0" + i;
        } else {
            return i;
        }
    }

    function show_not(){
        $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: '不能给自己点赞'});
    }
</script>