/**
 * Created by dsf74 on 2017/5/10.
 */
;(function () {
    showAllPeople();
    // showLatestPeople();
})();

function showAllPeople() {
    $("#model-all-people").addClass("model-people-item");
    $("#model-all-people").html("加载中...");
    $.get("project/listPeople", function (html) {
        $('#model-all-people').removeClass("model-people-item");
        $('#model-all-people').html(html);
        $(".model-people-item span").bind("click", selectPeople);
        loadSelectedPeople();
    });
    $("title").html('项目成员 - 轻项目');
}

function loadSelectedPeople() {
    var selectedPeopleIdString = $('#selected-people-id').val();
    if(selectedPeopleIdString != "") {
        var selectedPeopleId = selectedPeopleIdString.split(",");
        for(var i=0; i<selectedPeopleId.length-1; i++) {
            if(selectedPeopleId[i] != "") {
                var li = $('<li></li>');
                li.append($('<a class="pull-right" onclick="deleteSelectedPeople(this)">删除</a>'));
                li.append($('<span class="model-selected-people-id" data-id="' + selectedPeopleId[i] + '">' + getPeopleNameByPeopleId(selectedPeopleId[i]) + '</span>'));
                $("#model-selected-people").append(li);
            }
        }
    }
    countSelectedPeopleNumber();
}

function search() {
    $("#freeshare-searchresult").css("display","block").click();
}

function searchuser() {
    var keyword = $("#freeshare-searchcontent").val().trim();
    if(keyword != ""){
        $("#model-search-people").addClass("model-people-item");
        $.get("project/findUser",{keyword:keyword},function(html){
            $('#model-search-people').removeClass("model-people-item");
               $("#model-search-people").html(html);
                $(".model-people-item span").bind("click", selectPeople);
                loadSelectedPeople();
        });
    }
}

function getPeopleNameByPeopleId(id) {
    return $('#model-all-people').find('[data-id='+ id +']').html();
}

// function showLatestPeople(){
//     $("#model-latest-people").addClass("model-people-item");
//     $("#model-latest-people").html("加载中...");
//     $.get("report/listPeople", function (html) {
//         $('#model-latest-people').removeClass("model-people-item");
//         $('#model-latest-people').html(html);
//         $(".model-people-item span").bind("click", selectPeople);
//     });
// }

// $("#model-search-button").on('click', function () {
//     var keyword = $("#model-search-text").val().trim();
//     if (keyword != '') {
//         $("#model-search-title").css("display","block").click();
//         $("#model-search-people").addClass("model-people-item");
//         $("#model-search-people").html("加载中...");
//         $.get("report/listPeople", function (html) {
//             $('#model-search-people').removeClass("model-people-item");
//             $('#model-search-people').html(html);
//             $(".model-people-item span").bind("click", selectPeople);
//         });
//     }
// });
//
// $("#model-search-text").keyup(function (event) {
//     if(event.which == 13) {//按Enter键
//         $("#model-search-button").click();
//     }
// });

function selectPeople() {
    var PeopleName = $(this).text();
    var PeopleId = $(this).attr("data-id");
    var flag = 0;
    var selectedPeople = $("#model-selected-people li");
    for(var i=0;i<selectedPeople.length;i++){
        if(selectedPeople.eq(i).find("span").text() == PeopleName) {
            flag = 1;
        }
    }
    if(flag === 0){
        var li = $('<li style="margin-left: 0px;list-style-type:none"></li>');
        li.append($('<a class="pull-right" style="cursor:pointer" onclick="deleteSelectedPeople(this)">删除</a>'));
        li.append($('<span class="model-selected-people-id" style="cursor:pointer" data-id="' + PeopleId + '">' + PeopleName + '</span>'));
        $("#model-selected-people").append(li);
    }
    countSelectedPeopleNumber();
}

function deleteSelectedPeople(obj) {
    $(obj).parent("li").remove();
    countSelectedPeopleNumber();
}

function clearSelectedPeople() {
    $("#model-selected-people li").remove();
    countSelectedPeopleNumber();
}

function countSelectedPeopleNumber() {
    $("#model-people-num span").text($("#model-selected-people li").length);
}

function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

function submitSelectedPeople(){
    // var id = $("#projectid").val();
    var id= GetQueryString("id");
    var PeopleNameString = "";
    var PeopleIdString = "";
    var length = $(".model-selected-people-id").length;
    if(length > 0){
        for(var i=0;i<length;i++){
            var span = $(".model-selected-people-id:eq("+i+")");
            PeopleNameString += span.text() + "&nbsp;&nbsp;";
            PeopleIdString += span.attr("data-id") + ",";
        }
        $("#selected-people-id").val(PeopleIdString);
        var PeopleIds = $('#selected-people-id').val();
        $.post("project/addUser",{PeopleIdString:PeopleIds,id:id},function(data){
            if(!data){
                // location.href="memberflow?id="+id;
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'添加成员成功！'})
                /*$("#selected-people-name").html("<label>已选择：</label> " + PeopleNameString);*/
                $('.modal').modal('hide');
                location.href="info?type=user&&id=" + id;
            } else {
                $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'该成员已在项目组中，不能重复添加！'});
            }
        });

    }else{
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请至少选择一个参与人员'});
    }

}
