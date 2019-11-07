$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "account/numberOfReport",
        dataType: "json",
        success: function (data) {
            if ($.isNumeric(data)) {
                $("#person-report-number").html(data);
            }
        },
    })
})

$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "account/numberOfTask",
        dataType: "json",
        success: function (data) {
            if ($.isNumeric(data)) {
                $("#person-task-number").html(data);
            }
        },
    })
})


$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "account/numberOfProject",
        dataType: "json",
        success: function (data) {
            if ($.isNumeric(data)) {
                $("#person-project-number").html(data);
            }
        },
    })
})

$(document).ready(function () {
    $.ajax({
        type:"GET",
        url:"log/deleteLog",
    })
})
/*function deletelog() {


}*/

