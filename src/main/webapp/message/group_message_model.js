var reportId = $('#projectId').val();
var chatId = $('#chatId').val();
currentMessageUrl = "message/currentMessage";
historyMessageUrl = "message/historyMessage";
latestMessageUrl = "message/latestMessage";
earlierMessageUrl = "message/earlierMessage";

var messageSubmitButton = $('#message-submit-button');
var messagePanel = $('#show-message-area');
var messageNormalTextarea = $('#message-normal-textarea');

var postUrl;
var isSubmitted = false;
var description;
var currentPage = true;
var pageInterval="";
var commentInterval ="";
var latestdate;
var contentpopover="";
var liketime;
var commentTime;
var alltypelasttime;
var isShine = false;
var chatname;
var newmessageinterval=false;
var messagenotifymp3 = new Audio("statics/audio/newNotification.mp3");

var hiddenProperty = 'hidden' in document ? 'hidden' :
    'webkitHidden' in document ? 'webkitHidden' :
        'mozHidden' in document ? 'mozHidden' :
            null;
var visibilityChangeEvent = hiddenProperty.replace(/hidden/i, 'visibilitychange');
var onVisibilityChange = function(){
    if (!document[hiddenProperty]) {
        // console.log('页面非激活');
        isShine = false;//在这个页面上
    }else{
        isShine = true;//不在这个页面上
    }
}
document.addEventListener(visibilityChangeEvent, onVisibilityChange);


$(window).scroll(
    function () {
        var scrollTop = $(this).scrollTop();
        if (scrollTop==0) {
            currentPage = false;
            earlierMsgAjax();
            // $("#textSender").css("display", "none");
            // $.fillTipBox({type: 'success', icon: 'glyphicon glyphicon-repeat', content: '已显示前十条消息'});
            $("#history").attr("class","glyphicon glyphicon-refresh");
            // historyMsgAjax();
            setTimeout("edit=true;",1000);
        }else{
            console.log(scrollTop);
        }
    }
);

;(function () {
    // messageNormalTextarea.tah();
    // messageNormalTextarea.tah({moreSpace:5,maxHeight:70});
    $(document).keydown(function(event) {
        if (event.shiftKey && (event.keyCode == 13 || event.which == 10)) {
            var e = $(this).val();
            $(this).val(e + '\n');
        }
        else if (event.keyCode == 13 || event.which == 10) {
            messageSubmitButton.click() ;
        }
    });

})();


function chatMsgAjax() {
    if (currentPage===true) {
        postUrl = currentMessageUrl;
    } else {
        postUrl = historyMessageUrl;
    }
    $.post(postUrl, {
        "projectId": reportId
    }, function (html) {
        $('#project_name').html()
        messagePanel.html(html);
        // if(currentPage==true)
            window.scrollTo(0, document.documentElement.scrollHeight-document.documentElement.clientHeight);
    });
}

