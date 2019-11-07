package com.free4lab.freeRT.action;

import com.free4lab.freeRT.manager.ProjectHotValueManager;
import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.manager.UserManager;
import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.model.ProjectHotValue;
import com.free4lab.freeRT.model.User;
import com.free4lab.utils.group.Result;


import java.util.*;


public class MyProjectAction extends BaseAction{
    private Integer uid;
    private Project pro;
    private List<Project> prolist  = new ArrayList<Project>();//获取用户参与的所有的项目
//    private List<Project> completedprolist = new ArrayList<Project>();//获取用户所有已完成项目
//    private List<Project> livingprolist = new ArrayList<Project>();//获取用户的所有进行中项目
//    private List<Project> suspendprolist = new ArrayList<Project>();//获取用户所有已暂停项目
    private List<Project> userProjectAsManager = new ArrayList<Project>();//获取用户为管理员的项目
    private List<Project> projectList  = new ArrayList<Project>();
    private List<Project> allproject = new ArrayList<Project>();//获取所有数据库中的项目
    private Map<Project, Integer> AllProject = new HashMap<Project, Integer>(); //存储所有项目及其热力值
    private Map<Project,Integer> AllProjectList = new LinkedHashMap<Project, Integer>();  //存储热力值排序后所有项目
    private Map<Project, Integer> livinghotvalueList = new HashMap<Project, Integer>();  //存储进行中项目及其热力值
    private Map<Project,Integer> LivingHotValueList = new LinkedHashMap<Project, Integer>();//存储热力值排序后进行中项目
    private Map<Project, Integer> SuspendHotValueList = new LinkedHashMap<Project, Integer>();//存储已暂停项目及其热力值
    private Map<Project, Integer> CompleteHotValueList = new LinkedHashMap<Project, Integer>();//存储已完成项目及其热力值
    private Map<Project, Integer> AllHotValueList = new LinkedHashMap<Project, Integer>();// 存储用户全部项目及其热力值
    private Map<Project, Integer> ManageHotValueList = new LinkedHashMap<Project, Integer>();//存储用户管理的项目及其热力值
    private Map<Project, Integer> tempManageHotValueList = new HashMap<Project, Integer>();//存储用户管理的项目及其热力值临时
    private Map<Project, Integer> tempCompleteHotValueList = new HashMap<Project, Integer>();//存储已完成项目及其热力值临时
    private Map<Project, Integer> tempSuspendHotValueList = new HashMap<Project, Integer>();//存储已暂停项目及其热力值临时
    private Map<Project, Integer> tempAllHotValueList = new HashMap<Project, Integer>();// 存储用户全部项目及其热力值临时
    private Integer pid;
    private List<User> userList  = new ArrayList<User>();//获得所有用户的List
    private String keyword;
    private List<User> resultList = new ArrayList<User>();
    private String name;

