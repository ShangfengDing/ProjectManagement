package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.ChatDAO;
import com.free4lab.freeRT.dao.ConversationDAO;
import com.free4lab.freeRT.dao.ConversationGroupDAO;
import com.free4lab.freeRT.model.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationManager {
    private static final Logger LOGGER = Logger.getLogger(ConversationManager.class);

    public ConversationManager(){}
    private static ConversationManager instance = new ConversationManager();
    public static ConversationManager getInstance() { return instance;}
    private static ConversationDAO getConversationDAOInstance() { return ConversationDAO.getInstance(); }
    private static ConversationGroupDAO getGroupDAOInstance() { return ConversationGroupDAO.getInstance(); }
    private static ChatDAO getChatDAOInstance() { return ChatDAO.getInstance(); }

    public String addConversation(Conversation conv){
        return getConversationDAOInstance().addNewConversation(conv);
    }

    public String deleteConversation(Conversation conv){
        return getConversationDAOInstance().deleteOldConversation(conv);
    }

    public Conversation findConversaion(Conversation conv){
        return getConversationDAOInstance().find(conv);
    }

    public String updateConversation(Conversation conv){
        return getConversationDAOInstance().updateOldConversation(conv);
    }

    public List<Conversation> getConversationByReport(ConversationGroup cg, Project project){
        getGroupDAOInstance().addConversationGroup(cg);
        return getConversationDAOInstance().findConversationByProject(project);
    }

    public List<Conversation> getConversationByChat(ConversationGroup cg, Project project, String type){
        getGroupDAOInstance().addConversationGroup(cg);
        Chat newChat = getChatDAOInstance().findChatById(project.getId()+"",type);
        return getConversationDAOInstance().findConversationByChat(newChat);
    }

    public Integer getChatIdByProject(Project project, String type){
        Chat newChat = getChatDAOInstance().findChatById(project.getId()+"",type);
        return newChat.getChatId();
    }

    /*被点赞人*/
    public static List<Conversation> findLikedpeople(String name, int id){
        return ConversationDAO.getInstance().findByProperty(name,id);
    }

    /*是否点过赞*/
    public static List<Conversation> findIfLike(String name, int id, String name2, User id2,String name3,String id3){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(name,id);
        map.put(name2,id2);
        map.put(name3,id3);
        return ConversationDAO.getInstance().findByProperty(map,null,null,null,false);
    }

    /* 返回取消点赞后的点赞数 */
    public static Integer deleteLike(Integer id){
        Conversation com = ConversationDAO.getInstance().findById(id);
        com.setLikenumber(com.getLikenumber() - 1);
        ConversationDAO.getInstance().update(com);
        return com.getLikenumber();
    }

    /* 删除点赞 */
    public static void deleteById(String str1, int id1, String str2, User id2){
        List<Conversation> conversationList = ConversationDAO.getInstance().findByProperty2(str1, id1, str2, id2);
        ConversationDAO.getInstance().deleteByPrimaryKey(conversationList.get(0).getId());
    }

    /* 返回点赞后的点赞数 */
    public static Integer likeItNum(Integer id){
        Conversation com = ConversationDAO.getInstance().findById(id);
        com.setLikenumber(com.getLikenumber() + 1);
        ConversationDAO.getInstance().update(com);
        return com.getLikenumber();
    }

    /* 返回点赞列表 */
    public static List<Conversation> findLikeList(String name, int id, String name2, String id2){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(name,id);
        map.put(name2,id2);
        return ConversationDAO.getInstance().findByProperty(map,null,null,null,false);
    }

    public static Conversation findIfStar(String name, int id,String name2,User id2,String name3,String id3){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(name,id);
        map.put(name2,id2);
        map.put(name3,id3);
        List<Conversation> conversationList = ConversationDAO.getInstance().findByProperty(map,null,null,null,false);
        if(conversationList.size()>0) {
            return conversationList.get(0);
        }
        return null;
    }

    public static List<Conversation> findComment(String name, int id,String name3,String id3){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(name,id);
        map.put(name3,id3);
        List<Conversation> conversationList = ConversationDAO.getInstance().findByProperty(map,null,null,null,false);
        return conversationList;
    }

    public static void deleteit(int id){
        ConversationDAO.getInstance().deleteByPrimaryKey(id);
    }

//    public static Conversation findNewByProject(List<Project> projectList){
//        return ConversationDAO.getInstance().findNewByProject(projectList);
//    }
}
