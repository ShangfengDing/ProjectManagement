/**
 * Created by dsf74 on 2017/5/11.
 */
/**
 * Created by dsf74 on 2017/5/10.
 */
;(function () {
    showAllManager();
    // showLatestManager();
})();

function showAllManager() {
    $("#model-all-manager").addClass("model-people-item");
    $("#model-all-manager").html("加载中...");
    $.get("project/listPeople", function (html) {
        $('#model-all-manager').removeClass("model-people-item");
        $('#model-all-manager').html(html);
        $(".model-people-item span").bind("click", selectManager);
        loadSelectedManager();
    });
}

function loadSelectedManager() {
    var selectedManagerIdString = $('#selected-manager-id').val();
    if(selectedManagerIdString != "") {
        var selectedManagerId = selectedManagerIdString.split(",");
        for(var i=0; i<selectedManagerId.length-1; i++) {
            if(selectedManagerId[i] != "") {
                var li = $('<li></li>');
                li.append($('<a class="pull-right" onclick="deleteSelectedManager(this)">删除</a>'));
                li.append($('<span class="model-selected-manager-id" data-id="' + selectedManagerId[i] + '">' + getManagerNameByManagerId(selectedManagerId[i]) + '</span>'));
                $("#model-selected-manager").append(li);
            }
        }
    }
    countSelectedManagerNumber();
}

function getManagerNameByManagerId(id) {
    return $('#model-all-manager').find('[data-id='+ id +']').html();
}

// function showLatestManager(){
//     $("#model-latest-manager").addClass("model-people-item");
//     $("#model-latest-manager").html("加载中...");
//     $.get("report/listManager", function (html) {
//         $('#model-latest-manager').removeClass("model-people-item");
//         $('#model-latest-manager').html(html);
//         $(".model-people-item span").bind("click", selectManager);
//     });
// }

// $("#model-search-button").on('click', function () {
//     var keyword = $("#model-search-text").val().trim();
//     if (keyword != '') {
//         $("#model-search-title").css("display","block").click();
//         $("#model-search-manager").addClass("model-people-item");
//         $("#model-search-manager").html("加载中...");
//         $.get("report/listManager", function (html) {
//             $('#model-search-manager').removeClass("model-people-item");
//             $('#model-search-manager').html(html);
//             $(".model-people-item span").bind("click", selectManager);
//         });
//     }
// });
//
// $("#model-search-text").keyup(function (event) {
//     if(event.which == 13) {//按Enter键
//         $("#model-search-button").click();
//     }
// });

function selectManager() {
    var ManagerName = $(this).text();
    var ManagerId = $(this).attr("data-id");
    var flag = 0;
    var selectedManager = $("#model-selected-manager li");
    for(var i=0;i<selectedManager.length;i++){
        if(selectedManager.eq(i).find("span").text() == ManagerName) {
            flag = 1;
        }
    }
    if(flag === 0){
        var li = $('<li></li>');
        li.append($('<a class="pull-right" onclick="deleteSelectedManager(this)">删除</a>'));
        li.append($('<span class="model-selected-manager-id" data-id="' + ManagerId + '">' + ManagerName + '</span>'));
        $("#model-selected-manager").append(li);
    }
    countSelectedManagerNumber();
}

function deleteSelectedManager(obj) {
    $(obj).parent("li").remove();
    countSelectedManagerNumber();
}

function clearSelectedManager() {
    $("#model-selected-manager li").remove();
    countSelectedManagerNumber();
}

function countSelectedManagerNumber() {
    $("#model-manager-num span").text($("#model-selected-manager li").length);
}

function submitSelectedManager(){
    var ManagerNameString = "";
    var ManagerIdString = "";
    var length = $(".model-selected-manager-id").length;
    if(length > 0){
        for(var i=0;i<length;i++){
            var span = $(".model-selected-manager-id:eq("+i+")");
            ManagerNameString += span.text() + "&nbsp;&nbsp;";
            ManagerIdString += span.attr("data-id") + ",";
        }

        $("#selected-manager-id").val(ManagerIdString);
        $("#selected-manager-name").html("<label>已选择：</label> " + ManagerNameString);
        $('.modal').modal('hide');
        location.href="info?type=user&&id=" + id;
    }else{
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请至少选择一个负责人'});
    }
}
