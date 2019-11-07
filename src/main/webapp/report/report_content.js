
function trashReport(id) {
    $.tipModal('confirm', 'danger', '您确定要删除这个报告？', function (result) {
        if(result) {
            $.showLoading('show');
            $.post("report/trash", {
                id: id,
            }, function (json) {
                if(json) {
                    $.showLoading('reset');
                    window.location.reload();
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
                id: id,
            }, function (json) {
                if(json) {
                    $.showLoading('reset');
                    window.location.reload();
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
                id: id,
            }, function (json) {
                if(json) {
                    $.showLoading('reset');
                    window.location.reload();
                }
            })
        }
    });
}

function submitDraftReport(id) {
    $.tipModal('confirm', 'success', '您确定要提交这个报告？', function (result) {
        if(result) {
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
    });
}
