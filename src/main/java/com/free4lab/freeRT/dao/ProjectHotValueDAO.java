package com.free4lab.freeRT.dao;


import com.free4lab.freeRT.model.Log;
import com.free4lab.freeRT.model.ProjectHotValue;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import static com.free4lab.freeRT.utils.DateUtil.findMonday;

public class ProjectHotValueDAO extends AbstractDAO<ProjectHotValue> {


    private  List <ProjectHotValue> projectHotValuesList= null;
    public String getClassName(){
        return getEntityClass().getName();
    }
    private static class ProjectHotValueDAOSingletonHolder {
        static ProjectHotValueDAO instance = new ProjectHotValueDAO();
    }

    public static ProjectHotValueDAO getInstance() {
        return ProjectHotValueDAO.ProjectHotValueDAOSingletonHolder.instance;
    }


    public Class getEntityClass() { return ProjectHotValue.class; }
    public static final String PU_NAME = "FreeRT_PU";
    public String getPUName() { return PU_NAME; }

    public IEntityManagerHelper getEntityManagerHelper() { return new NoCacheEntityManagerHelper(); }


    public  void saveProjectHotValue( ProjectHotValue projectHotValue){
        save(projectHotValue);
    }

    public List<ProjectHotValue> findAllProjectHotValue(){
        List<ProjectHotValue> temList = findAll();
        this.getLogger().info(temList.size() + "projectHotValue found");
        return temList;

    }


    public List<ProjectHotValue> projectHotValueByProjectId(Integer projectId){
       projectHotValuesList =findByProperty("projectid",projectId);
        logger.info("The length of finding project is"+projectHotValuesList.size());
        if(projectHotValuesList!=null&&!projectHotValuesList.isEmpty()){
            return projectHotValuesList;
        }
        else{
            return null;
        }
    }
    public List<ProjectHotValue> findHotValueByWeek(Timestamp timestamp,Integer projectId){ //找到某个项目本周的热力值记录
        Timestamp monday= findMonday(timestamp.toString());
        System.out.println("monday is:"+monday);
        try {
            final String queryString = "SELECT model FROM ProjectHotValue model WHERE model.time >:monday AND model.projectid = :projectId";
            //final String queryString = "DELETE FROM Log model WHERE model.logId = 519";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("monday", monday);
            query.setParameter("projectId", projectId);
            projectHotValuesList = query.getResultList();
            this.getLogger().info(  " The hot Value after " + timestamp + " have been found. Total delete : " +projectHotValuesList.size() );

        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return projectHotValuesList;
    }







}
