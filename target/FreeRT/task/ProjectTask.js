var projectid = $("#selectedProject").val();
var card1 = $("#state1");
var card2 = $("#state2");
var card3 = $("#state3");
var card4 = $("#state4");
var page1=1;
var page2=1;
var page3=1;
var page4=1;
var pageSum1=1;
var pageSum2=1;
var pageSum3=1;
var pageSum4=1;


$(document).ready(function(){
    var auth = $("#auth").val();
    if(auth == "true"){
        panelHeight();
        var h = $(window).height()-190;
        $("#containState1").height(h);
        $("#containState2").height(h);
        $("#containState3").height(h);
        $("#containState4").height(h);
        showAllTask();
    }

});

function showAllTask() {
    card1.empty();
    card2.empty();
    card3.empty();
    card4.empty();
    page1=1;
    page2=1;
    page3=1;
    page4=1;
    pageSum1=1;
    pageSum2=1;
    pageSum3=1;
    pageSum4=1;
    loadTaskByState(1,page1);
    loadTaskByState(2,page2);
    loadTaskByState(3,page3);
    loadTaskByState(4,page4);
}

var nScrollHight1 = 0; //滚动距离总长(注意不是滚动条的长度)
var nScrollTop1 = 0;   //滚动到的当前位置
var nDivHight1 = 0;
$("#containState1").scroll(function(){
    nDivHight1 = $("#containState1").height();
    nScrollHight1 = $(this)[0].scrollHeight;
    nScrollTop1 = Math.ceil($(this)[0].scrollTop);
    if(nScrollTop1 + nDivHight1 >= nScrollHight1){
        page1++;
        loadTaskByState(1,page1);
    }
});

var nScrollHight2 = 0; //滚动距离总长(注意不是滚动条的长度)
var nScrollTop2 = 0;   //滚动到的当前位置
var nDivHight2 = 0;
$("#containState2").scroll(function(){
    nDivHight2 = $("#containState2").height();
    nScrollHight2 = $(this)[0].scrollHeight;
    nScrollTop2 = Math.ceil($(this)[0].scrollTop);
    if(nScrollTop2 + nDivHight2 >= nScrollHight2){
        page2++;
        loadTaskByState(2,page2);
    }
});

var nScrollHight3 = 0; //滚动距离总长(注意不是滚动条的长度)
var nScrollTop3 = 0;   //滚动到的当前位置
var nDivHight3 = 0;
$("#containState3").scroll(function(){
    nDivHight3 = $("#containState3").height();
    nScrollHight3 = $(this)[0].scrollHeight;
    nScrollTop3 = Math.ceil($(this)[0].scrollTop);
    if(nScrollTop3 + nDivHight3 >= nScrollHight3){
        page3++;
        loadTaskByState(3,page3);
    }
});

var nScrollHight4 = 0; //滚动距离总长(注意不是滚动条的长度)
var nScrollTop4 = 0;   //滚动到的当前位置
var nDivHight4 = 0;
$("#containState4").scroll(function(){
    nDivHight4 = $("#containState4").height();
    nScrollHight4 = $(this)[0].scrollHeight;
    nScrollTop4 = Math.ceil($(this)[0].scrollTop);
    if(nScrollTop4 + nDivHight4 >= nScrollHight4){
        page4++;
        loadTaskByState(4,page4);
    }
});

$("#project-buttons").click(function (event) {
    var target = $(event.target).is('span') ? $(event.target).parent() : $(event.target);
    projectid = target.attr('data-group');
    window.location.href="task/viewtask?projectId="+projectid;
});

function loadTaskByState(state,page) {
    var judge=1;
    switch (state) {
        case 1:
            judge=pageSum1;
            break;
        case 2:
            judge=pageSum2;
            break;
        case 3:
            judge=pageSum3;
            break;
        case 4:
            judge=pageSum4;
            break;
    }

    if(page<=judge){
        $.ajax({
            type: "POST",
            url: "task/ListTaskByState",
            data: {
                projectId: projectid,
                state: state,
                page: page,
                timestamp: new Date().getTime()
            },
            dataType: "json",
            success: function (result) {
                updatelistByState(result,state);

            }
        });
    }

}

