package com.free4lab.freeRT.dao;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;
import com.free4lab.freeRT.model.Monitor;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import com.free4lab.utils.sql.AbstractDAO;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.logging.Level;


public class MonitorDao extends AbstractDAO<Monitor> {
//    public static void main(String[] args) {
//        Monitor m =new Monitor();
//        m.setId(6666);
//        m.setContent("ybcyulycw");
//        MonitorDao md = new MonitorDao();
////        md.saveCarddata(m);
//        md.save(m);
//    }
    public String getClassName() {
        return getEntityClass().getName();
    }

    @Override
    public Class<Monitor> getEntityClass() {
        return Monitor.class;
    }
    public static final String PU_NAME = "Monitor";

    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }

    public Integer saveCarddata(Monitor carddataEntity){
        EntityManager em = getEntityManager();
        Integer id;
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
            log("A transaction is still active before another begin, we have to roll back it!", Level.SEVERE, null);
        }

        em.getTransaction().begin();

        log("saving " + getClassName() + " instance", Level.INFO, null);
        try {
            Session session = (Session) em.getDelegate();
            session.setFlushMode(FlushMode.MANUAL);
            Serializable result=session.save(carddataEntity);
            id=carddataEntity.getId();
            session.flush();
            session.clear();
            log("save successful", Level.INFO, null);
            em.getTransaction().commit();
            return id;
        } catch (RuntimeException re) {
            log("save failed", Level.SEVERE, re);
            em.getTransaction().rollback();
            throw re;
        }
    }
}