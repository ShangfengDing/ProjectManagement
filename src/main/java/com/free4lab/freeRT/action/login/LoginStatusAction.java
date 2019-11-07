package com.free4lab.freeRT.action.login;

import com.free4lab.freeRT.action.BaseAction;
import com.free4lab.freeRT.model.User;
import com.opensymphony.xwork2.ActionContext;

public class LoginStatusAction extends BaseAction{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //	private static LinklogManager log = new LinklogManager(LoginStatusAction.class);
    private User data;
    private int code;
    private String message;

    public String LoginStatus()
    {
        setCode(500);
        data = getCurrentUser();
        if(null == data)
        {
            setMessage("offline");
            return ERROR;
        }
        setCode(200);
        setMessage("online");
        return SUCCESS;
    }
    public String loginout()
    {
        setCode(500);
//		log.LoginOut(ServletActionContext.getRequest().getSession().getId(), getUserId());
        ActionContext.getContext().getSession().clear();
        setCode(200);
        return SUCCESS;
    }
    public String LoginTest()
    {
        return SUCCESS;
    }
    public String getMessage() {
        return super.getMessage();
    }
    public User getData() {
        return data;
    }
    public void setData(User data) {
        this.data = data;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
