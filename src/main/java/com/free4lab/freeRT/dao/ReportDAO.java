package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.model.Report;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;
import com.opensymphony.xwork2.ActionContext;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by yph on 17-4-11.
 */

public class ReportDAO extends AbstractDAO<Report> {

    private static class ReportDAOSingletonHolder {
        static ReportDAO instance = new ReportDAO();
    }

    public static ReportDAO getInstance() {
        return ReportDAO.ReportDAOSingletonHolder.instance;
    }

    @Override
    public Class getEntityClass() {
        return Report.class;
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

    public void saveReport(Report report) {
        super.save(report);
    }

    public void updateReport(Report report) {
        super.update(report);
    }

    public void trashReport(Report report) {
        report.setState(report.getState() + 1);
        super.update(report);
    }

    public void revertReport(Report report) {
        report.setState(report.getState() - 1);
        super.update(report);
    }

    public void deleteReport(Report report) {
        super.deleteByPrimaryKey(report.getId());
    }

    public Report findReportById(int id) {
        return super.findByPrimaryKey(id);
    }

    public List<Report> findAllReport(){
        List<Report> reportList = null;
        String queryPid;
        Integer uid = (Integer) ActionContext.getContext().getSession().get(Constants.USER_ID);
        List<Project> projectList = ProjectManager.findProjectByUser(uid);
        if(projectList == null){
            return reportList;
        }
        else {
            if (projectList.size() == 1) {
                queryPid = "model.project.id =" + String.valueOf(projectList.get(0).getId());
            } else {
                queryPid = "model.project.id =" + String.valueOf(projectList.get(0).getId());
                for (int i = 1; i < projectList.size(); i++) {
                    queryPid = queryPid + " OR model.project.id =" + String.valueOf(projectList.get(i).getId());
                }
            }
            try {
                //final String queryString = "SELECT model.id,model.user.id,model.time,model.name FROM Report model WHERE model.state=0 ORDER BY model.time DESC";
                final String queryString = "SELECT model.report FROM ReportPermission model WHERE  ("+ queryPid +") AND model.report.state=0 ORDER BY model.report.time DESC";
                Query query = getEntityManager().createQuery(queryString);
                reportList = query.getResultList();
                Set set = new  HashSet();
                List newList = new  ArrayList();
                for (Report cd:reportList) {
                    if(set.add(cd)){
                        newList.add(cd);
                    }
                }
                reportList = newList;
            } catch (Exception e) {
                this.log(e.getMessage(), Level.SEVERE, e);
            }
            return reportList;
        }
    }

    public List<Report> findReportByUserId(int userId) {
        List<Report> reportList = null;
        try {
            final String queryString = "SELECT model FROM Report model WHERE model.user.id = :userId AND model.state=0 ORDER BY model.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            reportList = query.getResultList();
            this.getLogger().info(reportList.size() + " report found with userId:" + userId);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportList;
    }

    public List<Report> findReportByUserId(int userId, int page, int pageSize) {
        List<Report> reportList = null;
        try {
            final String queryString = "SELECT model FROM Report model WHERE model.user.id = :userId AND model.state=0 ORDER BY model.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
            reportList = query.getResultList();
            this.getLogger().info(reportList.size() + " report found with userId:" + userId + " page:" + page);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportList;
    }

    public List<Report> findTrashReportByUserId(int userId, int page, int pageSize) {
        List<Report> reportList = null;
        try {
            final String queryString = "SELECT model FROM Report model WHERE model.user.id = :userId AND model.state in (1,3) ORDER BY model.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
            reportList = query.getResultList();
            this.getLogger().info(reportList.size() + " trash report found with userId:" + userId + " page:" + page);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportList;
    }

    public List<Report> findDraftReportByUserId(int userId, int page, int pageSize) {
        List<Report> reportList = null;
        try {
            final String queryString = "SELECT model FROM Report model WHERE model.user.id = :userId AND model.state=2 ORDER BY model.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
            reportList = query.getResultList();
            this.getLogger().info(reportList.size() + " draft report found with userId:" + userId + " page:" + page);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportList;
    }

    public List<Report> findReportByUserIdAndProjectId(int userId, int projectId) {
        List<Report> reportList = null;
        try {
            final String queryString = "SELECT model.report FROM ReportPermission model WHERE model.report.user.id = :userId AND model.project.id = :projectId AND model.report.state=0 ORDER BY model.report.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            query.setParameter("projectId", projectId);
            reportList = query.getResultList();
            this.getLogger().info(reportList.size() + " report found with userId:" + userId + " projectId:" + projectId);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportList;
    }

    public List<Report> findReportByUserIdAndProjectId(int userId, int projectId, int page, int pageSize) {
        List<Report> reportList = null;
        try {
            final String queryString = "SELECT model.report FROM ReportPermission model WHERE model.report.user.id = :userId AND model.project.id = :projectId AND model.report.state=0 ORDER BY model.report.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            query.setParameter("projectId", projectId);
            query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
            reportList = query.getResultList();
            this.getLogger().info(reportList.size() + " report found with userId:" + userId + " projectId:" + projectId + " page:" + page);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportList;
    }

    public List<Report> findReportByProjectId(int projectId,  int page, int pageSize) {
        List<Report> reportList = null;
        try {
            final String queryString = "SELECT model.report FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0 ORDER BY model.report.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectId", projectId);
            query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
            reportList = query.getResultList();
            this.getLogger().info(reportList.size() + " report found with projectId:" + projectId + " page:" + page);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportList;
    }

    public List<Report> findReportByProjectId(int projectId) {
        List<Report> reportList = null;
        try {
            final String queryString = "SELECT model.report FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0 ORDER BY model.report.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectId", projectId);
            reportList = query.getResultList();
            this.getLogger().info(reportList.size() + " report found with projectId:" + projectId);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportList;
    }

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

    public List<Long> countReportByPidAndTime(int projectId) throws ParseException {
        List<Long> list = new ArrayList<Long>();

        Date day=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");


        String thisWeekMonday = sdf.format(findThisWeekMonday(day));
        String lastWeekMonday = sdf.format(findLastWeekMonday(day));
        String lastTwoWeekMonday = sdf.format(findLastTwoWeekMonday(day));
        String lastThreeWeekMonday = sdf.format(findLastThreeWeekMonday(day));
        String lastFourWeekMonday = sdf.format(findLastFourWeekMonday(day));

        String thisWeekSunday = sdf.format(findThisWeekSunday(day));
        String lastWeekSunday = sdf.format(findLastWeekSunday(day));
        String lastTwoWeekSunday = sdf.format(findLastTwoWeekSunday(day));
        String lastThreeWeekSunday = sdf.format(findLastThreeWeekSunday(day));
        String lastFourWeekSunday = sdf.format(findLastFourWeekSunday(day));
        try {
            long count1 ;
            long count2 ;
            long count3 ;
            long count4 ;
            long count5 ;

//            for(int i = 0 ;i < 5 ; i++){
//
//                String queryString1 = "SELECT  COUNT(model) FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0 AND model.report.name LIKE :namei ";
//                Query query1 = getEntityManager().createQuery(queryString1);
//                query1.setParameter("projectId", projectId);
//                query1.setParameter("name", "%" + namei + "%");
//
//                count1 = (Long) query1.getSingleResult();
//
//                this.getLogger().info(count1 + " task finished during" + thisWeekMonday + " to" + thisWeekSunday);
//            }
            String queryString1 = "SELECT  COUNT(model) FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0 AND model.report.name LIKE :name ";
            Query query1 = getEntityManager().createQuery(queryString1);
            query1.setParameter("projectId", projectId);
            query1.setParameter("name", "%" + thisWeekMonday + "%");
            count1 = (Long) query1.getSingleResult();
            this.getLogger().info(count1 + " report finished during" + thisWeekMonday + " to" + thisWeekSunday);

            String queryString2 = "SELECT  COUNT(model) FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0 AND model.report.name LIKE :name ";
            Query query2 = getEntityManager().createQuery(queryString2);
            query2.setParameter("projectId", projectId);
            query2.setParameter("name", "%" + lastWeekMonday + "%");
            count2 = (Long) query2.getSingleResult();
            this.getLogger().info(count2 + " report finished during" + lastWeekMonday + " to" + lastWeekSunday);

            String queryString3 = "SELECT  COUNT(model) FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0 AND model.report.name LIKE :name ";
            Query query3 = getEntityManager().createQuery(queryString3);
            query3.setParameter("projectId", projectId);
            query3.setParameter("name", "%" + lastTwoWeekMonday + "%");
            count3 = (Long) query3.getSingleResult();
            this.getLogger().info(count3 + " report finished during" + lastTwoWeekMonday + " to" + lastTwoWeekSunday);

            String queryString4 = "SELECT  COUNT(model) FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0 AND model.report.name LIKE :name ";
            Query query4 = getEntityManager().createQuery(queryString4);
            query4.setParameter("projectId", projectId);
            query4.setParameter("name", "%" + lastThreeWeekMonday + "%");
            count4 = (Long) query4.getSingleResult();
            this.getLogger().info(count4 + " report finished during" + lastThreeWeekMonday + " to" + lastThreeWeekSunday);

            String queryString5 = "SELECT  COUNT(model) FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0 AND model.report.name LIKE :name ";
            Query query5 = getEntityManager().createQuery(queryString5);
            query5.setParameter("projectId", projectId);
            query5.setParameter("name", "%" + lastFourWeekMonday + "%");
            count5 = (Long) query5.getSingleResult();
            this.getLogger().info(count5 + " report finished during" + lastFourWeekMonday + " to" + lastFourWeekSunday);

            System.out.println(thisWeekMonday);


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
    public List<Report> findReportByProjectIds(List<Integer> projectIdList,  int page, int pageSize) {
        List<Report> reportList = null;
        try {
            final String queryString = "SELECT DISTINCT model.report FROM ReportPermission model WHERE model.project.id in (:projectIdList) AND model.report.state=0 ORDER BY model.report.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectIdList", projectIdList);
            query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
            reportList = query.getResultList();
            this.getLogger().info(reportList.size() + " report found with projectIds:" + projectIdList + " page:" + page);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportList;
    }

//    public List<Report> findReportByProjectIdAndWeek(int projectId, String week, int page, int pageSize) {
//        List<Report> reportList = null;
//        try {
//            final String queryString = "SELECT model.report FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.name LIKE :week AND model.report.state=0 ORDER BY model.report.time DESC";
//            Query query = getEntityManager().createQuery(queryString);
//            query.setParameter("projectId", projectId);
//            query.setParameter("week", "%" + week);
//            query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
//            reportList = query.getResultList();
//            this.getLogger().info(reportList.size() + " report found with projectId:" + projectId + " week:" + week + " page:" + page);
//        } catch (Exception e) {
//            this.log(e.getMessage(), Level.SEVERE, e);
//        }
//        return reportList;
//    }

    public long countReportByUserId(int userId) {
        long count =  super.countByProperty("user.id", userId, "state", 0);
        this.getLogger().info(count + " report count with userId:" + userId);
        return count;
    }

    public long countTrashReportByUserId(int userId) {
        long count =  super.countByProperty("user.id", userId, "state", 1);
        this.getLogger().info(count + " trash report count with userId:" + userId);
        return count;
    }

    public long countDraftReportByUserId(int userId) {
        long count =  super.countByProperty("user.id", userId, "state", 2);
        this.getLogger().info(count + " draft report count with userId:" + userId);
        return count;
    }

    public long countReportByUserIdAndProjectId(int userId, int projectId) {
        long count = 0;
        try {
            final String queryString = "SELECT COUNT(model.report) FROM ReportPermission model WHERE model.report.user.id = :userId AND model.project.id = :projectId AND model.report.state=0";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            query.setParameter("projectId", projectId);
            count = (Long) query.getSingleResult();
            this.getLogger().info(count + " report count with userId:" + userId + " projectId:" + projectId);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }

    public long countLastWeekReportSubmittedByProjectId(int projectId) {
        long count = 0;
        Date day=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String lastWeekMonday = sdf.format(findLastWeekMonday(day));
        String lastWeekSunday = sdf.format(findLastWeekSunday(day));
        try {
            String queryString = "SELECT  COUNT(model) FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0 AND model.report.name LIKE :name ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectId", projectId);
            query.setParameter("name", "%" + lastWeekMonday + "%");
            count = (Long) query.getSingleResult();
            this.getLogger().info(count + " report finished during" + lastWeekMonday + " to" + lastWeekSunday);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }

    public long countReportByProjectId(int projectId) {
        long count = 0;
        try {
            final String queryString = "SELECT COUNT(model.report) FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.state=0";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectId", projectId);
            count = (Long) query.getSingleResult();
            this.getLogger().info(count + " report count with projectId:" + projectId);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }

    public long countReportByProjectIds(List<Integer> projectIdList) {
        long count = 0;
        try {
            final String queryString = "SELECT COUNT(DISTINCT model.report) FROM ReportPermission model WHERE model.project.id in (:projectIdList) AND model.report.state=0";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("projectIdList", projectIdList);
            count = (Long) query.getSingleResult();
            this.getLogger().info(count + " report count with projectIds:" + projectIdList);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }

//    public long countReportByProjectIdAndWeek(int projectId, String week) {
//        long count = 0;
//        try {
//            final String queryString = "SELECT COUNT(model.report) FROM ReportPermission model WHERE model.project.id = :projectId AND model.report.name LIKE :week AND model.report.state=0";
//            Query query = getEntityManager().createQuery(queryString);
//            query.setParameter("projectId", projectId);
//            query.setParameter("week", "%" + week);
//            count = (Long) query.getSingleResult();
//            this.getLogger().info(count + " report count with projectId:" + projectId + " week:" + week);
//        } catch (Exception e) {
//            this.log(e.getMessage(), Level.SEVERE, e);
//        }
//        return count;
//    }

//    public List<String> findWeekByYearAndMonth(int year, int month) {
//        List<String> stringList = null;
//        try {
//            final String queryString = "SELECT DISTINCT model.name from Report model where model.name LIKE '"+year+".%"+month+".__-"+year+".__.__'";
//            Query query = getEntityManager().createQuery(queryString);
//            stringList = query.getResultList();
//            this.getLogger().info(stringList.size() + " week string found with year:" + year + " month:" + month);
//        } catch (Exception e) {
//            this.log(e.getMessage(), Level.SEVERE, e);
//        }
//        return stringList;
//    }

    public Report findPreviousReportByUserIdAndTime(int userId, Timestamp time) {
        Report report = null;
        try {
            final String queryString = "SELECT model FROM Report model WHERE model.user.id = :userId AND model.state=0 AND model.time < :time ORDER BY model.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            query.setParameter("time", time);
            query.setMaxResults(1);
            report = (Report) query.getSingleResult();
            this.getLogger().info("The previous report found with userId:" + userId + " time:" + time);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return report;
    }

    public Report findNextReportByUserIdAndTime(int userId, Timestamp time) {
        Report report = null;
        try {
            final String queryString = "SELECT model FROM Report model WHERE model.user.id = :userId AND model.state=0 AND model.time > :time ORDER BY model.time ASC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("userId", userId);
            query.setParameter("time", time);
            query.setMaxResults(1);
            report = (Report) query.getSingleResult();
            this.getLogger().info("The next report found with userId:" + userId + " time:" + time);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return report;
    }

    public static List removeDuplicate(List list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                Object test = list.get(j);
                //String test1 = test.get(0);
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }

}
