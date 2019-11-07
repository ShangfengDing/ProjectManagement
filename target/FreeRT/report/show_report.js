var reportId = $('#reportId').val();
var reportCommentDivPageUrl = "report/listReportComment";
var reportCommentDivPageUrl2 = "report/listReportCommentIgnoreOperation";


var commentSubmitButton = $('#comment-submit-button');
var commentPanel = $('#show-comment-area');
var commentNormalTextarea = $('#comment-normal-textarea');
var commentHighTextarea = $('#comment-high-textarea');

var simditor;
var toolbarParams = ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', 'link', 'image', 'hr', '|', 'indent', 'outdent', 'alignment'];
var uploadParams = {url:'report/uploadImage', params: null, fileKey: 'image', connectionCount: 1, leaveConfirm: '正在上传图片，您确定离开当前页面吗?' };

var isSubmitted = false;

;(function () {
    reportCommentDivPageIgnoreOperation(1);
    commentNormalTextarea.tah();
    $(document).keydown(function(e) {
        if (e.ctrlKey && e.which == 13 || e.which == 10) {//e.which == 13大键盘区回车 、e.which == 10 小键盘区回车
            commentSubmitButton.click();
        }
    });
    hidePreviousButton();
    hideNextButton();
})();

function reportCommentDivPage(page) {
    $.post(reportCommentDivPageUrl, {
        "report.id": reportId,
        page: page,
    }, function (html) {
        commentPanel.html(html);
    });
}
function reportCommentDivPageIgnoreOperation(page) {
    $.post(reportCommentDivPageUrl2, {
        "report.id": reportId,
        page: page,
    }, function (html) {
        commentPanel.html(html);
    });
}

function hidePreviousButton() {
    $.post("report/previousReport", {
        id: reportId,
    }, function (json) {
        if(!json.report) {
            $('#previous-button').css('visibility', 'hidden');
        }
    });
}

function hideNextButton() {
    $.post("report/nextReport", {
        id: reportId,
    }, function (json) {
        if(!json.report) {
            $('#next-button').hide();
        }
    });
}

function previous() {
    $.post("report/previousReport", {
        id: reportId,
    }, function (json) {
        if(json.report) {
            window.location.href = "report/show?id=" + json.report.id;
        } else {
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'这是第一篇'});
            $('#previous-button').css('visibility', 'hidden');
        }
    });
}

function next() {
    $.post("report/nextReport", {
        id: reportId,
    }, function (json) {
        if(json.report) {
            window.location.href = "report/show?id=" + json.report.id;
        } else {
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'这是最后一篇'});
            $('#next-button').hide();
        }
    });
}

