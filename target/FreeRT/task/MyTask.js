var projectid = 0;
window.onload=function(){
    panelHeight();
    var h = $(window).height()-190;
    $("#containState1").height(h);
    $("#containState2").height(h);
    $("#containState3").height(h);
    $("#containState4").height(h);
    var projectId = $("#projectId").val();
    if(projectId==""){
        showMyTask(0);
    } else {
        showMyTask(projectId);
    }

}
// $("#project-buttons").click(function (event) {
//     var target = $(event.target).is('span') ? $(event.target).parent() : $(event.target);
//     projectid = target.attr('data-group');
//     showMyTask(projectid);
// });
function showMyTask(pid) {
    if(pid == 0){
        var ProjectName = "全部";
        $("#AllMyTask").html(""+ProjectName+"<span class=\"caret\"></span>");
    } else {
        var ProjectName = $("#ProjectId" + pid).html();
        $("#AllMyTask").html(""+ProjectName+"<span class=\"caret\"></span>");
    }
    $("#taskloading").css("display", "block");
    $("#taskcontainer").css("display", "none");
        $.ajax({
            type: "GET",
            url: "task/mytask",
            data: {
                projectId :pid,
                timestamp: new Date().getTime()
            },
            dataType: "json",
            success: function (result) {
                updatelist(result);
            }
        });
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

function updatelist(result) {
    var card1 = $("#state1");
    var card2 = $("#state2");
    var card3 = $("#state3");
    var card4 = $("#state4");
    card1.empty();
    card2.empty();
    card3.empty();
    card4.empty();
    $.each(result.taskMemberList, function (n, taskmember) {
        var task = taskmember.task;
        var taskStr ="<div id='{taskid}'>"+ $('#modal-task').clone().html() +"</div>";
        var priority = "", priorityBadge = "";
        priority = "重要性"+task.priority;
        if(task.priority > 85){
            priorityBadge = "badge-danger";
        }else if(task.priority > 75){
            priorityBadge = "badge-urgent";
        }else {
            priorityBadge = "badge-normal";
        }
        taskStr = taskStr.replace(/{description}/g,task.description).replace(/{taskid}/g, task.taskid).replace(/{priority}/g, priority).replace(/{priorityBadge}/g, priorityBadge).replace(/{amount}/g, task.amount).replace(/{myName}/g,taskmember.user.name);
        var flag=0;
        if (!$.isEmptyObject(task.taskPic)){
            console.log(typeof task.taskPic+"  "+task.taskPic) ;
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
            if(member!=null) {
                var name = $("#modal-name").clone();
                name = name.html();
                name = name.replace(/{name}/, member.user.name);
                $task.find("#name-list").append($(name));
            }
        });
        switch (task.state){
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
        if(flag===0){

            $('#hasPic'+task.taskid).css('display','none');
            $('#hasPic1'+task.taskid).css('display','none');
        }
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
    });
    $("#taskloading").css("display", "none");
    $("#taskcontainer").css("display", "block");
    $("#taskloading").addClass("hidden");
    $("#taskloading").removeClass("hidden");
}

function changeStateShow($item,state,taskid) {
    $item.fadeOut();
    var task="<div id='" + taskid + "'>"+$item.html() +"</div>";
    switch (state){
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