function updatelistByState(result,state){
    $.ajax({
        type:"POST",
        data: {
            pid:projectid,
        },
        url:"task/taskContent",
        success: function (html) {
            var taskContent =$(html).html();
            var judge = $("#judge").val();//是否是项目管理员
            switch (state) {
                case 1:
                    pageSum1=result.pageSum;
                    break;
                case 2:
                    pageSum2=result.pageSum;
                    break;
                case 3:
                    pageSum3=result.pageSum;
                    break;
                case 4:
                    pageSum4=result.pageSum;
                    break;
            }
            $.each(result.taskList, function (n, task) {
                var taskStr = "<div id='{taskid}'>"+ taskContent +"</div>";//$('#modal-task').clone().html()
                var priority = "", priorityBadge = "";
                priority = "重要性" + task.priority;
                if (task.priority > 85) {
                    priorityBadge = "badge-danger";
                } else if (task.priority > 75) {
                    priorityBadge = "badge-urgent";
                } else {
                    priorityBadge = "badge-normal";
                }
               // console.log(task);
                var flag=0;
                if (!$.isEmptyObject(task.taskPic)){
                    // console.log(typeof task.taskPic+"  "+task.taskPic) ;
                    var taskPics = task.taskPic.split(",");
                    var taskPicUuid = taskPics[0];
                    flag = 1;
                    taskStr = taskStr.replace(/{description}/g, task.description).replace(/{taskid}/g, task.taskid).replace(/{taskPic}/g,taskPicUuid).replace(/{priority}/g, priority).replace(/{priorityBadge}/g, priorityBadge).replace(/{amount}/g, task.amount);
                }else{

                    flag = 0;
                    taskStr = taskStr.replace(/{description}/g, task.description).replace(/{taskid}/g, task.taskid).replace(/{priority}/g, priority).replace(/{priorityBadge}/g, priorityBadge).replace(/{amount}/g, task.amount);

                }

                var $task = $(taskStr);
                $task.find("#change-state" + task.state).remove();
                $.each(task.taskMembers, function (n, member) {
                    var name = $("#modal-name").clone();
                    name = name.html();
                    name = name.replace(/{name}/, member.user.name);
                    $task.find("#name-list").append($(name));
                });
                switch (state) {
                    case 1:
                        card1.append($task);
                        break;
                    case 2:
                        card2.append($task);
                        break;
                    case 3:
                        card3.append($task);
                        break;
                    case 4:
                        card4.append($task);
                        break;
                }
                if(flag===0){//判断此任务是否有图片

                    $('#hasPic'+task.taskid).css('display','none');
                    $('#hasPic1'+task.taskid).css('display','none');
                }
                if(judge=="true"){//判断是否为管理员，如果不是管理员，不可拖拽任务
                    $("#state4,#state3,#state2,#state1").sortable({
                        cursor: "pointer",
                        revert: true
                    });
                    $("#state4,#state3,#state2,#state1").draggable({
                        cursor: "pointer",
                        zIndex: "9999",
                        helper: function(){
                            var clone = $('<div class="ui-clone">' + $(this).html() + '</div>');
                            clone.appendTo('body');
                            return clone;
                        },
                        handle:".task",
                        revert: "invalid",
                        containment: "window",
                        scroll: false,
                        connectToSortable: "#sortable"});
                    $("#state4,#state3,#state2,#state1").droppable({
                        drop: function( event, ui ) {
                            var taskid = ui.draggable.find("input").val();
                            var obj = event.srcElement || event.target;
                            var state = parseInt(obj.id.charAt(obj.id.length-1));
                            changeStateShow(ui.draggable,state,taskid);
                            changeState(taskid,state);
                        }
                    })
                }
            });
            $(".taskPic").each(function(){
                var node= $(this).parent().prev("p");
                // console.log(node.prop('tagName'));
                // if($(node).prop("tagName")=='p')
                node.css("display","inline");
            });
        }
    })
}

$('#mailRemind').click(function () {
    $.tipModal('confirm', 'info', '确认发送邮件？', function (result) {
        if(result){
            mailReminder();
        }
    })
    $('#bootbox').addClass("bootBoxStyle");
})

function mailReminder(){
    var projectId=$("#selectedProject").val();
    $.ajax({
        type: "GET",
        url: "task/mailReminder?id="+projectId,
        dataType: "text",
        success: function (data) {
            if(data.length==2){
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'邮件发送成功！'})

            }
            else{
                alert(data);
            }
        },
    })

}

function changeStateShow($item,state,taskid) {
    $item.fadeOut();
    var task = "<div id='" + taskid + "'>" + $item.html() + "</div>";
    switch (state) {
        case 1:
            $("#state1").prepend(task);
            break;
        case 2:
            $("#state2").prepend(task);
            break;
        case 3:
            $("#state3").prepend(task);
            break;
        case 4:
            $("#state4").prepend(task);
            break;
    }
}

function changeState(id,state){
    $.ajax({
        type: "POST",
        url: "task/changestate",
        data: {
            taskId: id,
            state : state,
            projectId :projectid,
            timestamp: new Date().getTime()
        },
        dataType: "json",
        success: function (result) {
        }
    });
}

