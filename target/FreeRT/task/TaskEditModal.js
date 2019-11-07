/**
 * Created by dsf74 on 2017/6/12.
 */
function addUserToTask(id,name){
    $("#TaskMem").attr("data-clicked","clicked");
    var selecteid = $("#SelectedMemId").val()+id+",";
    var rel = $('<span class="front-badge-xlg badge-name" style="font-size: 13px" id='+id+' data-id=' + id + '>' + name + '</span>');
    $("#TaskMem").append(rel);
    $('#SelectedMemId').val(selecteid);
    document.getElementById("add"+id).style.display = "none";
    document.getElementById("del"+id).style.display = "block";
}

function deleteUserInTask(id){
    $("#TaskMem").attr("data-clicked","clicked");
    var  selecteid = $("#SelectedMemId").val();
    selecteid=selecteid.replace(id+",","");
    $('#SelectedMemId').val(selecteid);
    var obj = document.getElementById(id);
    obj.parentNode.removeChild(obj);
    document.getElementById("add"+id).style.display = "block";
    document.getElementById("del"+id).style.display = "none";

}

function changeadvance(){
    document.getElementById("advancemode").style.display="none";
    document.getElementById("simplemode").style.display="inline";
    document.getElementById("hiddenpart1").style.display="block";
    document.getElementById("hiddenpart2").style.display="block";
}

function changesimple(){
    document.getElementById("simplemode").style.display="none";
    document.getElementById("advancemode").style.display="inline";
    document.getElementById("hiddenpart1").style.display="none";
    document.getElementById("hiddenpart2").style.display="none";
}

$('#delete').click(function () {

    $.tipModal('confirm', 'warning', '确认删除', function(result) {

        if(result){
            deleteTask();
        }
        console.log('ok warning callback: ' + result)
    })
    $("#bootbox").addClass("bootBoxStyle");
})