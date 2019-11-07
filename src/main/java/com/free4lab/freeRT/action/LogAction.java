package com.free4lab.freeRT.action;

import com.free4lab.freeRT.manager.LogManager;
import com.free4lab.freeRT.model.Log;
import com.free4lab.utils.group.Result;

import java.util.List;

public class LogAction {

    private List<Log> logList;
    private String logType;
    private Integer pid;
    private int page;
    private int pageSize;

    public String execute() {
        logList = LogManager.findLogByPid(pid,logType,page,pageSize);
        return Result.SUCCESS;
    }

    public String findLogByUid(){
        logList = LogManager.findLogByUid(logType);
        return Result.SUCCESS;
    }

    public String deleteLog(){
        LogManager.deleteLog();
        return Result.SUCCESS;
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
