package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.ProjectHotValueDAO;
import com.free4lab.freeRT.model.ProjectHotValue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.free4lab.freeRT.utils.DateUtil.findMonday;

public class ProjectHotValueManager {
    private static ProjectHotValueManager projectHotValueManager = new ProjectHotValueManager();
    private static List<ProjectHotValueManager> projectHotValueManagerList = new ArrayList <ProjectHotValueManager>();

    private static ProjectHotValueManager instance =  new ProjectHotValueManager();
    public  static ProjectHotValueManager getProjectHotValueManagerInstance(){ return instance; }
    private static ProjectHotValueDAO getDAOInstance(){
        return ProjectHotValueDAO.getInstance();
    }

    private List<ProjectHotValue> projectHotValuesList =null;


    public boolean addHotValue(String type,Integer projectId){
        Integer temperHotValue=0;
        ProjectHotValue temperProHotValue = new ProjectHotValue();
        if(type.equals("report")){
            temperHotValue=10;
        }
        else if (type.equals("endTask")){
            temperHotValue=3;
        }
        else if(type.equals("newTask")){
            temperHotValue=1;
        }
        else if(type.equals("newComment")){
            temperHotValue=1;
        }
        try{
            projectHotValuesList=getDAOInstance().findAllProjectHotValue();
            temperProHotValue.setHotvalue(temperHotValue);
            temperProHotValue.setTime(new Timestamp(System.currentTimeMillis()));
            if (projectHotValuesList.size()==0){
                temperProHotValue.setId(1);
            }
            else {
                temperProHotValue.setId(projectHotValuesList.get(projectHotValuesList.size() - 1).getId() + 1);//找到最大那个id并加上1
            }
            temperProHotValue.setProjectid(projectId);
            getDAOInstance().saveProjectHotValue(temperProHotValue);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }
    public List<ProjectHotValue> findHotValueByPid(Integer pid){
        List <ProjectHotValue> temList=getDAOInstance().projectHotValueByProjectId(pid);
        return temList;
    }

    public Integer findProjectWeekHotValue(Integer projectId){
        Integer hotValueSum=0;
        ProjectHotValueDAO temDao = new ProjectHotValueDAO();
        List<ProjectHotValue> projectHotValueList=temDao.findHotValueByWeek(new Timestamp(System.currentTimeMillis()),projectId);
        for(ProjectHotValue tem : projectHotValueList){
             hotValueSum+=tem.getHotvalue();
        }

        return hotValueSum;
    }






}
