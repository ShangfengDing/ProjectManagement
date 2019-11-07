<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div class="media">
        <div class="media-left text-center">
            <p>
                <%--<img id="projectimg" class="img-circle img-lg-avatar"--%>
                     <%--src="http://freedisk.free4inno.com/download?uuid=<s:property value="pro.avatar"/>"--%>
                     <%--onerror="javascript:this.src='statics/images/group.png'">--%>
                <%--<img id="projectimg" class="img-circle img-lg-avatar"--%>
                     <%--style="display:block;"--%>
                     <%--src="http://freedisk.free4inno.com/download?uuid=<s:property value="pro.avatar"/>"--%>
                     <%--onerror="autoIcon(&quot;<s:property value="pro.name"/>&quot;)">--%>

                <%--<div style="font-size: 50px">--%>
                    <%--<span id="glIcon" style="display:none" class="glyphicon glyphicon-asterisk"></span>--%>
                <%--</div>--%>

                <img  id="projectimg" class="img-circle img-lg-avatar"
                      style="display:block"
                      src="http://freedisk.free4inno.com/download?uuid=<s:property value="pro.avatar"/>"
                      onerror="autoIcon('<s:property value="pro.id"/>')">

                <div id="circle" style="width: 100px; height: 100px; border-radius: 50px; display: none; background-color: white;position:relative;">
                    <span id="glIcon" style="display:block; font-size: 50px; width: 50px; height: 50px; color: white;margin: auto;  position: absolute;  top: 0; left: 0; bottom: 0; right: 3px;" class="glyphicon glyphicon-asterisk"></span>
                </div>
            </p>
            <p>
                <!-- 这个p标签的作用是保持上下间距一致 -->
            </p>
        </div>
        <div class="media-body media-middle">
            <h4 class="media-heading">
                <input type="hidden" id="projectid" value="<s:property value='pro.id'/>">
                <input type="hidden" id="projectState" value="<s:property value='pro.state'/>">
                <span id="myGroupName"><s:property value="pro.name"/></span>
                <span class="badge">
                    <s:if test="pro.state==2">已暂停</s:if>
                    <s:if test="pro.state==1">进行中</s:if>
                    <s:if test="pro.state==0">已完成</s:if>
                </span>
            </h4>
            <div style="word-wrap:break-word;word-break:break-all;"><s:property value="pro.description"/>
            </div>
            <div style="word-wrap:break-word;word-break:break-all;">
                <a data-group="member" onclick="detailChange(this)" style="cursor:pointer">成员</a>：<s:property value="numberOfUser"/>&nbsp;&nbsp;
                <a data-group="task" onclick="detailChange(this)" style="cursor:pointer">任务</a>：<s:property value="numberOfTask"/>&nbsp;&nbsp;
                <a data-group="report" onclick="detailChange(this)" style="cursor:pointer">报告</a>：<s:property value="numberOfReport"/>
            </div>
            <div style="padding-top:8px;">
                <a class="blankspace" style="cursor: pointer" id="upload-avatar"><span class="glyphicon glyphicon-edit "></span> 编辑图标</a>
                <s:if test="isAdmin == true">
                    <a class="blankspace" style="cursor: pointer" data-toggle="modal" data-size="modal-lg" data-target="#editProjectMod" id="modifyProject">
                        <span class="glyphicon glyphicon-cog"></span>编辑信息</a>
                    <a class="blankspace" style="cursor: pointer" data-toggle="modal" data-size="modal-lg" id="deleteProject" onclick="deleteProject()">
                        <span class="glyphicon glyphicon-remove"></span> 删除项目</a>
                </s:if>
                <input type="hidden" name="hiddenUuid" id="hiddenuuid" value="<s:property value="pro.avatar" />">
            </div>

        </div>
    </div>
