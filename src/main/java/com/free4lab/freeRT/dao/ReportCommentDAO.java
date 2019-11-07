package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.Report;
import com.free4lab.freeRT.model.ReportComment;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by yph on 17-4-26.
 */
public class ReportCommentDAO extends AbstractDAO<ReportComment> {

    private static class ReportCommentDAOSingletonHolder {
        static ReportCommentDAO instance = new ReportCommentDAO();
    }

    public static ReportCommentDAO getInstance() {
        return ReportCommentDAO.ReportCommentDAOSingletonHolder.instance;
    }

    @Override
    public Class getEntityClass() {
        return ReportComment.class;
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

    public void saveReportComment(ReportComment reportComment) {
        super.save(reportComment);
    }

    public void deleteReportComment(ReportComment reportComment) {
        super.deleteByPrimaryKey(reportComment.getId());
    }

    public List<ReportComment> findReportCommentByReport(Report report, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("report", report);
        List<ReportComment> reportCommentList = super.findByProperty(params, page - 1, pageSize, "time", false);
        this.getLogger().info(reportCommentList.size() + " report comment found with reportId:" + report.getId() + " page:" + page);
        return reportCommentList;
    }

    public List<ReportComment> findReportCommentByReportIgnoreOperation(Report report, int page, int pageSize) {
        List<ReportComment> reportCommentList = null;
        try {
            final String queryString = "SELECT model FROM ReportComment model WHERE model.report.id = :reportId AND model.description NOT LIKE '#操作#%' ORDER BY model.time DESC";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("reportId", report.getId());
            query.setMaxResults(pageSize).setFirstResult((page - 1) * pageSize);
            reportCommentList = query.getResultList();
            this.getLogger().info(reportCommentList.size() + " report comment found with reportId:" + report.getId() + " page:" + page + " ignore operation");
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return reportCommentList;
    }

    public long countReportCommentByReport(Report report) {
        long count = super.countByProperty("report", report);
        this.getLogger().info(count + " report comment count with reportId:" + report.getId());
        return count;
    }

    public long countReportCommentByReportIgnoreOperation(Report report) {
        long count = 0;
        try {
            final String queryString = "SELECT COUNT(model) FROM ReportComment model WHERE model.report.id = :reportId AND model.description NOT LIKE '#操作#%'";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("reportId", report.getId());
            count = (Long) query.getSingleResult();
            this.getLogger().info(count + " report comment count with reportId:" + report.getId() + " ignore operation");
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }

}