//抓取最新消息
function chatlatestAjax(latesttime,currentlasttime) {
    // console.log(currentlasttime);
    $.post(latestMessageUrl, {
        "projectId": reportId,
        "conversation.time": latesttime,//所有message最后一条
        "likelasttime":currentlasttime//所有类型消息最后一条
    }, function (json) {
        var arr=json.messageList;
        var MsgCommentArr=json.messageCommentList;
        var MsgDeleteArr=json.messageDeleteList;
        var likearr=json.likeListtime;
        var latestAllConversation=json.latestAllConversation;
        var id;
        var context;
        var date;
        var username;
        var timeinter;
        chatname=json.projectName+" - 项目会话";
        // console.log(chatname);
        for(i=0;i<arr.length;i++){
            id=arr[i].id;
            context=arr[i].context;
            username=arr[i].user.name;
            timeinter=arr[i].timeinterval;
            date=new Date(+new Date(new Date(arr[i].time).toJSON())+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
            if(arr.length>0){
                if(messagePanel.find('tbody').length == 0) {
                    $("#emptycontent").remove();
                    messagePanel.html(
                        '<table class="table" style="margin-bottom: 0px; border-collapse: separate; border-spacing:6px">'+
                        '    <tbody>'+
                        '        <tr>' +
                        '            <td style="border-top: 0px">' +
                        '               <div id="show-initial-time" style="text-align: center;color: #969696">' + date +
                        '               </div>'+
                        '           </td>'+
                        '       </tr>'+
                        '   </tbody>'+
                        '</table>'
                    );
                }

                if(timeinter==1){
                    messagePanel.find('tbody').append(
                        '<tr>' +
                        '   <td style="border-top: 0px">'+
                        '       <div id="show-time-interval" style="text-align: center;color: #969696">' + date +
                        '       </div>'+
                        '   </td>'+
                        '</tr>'
                    );
                }

                if(arr[i].user.userid==json.sessionUID){
                    // messagePanel.find('tbody').append(
                    //     '<tr style="background-color: #e4f4fe">' +
                    //     '   <td style="padding: 4px;border-top: 0px;">'+
                    //     '       <div class="media">'+
                    //     '           <div class="media-body">'+
                    //     '               <h5 class="media-heading" style="position: relative">'+
                    //     '                   <div class="front-text-break" onclick="quote_message(\'+context+\',\'+username+\')"><pre style="border:none; padding:0px;background-color: #e4f4fe;margin-bottom: 5px;line-height: 14px;white-space: pre-wrap;font-size: 14px;font-family: inherit;">'+context+
                    //     '                   </pre></div>'+
                    //     '               </h5>'+
                    //     '               <div style="font-size: 13px; margin-top: 2px">'+
                    //     '                   <span class="front-text-title" style="color: #777">'+date+'&nbsp;'+
                    //     '                   </span>'+
                    //     '                   <span class="front-text-title" style="color: #337ab7">'+username+
                    //     '                   </span>'+
                    //     '                   <span class="front-top-right pull-right">'+
                    //     '                       <a href="javascript:void(0);" onclick="edit_message('+id+','+context+',1)">编辑</a>&nbsp;'+
                    //     '                       <a href="javascript:void(0);" onclick="delete_message('+id+',2)">删除</a>&nbsp;'+
                    //     '                       <a href="javascript:show_not()">赞</a>'+
                    //     '                       <a href="javascript:void(0);" id="RemarkInfo'+id+'" onmouseover="RemarkToggle('+id+')"><span>('+arr[i].likenumber+')</span></a>&nbsp;'+
                    //     // '                       <span>('+arr[i].likenumber+')</span>&nbsp;'+
                    //     '                       <a href="javascript:void(0)" onclick="star_message('+id+',1)">星标</a>'+
                    //     '                       <span id="star'+id+'"></span>&nbsp;'+
                    //     '                       <a href="javascript:void(0);" onclick="setCommentDisplay('+id+')">评论<span style="padding-left:3px;top:3px" id="commenicon'+id+'" class="glyphicon glyphicon-chevron-down"/></a>&nbsp;'+
                    //     '                   </span>'+
                    //     '               </div>'+
                    //     '           </div>'+
                    //     '       </div>'+
                    //     '   </td>'+
                    //     '</tr>'+
                    //     '<tr style="background-color:rgba(0, 0, 0, 0);padding:0px 0px 0px 0px;" class="col-lg-12 hidden" id="comment'+id+'">'+
                    //     '</tr>'
                    // );
                }else{
                    messagePanel.find('tbody').append(
                        '<tr style="background-color: #f8f8f8">' +
                        '   <td style="padding: 4px;border-top: 0px;">'+
                        '       <div class="media">'+
                        '           <div class="media-body">'+
                        '               <h5 class="media-heading" style="position: relative">'+
                        '                   <div class="front-text-break" onclick="quote_message(\'+context+\',\'+username+\')"><pre style="border:none; padding:0px;background-color: #f8f8f8;margin-bottom: 5px;line-height: 14px;white-space: pre-wrap;font-size: 14px;font-family: inherit;">' +context+
                        '                   </pre></div>'+
                        '               </h5>'+
                        '               <div style="font-size: 13px; margin-top: 2px; color: #777">'+
                        '                   <span class="front-text-title">'+date+'&nbsp;'+
                        '                   </span>'+
                        '                   <span class="front-text-title">'+username+
                        '                   </span>'+
                        '                   <span class="front-top-right pull-right">'+
                        '                       <a id="likeIt'+id+'" href="javascript:void(0)" onclick="likeIt('+id+',1);">赞</a>'+
                        '                       <a href="javascript:void(0);" id="RemarkInfo'+id+'" onmouseover="RemarkToggle('+id+')"><span id="liking'+id+'">('+arr[i].likenumber+')</span></a>&nbsp;'+
                        // '                       <span>('+arr[i].likenumber+')</span>&nbsp;'+
                        '                       <a href="javascript:void(0)" onclick="star_message('+id+',1)">星标</a>'+
                        '                       <span id="star'+id+'"></span>&nbsp;'+
                        '                       <a href="javascript:void(0);" onclick="setCommentDisplay('+id+')">评论('+arr[i].commentNum+')<span style="padding-left:3px;top:3px" id="commenicon'+id+'" class="glyphicon glyphicon-chevron-down"/></a>&nbsp;'+
                        '                   </span>'+
                        '               </div>'+
                        '           </div>'+
                        '       </div>'+
                        '   </td>'+
                        '</tr>'+
                        '<tr style="background-color:rgba(0, 0, 0, 0);padding:0px 0px 0px 0px;" class="col-lg-12 hidden" id="comment'+id+'">'+
                        '</tr>'
                    );
                }
            }
        }

        for(i=0;i<MsgCommentArr.length;i++){
            var userid=MsgCommentArr[i].user.userid;
            if(userid!=json.sessionUID){
                var pName=MsgCommentArr[i].pUser.name;
                var name=MsgCommentArr[i].user.name;
                var pTime=MsgCommentArr[i].pTime;
                Date.prototype.datetime = function() {return myDate.getFullYear() + '-' +('0' + (myDate.getMonth()+1)).slice(-2)+ '-' +  myDate.getDate() + ' '+myDate.getHours()+ ':'+('0' + (myDate.getMinutes())).slice(-2)+ ':'+myDate.getSeconds(); };
                var myDate = new Date(pTime+"");
                $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: name+"评论了"+pName+myDate.datetime()+"发送的消息", delay: 2500});
            }
            // commentTime=new Date(+new Date(new Date(likearr[i].time).toJSON())+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
            chatMsgAjax();
        }

        for(i=0;i<MsgDeleteArr.length;i++){
            console.log(MsgDeleteArr.length);
            var userid=MsgDeleteArr[i].user.userid;
            if(userid!=json.sessionUID){
                var name=MsgDeleteArr[i].user.name;
                var oTime=MsgDeleteArr[i].oTime;
                Date.prototype.datetime = function() {return myDate.getFullYear() + '-' +('0' + (myDate.getMonth()+1)).slice(-2)+ '-' +  myDate.getDate() + ' '+myDate.getHours()+ ':'+('0' + (myDate.getMinutes())).slice(-2)+ ':'+myDate.getSeconds(); };
                var myDate = new Date(  oTime+"");
                $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: name+"删除了"+myDate.datetime()+"发送的消息", delay: 2500});
            }
            // commentTime=new Date(+new Date(new Date(likearr[i].time).toJSON())+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
            chatMsgAjax();
        }

        for(i=0;i<likearr.length;i++){
            var userid=likearr[i].user.userid;
            if(userid!=json.sessionUID){
                var peoplelikedname=likearr[i].peopleliked.name;
                var username=likearr[i].user.name;
                var time=likearr[i].time;
                Date.prototype.datetime = function() {return myDate.getFullYear() + '-' +('0' + (myDate.getMonth()+1)).slice(-2)+ '-' +  myDate.getDate() + ' '+myDate.getHours()+ ':'+('0' + (myDate.getMinutes())).slice(-2)+ ':'+myDate.getSeconds(); };
                var myDate = new Date(time+"");
                $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: username+"给"+peoplelikedname+myDate.datetime()+"发送的消息点了赞"});
            }
            liketime=new Date(+new Date(new Date(likearr[i].time).toJSON())+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
            // console.log("12345");
            chatMsgAjax();
        }

        alltypelasttime=new Date(+new Date(new Date(json.likelasttime).toJSON())+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
        // console.log(alltypelasttime);
        if(arr.length>0){
            latestdate=date;
            window.scrollTo(0, document.documentElement.scrollHeight-document.documentElement.clientHeight);

        }
        else{latestdate=latesttime;}
        // if(currentPage==true)
            // window.scrollTo(0, document.documentElement.scrollHeight-document.documentElement.clientHeight);

        if(latestAllConversation.length!=0){
            for(i=0;i<latestAllConversation.length;i++){
                if(latestAllConversation[i].user.userid!=json.sessionUID && latestAllConversation[i].type!="STAR"){
                    var titleInit = chatname;
                    // var titleInit = document.title, isShine = true;
                    if(newmessageinterval==false && isShine==true){
                        var interval=setInterval(function() {
                            // console.log("我循环了"+newmessageinterval);
                            var title = document.title;
                            if (isShine == true) {
                                if (/新/.test(title) == false) {
                                    document.title = '【有新消息啦】';
                                } else {
                                    document.title = '【　　　　　】';
                                }

                            } else {
                                document.title = titleInit;
                                clearInterval(interval);
                                newmessageinterval==false;
                            }
                            newmessageinterval=true;
                        }, 500);
                    }


                    if(isShine==true){
                        messagenotifymp3.play();
                    }


                    // if(window.Notification) {
                    //     // var button = document.getElementById('button'), text = document.getElementById('text');
                    //     var popNotice = function() {
                    //         if(latestAllConversation[i].type=="MESSAGE" && Notification.permission == "granted" && isShine==true){
                    //             var notification = new Notification("有新消息啦", {
                    //                 body: latestAllConversation[i].user.name+"发送新消息"
                    //             });
                    //
                    //             messagenotifymp3.play();
                    //
                    //             notification.onclick = function() {
                    //                 window.focus();
                    //             };
                    //         }
                    //         else if(latestAllConversation[i].type=="LIKE" && Notification.permission == "granted" && isShine==true){
                    //             var notification = new Notification("有新消息啦", {
                    //                 body: latestAllConversation[i].user.name+"点了赞"
                    //             });
                    //
                    //             messagenotifymp3.play();
                    //
                    //             notification.onclick = function() {
                    //                 window.focus();
                    //             };
                    //         }
                    //         else if(latestAllConversation[i].type=="COMMENT" && Notification.permission == "granted" && isShine==true){
                    //             var notification = new Notification("有新消息啦", {
                    //                 body: latestAllConversation[i].user.name+"有新评论"
                    //             });
                    //
                    //             messagenotifymp3.play();
                    //
                    //             notification.onclick = function() {
                    //                 window.focus();
                    //             };
                    //         }
                    //     };
                    //
                    //     if (Notification.permission == "granted" && isShine==true) {
                    //         popNotice();
                    //     } else if (Notification.permission != "denied" && isShine==true) {
                    //         Notification.requestPermission(function (permission) {
                    //             popNotice();
                    //         });
                    //     }
                    //
                    // }else {
                    //     alert('浏览器不支持Notification');
                    // }
                }
            }
        }

        if(isShine==false){
            // console.log("我改了flag"+newmessageinterval);
            newmessageinterval=false;
        }
    });

}

