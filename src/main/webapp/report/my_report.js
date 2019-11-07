var projectId = 0;
var reportDivPageUrl = "report/listUserReport";
sessionStorage.setItem("projectId", "0");
sessionStorage.setItem("manageButtonFlag", "2");
//manageButtonFlag:列表/日历按钮组监听，1为列表，2为日历。
//typeButtonFlag:已发送/收件箱按钮组监听，1为已发送，2为收件箱。

    ;(function () {

        if(sessionStorage.getItem("typeButtonFlag") == "2") {
            reportDivPageUrl = "report/listManagerReport";
            $('#all-report').load("report/managerReport");
            $('#type-buttons button').removeClass("active");
            $('#type-buttons button:nth-of-type(2)').addClass("active");
            reportDivPage(1);
        }else {
            $('#all-report').load("report/userReport");
            var temp = "project-buttons-dropdown" + sessionStorage.getItem("project");
            $('#project-buttons-dropdown-title').html($("#" + temp + "").text() + " <span class=\"caret\"></span>");
            reportDivPage(1);
        }
        $("#all-report").click(function (event) {
            $('#draftBox').removeClass("active");
            $('#trashCan').removeClass("active");
            var target = $(event.target);
            if (!isNaN(target.attr('data-group'))) {
                $('#all-report button:first').addClass("active");
                $('#project-buttons-dropdown-title').html(target.text() + " <span class=\"caret\"></span>");
                projectId = target.attr('data-group');
                sessionStorage.setItem("projectId", projectId);
                if ($('#reportshow-buttons-dropdown-title').text() == " 列表 ") {
                    sessionStorage.setItem("manageButtonFlag", "1");
                    reportDivPage(1);
                } else {
                    sessionStorage.setItem("manageButtonFlag", "2");
                    reportDivPage(1);
                }
            }

        });

        $("#new-report").click(function () {
            window.location.href = "report/new";
        });

        $('#type-buttons').click(function (event) {
            $('#type-buttons button').removeClass("active");
            $('#manage-buttons').removeClass("hidden");
            var target = $(event.target).is('span') ? $(event.target).parent() : $(event.target);
            target.addClass("active");

            $('#all-report button').removeClass("active");

            switch (target.attr('data-group')) {
                case 'user':
                    $('#all-report').load("report/userReport");
                    $('#manage-buttons').removeClass("hidden");
                    sessionStorage.setItem("typeButtonFlag", "1");

                    if ($('#reportshow-buttons-dropdown-title').text() == " 列表 ") {
                        reportDivPageUrl = "report/listUserReport";
                        sessionStorage.setItem("projectId", "0");
                        sessionStorage.setItem("manageButtonFlag", "1");
                        reportDivPage(1);
                    } else {
                        sessionStorage.setItem("projectId", "0");
                        sessionStorage.setItem("manageButtonFlag", "2");
                        reportDivPage(1);
                    }
                    break;
                case 'manager':
                    $("#all-report").load("report/managerReport");
                    $('#manage-buttons').removeClass("hidden");
                    sessionStorage.setItem("typeButtonFlag", "2");
                    if ($('#reportshow-buttons-dropdown-title').text() == " 列表 ") {
                        reportDivPageUrl = "report/listManagerReport";
                        sessionStorage.setItem("projectId", "0");
                        sessionStorage.setItem("manageButtonFlag", "1");
                        reportDivPage(1);
                    } else {
                        sessionStorage.setItem("projectId", "0");
                        sessionStorage.setItem("manageButtonFlag", "2");
                        reportDivPage(1);
                    }
                    break;
                case 'draftBox':
                    $('#reportshow-buttons-dropdown-title').html("<span class=\"glyphicon glyphicon-list\"></span> 列表 " +
                        "<span class=\"caret\"></span>");
                    $('#manage-buttons').addClass("hidden");
                    sessionStorage.setItem("manageButtonFlag", "1");
                    sessionStorage.setItem("typeButtonFlag", "1");
                    target.addClass("active");
                    reportDivPageUrl = "report/listDraftReport";
                    reportDivPage(1);
                    break;
                case 'trashCan':
                    $('#reportshow-buttons-dropdown-title').html("<span class=\"glyphicon glyphicon-list\"></span> 列表 " +
                        "<span class=\"caret\"></span>");
                    $('#manage-buttons').addClass("hidden");
                    sessionStorage.setItem("manageButtonFlag", "1");
                    sessionStorage.setItem("typeButtonFlag", "1");
                    $('#all-report').removeClass("active");
                    target.addClass("active");
                    reportDivPageUrl = "report/listTrashReport";
                    reportDivPage(1);
                    break;
            }
        });

        $('#manage-buttons').click(function (event) {

            var target = $(event.target);
            if ($('#type-buttons button:first').hasClass('active')) {
                if (target.attr('data-group') == "list") {
                    $('#reportshow-buttons-dropdown-title').html("<span class=\"glyphicon glyphicon-list\"></span>"
                        + target.text() + "<span class=\"caret\"></span>");
                    reportDivPageUrl = "report/listUserReport";
                    sessionStorage.setItem("manageButtonFlag", "1");
                    reportDivPage(1);
                } else if (target.attr('data-group') == "calendar") {
                    $('#reportshow-buttons-dropdown-title').html("<span class=\"glyphicon glyphicon-calendar\"></span>"
                        + target.text() + "<span class=\"caret\"></span>");
                    sessionStorage.setItem("manageButtonFlag", "2");
                    reportDivPage(1);
                }
            } else if ($('#type-buttons button:nth-of-type(2)').hasClass('active')) {
                if (target.attr('data-group') == "list") {
                    $('#reportshow-buttons-dropdown-title').html("<span class=\"glyphicon glyphicon-list\"></span>"
                        + target.text() + "<span class=\"caret\"></span>");
                    reportDivPageUrl = "report/listManagerReport";
                    sessionStorage.setItem("manageButtonFlag", "1");
                    reportDivPage(1);
                } else if (target.attr('data-group') == "calendar") {
                    $('#reportshow-buttons-dropdown-title').html("<span class=\"glyphicon glyphicon-calendar\"></span>"
                        + target.text() + "<span class=\"caret\"></span>");
                    sessionStorage.setItem("manageButtonFlag", "2");
                    reportDivPage(1);

                }
            } else if ($('#type-buttons button:nth-of-type(3)').hasClass('active')) {
                sessionStorage.setItem("typeButtonFlag", true);
                if (target.attr('data-group') == "list") {
                    $('#reportshow-buttons-dropdown-title').html("<span class=\"glyphicon glyphicon-list\"></span>"
                        + target.text() + "<span class=\"caret\"></span>");
                    reportDivPageUrl = "report/listDraftReport";
                    sessionStorage.setItem("manageButtonFlag", "1");
                    reportDivPage(1);
                }
            } else if ($('#type-buttons button:nth-of-type(4)').hasClass('active')) {
                sessionStorage.setItem("typeButtonFlag", "1");
                if (target.attr('data-group') == "list") {
                    $('#reportshow-buttons-dropdown-title').html("<span class=\"glyphicon glyphicon-list\"></span>"
                        + target.text() + "<span class=\"caret\"></span>");
                    reportDivPageUrl = "report/listTrashReport";
                    sessionStorage.setItem("manageButtonFlag", "1");
                    reportDivPage(1);
                }
            }

        });


        //搜索面板的下拉框在选择年月之后周次联动
        // $('#year').change(searchLink);
        // $('#month').change(searchLink);

    })();

    function reportDivPage(page) {

        if (sessionStorage.getItem("manageButtonFlag") == "2") {
                $('#reportshow-buttons-dropdown-title').html("<span class=\"glyphicon glyphicon-calendar\"></span> 日历 " +
                    "<span class=\"caret\"></span>");
            $.post("report/calendar", {}, function (html) {
                $('#list-area').html(html);
            });

        } else {
            $.post(reportDivPageUrl, {
                projectId: sessionStorage.getItem("projectId"),
                page: page,
            }, function (html) {
                $('#list-area').html(html);
            });

        }

    }


// function searchLink() {
//     if($('#year').val() == 0 || $('#month').val() == 0) {
//         $('#week').empty();
//         $('#week').append($('<option>').val('0').text('--全部--'));
//     } else {
//         $('#week').empty();
//         $('#week').append($('<option>').val('0').text('--全部--'));
//         $.post("report/listWeek", {year:$('#year').val(), month:$('#month').val()}, function (json) {
//             $.each(json, function () {
//                 $('#week').append($('<option>').val(this).text(this));
//             })
//         })
//     }
// }