var description;
function preSubmitComment() {
    if ($('#comment-normal-button').hasClass("active")) {
        description = commentNormalTextarea.val().trim();
        var regexp = /((http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/ig;//普通评论中的链接支持点击
        description = description.replace(regexp, "<a href='$1' target='_blank'>$1</a>");//普通评论中的链接支持点击
    } else {
        description = simditor.getValue();
    }
    if ($('#replyToUserId').val().trim() != "") {
        description = "回复@" + $('#replyToUserId').text().trim() + "：" + description;
    }
    if (description.length <= 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入评论内容'});
        return;
    }
    var loginUserName = $('#nav-user-demo ul li:first div:first span').html();
    var loginUserImgSrc = $('#nav-user-demo ul li:first img').attr("src");
    if(commentPanel.find('tbody').length == 0) {
        commentPanel.html(
            '<div class="panel panel-default front-panel">'+
            '    <div class="panel-body front-no-padding">'+
            '        <table class="table table-striped front-table" style="margin-bottom: 0px">' +
            '            <tbody>' +
            '<tr>' +
            '    <td>' +
            '        <div class="media">' +
            '            <div class="media-left">' +
            '                <a>' +
            '                    <img class="media-object img-circle img-avatar-50" style="width: 50px;height: 50px;" src="' + loginUserImgSrc + '">' +
            '                </a>' +
            '            </div>' +
            '            <div class="media-body">' +
            '                <h5 class="media-heading" style="position: relative">' +
            '                    <span class="front-text-title">' +
            '                        <a>' + loginUserName + '</a> ' +
            '                    </span>' +
            '                    <span id="resubmit-button" class="front-top-right pull-right">' +
            '                        提交中...' +
            '                    </span>' +
            '                </h5>' +
            '                <div class="front-text-break">' + description + '</div>' +
            '            </div>' +
            '        </div>' +
            '    </td>' +
            '</tr>' +
            '            </tbody>'+
            '        </table>'+
            '    </div>'+
            '</div>'
        );
    } else {
        commentPanel.find('tbody').prepend(
            '<tr>' +
            '    <td>' +
            '        <div class="media">' +
            '            <div class="media-left">' +
            '                <a>' +
            '                    <img class="media-object img-circle img-avatar-50" style="width: 50px;height: 50px;" src="' + loginUserImgSrc + '">' +
            '                </a>' +
            '            </div>' +
            '            <div class="media-body">' +
            '                <h5 class="media-heading" style="position: relative">' +
            '                    <span class="front-text-title">' +
            '                        <a>' + loginUserName + '</a> ' +
            '                    </span>' +
            '                    <span id="resubmit-button" class="front-top-right pull-right">' +
            '                        提交中...' +
            '                    </span>' +
            '                </h5>' +
            '                <div class="front-text-break">' + description + '</div>' +
            '            </div>' +
            '        </div>' +
            '    </td>' +
            '</tr>'
        );
    }
    commentSubmitButton.addClass("disabled");
    commentSubmitButton.html("提交中...");
    submitComment();
}
function submitComment() {
    $.post("report/saveReportComment", {
        "reportComment.report.id": reportId,
        "reportComment.description": description,
        replyToUserId: Number($('#replyToUserId').val().trim()),
    }, function (json) {
        commentSubmitButton.removeClass("disabled");
        commentSubmitButton.html('<span class="glyphicon glyphicon-pencil"></span> 评论');
        if($.isNumeric(json.reportComment.id) && json.reportComment.id != 0) {
            if(!json.sendMailException) {
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'评论发布成功'});
            } else {
                $.tipModal('alert', 'warning', '评论发布成功但邮件发送失败');
            }
            commentNormalTextarea.val("");
            $('#replyToUserId').val("");
            reportCommentDivPage(1);
            isSubmitted = true;
            normalComment();
        } else {
            $.tipModal('alert', 'danger', '评论发布失败');
            $('#resubmit-button').html('<a href="javascript:submitComment()">重新发布</a>');
        }
    })
}

function replyComment(userId, userName) {
    if($('#comment-normal-button').hasClass("active")) {
        commentNormalTextarea.attr("placeholder","回复@" + userName + "：");
        commentNormalTextarea.focus();
    } else {
        if(simditor.getValue() == "") {
            simditor = new Simditor({
                textarea: commentHighTextarea,
                toolbar: toolbarParams,
                upload: uploadParams,
                placeholder: "回复@" + userName + "：",
            });
        }
        simditor.focus();
    }
    commentSubmitButton.html('<span class="glyphicon glyphicon-retweet"></span> 回复');
    $('#replyToUserId').val(userId);
    $('#replyToUserId').text(userName);
}

commentNormalTextarea.blur(function () {
    if($(this).val() == ""){
        $("#replyToUserId").val("");
        $(this).attr("placeholder","按Ctrl+Enter发表评论");
        commentSubmitButton.html('<span class="glyphicon glyphicon-pencil"></span> 评论');
    }
});

