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
import component.model.DvdData;
import component.model.MemberShop;
import component.model.ShoppingCart;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author wachirapong
 */
public class ShoppingCartJpaController implements Serializable {

    public ShoppingCartJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ShoppingCart shoppingCart) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DvdData shoppingCartdvd = shoppingCart.getShoppingCartdvd();
            if (shoppingCartdvd != null) {
                shoppingCartdvd = em.getReference(shoppingCartdvd.getClass(), shoppingCartdvd.getDvdDataid());
                shoppingCart.setShoppingCartdvd(shoppingCartdvd);
            }
            MemberShop shoppingCartmember = shoppingCart.getShoppingCartmember();
            if (shoppingCartmember != null) {
                shoppingCartmember = em.getReference(shoppingCartmember.getClass(), shoppingCartmember.getMemberid());
                shoppingCart.setShoppingCartmember(shoppingCartmember);
            }
            em.persist(shoppingCart);
            if (shoppingCartdvd != null) {
                shoppingCartdvd.getShoppingCartList().add(shoppingCart);
                shoppingCartdvd = em.merge(shoppingCartdvd);
            }
            if (shoppingCartmember != null) {
                shoppingCartmember.getShoppingCartList().add(shoppingCart);
                shoppingCartmember = em.merge(shoppingCartmember);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findShoppingCart(shoppingCart.getShoppingCartid()) != null) {
                throw new PreexistingEntityException("ShoppingCart " + shoppingCart + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<ShoppingCart> getMemberCart(MemberShop member){
       EntityManager em = getEntityManager();
       List<ShoppingCart> sc = em.createNamedQuery("ShoppingCart.findByShoppingCartmember").setParameter("shoppingCartmember", member).getResultList();
       return sc;
    }

    public void edit(ShoppingCart shoppingCart) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ShoppingCart persistentShoppingCart = em.find(ShoppingCart.class, shoppingCart.getShoppingCartid());
            DvdData shoppingCartdvdOld = persistentShoppingCart.getShoppingCartdvd();
            DvdData shoppingCartdvdNew = shoppingCart.getShoppingCartdvd();
            MemberShop shoppingCartmemberOld = persistentShoppingCart.getShoppingCartmember();
            MemberShop shoppingCartmemberNew = shoppingCart.getShoppingCartmember();
            if (shoppingCartdvdNew != null) {
                shoppingCartdvdNew = em.getReference(shoppingCartdvdNew.getClass(), shoppingCartdvdNew.getDvdDataid());
                shoppingCart.setShoppingCartdvd(shoppingCartdvdNew);
            }
            if (shoppingCartmemberNew != null) {
                shoppingCartmemberNew = em.getReference(shoppingCartmemberNew.getClass(), shoppingCartmemberNew.getMemberid());
                shoppingCart.setShoppingCartmember(shoppingCartmemberNew);
            }
            shoppingCart = em.merge(shoppingCart);
            if (shoppingCartdvdOld != null && !shoppingCartdvdOld.equals(shoppingCartdvdNew)) {
                shoppingCartdvdOld.getShoppingCartList().remove(shoppingCart);
                shoppingCartdvdOld = em.merge(shoppingCartdvdOld);
            }
            if (shoppingCartdvdNew != null && !shoppingCartdvdNew.equals(shoppingCartdvdOld)) {
                shoppingCartdvdNew.getShoppingCartList().add(shoppingCart);
                shoppingCartdvdNew = em.merge(shoppingCartdvdNew);
            }
            if (shoppingCartmemberOld != null && !shoppingCartmemberOld.equals(shoppingCartmemberNew)) {
                shoppingCartmemberOld.getShoppingCartList().remove(shoppingCart);
                shoppingCartmemberOld = em.merge(shoppingCartmemberOld);
            }
            if (shoppingCartmemberNew != null && !shoppingCartmemberNew.equals(shoppingCartmemberOld)) {
                shoppingCartmemberNew.getShoppingCartList().add(shoppingCart);
                shoppingCartmemberNew = em.merge(shoppingCartmemberNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = shoppingCart.getShoppingCartid();
                if (findShoppingCart(id) == null) {
                    throw new NonexistentEntityException("The shoppingCart with id " + id + " no longer exists.");
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
            ShoppingCart shoppingCart;
            try {
                shoppingCart = em.getReference(ShoppingCart.class, id);
                shoppingCart.getShoppingCartid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The shoppingCart with id " + id + " no longer exists.", enfe);
            }
            DvdData shoppingCartdvd = shoppingCart.getShoppingCartdvd();
            if (shoppingCartdvd != null) {
                shoppingCartdvd.getShoppingCartList().remove(shoppingCart);
                shoppingCartdvd = em.merge(shoppingCartdvd);
            }
            MemberShop shoppingCartmember = shoppingCart.getShoppingCartmember();
            if (shoppingCartmember != null) {
                shoppingCartmember.getShoppingCartList().remove(shoppingCart);
                shoppingCartmember = em.merge(shoppingCartmember);
            }
            em.remove(shoppingCart);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ShoppingCart> findShoppingCartEntities() {
        return findShoppingCartEntities(true, -1, -1);
    }

    public List<ShoppingCart> findShoppingCartEntities(int maxResults, int firstResult) {
        return findShoppingCartEntities(false, maxResults, firstResult);
    }

    private List<ShoppingCart> findShoppingCartEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ShoppingCart.class));
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

    public ShoppingCart findShoppingCart(Integer id) {
        EntityManager em = getEntityManager();
        try {
//            List<ShoppingCart> scList = em.createNamedQuery("ShoppingCart.findByShoppingCartid").setParameter("shoppingCartid",id).getResultList();
//            if( scList.size() == 0)
//                return null;
//            else
            return em.find(ShoppingCart.class, id);
        } finally {
            em.close();
        }
    }
    
    public ShoppingCart findMemberCart(MemberShop member,DvdData dvd){
        EntityManager em = getEntityManager();
        List<ShoppingCart> query = em.createNamedQuery("ShoppingCart.findMemberCart").setParameter("shoppingCartmember", member).setParameter("shoppingCartdvd", dvd).getResultList();
        if( query.size() == 0)
           return null;
       return query.get(0);
    }

    public int getShoppingCartCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ShoppingCart> rt = cq.from(ShoppingCart.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
