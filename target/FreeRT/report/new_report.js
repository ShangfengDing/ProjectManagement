var simditor;
var isSubmitted = false;
var DAILY_TEMPLATE = "<h3>1.今日工作总结</h3><table><colgroup><col width='6%'><col width='94%'></colgroup><thead><tr><th>时间<br></th><th>工作内容<br></th></tr></thead><tbody><tr><td>上午<br></td><td><br></td></tr><tr><td>下午<br></td><td><br></td></tr><tr><td>晚上<br></td><td><br></td></tr></tbody></table><h3>2.明日工作计划</h3><p><br></p><h3>3.建议与想法</h3><p><br></p>";
var WEEKLY_TEMPLATE = "<h3>1.本周工作总结</h3><table><colgroup><col width='7%'><col width='31%'><col width='31%'><col width='31%'></colgroup><thead><tr><th><br></th><th>上午<br></th><th>下午<br></th><th>晚上<br></th></tr></thead><tbody><tr><td>周一<br></td><td><br></td><td><br></td><td><br></td></tr><tr><td>周二<br></td><td><br></td><td><br></td><td><br></td></tr><tr><td>周三<br></td><td><br></td><td><br></td><td><br></td></tr><tr><td>周四<br></td><td><br></td><td><br></td><td><br></td></tr><tr><td>周五<br></td><td><br></td><td><br></td><td><br></td></tr><tr><td>周六<br></td><td><br></td><td><br></td><td><br></td></tr><tr><td>周日<br></td><td><br></td><td><br></td><td><br></td></tr></tbody></table><h3>2.本周解决的问题</h3><p><br></p><h3>3.本周仍未解决的问题</h3><p><br></p><h3>4.本周新发现的问题</h3><p><br></p><h3>5.下周工作计划</h3><p><br></p><h3>6.对项目的建议</h3><p><br></p><h3>7.其他的想法</h3><p><br></p>";
var MONTHLY_TEMPLATE = "<h3>1.本月工作总结</h3><table><colgroup><col width='8%'><col width='92%'></colgroup><thead><tr><th>周次<br></th><th>工作内容<br></th></tr></thead><tbody><tr><td>第一周<br></td><td><br></td></tr><tr><td>第二周<br></td><td><br></td></tr><tr><td>第三周<br></td><td><br></td></tr><tr><td>第四周<br></td><td><br></td></tr><tr><td>第五周<br></td><td><br></td></tr></tbody></table> <h3>2.本月解决的问题</h3><p><br></p><h3>3.本月仍未解决的问题</h3><p><br></p><h3>4.本月新发现的问题</h3><p><br></p><h3>5.下月工作计划</h3><p><br></p><h3>6.对项目的建议</h3><p><br></p><h3>7.学习的收获</h3><p><br></p><h3>8.其他的想法</h3><p><br></p>";
var ANNUAL_TEMPLATE = "<h3>1.工作总结</h3><table><colgroup><col width='5%'><col width='45%'><col width='5%'><col width='45%'></colgroup><thead><tr><th>月份<br></th><th>工作内容<br></th><th>月份<br></th><th>工作内容<br></th></tr></thead><tbody><tr><td>1<br></td><td><br></td><td>2<br></td><td><br></td></tr><tr><td>3<br></td><td><br></td><td>4<br></td><td><br></td></tr><tr><td>5<br></td><td><br></td><td>6<br></td><td><br></td></tr><tr><td>7<br></td><td><br></td><td>8<br></td><td><br></td></tr><tr><td>9<br></td><td><br></td><td>10<br></td><td><br></td></tr><tr><td>11<br></td><td><br></td><td>12<br></td><td><br></td></tr></tbody></table><h3>2.今年完成的项目</h3><p><br></p><h3>3.仍在进行的项目</h3><p><br></p><h3>4.明年新的项目</h3><p><br></p><h3>5.明年工作计划</h3><p><br></p><h3>6.对项目的建议</h3><p><br></p><h3>7.学习的收获</h3><p><br></p><h3>8.其他的想法</h3><p><br></p>";
var attachmentCount = $('#attachment-list').children('span').length;

window.onload=function(){
    var refreshTime = setInterval("continueFresh()",100000);
}

