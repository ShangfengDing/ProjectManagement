<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id='calendar' style="max-width: 1140px;background: #f5f5f5;padding: 15px;margin: 0 auto 20px auto;"></div>
    <script>
        // alert(sessionStorage.getItem(typeButtonFlag));
        var geturl = "";
        if(sessionStorage.getItem("typeButtonFlag") == "1"){
            geturl = "report/listUserReportForCalendar";
        }else {
            geturl = "report/listManagerReportForCalendar";
        }

        $(document).ready(function() {
            $.ajax({
                type: "POST",
                url: geturl,
                data: {projectId: sessionStorage.getItem("projectId")},
                dataType: "json",
                success: function (reportList) {
                    var reportEvent =[];
                    $.each (reportList,function (n,report) {
                        var reportEventDetail = {};
                        reportEventDetail.title = report.name.substring(0,report.name.length-22);
                        reportEventDetail.start = report.time.substring(0,10);
                        reportEventDetail.url = 'report/show?id='+report.id;
                        reportEvent.push(reportEventDetail);
                    })
                    var thisday= moment().format('YYYY-MM-DD');
                    var initialLocaleCode = 'zh-cn';
                    $('#calendar').fullCalendar({
                        header: {
                            left: 'prev,next today',
                            center: 'title',
                            right: 'month,basicWeek,basicDay'
                        },
                        locale: initialLocaleCode,
                        defaultDate: thisday,//'2017-11-12',
                        navLinks: true, // can click day/week names to navigate views
                        editable: true,
                        eventLimit: true, // allow "more" link when too many events
                        events: reportEvent
                        //[{ title: 'shibaidezhoubao', start: '2017-11-01'}]
                    });


                }
            });
        });

    </script>

