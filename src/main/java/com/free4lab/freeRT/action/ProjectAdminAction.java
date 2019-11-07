package com.free4lab.freeRT.action;

import com.free4lab.freeRT.dao.UserOP;
import com.free4lab.freeRT.manager.*;
import com.free4lab.freeRT.model.Log;
import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.model.User;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.utils.group.Result;
import com.free4lab.freeRT.utils.PushMailUtil;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import java.util.ArrayList;
import java.util.List;

public class ProjectAdminAction {

    private List<User> userList;
    private List<User> managerList;
    private List<User> leaveList;//用于返回离职人员的list
    private List<Project> projectList;

    private Integer userId;
    private Integer groupid;
    private Integer uid;
    private Project pro;
    private Integer id;
    private Boolean isAdmin;
    private String PeopleIdString;
    private Boolean flag;
    private String sendMailException;//发送邮件时报错的标记
    private String avatar;
    private String type;//下拉框用户选的什么
    private Boolean AdminNum;
    private long numberOfTask;
    private long numberOfReport;
    private long numberOfUser;


    private long numberOfUserOnProject;
    private List<Log> logList;
    private Integer page;
    private Integer size = 15;
    private Integer endpage;
    private long numberOfReportSubmited;
    private List<Long> countTaskList;//项目近五周分别完成任务数
    private List<Long> countReportList;//项目近五周分别提交周报数
    private List<Long> countTaskByStateList;//项目上周任务状态统计
    private Boolean auth = false;
    private boolean judge=false;//false即当前用户不是此项目管理员


