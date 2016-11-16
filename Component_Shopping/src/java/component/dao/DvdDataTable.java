/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.dao;

import component.model.DvdData;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Stateless
public class DvdDataTable implements DvdDataTableLocal {

    @PersistenceContext(unitName = "Component_ShoppingPU")
    private EntityManager em;

    @Override
    public List<DvdData> getAllDvd() {
//        EntityManager em = emf.createEntityManager();   
        return em.createNamedQuery("DvdData.findAll").getResultList();
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
    }
}