// 设置页面刷新时间，打开评论后停止刷新，关上评论后开始刷新
pageInterval=setInterval('chatlatestAjax(latestdate,alltypelasttime)',2000);

function historyMsgAjax() {
    if(currentPage == true){
        resizeSmallText();
        currentPage = false;
        $("#textSender").css("display", "none");
        $.fillTipBox({type: 'success', icon: 'glyphicon glyphicon-repeat', content: '正在读取。。。'});
        $("#history").attr("class","glyphicon glyphicon-refresh");
        chatMsgAjax();
        $.fillTipBox({type: 'success', icon: 'glyphicon glyphicon-repeat', content: '已显示历史记录'});
    }
    else{
        resizeSmallText();
        currentPage = true;
        $("#textSender").css("display", "block");
        $.fillTipBox({type: 'success', icon: 'glyphicon glyphicon-refresh', content: '已显示最新消息'});
        $("#history").attr("class","glyphicon glyphicon-time");
        chatMsgAjax();
    }
}

function earlierMsgAjax() {
        currentPage = false;
        // $("#textSender").css("display", "none");
        $("#history").attr("class","glyphicon glyphicon-refresh");
        // chatMsgAjax();
        var earliestMsgTime=$("#show-initial-time").text();
        console.log("before"+earliestMsgTime);
        $.post(earlierMessageUrl,{
            "projectId": reportId,
            "conversation.time": earliestMsgTime,
        },function(json){
            console.log(json);
            var arr=json.messageList;
            var id;
            var context;
            var date;
            var username;
            var timeinter;
            for(i=0;i<arr.length;i++){
                id=arr[i].id;
                context=arr[i].context;
                username=arr[i].user.name;
                timeinter=arr[i].timeinterval;
                date=new Date(+new Date(new Date(arr[i].time).toJSON())+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
                if(arr.length>0){
                        if(arr[i].user.userid==json.sessionUID){
                            messagePanel.find('tbody').prepend(
                                '<tr style="background-color: #e4f4fe">' +
                                '   <td style="padding: 4px;border-top: 0px;">'+
                                '       <div class="media">'+
                                '           <div class="media-body">'+
                                '               <h5 class="media-heading" style="position: relative">'+
                                '                   <div class="front-text-break" onclick="quote_message(\'+context+\',\'+username+\')"><pre style="border:none; padding:0px;background-color: #e4f4fe;margin-bottom: 5px;line-height: 14px;white-space: pre-wrap;font-size: 14px;font-family: inherit;">'+context+
                                '                   </pre></div>'+
                                '               </h5>'+
                                '               <div style="font-size: 13px; margin-top: 2px">'+
                                '                   <span class="front-text-title" style="color: #777">'+date+'&nbsp;'+
                                '                   </span>'+
                                '                   <span class="front-text-title" style="color: #337ab7">'+username+
                                '                   </span>'+
                                '                   <span class="front-top-right pull-right">'+
                                '                       <a href="javascript:void(0);" onclick="edit_message('+id+','+context+',1)">编辑</a>&nbsp;'+
                                '                       <a href="javascript:void(0);" onclick="delete_message('+id+',2)">删除</a>&nbsp;'+
                                '                       <a href="javascript:show_not()">赞</a>'+
                                '                       <a href="javascript:void(0);" id="RemarkInfo'+id+'" onmouseover="RemarkToggle('+id+')"><span>('+arr[i].likenumber+')</span></a>&nbsp;'+
                                // '                       <span>('+arr[i].likenumber+')</span>&nbsp;'+
                                '                       <a href="javascript:void(0)" onclick="star_message('+id+',1)">星标</a>'+
                                '                       <span id="star'+id+'"></span>&nbsp;'+
                                '                       <a href="javascript:void(0);" onclick="setCommentDisplay('+id+')">评论('+arr[i].commentNum+')<span style="padding-left:3px;top:3px" id="commenicon'+id+'" class="glyphicon glyphicon-chevron-down"/></a>&nbsp;'+
                                '                   </span>'+
                                '               </div>'+
                                '           </div>'+
                                '       </div>'+
                                '   </td>'+
                                '</tr>'+
                                '<tr style="background-color:rgba(0, 0, 0, 0);padding:0px 0px 0px 0px;" class="col-lg-12 hidden" id="comment'+id+'">'+
                                '</tr>'
                            );
                        }
                        else{
                            messagePanel.find('tbody').prepend(
                                '<tr style="background-color: #f8f8f8">' +
                                '   <td style="padding: 4px;border-top: 0px;">'+
                                '       <div class="media">'+
                                '           <div class="media-body">'+
                                '               <h5 class="media-heading" style="position: relative">'+
                                '                   <div class="front-text-break" onclick="quote_message(\'+context+\',\'+username+\')"><pre style="border:none; padding:0px;background-color: #f8f8f8;margin-bottom: 5px;line-height: 14px;white-space: pre-wrap;font-size: 14px;font-family: inherit;">' +context+
                                '                   </pre></div>'+
                                '               </h5>'+
                                '               <div style="font-size: 13px; margin-top: 2px; color: #777">'+
                                '                   <span class="front-text-title">'+date+'&nbsp;'+
                                '                   </span>'+
                                '                   <span class="front-text-title">'+username+
                                '                   </span>'+
                                '                   <span class="front-top-right pull-right">'+
                                '                       <a id="likeIt'+id+'" href="javascript:void(0)" onclick="likeIt('+id+',1);">赞</a>'+
                                '                       <a href="javascript:void(0);" id="RemarkInfo'+id+'" onmouseover="RemarkToggle('+id+')"><span id="liking'+id+'">('+arr[i].likenumber+')</span></a>&nbsp;'+
                                // '                       <span>('+arr[i].likenumber+')</span>&nbsp;'+
                                '                       <a href="javascript:void(0)" onclick="star_message('+id+',1)">星标</a>'+
                                '                       <span id="star'+id+'"></span>&nbsp;'+
                                '                       <a href="javascript:void(0);" onclick="setCommentDisplay('+id+')">评论('+arr[i].commentNum+')<span style="padding-left:3px;top:3px" id="commenicon'+id+'" class="glyphicon glyphicon-chevron-down"/></a>&nbsp;'+
                                '                   </span>'+
                                '               </div>'+
                                '           </div>'+
                                '       </div>'+
                                '   </td>'+
                                '</tr>'+
                                '<tr style="background-color:rgba(0, 0, 0, 0);padding:0px 0px 0px 0px;" class="col-lg-12 hidden" id="comment'+id+'">'+
                                '</tr>'
                            );
                        }

                        if(timeinter==1){
                            messagePanel.find('tbody').append(
                                '<tr>' +
                                '   <td style="border-top: 0px">'+
                                '       <div id="show-time-interval" style="text-align: center;color: #969696">' + date +
                                '       </div>'+
                                '   </td>'+
                                '</tr>'
                            );
                        }

                }
            }
            date=new Date(+new Date(new Date(arr[arr.length-1].time).toJSON())+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
            $("#show-initial-time").html(date);
        });

}

function preSubmitMessage() {
    description = messageNormalTextarea.val().trim();
    var regexp = /((http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/ig;//普通评论中的链接支持点击
    description = description.replace(regexp, "<a href='$1' target='_blank'>$1</a>");//普通评论中的链接支持点击
    if (description.length <= 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入发送内容'});
        return;
    }
    // if(messagePanel.find('tbody').length == 0) {
    //     messagePanel.html(
    //         '<div class="panel panel-default front-panel">'+
    //         '    <div class="panel-body front-no-padding">'+
    //         '        <table class="table table-striped front-table" style="margin-bottom: 0px">' +
    //         '            <tbody>' +
    //         '<tr>' +
    //         '    <td>' +
    //         '        <div class="media">' +
    //         '            <div class="media-body">' +
    //         '                <h5 class="media-heading" style="position: relative">' +
    //         '                    <span id="resubmit-button" class="front-top-right pull-right">' +
    //         '                        提交中...' +
    //         '                    </span>' +
    //         '                </h5>' +
    //         '                <div class="front-text-break">' + description + '</div>' +
    //         '            </div>' +
    //         '        </div>' +
    //         '    </td>' +
    //         '</tr>' +
    //         '            </tbody>'+
    //         '        </table>'+
    //         '    </div>'+
    //         '</div>'
    //     );
    // } else {
    //     messagePanel.find('tbody').prepend(
    //         '<tr>' +
    //         '    <td>' +
    //         '        <div class="media">' +
    //         '            <div class="media-body">' +
    //         '                <h5 class="media-heading" style="position: relative">' +
    //         '                    <span id="resubmit-button" class="front-top-right pull-right">' +
    //         '                        提交中...' +
    //         '                    </span>' +
    //         '                </h5>' +
    //         '                <div class="front-text-break">' + description + '</div>' +
    //         '            </div>' +
    //         '        </div>' +
    //         '    </td>' +
    //         '</tr>'
    //     );
    // }
    messageSubmitButton.addClass("disabled");
    messageSubmitButton.html("提交中...");
    submitMessage();
}
function submitMessage() {
    $.post("message/saveMessage", {
        "conversation.chatId": chatId,
        "conversation.context": description,
    }, function (json) {
        messageSubmitButton.removeClass("disabled");
        messageSubmitButton.html('<span class="glyphicon glyphicon-send"></span> &nbsp;发送');
        if(json.saveResult==true) {
            messageNormalTextarea.val("");
            isSubmitted = true;
            chatMsgAjax();
            // chatlatestAjax(latestdate);
            resizeSmallText();
        } else {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '发送失败'});
            $('#resubmit-button').html('<a href="javascript:submitMessage()">重新发送</a>');
        }
    })
}


function delete_message(id,types){
    if(ifOther==true && types==1){
        closeit(oldCommentId);
        pageInterval=setInterval('chatlatestAjax(latestdate,alltypelasttime)',2000);
    }
    if(confirm("确认删除？")==true){
        $.post("message/deleteMessage", {
            "conversation.id": id,
        },function (json) {
            if(json.deleteResult ==true) {
                $.fillTipBox({type: 'info', icon: 'glyphicon-ok-sign', content: '已删除'});
                chatMsgAjax();
            } else {
                $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '删除失败'});
            }
        })
    }
}