</div>
<div class="modal fade" id="editProjectMod">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">编辑项目资料</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">项目名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control front-no-box-shadow" id="editprojectname"
                                   maxlength=50 placeholder="请填写项目名称(50字以内)" value="<s:property value='pro.name' />">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">项目简介</label>
                        <div class="col-sm-10">
                            <textarea id="editprojectdesc" class="form-control front-no-box-shadow" rows="3"
                                      maxlength=100 style="resize:none;" placeholder="可选择填写项目简介(100字以内)"><s:property
                                    value="pro.description"/></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">项目状态</label>
                        <label class="col-sm-10">
                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                        data-toggle="dropdown">
                                    <s:if test="pro.state==2">已暂停</s:if>
                                    <s:if test="pro.state==1">进行中</s:if>
                                    <s:if test="pro.state==0">已完成</s:if>
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                    <li role="presentation" id="state2"><a role="menuitem" tabindex="-1"
                                                                           onclick="changeState(2)"
                                                                           href="javascript:void(0)">已暂停</a></li>
                                    <li role="presentation" id="state1"><a role="menuitem" tabindex="-1"
                                                                           onclick="changeState(1)"
                                                                           href="javascript:void(0)">进行中</a></li>
                                    <li role="presentation" id="state0"><a role="menuitem" tabindex="-1"
                                                                           onclick="changeState(0)"
                                                                           href="javascript:void(0)">已完成</a></li>
                                </ul>
                            </div>
                        </label>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">项目目标</label>
                        <div class="col-sm-10">
                            <textarea id="editprojectgoal" class="form-control front-no-box-shadow" rows="3"
                                      style="resize:none;" maxlength=100 placeholder="可选择填写项目简介(100字以内)"><s:property
                                    value="pro.target"/></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">取消</button>
                <button class="btn btn-primary" onclick="updateProject()">完成</button>
            </div>

        </div>
    </div>
</div>
<div class="col-md-4" style="padding-left:0px;padding-right: 20px;">
    <div class="panel panel-default front-panel" style="margin-bottom:20px;padding-right: 0px">
        <div class="panel-heading">项目概况</div>
        <div id="taskAndReport" class="panel-body" style="height: 250px;">
            <s:iterator value="countTaskList" id="task" status="status">
                <input id="task<s:property value="#status.index"/>" value="<s:property value="#task"/>"
                       type="hidden"/><br/>
                <s:property value="#status.index"/>+<s:property value="#task"/>
            </s:iterator>
            <s:iterator value="countReportList" id="report" status="status">
                <input id="report<s:property value="#status.index"/>" value="<s:property value="#report"/>"
                       type="hidden"/><br/>
                <s:property value="#status.index"/>+<s:property value="#report"/>
            </s:iterator>
            <s:iterator value="countTaskByStateList" id="taskState" status="status">
                <input id="taskState<s:property value="#status.index"/>" value="<s:property value="#taskState"/>"
                       type="hidden"/><br/>
                <s:property value="#status.index"/>+<s:property value="#report"/>
            </s:iterator>
        </div>
    </div>
</div>
<div class="col-md-4" style="padding-left:0px;padding-right: 20px;">
    <div class="panel panel-default front-panel" style="margin-bottom:20px;">
        <div class="panel-heading">任务概况</div>
        <div id="taskLastWeek" class="panel-body" style="height: 250px;">
        </div>
    </div>
</div>
<div class="col-md-4" style="padding-left:0px;padding-right: 0px;">
    <div class="panel panel-default front-panel" style="margin-bottom:20px;">
        <div class="panel-heading">周报概况</div>
        <div id="reportLastWeek" class="panel-body" style="height: 250px;">
        </div>
    </div>
</div>
<div id="project-dynamics" class="col-md-12" style="padding-left:0px;padding-right: 0px;">
    <div class="panel panel-default front-panel" style="margin-bottom:20px;">
        <div class="panel-heading">近5周项目动态</div>
        <div class="panel-body" style="height: 550px;">
            <div id="dynamic">
            </div>
            <div id="navigation"></div>
        </div>
    </div>
</div>