    public String execute() {
        uid = getSessionUID();
        allproject = ProjectManager.findallproject();
//        System.out.println("所有项目："+allproject);
        prolist = ProjectManager.findProjectByUser(uid);
        userProjectAsManager = ProjectManager.findUserProjectAsManager(uid);
        for(Project project:userProjectAsManager){
            tempManageHotValueList.put(project,ProjectHotValueManager.getProjectHotValueManagerInstance().findProjectWeekHotValue(project.getId()));
        }
        for (Project project:prolist){
            tempAllHotValueList.put(project,ProjectHotValueManager.getProjectHotValueManagerInstance().findProjectWeekHotValue(project.getId()));
            switch (project.getState()){
                case 0:
                    tempCompleteHotValueList.put(project,ProjectHotValueManager.getProjectHotValueManagerInstance().findProjectWeekHotValue(project.getId()));
                    break;
                case 1:
                    livinghotvalueList.put(project,ProjectHotValueManager.getProjectHotValueManagerInstance().findProjectWeekHotValue(project.getId()));
                    break;
                case 2:
                    tempSuspendHotValueList.put(project,ProjectHotValueManager.getProjectHotValueManagerInstance().findProjectWeekHotValue(project.getId()));
                    break;
                default:
                    break;
            }
        }
        int size = prolist.size();
        System.out.print("prolist = "+size);

        //所有的项目序号排序
        Set<Map.Entry<Project,Integer>> allprojectmap = tempAllHotValueList.entrySet();
        List<Map.Entry<Project,Integer>> allpro = new LinkedList<Map.Entry<Project,Integer>>(allprojectmap);
        Collections.sort(allpro, new Comparator<Map.Entry<Project,Integer>>() {
            public int compare(Map.Entry<Project, Integer> o1, Map.Entry<Project, Integer> o2) {
                return o1.getKey().getId().compareTo(o2.getKey().getId());
            }
        });
        for(Map.Entry<Project,Integer> entry: allpro){
            AllHotValueList.put(entry.getKey(), entry.getValue());
        }

        //进行中项目序号排序
        Set<Map.Entry<Project,Integer>> livingproject = livinghotvalueList.entrySet();
        List<Map.Entry<Project,Integer>> living = new LinkedList<Map.Entry<Project,Integer>>(livingproject);
        Collections.sort(living, new Comparator<Map.Entry<Project,Integer>>() {
            public int compare(Map.Entry<Project, Integer> o1, Map.Entry<Project, Integer> o2) {
                return o1.getKey().getId().compareTo(o2.getKey().getId());
            }
        });
        for(Map.Entry<Project,Integer> entry: living){
            LivingHotValueList.put(entry.getKey(), entry.getValue());
        }

        //已暂停的项目序号排序
        Set<Map.Entry<Project,Integer>> suspendproject = tempSuspendHotValueList.entrySet();
        List<Map.Entry<Project,Integer>> suspendpro = new LinkedList<Map.Entry<Project,Integer>>(suspendproject);
        Collections.sort(suspendpro, new Comparator<Map.Entry<Project,Integer>>() {
            public int compare(Map.Entry<Project, Integer> o1, Map.Entry<Project, Integer> o2) {
                return o1.getKey().getId().compareTo(o2.getKey().getId());
            }
        });
        for(Map.Entry<Project,Integer> entry: suspendpro){
            SuspendHotValueList.put(entry.getKey(), entry.getValue());
        }

        //已完成的项目序号排序
        Set<Map.Entry<Project,Integer>> completeproject = tempCompleteHotValueList.entrySet();
        List<Map.Entry<Project,Integer>> completepro = new LinkedList<Map.Entry<Project,Integer>>(completeproject);
        Collections.sort(completepro, new Comparator<Map.Entry<Project,Integer>>() {
            public int compare(Map.Entry<Project, Integer> o1, Map.Entry<Project, Integer> o2) {
                return o1.getKey().getId().compareTo(o2.getKey().getId());
            }
        });
        for(Map.Entry<Project,Integer> entry: completepro){
            CompleteHotValueList.put(entry.getKey(), entry.getValue());
        }

        //管理的项目序号排序
        Set<Map.Entry<Project,Integer>> manageproject = tempManageHotValueList.entrySet();
        List<Map.Entry<Project,Integer>> managepro = new LinkedList<Map.Entry<Project,Integer>>(manageproject);
        Collections.sort(managepro, new Comparator<Map.Entry<Project,Integer>>() {
            public int compare(Map.Entry<Project, Integer> o1, Map.Entry<Project, Integer> o2) {
                return o1.getKey().getId().compareTo(o2.getKey().getId());
            }
        });
        for(Map.Entry<Project,Integer> entry: managepro){
            ManageHotValueList.put(entry.getKey(), entry.getValue());
        }

//        //进行中项目热力值排序
//        Set<Map.Entry<Project,Integer>> livingproject = livinghotvalueList.entrySet();
//        List<Map.Entry<Project,Integer>> living = new LinkedList<Map.Entry<Project,Integer>>(livingproject);
//        Collections.sort(living, new Comparator<Map.Entry<Project,Integer>>() {
//            public int compare(Map.Entry<Project, Integer> o1, Map.Entry<Project, Integer> o2) {
//                return o2.getValue().compareTo(o1.getValue());
//            }
//        });
//        for(Map.Entry<Project,Integer> entry: living){
//            LivingHotValueList.put(entry.getKey(), entry.getValue());
//        }

        //排序用于排行榜展示
        for(Project project:allproject){
            switch (project.getState()){
                case 1:
                AllProject.put(project,ProjectHotValueManager.getProjectHotValueManagerInstance().findProjectWeekHotValue(project.getId()));
                break;
            }
        }
        Set<Map.Entry<Project,Integer>> allproject = AllProject.entrySet();
        List<Map.Entry<Project,Integer>> allprojectlist = new LinkedList<Map.Entry<Project,Integer>>(allproject);
        Collections.sort(allprojectlist, new Comparator<Map.Entry<Project,Integer>>() {
            public int compare(Map.Entry<Project, Integer> o1, Map.Entry<Project, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(Map.Entry<Project,Integer> entry: allprojectlist){
            AllProjectList.put(entry.getKey(), entry.getValue());
        }
        return Result.SUCCESS;
    }

    public String userAll() {
        userList = UserManager.findAllUser();
        return Result.SUCCESS;
    }

    public String searchUser(){
        resultList = UserManager.findUserByName(keyword);
        return Result.SUCCESS;
    }

    public String searchProjectByName(){
        projectList = ProjectManager.findprojectByName(name);
        return Result.SUCCESS;
    }

    public String searchProjectByIdAndName(){
        uid = getSessionUID();
        projectList = ProjectManager.findprojectByIdAndName(uid,name);
        return Result.SUCCESS;
    }

    public String goToHead(){
        return Result.SUCCESS;
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

    public List<Project> getProlist() {
        return prolist;
    }

    public void setProlist(List<Project> prolist) {
        this.prolist = prolist;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

//    public List<Project> getCompletedprolist() {
//        return completedprolist;
//    }
//
//    public void setCompletedprolist(List<Project> completedprolist) {
//        this.completedprolist = completedprolist;
//    }
//
//    public List<Project> getLivingprolist() {
//        return livingprolist;
//    }
//
//    public void setLivingprolist(List<Project> livingprolist) {
//        this.livingprolist = livingprolist;
//    }
//
//    public List<Project> getSuspendprolist() {
//        return suspendprolist;
//    }
//
//    public void setSuspendprolist(List<Project> suspendprolist) {
//        this.suspendprolist = suspendprolist;
//    }

    public List<Project> getUserProjectAsManager() {
        return userProjectAsManager;
    }

    public void setUserProjectAsManager(List<Project> userProjectAsManager) {
        this.userProjectAsManager = userProjectAsManager;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<User> getResultList() {
        return resultList;
    }

    public void setResultList(List<User> resultList) {
        this.resultList = resultList;
    }

    public List<Project> getProjectList() {  return projectList;   }

    public void setProjectList(List<Project> projectList) {    this.projectList = projectList;    }

    public String getName() {    return name;    }

    public void setName(String name) {    this.name = name;    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Map<Project, Integer> getLivinghotvalueList() {
        return livinghotvalueList;
    }

    public void setLivinghotvalueList(Map<Project, Integer> livinghotvalueList) {
        this.livinghotvalueList = livinghotvalueList;
    }

    public Map<Project, Integer> getLivingHotValueList() {
        return LivingHotValueList;
    }

    public void setLivingHotValueList(Map<Project, Integer> livingHotValueList) {
        LivingHotValueList = livingHotValueList;
    }

    public Map<Project, Integer> getSuspendHotValueList() {
        return SuspendHotValueList;
    }

    public void setSuspendHotValueList(Map<Project, Integer> suspendHotValueList) {
        SuspendHotValueList = suspendHotValueList;
    }

    public Map<Project, Integer> getCompleteHotValueList() {
        return CompleteHotValueList;
    }

    public void setCompleteHotValueList(Map<Project, Integer> completeHotValueList) {
        CompleteHotValueList = completeHotValueList;
    }

    public Map<Project, Integer> getAllHotValueList() {
        return AllHotValueList;
    }

    public void setAllHotValueList(Map<Project, Integer> allHotValueList) {
        AllHotValueList = allHotValueList;
    }

    public Map<Project, Integer> getManageHotValueList() {
        return ManageHotValueList;
    }

    public void setManageHotValueList(Map<Project, Integer> manageHotValueList) {
        ManageHotValueList = manageHotValueList;
    }

    public List<Project> getAllproject() {
        return allproject;
    }

    public void setAllproject(List<Project> allproject) {
        this.allproject = allproject;
    }

    public Map<Project, Integer> getAllProject() {
        return AllProject;
    }

    public void setAllProject(Map<Project, Integer> allProject) {
        AllProject = allProject;
    }

    public Map<Project, Integer> getAllProjectList() {
        return AllProjectList;
    }

    public void setAllProjectList(Map<Project, Integer> allProjectList) {
        AllProjectList = allProjectList;
    }

    public Map<Project, Integer> getTempManageHotValueList() {
        return tempManageHotValueList;
    }

    public void setTempManageHotValueList(Map<Project, Integer> tempManageHotValueList) {
        this.tempManageHotValueList = tempManageHotValueList;
    }

    public Map<Project, Integer> getTempCompleteHotValueList() {
        return tempCompleteHotValueList;
    }

    public void setTempCompleteHotValueList(Map<Project, Integer> tempCompleteHotValueList) {
        this.tempCompleteHotValueList = tempCompleteHotValueList;
    }

    public Map<Project, Integer> getTempSuspendHotValueList() {
        return tempSuspendHotValueList;
    }

    public void setTempSuspendHotValueList(Map<Project, Integer> tempSuspendHotValueList) {
        this.tempSuspendHotValueList = tempSuspendHotValueList;
    }

    public Map<Project, Integer> getTempAllHotValueList() {
        return tempAllHotValueList;
    }

    public void setTempAllHotValueList(Map<Project, Integer> tempAllHotValueList) {
        this.tempAllHotValueList = tempAllHotValueList;
    }
}

