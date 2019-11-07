<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
    <%@include file="../../statics/head.html"%>
    <title>个人信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="statics/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="statics/css/front.css"/>
    <link rel="stylesheet" type="text/css" href="statics/css/timeline.css">
    <link href="//s2.music.126.net/web/s/core.css?8a07f867c623895f037f904778a3dbda" type="text/css" rel="stylesheet"/><link href="//s2.music.126.net/web/s/pt_frame.css?2f159e0a369ac91f80a6368552258dc7" type="text/css" rel="stylesheet"/>
    <link href="//s2.music.126.net/web/s/pt_profile_home.css?0441c359d347bc86ffe8d127f4aba101" type="text/css" rel="stylesheet"/>

    <style type="text/css">
        .line_02{
            height: 1px;
            border-top: 1px solid #ddd;
            text-align: center;
        }
        .line_02 span{
            position: relative;
            top: -8px;
            background: #fff;
            padding: 0 20px;
        }
        .u-title {
            border-bottom: 2px solid #ccc;
        }
        .m-cvrlst li {
            float: left;
            display: inline-block;
            width: 140px;
            /*height: 188px;*/
            /*overflow: hidden;*/
            padding: 0px 0px 30px 70px;
            /*line-height: 1.4;*/
        }
        .add-padding{
            padding-top:20px;
        }
    </style>
</head>

<body class="front-body">
<s:include value="../nav.jsp?act=project">
</s:include>
<div class="front-inner">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="g-bd">
                    <div class="g-wrap p-prf">
                        <dl class="m-proifo f-cb" id="head-box">
                            <dt class="f-pr" id="ava">
                                <img src="statics/images/user2.jpg">
                                <div class="btm" ><a href="/user/update?id=122256245&sub=ava" class="upload">更换头像</a></div>
                            </dt>
                            <dd>
                                <div class="name f-cb">
                                    <div class="f-cb">
                                        <div class="edit"><a href="/user/update?id=122256245" hidefocus="true" class="u-btn2 u-btn2-1"><i>编辑个人资料</i></a></div>
                                        <div class="rect" id="newmusician"></div>
                                        <h2 id="j-name-wrap" class="wrap f-fl f-cb ">
                                            <span class="tit f-ff2 s-fc0 f-thide">自邮之翼</span>

                                            <i class="icn u-icn u-icn-01"></i>
                                        </h2>
                                    </div>
                                </div>
                                <ul class="data s-fc3 f-cb" id="tab-box">
                                    <li class="fst"><a href="/user/event?id=122256245"><strong id="event_count">2</strong><span>项目</span></a></li>
                                    <li><a href="/user/follows?id=122256245"><strong id="follow_count">3</strong><span>任务</span></a></li>
                                    <li>
                                        <a href="/user/fans?id=122256245">
                                            <strong id="fan_count">1</strong>
                                            <span>报告</span>
                                            <i class="u-icn u-icn-68 f-alpha" id="newCount" style="display:none;"></i>
                                        </a></li>
                                </ul>
                                <div class="inf s-fc3">
                                    <span>邮箱：free4inno@163.com </span>
                                </div>
                                <div class="inf s-fc3 f-cb">
                                    <span class="tit">简介：用心培养有心的学生，切实解决现实的问题。</span>
                                </div>
                            </dd>
                        </dl>

                        <div class="u-title u-title-1 f-cb m-record-title " id='rHeader' >
                            <h3><span class="f-ff2 s-fc3">任务时间轴</span></h3>
                            <h4>累计完成任务2个</h4>
                            <span class="n-iconpoint">
                                <a href="javascript:void(0)" class="icon u-icn2 u-icn2-5 j-flag"></a>
                                <div class="tip"><!-- icon在hover的时候显示tip -->
                                    <p>所有时间完成任务数量总和。</p>
                                    <i class="t"></i><i class="b"></i>
                                </div>
                            </span>
                            <div class="nav f-cb">
                                <span data-action="songsall" id="songsall">所有时间</span>
                                <i></i>
                                <span data-action="songsweek" id="songsweek">最近一周</span>
                            </div>
                        </div>
                        <div class="content">
                            <article>
                                <h3>2018</h3>
                                <section>
                                    <span class="point-time point-red"></span>
                                    <time datetime="2018-07">
                                        <span class="small_font_size">July</span>
                                        <span class="small_font_size add-weight">07-09</span>
                                    </time>
                                    <aside>
                                        <p class="things medium_font_size">个人主页原型设计</p>
                                        <p class="brief"><span class="text-red">进行中</span></p>
                                    </aside>
                                </section>
                                <section>
                                    <span class="point-time point-yellow"></span>
                                    <time datetime="2018-07">
                                        <span class="small_font_size">July</span>
                                        <span class="small_font_size add-weight">07-08</span>
                                    </time>
                                    <aside>
                                        <p class="things medium_font_size">添加人员支持模糊搜索</p>
                                        <p class="brief"><span class="text-yellow">待验收</span></p>
                                    </aside>
                                </section>
                                <section>
                                    <span class="point-time point-green"></span>
                                    <time datetime="2018-07">
                                        <span class="small_font_size">July</span>
                                        <span class="small_font_size add-weight">07-05</span>
                                    </time>
                                    <aside>
                                        <p class="things medium_font_size">添加人员搜索结果</p>
                                        <p class="brief"><span class="text-green">已完成</span></p>
                                    </aside>
                                </section>
                            </article>

                        </div>
                        <div class="u-title u-title-1 f-cb m-record-title " id='rHeader2' >
                            <h3><span class="f-ff2 s-fc3">统计信息</span></h3>
                        </div>

                        <ul class="f-cb add-padding" id="cBox">
                            <li>
                                <div class="col-md-4">
                                    <div class="panel panel-default front-panel">
                                        <div class="panel-heading">任务概况</div>
                                        <div id="taskThisWeek" class="panel-body" style="height: 250px;">
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="col-md-4">
                                    <div class="panel panel-default front-panel">
                                        <div class="panel-heading">项目贡献度</div>
                                        <div class="wrap">
                                            <select>
                                                <option value ="">--请选择项目--</option>
                                                <option value ="轻项目">轻项目</option>
                                                <option value="项目2">项目2</option>
                                                <option value="项目3">项目3</option>
                                            </select>
                                        </div>
                                        <div id="myContribution" class="panel-body" style="height: 230px;">
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="col-md-4">
                                    <div class="panel panel-default front-panel">
                                        <div class="panel-heading">周报概况</div>
                                        <div id="myReport" class="panel-body" style="height: 250px;">
                                            <p>&nbsp;</p>
                                            <p class="medium_font_size">未提交：</p>
                                            <p>&nbsp;</p>
                                            <p class="small_font_size"><a href="report" target="_blank">项目3</a></p>
                                            <p>&nbsp;</p><p>&nbsp;</p>
                                            <p class="medium_font_size">已提交：</p>
                                            <p>&nbsp;</p>
                                            <p class="small_font_size">项目1，项目2</p>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>

                </div>
            </div>
            <%--<div class="col-md-12">--%>
                <%--<div class="g-bd">--%>
                    <%--<div class="g-wrap p-prf">--%>
                        <%--<div class="col-md-4">--%>
                            <%--<div class="panel panel-default front-panel">--%>
                                <%--<div class="panel-heading">Title</div>--%>
                                <%--<div class="panel-body front-last-no-margin">--%>
                                    <%--<p>Notice1</p>--%>
                                    <%--<p>Notice2</p>--%>
                                    <%--<p>Notice3</p>--%>
                                    <%--<p>Notice4</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>

    <s:include value="../footer.jsp"/>
