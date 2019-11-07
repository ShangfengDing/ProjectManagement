;(function () {
    showAllProject();
})();

function showAllProject() {
    $("#model-all-project").addClass("model-project-item");
    $("#model-all-project").html("加载中...");
    $.get("report/listProject", function (html) {
        $('#model-all-project').removeClass("model-project-item");
        $('#model-all-project').html(html);
        $(".model-project-item span").bind("click", selectProject);
        loadSelectedProject();
    });
}

function loadSelectedProject() {
	var selectedProjectIdString = $('#selected-project-id').val();
	if(selectedProjectIdString != "") {
		var selectedProjectId = selectedProjectIdString.split(",");
		for(var i=0; i<selectedProjectId.length-1; i++) {
			if(selectedProjectId[i] != "") {
                var li = $('<li></li>');
                li.append($('<a class="pull-right" onclick="deleteSelectedProject(this)">删除</a>'));
                li.append($('<span class="model-selected-project-id" data-id="' + selectedProjectId[i] + '">' + getProjectNameByProjectId(selectedProjectId[i]) + '</span>'));
                $("#model-selected-project").append(li);
			}
		}
	}
    countSelectedProjectNumber();
}

function getProjectNameByProjectId(id) {
    return $('#model-all-project').find('[data-id='+ id +']').html();
}

// function showLatestProject(){
//     $("#model-latest-project").addClass("model-project-item");
//     $("#model-latest-project").html("加载中...");
//     $.get("report/listProject", function (html) {
//         $('#model-latest-project').removeClass("model-project-item");
//         $('#model-latest-project').html(html);
//         $(".model-project-item span").bind("click", selectProject);
//     });
// }

$("#model-search-button").on('click', function () {
    var keyword = $("#model-search-text").val().trim();
    if (keyword != '') {
        $("#model-search-title").css("display","block").click();
        $("#model-search-project").addClass("model-project-item");
        $("#model-search-project").html("加载中...");
        $.get("report/searchProject", {
            searchProjectKeyword:keyword,
        }, function (html) {
            $('#model-search-project').removeClass("model-project-item");
            $('#model-search-project').html(html);
            $(".model-project-item span").bind("click", selectProject);
        });
    }
});
$("#model-search-text").keyup(function (event) {
    if(event.which == 13) {//按Enter键
        $("#model-search-button").click();
    }
});

function selectProject() {
    var projectName = $(this).text();
    var projectId = $(this).attr("data-id");
    var flag = 0;
    var selectedProject = $("#model-selected-project li");
    for(var i=0;i<selectedProject.length;i++){
        if(selectedProject.eq(i).find("span").text() == projectName) {
            flag = 1;
        }
    }
    if(flag === 0){
        var li = $('<li></li>');
        li.append($('<a class="pull-right" onclick="deleteSelectedProject(this)">删除</a>'));
        li.append($('<span class="model-selected-project-id" data-id="' + projectId + '">' + projectName + '</span>'));
        $("#model-selected-project").append(li);
    }
    countSelectedProjectNumber();
}

function deleteSelectedProject(obj) {
    $(obj).parent("li").remove();
    countSelectedProjectNumber();
}

function clearSelectedProject() {
    $("#model-selected-project li").remove();
    countSelectedProjectNumber();
}

function countSelectedProjectNumber() {
    $("#model-project-num span").text($("#model-selected-project li").length);
}

function submitSelectedProject(){
    var projectNameString = "";
    var projectIdString = "";
    var length = $(".model-selected-project-id").length;
    if(length > 0){
        for(var i=0;i<length;i++){
            var span = $(".model-selected-project-id:eq("+i+")");
            projectNameString += span.text() + "&nbsp;&nbsp;";
            projectIdString += span.attr("data-id") + ",";
        }
        $("#selected-project-id").val(projectIdString);
        $("#selected-project-name").html("<label>已选择：</label> " + projectNameString);
        $('.modal').modal('hide');
    }else{
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'请至少选择一个项目'});
    }
}
