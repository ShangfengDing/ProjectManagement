package com.free4lab.freeRT.dao;

import com.free4lab.freeRT.model.ConversationGroup;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

import java.util.List;

public class  ConversationGroupDAO extends AbstractDAO<ConversationGroup> {
    public String getClassName() {
        return getEntityClass().getName();
    }

    private static class ConversationGroupDAOSingletonHolder {
        static ConversationGroupDAO instance = new ConversationGroupDAO();
    }

    public static ConversationGroupDAO getInstance() {
        return ConversationGroupDAOSingletonHolder.instance;
    }
    @Override
    public Class<ConversationGroup> getEntityClass() {
        return ConversationGroup.class;
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

    public List<ConversationGroup> getConvGByUid(int Uid) {
        List<ConversationGroup> cglist = findByProperty("userId", Uid);
        if (cglist == null) {
            return null;
        } else {
            return cglist;
        }
    }

    public void addConversationGroup(ConversationGroup CG){
        List<ConversationGroup> cglist = findByProperty2("userId",CG.getUserId(),"chatId",CG.getChatId());
        if(cglist.isEmpty()){
            save(CG);
            System.out.println("cg saved");
        }
    }


}
