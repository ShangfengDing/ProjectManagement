var projectId = $('#projectId').val();

;(function () {

    reportDivPage(1);

    $('#display-buttons').click(function (event) {
        // $('#calendar').hide();
        // $('#list-area').hide();
        var target = $(event.target).is('span') ? $(event.target).parent() : $(event.target);
        switch(target.attr('data-group')) {
            case 'list':
                $('#list-area').show();
                reportDivPage(1);
                break;
            case 'calendar':
                $('#calender').show();
                $.post("report/calendar", {}, function (html) {
                    $('#list-area').html(html);
                });
                break;
        }
    });
    $('title').html('项目报告-轻项目');
})();

function reportDivPage(page) {
    $.post("report/listProjectReport", {
        projectId:projectId,
        page:page,
    }, function (html) {
        $('#list-area').html(html);
    });
    $('title').html('项目报告-轻项目');
}
