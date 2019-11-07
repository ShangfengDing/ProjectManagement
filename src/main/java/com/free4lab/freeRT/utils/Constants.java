package com.free4lab.freeRT.utils;

import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.Properties;
import com.free4lab.utils.account.ConfigurationUtil;

public class Constants {
    public static final String DOMAIN = "http://localhost:1015/freeshare";
    /**
     * new api common keys  http://open.free4inno.com
     */
    public static final String COMMON_KEY_APPID = "appId";
    public static final String COMMON_KEY_ACCESSTOKEN = "accesstoken";
    public static final String COMMON_KEY_RANDOM = "random";
    public static final String COMMON_KEY_TC = "tc";
    public static final String COMMON_KEY_SIGNATURE = "signature";
    public static final String COMMON_KEY_ID = "id";
    /**
     * new api common values
     */
    public static final String COMMON_VALUE_APPID = "freeproject";
    public static final String COMMON_VALUE_APPKEY = "opq";
    /**
     * new api actions
     */
    public static final String API_GROUP = "api/v1/group";

    //account
    public static final String HTTPS_ACCOUNT;
    public static final String USER;
    public static final String USER_NAME ;
    public static final String USER_EMAIL;
    public static final String ACCESS_TOKEN ;
    public static final String USER_ID;
    public static final String AVATAR;
    public final static String CLIENT_SECRET_KEY ;
    public final static String CLIENT_ID;
    public static final String ACC_TOKEN ;//= "accToken";
    public static final String PROFILE_IMAGE_URL;// = "profile_image_url";
    public  static final String APIPrefix_AvatarAccount;
    //freedisk
    public static final String APIPrefix_FreeDisk;
    //mail
    public static final String EMAIL_FROM;
    public static final String EMAIL_PWD;
    public static final String EMAIL_HOST;
    //linklog
    public static final String APP_NAME;

    static {
        final Logger logger = Logger.getLogger("App configuration");
        logger.info("+++++++++++App configuration information++++++++++++");
        try {
            ConfigurationUtil configurationUtil = new ConfigurationUtil();
            Properties p = configurationUtil.getPropertyFileConfiguration("app.properties");

            HTTPS_ACCOUNT = p.getProperty("HTTPS_ACCOUNT");
            logger.info("HTTPS_ACCOUNT:" + HTTPS_ACCOUNT);

            USER = p.getProperty("USER");
            logger.info("USER:" + USER);

            USER_NAME = p.getProperty("USER_NAME");
            logger.info("USER_NAME:" + USER_NAME);

            USER_EMAIL = p.getProperty("USER_EMAIL");
            logger.info("USER_EMAIL:" + USER_EMAIL);

            ACCESS_TOKEN = p.getProperty("ACCESS_TOKEN");
            logger.info("ACCESS_TOKEN:" + ACCESS_TOKEN);

            USER_ID = p.getProperty("USER_ID");
            logger.info("USER_ID:" + USER_ID);

            AVATAR = p.getProperty("AVATAR");
            logger.info("AVATAR:" + AVATAR);

            CLIENT_SECRET_KEY = p.getProperty("CLIENT_SECRET_KEY");
            logger.info("CLIENT_SECRET_KEY:" + CLIENT_SECRET_KEY);

            CLIENT_ID = p.getProperty("CLIENT_ID");
            logger.info("CLIENT_ID:" + CLIENT_ID);

            PROFILE_IMAGE_URL = p.getProperty("PROFILE_IMAGE_URL");
            logger.info("PROFILE_IMAGE_URL:"+PROFILE_IMAGE_URL);

            ACC_TOKEN = p.getProperty("ACC_TOKEN");
            logger.info("ACC_TOKEN:" + ACC_TOKEN);

            APIPrefix_AvatarAccount = p.getProperty("APIPrefix_AvatarAccount");

            APIPrefix_FreeDisk = p.getProperty("APIPrefix_FreeDisk");

            EMAIL_FROM = p.getProperty("EMAIL_FROM");
            logger.info("EMAIL_FROM:" + EMAIL_FROM);
            EMAIL_PWD = p.getProperty("EMAIL_PWD");
            logger.info("EMAIL_PWD:" + EMAIL_PWD);
            EMAIL_HOST = p.getProperty("EMAIL_HOST");
            logger.info("EMAIL_HOST:" + EMAIL_HOST);

            APP_NAME = p.getProperty("APP_NAME");
            logger.info("APP_NAME:" + APP_NAME);

        }catch (IOException e) {
            logger.warn("Failed to init app.properties configuration" + e.getMessage());
            throw new RuntimeException("Failed to init app.properties configuration", e);
        }
        logger.info("----------App configuration successfully----------");
    }

}
