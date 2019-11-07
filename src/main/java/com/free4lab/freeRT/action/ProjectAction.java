package com.free4lab.freeRT.action;

import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.utils.group.Result;
import com.opensymphony.xwork2.ActionContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsf74 on 2017/5/12.
 */
public class ProjectAction {


    private Integer uid;
    private List<Project> prolist  = new ArrayList<Project>();
    private long numberOfProject;
//    private long numberOfTask;
//    private long numberOfReport;
    private String description;
    private String name;
    private String target;
    private Integer groupid = 1;
    private Boolean checkpro = false;
    Project project;
    private String InformationResult;

    public String newProject() {
        return Result.SUCCESS;
    }

    public String InformationResult(){
        InformationResult = ProjectManager.getLog();
        return Result.SUCCESS;
    }

    public String projectNumber() {
        uid = getSessionUID();
        numberOfProject= ProjectManager.findProjectNumByUser(uid);

        return Result.SUCCESS;
    }

//    public String infoNumber(){
////        uid = getSessionUID();
//        numberOfReport = ReportManager.count(uid);
//        numberOfTask= TaskManager.count(uid);
//        return Result.SUCCESS;
//    }
    public String updateProject() {
        Project editPro=ProjectManager.findProject(project.getId());
        editPro.setName(project.getName());
        editPro.setDescription(project.getDescription());
        editPro.setState(project.getState());
        editPro.setTarget(project.getTarget());
        ProjectManager.updateProject(editPro);
        checkpro = true;
        return Result.SUCCESS;
    }

    public Integer getSessionUID() {
        try {
            Integer id = (Integer) ActionContext.getContext().getSession()
                    .get(Constants.USER_ID);
            return id;
        } catch (NullPointerException e) {
            return null;
        }
    }
    public String createProject(){
        uid = getSessionUID();
        Boolean is = false;
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if(name != null){
            List<Project> projectList = ProjectManager.findallproject();
            for(Project p:projectList){
                if(p.getName().equals(name)){
                    checkpro = true;
                }
            }
        }
        if(checkpro){
            return "error";
        }else{
            Project pro = new Project();
            pro.setGroupid(groupid);
            pro.setName(name);
            pro.setDescription(description);
            pro.setTarget(target);
            pro.setStartime(time);
            pro.setState(1);
            is = ProjectManager.addProject(pro,uid);
            if(is){
                return "success";
            }else{
                return null;
            }
        }

    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public List<Project> getProlist() {
        return prolist;
    }

    public void setProlist(List<Project> prolist) {
        this.prolist = prolist;
    }

    public long getNumberOfProject() {
        return numberOfProject;
    }

    public void setNumberOfProject(long numberOfProject) {
        this.numberOfProject = numberOfProject;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Boolean getCheckpro() {
        return checkpro;
    }

    public void setCheckpro(Boolean checkpro) {
        this.checkpro = checkpro;
    }

    public void setInformationResult(String informationResult) {
        InformationResult = informationResult;
    }

    public String getInformationResult(){ return InformationResult;}
}
