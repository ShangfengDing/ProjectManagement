// var editdescription,adddescription;
// (function() {
//     Simditor.locale = 'zh-CN';
//     adddescription = new Simditor({
//         textarea: $('#adddescription'),
//         placeholder:'请在此输入任务描述...',
//         toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', 'link', 'hr', '|', 'indent', 'outdent', 'alignment'],
//         toolbarFloat: true,
//         toolbarFloatOffset: 50,
//
//     });
// })();
function panelHeight(){
    var h = $(window).height();
    $(".task-box").css({"min-height":(h-170)+"px"});
}
function addTask(){
    var re = /^[1-9]+[0-9]*]*$/;
    var addpriority=$("#addpriority").val();
    // var editor=UE.getEditor('adddescription');
    var description=adddescription.getValue();
    if(description.trim().length == 0){
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请填写任务描述'});
    } else if(!(re.test(addpriority))|| (addpriority<=0||addpriority>100)) {
            $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请输入一百以内的正整数'});
        //$("#addpriority").rate.focus();
        return false;
    } else {
         var taskPic = "";
        $('#TaskPic-list').children('span').each(function (index, element) {
            taskPic += $(element).attr("title") + ",";
        });
            $.ajax({
                type: "POST",
                url: "task/addtask",
                data: {
                    projectId: $("#addpid").val(),
                    description:description,
                    state: $("#addstate").val(),
                    amount: $("#addamount").val(),
                    priority: $("#addpriority").val(),
                    memberIdString: $("#SelectedMemId").val(),
                    timestamp: new Date().getTime(),
                    taskPic: taskPic,
                    plan_bengintime:$("#planbegintime").val(),
                    plan_endtime:$("#planendtime").val()
                },
                dataType: "text",
                success: function (result) {
                    if (result == "\"success\"") {
                        location.href = "task/ProjectTask?id=" + $("#addpid").val();
                    } else {
                        alert("添加失败");
                    }
                }
            });
        }
}
function updateTask(){
    var re = /^[1-9]+[0-9]*]*$/;
    // var ue=UE.getEditor("editdescription");
    // var editdescription=ue.getContent();
    var description=editdescription.getValue();
    var editpriority=$("#editpriority").val();
    if(description.trim().length == 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请填写任务描述'});
    } else if(!(re.test(editpriority))|| (editpriority<=0||editpriority>100)) {
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请输入一百以内的正整数'});
        //$("#addpriority").rate.focus();
        return false;
    } else {
        var taskPic = "";
        $('#TaskPic-list').children('span').each(function (index, element) {
            taskPic += $(element).attr("title") + ",";
        });
        $.ajax({
            type: "POST",
            url: "task/updatetask",
            data: {
                taskId: taskid,
                projectId: $("#editpid").val(),
                description: description,
                state: $("#editstate").val(),
                amount: $("#editamount").val(),
                priority: $("#editpriority").val(),
                memberChange: $("#TaskMem").attr("data-clicked"),
                memberIdString: $("#SelectedMemId").val(),
                timestamp: new Date().getTime(),
                taskPic: taskPic,
                plan_bengintime: $("#planbegintime").val(),
                plan_endtime: $("#planendtime").val()
            },
            dataType: "text",
            success: function (result) {
                if (result == "\"success\"") {
                    $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '修改成功'});
                    location.href = "task/ProjectTask?id=" + $("#editpid").val();
                } else {
                    alert("编辑失败");
                }
            }
        });
    }
}
function deleteTask(){
    $.ajax({
        type: "POST",
        url: "task/deletetask",
        data: {
            taskId : taskid,
            timestamp: new Date().getTime()
        },
        dataType: "text",
        success: function (result) {
            if(result == "\"success\""){
                location.href="task/ProjectTask?id="+$("#editpid").val();
            }else{
                alert("删除失败");
            }
        }
    });
}

function openMem(){
    $("#TaskMem").attr("data-clicked","clicked");
    $.ajax({
        type: "POST",
        url: "task/getMem",
        data: {
            projectId: $("#addpid").val()?$("#addpid").val():$("#editpid").val()
        },
        dataType: "text",
        success: function (result) {
            result = JSON.parse(result);
            checkBefore();
            memShow(result);
            $("#MemModal").modal("toggle");
        }
    });
}
var taskPicCount = $('#TaskPic-list').children('span').length;
$('.fr-file-upload').frFileUpload({
    maxFileSize:10*1024*1024,//10MB

    uploadCallback:function (json) {
        if(json.uuid == null || json.uuid == "") {
            return false;
        } else {
            if(taskPicCount == 0) {
                $('#TaskPic-list').show();

                 // $('#taskPicLableTemp').show();
            }
            taskPicCount++;
            var $newFileSpan = $('<span title="' + json.uuid + '">' + json.fileFileName + '&nbsp;<a href="javascript:void(0)" onclick="deleteTaskPic(this,\'' + json.uuid + '\')">[删除]</a></span>');
            $newFileSpan.appendTo('#TaskPic-list');
            return true;
        }
    }
});

function deleteTaskPic(target, uuid) {
    $(target).html("删除中");
    $.post("task/deleteTaskPic", {
        uuid:uuid,
    }, function (json) {
        if(json) {
            taskPicCount--;
            $(target).parent().remove();
            if(taskPicCount == 0) {
                $('#TaskPic-list').hide();
            }
        } else {
            $(target).html("[删除]");
        }
    });
}
// //判断是否是数字可以直接用isNaN：
// var a="123.455";
// var b=123;
// !isNan(a) // True
// !isNaN(b) //True
// 还可以通过正则表达式判断
// //判断字符串是否为数字
// function checkRate(input)
// {
//     var re = /^[0-9]+.?[0-9]*$/;
//     if (!re.test(input.rate.value))
//     {
//         alert("请输入数字(例:0.02)");
//         input.rate.focus();
//         return false;
//     }
// }
// //判断正整数
// function checkRate(input)
// {
//     var re = /^[1-9]+[0-9]*]*$/；
// if (!re.test(input.rate.value))
// {
//     alert("请输入正整数");
//     input.rate.focus();
//     return false;
// }
// }