function edit_message(id,context,types){
    if(ifOther==true && types==1){
        closeit(oldCommentId);
        pageInterval=setInterval('chatlatestAjax(latestdate,alltypelasttime)',2000);
    }
    var newmessage = prompt("修改内容",context);
    if(newmessage!=null&&newmessage!=""){
        $.post("message/editMessage", {
            "conversation.id": id,
            "conversation.context": newmessage,
        },function (json) {
            if(json.updateResult==true) {
                // chatMsgAjax();
                $.fillTipBox({type: 'info', icon: 'glyphicon-sign', content: '更新成功'});
                chatMsgAjax()
            } else {
                $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '更新失败'});
            }
        })
    }
}

function likeIt(id,publishername,types){
    // console.log(id);
    // console.log(publishername);
    if(ifOther==true && types==1){
        closeit(oldCommentId);
        pageInterval=setInterval('chatlatestAjax(latestdate,alltypelasttime)',2000);
    }
    $.post('message/likeIt',{
        "conversation.id": id,
        "conversation.chatId": chatId
    },function(data){
        //alert(data.sumNum);
        if (data.result == "success") {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '点赞成功！'});
            // $("#liking" + id).html("(" + data.sumNum + ")");
            $("#likeIt" + id).attr("style", "color: #ff542d");
            // if(types==2){
            //     $("#liking" + id).html("(" + data.sumNum + ")");
            // }
        } else if (data.result == "already") {
            $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: '取消点赞！'});
            // $("#liking" + id).html("(" + data.sumNum + ")");
            $("#likeIt" + id).removeAttr("style");
            // if(types==2){
            //     $("#liking" + id).html("(" + data.sumNum + ")");
            // }
            chatMsgAjax();
        }
        // chatMsgAjax();
    });
}

