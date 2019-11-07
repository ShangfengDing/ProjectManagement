package com.free4lab.freeRT.utils;

import com.free4lab.utils.log.LogOperation;
import com.free4lab.utils.log.LogOperationImpl;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

/**
 * Created by yph on 17-7-4.
 */
public class LogOperationUtil {

    private static LogOperation logOperation = new LogOperationImpl();

    public static boolean produceLog(Map<String, String> properties) {
        properties.put("userId", ActionContext.getContext().getSession().get(Constants.USER_ID).toString());
        properties.put("userName", ActionContext.getContext().getSession().get(Constants.USER_NAME).toString());
        return logOperation.produceLog(Constants.APP_NAME, properties);
    }

    public static String getLog(Map<String, String> properties){
        return logOperation.getLog(Constants.APP_NAME, properties);
    }

//    public static String getLog(Map<String, String> query) {
//        return logOperation.getLog(Constants.APP_NAME, query);
//    }

}
