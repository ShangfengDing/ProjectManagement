package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.ProjectDAO;
import com.free4lab.freeRT.dao.ReportCommentDAO;
import com.free4lab.freeRT.model.Report;
import com.free4lab.freeRT.model.ReportComment;
import com.free4lab.freeRT.utils.LogOperationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.free4lab.freeRT.manager.LogManager.reportCommentLog;

/**
 * Created by yph on 17-4-27.
 */
public class ReportCommentManager {

    private static ReportCommentDAO getReportCommentDAO() {
        return ReportCommentDAO.getInstance();
    }

    private static boolean produceLog(int id, String behavior) {
        ProjectDAO projectDAO=new ProjectDAO();
        int pid[] = projectDAO.findPidByReportId(id);
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("level", "info");
        properties.put("manager", "reportComment");
        properties.put("id", String.valueOf(id));
        properties.put("behavior", behavior);
        properties.put("pid",String.valueOf(pid[0]));
        return LogOperationUtil.produceLog(properties);
    }

    public static void save(ReportComment reportComment) {
        getReportCommentDAO().saveReportComment(reportComment);
        Integer reportId = reportComment.getReport().getId();
        String uName = ReportManager.find(reportId).getUser().getName();
        produceLog(reportComment.getReport().getId(), "insert");
        reportCommentLog(reportComment.getReport().getId(), "insert:"+uName);
    }

    public static void delete(ReportComment reportComment) {
        getReportCommentDAO().deleteReportComment(reportComment);
        produceLog(reportComment.getReport().getId(), "delete");
        reportCommentLog(reportComment.getReport().getId(), "delete");
    }

    public static List<ReportComment> find(Report report, int page, int pageSize, boolean ignoreOperation) {
        if(ignoreOperation) {
            return getReportCommentDAO().findReportCommentByReportIgnoreOperation(report, page, pageSize);
        } else {
            return getReportCommentDAO().findReportCommentByReport(report, page, pageSize);
        }
    }

    public static long count(Report report, boolean ignoreOperation) {
        if(ignoreOperation) {
            return getReportCommentDAO().countReportCommentByReportIgnoreOperation(report);
        } else {
            return getReportCommentDAO().countReportCommentByReport(report);
        }
    }

}