function continueFresh() {
    $.post("report/refreshTime",{},function () {})
}

;(function () {

    $('#datepicker').datepicker({dateFormat: "yy.mm.dd"});
    $('#datepicker').datepicker("setDate", new Date());

    Simditor.locale = 'zh-CN';
    simditor = new Simditor({
        textarea:$('#simditor'),
        toolbar:['title','bold','italic','underline','strikethrough','fontScale','color','|','ol','ul','blockquote','code','table','link','image','hr','|','indent','outdent','alignment'],
        toolbarFloat:true,
        toolbarFloatOffset:50,
        upload:{
            url:'report/uploadImage',
            params: null,
            fileKey: 'image',
            connectionCount: 1,
            leaveConfirm: '正在上传图片，您确定离开当前页面吗?',
        }
    });

    var title = "周报";
    $('#name').attr("disabled", true);
    $('#name').val("项目" + title + "-" + $('#user-name').val() + "-" + $('#select-week-div select').val());
    simditor.setValue(WEEKLY_TEMPLATE);

    $('#report-type-buttons').click(function (event) {
        $.tipModal('confirm', 'warning', '您已输入的内容将不被保存，确定改变报告类型？', function (result) {
            if(result) {
                $('#name').val("");
                $('#select-day-div').hide();
                $('#select-week-div').hide();
                $('#select-month-div').hide();
                $('#select-year-div').hide();
                $('#name').attr("disabled", true);
                simditor.setValue("");
                var target = $(event.target).is('span') ? $(event.target).parent() : $(event.target);
                switch(target.attr('data-group')) {
                    case 'day':
                        title = "日报";
                        $('#select-day-div').show();
                        $('#name').val("项目" + title + "-" + $('#user-name').val() + "-" + $('#datepicker').val());
                        simditor.setValue(DAILY_TEMPLATE);
                        break;
                    case 'week':
                        title = "周报";
                        $('#select-week-div').show();
                        $('#name').val("项目" + title + "-" + $('#user-name').val() + "-" + $('#select-week-div select').val());
                        simditor.setValue(WEEKLY_TEMPLATE);
                        break;
                    case 'month':
                        title = "月报";
                        $('#select-month-div').show();
                        $('#name').val("项目" + title + "-" + $('#user-name').val() + "-" + $('#select-month-div select').val());
                        simditor.setValue(MONTHLY_TEMPLATE);
                        break;
                    case 'year':
                        title = "年报";
                        $('#select-year-div').show();
                        $('#name').val("项目" + title + "-" + $('#user-name').val() + "-" + $('#select-year-div select').val());
                        simditor.setValue(ANNUAL_TEMPLATE);
                        break;
                    case 'other':
                        $('#name').attr("disabled", false);
                        break;
                }
            }
        });
    });

    $('#datepicker').change(function () {
        $('#name').val("项目" + title + "-" + $('#user-name').val() + "-" + $(this).val());
    });
    $('select').change(function () {
        $('#name').val("项目" + title + "-" + $('#user-name').val() + "-" + $(this).val());
    });

    $(window).on('beforeunload', function() {
        if(isSubmitted) {

        } else {
            return '您已输入的内容将不被保存，确定取消提交？';
        }
    });

})();

function cancel() {
    $.tipModal('confirm', 'warning', '您已输入的内容将不被保存，确定取消提交？', function (result) {
        if(result) {
            isSubmitted = true;
            history.back(-1);
        }
    });
}

var name;
var description;
var attachment;
var selectedProjectIdString;

function params() {
    name = $('#name').val();
    if(name.trim() == "") {
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请输入报告标题'});
        return false;
    }
    selectedProjectIdString = $('#selected-project-id').val();
    if(selectedProjectIdString == "" || $('#selected-project-name').html().trim() == "") {
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请至少选择一个项目'});
        return false;
    }
    $.showLoading('show');
    description = simditor.getValue().replace(/<table>/g, '<table class="table table-striped front-table">');
    attachment = "";
    $('#attachment-list').children('span').each(function (index, element) {
        attachment += $(element).attr("title") + ",";
    });
    return true;
}

