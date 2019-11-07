package com.free4lab.freeRT.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.free4lab.freeRT.model.User;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.freeRT.utils.HttpUtil;
import com.free4lab.freeRT.utils.JSONUtil;
import com.free4lab.freeRT.utils.UrlUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class GroupOP {
    private static final Logger logger = Logger.getLogger(User.class);

    public List<User> getgroupuser(Integer groupId) {
        List<User> userList = new ArrayList<User>();
        if (groupId == null || groupId.equals("")) {
            return null;
        }
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("writeroles", "3");
            String url = UrlUtil.genGetUrl(Constants.API_GROUP + "/"
                    + groupId, params);
            logger.info("Group url=" + url);
            JSONObject response = HttpUtil.get(url);
            logger.info("group response=" + response);
            if (null != response) {
                String message = JSONUtil.getMessage(response);
                Integer code = JSONUtil.getCode(response);
                logger.info("Group message = " + message + " code = " + code);
                if (code.equals(1000)) {
                    JSONArray jsonArray = JSONUtil.getContent(response).getJSONArray("members");
                    for (int i=0; i<jsonArray.length(); i++) {
                        User user =  new User();
                        JSONObject g = jsonArray.getJSONObject(i);
                        user.setName(g.getString("name"));
                        user.setEmail(g.getString("email"));
                        user.setUserid(g.getInt("userId"));
                        userList.add(user);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return userList;
    }

}
