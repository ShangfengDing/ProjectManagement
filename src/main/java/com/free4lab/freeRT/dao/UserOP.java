package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.User;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Administrator on 2017/4/26.
 */
public class UserOP extends AbstractDAO<User>{
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class UserOPSingletonHolder {
        static UserOP instance = new UserOP();
    }

    public static UserOP getInstance() {
        return UserOPSingletonHolder.instance;
    }
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
    public static final String PU_NAME = "FreeRT_PU";

    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }
    public User getUserById(Integer userid){
        List<User> userlist = findByProperty("userid",userid);
        if(userlist == null|| userlist.size()!=1) {
            return null;
        }else{
            return userlist.get(0);
        }
    }

    public List<User> getUserByName(String name){
        log("finding " + getClassName() + " instance with property: name, value: " + name, Level.INFO, null);
        final String queryString = "select model from " + getClassName() + " model where model.name like '"+name+"%'";
        Query query = getEntityManager().createQuery(queryString);
        List<User> userList= query.getResultList();
        this.getLogger().info(userList.size() + " users found");
        return userList;

    }

    public String addNewUser(User user){
        save(user);
        return "success";
    }
    public String updateUser(User user){
        User tmp = UserOP.getInstance().getUserById(user.getUserid());
        tmp.setName(user.getName());
        tmp.setAvatar(user.getAvatar());
        tmp.setEmail(user.getEmail());
        update(tmp);
        return "success";
    }
    public String addUsertoGroup(Integer userid){
        User user = new User();
        return "success";
    }
    public String deleteUserfromGroup(Integer userid){

        return  "success";
    }

    public List<User> findAllUser() {
        List<User> userList = findAll();
        this.getLogger().info(userList.size() + " user found");
        return userList;
    }

}
