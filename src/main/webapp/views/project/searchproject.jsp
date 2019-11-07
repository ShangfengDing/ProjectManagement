<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="projectList.size() == 0">
    <div class="front-resource-info " style="margin-left: 13px;margin-bottom: 13px">
        <div class="pull-left">搜索结果共0个，建议更换搜索内容。</div>
    </div>
</s:if>
<s:else>

    <s:iterator value="projectList" id="plist">
        <div class="col-md-4">
            <div class="panel panel-default front-panel">
                <div class="panel-body">
                    <div class="media">
                        <div class="media-left">
                            <div class="imgIcon<s:property value="#plist.id" />" style="display:block">
                                <img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.avatar"/>"
                                      onerror="autoIcon('<s:property value="#plist.id" />')" onclick="window.location.href='info?id=<s:property value="#plist.id" />&&type=survey'">
                            </div>

                            <div class="circle<s:property value="#plist.id" />" style="width: 50px; height: 50px; border-radius: 25px; display: none; background-color: white;position:relative;">
                                <span class="glIcon<s:property value="#plist.id" />" style="display:block; font-size: 25px; width: 25px; height: 25px; color: white;margin: auto;  position: absolute;  top: 5; left: 0; bottom: 0; right: 0;" class="glyphicon glyphicon-asterisk"></span>
                            </div>
                            <%--<img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.avatar"/>"--%>
                                  <%--onerror="javascript:this.src='statics/images/group.png'" onclick="window.location.href='info?id=<s:property value="#plist.id" />&&type=survey'">--%>
                        </div>

                        <div class="media-body">

                            <h4 class="media-heading">
												<span class="front-text-title" >
	                                    		<a herf="#" onclick="window.location.href='info?id=<s:property value="#plist.id" />&&type=survey'"><s:property value="#plist.name" /></a>
	                                    		<s:if test='%{#plist.state == "1"}'><span class="badge" >进行中</span>
                                                </s:if>
												<s:if test='%{#plist.state == "2"}'><span class="badge">已暂停</span>
                                                </s:if>
												<s:if test='%{#plist.state == "0"}'><span class="badge">已完成</span>
                                                </s:if>
                                                    <img src="statics/images/hotpoint.png" title="热力值：项目本周活跃程度&#10;热力值计算规则如下：热力值按百分制计算&#10;每提交一份周报加10分&#10;每完成一项任务加3分&#10;每新建一个任务加1分&#10;每增加一条周报评论加1分"/>
	                                			</span>
                            </h4>
                            <div class="as-desc">
                                <div style="display: inline-block">
                                    <s:property value="#plist.description" />
                                </div>
                            </div>
                        </div>
                        <div class="text-right">
                            <a  class="blankspace" id="showdetail-all<s:property value="#plist.id" />" href="info?id=<s:property value="#plist.id" />&&type=survey"> &nbsp;详情&nbsp;</a>
                            <a  class="blankspace" href="info?id=<s:property value="#plist.id" />&&type=user"> &nbsp;成员&nbsp;</a>
                            <a  class="blankspace" href="task/viewtask?projectId=<s:property value="#plist.id" />"> &nbsp;任务&nbsp;</a>
                            <a  class="blankspace" href="info?id=<s:property value="#plist.id" />&&type=report"> &nbsp;报告&nbsp;</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:iterator>

</s:else>

<script type="text/javascript">
    autoIcon = function(){
        var img=event.srcElement;
        img.onerror=null;// 控制不要一直跳动
    }

    autoIcon = function(name){
        var imgName = "imgIcon"+name;
        var iconName = "glIcon"+name;
        var circle = "circle"+name;

        var A = name.charCodeAt(0);
        var B = name.charCodeAt(1);
        var resultIcon = (A + B) % 15;
        var resultColo = (A + B) % 10;
        var iconType = "cloud";
        var iconColo = "rgb(255,145,0)";
        switch(resultIcon){
            case 0:
                iconType = "asterisk";
                break;
            case 1:
                iconType = "cloud";
                break;
            case 2:
                iconType = "inbox";
                break;
            case 3:
                iconType = "book";
                break;
            case 4:
                iconType = "gift";
                break;
            case 5:
                iconType = "leaf";
                break;
            case 6:
                iconType = "fire";
                break;
            case 7:
                iconType = "road";
                break;
            case 8:
                iconType = "eye-open";
                break;
            case 9:
                iconType = "piggy-bank";
                break;
            case 10:
                iconType = "tower";
                break;
            case 11:
                iconType = "king";
                break;
            case 12:
                iconType = "queen";
                break;
            case 13:
                iconType = "grain";
                break;
            case 14:
                iconType = "sunglasses";
                break;
        }
        switch(resultColo){
            case 0:
                iconColo = "#b7191d"
                break;
            case 1:
                iconColo = "#890e4f"
                break;
            case 2:
                iconColo = "#4a148c"
                break;
            case 3:
                iconColo = "#0e47a1"
                break;
            case 4:
                iconColo = "#1c5e20"
                break;
            case 5:
                iconColo = "#1d88e6"
                break;
            case 6:
                iconColo = "#3e50b4"
                break;
            case 7:
                iconColo = "#2f7d32"
                break;
            case 8:
                iconColo = "#ff6f00"
                break;
            case 9:
                iconColo = "#459f47"
                break;
        }
        iconType = "glyphicon glyphicon-"+iconType;
        $("."+imgName).css({"display":"none"});
        $("."+circle).css({"display": "block","backgroundColor":iconColo});
        $("."+iconName).css({"display": "block"});
        $("."+iconName).attr("class", iconType)


        // $("#"+iconName).replaceWith("<span style=\"display:block; color: "+iconColo+";\" class=\"glyphicon glyphicon-"+iconType+"\"></span>");
        //$("#"+iconName).replaceWith("<span style=\"display:block; color: "+iconColo+";\" class=\"glyphicon glyphicon-"+iconType+"\"></span>");
    }
</script>