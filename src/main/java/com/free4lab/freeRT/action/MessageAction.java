package com.free4lab.freeRT.action;

import com.free4lab.freeRT.manager.ConversationManager;
import com.free4lab.freeRT.manager.ProjectManager;
import com.free4lab.freeRT.manager.UserManager;
import com.free4lab.freeRT.model.Chat;
import com.free4lab.freeRT.model.Conversation;
import com.free4lab.freeRT.model.ConversationGroup;
import com.free4lab.freeRT.model.Project;
import com.free4lab.freeRT.utils.Constants;
import com.free4lab.utils.group.Result;
import com.opensymphony.xwork2.ActionContext;

import java.sql.Timestamp;
import java.util.*;

public class MessageAction {
    private Chat chat;
    private Integer chatId;
    private Integer projectId;
    private boolean saveResult;
    private boolean deleteResult;
    private boolean updateResult;
    private boolean commentResult;
    private boolean latestResult;
    private String type;
    private Conversation conversation;//表一
    private List<Conversation> MessageALLList,likeList,commentList;
    private List<Conversation> LatestAllConversation = new LinkedList<Conversation>();
    private List<Map> likeListtime = new LinkedList<Map>();
    private List<Map> MessageList = new LinkedList<Map>();
    private List<Map> MessageCommentList = new LinkedList<Map>();
    private List<Map> MessageDeleteList = new LinkedList<Map>();
    private String projectName;
    private String result;
    private Integer sumNum;
    private int time_signal;
    private Project pro;
    private List<Project> projectList = new ArrayList<Project>();
    ConversationManager cm = new ConversationManager();
    private Conversation newConv;
    private Timestamp currentlasttime,likelasttime;
    private String commentCount;
    public String execute() {
        return Result.SUCCESS;
    }

    public String projectNameAjax() {
        try {

            pro = ProjectManager.findProject(projectId);
            projectName = pro.getName();
            chatId = cm.getChatIdByProject(pro,Chat.PROJECT);
            return Result.SUCCESS;
        } catch (Exception e){
            return Result.ERROR;
        }
    }

    public String currentMessage() {
        int uid;
        List<Conversation> conversationList;
        ConversationGroup conversationGroup;
        List<Conversation> conversationListCurrent = new LinkedList<Conversation>();
        Project project = new Project();
        Timestamp timelast;
        try {
            uid = getSessionUID();
            conversationGroup = new ConversationGroup(uid, projectId);
            project.setId(projectId);
//            conversationList=cm.getConversationByReport(conversationGroup, project);
            conversationList=cm.getConversationByChat(conversationGroup, project, Chat.PROJECT);
            currentlasttime=conversationList.get(conversationList.size()-1).getTime();
            Collections.reverse(conversationList);
//            int size = conversationList.size();
//            int cur_msg_size = size>20 ? 20:size;
//            for(int i=0;i<cur_msg_size;i++){
//                conversationListCurrent.add(conversationList.get(size-i-1));
//            }
            int num=0;
            for(Conversation con:conversationList){
                if(con.getType().equals(Conversation.MESSAGE) && num<20){
                    conversationListCurrent.add(con);
                    num++;
                }
            }
            Collections.reverse(conversationListCurrent);
            MessageALLList = conversationListCurrent;
            if(MessageALLList.size()>0){
                timelast=MessageALLList.get(0).getTime();
            }
            else{
                timelast=new Timestamp(new Date().getTime());
            }
            for(Conversation con : MessageALLList){
                Map mapEle = new HashMap();
//                List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid));
                List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid),"type",Conversation.LIKE);
                if(temp != null&&temp.size()!=0){
                    mapEle.put("like", true);
                }else{
                    mapEle.put("like", false);
                }
                Conversation conver=ConversationManager.findIfStar("parentId",con.getId(),"user",UserManager.findUserById(uid),"type",Conversation.STAR);
                commentList=ConversationManager.findComment("parentId",con.getId(),"type",Conversation.COMMENT);
                if(conver!=null){
                    mapEle.put("star",true);
                }else{
                    mapEle.put("star",false);
                }
                mapEle.put("id",con.getId());
                mapEle.put("sourceType",con.getSourceType());
                mapEle.put("user",con.getUser());
                mapEle.put("project",con.getChatId());
                mapEle.put("context",con.getContext());
                mapEle.put("time",con.getTime());
                time_signal=getTimeShow(timelast,con.getTime());
                timelast=con.getTime();
                mapEle.put("timeinterval",time_signal);
                mapEle.put("likenumber",con.getLikenumber());
                mapEle.put("type",con.getType());
                mapEle.put("parentId",con.getParentId());
                mapEle.put("commentNum",commentList.size());
                MessageList.add(mapEle);
            }