function RemarkToggle(id) {
    $.post('message/likelist', {
        "conversation.parentId": id
    }, function (json) {
        // console.log(json.likeList);
        var arr=json.likeList;
        for(i=0;i<arr.length;i++){
            contentpopover +="<tr><td>"+arr[i].user.name+"</tr></td>"
        }
        if(arr.length==0){
            contentpopover="目前无人点赞";
        }
        $("#RemarkInfo"+id).popover({
            trigger: 'hover',
            placement: 'left',
            // title: '点赞列表',
            html: 'true',
            container: 'body',
            content: '<table><tbody>'+contentpopover+'</tbody></table>'
        });
        // contentpopover="";
        $("#RemarkInfo"+id).popover('show');
        //隐藏后销毁popover
        // $("#RemarkInfo"+id).on('hidden.bs.popover', function () {
        //     $("#RemarkInfo"+id).popover('destroy');
        // })
        contentpopover="";
    });

}

function quote_message(context,username){
    // alert(context);
    console.log(context);
    console.log(username);
    var originalmessage=messageNormalTextarea.val();
    // messageNormalTextarea.val(originalmessage+context);
    messageNormalTextarea.val(originalmessage+"「"+username+":"+context+"」"+" \n "+"- - - - - - - - - - - - - - -"+" \n ");
}