function normalComment() {
    if(simditor.getValue() != "" && !isSubmitted) {
        $.tipModal('confirm', 'warning', '您已输入的内容将不被保存，确定切换？', function (result) {
            if(result) {
                $('#comment-normal-button').addClass("active");
                $('#comment-high-button').removeClass("active");
                commentNormalTextarea.removeClass("hidden");
                commentNormalTextarea.blur();
                simditor.destroy();
            }
        });
    } else {
        isSubmitted = false;
        $('#comment-normal-button').addClass("active");
        $('#comment-high-button').removeClass("active");
        commentNormalTextarea.removeClass("hidden");
        commentNormalTextarea.blur();
        simditor.destroy();
    }
}

function highComment() {
    $('#comment-normal-button').removeClass("active");
    $('#comment-high-button').addClass("active");
    commentNormalTextarea.addClass("hidden");
    Simditor.locale = 'zh-CN';
    if($('#replyToUserId').val().trim() != "") {
        simditor = new Simditor({
            textarea:commentHighTextarea,
            toolbar:toolbarParams,
            upload: uploadParams,
            placeholder:"回复@" + $('#replyToUserId').text() + "：",
        });
    } else {
        simditor = new Simditor({
            textarea: commentHighTextarea,
            toolbar: toolbarParams,
            upload: uploadParams,
            placeholder: "按Ctrl+Enter发表评论",
        });
    }
}

function deleteComment(id) {
    $.tipModal('confirm', 'danger', '确认删除？', function(result) {
        if (result) {
            $.showLoading('show');
            $.post("report/deleteReportComment", {
                "reportComment.id": id,
            }, function (json) {
                if (json) {
                    $.showLoading('reset');
                    reportCommentDivPage(1);
                }
            });
        }
    });
}

function ignoreOperationComment() {
    $("#comment-operation-button").blur();
    $("#comment-operation-button").html("<span class=\"glyphicon glyphicon-eye-open\"></span> 显示操作记录");
    $("#comment-operation-button").attr("href", "javascript:showOperationComment();");
    reportCommentDivPageUrl = "report/listReportCommentIgnoreOperation";
    reportCommentDivPage(1);
}

function showOperationComment() {
    $("#comment-operation-button").blur();
    $("#comment-operation-button").html("<span class=\"glyphicon glyphicon-eye-close\"></span> 隐藏操作记录");
    $("#comment-operation-button").attr("href", "javascript:ignoreOperationComment();");
    reportCommentDivPageUrl = "report/listReportComment";
    reportCommentDivPage(1);
}

function trashReport(id) {
    $.tipModal('confirm', 'danger', '您确定要删除这个报告？', function (result) {
        if(result) {
            $.showLoading('show');
            $.post("report/trash", {
                id:id,
            }, function (json) {
                if(json) {
                    $.showLoading('reset');
                    location.href = "report";
                }
            })
        }
    });
}

function revertReport(id) {
    $.tipModal('confirm', 'info', '您确定要还原这个已删除的报告？', function (result) {
        if(result) {
            $.showLoading('show');
            $.post("report/revert", {
                id:id,
            }, function (json) {
                if(json) {
                    $.showLoading('reset');
                    location.href = "report";
                }
            })
        }
    });
}

function deleteReport(id) {
    $.tipModal('confirm', 'danger', '您确定要彻底删除这个报告？', function (result) {
        if(result) {
            $.showLoading('show');
            $.post("report/delete", {
                id:id,
            }, function (json) {
                if(json) {
                    $.showLoading('reset');
                    location.href = "report";
                }
            })
        }
    });
}

function submitDraftReport(id) {
    $.showLoading('show');
    $.post("report/submitDraftReport", {
        id: id,
    }, function (json) {
        if (json.sendMailException == "") {
            location.href = "report/show?id=" + id;
        } else {
            $.showLoading('reset');
            $.tipModal('alert', 'warning', "提交成功但给" + json.sendMailException.substring(0, json.sendMailException.length - 1) + "的邮件发送失败", function (result) {
                location.href = "report/show?id=" + id;
            });
        }
    });
}
