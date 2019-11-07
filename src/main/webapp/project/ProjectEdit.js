var projectState = $("#projectState").val();

function changeState(state){
    if(state==2){
        $("#dropdownMenu1").html('已暂停'+'&nbsp<span class="caret"></span>');
        projectState=2;
    }
    if(state==1){
        $("#dropdownMenu1").html('进行中'+'&nbsp<span class="caret"></span>');
        projectState=1;
    }
    if(state==0){
        $("#dropdownMenu1").html('已完成'+'&nbsp<span class="caret"></span>');
        projectState=0;
    }
}


function updateProject(){
    if($("#editprojectname").val().trim().length == 0){
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请填写项目名称'});
    } else {
        $.post("project/updateProject", {
            "project.id" :$("#projectid").val(),
            "project.name" : $("#editprojectname").val().trim(),
            "project.description" : $("#editprojectdesc").val(),
            "project.state" : projectState,
            "project.target" : $("#editprojectgoal").val(),
        }, function (data) {
            if(data){
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'项目资料修改成功！'});
                location.href="info?id=" + $("#projectid").val()+"&&type=survey";
            }
        });
    }

}

function editproject(){
    $("#menu2").removeClass("active");
    $("#menu1").addClass("active");
    $("#promem").addClass("hidden");
    $("#proedit").removeClass("hidden");
}
function projectmem(){
    $("#menu1").removeClass("active");
    $("#menu2").addClass("active");
    $("#proedit").addClass("hidden");
    $("#promem").removeClass("hidden");
}
function deleteUser(userid){
    var id = $("#projectid").val();
    $.tipModal('confirm','info','确定将用户从项目中删除？',function(result){
        $('.modal').modal('hide');
        if(result==true){
            $.post("project/deleteUser",{id:id,uid:userid},function(data){
                if(data){
                    $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'项目成员删除成功！'});
                    location.href="info?type=user&&id=" + id;
                }
            });
        }
    })
}

function setLeave(userid){
    var id = $("#projectid").val();
    $.tipModal('confirm','info','确定将用户设置为离职状态？',function(result){
        $('.modal').modal('hide');
        if(result==true){
            $.post("project/setLeave",{id:id,uid:userid},function(data){
                if(data){
                    $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'离职状态设置成功！'});
                    location.href="info?type=user&&id=" + id;
                }
            });
        }
    })
}
//设置人员上岗
function setOnProject(userid){
    var id = $("#projectid").val();
    $.tipModal('confirm','info','确定将该用户设置成上岗？',function(result){
        $('.modal').modal('hide');
        if(result==true){
            $.post("project/setOnProject",{id:id,uid:userid},function(data){
                if(data){
                    $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'设置上岗成功！'});
                    location.href="info?type=user&&id=" + id;
                }
            });
        }
    })
}

function addAdmin(userid){
    var id = $("#projectid").val();
    $.tipModal('confirm','info','确定将用户设置为项目管理员？',function(result){
        $('.modal').modal('hide');
        if(result==true){
            $.post("project/addAdmin",{id:id,uid:userid},function(data){
                if(data){
                    $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'项目管理员设置成功！'});
                    location.href="info?type=user&&id=" + id;
                }
            });
        }
    })
}

function cancelAdmin(userid){
    var id = $("#projectid").val();
    $.tipModal('confirm','info','确定取消项目管理员？',function(result){
        $('.modal').modal('hide');
        if(result ==true){
            $.post("project/cancelAdmin",{id:id,uid:userid},function(data){
                if(data){
                    $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'项目管理员取消成功！'});
                    location.href="info?type=user&&id=" + id;
                }
            });
        }
    })
}

$('#upload-avatar').on('click', function() {
    if($("#projectimg").attr("src") == "statics/images/group.png"){
        $("#hiddenuuid").val("1b381b51-0d09-4e64-a450-7e8cda3428cf");//images/group.png 的uuid值
    }
    var uploadAvatarModal = new UploadAvatarModal({
        title : '修改头像', // 模态框标题
        name : 'photo', // <input type="file" name="photo">
        uploadUrl : 'project/uploadAvatar', // 后端上传图像URL
        dataUrl : 'http://freedisk.free4inno.com/download?uuid=', // 图像存储URL
        dataSource : 'cfcb802e-94d7-4898-ab54-980e2dea6c80|8c8a0c14-d532-4e9c-9f0c-a34927218153|16f35996-98bc-40ac-8250-b102ebfed8c3|faf96f1a-9902-4c82-a07a-e0ca35b067cf|d837bd27-791d-447a-973c-4a14645234d8', // 系统图像UUID(以|分隔)
        dataUUID : $("#hiddenuuid").val(), // 默认图像UUID
        retUUID : 'docUuid' // 后端上传图像返回UUID的参数名
    });

    uploadAvatarModal.generate().on('avatar-upload.fr.modal.confirm', function() {
        var uuid = uploadAvatarModal.getUUID();
        console.log(uuid);
        $.showLoading('show');
        var hiddenuuid = uuid;
        if(hiddenuuid == ''){
            $("#avatarinfo").html("<font color=\"red\">你没有上传新的头像文件</font>");
            setTimeout(function(){$("#avatarinfo").html("");},3000);
            return false;
        }else{
            $.post("project/updateProjectAvatar", {avatar:uuid,id:$("#projectid").val()}, function(data){
                $("#hiddenuuid").val(uuid);
                var path ="http://freedisk.free4inno.com/download?uuid="+uuid;
                $("#projectimg").attr('src',path);
                $.showLoading('reset');
            });
        }
    })
});
