package com.free4lab.freeRT.action;

import com.free4lab.freeRT.manager.FileManager;
import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.manager.UserManager;
import com.free4lab.freeRT.model.FileEntity;
import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.model.User;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.utils.group.Result;
import com.opensymphony.xwork2.ActionContext;

import java.sql.Timestamp;
import java.util.List;

public class ProjectProfileAction extends BaseAction{
    private Project project;
    private List<FileEntity> fileList;
    private FileEntity fileEntity;
    private int id;
    private String search;
    private boolean judge=false;//false即当前用户不是此项目管理员
    private Boolean auth = false;
    private Project pro;
    private List<Project> projectList;
    public String getSearch() {
        return search;
    }
    public User starUser;
    public void setSearch(String search) {
        this.search = search;
    }

    private static final int PAGE_SIZE = 20;//用于分页
    private int pageSum;//用于分页
    private int page;//用于分页

    private FileEntity file;



    public String viewfile(){
        int uid = getSessionUID();
        auth = ProjectManager.isMember(id,uid);
        judge=ProjectManager.isAdmin(id,uid);
        if(auth){
            id = id;
            pro = ProjectManager.findProject(id);
            projectList = ProjectManager.findProjectByUser(uid);
            return Result.SUCCESS;
        }
        else {
            return Result.ERROR;
        }
    }

    public String listFile() {
        pageSum = (int) Math.ceil((double) FileManager.count(project) / (double)PAGE_SIZE);
        fileList = FileManager.find(project, page, PAGE_SIZE);
        for (FileEntity f:fileList) {
            System.out.println(f.toString());
        }
        return Result.SUCCESS;
    }

    public String saveUrl() {
        fileEntity.setUser(UserManager.findUserById((Integer) ActionContext.getContext().getSession().get(Constants.USER_ID)));
        fileEntity.setTime(new Timestamp(System.currentTimeMillis()));
        fileEntity.setRealname(null);
        fileEntity.setStar(0);
        FileManager.save(fileEntity);
        return Result.SUCCESS;
    }

    public String starFile(){
        FileManager.starFile(fileEntity);
        return Result.SUCCESS;
    }

    public String editFile(){
        FileManager.editFile(fileEntity);
        return Result.SUCCESS;
    }

    public String searchFile(){
        fileList = FileManager.findByContent(search,project);
        for (FileEntity f:fileList) {
            System.out.println("Result: "+f.toString());
        }
        return Result.SUCCESS;
    }

    public String deleteFile(){
        FileManager.delete(fileEntity);
        System.out.println("Deleted file: "+fileEntity.toString());
        return Result.SUCCESS;
    }
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<FileEntity> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileEntity> fileList) {
        this.fileList = fileList;
    }

    public FileEntity getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
    }

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Project getPro() {
        return pro;
    }

    public void setPro(Project pro) {
        this.pro = pro;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

}
