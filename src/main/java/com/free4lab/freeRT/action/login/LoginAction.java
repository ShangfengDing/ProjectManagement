package com.free4lab.freeRT.action.login;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import com.free4lab.freeRT.dao.UserOP;
import com.free4lab.freeRT.manager.UserManager;
import com.free4lab.freeRT.model.User;
import com.free4lab.freeRT.utils.Constants;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.free4lab.utils.account.NewBaseLoginAction;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction extends NewBaseLoginAction{

    private static final long serialVersionUID = 1L;
    private String redirectUrl;
    final static Logger logger = Logger.getLogger(LoginAction.class);
//	private static LinklogManager log = new LinklogManager(LoginAction.class);

    @Override
    public boolean writeToSession(JSONObject obj) {

        logger.info("LoginAction：写session"+obj);
        //可以通过检查uid字段判断此json对象是否有效
        if (obj != null && obj.has(Constants.USER_ID)){
            Map<String, Object> session = ActionContext.getContext().getSession();
            //将用户信息存入session，session的key可以自己在应用的Constants里定义
            Integer uid = jsonGetUserId(obj);
            String email = jsonGetEmail(obj);
            String realname = jsonGetRealName(obj);
            String profileimageurl = jsonGetProfileImageUrl(obj);
            session.put(Constants.USER_ID, uid);
            session.put(Constants.USER_EMAIL, email);
            session.put(Constants.USER_NAME, realname);
            session.put(Constants.PROFILE_IMAGE_URL, profileimageurl);
            User user = new User();
            user.setUserid(uid);
            user.setEmail(email);
            user.setName(realname);
            user.setAvatar(profileimageurl);
            UserManager.addUser(user);
            return true;
        } else {
            logger.info("获取的用户信息为空");
        }
        return false;
    }

    @Override
    public String giveDefaultRedirect() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Integer uid = (Integer) session.get(Constants.USER_ID);
        return "/home";
    }

    @Override
    public String writeAccessTokenToSession(String access_token) {
        logger.info("LoginAction：accessToken，写session"+access_token);
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put(Constants.ACCESS_TOKEN, access_token);
        session.put(Constants.ACC_TOKEN, access_token.substring(8, 24));
        return null;
    }

    @Override
    public String giveClientSecret() {
        return Constants.CLIENT_SECRET_KEY;
    }

    @Override
    public String giveLandingUrl() {
        return "/views/landing.jsp";
    }
    public String getRedirectUrl() {
        try {
            redirectUrl = URLDecoder.decode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("encode redirectUrl error!!!", e);
        }
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

}
