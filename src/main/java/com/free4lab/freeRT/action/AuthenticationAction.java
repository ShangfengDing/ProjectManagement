package com.free4lab.freeRT.action;

import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.model.User;

import java.util.List;



public class AuthenticationAction extends BaseAction{


    private Integer uid;//当前用户的user_id
    private Integer pid;//当前项目的项目id
    private boolean judge=false;//false即当前用户不是此项目管理员

    public String execute() {
        uid = getSessionUID();
        judge=ProjectManager.isAdmin(pid,uid);
        return "success";
    }

    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public boolean isJudge() {
        return judge;
    }
    public void setJudge(boolean judge) {
        this.judge = judge;
    }
}
