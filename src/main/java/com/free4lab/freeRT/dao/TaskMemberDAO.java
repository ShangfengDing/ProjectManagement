package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.TaskMember;
import com.free4lab.freeRT.model.User;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;


public class TaskMemberDAO extends AbstractDAO<TaskMember>{
    private static class TaskMemberDAOSingletonHolder {static TaskMemberDAO instance = new TaskMemberDAO();}
    public static TaskMemberDAO getInstance() {return TaskMemberDAOSingletonHolder.instance;}

    @Override
    public Class getEntityClass() {
        return TaskMember.class;
    }
    public String getClassName() { return getEntityClass().getName();}

    public static final String PU_NAME = "FreeRT_PU";
    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }

    public void saveTaskMember(TaskMember taskMember) {
        save(taskMember);
    }
    public void deleteMemberByTid(int tid){
        List<TaskMember> list;
        try{
            String queryString ="select model from TaskMember model where model.task.taskid = :taskid";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("taskid", tid);
            list = query.getResultList();
            for(TaskMember thisTaskMember:list) {
                deleteByPrimaryKey(thisTaskMember.getId());
            }
        }catch(Exception e){
            this.log(e.getMessage(), Level.SEVERE, e);
        }

    }
    public List<TaskMember> findTaskMemberByUid(int uid){
        List<TaskMember> list = null;
        try{
            String queryString ="select model from TaskMember model where model.user.id = :userid and model.task.state != 5 and model.task.project.state =1 order by model.task.priority DESC ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userid", uid);
            list = query.getResultList();
            this.getLogger().info("The length of the finding alive-task that belongs to user "+uid+" is "+list.size());
        }catch(Exception e){
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return list;
    }
    public List<TaskMember> findTaskMemberByUidAndPid(int uid,int pid){
        List<TaskMember> list = null;
        try{
            String queryString ="select model from TaskMember model where model.user.id = :userid and model.task.project.id = :projectid and model.task.state != 5 order by model.task.priority DESC ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userid", uid);
            query.setParameter("projectid", pid);
            list = query.getResultList();
            this.getLogger().info("The length of the finding alive-task that belongs to user "+uid+" and project "+pid+" is "+list.size());
        }catch(Exception e){
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return list;
    }
    public List<User> findTaskMemberByTid(int tid){
        List<User> list = null;
        try{
            String queryString = "select model.user from TaskMember model where model.task.id = :taskid ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("taskid", tid);
            list = query.getResultList();
            this.getLogger().info("The count of the member of task-"+tid+" is "+list.size());
        }catch(Exception e){
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return list;
    }
//    public List<User> findTaskMemberOnProjectByTid(int tid){
//        List<User> list = null;
//        try{
//            String queryString = "select model.user from TaskMember model , ProjectPermission  modelTwo where model.task.id = :taskid and modelTwo.role!=2 ";
//            Query query = getEntityManager().createQuery(queryString);
//            query.setParameter("taskid", tid);
//            list = query.getResultList();
//            this.getLogger().info("The count of the member of task-"+tid+" is "+list.size());
//        }catch(Exception e){
//            this.log(e.getMessage(), Level.SEVERE, e);
//        }
//        return list;
//    }
    public long countTaskByUserId(int userId) {
        long count =  0;
        try {
            final String queryString = "SELECT COUNT(model) FROM TaskMember model WHERE model.user.id = :userId AND model.task.state != 5 and model.task.project.state =1";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            count = (Long) query.getSingleResult();
            this.getLogger().info(count + " task count with userId:" + userId);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }

}
