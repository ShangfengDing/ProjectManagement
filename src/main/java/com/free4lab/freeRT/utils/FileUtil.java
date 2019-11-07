package com.free4lab.freeRT.utils;

import com.free4lab.utils.http.DiskClient;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URLEncoder;

/**
 * Created by yph on 17-6-3.
 */
public class FileUtil {

    private static final Logger LOGGER = Logger.getLogger(FileUtil.class);

    public static String upload(File file, String filename, String handlerUrl) {
        try {
            return DiskClient.upload(file, URLEncoder.encode(filename, "UTF-8"), handlerUrl);
        } catch (Exception e) {
            LOGGER.debug("upload error:" + e.getMessage());
            return null;
        }
    }

    public static boolean delete(String uuid) {
        try {
            DiskClient.delete(uuid);
        } catch (Exception e) {
            LOGGER.debug("delete error:" + e.getMessage());
            return false;
        }
        return true;
    }

    public static String getFileName(String uuid) {
        try {
            return DiskClient.getFileName(uuid);
        } catch (Exception e) {
            LOGGER.debug("getFileName error:" + e.getMessage());
            return null;
        }
    }

}
