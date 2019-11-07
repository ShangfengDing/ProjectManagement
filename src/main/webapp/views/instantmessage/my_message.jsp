<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2019/4/27
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <title>我的会话- 轻项目</title>
    <%@include file="/statics/head.html"%>
    <style type="text/css">
        .modal>div{/* 追加此行 */
            display:table;
            width:100%;
            height:100%;
        }

        .modal-dialog {
            /* 略去展示原有内容，此处只显示追加内容 */
            width: 1000px;
            display:table-cell;
            vertical-align:middle;
            margin: 0px 20px;
        }

        .modal-content {
            /* 略去展示原有内容，此处只显示追加内容 */
            display:block;
            width: 1000px;
            /*margin-left: 0 auto;*/
            /*margin-right:0 auto;*/
            margin:0 auto;
            margin-top: 4%;
        }
        .bootBoxStyle {
            /* 略去展示原有内容，此处只显示追加内容 */
            display:block;
            width: 298px;
            margin:0 auto;
            margin-top: 2%;
        }
    </style>
    <style>
        @media (min-width: 1024px){
            #xiangyingshi {
                height: 0px;
                padding-bottom: 40%;
            }
        }
        @media (min-width: 768px) and (max-width: 1024px){
            #xiangyingshi {
                height: 0px;
                padding-bottom: 45%;
            }
        }
        @media (max-width: 768px){
            #xiangyingshi {
                height: 0px;
                padding-bottom: 50%;
            }
        }

    </style>
    <style type="text/css">
        .box2{}
        .box2 a{color:#333; text-decoration:none;border:2px solid #FFF; float:left; cursor:hand;}
        .box2 a span{display:none; color:#2b2b2b;font-family:"微软雅黑";
            font-size: 4px;}
        .box2 a:hover{color: #ffffff; border:2px solid #ffffff; }
        .box2 a:hover span{display:inline; position:absolute;}
    </style>
    <script>
        function autoIcon(name){
            name = name.replace("/","'/'");
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
</head>
<body class="front-body">
<s:include value="../nav.jsp?act=message"/>
<div class="front-inner front-inner-media">
    <div class="container">
        <div class="row">
            <h3>项目会话</h3>
        </div>
        <div class="row">
            <s:iterator value="chatListPro">
            <div class="col-md-3" style="padding-left: 10px;padding-right: 0px">
            <div class="panel panel-default front-panel">
                <div class="panel-body xiangyingshi">
                    <div class="media">
                        <div class="media-left">
                            <div class="imgIcon<s:property value="cid"/>" style="display:block">
                                <%--<img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.key.avatar"/>"--%>
                                      <%--onerror="autoIcon('<s:property value="#plist.id"/>')" >--%>//自定义头像
                                    <img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.key.avatar"/>"
                                        onerror="autoIcon('<s:property value="cid"/>')" >
                              </div>

                              <div class="circle<s:property value="cid"/>" style="width: 50px; height: 50px; border-radius: 25px; display: none; background-color: white;position:relative;">
                                  <span class="glIcon<s:property value="cid"/>" style="display:block; font-size: 25px; width: 25px; height: 25px; color: white;margin: auto;  position: absolute;  top: 5px; left: 0; bottom: 0; right: 0;" class="glyphicon glyphicon-asterisk"></span>
                              </div>
                          </div>

                          <div class="media-body" style="width: 190px">
                              <h4 class="media-heading" style="width: 190px">
                                  <span class="front-text-title" style="height: 20px">
                                                  <a herf="#" class="textlen-control"  href="message/group?projectId=<s:property value="cid" />" target="_blank"><s:property value="name" /></a>
                                                  <div>
                                                    <span class="badge" style="background-color: #8a6d3b">未读<s:property value="#plist.value"/></span>
												</div>
												</span>
                            </h4>
                            <div class="as-desc">
                                <div style="display: inline-block;height:0px;padding-bottom:30%">
                                    <%--<s:property value="#plist.key.description.substring(0,35) + '......'" />--%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </s:iterator>
        </div>

        <div class="row">
            <h3>自建会话</h3>
        </div>
        <div class="row">
            <s:iterator value="chatListSelf">
            <div class="col-md-3" style="padding-left: 10px;padding-right: 0px">
                <div class="panel panel-default front-panel">
                    <div class="panel-body xiangyingshi">
                        <div class="media">
                            <div class="media-left">
                                <div class="imgIcon<s:property value="id"/>" style="display:block">
                                    <img  class="img-circle img-avatar-50" src="http://freedisk.free4inno.com/download?uuid=<s:property value="#plist.key.avatar"/>"
                                          onerror="autoIcon('<s:property value="id"/>')" >
                                </div>
                                <div class="circle<s:property value="id"/>" style="width: 50px; height: 50px; border-radius: 25px; display: none; background-color: white;position:relative;">
                                    <span class="glIcon<s:property value="id"/>" style="display:block; font-size: 25px; width: 25px; height: 25px; color: white;margin: auto;  position: absolute;  top: 5px; left: 0; bottom: 0; right: 0;" class="glyphicon glyphicon-asterisk"></span>
                                </div>
                            </div>

                            <div class="media-body" style="width: 190px">
                                <h4 class="media-heading" style="width: 190px">
                                    <div>
                                        <button type="button" class="btn btn-link" style="position:relative;left: 10px;top: 3px;font-size: large ">自言自语</button>
                                    </div>
                                </h4>
                                <div class="as-desc">
                                    <div style="display: inline-block;height:0px;padding-bottom:30%">
                                        <s:property value="#plist.key.description.substring(0,35) + '......'" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3" style="padding-left: 10px;padding-right: 0px">
            <a class="pull-right"  data-toggle="front-modal" data-size="modal-lg"  style="cursor:pointer;text-decoration:none;color:deepskyblue;padding-top: 2px;position: absolute;left: 100px;top: 23px;font-size: 80px">
                <span class="glyphicon glyphicon-plus" id="addTask"></span>
            </a><br>
                <br>
                <br>
                <br>
                <br>
                <h4 style="position:absolute;left: 100px">新建对话</h4>
            </div>
            </s:iterator>
        </div>
</div>
</div>
</body>
</html>
