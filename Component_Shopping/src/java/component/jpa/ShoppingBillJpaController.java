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
import component.model.MemberShop;
import component.model.ShoppingBill;
import component.model.ShoppingBillDetail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wachirapong
 */
public class ShoppingBillJpaController implements Serializable {

    public ShoppingBillJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ShoppingBill shoppingBill) throws PreexistingEntityException, Exception {
        if (shoppingBill.getShoppingBillDetailList() == null) {
            shoppingBill.setShoppingBillDetailList(new ArrayList<ShoppingBillDetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MemberShop shoppingBillmember = shoppingBill.getShoppingBillmember();
            if (shoppingBillmember != null) {
                shoppingBillmember = em.getReference(shoppingBillmember.getClass(), shoppingBillmember.getMemberid());
                shoppingBill.setShoppingBillmember(shoppingBillmember);
            }
            List<ShoppingBillDetail> attachedShoppingBillDetailList = new ArrayList<ShoppingBillDetail>();
            for (ShoppingBillDetail shoppingBillDetailListShoppingBillDetailToAttach : shoppingBill.getShoppingBillDetailList()) {
                shoppingBillDetailListShoppingBillDetailToAttach = em.getReference(shoppingBillDetailListShoppingBillDetailToAttach.getClass(), shoppingBillDetailListShoppingBillDetailToAttach.getShoppingBillDetailseq());
                attachedShoppingBillDetailList.add(shoppingBillDetailListShoppingBillDetailToAttach);
            }
            shoppingBill.setShoppingBillDetailList(attachedShoppingBillDetailList);
            em.persist(shoppingBill);
            if (shoppingBillmember != null) {
                shoppingBillmember.getShoppingBillList().add(shoppingBill);
                shoppingBillmember = em.merge(shoppingBillmember);
            }
            for (ShoppingBillDetail shoppingBillDetailListShoppingBillDetail : shoppingBill.getShoppingBillDetailList()) {
                ShoppingBill oldShoppingBillDetailbillOfShoppingBillDetailListShoppingBillDetail = shoppingBillDetailListShoppingBillDetail.getShoppingBillDetailbill();
                shoppingBillDetailListShoppingBillDetail.setShoppingBillDetailbill(shoppingBill);
                shoppingBillDetailListShoppingBillDetail = em.merge(shoppingBillDetailListShoppingBillDetail);
                if (oldShoppingBillDetailbillOfShoppingBillDetailListShoppingBillDetail != null) {
                    oldShoppingBillDetailbillOfShoppingBillDetailListShoppingBillDetail.getShoppingBillDetailList().remove(shoppingBillDetailListShoppingBillDetail);
                    oldShoppingBillDetailbillOfShoppingBillDetailListShoppingBillDetail = em.merge(oldShoppingBillDetailbillOfShoppingBillDetailListShoppingBillDetail);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findShoppingBill(shoppingBill.getShoppingBillid()) != null) {
                throw new PreexistingEntityException("ShoppingBill " + shoppingBill + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ShoppingBill shoppingBill) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ShoppingBill persistentShoppingBill = em.find(ShoppingBill.class, shoppingBill.getShoppingBillid());
            MemberShop shoppingBillmemberOld = persistentShoppingBill.getShoppingBillmember();
            MemberShop shoppingBillmemberNew = shoppingBill.getShoppingBillmember();
            List<ShoppingBillDetail> shoppingBillDetailListOld = persistentShoppingBill.getShoppingBillDetailList();
            List<ShoppingBillDetail> shoppingBillDetailListNew = shoppingBill.getShoppingBillDetailList();
            if (shoppingBillmemberNew != null) {
                shoppingBillmemberNew = em.getReference(shoppingBillmemberNew.getClass(), shoppingBillmemberNew.getMemberid());
                shoppingBill.setShoppingBillmember(shoppingBillmemberNew);
            }
            List<ShoppingBillDetail> attachedShoppingBillDetailListNew = new ArrayList<ShoppingBillDetail>();
            for (ShoppingBillDetail shoppingBillDetailListNewShoppingBillDetailToAttach : shoppingBillDetailListNew) {
                shoppingBillDetailListNewShoppingBillDetailToAttach = em.getReference(shoppingBillDetailListNewShoppingBillDetailToAttach.getClass(), shoppingBillDetailListNewShoppingBillDetailToAttach.getShoppingBillDetailseq());
                attachedShoppingBillDetailListNew.add(shoppingBillDetailListNewShoppingBillDetailToAttach);
            }
            shoppingBillDetailListNew = attachedShoppingBillDetailListNew;
            shoppingBill.setShoppingBillDetailList(shoppingBillDetailListNew);
            shoppingBill = em.merge(shoppingBill);
            if (shoppingBillmemberOld != null && !shoppingBillmemberOld.equals(shoppingBillmemberNew)) {
                shoppingBillmemberOld.getShoppingBillList().remove(shoppingBill);
                shoppingBillmemberOld = em.merge(shoppingBillmemberOld);
            }
            if (shoppingBillmemberNew != null && !shoppingBillmemberNew.equals(shoppingBillmemberOld)) {
                shoppingBillmemberNew.getShoppingBillList().add(shoppingBill);
                shoppingBillmemberNew = em.merge(shoppingBillmemberNew);
            }
            for (ShoppingBillDetail shoppingBillDetailListOldShoppingBillDetail : shoppingBillDetailListOld) {
                if (!shoppingBillDetailListNew.contains(shoppingBillDetailListOldShoppingBillDetail)) {
                    shoppingBillDetailListOldShoppingBillDetail.setShoppingBillDetailbill(null);
                    shoppingBillDetailListOldShoppingBillDetail = em.merge(shoppingBillDetailListOldShoppingBillDetail);
                }
            }
            for (ShoppingBillDetail shoppingBillDetailListNewShoppingBillDetail : shoppingBillDetailListNew) {
                if (!shoppingBillDetailListOld.contains(shoppingBillDetailListNewShoppingBillDetail)) {
                    ShoppingBill oldShoppingBillDetailbillOfShoppingBillDetailListNewShoppingBillDetail = shoppingBillDetailListNewShoppingBillDetail.getShoppingBillDetailbill();
                    shoppingBillDetailListNewShoppingBillDetail.setShoppingBillDetailbill(shoppingBill);
                    shoppingBillDetailListNewShoppingBillDetail = em.merge(shoppingBillDetailListNewShoppingBillDetail);
                    if (oldShoppingBillDetailbillOfShoppingBillDetailListNewShoppingBillDetail != null && !oldShoppingBillDetailbillOfShoppingBillDetailListNewShoppingBillDetail.equals(shoppingBill)) {
                        oldShoppingBillDetailbillOfShoppingBillDetailListNewShoppingBillDetail.getShoppingBillDetailList().remove(shoppingBillDetailListNewShoppingBillDetail);
                        oldShoppingBillDetailbillOfShoppingBillDetailListNewShoppingBillDetail = em.merge(oldShoppingBillDetailbillOfShoppingBillDetailListNewShoppingBillDetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = shoppingBill.getShoppingBillid();
                if (findShoppingBill(id) == null) {
                    throw new NonexistentEntityException("The shoppingBill with id " + id + " no longer exists.");
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
            ShoppingBill shoppingBill;
            try {
                shoppingBill = em.getReference(ShoppingBill.class, id);
                shoppingBill.getShoppingBillid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The shoppingBill with id " + id + " no longer exists.", enfe);
            }
            MemberShop shoppingBillmember = shoppingBill.getShoppingBillmember();
            if (shoppingBillmember != null) {
                shoppingBillmember.getShoppingBillList().remove(shoppingBill);
                shoppingBillmember = em.merge(shoppingBillmember);
            }
            List<ShoppingBillDetail> shoppingBillDetailList = shoppingBill.getShoppingBillDetailList();
            for (ShoppingBillDetail shoppingBillDetailListShoppingBillDetail : shoppingBillDetailList) {
                shoppingBillDetailListShoppingBillDetail.setShoppingBillDetailbill(null);
                shoppingBillDetailListShoppingBillDetail = em.merge(shoppingBillDetailListShoppingBillDetail);
            }
            em.remove(shoppingBill);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ShoppingBill> findShoppingBillEntities() {
        return findShoppingBillEntities(true, -1, -1);
    }

    public List<ShoppingBill> findShoppingBillEntities(int maxResults, int firstResult) {
        return findShoppingBillEntities(false, maxResults, firstResult);
    }

    private List<ShoppingBill> findShoppingBillEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ShoppingBill.class));
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

    public ShoppingBill findShoppingBill(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ShoppingBill.class, id);
        } finally {
            em.close();
        }
    }

    public int getShoppingBillCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ShoppingBill> rt = cq.from(ShoppingBill.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
