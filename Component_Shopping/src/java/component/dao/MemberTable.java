/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.dao;

import component.model.MemberShop;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author wachirapong
 */
@Stateless
public class MemberTable implements MemberTableLocal{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Component_ShoppingPU");
//    @PersistenceContext(unitName="Component_ShoppingPU")
//    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

//    @Override
    public int getCount() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("MemberShop.findAll").getResultList().size();
    }
    
    @Inject
    public MemberTable() {

    }
    

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public MemberShop getMemberByUsername(String Username) {
        EntityManager em = emf.createEntityManager();
        List<MemberShop>list = em.createNamedQuery("MemberShop.findByMemberusername").setParameter("memberusername", Username).getResultList();
        if(list.size() == 0)
            return null;
        TypedQuery<MemberShop> query = em.createNamedQuery("MemberShop.findByMemberusername", MemberShop.class);
        query.setParameter("memberusername", Username);
        return query.getSingleResult();
    }

   
    
}
