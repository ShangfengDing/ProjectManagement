package com.free4lab.freeRT.action;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.free4lab.freeRT.model.User;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.freeRT.manager.UserManager;

public class BaseAction extends ActionSupport {

    /**
     *
     */
    private static final long serialVersionUID = 14952012357909098L;

    public static final String NOT_LOGINED = "notlogined";
    public static final String INDEX_FAILED = "indexfailed";
    public static final String STORE_ERROR = "storerror";

    public static final String NULL_QUERY = "";
    private String message;

    public static final Logger logger = Logger.getLogger(BaseAction.class);

    /**
     * 返回用户名
     *
     * @return
     */
    public String getSessionUserName() {
        return (String) ActionContext.getContext().getSession()
                .get(Constants.USER_NAME);
    }

    public String getSessionToken() {
        return (String) ActionContext.getContext().getSession()
                .get(Constants.ACCESS_TOKEN);
    }

	/*public String getSessionEmail() {
		return (String) ActionContext.getContext().getSession()
				.get(SessionConstants.USER_EMAIL);
	}*/

    public Integer getSessionUID() {
        try {
            Integer id = (Integer) ActionContext.getContext().getSession()
                    .get(Constants.USER_ID);
            return id;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getSessionAvatar() {
        return (String) ActionContext.getContext().getSession()
                .get(Constants.AVATAR);
    }

    public void update(String key, Object value){
        ActionContext.getContext().getSession().put(key, value);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getCurrentUser() {
        return UserManager.getInstance().findUserById(getSessionUID());
    }
}
