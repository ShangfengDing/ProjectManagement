package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.Chat;
import com.free4lab.freeRT.model.Conversation;
import com.free4lab.freeRT.model.Project;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import java.util.List;
import java.util.logging.Level;

public class ConversationDAO extends AbstractDAO<Conversation> {
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class ConversationDAOSingletonHolder {
        static ConversationDAO instance = new ConversationDAO();
    }

    public static ConversationDAO getInstance() {
        return ConversationDAOSingletonHolder.instance;
    }
    @Override
    public Class<Conversation> getEntityClass() {
        return Conversation.class;
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

    public String addNewConversation(Conversation conv){
        save(conv);
        return "success";
    }

    public String deleteOldConversation(Conversation conv){
//        deleteByPrimaryKey(conv.getId());
        update(conv);
        return "success";
    }

    public  Conversation find(Conversation conv){
        return findById(conv.getId());
    }

    public String updateOldConversation(Conversation conv){
        update(conv);
        return "success";
    }

    public List<Conversation> findConversationByProject(Project project) {
        List<Conversation> rst;
        try {
            rst = super.findByProperty("project", project);
            this.getLogger().info(rst.size()+" conversation found with reportId:"+project.getId());
            return rst;
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
            return null;
        }
    }

    public List<Conversation> findConversationByChat(Chat chat) {
        List<Conversation> rst;
        try {
            rst = super.findByProperty("chatId", chat.getChatId());
            this.getLogger().info(rst.size()+" conversation found with reportId:"+chat.getChatId());
            return rst;
        } catch (Exception e) {
            this.log(e.getMessage(), Level.SEVERE, e);
            return null;
        }
    }

//    public Conversation findNewByProject(List<Project> projectList){
//        List<Conversation> convList;
//        Conversation conv=null;
//        try {
//            final String queryString = "SELECT model FROM Conversation model WHERE model.project.id =:proId ORDER BY model.time DESC";
//            Query query = getEntityManager().createQuery(queryString);
//            for(Project pro:projectList){
//                query.setParameter("proId", pro.getId());
//                query.setMaxResults(1);
//                convList=(query.getResultList());
//                if(conv!=null && convList.get(0).getTime().after(conv.getTime())){
//                    conv=convList.get(0);
//                }
////                else if(conv != null && convList.get(0).getTime().before(conv.getTime())){
////                }
//                else if(conv == null && convList != null)
//                    conv=convList.get(0);
////                else if(conv == null && convList == null)
//            }
//        } catch (Exception e) {
//            this.log(e.getMessage(), Level.SEVERE, e);
//        }
//        return conv;
//    }

}
