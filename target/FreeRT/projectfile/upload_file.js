var projectId = $('#projectId').val();
var uploadButton = $('#file_upload_button');
var uploadContext = $('#file-url');
var realName = $('#file-name');
var fileType = $('#dropdown');
var filePanel = $('#show-file-area');
var description = $('#file-description');
var fileDivPageUrl = "file/listFile";
var fileSearchUrl = "file/searchFile";

$(document).ready(function(){
    $.post(fileDivPageUrl, {
        "project.id": projectId,/*待修改*/
        page: 1
    }, function (html) {
        filePanel.html(html);
    });
});

function searchFile(){
        var search = $("#file-search").val();
        if(search.length==0){
            fileDivPage(1);
        }
        else{
            $.post(fileSearchUrl,{
                "project.id":projectId,
                "search":search,
            },function(html){
                filePanel.html(html);
            });
        }

}
//
// $('#file-select').click(function (event) {
//     var target = $(event.target).is('span') ? $(event.target).parent() : $(event.target);
//
//     switch (target.attr('data-group')) {
//         case 'list' :
//             $("#file-upload").css("display", "none");
//             $("#show-file-area").css("display", "block");
//             $("#file-search").css("display","block");
//             break;
//         case 'upload' :
//             $("#file-upload").css("display", "block");
//             $("#show-file-area").css("display", "none");
//             $("#file-search").css("display","none");
//             break;
//     }
// })

$('#to-list').click(function () {
    $("#file-upload").css("display", "none");
    $("#show-file-area").css("display", "block");
    $("#file-search").css("display","block");
})

function fileEdit(id,description,url){
    $("#file-edit").css("display", "block");
    $("#show-file-area").css("display", "none");
    $("#file-search").css("display","none");
    $('#bread-4').css("display","inline");
    $('#file-op-0').css("display","none");
    $("#file-op-2").html("编辑文件 <span class=\"caret\"></span>");
    $("#title").html("<a data-group=\"list\" type=\"button\" id=\"back-to-file\" className=\"btn-small btn-default dropdown-toggle\"\n" +
        "                                style=\"background-color: #f5f5f5;border:0;cursor: pointer;\" onclick=\"fileSelect(this)\">\n" +
        "                文件&nbsp;\n" +
        "            </a>");

    $("#file-description-edit").val(description);
    $("#file-url-edit").val(url);
    $("#file_upload_button-edit").click(function () {
        if ($("#file-description-edit").val().length <= 0) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入文件说明'});
            return;
        }
        if ($("#file-url-edit").val().length <= 0) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入URL'});
            return;
        }
        if(/^[\u3220-\uFA29]+$/.test($("#file-url-edit").val())){
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '链接不可以包含中文'});
            return;
        }
        $("#file_upload_button-edit").addClass("disabled");
        $("#file_upload_button-edit").html("修改中...");

        $.post("file/editFile", {
            "fileEntity.id": id,
            "fileEntity.url": $("#file-url-edit").val(),
            "fileEntity.description": $("#file-description-edit").val(),
        }, function (json) {
            if(json) {
                $("#file_upload_button-edit").removeClass("disabled");
                $("#file_upload_button-edit").html('修改文件');
                // if($.isNumeric(json.reportComment.id) && json.reportComment.id != 0) {/*待修改*/
                $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '修改成功'});
                fileDivPage(1);/*待修改*/
                // } else {
                //     $.tipModal('alert', 'danger', '上传失败');
                //     //$('#resubmit-button').html('<a href="javascript:submitComment()">重新发布</a>');
                // }
            }
        })
    })
}


function fileSelect(event) {
    var target = event.getAttribute("data-group");
    switch (target) {
        case 'upload' :
            // $("#file-upload").css("display", "none");
            // $("#show-file-area").css("display", "block");
            // $("#file-search").css("display","block");
            //$("#file-op").html("上传文件");
            break;
        case 'link' :
            $("#file-upload").css("display", "block");
            $("#show-file-area").css("display", "none");
            $("#file-search").css("display","none");
            $('#bread-4').css("display","inline");
            $('#file-op-0').css("display","none");
            $("#file-edit").css("display","none");
            $("#file-op-2").html("分享链接 <span class=\"caret\"></span>");
            $("#title").html("<a data-group=\"list\" type=\"button\" id=\"back-to-file\" className=\"btn-small btn-default dropdown-toggle\"\n" +
                "                                style=\"background-color: #f5f5f5;border:0;cursor: pointer;\" onclick=\"fileSelect(this)\">\n" +
                "                文件&nbsp;\n" +
                "            </a>");
            break;
        case 'list':
            $("#file-upload").css("display", "none");
            $("#show-file-area").css("display", "block");
            $("#file-search").css("display","block");
            $('#bread-4').css("display","none");
            $('#file-op-0').css("display","inline");
            $("#file-edit").css("display","none");
            $("#title").html("<a type=\"button\" id=\"projectDetail\" class=\"btn-small btn-default dropdown-toggle\" style=\"background-color: #f5f5f5;border:0;cursor: pointer;\" data-toggle=\"dropdown\">\n" +
                "                                文件&nbsp;<span class=\"caret\"></span>\n" +
                "                            </a>\n" +
                "                            <ul class=\"dropdown-menu\" role=\"menu\" style=\"border: 0px;\">\n" +
                "                                <li><a data-group=\"survey\" onclick=\"detailChange(this)\" style=\"cursor:pointer\">详情</a></li>\n" +
                "                                <li><a data-group=\"member\" onclick=\"detailChange(this)\" style=\"cursor:pointer\">成员</a></li>\n" +
                "                                <li><a data-group=\"task\" onclick=\"detailChange(this)\" style=\"cursor:pointer\">任务</a></li>\n" +
                "                                <li><a data-group=\"report\" onclick=\"detailChange(this)\" style=\"cursor:pointer\">报告</a></li>\n" +
                "                                <li><a data-group=\"file\" onclick=\"detailChange(this)\" style=\"cursor:pointer\">文件</a></li>\n" +
                "                            </ul>");
            $("#file-op-2").html("新建文件 <span class=\"caret\"></span>");
            break;
    }
}