//评论部分
function comment_message(id){
    var newcomment = prompt("评论会话");
    if(newcomment!=null&&newcomment!=""){
        $.post('message/commentMessage',{
            "conversation.id": id,
            "conversation.chatId": chatId,
            "conversation.context": newcomment,
        },function(json){
            if(json.commentResult==true) {
                $.fillTipBox({type: 'info', icon: 'glyphicon-ok-sign', content: '评论此会话成功'});
            } else {
                $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '评论此会话失败'});
            }
            pageInterval=setInterval('chatlatestAjax(latestdate,alltypelasttime)',2000);
            // show_comment(id);
        });
    }
}

messageNormalTextarea.blur(function () {
    if($(this).val() == ""){
        $(this).attr("placeholder","按Enter发出消息");
        messageSubmitButton.html('<span class="glyphicon glyphicon-send"></span> &nbsp;发送');
    }
});

function star_message(id,types){
    if(ifOther==true && types==1){
        closeit(oldCommentId);
        pageInterval=setInterval('chatlatestAjax(latestdate,alltypelasttime)',2000);
    }
    $.post("message/starit",{
        "conversation.id":id,
        "conversation.chatId":chatId
    },function(data){
        if(data.result=="success"){
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '标记成功！'});
            // $("#star"+id).addClass("glyphicon glyphicon-star");

        }else{
            $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: '取消标记！'});
            // $("#star"+id).removeClass("glyphicon glyphicon-star");

        }
        chatMsgAjax();
    })
}
ifOther=false;
function setCommentDisplay(id){
    clearInterval(pageInterval);
    //只能显示一条信息的评论

    if($("#comment"+id).hasClass("hidden")==true){
        if(ifOther==true){
            closeit(oldCommentId);
        }
        ifOther=true;
        oldCommentId=id;
        $("#comment"+id).removeClass("hidden");
        // show_comment(id);
        // commentInterval=setInterval(function(){
        //     $.post("message/showComment",{
        //         "conversation.id":id,
        //         "conversation.chatId":chatId
        //     },function(data){
        //         $("#comment"+id).html(data);
        //     })
        // },2000);
        refreshComment(id);
    }else{
        closeit(id);
        pageInterval=setInterval('chatlatestAjax(latestdate,alltypelasttime)',2000);
    }

}
//关闭其他评论框
function closeit(id){
    // clearInterval(commentInterval);
    // $("#comment"+id).html("");
    refreshComment(-1);
    $("#comment"+id).addClass("hidden");
    $("#commenicon"+id).removeClass("glyphicon glyphicon-chevron-up");
    $("#commenicon"+id).addClass("glyphicon glyphicon-chevron-down");
    ifOther=false;
    console.log(commentInterval);
}