    public String execute() {
        if(id != null){
            uid = getSessionUID();
            Boolean auth = ProjectManager.isMember(id,uid);
            if(auth){
                userList =ProjectManager.findProjectUser(id);
                managerList = ProjectManager.findProjectManager(id);
                leaveList=ProjectManager.findProjectLeave(id);
                List<User> managers = ProjectManager.findProjectManager(id);
                if(managers.size()!= 0 && managers.size()!= 1){
                    AdminNum = true;
                } else {
                    AdminNum = false;
                }
                if(ProjectManager.isAdmin(id,getSessionUID())){
                    isAdmin = true;
                } else {
                    isAdmin = false;
                }
                pro = ProjectManager.findProject(id);
//                userId = UserManager.findUserById(getSessionUID()).getId();
                numberOfReport = ReportManager.countByProjectId(id);
                numberOfTask = TaskManager.countByProjectId(id);
                numberOfUser = ProjectManager.countUserByProjectId(id);
                numberOfUserOnProject = ProjectManager.countUserOnProjectByProjectId(id);

                try {
                    countTaskList = TaskManager.countTaskByPidAndTime(id);
                    countReportList = ReportManager.countReportByPidAndTime(id);
                    countTaskByStateList = TaskManager.countLastWeekTaskByPidAndState(id);
                    numberOfReportSubmited = ReportManager.countLastWeekReportSubmittedByProjectId(id);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return Result.SUCCESS;
            }else {
                return Result.ERROR;
            }
        }else {
            return "wrong";
        }


    }

    public String viewProject(){
        if(id != null){
            uid = getSessionUID();
            Boolean auth = ProjectManager.isMember(id,uid);
            if(auth){
                pro = ProjectManager.findProject(id);
                projectList = ProjectManager.findProjectByUser(uid);
                return Result.SUCCESS;
            }else {
                return Result.ERROR;
            }
        }else {
            return Result.ERROR;
        }
    }
    public String viewProjectTask(){
        int uid = getSessionUID();
        auth = ProjectManager.isMember(id,uid);
        judge=ProjectManager.isAdmin(id,uid);
        if(id != null){
            if(auth){
                pro = ProjectManager.findProject(id);
                projectList = ProjectManager.findProjectByUser(uid);
                return Result.SUCCESS;
            }else {
                return Result.ERROR;
            }
        }else {
            return Result.ERROR;
        }
    }
    public String mailToUser() {
        sendMailException = "";
        int userId = UserManager.findUserById(getSessionUID()).getId();

        //List<User> allUserList=ProjectManager.findProjectAllUser(id);
        List<User> allUserList=ProjectManager.findProjectUserOnProject(id);
        List<Integer> allUserId = new ArrayList<Integer>();
        for(User user:allUserList){
            allUserId.add(user.getId());
        }
        for (int i = 0; i < allUserId.size(); i++) {
            if (allUserId.get(i) == userId) {
                allUserId.remove(i);
                i--;
            }
        }
        allUserList.clear();
        for(int i=0;i<allUserId.size();i++){
            allUserList.add(UserManager.findUserByPrimaryId(allUserId.get(i)));
        }
        userList=allUserList;

        HttpServletRequest request = ServletActionContext.getRequest();

        for(User to : userList) {
            StringBuffer content = new StringBuffer();
            content.append(to.getName()).append("，您好：<br><br>");
            pro = ProjectManager.findProject(id);
            content.append(pro.getName()).append("项目");
            content.append("任务列表已更新，请点击下面的链接查看。<br><br>");
            content.append("http://freeproject.free4inno.com/task<br><br>");
            content.append("此邮件由系统自动生成，请勿直接回复。<br><br><hr>用轻项目记录我的极客时光<br><br>轻项目<br>");
            content.append(new SimpleDateFormat("yyyy.M.d").format(new Date()));
            try {
                PushMailUtil.sendMail(to.getEmail(), pro.getName() + "任务已更新", content.toString());
            } catch (Exception e) {
                e.printStackTrace();
                sendMailException += to.getEmail() + ",";
            }
        }
        return Result.SUCCESS;
    }

    public String deleteUser(){
        if(id != null && uid != null){
            ProjectManager.deleteUser(id,uid);
            flag = true;
            return "success";
        }else {
            return null;
        }
    }
    public String addAdmin(){
        if(id != null && uid != null){
            ProjectManager.addAdmin(id,uid);
            flag = true;
            return "success";
        }else {
            return null;
        }
    }
    public String setLeave(){
        if(id != null && uid != null){
            ProjectManager.setLeave(id,uid);
            flag = true;
            return "success";
        }else {
            return null;
        }
    }
//    将人员设置成上岗
    public String setOnProject(){
        if(id != null && uid != null){
            ProjectManager.setOnProject(id,uid);
            flag = true;
            return "success";
        }else {
            return null;
        }
    }
    public String cancelAdmin(){
        if(id != null && uid != null){
            ProjectManager.cancelAdmin(id,uid);
            flag = true;
            return "success";
        } else {
            return null;
        }
    }
    public String addRelation(){
        List<Integer> userList = new ArrayList<Integer>();
        if(id != null && PeopleIdString != null){
            String[] selectedPeopleId = PeopleIdString.split(",");
            for(String userid : selectedPeopleId) {
                if(!userid.equals("")) {
                    Integer uid = (UserOP.getInstance().findByPrimaryKey(Integer.parseInt(userid))).getUserid();
                    List<User> users = ProjectManager.findProjectAllUser(id);
                    List<Integer> userids = new ArrayList<Integer>();
                    for(User user:users){
                        userids.add(user.getUserid());
                    }
                    flag = userids.contains(uid);
                    if (!flag){
                        userList.add(uid);
                        ProjectManager.adduser(id,uid);
                    }
                }
            }
            return "success";
        } else {
            return null;
        }
    }

    public String deleteProject(){
        if(id != null){
            ProjectManager.deletePro(id);
            flag = true;
            return "success";
        }else {
            return null;
        }
    }

    public String updateProjectAvatar(){
        System.out.println(avatar);
        if(id != null){
            pro = ProjectManager.findProject(id);
            pro.setAvatar(avatar);
            ProjectManager.updateProject(pro);
            return Result.SUCCESS;
        }else {
            return null;
        }

    }

    public String getDynamic(){
        logList = LogManager.findLogByPid(id,null,page,size);
        long numOfLog = LogManager.countLogByProjectId(id);
        if(numOfLog % size == 0){
            endpage = (int) numOfLog / size;
        }else {
            endpage = (int) numOfLog / size + 1;
        }
        return "success";
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


    public List<User> getUserList() {return userList;}
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public Integer getGroupid() {
        return groupid;
    }
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }
    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Project getPro() {
        return pro;
    }
    public void setPro(Project pro) {
        this.pro = pro;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
    public Boolean getFlag() { return flag; }
    public void setFlag(boolean flag) { this.flag = flag; }
    public Boolean getAdminNum() { return AdminNum; }
    public void setAdminNum(boolean AdminNum) { this.AdminNum = AdminNum; }
    public List<User> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<User> managerList) {
        this.managerList = managerList;
    }

    public List<User> getLeaveList() { return leaveList; }

    public void setLeaveList(List<User> leaveList) {this.leaveList = leaveList;}

    public String getPeopleIdString() {
        return PeopleIdString;
    }

    public void setPeopleIdString(String peopleIdString) {
        PeopleIdString = peopleIdString;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void setAdminNum(Boolean adminNum) {
        AdminNum = adminNum;
    }

    public long getNumberOfTask() {
        return numberOfTask;
    }

    public void setNumberOfTask(long numberOfTask) {
        this.numberOfTask = numberOfTask;
    }

    public long getNumberOfReport() {
        return numberOfReport;
    }

    public void setNumberOfReport(long numberOfReport) {
        this.numberOfReport = numberOfReport;
    }

    public long getNumberOfUser() {
        return numberOfUser;
    }

    public void setNumberOfUser(long numberOfUser) {
        this.numberOfUser = numberOfUser;
    }

    public long getNumberOfUserOnProject() { return numberOfUserOnProject; }

    public void setNumberOfUserOnProject(long numberOfUserOnProject) { this.numberOfUserOnProject = numberOfUserOnProject;  }

    public String getSendMailException() {
        return sendMailException;
    }

    public void setSendMailException(String sendMailException) {
        this.sendMailException = sendMailException;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getEndpage() {
        return endpage;
    }

    public void setEndpage(Integer endpage) {
        this.endpage = endpage;
    }

    public List<Long> getCountTaskList() {
        return countTaskList;
    }

    public void setCountTaskList(List<Long> countTaskList) {
        this.countTaskList = countTaskList;
    }

    public List<Long> getCountReportList() {
        return countReportList;
    }

    public void setCountReportList(List<Long> countReportList) {
        this.countReportList = countReportList;
    }

    public long getNumberOfReportSubmited() {
        return numberOfReportSubmited;
    }

    public void setNumberOfReportSubmited(long numberOfReportSubmited) {
        this.numberOfReportSubmited = numberOfReportSubmited;
    }

    public List<Long> getCountTaskByStateList() {
        return countTaskByStateList;
    }

    public void setCountTaskByStateList(List<Long> countTaskByStateList) {
        this.countTaskByStateList = countTaskByStateList;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public boolean isJudge() {
        return judge;
    }

    public void setJudge(boolean judge) {
        this.judge = judge;
    }
}
