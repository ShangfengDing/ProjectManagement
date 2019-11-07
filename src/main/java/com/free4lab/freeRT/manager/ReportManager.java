package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.ProjectDAO;
import com.free4lab.freeRT.dao.ReportCommentDAO;
import com.free4lab.freeRT.dao.ReportDAO;
import com.free4lab.freeRT.dao.ReportPermissionDAO;
import com.free4lab.freeRT.model.*;
import com.free4lab.freeRT.utils.FileUtil;
import com.free4lab.freeRT.utils.LogOperationUtil;

import java.text.ParseException;
import java.util.*;

import static com.free4lab.freeRT.manager.LogManager.reportLog;

/**
 * Created by yph on 17-4-11.
 */

public class ReportManager {

    private static ReportDAO getReportDAOInstance() {
        return ReportDAO.getInstance();
    }
    private static ReportPermissionDAO getReportPermissionDAOInstance() {
        return ReportPermissionDAO.getInstance();
    }
    private static ReportCommentDAO getReportCommentDAOInstance() {
        return ReportCommentDAO.getInstance();
    }

    private static boolean produceLog(int id, String behavior) {
        ProjectDAO projectDAO=new ProjectDAO();
        int pid[] = projectDAO.findPidByReportId(id);
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("level", "info");
        properties.put("manager", "report");
        properties.put("id", String.valueOf(id));
        properties.put("behavior", behavior);
        properties.put("pid",String.valueOf(pid[0]));
        return LogOperationUtil.produceLog(properties);
    }

    public static void save(Report report) {
        getReportDAOInstance().saveReport(report);
        produceLog(report.getId(), "insert");
        reportLog(report.getId(), "insert");
    }

    public static void update(Report report) {
        getReportDAOInstance().updateReport(report);
        produceLog(report.getId(), "update");
        reportLog(report.getId(), "update");
    }

    public static void save(Report report, List<Project> projectList) {
        getReportDAOInstance().saveReport(report);
        Iterator<Project> projectIterator = projectList.iterator();
        while(projectIterator.hasNext()) {
            ReportPermission reportPermission = new ReportPermission();
            reportPermission.setReport(report);
            reportPermission.setProject(projectIterator.next());
            getReportPermissionDAOInstance().saveReportPermission(reportPermission);
//            report.getReportPermissions().save(reportPermission);
//            project.getReportPermissions().save(reportPermission);
        }
        produceLog(report.getId(), "insert");
        reportLog(report.getId(), "insert");
    }

    public static void update(Report report, List<Project> projectList) {
        getReportDAOInstance().updateReport(report);
        report = getReportDAOInstance().findReportById(report.getId());
        Iterator<ReportPermission> reportPermissionIterator = report.getReportPermissions().iterator();
        while(reportPermissionIterator.hasNext()) {
            getReportPermissionDAOInstance().deleteReportPermission(reportPermissionIterator.next());
        }
        Iterator<Project> projectIterator = projectList.iterator();
        while(projectIterator.hasNext()) {
            ReportPermission reportPermission = new ReportPermission();
            reportPermission.setReport(report);
            reportPermission.setProject(projectIterator.next());
            getReportPermissionDAOInstance().saveReportPermission(reportPermission);
//            report.getReportPermissions().save(reportPermission);
//            project.getReportPermissions().save(reportPermission);
        }
        produceLog(report.getId(), "update");
        reportLog(report.getId(), "update");
    }

    public static void trash(Report report) {
        getReportDAOInstance().trashReport(report);
        produceLog(report.getId(), "trash");
        reportLog(report.getId(), "trash");
    }

    public static void revert(Report report) {
        getReportDAOInstance().revertReport(report);
        produceLog(report.getId(), "revert");
        reportLog(report.getId(), "revert");
    }

    public static void delete(Report report) {
        if(report.getAttachment() != null) {
            String[] attachmentUuids = report.getAttachment().split(",");
            if (attachmentUuids.length > 0) {
                for (String attachmentUuid : attachmentUuids) {
                    FileUtil.delete(attachmentUuid);
                }
            }
        }
        report = getReportDAOInstance().findReportById(report.getId());
        Iterator<ReportPermission> reportPermissionIterator = report.getReportPermissions().iterator();
        while(reportPermissionIterator.hasNext()) {
            getReportPermissionDAOInstance().deleteReportPermission(reportPermissionIterator.next());
        }
        report = getReportDAOInstance().findReportById(report.getId());
        Iterator<ReportComment> reportCommentIterator = report.getReportComments().iterator();
        while(reportCommentIterator.hasNext()) {
            getReportCommentDAOInstance().deleteReportComment(reportCommentIterator.next());
        }
        getReportDAOInstance().deleteReport(report);
        produceLog(report.getId(), "delete");
        reportLog(report.getId(), "delete");
    }