function saveReport() {
    if(params()) {
        $.post("report/saveReport", {
            "report.name": name,
            "report.description": description,
            "report.attachment": attachment,
            selectedProjectIdString: selectedProjectIdString,
        }, function (json) {
            if ($.isNumeric(json.report.id) && json.report.id != 0) {
                isSubmitted = true;
                location.href = "report/show?id=" + json.report.id;
            } else {
                $.showLoading('reset');
                $.tipModal('alert', 'danger', "保存失败");
            }
        });
    }
}

function submitReport() {
    $.tipModal('confirm', 'info', '确认提交报告？', function (result) {
        if(result) {
            if (params()) {
                $.post("report/submitReport", {
                    "report.name": name,
                    "report.description": description,
                    "report.attachment": attachment,
                    selectedProjectIdString: selectedProjectIdString,
                }, function (json) {
                    if ($.isNumeric(json.report.id) && json.report.id != 0) {
                        if (json.sendMailException == "") {
                            isSubmitted = true;
                            location.href = "report/show?id=" + json.report.id;
                        } else {
                            $.showLoading('reset');
                            $.tipModal('alert', 'warning', "提交成功但给" + json.sendMailException.substring(0, json.sendMailException.length - 1) + "的邮件发送失败", function (result) {
                                isSubmitted = true;
                                location.href = "report/show?id=" + json.report.id;
                            });
                        }
                    } else {
                        $.showLoading('reset');
                        $.tipModal('alert', 'danger', "提交失败");
                    }
                });
            }
        }
    });
}

function submitDraftReport(id) {
    if(params()) {
        $.post("report/submitReport", {
            "report.id": id,//与submitReport()只有这一行不同
            "report.name": name,
            "report.description": description,
            "report.attachment": attachment,
            selectedProjectIdString: selectedProjectIdString,
        }, function (json) {
            if ($.isNumeric(json.report.id) && json.report.id != 0) {
                if (json.sendMailException == "") {
                    isSubmitted = true;
                    location.href = "report/show?id=" + json.report.id;
                } else {
                    $.showLoading('reset');
                    $.tipModal('alert', 'warning', "提交成功但给" + json.sendMailException.substring(0, json.sendMailException.length - 1) + "的邮件发送失败", function (result) {
                        isSubmitted = true;
                        location.href = "report/show?id=" + json.report.id;
                    });
                }
            } else {
                $.showLoading('reset');
                $.tipModal('alert', 'danger', "提交失败");
            }
        });
    }
}

function updateReport(id) {
    params();
    $.post("report/updateReport", {
        "report.id":id,
        "report.name":name,
        "report.description":description,
        "report.attachment":attachment,
        selectedProjectIdString:selectedProjectIdString,
    }, function (json) {
        $.post("report/saveReportComment", {
            "reportComment.report.id": id,
            "reportComment.description": "#操作# 编辑此报告",
        }, function (json) {
            if($.isNumeric(json.reportComment.id) && json.reportComment.id != 0) {
                isSubmitted = true;
                location.href = "report/show?id=" + id;
            } else {
                $.showLoading('reset');
                $.tipModal('alert', 'danger', "编辑失败");
            }
        });
    });
}

$('.fr-file-upload').frFileUpload({
    maxFileSize:10*1024*1024,//10MB
    uploadCallback:function (json) {
        if(json.uuid == null || json.uuid == "") {
            return false;
        } else {
            if(attachmentCount == 0) {
                $('#attachment-list').show();
            }
            attachmentCount++;
            var $newFileSpan = $('<span title="' + json.uuid + '"><br>' + json.fileFileName + '&nbsp;<a href="javascript:void(0)" onclick="deleteAttachment(this,\'' + json.uuid + '\')">[删除]</a></span>');
            $newFileSpan.appendTo('#attachment-list');
            return true;
        }
    }
});

function deleteAttachment(target, uuid) {
    $(target).html("删除中");
    $.post("report/deleteAttachment", {
        uuid:uuid,
    }, function (json) {
        if(json) {
            attachmentCount--;
            $(target).parent().remove();
            if(attachmentCount == 0) {
                $('#attachment-list').hide();
            }
        } else {
            $(target).html("[删除]");
        }
    });
}
