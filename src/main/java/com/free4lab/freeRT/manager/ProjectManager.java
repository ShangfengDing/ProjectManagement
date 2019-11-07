package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.ProjectDAO;
import com.free4lab.freeRT.dao.ProjectPermissionDAO;
import com.free4lab.freeRT.dao.UserOP;
import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.model.ProjectPermission;
import com.free4lab.freeRT.model.User;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.freeRT.utils.LogOperationUtil;
import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.free4lab.freeRT.manager.LogManager.projectLog;

public class ProjectManager {
    private static Project project = new Project();
    private  static List<Project> projectList = new ArrayList<Project>();
    private static User user = new User();
    private static List<User> userList;
    private static Integer proNum;
    private static final Logger LOGGER = Logger.getLogger(ProjectManager.class);

    private ProjectManager(){}
    private static ProjectManager instance = new ProjectManager();
    public static ProjectManager getInstance() { return instance;}
    private static ProjectDAO getDAOInstance() { return ProjectDAO.getInstance(); }
    private static ProjectPermissionDAO permissionDAO = new ProjectPermissionDAO();

    private static boolean produceLog(int id, String behavior) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("level", "info");
        properties.put("manager", "project");
        properties.put("id", String.valueOf(id));
        properties.put("behavior", behavior);
        properties.put("pid", String.valueOf(id));
        return LogOperationUtil.produceLog(properties);
    }

    public static String getLog(){
        //Integer uid = (Integer) ActionContext.getContext().getSession().get(Constants.USER_ID);
        Integer uid = 10;
        projectList = ProjectManager.findProjectByUser(uid);
        List<Integer> pidList=new ArrayList<Integer>();
        for(Project project:projectList){
            pidList.add(project.getId());
        }
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("timeasc", "no");
        properties.put("size", "10");
        String result="";
        for(Integer pid:pidList){
            properties.put("pid",String.valueOf(pid));
            result = result + LogOperationUtil.getLog(properties);
        }
        return result;
    }

    public static Project findproject(Integer openId)
    {
        return getDAOInstance().findProjectById(openId);
    }

    public static boolean addProject(Project pro,Integer userid)
    {
        try{
            Integer pid = getDAOInstance().saveProject(pro);
            String pname = pro.getName();
            ProjectPermission pp = new ProjectPermission();
            pp.setRole(1);
            pp.setUserid(userid);
            pp.setProjectid(pid);
            Boolean isrelation = permissionDAO.addRelation(pp);
            produceLog(pid, "insert");
            projectLog(pid,pname, "insert");
            return true;
        }catch (Exception e) {
            LOGGER.debug(e);
            System.out.println(e);
            return false;
        }
    }
    public static List<Project> findallproject(){
        projectList=getDAOInstance().findAllProject();
            return projectList;
    }
    public static Project findProject(Integer id)
    {
        return getDAOInstance().findProjectById(id);
    }

    public static void updateProject(Project pro){
        String pname = pro.getName();
        getDAOInstance().updateProject(pro);
        produceLog(pro.getId(), "update");
        projectLog(pro.getId(),pname, "update");
    }

    public static void deletePro(Integer pid){
        String pname = ProjectManager.findProject(pid).getName();
        getDAOInstance().deletePro(pid);
        permissionDAO.deleteRelationBypid(pid);
        produceLog(pid, "delete");
        projectLog(pid,pname, "delete");
    }

    public static List<Project> findProject(String name) {
        return getDAOInstance().findProjectByName(name);
    }

    public static List<User> findProjectManager(Integer pid){
        if(pid != null){
            userList = permissionDAO.findProjectManager(pid);
            return userList;
        }else{
            return null;
        }
    }
    public static List<User> findProjectUser(Integer pid){
        if(pid != null){
            userList = permissionDAO.findProjectUser(pid);
            return userList;
        }else{
            return null;
        }
    }
    public static List<User> findProjectLeave(Integer pid){
        if(pid != null){
            userList = permissionDAO.findProjectLeave(pid);
            return userList;
        }else{
            return null;
        }
    }

    public static List<User> findProjectAllUser(Integer pid){
        if(pid != null){
            userList = permissionDAO.findProjectAllUser(pid);
            return userList;
        }else{
            return null;
        }
    }
    public static List<User> findProjectUserOnProject(Integer pid){ //项目中所有非离职人员
        if(pid != null){
          userList=permissionDAO.findProjectUser(pid);
            List<User> listManager=permissionDAO.findProjectManager(pid);
            userList.addAll(listManager);
            return userList;
        }else{
            return null;
        }
    }
    public static List<Project> findprojectByName(String name){
        if(name != null){
            projectList = ProjectDAO.getInstance().findProjectByName(name);
            return projectList;
        }else{
            return null;
        }
    }
    public static List<Project> findprojectByIdAndName(Integer uid , String name){
        if(name != null){
            projectList = ProjectDAO.getInstance().findByIdAndName(uid,name);
            return projectList;
        }else{
            return null;
        }
    }
    public static List<Project> findProjectByUser(Integer uid){
        if(uid != null){
            projectList = permissionDAO.findUserProjectList(uid);
            return projectList;
        }else{
            return null;
        }
    }
    public static List<Project> findProjectNotLeaveByUser(Integer uid){
        if(uid != null){
            projectList = permissionDAO.findUserProjectListNotLeave(uid);
            return projectList;
        }else{
            return null;
        }
    }

    public static List<Project> findUserProjectAsManager(Integer uid){
        if(uid != null){
            projectList = permissionDAO.findManagerProjectList(uid);
            return projectList;
        }else{
            return null;
        }
    }
    public static Integer findProjectNumByUser(Integer uid){
        if(uid != null){
            proNum = permissionDAO.findProjectNumByUser(uid);
            return proNum;
        }else{
            return null;
        }

    }


    /* 为项目设置离职人员*/
    public static String setLeave(Integer pid,Integer uid){
        List<ProjectPermission> ppList = new ArrayList<ProjectPermission>();
        ppList = permissionDAO.findRelation(pid,uid);
        if(ppList.size() == 1){
            ProjectPermission pp = new ProjectPermission();
            pp = ppList.get(0);
            pp.setRole(2);       //离职状态为2
            permissionDAO.updateRelation(pp);
//动态部分暂时不增加
//            String pname = ProjectManager.findProject(pid).getName();
//            UserOP temp = new UserOP();
//            String uName = temp.getUserById(uid).getName();
//            produceLog(pid, "add admin:" + uid);
//            projectLog(pid,pname, "add admin:" + uName);
            return "success";
        }else {
            return null;
        }
    }

    //将用户设置成上岗
    public static String setOnProject(Integer pid,Integer uid){
        List<ProjectPermission> ppList = new ArrayList<ProjectPermission>();
        ppList = permissionDAO.findRelation(pid,uid);
        if(ppList.size() == 1){
            ProjectPermission pp = new ProjectPermission();
            pp = ppList.get(0);
            pp.setRole(0);       //普通人员状态为0
            permissionDAO.updateRelation(pp);
//动态部分暂时不增加
//            String pname = ProjectManager.findProject(pid).getName();
//            UserOP temp = new UserOP();
//            String uName = temp.getUserById(uid).getName();
//            produceLog(pid, "add admin:" + uid);
//            projectLog(pid,pname, "add admin:" + uName);
            return "success";
        }else {
            return null;
        }
    }




    /*增加项目管理员*/
    public static String addAdmin(Integer pid,Integer uid){
        List<ProjectPermission> ppList = new ArrayList<ProjectPermission>();
        ppList = permissionDAO.findRelation(pid,uid);
        if(ppList.size() == 1){
            ProjectPermission pp = new ProjectPermission();
            pp = ppList.get(0);
            pp.setRole(1);
            permissionDAO.updateRelation(pp);
            String pname = ProjectManager.findProject(pid).getName();
            UserOP temp = new UserOP();
            String uName = temp.getUserById(uid).getName();
            produceLog(pid, "add admin:" + uid);
            projectLog(pid,pname, "add admin:" + uName);
            return "success";
        }else {
            return null;
        }
    }
    /*增加项目普通成员*/
    public static String adduser(Integer pid,Integer uid){
        ProjectPermission pp = new ProjectPermission();
        pp.setProjectid(pid);
        pp.setUserid(uid);
        pp.setRole(0);
        permissionDAO.addRelation(pp);
        String pname = ProjectManager.findProject(pid).getName();
        UserOP temp = new UserOP();
        String uName = temp.getUserById(uid).getName();
        produceLog(pid, "add user " + uid);
        projectLog(pid,pname, "add user:" + uName);
        return "success";
    }
    /*取消项目管理员权限，变为普通成员*/
    public static String cancelAdmin(Integer pid,Integer uid){
        List<ProjectPermission> ppList = new ArrayList<ProjectPermission>();
        ppList = permissionDAO.findRelation(pid,uid);
        if(ppList.size() == 1){
            ProjectPermission pp = new ProjectPermission();
            pp = ppList.get(0);
            pp.setRole(0);
            permissionDAO.updateRelation(pp);
            String pname = ProjectManager.findProject(pid).getName();
            UserOP temp = new UserOP();
            String uName = temp.getUserById(uid).getName();
            produceLog(pid, "cancel admin " + uid);
            projectLog(pid,pname, "cancel admin:" + uName);
            return "success";
        }else {
            return null;
        }
    }
    /*删除项目成员*/
    public static String deleteUser(Integer pid,Integer uid){
        List<ProjectPermission> ppList = new ArrayList<ProjectPermission>();
        ppList = permissionDAO.findRelation(pid,uid);
        if(ppList.size() == 1){
            ProjectPermission pp = new ProjectPermission();
            pp = ppList.get(0);
            permissionDAO.deleteRelation(pp);
            String pname = ProjectManager.findProject(pid).getName();
            UserOP temp = new UserOP();
            String uName = temp.getUserById(uid).getName();
            produceLog(pid, "delete user " + uid);
            projectLog(pid, pname,"delete user:" + uName);
            return "success";
        }else {
            return null;
        }
    }

    /*查找是否为项目管理员*/
    public static Boolean isAdmin(Integer pid,Integer uid){
        List<ProjectPermission> ppList = new ArrayList<ProjectPermission>();
        ppList = permissionDAO.findRelation(pid,uid);
        if(ppList.size() == 1){
            ProjectPermission pp = new ProjectPermission();
            pp = ppList.get(0);
            if(pp.getRole() == 1){
                return  true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    /*查找用户是否存在于项目组*/
    public  static Boolean isMember (Integer pid,Integer uid){
        List<ProjectPermission> ppList = new ArrayList<ProjectPermission>();
        ppList = permissionDAO.findRelation(pid,uid);
        if(ppList.size() == 1){
            return true;
        }else {
            return false;
        }
    }
    public static long countUserByProjectId(Integer projectId){
        if(projectId!=null){
            userList = permissionDAO.findProjectAllUser(projectId);
            userList.size();
            return userList.size();
        }else{
            return 0;
        }

    }
//    在职人员的人数
    public static long countUserOnProjectByProjectId(Integer projectId){
        if(projectId!=null){
            userList = permissionDAO.findProjectAllUser(projectId);
            int size=userList.size();
            userList=permissionDAO.findProjectLeave(projectId);
            size=size-userList.size();//总人数减去离职人员的人数
            userList.size();
            return size;
        }else{
            return 0;
        }

    }
}
