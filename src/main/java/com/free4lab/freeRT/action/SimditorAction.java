package com.free4lab.freeRT.action;

import com.free4lab.freeRT.utils.Constants;
import com.free4lab.freeRT.utils.FileUtil;
import com.free4lab.utils.group.Result;

import java.io.File;

/**
 * Created by yph on 17-6-5.
 */
public class SimditorAction {

    private File image;
    private String imageFileName;
    private String imageContentType;
    private String original_filename;
    private boolean success;
    private String file_path;

    public String uploadImage() {
        String uuid = FileUtil.upload(image, imageFileName, null);
        if(uuid == null) {
            success = false;
        } else {
            success = true;
            file_path = Constants.APIPrefix_FreeDisk + "/download?uuid=" + uuid;
        }
        return Result.SUCCESS;
    }

    public File getImage() {
        return image;
    }
    public void setImage(File image) {
        this.image = image;
    }
    public String getImageFileName() {
        return imageFileName;
    }
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
    public String getImageContentType() {
        return imageContentType;
    }
    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }
    public String getOriginal_filename() {
        return original_filename;
    }
    public void setOriginal_filename(String original_filename) {
        this.original_filename = original_filename;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getFile_path() {
        return file_path;
    }
    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

}
