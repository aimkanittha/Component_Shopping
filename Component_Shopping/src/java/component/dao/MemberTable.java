/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wachirapong
 */
@Stateless
public class MemberTable{

    @PersistenceContext(unitName = "Component_ShoppingPU")
    private EntityManager em;
    
    public int getCount() {
        return em.createNamedQuery("MemberShop.findAll").getResultList().size();
    }
    
    @Inject
    public MemberTable() {

    }

    public void persist(Object object) {
        em.persist(object);
    }
}
