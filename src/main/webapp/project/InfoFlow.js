function showmore(pid, state) {
    if ($("#moreinfo-" + state + pid).css("display") == 'none') {//如果show是隐藏的
        $("#showdetail-" + state + pid).html("<span class='glyphicon glyphicon-minus' class='btn btn-sm btn-default'></span> 收起");
        $("#moreinfo-" + state + pid).css("display", "block");//show的display属性设置为block（显示）
    } else {//如果show是显示的
        $("#showdetail-" + state + pid).html("<span class='glyphicon glyphicon-search' class='btn btn-sm btn-default'></span> 详情");
        $("#moreinfo-" + state + pid).css("display", "none");//show的display属性设置为none（隐藏）

    }
}


function searchProject() {
    $("#loadingstyle").css("display", "block");
    var name = $("#key").val().trim();
    if (name.length == 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请填写项目名称（可模糊查询）！'});
    } else {
        $.ajax({
            url: "project/SearchProjectByIdAndName",
            type: "post",
            data: {
                name: name
            },
            success: function (html) {
                $("#loadingstyle").css("display", "none");
                $("#showresult").css("display", "block").html(html);
            }
        })
    }
}


function EnterPress(e) {
    var e = e || window.event;
    if (e.keyCode == 13) {
        document.getElementById("search-button").focus();
    }
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

var projectId = GetQueryString("id");

function projectChange(id) {
    var projectType = $("#projectType").html();
    window.location.href = "info?type="+projectType+"&&id="+id;
}

function detailChange(obj) {
    var group = obj.getAttribute("data-group");
    switch (group) {
        case 'survey' :
            $("#projectType").html("survey");
            $("#projectDetail").html("详情&nbsp;<span class=\"caret\"></span>");
            $("#loading-survey").css("display", "block");
            $.ajax({
                url: "surveyflow",
                type: "post",
                data: {
                    id: projectId
                },
                success: function (html) {
                    $("#projectType").val("survey");
                    $("#project-survey").html(html);
                    getDynamic(1);
                }
            });
            $("#loading-survey").css("display", "none");
            $("#project-survey").css("display", "block");
            $("#project-member").css("display", "none");
            $("#project-task").css("display", "none");
            $("#project-report").css("display", "none");
            $("#project-dynamics").css("display", "block");
            break;
        case 'member' :
            $("#projectType").html("user");
            $("#projectDetail").html("成员&nbsp;<span class=\"caret\"></span>");
            $("#loading-member").css("display", "block");
            $("title").html("项目成员 - 轻项目");
            $.ajax({
                url: "memberflow",
                type: "post",
                data: {
                    id: projectId
                },
                success: function (html) {
                    $("#project-member").html(html);
                }
            });
            $("#project-survey").css("display", "none");
            $("#loading-member").css("display", "none");
            $("#project-member").css("display", "block");
            $("#project-task").css("display", "none");
            $("#project-report").css("display", "none");
            $("#project-dynamics").css("display", "none");
            break;
        case 'task' :
            window.location.href='task/viewtask?projectId='+projectId;
            break;
        case 'report' :
            $("#projectType").html("report");
            $("#projectDetail").html("报告&nbsp;<span class=\"caret\"></span>");
            $("#loading-report").css("display", "block");
            $("title").html("项目报告 - 轻项目");
            $.ajax({
                url: "report/reportflow",
                type: "post",
                data: {
                    projectId: projectId
                },
                success: function (html) {
                    $("#project-report").html(html);
                }
            });
            $("#project-survey").css("display", "none");
            $("#project-member").css("display", "none");
            $("#project-task").css("display", "none");
            $("#loading-report").css("display", "none");
            $("#project-report").css("display", "block");
            $("#project-dynamics").css("display", "none");
            break;
        case 'file' :
            window.location.href='file/viewfile?id='+projectId;
            break;
    }
}

var id = $("#projectid").val();

$(document).ready(function () {
    var type = $("#projectType").html();
    switch (type) {
        case 'survey':
            $("#loading-survey").css("display", "block");
            getSurvey();
            $("#loading-survey").css("display", "block");
            break;
        case 'user':
            $("#loading-member").css("display", "block");
            getUser();
            $("#loading-member").css("display", "none");
            break;
        case 'report':
            $("#loading-report").css("display", "block");
            getReport();
            $("#loading-report").css("display", "none");
            break;
    }
});

function getReport() {
    $("#projectDetail").html("报告&nbsp;<span class=\"caret\"></span>");
    $("#loading-report").css("display", "block");
    $.ajax({
        url: "report/reportflow",
        type: "post",
        data: {
            projectId: projectId
        },
        success: function (html) {
            $("#project-report").html(html);
        }
    });
    $("#project-survey").css("display", "none");
    $("#project-member").css("display", "none");
    $("#project-task").css("display", "none");
    $("#loading-report").css("display", "none");
    $("#project-report").css("display", "block");
    $("#project-dynamics").css("display", "none");
}

function getSurvey() {

    $("#projectDetail").html("详情&nbsp;<span class=\"caret\"></span>");
    $("#loading-survey").css("display", "block");
    $.ajax({
        url: "surveyflow",
        type: "post",
        data: {
            id: projectId
        },
        success: function (html) {
            $("#project-survey").html(html);
            getDynamic(1);
        }
    });
    $("#loading-survey").css("display", "none");
    $("#project-survey").css("display", "block");
    $("#project-member").css("display", "none");
    $("#project-task").css("display", "none");
    $("#project-report").css("display", "none");
    $("#project-dynamics").css("display", "block");
    $("title").html('项目详情 - 轻项目');
}

function getUser() {
    var projectId = GetQueryString("id");
    $("#projectDetail").html("成员&nbsp;<span class=\"caret\"></span>");
    $("#loading-member").css("display", "block");
    $.ajax({
        url: "memberflow",
        type: "post",
        data: {
            id: projectId
        },
        success: function (html) {
            $("#project-member").html(html);
        }
    });
    $("#project-survey").css("display", "none");
    $("#loading-member").css("display", "none");
    $("#project-member").css("display", "block");
    $("#project-task").css("display", "none");
    $("#project-report").css("display", "none");
    $("#project-dynamics").css("display", "none");
    $("title").html('项目成员 - 轻项目');
}

function getDynamic(page) {
    $.get("getdynamic", {page: page, id: id}, function (data) {
        var strHTML = "";
        var blank = "<h4>本项目还没有动态⊙﹏⊙!</h4>";
        var list = data.logList;
        $.each(list, function (index, info) {

            strHTML += '<div class="dynamic"><div><span class="glyphicon glyphicon-pencil"></span><span style="margin-left: 10px;">'
                + info.createTime.replace(/T/g, ' ') + '</span><span style="margin-left: 10px;">' + info.userName;
            switch (info.type) {
                case "project":
                    switch (info.behavior) {
                        case "insert":
                            strHTML += '创建了' + info.pname;
                            break;
                        case "update":
                            strHTML += '修改了' + info.pname + '的项目资料';
                            break;
                        case "delete":
                            strHTML += '删除了' + info.pname;
                            break;
                        default:
                            behav_arry = info.behavior.split(":");
                            switch (behav_arry[0]) {
                                case "add admin":
                                    strHTML += '在' + info.pname + '中新增管理员' + behav_arry[1];
                                    break;
                                case "add user":
                                    strHTML += '在' + info.pname + '中新增成员' + behav_arry[1];
                                    break;
                                case "cancel admin":
                                    strHTML += '在' + info.pname + '中取消管理员' + behav_arry[1];
                                    break;
                                case "delete user":
                                    strHTML += '在' + info.pname + '中删除成员' + behav_arry[1];
                                    break;
                            }
                    }
                    break;
                case "task":
                    switch (info.behavior) {
                        case "insert":
                            strHTML += '在' + info.pname + '中新增一个<a href="task/viewtask?projectId=' + info.pid + '">任务条目</a>';
                            break;
                        case "update":
                            strHTML += '在' + info.pname + '中修改一个<a href="task/viewtask?projectId=' + info.pid + '">任务条目</a>';
                            break;
                        case "delete":
                            strHTML += '在' + info.pname + '中删除一个<a href="task/viewtask?projectId=' + info.pid + '">任务条目</a>';
                            break;
                        case "delete member":
                            strHTML += '在' + info.pname + '中删除一个<a href="task/viewtask?projectId=' + info.pid + '">任务条目</a>所分配的成员';
                            break;
                        default:
                            behav_arry = info.behavior.split(":");
                            if (behav_arry[0] == "add user") {
                                strHTML += '在' + info.pname + '中为' + behav_arry[1] + '分配一个<a href="task/viewtask?projectId=' + info.pid + '">任务条目</a>';
                            } else if (behav_arry[0] == "change state to 4") {
                                strHTML += '在' + info.pname + '中将一个<a href="task/viewtask?projectId=' + info.pid + '">任务条目</a>的状态改为 已完成';
                            } else if (behav_arry[0] == "change state to 3") {
                                strHTML += '在' + info.pname + '中将一个<a href="task/viewtask?projectId=' + info.pid + '">任务条目</a>的状态改为 待验收';
                            } else if (behav_arry[0] == "change state to 2") {
                                strHTML += '在' + info.pname + '中将一个<a href="task/viewtask?projectId=' + info.pid + '">任务条目</a>的状态改为 进行中';
                            } else if (behav_arry[0] == "change state to 1") {
                                strHTML += '在' + info.pname + '中将一个<a href="task/viewtask?projectId=' + info.pid + '">任务条目</a>的状态改为 待处理';
                            }
                    }
                    break;
                case "report":
                    switch (info.behavior) {
                        case "insert":
                            strHTML += '向' + info.pname + '中提交一份<a href="report/show?id=' + info.id + '">报告</a>';
                            break;
                        case "update":
                            strHTML += '修改了' + info.pname + '中的一份<a href="report/show?id=' + info.id + '">报告</a>';
                            break;
                    }
                    break;
                case "reportComment":
                    behav_arry = info.behavior.split(":");
                    if (behav_arry[0] == "insert") {
                        strHTML += '回复了' + behav_arry[1] + '向' + info.pname + '提交的<a href="report/show?id=' + info.id + '">报告</a>';
                    }
                    break;
            }
            strHTML += '</span></div></div>';
        });
        if (data.endpage > 1) {
            $("#navigation").html($.getDivPageHtml(data.page, data.endpage, "getDynamic"));
            $('#navigation').css('display', 'block');
        }

        if (strHTML == "" || strHTML == null) {
            $('#dynamic').html(blank);
        }
        else {
            $('#dynamic').html(strHTML);
        }
    });
}
