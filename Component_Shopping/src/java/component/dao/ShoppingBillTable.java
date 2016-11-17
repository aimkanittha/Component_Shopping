/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.dao;

import component.model.ShoppingBill;
import component.model.ShoppingBillDetail;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author wachirapong
 */
@Stateless
public class ShoppingBillTable implements ShoppingBillTableLocal {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Component_ShoppingPU");

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
    public void removeItem(String itemId) {
        EntityManager em = emf.createEntityManager();
        em.remove(em.createNamedQuery("ShoppingBillDetail.findByDvdDataId").setParameter("ShoppingBillDetail_dvdItem", itemId).getResultList());
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<ShoppingBillDetail> findAll() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("ShoppingBillDetail.findAll").getResultList();
    }

}
