package com.free4lab.freeRT.action;


import com.free4lab.freeRT.dao.ProjectDAO;
import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.model.Project;
import java.sql.Timestamp;

import static com.free4lab.utils.group.Result.SUCCESS;


public class HomeAction {

    public String execute() throws Exception {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        System.out.println("success");
        return SUCCESS;
    }
}