</div>
</body>
</html>

<script src="http://newfront.free4inno.com/plugin/fileupload/fileupload.min.js"></script>
<script src="http://newfront.free4inno.com/plugin/imgupload/imgupload.js"></script>
<script src="project/ProjectEdit.js"></script>
<script src="statics/js/echarts.js"></script>
<script src="statics/js/echarts-liquidfill.js"></script>
<script type="text/javascript">

    $(function() {
        var Chart1 = echarts.init(document.getElementById('taskThisWeek'));
        var Chart2 = echarts.init(document.getElementById('myContribution'));
        var liquidColor='rgb(64,150,238)';
        option = {
            xAxis: {
                type: 'category',
                data: ['待处理', '进行中', '待验收', '已完成']
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: [5, 3, 1, 2],
                type: 'bar',
                label: {//图形上的文本标签
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: '#a8aab0',
                            fontStyle: 'normal',
                            fontFamily: '微软雅黑',
                            fontSize: 12,
                        },
                    },
                },
                //配置样式
                itemStyle: {
                    //通常情况下：
                    normal:{
                        //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                        color: function (params){
                            var colorList = ['rgb(164,205,238)','rgb(42,170,227)','rgb(25,46,94)','rgb(195,229,235)'];
                            return colorList[params.dataIndex];
                        }
                    },
                    //鼠标悬停时：
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                //设置柱子的宽度
                barWidth : 30
            }]
        };
        option2 = {
            series: [{
                type: 'liquidFill',
                data: [0.6],
                radius:'80%',
                color:[liquidColor],
                name: '项目贡献度',
                label:{
                    normal:{
                        textStyle:{
                            fontSize:28
                        }
                    }
                },
                outline:{
                    itemStyle:{
                        borderColor:liquidColor
                    }
                }
            }],
            tooltip: {
                show: true
            }
        };
        Chart1.setOption(option);
        Chart2.setOption(option2);
    })
</script>