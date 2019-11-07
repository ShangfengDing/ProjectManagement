package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.FileEntity;
import com.free4lab.freeRT.model.Project;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class FileDAO extends AbstractDAO<FileEntity> {
    public static class FileDAOSingletonHolder {
        static FileDAO instance = new FileDAO();
    }

    public static FileDAO getInstance(){
        return FileDAOSingletonHolder.instance;
    }

    @Override
    public Class getEntityClass() {
        return FileEntity.class;
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

    public void saveFile(FileEntity fileEntity) {
        super.save(fileEntity);
    }

    public void deleteFile(FileEntity fileEntity) {
        super.deleteByPrimaryKey(fileEntity.getId());
    }

    public List<FileEntity> findFileByProject(Project project, int page, int pageNum) {
        List<FileEntity> rst=null;
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("project", project);
        try {

            rst = super.findByProperty(map, page-1, pageNum, "time", false);
            this.getLogger().info(rst.size()+" file found with projectId:"+project.getId());
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return rst;
    }

    public List<FileEntity> findFileByContent(String content, Project project) {
        List<FileEntity> rst = null;
        try {
            final String hql = "SELECT model FROM FileEntity model WHERE model.project = :project AND ( model.description LIKE :content OR model.user.name LIKE :content)";
            Query query = getEntityManager().createQuery(hql);
            query.setParameter("project", project);
            query.setParameter("content", "%"+content+"%");
            rst = query.getResultList();
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return rst;
    }

    public long countFileByProject(Project project) {
        long count=0;
        try {
            count = super.countByProperty("project", project);
            this.getLogger().info(count + " file count with projectId:" + project.getId());
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
        }
        return count;
    }

    public void starFile(FileEntity fileEntity){
       // fileEntity.setStarUser(starUser);
        fileEntity=findByPrimaryKey(fileEntity.getId());
        if(fileEntity.getStar()==0){
            fileEntity.setStar(1);
        }
        else
        {
            fileEntity.setStar(0);
        }
        super.update(fileEntity);
    }

    public void editFile(FileEntity newfileEntity){
        FileEntity fileEntity=findByPrimaryKey(newfileEntity.getId());
        fileEntity.setDescription(newfileEntity.getDescription());
        fileEntity.setUrl(newfileEntity.getUrl());
        super.update(fileEntity);
    }

}