function refreshComment(id){
    if(id==-1){
        clearTimeout(commentInterval);
    }else{
        $.post("message/showComment",{
            "conversation.id":id,
            "conversation.chatId":chatId
        },function(data){
            $("#comment"+id).removeClass("hidden");
            $("#comment"+id).html(data);
            $("#commenicon"+id).removeClass("glyphicon glyphicon-chevron-down");
            $("#commenicon"+id).addClass("glyphicon glyphicon-chevron-up");
            commentCount(id);
        })
        commentInterval=setTimeout('refreshComment('+id+')',2000);
    }
}

function commentCount(id){
        $.post("message/countComment",{
            "conversation.id":id,
            "conversation.chatId":chatId
    },function(json){
        $("#commentCount"+id).html("评论("+json.commentCount+")"+
        '<span style="padding-left:3px;top:3px" id="commenicon'+
            id+
            '" class="glyphicon glyphicon-chevron-up"/>');
    })
}

// function show_comment(id){
//     $.post("message/showComment",{
//         "conversation.id":id,
//         "conversation.project.id":reportId
//     },function(data){
//         $("#comment"+id).removeClass("hidden");
//         $("#comment"+id).html(data);
//         $("#commenicon"+id).removeClass("glyphicon glyphicon-chevron-down");
//         $("#commenicon"+id).addClass("glyphicon glyphicon-chevron-up");
//     })
// }

