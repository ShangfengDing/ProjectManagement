package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.model.ProjectPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.free4lab.freeRT.model.User;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;

public class ProjectPermissionDAO extends AbstractDAO<ProjectPermission> {
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class ProjectPermissionDAOSingletonHolder {
        static ProjectPermissionDAO instance = new ProjectPermissionDAO();
    }
    private static ProjectPermissionDAO getProjectPermissionDAOInstance() { return ProjectPermissionDAO.getInstance();}
    public static ProjectPermissionDAO getInstance() {
        return ProjectPermissionDAOSingletonHolder.instance;
    }
    @Override
    public Class<ProjectPermission> getEntityClass() {
        return ProjectPermission.class;
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


    /*根据用户Id查找所参加项目列表*/
    public List<Project> findUserProjectList(Integer uid){
        List<ProjectPermission> pidList = findByProperty("userid",uid);
        List<Project> projectList = new ArrayList<Project>();
        if(pidList != null){
            for(ProjectPermission projectPermission:pidList){
                 ProjectDAO projectDAO = new ProjectDAO();
                 Integer pid = projectPermission.getProjectid();
                 Project project = projectDAO.findProjectById(pid);
                 projectList.add(project);
            }
        }
        return projectList;
    }
    /*根据用户Id查找他所在非离职项目*/
    public List<Project> findUserProjectListNotLeave(Integer uid){
        List<ProjectPermission> pidList = findByProperty("userid",uid);
        List<Project> projectList = new ArrayList<Project>();
        if(pidList != null){
            for(ProjectPermission projectPermission:pidList){
                ProjectDAO projectDAO = new ProjectDAO();
                if (projectPermission.getRole()!=2)
                {
                    Integer pid = projectPermission.getProjectid();
                    Project project = projectDAO.findProjectById(pid);
                    projectList.add(project);
                }

            }
        }
        return projectList;
    }


    /*根据用户Id查找用户管理的项目*/
    public List<Project> findManagerProjectList(Integer uid){
        List<ProjectPermission> pidList = findByProperty("userid",uid);
        List<Project> projectList = new ArrayList<Project>();
        if(pidList != null){
            for(ProjectPermission projectPermission:pidList){
                ProjectDAO projectDAO = new ProjectDAO();
                if(projectPermission.getRole() == 1){
                    Integer pid = projectPermission.getProjectid();
                    Project project = projectDAO.findProjectById(pid);
                    projectList.add(project);
                }
            }
        }
        return projectList;
    }

    /*根据项目Id查找项目所有成员列表*/
    public List<User> findProjectAllUser(Integer pid){
        List<User> userList = new ArrayList<User>();
        List<ProjectPermission> pidList = findByProperty("projectid",pid);
        if(pidList != null){
            for(ProjectPermission projectPermission:pidList){
                UserOP userOP = new UserOP();
                User user = userOP.getUserById(projectPermission.getUserid());
                userList.add(user);
            }
        }
        return userList;
    }
    public Integer findProjectNumByUser(Integer uid){
        Integer proNum = 0;
        List<ProjectPermission> pidList = findByProperty("userid",uid);
        proNum = pidList.size();
        return proNum;
    }

    /*根据项目Id查找项目的普通用户列表*/
    public List<User> findProjectUser(Integer pid){
        List<User> userList = new ArrayList<User>();
        List<ProjectPermission> pidList = findByProperty("projectid",pid);
        if(pidList != null){
            for(ProjectPermission projectPermission:pidList){
                if(projectPermission.getRole() == 0){
                    UserOP userOP = new UserOP();
                    User user = userOP.getUserById(projectPermission.getUserid());
                    userList.add(user);
                }
            }
        }
        return userList;
    }
    /*根据项目Id寻找项目的管理员列表*/
    public List<User> findProjectManager(Integer pid){
        List<User> userList = new ArrayList<User>();
        List<ProjectPermission> pidList = findByProperty("projectid",pid);
        if(pidList != null){
            for(ProjectPermission projectPermission:pidList){
                if (projectPermission.getRole() == 1){
                    UserOP userOP = new UserOP();
                    User user = userOP.getUserById(projectPermission.getUserid());
                    userList.add(user);
                }
            }
        }
        return userList;
    }
    /*根据项目的Id寻找项目离职人员的列表*/
    public List<User> findProjectLeave(Integer pid){
        List<User> userList = new ArrayList<User>();
        List<ProjectPermission> pidList = findByProperty("projectid",pid);
        if(pidList != null){
            for(ProjectPermission projectPermission:pidList){
                if (projectPermission.getRole() == 2){
                    UserOP userOP = new UserOP();
                    User user = userOP.getUserById(projectPermission.getUserid());
                    userList.add(user);
                }
            }
        }
        return userList;
    }

    public Boolean addRelation(ProjectPermission pp){
        if (pp != null){
            save(pp);
            return true;
        }else{
            return false;
        }
    }

    /*根据项目Id和用户Id查找对应关系*/
    public List<ProjectPermission> findRelation(Integer pid,Integer userid){
        List<ProjectPermission> pp = new ArrayList<ProjectPermission>();
        try{
            String queryString1 = "select model from ProjectPermission model where model.projectid = :projectid and model.userid = :userid ";
            Query query = getEntityManager().createQuery(queryString1);
            query.setParameter("projectid", pid);
            query.setParameter("userid", userid);
            pp = query.getResultList();
            this.getLogger().info("The length of the finding project-user relationship is " + pp.size());
        }catch(Exception e){
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return pp;
    }
    /*更新项目与用户关系*/
    public void updateRelation(ProjectPermission pp){
        update(pp);
    }
    /*删除项目与用户关系*/
    public void deleteRelation(ProjectPermission pp){
        Integer id = pp.getId();
        deleteByPrimaryKey(id);
    }
    public void deleteRelationBypid(Integer pid){
        List<ProjectPermission> pp = new ArrayList<ProjectPermission>();
        try{
            String queryString1 = "select model from ProjectPermission model where model.projectid = :projectid ";
            Query query = getEntityManager().createQuery(queryString1);
            query.setParameter("projectid", pid);
            pp = query.getResultList();
            for(ProjectPermission p:pp){
                deleteByPrimaryKey(p.getId());
            }
            this.getLogger().info("Delete Relationship By pid! " );
        }catch(Exception e){
            this.log(e.getMessage(), Level.SEVERE, e);
        }
    }
}
