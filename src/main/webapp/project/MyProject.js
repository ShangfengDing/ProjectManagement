

    function showmore(pid,state) {
        if ($("#moreinfo-"+state+pid).css("display") == 'none') {//如果show是隐藏的
            $("#showdetail-"+state+pid).html("<span class='glyphicon glyphicon-minus' class='btn btn-sm btn-default'></span> 收起");
            $("#moreinfo-"+state+pid).css("display", "block");//show的display属性设置为block（显示）
        } else {//如果show是显示的
            $("#showdetail-"+state+pid).html("<span class='glyphicon glyphicon-search' class='btn btn-sm btn-default'></span> 详情");
            $("#moreinfo-"+state+pid).css("display", "none");//show的display属性设置为none（隐藏）

        }
    }
    function cancelSearch() {
        $("#key").val("");
        $("#month").val("0");
    }
    
    function searchProject() {
        $("#loadingstyle").css("display","block");
        var name = $("#key").val().trim();
        if(name.length == 0){
            $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请填写项目名称（可模糊查询）！'});
        }else {
            $.ajax({
                url:"project/SearchProjectByIdAndName",
                type:"post",
                data:{
                    name:name
                },
                success:function(html){
                    $("#loadingstyle").css("display","none");
                    $("#showresult").css("display","block").html(html);
                }
            })
        }
    }
    // }
    // $("#show2").click(function(){
    //     if($("#moreinfo2").css("display")=='none'){//如果show是隐藏的
    //         $("#moreinfo2").css("display","block");//show的display属性设置为block（显示）
    //     }else{//如果show是显示的
    //         $("#moreinfo2").css("display","none");//show的display属性设置为none（隐藏）
    //     }
    // })
    $(document).ready(function() {
// //限制字符个数
//         $("#name<s:property value="prolist.id" />").each(function(){
//             var maxwidth=23;
//             if($(this).text().length>maxwidth){
//                 $(this).text($(this).text().substring(0,maxwidth));
//                 $(this).html($(this).html()+'…');
//             }
//         });
//     });
        function EnterPress(e) {
            var e = e || window.event;
            if (e.keyCode == 13) {
                document.getElementById("search-button").focus();
            }
        }

        $('#my_project').click(function (event) {
            var target = $(event.target).is('span') ? $(event.target).parent() : $(event.target);

            switch (target.attr('data-group')) {
                case 'all' :
                    $("#project-search").css("display", "none");
                    $("#project-onging").css("display", "none");
                    $("#project-suspend").css("display", "none");
                    $("#project-complete").css("display", "none");
                    $("#project-manage").css("display", "none");
                    $("#project-all").css("display", "block");
                    $("#project-sort").css("display", "block");//切换界面重新显示热度排行
                    break;
                case 'onging' :
                    $("#project-search").css("display", "none");
                    $("#project-onging").css("display", "block");
                    $("#project-suspend").css("display", "none");
                    $("#project-complete").css("display", "none");
                    $("#project-manage").css("display", "none");
                    $("#project-all").css("display", "none");
                    $("#project-sort").css("display", "block");//切换界面重新显示热度排行
                    break;
                case 'suspend' :
                    $("#project-search").css("display", "none");
                    $("#project-onging").css("display", "none");
                    $("#project-suspend").css("display", "block");
                    $("#project-complete").css("display", "none");
                    $("#project-manage").css("display", "none");
                    $("#project-all").css("display", "none");
                    $("#project-sort").css("display", "block");//切换界面重新显示热度排行
                    break;
                case 'complete' :
                    $("#project-search").css("display", "none");
                    $("#project-onging").css("display", "none");
                    $("#project-suspend").css("display", "none");
                    $("#project-complete").css("display", "block");
                    $("#project-manage").css("display", "none");
                    $("#project-all").css("display", "none");
                    $("#project-sort").css("display", "block");//切换界面重新显示热度排行
                    break;
                case 'manage' :
                    $("#project-search").css("display", "none");
                    $("#project-onging").css("display", "none");
                    $("#project-suspend").css("display", "none");
                    $("#project-complete").css("display", "none");
                    $("#project-manage").css("display", "block");
                    $("#project-all").css("display", "none");
                    $("#project-sort").css("display", "block");//切换界面重新显示热度排行
                    break;
                case 'search' :
                    $("#project-all").css("display", "none");
                    $("#project-onging").css("display", "none");
                    $("#project-suspend").css("display", "none");
                    $("#project-complete").css("display", "none");
                    $("#project-manage").css("display", "none");
                    $("#project-search").css("display", "block");
                    $("#project-sort").css("display", "none");//搜索界面不显示热度排行
                    break;
            }
        })
    })