    public static Report find(int id) {
        return getReportDAOInstance().findReportById(id);
    }

    public static List<Report> findAll(){
        return getReportDAOInstance().findAllReport();
    }

    public static List<Report> find(int userId, int page, int pageSize) {
        return getReportDAOInstance().findReportByUserId(userId, page, pageSize);
    }

    public static List<Report> findForSend(int userId) {
        return getReportDAOInstance().findReportByUserId(userId);
    }

    public static List<Report> findTrash(int userId, int page, int pageSize) {
        return getReportDAOInstance().findTrashReportByUserId(userId, page, pageSize);
    }

    public static List<Report> findDraft(int userId, int page, int pageSize) {
        return getReportDAOInstance().findDraftReportByUserId(userId, page, pageSize);
    }

    public static List<Report> find(int userId, int projectId) {
        return getReportDAOInstance().findReportByUserIdAndProjectId(userId, projectId);
    }

    public static List<Report> find(int userId, int projectId, int page, int pageSize) {
        return getReportDAOInstance().findReportByUserIdAndProjectId(userId, projectId, page, pageSize);
    }

    public static List<Report> find(Project project, int page, int pageSize) {
        return getReportDAOInstance().findReportByProjectId(project.getId(), page, pageSize);
    }

    public static List<Report> find(Project project) {
        return getReportDAOInstance().findReportByProjectId(project.getId());
    }

    public static List<Report> find(List<Project> projectList, int page, int pageSize) {
        List<Integer> projectIdList = new ArrayList<Integer>();
        Iterator<Project> projectIterator = projectList.iterator();
        while(projectIterator.hasNext()) {
            projectIdList.add(projectIterator.next().getId());
        }
        return getReportDAOInstance().findReportByProjectIds(projectIdList, page, pageSize);
    }

//    public static List<Report> find(int projectId, String week, int page, int pageSize) {
//        return getReportDAOInstance().findReportByProjectIdAndWeek(projectId, week, page, pageSize);
//    }

    public static List<Long> countReportByPidAndTime(int projectId) throws ParseException {return getReportDAOInstance().countReportByPidAndTime(projectId);}
    public static long countLastWeekReportSubmittedByProjectId(int projectId) throws ParseException {return getReportDAOInstance().countLastWeekReportSubmittedByProjectId(projectId);}
    public static long count(int userId) {
        return getReportDAOInstance().countReportByUserId(userId);
    }

    public static long countByProjectId(int projectId){
        return getReportDAOInstance().countReportByProjectId(projectId);
    }

    public static long countTrash(int userId) {
        return getReportDAOInstance().countTrashReportByUserId(userId);
    }

    public static long countDraft(int userId) {
        return getReportDAOInstance().countDraftReportByUserId(userId);
    }

    public static long count(int userId, int projectId) {
        return getReportDAOInstance().countReportByUserIdAndProjectId(userId, projectId);
    }

    public static long count(Project project) {
        return getReportDAOInstance().countReportByProjectId(project.getId());
    }

    public static long count(List<Project> projectList) {
        List<Integer> projectIdList = new ArrayList<Integer>();
        Iterator<Project> projectIterator = projectList.iterator();
        while(projectIterator.hasNext()) {
            projectIdList.add(projectIterator.next().getId());
        }
        return getReportDAOInstance().countReportByProjectIds(projectIdList);
    }

//    public static long count(int projectId, String week) {
//        return getReportDAOInstance().countReportByProjectIdAndWeek(projectId, week);
//    }

//    public static List<String> findWeek(int year, int month) {
//        return getReportDAOInstance().findWeekByYearAndMonth(year, month);
//    }

    public static Report findPervious(int id) {
        Report report = getReportDAOInstance().findReportById(id);
        return getReportDAOInstance().findPreviousReportByUserIdAndTime(report.getUser().getId(), report.getTime());
    }

    public static Report findNext(int id) {
        Report report = getReportDAOInstance().findReportById(id);
        return getReportDAOInstance().findNextReportByUserIdAndTime(report.getUser().getId(), report.getTime());
    }

}