            return Result.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return Result.ERROR;
        }

    }

    public String historyMessage() {
        int uid;
        List<Conversation> conversationList;
        ConversationGroup conversationGroup;
        Project project = new Project();
        Timestamp timelast;
        try {
            uid = getSessionUID();
            conversationGroup = new ConversationGroup(uid, projectId);
            project.setId(projectId);
            conversationList=cm.getConversationByChat(conversationGroup, project,Chat.PROJECT);
            currentlasttime=conversationList.get(conversationList.size()-1).getTime();
            MessageALLList = conversationList;
            if(MessageALLList.size()>0){
                timelast=MessageALLList.get(0).getTime();
            }
            else{
                timelast=new Timestamp(new Date().getTime());
            }
            for(Conversation con : MessageALLList){
                Map mapEle = new HashMap();
//                List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid));
                List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid),"type",Conversation.LIKE);
                if(temp != null&&temp.size()!=0){
                    mapEle.put("like", true);
                }else{
                    mapEle.put("like", false);
                }
                Conversation conver=ConversationManager.findIfStar("parentId",con.getId(),"user",UserManager.findUserById(uid),"type",Conversation.STAR);
                commentList=ConversationManager.findComment("parentId",con.getId(),"type",Conversation.COMMENT);
                if(conver!=null){
                    mapEle.put("star",true);
                }else{
                    mapEle.put("star",false);
                }
                mapEle.put("id",con.getId());
                mapEle.put("sourceType",con.getSourceType());
                mapEle.put("user",con.getUser());
                mapEle.put("project",con.getChatId());
                mapEle.put("context",con.getContext());
                mapEle.put("time",con.getTime());
                time_signal=getTimeShow(timelast,con.getTime());
                timelast=con.getTime();
                mapEle.put("timeinterval",time_signal);
                mapEle.put("likenumber",con.getLikenumber());
                mapEle.put("type",con.getType());
                mapEle.put("parentId",con.getParentId());
                mapEle.put("commentNum",commentList.size());
                MessageList.add(mapEle);
            }
            return Result.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return Result.ERROR;
        }

    }

    public String earlierMessage() {
        int uid;
        List<Conversation> allConversationList;
        List<Conversation> conversationList = new ArrayList<Conversation>();
        ConversationGroup conversationGroup;
        Project project = new Project();
        Timestamp timelast;
        System.out.println("before time");
        Timestamp earliestMsgTime = conversation.getTime();
        System.out.println("after time");
        try {
            uid = getSessionUID();
            conversationGroup = new ConversationGroup(uid, projectId);
            project.setId(projectId);
            allConversationList=cm.getConversationByChat(conversationGroup, project,Chat.PROJECT);
//            currentlasttime=conversationList.get(conversationList.size()-1).getTime();
            int count = 0;
            Collections.reverse(allConversationList);
            for (Conversation con : allConversationList) {
                if (con.getType().equals(Conversation.MESSAGE) && count < 10 && con.getTime().getTime() < earliestMsgTime.getTime()) {
                    conversationList.add(con);
                    count++;
                }
            }
            if(conversationList.size()>0){
                timelast=conversationList.get(0).getTime();
            }
            else{
                timelast=new Timestamp(new Date().getTime());
            }
            for(Conversation con : conversationList){
                    Map mapEle = new HashMap();
//                List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid));
                    List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid),"type",Conversation.LIKE);
                    if(temp != null&&temp.size()!=0){
                        mapEle.put("like", true);
                    }else{
                        mapEle.put("like", false);
                    }
                    Conversation conver=ConversationManager.findIfStar("parentId",con.getId(),"user",UserManager.findUserById(uid),"type",Conversation.STAR);
                    commentList=ConversationManager.findComment("parentId",con.getId(),"type",Conversation.COMMENT);
                    if(conver!=null){
                        mapEle.put("star",true);
                    }else{
                        mapEle.put("star",false);
                    }
                    mapEle.put("id",con.getId());
                    mapEle.put("sourceType",con.getSourceType());
                    mapEle.put("user",con.getUser());
                    mapEle.put("project",con.getChatId());
                    mapEle.put("context",con.getContext());
                    mapEle.put("time",con.getTime());
                    time_signal=getTimeShow(timelast,con.getTime());
                    timelast=con.getTime();
                    mapEle.put("timeinterval",time_signal);
                    mapEle.put("likenumber",con.getLikenumber());
                    mapEle.put("type",con.getType());
                    mapEle.put("parentId",con.getParentId());
                    mapEle.put("commentNum",commentList.size());
                    MessageList.add(mapEle);

            }
            System.out.println("before return"+MessageList.size());
            return Result.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("after error");
            return Result.ERROR;
        }
    }


    //最新消息
    public String latestMessage() {
        int uid;
        List<Conversation> conversationList;
        ConversationGroup conversationGroup;
        List<Conversation> conversationListCurrent = new LinkedList<Conversation>();
        List<Conversation> likedpeople = new LinkedList<Conversation>();
        Project project = new Project();
        Timestamp timelast;
        Timestamp timelastest= conversation.getTime();//所有message最后一条
        Timestamp timelike=likelasttime;//所有类型消息最后一条
        try {
            uid = getSessionUID();
            conversationGroup = new ConversationGroup(uid, projectId);
            project.setId(projectId);
//            conversationList=cm.getConversationByReport(conversationGroup, project);
            conversationList = cm.getConversationByChat(conversationGroup, project, Chat.PROJECT);
            pro = ProjectManager.findProject(projectId);
            projectName = pro.getName();
            if(conversationList!=null){
                for (Conversation con : conversationList) {
                    if(con.getType().equals("LIKE")){
                        if(con.getTime().getTime() > timelike.getTime()){
                            Map mapElelike = new HashMap();
                            likedpeople=cm.findLikedpeople("id",con.getParentId());
//                            System.out.println("============================="+likedpeople+"=======================");
                            mapElelike.put("peopleliked",likedpeople.get(0).getUser());
                            mapElelike.put("id", con.getId());
                            mapElelike.put("sourceType", con.getSourceType());
                            mapElelike.put("user", con.getUser());
                            mapElelike.put("project", con.getChatId());
                            mapElelike.put("context", con.getContext());
                            mapElelike.put("time", con.getTime());
                            mapElelike.put("likenumber", con.getLikenumber());
                            mapElelike.put("type", con.getType());
                            mapElelike.put("parentId", con.getParentId());
                            likeListtime.add(mapElelike);
                        }
                    }
                }

                for (Conversation con : conversationList) {
                    if (con.getTime().getTime() > timelike.getTime()) {
                        if(con.getType().equals(Conversation.DELETE)){//删除提醒
                            Conversation ocon = new Conversation();
                            ocon.setId(con.getParentId());
                            ocon = cm.findConversaion(ocon);
                            Map mapEle = new HashMap();
                            mapEle.put("oid", ocon.getId());
                            mapEle.put("id", con.getId());
                            mapEle.put("oTime", ocon.getTime());
                            mapEle.put("oUser", ocon.getUser());
                            mapEle.put("user", con.getUser());
                            MessageDeleteList.add(mapEle);
                        }
                        if(con.getType().equals(Conversation.COMMENT)){//评论提醒功能-找爹
                            Conversation pcon = new Conversation();
                            pcon.setId(con.getParentId());
                            pcon = cm.findConversaion(pcon);
                            Map mapEle = new HashMap();
                            mapEle.put("pid", pcon.getId());
                            mapEle.put("id", con.getId());
                            mapEle.put("pTime", pcon.getTime());
                            mapEle.put("pUser", pcon.getUser());
                            mapEle.put("user", con.getUser());
                            MessageCommentList.add(mapEle);
                        }
                    }
                }

//                if(conversationList.get(conversationList.size()-1).getTime().getTime()>timelike.getTime()){
                    for (Conversation con : conversationList) {
                        if(con.getTime().getTime() > timelike.getTime()){
                            LatestAllConversation.add(con);
                        }
                    }
//                }

                likelasttime=conversationList.get(conversationList.size()-1).getTime();
            }
//            System.out.println("============================="+likeListtime+"=======================");
            Collections.reverse(conversationList);
//            int size = conversationList.size();
//            int cur_msg_size = size>20 ? 20:size;
//            for(int i=0;i<cur_msg_size;i++){
//                conversationListCurrent.add(conversationList.get(size-i-1));
//            }
            int num = 0;
            for (Conversation con : conversationList) {
                if (con.getParentId() == null && num < 20) {
                    conversationListCurrent.add(con);
                    num++;
                }
            }
            Collections.reverse(conversationListCurrent);
            MessageALLList = conversationListCurrent;
            if(MessageALLList.size()>0){
                timelast=timelastest;
            }
            else{
                timelast=new Timestamp(new Date().getTime());
            }

                    for (Conversation con : MessageALLList) {
                        if (con.getTime().getTime() > timelastest.getTime()) {
                    Map mapEle = new HashMap();
//                List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid));
                    List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid), "type", Conversation.LIKE);
                    if (temp != null && temp.size() != 0) {
                        mapEle.put("like", true);
                    } else {
                        mapEle.put("like", false);
                    }
                    Conversation conver = ConversationManager.findIfStar("parentId", con.getId(), "user", UserManager.findUserById(uid), "type", Conversation.STAR);
                    commentList=ConversationManager.findComment("parentId",con.getId(),"type",Conversation.COMMENT);
                    if (conver != null) {
                        mapEle.put("star", true);
                    } else {
                        mapEle.put("star", false);
                    }
                    mapEle.put("id", con.getId());
                    mapEle.put("sourceType", con.getSourceType());
                    mapEle.put("user", con.getUser());
                    mapEle.put("project", con.getChatId());
                    mapEle.put("context", con.getContext());
                    mapEle.put("time", con.getTime());
                    time_signal = getTimeShow(timelast, con.getTime());
                    timelast = con.getTime();
                    mapEle.put("timeinterval", time_signal);
                    mapEle.put("likenumber", con.getLikenumber());
                    mapEle.put("type", con.getType());
                    mapEle.put("parentId", con.getParentId());
                    mapEle.put("commentNum",commentList.size());
                    MessageList.add(mapEle);
                }
            }