function fileDivPage(page) {
    $.post(fileDivPageUrl, {
        "project.id": projectId,
        page: page
    }, function (html) {
        filePanel.html(html);
    });
}

function preuploadFile(){
    // uploadContext = uploadContext.val().trim();
    // var regexp = /((http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/ig;//url链接支持点击
    // uploadContext = uploadContext.replace(regexp, "<a href='$1' target='_blank'>$1</a>");//url链接支持点击
    if (description.val().length <= 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入文件说明'});
        return;
    }
    if (uploadContext.val().length <= 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入URL'});
        return;
    }
    if(/^[\u3220-\uFA29]+$/.test(uploadContext.val())){
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '链接不可以包含中文'});
        return;
    }
    var loginUserName = $('#nav-user-demo ul li:first div:first span').html();//抓取当前用户名
    var loginUserImgSrc = $('#nav-user-demo ul li:first img').attr("src");//抓取当前用户头像
    if(filePanel.find('tbody').length == 0) {
        filePanel.html(
           '<div class="fixed-table-container" style="">\n' +
            '            <div class="fixed-table-header" style="display: block;margin-right: 0px">\n' +
            '                <table  class="table table-hover table-bordered" style="margin-top: -81px;">\n' +
            '                    <thead>\n' +
            '                    <tr>\n' +
            '                        <th style="text-align: center; vertical-align: middle; " data-field="id">\n' +
            '                            <div class="th-inner sortable both">文件说明</div>\n' +
            '                        </th>' +
            // '                         <th style="text-align: center; ">\n' +
            // '                            <div class="th-inner sortable both">描述</div>\n' +
            // '                             <div class="fht-cell"></div>\n' +
            // '                         </th>'+
            '                        <th style="text-align: center; " data-field="name" data-not-first-th="">\n' +
            '                            <div class="th-inner sortable both">时间</div>\n' +
            '                        </th>\n' +
            '                        <th style="text-align: center; " data-field="price">\n' +
            '                            <div class="th-inner sortable both">创建人</div>\n' +
            '                        </th>\n' +
            '                        <th style="text-align: center; " data-field="operate">\n' +
            '                            <div class="th-inner ">操作</div>\n' +
            '                        </th>\n' +
            '                    </tr>\n' +
            '                    </thead>' +
            '<tbody>' +
            '<tr>' +
            // '    <td style="text-align: center; vertical-align: middle; ">' +
            // '       <a>' + realName.val() + '</a>' +
            // '    </td>' +
            '      <td style="text-align: center; ">' +description.val() +'</td>'+
            '   <td style="text-align: center; ">提交中...</td>' +
            '   <td style="text-align: center; ">' + loginUserName + '</td>' +
            '   <td style="text-align: center; ">'+
            '       <a class="like" href="javascript:void(0)" title="Like">详情</a>\<n></n>' +
            '       <a class="remove" href="javascript:void(0)" title="Remove">删除</a>' +
            '   </td> '+
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '</div>' +
            '</div>'
        );
    } else {
        filePanel.find('tbody').prepend(
            '<tr>' +
            '    <td style="text-align: center; vertical-align: middle; ">' +
            '       <a>' + description.val() + '</a>' +
            '    </td>' +
            '   <td style="text-align: center; ">提交中...</td>' +
            '   <td style="text-align: center; ">' + loginUserName + '</td>' +
            '   <td style="text-align: center; ">'+
            '       <a class="like" href="javascript:void(0)" title="Like">详情</a>\<n></n>' +
            '       <a class="remove" href="javascript:void(0)" title="Remove">删除</a>' +
            '   </td> '+
            '</tr>'
        );
    }
    uploadButton.addClass("disabled");
    uploadButton.html("提交中...");
    uploadFile();
}

function uploadFile() {
    /*待添加*/
    $.post("file/uploadFile", {
        "fileEntity.project.id": projectId,
        "fileEntity.url": uploadContext.val(),
        "fileEntity.description": description.val(),
        "fileEntity.source": 1,
    }, function (html) {
        filePanel.html(html);
        uploadButton.removeClass("disabled");
        uploadButton.html('新增文件');
        // if($.isNumeric(json.reportComment.id) && json.reportComment.id != 0) {/*待修改*/
            $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'上传成功'});
            uploadContext.val("");
            description.val("");
            realName.val("");
            fileDivPage(1);/*待修改*/
        // } else {
        //     $.tipModal('alert', 'danger', '上传失败');
        //     //$('#resubmit-button').html('<a href="javascript:submitComment()">重新发布</a>');
        // }
    })
}

// function downloadFile(source,url){
//     switch (source) {
//         case(1):
//             window.open(url);
//             break;
//     }
//     return false;
// }

function deleteFile(id) {
    $.tipModal('confirm', 'danger', '确认删除？', function(result) {
        if (result) {
            $.showLoading('show');
            $.post("file/deleteFile", {
                "fileEntity.id": id,
            }, function (json) {
                if (json) {
                    $.showLoading('reset');
                    fileDivPage(1);
                }
            });
        }
    });
}

function starFile(id){
    $.post("file/starFile", {
        "fileEntity.id": id,
    }, function (json) {
        if (json) {
            $.showLoading('reset');
            fileDivPage(1);
        }
    });
}
