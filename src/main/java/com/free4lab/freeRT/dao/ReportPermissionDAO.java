package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.ReportPermission;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import java.util.List;

/**
 * Created by yph on 17-4-13.
 */
public class ReportPermissionDAO extends AbstractDAO<ReportPermission> {

    private static class ReportPermissionDAOSingletonHolder {
        static ReportPermissionDAO instance = new ReportPermissionDAO();
    }

    public static ReportPermissionDAO getInstance() {
        return ReportPermissionDAO.ReportPermissionDAOSingletonHolder.instance;
    }

    @Override
    public Class getEntityClass() {
        return ReportPermission.class;
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

    public void saveReportPermission(ReportPermission reportPermission) {
        super.save(reportPermission);
    }

    public void deleteReportPermission(ReportPermission reportPermission) {
        super.deleteByPrimaryKey(reportPermission.getId());
    }

}
