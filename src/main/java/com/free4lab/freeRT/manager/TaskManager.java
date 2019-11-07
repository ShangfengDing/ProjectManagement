package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.ProjectDAO;
import com.free4lab.freeRT.dao.TaskDAO;
import com.free4lab.freeRT.dao.TaskMemberDAO;
import com.free4lab.freeRT.model.Task;
import com.free4lab.freeRT.model.TaskMember;
import com.free4lab.freeRT.model.User;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.freeRT.utils.LogOperationUtil;
import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.free4lab.freeRT.manager.LogManager.taskLog;

public class TaskManager {
    private static final Logger LOGGER = Logger.getLogger(TaskManager.class);

    private TaskManager(){}
    private static TaskDAO getTaskDAOInstance() { return TaskDAO.getInstance();}
    private static TaskMemberDAO getTaskMemberDAOInstance(){ return TaskMemberDAO.getInstance();}

    private static boolean produceLog(int id, String behavior) {
        ProjectDAO projectDAO=new ProjectDAO();
        int pid = projectDAO.findPidByTaskId(id);
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("level", "info");
        properties.put("manager", "task");
        properties.put("id", String.valueOf(id));
        properties.put("behavior", behavior);
        properties.put("pid",String.valueOf(pid));
        return LogOperationUtil.produceLog(properties);
    }

    public static Task find(Integer id) { return getTaskDAOInstance().findTaskById(id);}

    public static List<Task> findTaskByPidAndState(int projectId,  int page, int pageSize, int state){
        return getTaskDAOInstance().findTaskByPidAndState(projectId, page, pageSize, state);
    }

    public static List<Task> findTaskByTime(int userId,String startTime, String endTime) throws ParseException {
        return getTaskDAOInstance().findTaskByTime(userId,startTime,endTime);
    }

    public static  long count(int projectId, int state){
        return  getTaskDAOInstance().countTaskByProjectIdAndState(projectId,state);
    }

    public static  long usercount(int state){
        Integer uid = (Integer) ActionContext.getContext().getSession().get(Constants.USER_ID);
        return  getTaskDAOInstance().countTaskByUserAndState(uid,state);
    }

    public static boolean save(Task task)
    {
        try{
            getTaskDAOInstance().saveTask(task);
            produceLog(task.getTaskid(), "insert");
            taskLog(task.getTaskid(), "insert");
            return true;
        }catch (Exception e) {
            LOGGER.debug(e);
            System.out.println(e);
            return false;
        }
    }
    public static boolean addMember(Task task,User user) {
        TaskMember taskMember = new TaskMember();
        taskMember.setTask(task);
        taskMember.setUser(user);
        taskMember.setIdentity(0);
        try{
            getTaskMemberDAOInstance().saveTaskMember(taskMember);
            produceLog(task.getTaskid(), "add member " + user.getId());
            taskLog(task.getTaskid(), "add user:" + user.getName());
            return true;
        }catch (Exception e) {
            LOGGER.debug(e);
            System.out.println(e);
            return false;
        }
    }
    public static boolean edit(Integer taskid, Integer projectid, String description, Integer state, Integer amount, Integer priority, String taskPic, Timestamp plan_bengintime,Timestamp plan_endtime)
    {
        try{
            Task tmp = find(taskid);
            tmp.setProject(ProjectManager.findProject(projectid));
            tmp.setDescription(description);
            tmp.setState(state);
            tmp.setAmount(amount);
            tmp.setPriority(priority);
            tmp.setTaskPic(taskPic);
            tmp.setPlan_beginTime(plan_bengintime);
            tmp.setPlan_endTime(plan_endtime);
            getTaskDAOInstance().update(tmp);
            produceLog(taskid, "update");
            taskLog(taskid, "update");
            return true;
        }catch (Exception e) {
            LOGGER.debug(e);
            System.out.println(e);
            return false;
        }
    }

    public static boolean delete(Integer id)
    {
        try{
            getTaskDAOInstance().deleteTask(id);
            produceLog(id, "delete");
            taskLog(id, "delete");
            return true;
        }catch (Exception e) {
            LOGGER.debug(e);
            System.out.println(e);
            return false;
        }
    }

    public static boolean changeState(int id,int state){
        try{
            getTaskDAOInstance().changeTaskState(id,state);
            produceLog(id, "change state to " + state);
            taskLog(id, "change state to " + state);
            return true;
        }catch (Exception e){
            LOGGER.debug(e);
            System.out.println(e);
            return false;
        }
    }

    public static void deleteMemberByTask(int tid){
        getTaskMemberDAOInstance().deleteMemberByTid(tid);
        produceLog(tid, "delete member");
        taskLog(tid, "delete member");
    }

    public static List<User> findUserByTask(int tid){
        return getTaskMemberDAOInstance().findTaskMemberByTid(tid);
    }
//    public static List<User> findUserOnProjectByTask(int tid){
//        return getTaskMemberDAOInstance().findTaskMemberOnProjectByTid(tid);
//    }
    public static List<Task> findByPid(int pid,boolean all){return getTaskDAOInstance().findTaskByPid(pid,all);}
    public static List<TaskMember> findByUid(int uid){return getTaskMemberDAOInstance().findTaskMemberByUid(uid);}
    public static long count(int userId) {return getTaskMemberDAOInstance().countTaskByUserId(userId);}
    public static List<Long> countTaskByPidAndTime(int projectId) throws ParseException {return getTaskDAOInstance().countTaskByPidAndTime(projectId);}
    public static List<Long> countLastWeekTaskByPidAndState(int projectId) throws ParseException{return  getTaskDAOInstance().countLastWeekTaskByPidAndState(projectId);}
    public static long countByProjectId(int projectId){ return getTaskDAOInstance().countTaskByProjectId(projectId);}
    public static List<TaskMember> findByUidAndPid(int uid,int pid){return getTaskMemberDAOInstance().findTaskMemberByUidAndPid(uid,pid);}
}