//            System.out.println(MessageList);
            latestResult = true;
            return Result.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            latestResult = false;
            return Result.ERROR;
        }
    }

    public String saveMessage() {
        try {
            int uid = getSessionUID();
            conversation.setSourceType(0);
            conversation.setUser(UserManager.findUserById(uid));
            conversation.setTime(new Timestamp(System.currentTimeMillis()));
            conversation.setLikenumber(0);
            conversation.setParentId(null);
            conversation.setType(Conversation.MESSAGE);
            cm.addConversation(conversation);
            saveResult = true;
            return Result.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            saveResult = false;
            return Result.ERROR;
        }

    }

    public String deleteMessage() {
        try {
            int uid = getSessionUID();
            Conversation temp=cm.findConversaion(conversation);
            conversation.setLikenumber(temp.getLikenumber());
            conversation.setSourceType(temp.getSourceType());
            conversation.setContext(temp.getContext());
            conversation.setTime(temp.getTime());
            conversation.setUser(temp.getUser());
            conversation.setSourceType(temp.getSourceType());
            conversation.setChatId(temp.getChatId());
            conversation.setType(Conversation.DELETED);
            conversation.setParentId(temp.getParentId());
            cm.deleteConversation(conversation);
            Conversation dele = new Conversation();
            dele.setSourceType(0);
            dele.setUser(UserManager.findUserById(uid));
            dele.setChatId(conversation.getChatId());
            dele.setContext(null);
            dele.setLikenumber(null);
            dele.setTime(new Timestamp(System.currentTimeMillis()));
            dele.setType(Conversation.DELETE);
            dele.setParentId(conversation.getId());
            cm.addConversation(dele);
            deleteResult = true;
            return Result.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            deleteResult = false;
            return Result.ERROR;
        }

    }

    public String editMessage() {
        try {
            Conversation temp=cm.findConversaion(conversation);
            conversation.setLikenumber(temp.getLikenumber());
            conversation.setSourceType(temp.getSourceType());
            conversation.setTime(temp.getTime());
            conversation.setUser(temp.getUser());
            conversation.setSourceType(temp.getSourceType());
//            conversation.setProject(temp.getProject());
            conversation.setChatId(temp.getChatId());
            conversation.setType(temp.getType());
            conversation.setParentId(temp.getParentId());
            cm.updateConversation(conversation);
            updateResult = true;
            return Result.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            updateResult = false;
            return Result.ERROR;
        }

    }

    public String likeIt(){
        try{
            int uid = getSessionUID();
            likeList = ConversationManager.findIfLike("parentId", conversation.getId(), "user", UserManager.findUserById(uid),"type",Conversation.LIKE);
            Conversation comLike=null;
            for(Conversation con:likeList){
                if(con.getType().equals(Conversation.LIKE)) {
                    comLike = con;
                    break;
                }
            }
//            if(likeList==null||likeList.size() == 0){
//                comLike = null;
//            }else{
//                comLike = likeList.get(0);
//            }
            if(comLike != null){
                setResult("already");
                sumNum = ConversationManager.deleteLike(conversation.getId());
//                ConversationManager.deleteById("parentId", conversation.getId(), "user", UserManager.findUserById(uid));
                ConversationManager.deleteit(comLike.getId());
                System.out.println("============" + "取消点赞！" + "===========");
                System.out.println("============" + sumNum + "===========");
                return Result.SUCCESS;
            }
            sumNum = ConversationManager.likeItNum(conversation.getId());

            Conversation conversationLike = new Conversation();
            conversationLike.setSourceType(0);
            conversationLike.setUser(UserManager.findUserById(uid));
            conversationLike.setChatId(conversation.getChatId());
            conversationLike.setContext(null);
            conversationLike.setLikenumber(null);
            conversationLike.setTime(new Timestamp(System.currentTimeMillis()));
            conversationLike.setType(Conversation.LIKE);
            conversationLike.setParentId(conversation.getId());

            cm.addConversation(conversationLike);
            setResult("success");
            System.out.println("============" + "点赞成功！" + "===========");
            System.out.println("============" + sumNum + "===========");
            return Result.SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    //点赞列表
    public String likeList(){
        try{
            int uid = getSessionUID();
            likeList = ConversationManager.findLikeList("parentId", conversation.getParentId(), "type",Conversation.LIKE);
            System.out.println(likeList);
            return Result.SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public String commentMessage() {
        try {
            int uid = getSessionUID();
            Conversation conversationComment = new Conversation();
            conversationComment.setSourceType(0);
            conversationComment.setUser(UserManager.findUserById(uid));
            conversationComment.setChatId(conversation.getChatId());
            conversationComment.setContext(conversation.getContext());
            conversationComment.setLikenumber(0);
            conversationComment.setTime(new Timestamp(System.currentTimeMillis()));
            conversationComment.setType(Conversation.COMMENT);
            conversationComment.setParentId(conversation.getId());

            cm.addConversation(conversationComment);
            commentResult = true;
            return Result.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            commentResult = false;
            return Result.ERROR;
        }

    }

    public Integer getSessionUID() {
        try {
            Integer id = (Integer) ActionContext.getContext().getSession().get(Constants.USER_ID);
            return id;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public int getTimeShow(Timestamp time_last,Timestamp time_current){
        try {
            long timedifference=(time_current.getTime()-time_last.getTime())/(1000*60*60);
            time_signal=timedifference>12 ? 1:0;
//            System.out.println("============" + time_signal + "===========");
            return time_signal;
        } catch (Exception e) {
            return -1;
        }
    }

    public String starMessage(){
        int uid=getSessionUID();
        Conversation conver=ConversationManager.findIfStar("parentId",conversation.getId(),"user",UserManager.findUserById(uid),"type",Conversation.STAR);
        if(conver==null){
            Conversation conversationLike = new Conversation();
            conversationLike.setSourceType(0);
            conversationLike.setUser(UserManager.findUserById(uid));
            conversationLike.setChatId(conversation.getChatId());
            conversationLike.setContext(null);
            conversationLike.setLikenumber(null);
            conversationLike.setTime(new Timestamp(System.currentTimeMillis()));
            conversationLike.setType(Conversation.STAR);
            conversationLike.setParentId(conversation.getId());
            cm.addConversation(conversationLike);
            result="success";
        }else{
            result="cancel";
//            ConversationManager.deleteById("parentId", conversation.getId(), "user", UserManager.findUserById(uid));
            ConversationManager.deleteit(conver.getId());

        }
        return Result.SUCCESS;

    }

    public String showComment(){
        int uid=getSessionUID();
        commentList=ConversationManager.findComment("parentId",conversation.getId(),"type",Conversation.COMMENT);
        for(Conversation con : commentList){
            Map mapEle = new HashMap();
//                List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid));
            List<Conversation> temp = ConversationManager.findIfLike("parentId", con.getId(), "user", UserManager.findUserById(uid),"type",Conversation.LIKE);
            if(temp != null&&temp.size()!=0){
                mapEle.put("like", true);
            }else{
                mapEle.put("like", false);
            }
            Conversation conver=ConversationManager.findIfStar("parentId",con.getId(),"user",UserManager.findUserById(uid),"type",Conversation.STAR);
            if(conver!=null){
                mapEle.put("star",true);
            }else{
                mapEle.put("star",false);
            }
            mapEle.put("id",con.getId());
            mapEle.put("sourceType",con.getSourceType());
            mapEle.put("user",con.getUser());
            mapEle.put("project",con.getChatId());
            mapEle.put("context",con.getContext());
            mapEle.put("time",con.getTime());
            mapEle.put("timeinterval",time_signal);
            mapEle.put("likenumber",con.getLikenumber());
            mapEle.put("type",con.getType());
            mapEle.put("parentId",con.getParentId());
            MessageList.add(mapEle);
        }
        result="success";
        return "success";
    }

    public String countComment(){
        commentList=ConversationManager.findComment("parentId",conversation.getId(),"type",Conversation.COMMENT);
        commentCount = commentList.size()+"";
        return Result.SUCCESS;
    }

//    public String findNewByProject(){
//        int uid = getSessionUID();
//        projectList = ProjectManager.findProjectByUser(uid);
//        newConv = ConversationManager.findNewByProject(projectList);
//        return "success";
//    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Conversation getConversation() { return conversation; }

    public void setConversation(Conversation conversation) { this.conversation = conversation; }

    public List<Conversation> getMessageALLList() {
        return MessageALLList;
    }

    public void setMessageALLList(List<Conversation> messageALLList) {
        MessageALLList = messageALLList;
    }

    public List<Conversation> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<Conversation> likeList) {
        this.likeList = likeList;
    }

    public List<Map> getMessageList() {
        return MessageList;
    }

    public void setMessageList(List<Map> messageList) {
        MessageList = messageList;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public boolean isSaveResult() {
        return saveResult;
    }

    public void setSaveResult(boolean saveResult) {
        this.saveResult = saveResult;
    }

    public boolean isDeleteResult() { return deleteResult; }

    public void setDeleteResult(boolean deleteResult) { this.deleteResult = deleteResult; }

    public boolean isUpdateResult() { return updateResult; }

    public void setUpdateResult(boolean updateResult) { this.updateResult = updateResult; }

    public boolean isCommentResult() {
        return commentResult;
    }

    public void setCommentResult(boolean commentResult) {
        this.commentResult = commentResult;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getSumNum() {
        return sumNum;
    }

    public void setSumNum(Integer sumNum) {
        this.sumNum = sumNum;
    }

    public int getTime_signal() {
        return time_signal;
    }

    public void setTime_signal(int time_signal) {
        this.time_signal = time_signal;
    }

    public List<Conversation> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Conversation> commentList) {
        this.commentList = commentList;
    }

    public boolean isLatestResult() {
        return latestResult;
    }

    public void setLatestResult(boolean latestResult) {
        this.latestResult = latestResult;
    }

    public Timestamp getCurrentlasttime() {
        return currentlasttime;
    }

    public void setCurrentlasttime(Timestamp currentlasttime) {
        this.currentlasttime = currentlasttime;
    }

    public Timestamp getLikelasttime() {
        return likelasttime;
    }

    public void setLikelasttime(Timestamp likelasttime) {
        this.likelasttime = likelasttime;
    }

    public List<Map> getLikeListtime() {
        return likeListtime;
    }

    public void setLikeListtime(List<Map> likeListtime) {
        this.likeListtime = likeListtime;
    }

    public List<Conversation> getLatestAllConversation() {
        return LatestAllConversation;
    }

    public void setLatestAllConversation(List<Conversation> latestAllConversation) {
        LatestAllConversation = latestAllConversation;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public List<Map> getMessageCommentList() {
        return MessageCommentList;
    }

    public void setMessageCommentList(List<Map> messageCommentList) {
        MessageCommentList = messageCommentList;
    }

    public List<Map> getMessageDeleteList() {
        return MessageDeleteList;
    }

    public void setMessageDeleteList(List<Map> messageDeleteList) {
        MessageDeleteList = messageDeleteList;
    }
}
