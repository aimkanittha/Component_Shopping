/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.jpa;

import component.jpa.exceptions.IllegalOrphanException;
import component.jpa.exceptions.NonexistentEntityException;
import component.model.MemberShop;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import component.model.ShoppingBill;
import java.util.ArrayList;
import java.util.List;
import component.model.ShoppingCart;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wachirapong
 */
public class MemberShopJpaController implements Serializable {

    public MemberShopJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MemberShop memberShop) {
        if (memberShop.getShoppingBillList() == null) {
            memberShop.setShoppingBillList(new ArrayList<ShoppingBill>());
        }
        if (memberShop.getShoppingCartList() == null) {
            memberShop.setShoppingCartList(new ArrayList<ShoppingCart>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ShoppingBill> attachedShoppingBillList = new ArrayList<ShoppingBill>();
            for (ShoppingBill shoppingBillListShoppingBillToAttach : memberShop.getShoppingBillList()) {
                shoppingBillListShoppingBillToAttach = em.getReference(shoppingBillListShoppingBillToAttach.getClass(), shoppingBillListShoppingBillToAttach.getShoppingBillid());
                attachedShoppingBillList.add(shoppingBillListShoppingBillToAttach);
            }
            memberShop.setShoppingBillList(attachedShoppingBillList);
            List<ShoppingCart> attachedShoppingCartList = new ArrayList<ShoppingCart>();
            for (ShoppingCart shoppingCartListShoppingCartToAttach : memberShop.getShoppingCartList()) {
                shoppingCartListShoppingCartToAttach = em.getReference(shoppingCartListShoppingCartToAttach.getClass(), shoppingCartListShoppingCartToAttach.getShoppingCartid());
                attachedShoppingCartList.add(shoppingCartListShoppingCartToAttach);
            }
            memberShop.setShoppingCartList(attachedShoppingCartList);
            em.persist(memberShop);
            for (ShoppingBill shoppingBillListShoppingBill : memberShop.getShoppingBillList()) {
                MemberShop oldShoppingBillmemberOfShoppingBillListShoppingBill = shoppingBillListShoppingBill.getShoppingBillmember();
                shoppingBillListShoppingBill.setShoppingBillmember(memberShop);
                shoppingBillListShoppingBill = em.merge(shoppingBillListShoppingBill);
                if (oldShoppingBillmemberOfShoppingBillListShoppingBill != null) {
                    oldShoppingBillmemberOfShoppingBillListShoppingBill.getShoppingBillList().remove(shoppingBillListShoppingBill);
                    oldShoppingBillmemberOfShoppingBillListShoppingBill = em.merge(oldShoppingBillmemberOfShoppingBillListShoppingBill);
                }
            }
            for (ShoppingCart shoppingCartListShoppingCart : memberShop.getShoppingCartList()) {
                MemberShop oldShoppingCartmemberOfShoppingCartListShoppingCart = shoppingCartListShoppingCart.getShoppingCartmember();
                shoppingCartListShoppingCart.setShoppingCartmember(memberShop);
                shoppingCartListShoppingCart = em.merge(shoppingCartListShoppingCart);
                if (oldShoppingCartmemberOfShoppingCartListShoppingCart != null) {
                    oldShoppingCartmemberOfShoppingCartListShoppingCart.getShoppingCartList().remove(shoppingCartListShoppingCart);
                    oldShoppingCartmemberOfShoppingCartListShoppingCart = em.merge(oldShoppingCartmemberOfShoppingCartListShoppingCart);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MemberShop memberShop) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MemberShop persistentMemberShop = em.find(MemberShop.class, memberShop.getMemberid());
            List<ShoppingBill> shoppingBillListOld = persistentMemberShop.getShoppingBillList();
            List<ShoppingBill> shoppingBillListNew = memberShop.getShoppingBillList();
            List<ShoppingCart> shoppingCartListOld = persistentMemberShop.getShoppingCartList();
            List<ShoppingCart> shoppingCartListNew = memberShop.getShoppingCartList();
            List<String> illegalOrphanMessages = null;
            for (ShoppingCart shoppingCartListOldShoppingCart : shoppingCartListOld) {
                if (!shoppingCartListNew.contains(shoppingCartListOldShoppingCart)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ShoppingCart " + shoppingCartListOldShoppingCart + " since its shoppingCartmember field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ShoppingBill> attachedShoppingBillListNew = new ArrayList<ShoppingBill>();
            for (ShoppingBill shoppingBillListNewShoppingBillToAttach : shoppingBillListNew) {
                shoppingBillListNewShoppingBillToAttach = em.getReference(shoppingBillListNewShoppingBillToAttach.getClass(), shoppingBillListNewShoppingBillToAttach.getShoppingBillid());
                attachedShoppingBillListNew.add(shoppingBillListNewShoppingBillToAttach);
            }
            shoppingBillListNew = attachedShoppingBillListNew;
            memberShop.setShoppingBillList(shoppingBillListNew);
            List<ShoppingCart> attachedShoppingCartListNew = new ArrayList<ShoppingCart>();
            for (ShoppingCart shoppingCartListNewShoppingCartToAttach : shoppingCartListNew) {
                shoppingCartListNewShoppingCartToAttach = em.getReference(shoppingCartListNewShoppingCartToAttach.getClass(), shoppingCartListNewShoppingCartToAttach.getShoppingCartid());
                attachedShoppingCartListNew.add(shoppingCartListNewShoppingCartToAttach);
            }
            shoppingCartListNew = attachedShoppingCartListNew;
            memberShop.setShoppingCartList(shoppingCartListNew);
            memberShop = em.merge(memberShop);
            for (ShoppingBill shoppingBillListOldShoppingBill : shoppingBillListOld) {
                if (!shoppingBillListNew.contains(shoppingBillListOldShoppingBill)) {
                    shoppingBillListOldShoppingBill.setShoppingBillmember(null);
                    shoppingBillListOldShoppingBill = em.merge(shoppingBillListOldShoppingBill);
                }
            }
            for (ShoppingBill shoppingBillListNewShoppingBill : shoppingBillListNew) {
                if (!shoppingBillListOld.contains(shoppingBillListNewShoppingBill)) {
                    MemberShop oldShoppingBillmemberOfShoppingBillListNewShoppingBill = shoppingBillListNewShoppingBill.getShoppingBillmember();
                    shoppingBillListNewShoppingBill.setShoppingBillmember(memberShop);
                    shoppingBillListNewShoppingBill = em.merge(shoppingBillListNewShoppingBill);
                    if (oldShoppingBillmemberOfShoppingBillListNewShoppingBill != null && !oldShoppingBillmemberOfShoppingBillListNewShoppingBill.equals(memberShop)) {
                        oldShoppingBillmemberOfShoppingBillListNewShoppingBill.getShoppingBillList().remove(shoppingBillListNewShoppingBill);
                        oldShoppingBillmemberOfShoppingBillListNewShoppingBill = em.merge(oldShoppingBillmemberOfShoppingBillListNewShoppingBill);
                    }
                }
            }
            for (ShoppingCart shoppingCartListNewShoppingCart : shoppingCartListNew) {
                if (!shoppingCartListOld.contains(shoppingCartListNewShoppingCart)) {
                    MemberShop oldShoppingCartmemberOfShoppingCartListNewShoppingCart = shoppingCartListNewShoppingCart.getShoppingCartmember();
                    shoppingCartListNewShoppingCart.setShoppingCartmember(memberShop);
                    shoppingCartListNewShoppingCart = em.merge(shoppingCartListNewShoppingCart);
                    if (oldShoppingCartmemberOfShoppingCartListNewShoppingCart != null && !oldShoppingCartmemberOfShoppingCartListNewShoppingCart.equals(memberShop)) {
                        oldShoppingCartmemberOfShoppingCartListNewShoppingCart.getShoppingCartList().remove(shoppingCartListNewShoppingCart);
                        oldShoppingCartmemberOfShoppingCartListNewShoppingCart = em.merge(oldShoppingCartmemberOfShoppingCartListNewShoppingCart);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = memberShop.getMemberid();
                if (findMemberShop(id) == null) {
                    throw new NonexistentEntityException("The memberShop with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MemberShop memberShop;
            try {
                memberShop = em.getReference(MemberShop.class, id);
                memberShop.getMemberid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The memberShop with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ShoppingCart> shoppingCartListOrphanCheck = memberShop.getShoppingCartList();
            for (ShoppingCart shoppingCartListOrphanCheckShoppingCart : shoppingCartListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MemberShop (" + memberShop + ") cannot be destroyed since the ShoppingCart " + shoppingCartListOrphanCheckShoppingCart + " in its shoppingCartList field has a non-nullable shoppingCartmember field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ShoppingBill> shoppingBillList = memberShop.getShoppingBillList();
            for (ShoppingBill shoppingBillListShoppingBill : shoppingBillList) {
                shoppingBillListShoppingBill.setShoppingBillmember(null);
                shoppingBillListShoppingBill = em.merge(shoppingBillListShoppingBill);
            }
            em.remove(memberShop);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MemberShop> findMemberShopEntities() {
        return findMemberShopEntities(true, -1, -1);
    }

    public List<MemberShop> findMemberShopEntities(int maxResults, int firstResult) {
        return findMemberShopEntities(false, maxResults, firstResult);
    }

    private List<MemberShop> findMemberShopEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MemberShop.class));
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

    public MemberShop findMemberShop(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MemberShop.class, id);
        } finally {
            em.close();
        }
    }

    public int getMemberShopCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MemberShop> rt = cq.from(MemberShop.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
