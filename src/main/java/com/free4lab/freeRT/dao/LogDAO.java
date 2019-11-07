package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.model.Log;

import com.free4lab.freeRT.model.Project;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class LogDAO  extends AbstractDAO<Log>{

    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class LogDAOSingletonHolder {
        static LogDAO instance = new LogDAO();
    }

    public static LogDAO getInstance() {
        return LogDAOSingletonHolder.instance;
    }
    @Override
    public Class<Log> getEntityClass() {
        return Log.class;
    }
    public static final String PU_NAME = "FreeRT_PU";

    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }

    public void saveLog(Log log) { super.save(log); }

    public List<Log> findAllLog() { return findAll(); }

    public void deleteLog(){
        List<Log> logList = new ArrayList<Log>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - 35);
        Date d = c.getTime();
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dataStr = sdf.format(d);
        String time = dataStr.replaceAll("/","-");
        Timestamp timeBefore = Timestamp.valueOf(time);
        try {
            final String queryString = "SELECT model FROM Log model WHERE model.createTime < :timeBefore";
            //final String queryString = "DELETE FROM Log model WHERE model.logId = 519";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("timeBefore", timeBefore);
            logList = query.getResultList();
            this.getLogger().info(  " The log before " + timeBefore + " have been deleted. Total delete : " +logList.size() );
            for (Log log:logList){
                super.deleteByPrimaryKey(log.getLogId());
            }
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
    }

    /**
     * 根据当前用户所在项目组，筛选需展示的信息
     * @param uid
     * @return
     */
    public List<Log> findLogByUid(Integer uid,String logType){
        List<Log> logList = new ArrayList<Log>();
        String queryPid;
        List<Project> projectList = ProjectManager.findProjectByUser(uid);
        if(projectList == null){
            return logList;
        }
        else {
            if(projectList.size() == 1) {
                queryPid = "model.pid =" + String.valueOf(projectList.get(0).getId());
            }
            else {
                queryPid = "model.pid =" + String.valueOf(projectList.get(0).getId());
                for(int i=1;i < projectList.size();i++){
                    queryPid = queryPid + " OR model.pid =" + String.valueOf(projectList.get(i).getId());
                 }
            }
            try {
                String queryString = null;
                if(logType == null){
                    queryString = "SELECT model FROM Log model WHERE "+ queryPid +" ORDER BY model.createTime DESC";
                    Query query = getEntityManager().createQuery(queryString);
                    logList = query.getResultList();
                }
                else {
                    queryString = "SELECT model FROM Log model WHERE ("+ queryPid +") AND model.type = :logType ORDER BY model.createTime DESC";
                    Query query = getEntityManager().createQuery(queryString);
                    query.setParameter("logType", logType);
                    logList = query.getResultList();
                }
                this.getLogger().info(logList.size() + " log found with userId:" + uid );
            } catch (Exception e) {
                this.log(e.getMessage(), Level.SEVERE, e);
            }
            return logList;
        }

    }

    /**
     * 根据项目组，筛选需展示的信息
     * @param pid
     * @return
     */
    public List<Log> findLogByPid(Integer pid, String logType, int page, int pageSize){
        List<Log> logList = new ArrayList<Log>();

        try {
            String queryString = null;
            if(logType == null){
                queryString = "SELECT model FROM Log model WHERE model.pid = :pid  ORDER BY model.createTime DESC";
                Query query = getEntityManager().createQuery(queryString);
                query.setParameter("pid", pid);
                query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
                logList = query.getResultList();
            }
            else {
                queryString = "SELECT model FROM Log model WHERE model.pid = :pid AND model.type = :logType ORDER BY model.createTime DESC";
                Query query = getEntityManager().createQuery(queryString);
                query.setParameter("pid", pid);
                query.setParameter("logType", logType);
                query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
                logList = query.getResultList();
            }
            this.getLogger().info(logList.size() + " log found with projectId:" + pid );
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return logList;
    }

    /**
     * 根据项目组，统计日志条数
     * @param pid
     * @return
     */
    public long countLogByProjectId(Integer pid) {
        long count =  super.countByProperty("pid", pid);
        this.getLogger().info(count + " logs count with projectId:" + pid);
        return count;
    }

}


