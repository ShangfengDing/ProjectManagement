package com.free4lab.freeRT.action;

import com.free4lab.freeRT.utils.FileUtil;
import com.free4lab.utils.group.Result;
import com.free4lab.utils.http.DiskClient;
//import com.opensymphony.xwork2.Result;

import java.io.File;

import static com.free4lab.freeRT.action.BaseAction.logger;
import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;

/**
 * Created by yph on 17-6-2.
 */
public class FileAction {

    private File file;
    private String fileFileName;
    private String fileContentType;
    private String uuid;

    private File photo;
    private String photoFileName;
    private String photoContentType;
    private String docUuid;

    public String upload() {
        uuid = FileUtil.upload(file, fileFileName, null);
        return Result.SUCCESS;
    }

    public String delete() {
        if(FileUtil.delete(uuid)) {
            return Result.SUCCESS;
        } else {
            return Result.ERROR;
        }
    }

    //上传项目头像
    public String uploadAvatar() {
        try {
            logger.info("the filename is " + photoFileName);
            docUuid = DiskClient.upload(photo, photoFileName, null);
        } catch (Exception ex) {
            logger.debug("upload error:" + ex.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public String getFileFileName() {
        return fileFileName;
    }
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
    public String getFileContentType() {
        return fileContentType;
    }
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public File getPhoto() {
        return photo;
    }
    public void setPhoto(File photo) {
        this.photo = photo;
    }
    public String getPhotoFileName() {
        return photoFileName;
    }
    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }
    public String getPhotoContentType() {
        return photoContentType;
    }
    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }
    public String getDocUuid() {
        return docUuid;
    }
    public void setDocUuid(String docUuid) {
        this.docUuid = docUuid;
    }
}
