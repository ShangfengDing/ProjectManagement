package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.UserOP;
import com.free4lab.freeRT.model.User;
import org.apache.log4j.Logger;
import java.util.List;


public class UserManager {
    private static final Logger LOGGER = Logger.getLogger(UserManager.class);

    public UserManager(){}
    private static UserManager instance = new UserManager();
    public static UserManager getInstance() { return instance;}
    private static UserOP getDAOInstance() { return UserOP.getInstance(); }

    public static String addUser(User user){
        Integer userid = user.getUserid();
        User tmp = getDAOInstance().getUserById(userid);
        if(tmp == null){
        return getInstance().addNewUser(user);
        }else{
            return getDAOInstance().updateUser(user);
        }

    }
    public static User findUserById(Integer uid){
        return UserOP.getInstance().getUserById(uid);
    }
    public static User findUserByPrimaryId(Integer id) {
        return UserOP.getInstance().findByPrimaryKey(id);
    }
    public static List<User> findUserByName(String name){
        return UserOP.getInstance().getUserByName(name);
    }

    public String addNewUser(User user){
        if(user.getUserid() != null || user.getUserid() != 0){
            return getDAOInstance().addNewUser(user);
        }else{
            return null;
        }
    }
    public static String updateUser(User user){
        if(user.getUserid() != null || user.getUserid() != 0){
            return getDAOInstance().updateUser(user);
        }else{
            return null;
        }
    }

    public static List<User> findAllUser(){
        return getDAOInstance().findAllUser();
    }

}
