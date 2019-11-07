package com.free4lab.freeRT.manager;

import com.free4lab.freeRT.dao.ChatDAO;
import com.free4lab.freeRT.model.Chat;
import org.apache.log4j.Logger;

import java.util.List;

public class ChatManager {
    private static final Logger LOGGER = Logger.getLogger(ChatManager.class);

    public ChatManager() {}
    private static ChatManager instance = new ChatManager();
    public static ChatManager getInstance() { return instance;}
    private static ChatDAO getChatDAOInstance() { return ChatDAO.getInstance(); }

    public String addChat(Chat chat){
        if(ChatDAO.getInstance().findChat(chat) == null){
            return getChatDAOInstance().addNewChat(chat);
        }
        else
            return "error";
    }

    public String deleteChat(Chat chat){
        return getChatDAOInstance().deleteChat(chat);
    }

    public String updateChat(Chat chat){
        return getChatDAOInstance().updateChat(chat);
    }

    public List<Chat> getChat(Chat chat){
        return getChatDAOInstance().findChat(chat);
    }

    public List<Chat> findChatsById(List<String> idList,String type){
        return getChatDAOInstance().findChatsById(idList,type);
    }

}
