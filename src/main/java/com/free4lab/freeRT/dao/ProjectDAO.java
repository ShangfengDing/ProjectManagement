package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.Project;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectDAO extends AbstractDAO<Project>{
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class ProjectDAOSingletonHolder {
        static ProjectDAO instance = new ProjectDAO();
    }

    public static ProjectDAO getInstance() {
        return ProjectDAOSingletonHolder.instance;
    }
    @Override
    public Class<Project> getEntityClass() {
        return Project.class;
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

    public Integer saveProject(Project pro) {
        Integer pid;
        save(pro);
        pid = pro.getId();
        return pid;
    }

    public void updateProject(Project pro) {
        update(pro);
    }

    public Project findProjectById(int id) {
        return findByPrimaryKey(id);
    }

    public Project findProjectByGid(int gid) {
        List<Project> list = findByProperty("groupid", gid);
        logger.info("The length of finding project is"+list.size());
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public int[] findPidByReportId(int reportId){
        List<Project> projectList = null;
        try {
            final String queryString = "SELECT model.project FROM ReportPermission model WHERE model.report.id =:reportId";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("reportId", reportId);
            projectList = query.getResultList();
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        int projectIdList[] = new int[5];
        int i=0;
        for(Project project:projectList){
            projectIdList[i] = project.getId();
            i++;
        }
        return projectIdList;
    }
    public List<Project>findProByReportId(Integer reportId){
        List<Project> projectList = null;
        try {
            final String queryString = "SELECT model.project FROM ReportPermission model WHERE model.report.id =:reportId";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("reportId", reportId);
            projectList = query.getResultList();
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }

        return projectList;

    }


    public int findPidByTaskId(int taskId){
        List<Project> projectList = null;
        try {
            final String queryString = "SELECT model.project FROM Task model WHERE model.taskid =:taskId";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("taskId", taskId);
            projectList = query.getResultList();
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        int pid=0;
        for(Project project:projectList){
            pid = project.getId();
        }
        return pid;
    }

    public List<Project> findProjectByName(String name) {
        List<Project> projectList = null;
        try {
            final String queryString = "SELECT model FROM Project model WHERE model.name LIKE :name";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("name", "%" + name + "%");
            projectList = query.getResultList();
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return projectList;
    }

    public List<Project> findByIdAndName(Integer uid, String name) {

        List<Project> projectList = new ArrayList<Project>();
        try{
            List<Project> projectList1 = ProjectPermissionDAO.getInstance().findUserProjectList(uid);


            Pattern pattern = Pattern.compile(name);
            for(int i = 0; i < projectList1.size(); i++){
                Matcher matcher = pattern.matcher((projectList1.get(i)).getName());
                Matcher matcher1 = pattern.matcher((projectList1.get(i)).getDescription());
                boolean m1 = matcher.find();
                boolean m2 = matcher1.find();
                if(m1 || m2){
                    projectList.add(projectList1.get(i));
                }
            }
            } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return projectList;
    }

    public List<Project> findAllProject() {
        return findAll();
    }

    public String deletePro(Integer pid){
        try {
            deleteByPrimaryKey(pid);
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return "success";
    }

}
