package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.FileDAO;
import com.free4lab.freeRT.model.FileEntity;
import com.free4lab.freeRT.model.Project;

import java.util.List;

public class FileManager {
    private static FileDAO getFileDAO() {
        return FileDAO.getInstance();
    }

    public static void save(FileEntity fileEntity) {
        getFileDAO().saveFile(fileEntity);
    }

    public static void delete(FileEntity fileEntity) {
        getFileDAO().deleteFile(fileEntity);
    }

    public static List<FileEntity> find(Project project, int page, int pageNum) {
        List<FileEntity> rst = getFileDAO().findFileByProject(project, page, pageNum);
        for (FileEntity f:rst) {
            if (f.getSource()==1) {
                f.setUrl("http://freedisk.free4inno.com/download?uuid="+f.getUrl());
            }
        }
        return rst;
    }

    public static List<FileEntity> findByContent(String realname, Project project) {
        return getFileDAO().findFileByContent(realname, project);
    }

    public static long count(Project project) {
        return getFileDAO().countFileByProject(project);
    }

    public static void starFile(FileEntity fileEntity){
        getFileDAO().starFile(fileEntity);
    }

    public static  void editFile(FileEntity fileEntity){
        getFileDAO().editFile(fileEntity);
    }
}
