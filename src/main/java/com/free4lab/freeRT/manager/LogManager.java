package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.LogDAO;
import com.free4lab.freeRT.dao.ProjectDAO;
import com.free4lab.freeRT.model.Log;
import com.free4lab.freeRT.utils.Constants;
import com.opensymphony.xwork2.ActionContext;

import java.sql.Timestamp;
import java.util.List;

public class LogManager {

    private static LogDAO getDAOInstance() { return LogDAO.getInstance(); }
    private static ProjectDAO getProjectDAOInstance() { return ProjectDAO.getInstance(); }

    public static void projectLog(int id, String pname,String behavior){
        Integer uid = (Integer) ActionContext.getContext().getSession().get(Constants.USER_ID);
        String userName = (String) ActionContext.getContext().getSession().get(Constants.USER_NAME);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setType("project");
        log.setId(id);
        log.setPid(id);
        log.setUserId(uid);
        log.setBehavior(behavior);
        log.setUserName(userName);
        log.setCreateTime(time);
        log.setPname(pname);
        getDAOInstance().saveLog(log);
    }

    public static void taskLog(int id, String behavior){
        Integer uid = (Integer) ActionContext.getContext().getSession().get(Constants.USER_ID);
        String userName = (String) ActionContext.getContext().getSession().get(Constants.USER_NAME);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String pname = TaskManager.find(id).getProject().getName();
        Log log = new Log();
        int pid = getProjectDAOInstance().findPidByTaskId(id);
        log.setType("task");
        log.setId(id);
        log.setPid(pid);
        log.setUserId(uid);
        log.setBehavior(behavior);
        log.setUserName(userName);
        log.setCreateTime(time);
        log.setPname(pname);
        getDAOInstance().saveLog(log);
    }

    public static void reportLog(int id, String behavior){
        Integer uid = (Integer) ActionContext.getContext().getSession().get(Constants.USER_ID);
        String userName = (String) ActionContext.getContext().getSession().get(Constants.USER_NAME);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        int pidList[] = getProjectDAOInstance().findPidByReportId(id);
        for(int i=0;pidList[i]!=0;i++){
            String pName = ProjectManager.findProject(pidList[i]).getName();
            Log log = new Log();
            log.setType("report");
            log.setId(id);
            log.setPid(pidList[i]);
            log.setUserId(uid);
            log.setBehavior(behavior);
            log.setUserName(userName);
            log.setCreateTime(time);
            log.setPname(pName);
            getDAOInstance().saveLog(log);
        }
    }

    /**
     * id是评论所属周报的id，不是周报评论的id
     * userId不是评论者的userId，是周报提交者的userId
     * @param id
     * @param behavior
     */
    public static void reportCommentLog(int id, String behavior){
        //Integer uid = (Integer) ActionContext.getContext().getSession().get(Constants.USER_ID);
        Integer uid = ReportManager.find(id).getUser().getUserid();
        String userName = (String) ActionContext.getContext().getSession().get(Constants.USER_NAME);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        int pidList[] = getProjectDAOInstance().findPidByReportId(id);
        for(int i=0;pidList[i]!=0;i++){
            String pName = ProjectManager.findProject(pidList[i]).getName();
            Log log = new Log();
            log.setType("reportComment");
            log.setId(id);
            log.setPid(pidList[i]);
            log.setUserId(uid);
            log.setBehavior(behavior);
            log.setUserName(userName);
            log.setCreateTime(time);
            log.setPname(pName);
            getDAOInstance().saveLog(log);
        }
    }

    public static List<Log> findLogByUid(String logType){
        Integer uid = (Integer) ActionContext.getContext().getSession().get(Constants.USER_ID);
        List<Log> logList = getDAOInstance().findLogByUid(uid,logType);
        if(logList.size()==0){
            return logList;
        }
        else {
            for(int i=0;i<logList.size();i++){
                if(logList.get(i).getType().equals("reportComment")){
                    if(!logList.get(i).getUserId().equals(uid)){
                        logList.remove(i);//判断周报评论是否属于当前用户，若不是，不展示
                        i--;
                    }
                }
            }
            return logList;
        }
    }

    public static List<Log> findLogByPid(Integer pid, String logType, int page, int pageSize){
        List<Log> logList = getDAOInstance().findLogByPid(pid,logType,page,pageSize);
        return logList;
    }

    public static long countLogByProjectId(Integer pid){
        long count = getDAOInstance().countLogByProjectId(pid);
        return count;
    }

    public static void deleteLog(){getDAOInstance().deleteLog();}

}