//发送图片
$('.fr-file-upload').frFileUpload({
    maxFileSize:10*1024*1024,//10MB

    uploadCallback:function (json) {
        if(json.uuid == null || json.uuid == "") {
            return false;
        } else {
            console.log(json.uuid);
            var pictureuuid=json.uuid;
            var des;
            des="<img src=\"http://freedisk.free4inno.com/download?uuid=" + pictureuuid + "\""+"style=\"max-width: 100%;max-height: 200px;"+"\">";
            // console.log(des);
            $.post("message/saveMessage", {
                "conversation.chatId": chatId,
                "conversation.context": des,
            }, function (json) {
                messageSubmitButton.removeClass("disabled");
                messageSubmitButton.html('<span class="glyphicon glyphicon-send"></span> &nbsp;发送');
                if(json.saveResult==true) {
                    messageNormalTextarea.val("");
                    isSubmitted = true;
                    chatMsgAjax();
                } else {
                    $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '发送失败'});
                    // $.tipModal('alert', 'danger', '发送失败');
                    $('#resubmit-button').html('<a href="javascript:submitMessage()">重新发送</a>');
                }
            })
            return true;
        }
    }
});

//扩大textarea占满屏幕
function resizeFullText() {
    var windowwidth=window.screen.width;
    var windowheight=window.screen.height;
    console.log(windowwidth);
    console.log(windowheight);
    document.getElementById("resizeFull").style.display="none";
    document.getElementById("resizeSmall").style.display="";

    document.getElementById("show-message-area").style.display="none";
    document.getElementById("message-normal-textarea").style.height=(windowheight-220)+"px";
    // if(windowwidth>=1200){
    //     document.getElementById("message-normal-textarea").style.height="690px";
    // }else if(windowwidth>=992 && windowwidth<=1199){
    //     document.getElementById("message-normal-textarea").style.height="1270px";
    // }else if(windowwidth>=741 && windowwidth<=991){
    //     document.getElementById("message-normal-textarea").style.height="935px";
    // }else if(windowwidth>=475 && windowwidth<=740){
    //     document.getElementById("message-normal-textarea").style.height="1270px";
    // }else if(windowwidth>=376 && windowwidth<=474){
    //     document.getElementById("message-normal-textarea").style.height="635px";
    // }else if(windowwidth>=361 && windowwidth<=375){
    //     document.getElementById("message-normal-textarea").style.height="575px";
    // }else if(windowwidth<=360){
    //     document.getElementById("message-normal-textarea").style.height="545px";
    // }
    //
    // if(windowwidth>=411 && windowheight==823){
    //     document.getElementById("message-normal-textarea").style.height="730px";
    // }
    //
    // if(windowwidth>=375 && windowheight==812){
    //     document.getElementById("message-normal-textarea").style.height="715px";
    // }
    //
    // if(windowheight<600){
    //     document.getElementById("message-normal-textarea").style.height="400px";
    // }

}

//缩小textarea回到原来的样式
function resizeSmallText() {
    document.getElementById("resizeSmall").style.display="none";
    document.getElementById("resizeFull").style.display="";

    document.getElementById("show-message-area").style.display="";

    document.getElementById("message-normal-textarea").style.height="auto";
    // messageNormalTextarea.tah({moreSpace:5,maxHeight:70});
}
