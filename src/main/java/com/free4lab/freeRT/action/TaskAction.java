package com.free4lab.freeRT.action;


import com.free4lab.freeRT.manager.ProjectHotValueManager;
import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.manager.TaskManager;
import com.free4lab.freeRT.manager.UserManager;
import com.free4lab.freeRT.model.*;
import com.free4lab.freeRT.utils.FileUtil;
import com.free4lab.utils.group.Result;
import org.apache.commons.collections.ListUtils;
import java.sql.Timestamp;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class TaskAction extends BaseAction {
    private static final int PAGE_SIZE = 10;//用于分页动态加载
    private int pageSum;//用于分页动态加载
    private int page;//用于分页动态加载

    private int id;
    private int userId;
    private int projectId;
    private int taskId;
    private int state;
    private int priority;
    private int amount;
    private String taskPic;
    private Timestamp time;
    private Timestamp plan_bengintime;
    private Timestamp plan_endtime;
    private String projectName;//用于MemModal框显示
    private String description;
    private String userName;//用于MyTask页面的badge显示
    private String msg;
    private String memberIdString;//任务成员的id
    private String memberChange;//编辑任务时使用，false时表示任务成员一定未改变过
    private String startTime;//筛选已完成任务的开始时间
    private String endTime;//筛选已完成任务的结束时间
    private List<User> userList;//添加人员时拉取当前项目的所有成员
    private List<User> userListOnProject;//负责某任务的在职人员
    private List<User> userListLeaveProject;//该项目的所有离职人员
    private List<User> memberList;//负责某任务的所有成员，list长度为1或2
    private List<Task> taskList;
    private List<TaskMember> taskMemberList;
    private List<TaskPic> taskPicList;//用于显示、编辑报告
    private List<Project> projectList;
    private List<Project> aliveProList;//当前用户参与的处于进行中的项目



    private List<Project> aliveProListByManager;//当前用户作为管理员的项目；
    private List<Project> otherAliveProList;//当前用户参与的处于进行中的项目(下拉显示）
    private List<User> otherUserList;//除去负责某任务所有成员后剩余的所有成员
    private long numOfTask;
    private Boolean auth = false;
    private Project pro;
    private Task task;//用于新建、编辑、显示任务
    private boolean judge=false;//false即当前用户不是此项目管理员



    public String execute(){
        int uid = getSessionUID();
        int count=0;//显示项目的数量，大于5为下拉显示
        judge=ProjectManager.isAdmin(projectId,uid);
        pro = ProjectManager.findProject(projectId);
        aliveProList = new ArrayList<Project>();
        otherAliveProList = new ArrayList<Project>();
        projectList = ProjectManager.findProjectByUser(uid);
        aliveProListByManager=ProjectManager.findUserProjectAsManager(uid);
            for (Project project:projectList) {
                if (project.getState() == 1&&count<5) {
                    aliveProList.add(project);
                    count++;
                }
                else if(project.getState() == 1&&count>=5){
                    otherAliveProList.add(project);
                }
            }
        userList = ProjectManager.findProjectAllUser(projectId);
        userListOnProject= ProjectManager.findProjectUserOnProject(projectId) ;
//        taskPicList = getTaskPicList(task);
        return Result.SUCCESS;
    }

    public String listMytask(){
        int uid = getSessionUID();
        int count=0;//显示项目的数量，大于5为下拉显示
        judge=ProjectManager.isAdmin(projectId,uid);
        pro = ProjectManager.findProject(projectId);
        aliveProList = new ArrayList<Project>();
        otherAliveProList = new ArrayList<Project>();

        projectList = ProjectManager.findProjectByUser(uid);
        for (Project project:projectList) {
            if (project.getState() == 1&&count<5) {
                aliveProList.add(project);
                count++;
            }
            else if(project.getState() == 1&&count>=5){
                otherAliveProList.add(project);
            }
        }
        boolean judgeForProject = false;
        for(Project project:aliveProList){
            if(project.getId()==projectId||projectId==0){
                judgeForProject=true;//在前五个显示里有选中的项目
            }
        }
        if(!judgeForProject){//在前五个显示里没有选中的项目；
            aliveProList.add(ProjectManager.findProject(projectId));
        }

        for(int i=0;i< otherAliveProList.size();i++){//从下拉显示项目中移除项目
            if(otherAliveProList.get(i).getId()==projectId){
                otherAliveProList.remove(i);
            }
        }

        return Result.SUCCESS;
    }

    //不要删，旧的项目展示方法，展示前5个项目，多的项目用下拉框展示
    public String listProjectTaskOld(){
        int uid = getSessionUID();
        auth = ProjectManager.isMember(projectId,uid);
        judge=ProjectManager.isAdmin(projectId,uid);
        if(auth){
            int count=0;//显示项目的数量，大于5为下拉显示
            boolean judge=false;
            aliveProList = new ArrayList<Project>();
            otherAliveProList = new ArrayList<Project>();
            projectList = ProjectManager.findProjectByUser(uid);
            for (Project project:projectList) {
                if (project.getState() == 1&&count<5) {
                    aliveProList.add(project);
                    count++;
                }
                else if(project.getState() == 1&&count>=5){
                    otherAliveProList.add(project);
                }
            }
            for(Project project:aliveProList){
                if(project.getId()==projectId){
                    judge=true;//在前五个显示里有选中的项目
                }
            }
            if(!judge){//在前五个显示里没有选中的项目；
                aliveProList.add(ProjectManager.findProject(projectId));
            }
            for(int i=0;i< otherAliveProList.size();i++){//从下拉显示项目中移除项目
                if(otherAliveProList.get(i).getId()==projectId){
                    otherAliveProList.remove(i);
                }
            }
            return "success";
        }
        else {
            return Result.ERROR;
        }

    }

    public String listProjectTask(){
        int uid = getSessionUID();
        auth = ProjectManager.isMember(projectId,uid);
        judge=ProjectManager.isAdmin(projectId,uid);
        if(auth){
            id = projectId;
            pro = ProjectManager.findProject(projectId);
            projectList = ProjectManager.findProjectByUser(uid);
            return "success";
        }
        else {
            return Result.ERROR;
        }

    }

    public String taskNumber(){
        userId = UserManager.findUserById(getSessionUID()).getId();
        numOfTask= TaskManager.count(userId);
        return Result.SUCCESS;
    }

    public String GetMember(){
        projectName = ProjectManager.findProject(projectId).getName();
        userList = ProjectManager.findProjectAllUser(projectId);
        msg = "success";
        return Result.SUCCESS;
    }

    public String AddTask(){
        Task task = new Task();
        task.setProject(ProjectManager.findProject(projectId));
        task.setDescription(description);
        task.setState(state);
        task.setAmount(amount);
        task.setPriority(priority);
        task.setTaskPic(taskPic);
        task.setPlan_beginTime(plan_bengintime);
        task.setPlan_endTime(plan_endtime);
        boolean flag = TaskManager.save(task);
        int uid = getSessionUID();
        judge=ProjectManager.isAdmin(projectId,uid);

        String[] selectedMemberId = memberIdString.split(",");
        memberList = new ArrayList<User>();
        for(String uId : selectedMemberId) {
                if(!uId.equals("")) {
                memberList.add(UserManager.findUserByPrimaryId(Integer.parseInt(uId)));
            }
        }
        for(User member : memberList){
            flag = flag && TaskManager.addMember(task,member);
        }
        if(flag){
            msg = "success";
        }else{
            msg = "fail";
        }


//        设置热力值
        new ProjectHotValueManager().addHotValue("newTask",projectId);
        return Result.SUCCESS;
    }

    public String FindTask(){
        Task tasktmp = TaskManager.find(taskId);
        description = tasktmp.getDescription();
        projectId = tasktmp.getProject().getId();
        state = tasktmp.getState();
        amount = tasktmp.getAmount();
        priority = tasktmp.getPriority();
        time=tasktmp.getTime();
        plan_bengintime=tasktmp.getPlan_beginTime();
        plan_endtime=tasktmp.getPlan_endTime();
        aliveProList = new ArrayList<Project>();
        memberList = TaskManager.findUserByTask(taskId);
        userListLeaveProject=ProjectManager.findProjectLeave(projectId);
        taskPicList=getTaskPicList(tasktmp);  //找到了这个Task中所有图片的信息：uuid,name

        int uid = getSessionUID();
        projectList = ProjectManager.findProjectByUser(uid);
        int pid =TaskManager.find(taskId).getProject().getId();//
        List<User> allUserList=ProjectManager.findProjectAllUser(pid);
        List<Integer> allUserId = new ArrayList<Integer>();
        List<Integer> memberUserId = new ArrayList<Integer>();
        List<Integer> userLeaveProjectId = new ArrayList<Integer>();
        List<Integer> otherUserId ;
        for(User user:allUserList){
            allUserId.add(user.getId());
        }
        allUserList.clear();
        for(User user:memberList){
            memberUserId.add(user.getId());
        }
        for (User user:userListLeaveProject){
            userLeaveProjectId.add(user.getId());
        }
        otherUserId=ListUtils.subtract(allUserId,memberUserId);
        otherUserId=ListUtils.subtract(otherUserId,userLeaveProjectId);
        for(int i=0;i<otherUserId.size();i++){
            allUserList.add(UserManager.findUserByPrimaryId(otherUserId.get(i)));
        }
        otherUserList=allUserList;

        for (Project project:projectList) {
            if (project.getState() == 1) {
                aliveProList.add(project);
            }
        }
        return Result.SUCCESS;
    }

    public String UpdateTask(){
        int uid = getSessionUID();
        judge=ProjectManager.isAdmin(projectId,uid);
        boolean flag = TaskManager.edit(taskId,projectId,description,state,amount,priority,taskPic,plan_bengintime,plan_endtime);
        System.out.println(memberChange);
        if(memberChange.equals("clicked")){
            TaskManager.deleteMemberByTask(taskId);
            String[] selectedMemberId = memberIdString.split(",");
            memberList = new ArrayList<User>();
            for(String uId : selectedMemberId) {
                if(!uId.equals("")) {
                    memberList.add(UserManager.findUserByPrimaryId(Integer.parseInt(uId)));
                }
            }
            for(User member : memberList){
                flag = flag && TaskManager.addMember(TaskManager.find(taskId),member);
            }
        }
        if(flag){
            msg = "success";
        }else{
            msg = "fail";
        }
        return Result.SUCCESS;
    }

    public String DeleteTask(){
        boolean flag = TaskManager.delete(taskId);
        if(flag){
            msg = "success";
        }else{
            msg = "fail";
        }
        return Result.SUCCESS;
    }

    public  String ListTaskByState(){
        pageSum = (int) Math.ceil((double)TaskManager.count(projectId,state) / (double) PAGE_SIZE);
        taskList=TaskManager.findTaskByPidAndState(projectId,page,PAGE_SIZE,state);
        for(Task thisTask:taskList) {
            thisTask.setProject(null);
        }
        return Result.SUCCESS;
    }

    public String CountTaskByState(){
        numOfTask = TaskManager.usercount(state);
        return Result.SUCCESS;
    }


    public String ListFinishTaskByTime() throws ParseException {
        userId = UserManager.findUserById(getSessionUID()).getId();//4
        User myself = UserManager.findUserByPrimaryId(userId);
        taskList=TaskManager.findTaskByTime(userId,startTime,endTime);
        for(Task thisTask:taskList) {
            thisTask.setProject(null);
            /*List<TaskMember> temp = thisTask.getTaskMembers();
            thisTask.getTaskMembers().size()*/
        }
        return Result.SUCCESS;
    }

    public String ListAllTaskByProject(){
        if(projectId==0) {
            taskList = TaskManager.findByPid(projectId,true);
        }else {
            taskList = TaskManager.findByPid(projectId,true);
        }
        for(Task thisTask:taskList) {
            thisTask.setProject(null);
        }
        return Result.SUCCESS;
    }

    public String ListAliveTaskByProject(){
        if(projectId==0) {
            taskList = TaskManager.findByPid(projectId,false);
        }else {
            taskList = TaskManager.findByPid(projectId,false);
        }
        for(Task thisTask:taskList) {
            thisTask.setProject(null);
        }
        return Result.SUCCESS;
    }
    public String ChangeTaskState(){
        boolean flag = TaskManager.changeState(taskId,state);

        if(flag){
            msg = "success";
        }else{
            msg = "fail";
        }

        //添加热力值
        if(state==4){
            new ProjectHotValueManager().addHotValue("endTask",projectId);
        }
        return Result.SUCCESS;
    }

    public String ListTaskByUserAndProject(){
        int uid = getSessionUID();
        judge=ProjectManager.isAdmin(projectId,uid);
        userId = UserManager.findUserById(getSessionUID()).getId();
        userName = UserManager.findUserById(getSessionUID()).getName();
        if(projectId == 0) {
            taskMemberList = TaskManager.findByUid(userId);
        }else {
            taskMemberList = TaskManager.findByUidAndPid(userId, projectId);
        }
        for(TaskMember thisTaskMember:taskMemberList) {
            thisTaskMember.getTask().setProject(null);
        }
        return Result.SUCCESS;
    }

    private List<TaskPic> getTaskPicList(Task task) {
        List<TaskPic> taskPicList = null;
        if(task.getTaskPic() != null) {
            String[] taskPicUuids = task.getTaskPic().split(",");
            if (taskPicUuids.length > 0) {
                taskPicList = new ArrayList<TaskPic>();
                for (String taskPicUuid : taskPicUuids) {
                    if (!taskPicUuid.equals("")) {
                        TaskPic temp = new TaskPic();
                        temp.setUuid(taskPicUuid);
                        temp.setName(FileUtil.getFileName(taskPicUuid));
                        taskPicList.add(temp);
                    }
                }
            }
        }
        return taskPicList;
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
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
    public int getProjectId() {return projectId;}
    public void setProjectId(int projectId) {this.projectId = projectId;}
    public int getTaskId() {return taskId;}
    public void setTaskId(int taskId) {this.taskId = taskId;}
    public List<Task> getTaskList() {return taskList;}
    public void setTaskList(List<Task> taskList) {this.taskList = taskList;}
    public int getState() {return state;}
    public void setState(int state) {this.state = state;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public int getPriority() {return priority;}
    public void setPriority(int priority) {this.priority = priority;}
    public int getAmount() {return amount;}
    public void setAmount(int amount) {this.amount = amount;}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<TaskMember> getTaskMemberList() {return taskMemberList;}
    public void setTaskMemberList(List<TaskMember> taskMemberList) {this.taskMemberList = taskMemberList;}
    public List<Project> getProjectList() {return projectList;}
    public void setProjectList(List<Project> projectList) {this.projectList = projectList;}
    public String getMsg() {return msg;}
    public void setMsg(String msg) {this.msg = msg;}
    public long getNumOfTask() {
        return numOfTask;
    }
    public void setNumOfTask(long numOfTask) {
        this.numOfTask = numOfTask;
    }
    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}
    public List<Project> getAliveProList() {
        return aliveProList;
    }
    public void setAliveProList(List<Project> aliveProList) {
        this.aliveProList = aliveProList;
    }
    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public List<User> getUserListOnProject() { return userListOnProject;}
    public void setUserListOnProject(List<User> userListOnProject) {this.userListOnProject = userListOnProject;}
    public List<User> getUserListLeaveProject() { return userListLeaveProject;    }

    public void setUserListLeaveProject(List<User> userListLeaveProject) { this.userListLeaveProject = userListLeaveProject;  }

    public List<User> getMemberList() {
        return memberList;
    }
    public void setMemberList(List<User> memberList) {
        this.memberList = memberList;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getMemberIdString() {
        return memberIdString;
    }
    public void setMemberIdString(String memberIdString) {
        this.memberIdString = memberIdString;
    }
    public String getMemberChange() {
        return memberChange;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public void setMemberChange(String memberChange) {
        this.memberChange = memberChange;
    }
    public List<User> getOtherUserList() {
        return otherUserList;
    }
    public void setOtherUserList(List<User> otherUserList) {
        this.otherUserList = otherUserList;
    }
    public List<Project> getOtherAliveProList() {
        return otherAliveProList;
    }
    public void setOtherAliveProList(List<Project> otherAliveProList) {
        this.otherAliveProList = otherAliveProList;
    }
    public List<Project> getAliveProListByManager() {
        return aliveProListByManager;
    }

    public Timestamp getPlan_bengintime() {
        return plan_bengintime;
    }

    public void setPlan_bengintime(Timestamp plan_bengintime) {
        this.plan_bengintime = plan_bengintime;
    }

    public Timestamp getPlan_endtime() {
        return plan_endtime;
    }

    public void setPlan_endtime(Timestamp plan_endtime) {
        this.plan_endtime = plan_endtime;
    }


    public void setAliveProListByManager(List<Project> aliveProListByManager) {
        this.aliveProListByManager = aliveProListByManager;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public Project getPro() {
        return pro;
    }

    public void setPro(Project pro) {
        this.pro = pro;
    }

    public String getTaskPic() {
        return taskPic;
    }

    public void setTaskPic(String taskPic) {
        this.taskPic = taskPic;
    }

    public List<TaskPic> getTaskPicList() {
        return taskPicList;
    }

    public void setTaskPicList(List<TaskPic> taskPicList) {
        this.taskPicList = taskPicList;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isJudge() {
        return judge;
    }

    public void setJudge(boolean judge) {
        this.judge = judge;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    class TaskPic {
        private String uuid;
        private String name;
        public String getUuid() {
            return uuid;
        }
        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
