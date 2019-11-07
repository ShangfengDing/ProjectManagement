package com.free4lab.freeRT.action;

import com.free4lab.freeRT.manager.ChatManager;
import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.model.Chat;
import com.free4lab.freeRT.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ChatAction extends BaseAction{
    private Chat chat;
    private String type;
    private List<Chat> chatListPro;
    private List<Chat> chatListCrt;
    private List<Chat> chatListSelf;
    private boolean judge=false;//false即当前用户不是此项目管理员
    private Boolean auth = false;
    private int id;
    private Project pro;
    private List<Project> projectList;
    private List<String> projectIdList = new ArrayList<String>();
    ChatManager cm = new ChatManager();
    private Integer uid;
//    public String execute(){
//        uid = getSessionUID();
//        prolist = ProjectManager.findProjectByUser(uid);
//        return Result.SUCCESS;
//    }

    public String viewChatProject(){
        int uid = getSessionUID();
        projectList = ProjectManager.findProjectByUser(uid);
        for(Project pro : projectList){
            projectIdList.add(pro.getId()+"");
        }
        chatListPro = cm.findChatsById(projectIdList,Chat.PROJECT);
//            for(Project pro : projectList)
//                Chat proChat = new Chat();
//                proChat.setId(pro.getId());
//                proChat.setChatType(Chat.PROJECT);
//                cm.addChat(proChat);
//            }
        return SUCCESS;
    }

    public String viewChatCreat(){//用户自建表
        return SUCCESS;
    }

    public String viewChatSelf(){
        this.chat.setChatType(Chat.SELF);
        this.chat.setCid(id);
        chatListSelf = cm.getChat(this.chat);
        return SUCCESS;
    }

    public void creatChat(){}

    public void creatSelf(){
        this.chat.setCid(id);
        this.chat.setChatType(Chat.SELF);
        cm.addChat(this.chat);
    }

    public List<Chat> getChatListPro() {
        return chatListPro;
    }

    public void setChatListPro(List<Chat> chatListPro) {
        this.chatListPro = chatListPro;
    }

    public List<Chat> getChatListCrt() {
        return chatListCrt;
    }

    public void setChatListCrt(List<Chat> chatListCrt) {
        this.chatListCrt = chatListCrt;
    }

    public List<Chat> getChatListSelf() {
        return chatListSelf;
    }

    public void setChatListSelf(List<Chat> chatListSelf) {
        this.chatListSelf = chatListSelf;
    }
}
