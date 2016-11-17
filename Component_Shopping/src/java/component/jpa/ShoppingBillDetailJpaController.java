/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.jpa;

import component.jpa.exceptions.NonexistentEntityException;
import component.jpa.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import component.model.ShoppingBill;
import component.model.DvdData;
import component.model.ShoppingBillDetail;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wachirapong
 */
public class ShoppingBillDetailJpaController implements Serializable {

    public ShoppingBillDetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ShoppingBillDetail shoppingBillDetail) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ShoppingBill shoppingBillDetailbill = shoppingBillDetail.getShoppingBillDetailbill();
            if (shoppingBillDetailbill != null) {
                shoppingBillDetailbill = em.getReference(shoppingBillDetailbill.getClass(), shoppingBillDetailbill.getShoppingBillid());
                shoppingBillDetail.setShoppingBillDetailbill(shoppingBillDetailbill);
            }
            DvdData shoppingBillDetaildvdItem = shoppingBillDetail.getShoppingBillDetaildvdItem();
            if (shoppingBillDetaildvdItem != null) {
                shoppingBillDetaildvdItem = em.getReference(shoppingBillDetaildvdItem.getClass(), shoppingBillDetaildvdItem.getDvdDataid());
                shoppingBillDetail.setShoppingBillDetaildvdItem(shoppingBillDetaildvdItem);
            }
            em.persist(shoppingBillDetail);
            if (shoppingBillDetailbill != null) {
                shoppingBillDetailbill.getShoppingBillDetailList().add(shoppingBillDetail);
                shoppingBillDetailbill = em.merge(shoppingBillDetailbill);
            }
            if (shoppingBillDetaildvdItem != null) {
                shoppingBillDetaildvdItem.getShoppingBillDetailList().add(shoppingBillDetail);
                shoppingBillDetaildvdItem = em.merge(shoppingBillDetaildvdItem);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findShoppingBillDetail(shoppingBillDetail.getShoppingBillDetailseq()) != null) {
                throw new PreexistingEntityException("ShoppingBillDetail " + shoppingBillDetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ShoppingBillDetail shoppingBillDetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ShoppingBillDetail persistentShoppingBillDetail = em.find(ShoppingBillDetail.class, shoppingBillDetail.getShoppingBillDetailseq());
            ShoppingBill shoppingBillDetailbillOld = persistentShoppingBillDetail.getShoppingBillDetailbill();
            ShoppingBill shoppingBillDetailbillNew = shoppingBillDetail.getShoppingBillDetailbill();
            DvdData shoppingBillDetaildvdItemOld = persistentShoppingBillDetail.getShoppingBillDetaildvdItem();
            DvdData shoppingBillDetaildvdItemNew = shoppingBillDetail.getShoppingBillDetaildvdItem();
            if (shoppingBillDetailbillNew != null) {
                shoppingBillDetailbillNew = em.getReference(shoppingBillDetailbillNew.getClass(), shoppingBillDetailbillNew.getShoppingBillid());
                shoppingBillDetail.setShoppingBillDetailbill(shoppingBillDetailbillNew);
            }
            if (shoppingBillDetaildvdItemNew != null) {
                shoppingBillDetaildvdItemNew = em.getReference(shoppingBillDetaildvdItemNew.getClass(), shoppingBillDetaildvdItemNew.getDvdDataid());
                shoppingBillDetail.setShoppingBillDetaildvdItem(shoppingBillDetaildvdItemNew);
            }
            shoppingBillDetail = em.merge(shoppingBillDetail);
            if (shoppingBillDetailbillOld != null && !shoppingBillDetailbillOld.equals(shoppingBillDetailbillNew)) {
                shoppingBillDetailbillOld.getShoppingBillDetailList().remove(shoppingBillDetail);
                shoppingBillDetailbillOld = em.merge(shoppingBillDetailbillOld);
            }
            if (shoppingBillDetailbillNew != null && !shoppingBillDetailbillNew.equals(shoppingBillDetailbillOld)) {
                shoppingBillDetailbillNew.getShoppingBillDetailList().add(shoppingBillDetail);
                shoppingBillDetailbillNew = em.merge(shoppingBillDetailbillNew);
            }
            if (shoppingBillDetaildvdItemOld != null && !shoppingBillDetaildvdItemOld.equals(shoppingBillDetaildvdItemNew)) {
                shoppingBillDetaildvdItemOld.getShoppingBillDetailList().remove(shoppingBillDetail);
                shoppingBillDetaildvdItemOld = em.merge(shoppingBillDetaildvdItemOld);
            }
            if (shoppingBillDetaildvdItemNew != null && !shoppingBillDetaildvdItemNew.equals(shoppingBillDetaildvdItemOld)) {
                shoppingBillDetaildvdItemNew.getShoppingBillDetailList().add(shoppingBillDetail);
                shoppingBillDetaildvdItemNew = em.merge(shoppingBillDetaildvdItemNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = shoppingBillDetail.getShoppingBillDetailseq();
                if (findShoppingBillDetail(id) == null) {
                    throw new NonexistentEntityException("The shoppingBillDetail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ShoppingBillDetail shoppingBillDetail;
            try {
                shoppingBillDetail = em.getReference(ShoppingBillDetail.class, id);
                shoppingBillDetail.getShoppingBillDetailseq();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The shoppingBillDetail with id " + id + " no longer exists.", enfe);
            }
            ShoppingBill shoppingBillDetailbill = shoppingBillDetail.getShoppingBillDetailbill();
            if (shoppingBillDetailbill != null) {
                shoppingBillDetailbill.getShoppingBillDetailList().remove(shoppingBillDetail);
                shoppingBillDetailbill = em.merge(shoppingBillDetailbill);
            }
            DvdData shoppingBillDetaildvdItem = shoppingBillDetail.getShoppingBillDetaildvdItem();
            if (shoppingBillDetaildvdItem != null) {
                shoppingBillDetaildvdItem.getShoppingBillDetailList().remove(shoppingBillDetail);
                shoppingBillDetaildvdItem = em.merge(shoppingBillDetaildvdItem);
            }
            em.remove(shoppingBillDetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ShoppingBillDetail> findShoppingBillDetailEntities() {
        return findShoppingBillDetailEntities(true, -1, -1);
    }

    public List<ShoppingBillDetail> findShoppingBillDetailEntities(int maxResults, int firstResult) {
        return findShoppingBillDetailEntities(false, maxResults, firstResult);
    }

    private List<ShoppingBillDetail> findShoppingBillDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ShoppingBillDetail.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ShoppingBillDetail findShoppingBillDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ShoppingBillDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getShoppingBillDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ShoppingBillDetail> rt = cq.from(ShoppingBillDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