<script src="http://newfront.free4inno.com/plugin/fileupload/fileupload.min.js"></script>
<script src="http://newfront.free4inno.com/plugin/imgupload/imgupload.js"></script>
<script src="project/ProjectEdit.js"></script>
<script src="statics/js/echarts.js"></script>
<script type="text/javascript">
    //    function getDate(tm){
    //        var tt=new Date(parseInt(tm)/1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ")
    //        return tt;
    //    }
    function detailChange(obj) {
        var group = obj.getAttribute("data-group");
        switch (group) {
            case 'survey' :
                $("#projectType").html("survey");
                $("#projectDetail").html("详情&nbsp;<span class=\"caret\"></span>");
                $("#loading-survey").css("display", "block");
                $.ajax({
                    url: "surveyflow",
                    type: "post",
                    data: {
                        id: projectId
                    },
                    success: function (html) {
                        $("#projectType").val("survey");
                        $("#project-survey").html(html);
                        getDynamic(1);
                    }
                });
                $("#loading-survey").css("display", "none");
                $("#project-survey").css("display", "block");
                $("#project-member").css("display", "none");
                $("#project-task").css("display", "none");
                $("#project-report").css("display", "none");
                $("#project-dynamics").css("display", "block");
                break;
            case 'member' :
                $("#projectType").html("user");
                $("#projectDetail").html("成员&nbsp;<span class=\"caret\"></span>");
                $("#loading-member").css("display", "block");
                $("title").html("项目成员 - 轻项目");
                $.ajax({
                    url: "memberflow",
                    type: "post",
                    data: {
                        id: projectId
                    },
                    success: function (html) {
                        $("#project-member").html(html);
                    }
                });
                $("#project-survey").css("display", "none");
                $("#loading-member").css("display", "none");
                $("#project-member").css("display", "block");
                $("#project-task").css("display", "none");
                $("#project-report").css("display", "none");
                $("#project-dynamics").css("display", "none");
                break;
            case 'task' :
                window.location.href='task/viewtask?projectId='+projectId;
                break;
            case 'report' :
                $("#projectType").html("report");
                $("#projectDetail").html("报告&nbsp;<span class=\"caret\"></span>");
                $("#loading-report").css("display", "block");
                $("title").html("项目报告 - 轻项目");
                $.ajax({
                    url: "report/reportflow",
                    type: "post",
                    data: {
                        projectId: projectId
                    },
                    success: function (html) {
                        $("#project-report").html(html);
                    }
                });
                $("#project-survey").css("display", "none");
                $("#project-member").css("display", "none");
                $("#project-task").css("display", "none");
                $("#loading-report").css("display", "none");
                $("#project-report").css("display", "block");
                $("#project-dynamics").css("display", "none");
                break;
            case 'file' :
                window.location.href='file/viewfile?Id='+projectId;
                break;
        }
    }

    function getDate1(tm) {
        var date = new Date(tm);
        Y = date.getFullYear() + '-';
        //M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        M = date.getMonth()+1 +'-';
        D = date.getDate() + ' ';
//        h = date.getHours() + ':';
//        m = date.getMinutes() + ':';
//        s = date.getSeconds();
        return Y + M + D;
    }

    $(function () {
        var taskDate1 = getDate1(parseFloat($("#task0").val().trim()));
        var taskDate2 = getDate1(parseFloat($("#task2").val().trim()));
        var taskDate3 = getDate1(parseFloat($("#task4").val().trim()));
        var taskDate4 = getDate1(parseFloat($("#task6").val().trim()));
        var taskDate5 = getDate1(parseFloat($("#task8").val().trim()));
        console.log(parseFloat($("#task0").val().trim()));
        console.log(taskDate5);
        taskDate1 = taskDate1.substring(5,10);
        taskDate2 = taskDate2.substring(5,10);
        taskDate3 = taskDate3.substring(5,10);
        taskDate4 = taskDate4.substring(5,10);
        taskDate5 = taskDate5.substring(5,10);
        var taskCount1 = $("#task1").val().trim();
        var taskCount2 = $("#task3").val().trim();
        var taskCount3 = $("#task5").val().trim();
        var taskCount4 = $("#task7").val().trim();
        var taskCount5 = $("#task9").val().trim();
        var taskState1 = $("#taskState1").val().trim();
        var taskState2 = $("#taskState3").val().trim();
        var taskState3 = $("#taskState5").val().trim();
        var taskState4 = $("#taskState7").val().trim();
        var reportCount1 = $("#report1").val().trim();
        var reportCount2 = $("#report3").val().trim();
        var reportCount3 = $("#report5").val().trim();
        var reportCount4 = $("#report7").val().trim();
        var reportCount5 = $("#report9").val().trim();
        var reportSubmitted = <s:property value="numberOfReportSubmited"/>;
        var reportShouldBeSubmitted = <s:property value="numberOfUserOnProject"/>;

        var timeline = [taskDate5, taskDate4, taskDate3, taskDate2, taskDate1];
        var taskCount = [taskCount5, taskCount4, taskCount3, taskCount2, taskCount1];
        var reportCount = [reportCount5, reportCount4, reportCount3, reportCount2, reportCount1];
        var myChart1 = echarts.init(document.getElementById('taskAndReport'));
        var myChart2 = echarts.init(document.getElementById('taskLastWeek'));
        var myChart3 = echarts.init(document.getElementById('reportLastWeek'));
        option = {

            /*title: [{
                text: '近五周项目概况',
                left: '1%',
                top: '6%'
//                textStyle: {
//                    color: '#ffd285'
//                }
            }, {
                text: '上周任务概况',
                left: '55%',
                top: '6%',
                textAlign: 'center'
//                textStyle: {
//                    color: '#ffd285'
//                }
            }, {
                text: '上周周报概况',
                left: '75%',
                top: '6%',
                textAlign: 'center'
//                textStyle: {
//                    color: '#ffd285'
//                }
            }],*/
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                x: 100,
                top: '1%',
                bottom:'10%',
                textStyle: {
                    color: '#000000'
                },
                data: ['已完成任务', '已提交周报']
            },
            grid: {
                left: '5%',
                right: '10%',
                top: '10%',
                bottom: '6%',
                containLabel: true
            },
            toolbox: {
                "show": false,
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                "axisLine": {
                    lineStyle: {
                        color: '#c0576d'
                    }
                },
                "axisTick": {
                    "show": false
                },
                axisLabel: {
                    textStyle: {
                        color: '#000000'
                    }
                },
                boundaryGap: false,
                data: timeline
            },
            yAxis: {
                "axisLine": {
                    lineStyle: {
                        color: '#000000'
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#000000'
                    }
                },
                "axisTick": {
                    "show": false
                },
                axisLabel: {
                    textStyle: {
                        color: '#000000'
                    }
                },
                type: 'value'
            },
            series: [{
                name: '已完成任务',
                smooth: true,
                type: 'line',
                symbolSize: 8,
                symbol: 'circle',
                data: taskCount
            }, {
                name: '已提交周报',
                smooth: true,
                type: 'line',
                symbolSize: 8,
                symbol: 'circle',
                data: reportCount
            }
            ]
        };
        option2 = {

            tooltip: {
                trigger: 'axis'
            },
            legend:{
                orient: 'vertical',
                x:'left',
                itemWidth:5,
                data:['待处理','进行中','待验收','已完成']
            },
            series: [{
                name: '上周任务详情',
                type: 'pie',
                center: ['50%', '50%'],
                radius: ['80%', '90%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
                    {
                        value: taskState1, name: '待处理', label: {
                        trigger: 'item',
                        normal: {
                            formatter: "{b}\n\n{c}",
                            textStyle: {
                                fontSize: 10
                            }
                        }
                    }
                    },
                    {
                        value: taskState2, name: '进行中', label: {
                        trigger: 'item',
                        normal: {
                            formatter: "{b}\n\n{c}",
                            textStyle: {
                                fontSize: 10
                            }
                        }
                    }
                    },
                    {
                        value: taskState3, name: '待验收', label: {
                        trigger: 'item',
                        normal: {
                            formatter: "{b}\n\n{c}",
                            textStyle: {
                                fontSize: 10
                            }
                        }
                    }
                    },
                    {
                        value: taskState4, name: '已完成', label: {
                        trigger: 'item',
                        normal: {
                            formatter: "{b}\n\n{c}",
                            textStyle: {
                                fontSize: 10
                            }
                        }
                    }
                    }
                ]
            },
            ]
        };
        option3 = {
            tooltip: {
                trigger: 'axis'
            },
            legend:{
                orient: 'vertical',
                x:'left',
                itemWidth:5,
                data:['已提交','未提交']
            },
            series: [{
                name: '访问来源',
                type: 'pie',
                center: ['50%', '50%'],
                radius: ['80%', '90%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data: [
                    {
                        value: reportShouldBeSubmitted - reportSubmitted,
                        name: '未提交',
                        itemStyle: {
                            normal: {
                                color: '#79100f'
                            }
                        }, label: {
                        trigger: 'item',
                        normal: {
                            formatter: "{b}\n\n{c}",
                            textStyle: {
                                fontSize: 10
                            }
                        }
                    }
                    },
                    {
                        value: reportSubmitted,
                        name: '已提交',
                        itemStyle: {
                            normal: {
                                color: '#7c6475'
                            }
                        },
                        label: {
                            trigger: 'item',
                            normal: {
                                formatter: "{b}\n\n{c}",
                                textStyle: {
                                    fontSize: 10
                                }
                            }
                        }
                    }
                ]
            },
            ]
        };

        /*自动旋转展示饼图中所有数据
        var currentIndex = -1;
        setInterval(function () {
            var dataLen = option.series[0].data.length;
            // 取消之前高亮的图形
            myChart2.dispatchAction({
                type: 'downplay',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
            currentIndex = (currentIndex + 1) % dataLen;
            // 高亮当前图形
            myChart2.dispatchAction({
                type: 'highlight',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
            // 显示 tooltip
            myChart2.dispatchAction({
                type: 'showTip',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
        }, 2000);*/
        //记录上次高亮的索引
        var lastMouseOverIndex=null;
        // mouseover事件，记录当前数据索引并取消其他高亮，over在out之后
        myChart2.on('mouseover', function (params) {
            var dataLen = option.series[0].data.length;
            lastMouseOverIndex = params.dataIndex;
            for(var i=0;i<dataLen;i++){
                if(i!= params.dataIndex){
                    myChart2.dispatchAction({
                        type: 'downplay',
                        seriesIndex: 0,
                        dataIndex: i
                    })
                }
            }
        });
        // mouseout事件，将上次的高亮
        myChart2.on('mouseout', function (params) {
            myChart2.dispatchAction({
                type: 'highlight',
                seriesIndex: 0,
                dataIndex: lastMouseOverIndex
            })
        });
        //记录上次高亮的索引
        var lastMouseOverIndex2=null;
        // mouseover事件，记录当前数据索引并取消其他高亮，over在out之后
        myChart3.on('mouseover', function (params) {
            var dataLen = option.series[0].data.length;
            lastMouseOverIndex2 = params.dataIndex;
            for(var i=0;i<dataLen;i++){
                if(i!= params.dataIndex){
                    myChart3.dispatchAction({
                        type: 'downplay',
                        seriesIndex: 0,
                        dataIndex: i
                    })
                }
            }
        });
        // mouseout事件，将上次的高亮
        myChart3.on('mouseout', function (params) {
            myChart3.dispatchAction({
                type: 'highlight',
                seriesIndex: 0,
                dataIndex: lastMouseOverIndex2
            })
        });
        myChart1.setOption(option);
        myChart2.setOption(option2);
        myChart3.setOption(option3);
        myChart2.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: 0
        })
        myChart3.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: 0
        })
    });

    function deleteProject() {
        var id = $("#projectid").val();
        $.tipModal('confirm', 'info', '确定删除该项目？', function (result) {
            $('.modal').modal('hide');
            if (result == true) {
                $.post("project/deleteProject", {id: id}, function (data) {
                    if (data) {
                        $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '项目删除成功！'})
                        location.href = "home";
                    }
                });
            }
        })
    }

    function autoIcon(name){

        $("#projectimg").css("display", "none");
        $("#glIcon").css("display", "block");

        var A = name.charCodeAt(0);
        var B = name.charCodeAt(1);
        var resultIcon = (A + B) % 15;
        var resultColo = (A + B) % 10;
        var iconType = "cloud";
        var iconColo = "rgb(255,145,0)";
        switch(resultIcon){
            case 0:
                iconType = "asterisk";
                break;
            case 1:
                iconType = "cloud";
                break;
            case 2:
                iconType = "inbox";
                break;
            case 3:
                iconType = "book";
                break;
            case 4:
                iconType = "gift";
                break;
            case 5:
                iconType = "leaf";
                break;
            case 6:
                iconType = "fire";
                break;
            case 7:
                iconType = "road";
                break;
            case 8:
                iconType = "eye-open";
                break;
            case 9:
                iconType = "piggy-bank";
                break;
            case 10:
                iconType = "tower";
                break;
            case 11:
                iconType = "king";
                break;
            case 12:
                iconType = "queen";
                break;
            case 13:
                iconType = "grain";
                break;
            case 14:
                iconType = "sunglasses";
                break;
        }
        switch(resultColo){
            case 0:
                iconColo = "#b7191d"
                break;
            case 1:
                iconColo = "#890e4f"
                break;
            case 2:
                iconColo = "#4a148c"
                break;
            case 3:
                iconColo = "#0e47a1"
                break;
            case 4:
                iconColo = "#1c5e20"
                break;
            case 5:
                iconColo = "#1d88e6"
                break;
            case 6:
                iconColo = "#3e50b4"
                break;
            case 7:
                iconColo = "#2f7d32"
                break;
            case 8:
                iconColo = "#ff6f00"
                break;
            case 9:
                iconColo = "#459f47"
                break;
        }
        iconType = "glyphicon glyphicon-"+iconType;
        //$("#glIcon").replaceWith("<span style=\"display:block; color: "+iconColo+";\" class=\"glyphicon glyphicon-"+iconType+"\"></span>");
        $("#circle").css({"display": "block","backgroundColor":iconColo});
        $("#glIcon").css({"display": "block"});
        $("#glIcon").attr("class", iconType)

    }
</script>
