package com.free4lab.freeRT.dao;

import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;

import com.free4lab.freeRT.manager.UserManager;
import com.free4lab.freeRT.model.Task;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Query;

public class TaskDAO extends AbstractDAO<Task> {

    private static class TaskDAOSingletonHolder { static TaskDAO instance = new TaskDAO();}
    public static TaskDAO getInstance() { return TaskDAOSingletonHolder.instance;}
    @Override
    public Class<Task> getEntityClass() { return Task.class;}
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

    public Task findTaskById(int id){ return findByPrimaryKey(id); }
    public void saveTask(Task task) {save(task); }
    public void deleteTask(Integer id) {
        Task tmp = findTaskById(id);
        tmp.setState(5);
        update(tmp);
    }

    public List<Task> findTaskByPid(int pid,boolean all){//all为true搜索所有任务，all为false搜索state不为5的任务
        List<Task> list = null;
        try{
            String queryString1 = "select model from Task model where model.project.id = :projectid and model.state != 5 order by model.priority DESC";
            if(all) {
                 queryString1 = "select model from Task model where model.project.id = :projectid order by model.priority DESC ";
            }
            Query query = getEntityManager().createQuery(queryString1);
            query.setParameter("projectid", pid);
            list = query.getResultList();
            if(all) {
                this.getLogger().info("The length of the finding all-task that belongs to project " + pid + " is " + list.size());
            }else{
                this.getLogger().info("The length of the finding alive-task that belongs to project " + pid + " is " + list.size());
            }
            }catch(Exception e){
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return list;
    }

    public List<Task> findTaskByPidAndState(int projectId,  int page, int pageSize, int state) {
        List<Task> list = null;
        try {
            String queryString = "SELECT model FROM Task model WHERE model.project.id = :projectId AND model.state = :state order by model.priority DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectId", projectId);
            query.setParameter("state", state);
            query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
            list = query.getResultList();
            this.getLogger().info(list.size() + " task found with projectId:" + projectId + " page:" + page);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return list;
    }

    public List<Task> findTaskByTime(int userId,String startTime, String endTime) throws ParseException {
        List<Task> list = null;

        int state = 4;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startTime);
        Date temp = sdf.parse(endTime);
        Calendar c = Calendar.getInstance();
        c.setTime(temp);
        c.add(Calendar.DAY_OF_MONTH, 1);// +1天
        Date endDate = c.getTime();
        try {
            String queryString = "SELECT model FROM Task model WHERE model.state = :state AND model.finishtime BETWEEN :startDate AND :endDate";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("state", state);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            list = query.getResultList();
            this.getLogger().info(list.size() + " task finished during" + startTime + " to" + endTime);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return list;
    }

    /*
    state 1 待处理
    state 2 进行中
    state 3 待验收
    state 4 已完成
     */
    private static Date findLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(findThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }
    private static Date findLastWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(findThisWeekSunday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }
    private static Date findLastTwoWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(findThisWeekMonday(date));
        cal.add(Calendar.DATE, -14);
        return cal.getTime();
    }
    private static Date findLastTwoWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(findThisWeekSunday(date));
        cal.add(Calendar.DATE, -14);
        return cal.getTime();
    }
    private static Date findLastThreeWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(findThisWeekMonday(date));
        cal.add(Calendar.DATE, -21);
        return cal.getTime();
    }
    private static Date findLastThreeWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(findThisWeekSunday(date));
        cal.add(Calendar.DATE, -21);
        return cal.getTime();
    }
    private static Date findLastFourWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(findThisWeekMonday(date));
        cal.add(Calendar.DATE, -28);
        return cal.getTime();
    }
    private static Date findLastFourWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(findThisWeekSunday(date));
        cal.add(Calendar.DATE, -28);
        return cal.getTime();
    }
    private static Date findThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayOfWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
    private static Date findThisWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，用最后一天与当前日期的差值
        cal.add(Calendar.DATE,  - day+8);
        return cal.getTime();
    }

    public List<Long> countLastWeekTaskByPidAndState(int projectId) throws ParseException{

        List<Long> list = new ArrayList<Long>();
        Date day=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastWeekMonday = sdf.format(findLastWeekMonday(day));
        String lastWeekSunday = sdf.format(findLastWeekSunday(day));

        try {
            long count[] = new long[4];
//            String queryString1 = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = 1 AND model.finishtime BETWEEN :lastWeekMonday AND :lastWeekSunday";
//            Query query1 = getEntityManager().createQuery(queryString1);
//            query1.setParameter("projectId", projectId);
//            query1.setParameter("lastWeekMonday", findThisWeekMonday(day));
//            query1.setParameter("lastWeekSunday", findThisWeekSunday(day));
//            count[0] = (Long) query1.getSingleResult();
//            list.add((long) (1));
//            list.add(count[0]);
//            this.getLogger().info(count[0] + " tasks' state are "+ 1 +" during" + lastWeekMonday + " to" + lastWeekSunday);
//
//            String queryString2 = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = 2 AND model.finishtime BETWEEN :lastWeekMonday AND :lastWeekSunday";
//            Query query2 = getEntityManager().createQuery(queryString2);
//            query2.setParameter("projectId", projectId);
//            query2.setParameter("lastWeekMonday", findThisWeekMonday(day));
//            query2.setParameter("lastWeekSunday", findThisWeekSunday(day));
//            count[1] = (Long) query2.getSingleResult();
//            list.add((long) (2));
//            list.add(count[1]);
//            this.getLogger().info(count[1] + " tasks' state are "+ 2 +" during" + lastWeekMonday + " to" + lastWeekSunday);
//
//            String queryString3 = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = 3 AND model.finishtime BETWEEN :lastWeekMonday AND :lastWeekSunday";
//            Query query3 = getEntityManager().createQuery(queryString3);
//            query3.setParameter("projectId", projectId);
//            query3.setParameter("lastWeekMonday", findThisWeekMonday(day));
//            query3.setParameter("lastWeekSunday", findThisWeekSunday(day));
//            count[0] = (Long) query.getSingleResult();
//            list.add((long) (1));
//            list.add(count[0]);
//            this.getLogger().info(count[0] + " tasks' state are "+ 1 +" during" + lastWeekMonday + " to" + lastWeekSunday);
//
            for(int i = 0; i < 3; i++){
                String queryString = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = :state ";
                Query query = getEntityManager().createQuery(queryString);
                query.setParameter("projectId", projectId);
                query.setParameter("state", i+1);
                count[i] = (Long) query.getSingleResult();
                list.add((long) (i+1));
                list.add(count[i]);
                this.getLogger().info(count[i] + " tasks' state are "+ (i+1) +" during" + lastWeekMonday + " to" + lastWeekSunday);
            }
            String queryString = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = 4 AND model.finishtime BETWEEN :lastWeekMonday AND :lastWeekSunday";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectId", projectId);
            query.setParameter("lastWeekMonday", findLastWeekMonday(day));
            query.setParameter("lastWeekSunday", findLastWeekSunday(day));
            count[3] = (Long) query.getSingleResult();
            list.add((long) (4));
            list.add(count[3]);
            this.getLogger().info(count[3] + " tasks' state are "+ 4 +" during" + lastWeekMonday + " to" + lastWeekSunday);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return list;
    }

    public List<Long> countTaskByPidAndTime(int projectId) throws ParseException {
//        String[] counts = new String[5];
        List<Long> list = new ArrayList<Long>();
        int state = 4;
        Date day=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        String thisWeekMonday = sdf.format(findThisWeekMonday(day));
        System.out.println(thisWeekMonday);
        String lastWeekMonday = sdf.format(findLastWeekMonday(day));
        String lastTwoWeekMonday = sdf.format(findLastTwoWeekMonday(day));
        String lastThreeWeekMonday = sdf.format(findLastThreeWeekMonday(day));
        String lastFourWeekMonday = sdf.format(findLastFourWeekMonday(day));

        String thisWeekSunday = sdf.format(findThisWeekSunday(day));
        String lastWeekSunday = sdf.format(findLastWeekSunday(day));
        String lastTwoWeekSunday = sdf.format(findLastTwoWeekSunday(day));
        String lastThreeWeekSunday = sdf.format(findLastThreeWeekSunday(day));
        String lastFourWeekSunday = sdf.format(findLastFourWeekSunday(day));
//        Date startDate = sdf.parse(startTime);
//        Date temp = sdf.parse(endTime);
//        Calendar c = Calendar.getInstance();
//        c.setTime(temp);
//        c.add(Calendar.DAY_OF_MONTH, 1);// +1天
//        Date endDate = c.getTime();
        try {
            long count1 = 0;
            long count2 = 0;
            long count3 = 0;
            long count4 = 0;
            long count5 = 0;
            String queryString1 = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = :state AND model.finishtime BETWEEN :thisWeekMonday AND :thisWeekSunday";
            Query query1 = getEntityManager().createQuery(queryString1);
            query1.setParameter("projectId", projectId);
            query1.setParameter("state", state);
            query1.setParameter("thisWeekMonday", findThisWeekMonday(day));
            query1.setParameter("thisWeekSunday", findThisWeekSunday(day));
            count1 = (Long) query1.getSingleResult();

            this.getLogger().info(count1 + " task finished during" + thisWeekMonday + " to" + thisWeekSunday);

            String queryString2 = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = :state AND model.finishtime BETWEEN :lastWeekMonday AND :lastWeekSunday";
            Query query2 = getEntityManager().createQuery(queryString2);
            query2.setParameter("projectId", projectId);
            query2.setParameter("state", state);
            System.out.println(lastWeekMonday);
            query2.setParameter("lastWeekMonday", findLastWeekMonday(day));
            query2.setParameter("lastWeekSunday", findLastWeekSunday(day));
            count2 = (Long) query2.getSingleResult();

            this.getLogger().info(count2 + " task finished during" + lastWeekMonday + " to" + lastWeekSunday);

            String queryString3 = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = :state AND model.finishtime BETWEEN :lastTwoWeekMonday AND :lastTwoWeekSunday";
            Query query3 = getEntityManager().createQuery(queryString3);
            query3.setParameter("projectId", projectId);
            query3.setParameter("state", state);
            query3.setParameter("lastTwoWeekMonday", findLastTwoWeekMonday(day));
            query3.setParameter("lastTwoWeekSunday", findLastTwoWeekSunday(day));
            count3 = (Long) query3.getSingleResult();

            this.getLogger().info(count3 + " task finished during" + lastTwoWeekMonday + " to" + lastTwoWeekSunday);

            String queryString4 = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = :state AND model.finishtime BETWEEN :lastThreeWeekMonday AND :lastThreeWeekSunday";
            Query query4 = getEntityManager().createQuery(queryString4);
            query4.setParameter("projectId", projectId);
            query4.setParameter("state", state);
            query4.setParameter("lastThreeWeekMonday", findLastThreeWeekMonday(day));
            query4.setParameter("lastThreeWeekSunday", findLastThreeWeekSunday(day));
            count4 = (Long) query4.getSingleResult();

            this.getLogger().info(count4 + " task finished during" + lastThreeWeekMonday + " to" + lastThreeWeekSunday);

            String queryString5 = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state = :state AND model.finishtime BETWEEN :lastFourWeekMonday AND :lastFourWeekSunday";
            Query query5 = getEntityManager().createQuery(queryString5);
            query5.setParameter("projectId", projectId);
            query5.setParameter("state", state);
            query5.setParameter("lastFourWeekMonday", findLastFourWeekMonday(day));
            query5.setParameter("lastFourWeekSunday", findLastFourWeekSunday(day));
            count5 = (Long) query5.getSingleResult();

            this.getLogger().info(count5 + " task finished during" + lastFourWeekMonday + " to" + lastFourWeekSunday);
//            counts = new String[]{String.valueOf(count1), String.valueOf(count2), String.valueOf(count3), String.valueOf(count4), String.valueOf(count5)};
//            System.out.println(counts);


            long dateMon1 = sdf.parse(thisWeekMonday).getTime();
            long dateMon2 = sdf.parse(lastWeekMonday).getTime();
            long dateMon3 = sdf.parse(lastTwoWeekMonday).getTime();
            long dateMon4 = sdf.parse(lastThreeWeekMonday).getTime();
            long dateMon5 = sdf.parse(lastFourWeekMonday).getTime();
                list.add(dateMon1);
                list.add(count1);
                list.add(dateMon2);
                list.add(count2);
                list.add(dateMon3);
                list.add(count3);
                list.add(dateMon4);
                list.add(count4);
                list.add(dateMon5);
                list.add(count5);

        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return list;
    }
    public long countTaskByProjectIdAndState(int projectId, int state){
        long count=0;
        try {
            String queryString = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId AND model.state=:state";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectId", projectId);
            query.setParameter("state", state);
            count = (Long) query.getSingleResult();
            this.getLogger().info(count + " task count with projectId :" + projectId +" state: " + state);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }
    public long countTaskByUserAndState(Integer uid,int state){
        long count=0;
        try {
            String queryString = "SELECT COUNT(model) FROM TaskMember model WHERE model.user.userid = :uid AND model.task.state=:state";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("uid", uid);
            query.setParameter("state", state);
            count = (Long) query.getSingleResult();
            this.getLogger().info(count + " task count with user :" + uid +" state: " + state);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }
    public long countTaskByProjectId(int projectId){
        long count=0;
        try {
            String queryString = "SELECT COUNT(model) FROM Task model WHERE model.project.id = :projectId ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectId", projectId);
            count = (Long) query.getSingleResult();
            this.getLogger().info(count + " task count with projectId :" + projectId );
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }

    public void changeTaskState(int id,int state){
        Task tmp = findTaskById(id);
        tmp.setState(state);
        if(state == 4){
            Timestamp time = new Timestamp(System.currentTimeMillis());
            tmp.setFinishtime(time);
        }
        update(tmp);
    }

}
