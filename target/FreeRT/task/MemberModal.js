function memShow(result){
    var flag=0;
    var rel='';
    $("#ProName").html(result.projectName);
    $.each(result.userList, function (n, user) {
        rel += '<div class="front-selectitem"><span data-id="' + user.id + '">'+ user.name + '</span></div>';
        flag=1;
    });
    if(!flag){
        rel += '<div style=\"padding-top: 3%;padding-right: 5%; padding-left: 5%;\">无成员</div>';
    }
    $("#ProMember").html(rel);
    $("#ProMember span").bind("click", checkMem);
}
function checkBefore(){
    clearList();
    var membefore = $("#TaskMem span");
    if(membefore.length){
        for(var i=0; i< membefore.length; i++){
            var selectedid = ($(membefore[i]).data("id"));
            var selectedname = ($(membefore[i]).text());
            var relli = $('<li class="front-selectitem"></li>');
            var rela = $('<a class="pull-right" name="deleteuser" onclick="deleteSelected(this)">删除</a>');
            var relspan = $('<span  class="selectedmem" data-id="'+selectedid+'">'+ selectedname +'</span>');
            $("#userselected").append(relli);
            relli.append(rela);
            relli.append(relspan);
        }
    }
}
function checkMem(){
    var username = $(this).text();
    var userid = $(this).attr("data-id");
    var flag = 0;
    var selected = $("#userselected li");
        for(var i=0;i<selected.length;++i){
            var selectedid = selected.eq(i).find("span").attr("data-id");
            if(selectedid == userid){
                flag = 1;
            }
        }
        if(flag == 0){
            if(selected.length<2){
                var relli = $('<li class="front-selectitem"></li>');
                var rela = $('<a class="pull-right" name="deleteuser" onclick="deleteSelected(this)">删除</a>');
                var relspan = $('<span class="selectedmem" data-id="'+userid+'">'+ username +'</span>');
                $("#userselected").append(relli);
                relli.append(rela);
                relli.append(relspan);
            } else {
                $("#numalert").css("display","block");
            }
        }
        calNum();
}
function calNum(){
    var num =$("#MemNum span");
    var selected = $("#userselected li");
    num.text(selected.length);
    if(selected.length<2){
        $("#numalert").css("display","none");
    }
}
function deleteSelected(obj)
{
    $(obj).parent("li").remove();
    calNum();
}
function clearList() {
    $("#userselected li").remove();
    calNum();
}
function clearMem(){
    $("span[class='front-badge-xlg badge-name']").remove();
    $("#TaskMem").attr("data-clicked","clicked");
    clearList();
}

function submitMem(){
    $("#TaskMem").children().remove();
    var memidstring = "";
    var selectedmem = $("span[class='selectedmem']");
    if(selectedmem.length) {
        for (var i = 0; i < selectedmem.length; i++) {
            var selectedid = ($(selectedmem[i]).data("id"));
            var selectedname = ($(selectedmem[i]).text());
            var rel = $('<span class="front-badge-xlg badge-name" data-id=' + selectedid + '>' + selectedname + '</span>');
            $("#TaskMem").append(rel);
            memidstring += selectedid + ",";
        }
        $('#SelectedMemId').val(memidstring);
        $('#MemModal').modal('hide');
    }else{
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请至少选择一个成员'});
    }
}