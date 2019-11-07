package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.Chat;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import java.util.ArrayList;
import java.util.List;

public class ChatDAO  extends AbstractDAO<Chat> {
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class ChatDAOSingletonHolder {
        static ChatDAO instance = new ChatDAO();
    }
    public static ChatDAO getInstance(){return ChatDAOSingletonHolder.instance;}
    @Override
    public Class<Chat> getEntityClass() {
        return Chat.class;
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

    public String addNewChat(Chat chat){
        save(chat);
        return "success";
    }

    public String updateChat(Chat chat){
        update(chat);
        return "success";
    }

    public String deleteChat(Chat chat){
        deleteByPrimaryKey(chat.getChatId());
        return "success";
    }

    public List<Chat> findChat(Chat chat){
        return findByProperty2("chat_type",chat.getChatType(),"id",chat.getCid());
    }

    public List<Chat> findChatsById(List<String> idList,String type){
        List<Chat> chats = new ArrayList<Chat>();
        Chat newChat = new Chat();
        for(String id : idList){
            Integer idToInteger = Integer.valueOf(id);
            newChat = (findByProperty2("chatType",type+"","cid",idToInteger)).get(0);
            chats.add(newChat);
            this.getLogger().info("Chats " + newChat.getName() + " found with chatId:"+newChat.getChatId());
        }
        this.getLogger().info(chats.size()+" chats found");
        return chats;
    }

    public Chat findChatById(String id, String type){
        Integer idToInteger = Integer.valueOf(id);
        Chat newChat = new Chat();
        newChat = (findByProperty2("chatType",type+"","cid",idToInteger)).get(0);
        this.getLogger().info("Chats " + newChat.getName() + " found with chatId:"+newChat.getChatId());
        return newChat;
    }
}

