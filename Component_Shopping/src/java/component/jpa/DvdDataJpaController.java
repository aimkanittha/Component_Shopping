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
import component.model.Catalog;
import component.model.DvdData;
import component.model.ShoppingBillDetail;
import java.util.ArrayList;
import java.util.List;
import component.model.ShoppingCart;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wachirapong
 */
public class DvdDataJpaController implements Serializable {

    public DvdDataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DvdData dvdData) throws PreexistingEntityException, Exception {
        if (dvdData.getShoppingBillDetailList() == null) {
            dvdData.setShoppingBillDetailList(new ArrayList<ShoppingBillDetail>());
        }
        if (dvdData.getShoppingCartList() == null) {
            dvdData.setShoppingCartList(new ArrayList<ShoppingCart>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catalog dvdDatacatalog = dvdData.getDvdDatacatalog();
            if (dvdDatacatalog != null) {
                dvdDatacatalog = em.getReference(dvdDatacatalog.getClass(), dvdDatacatalog.getCatelogseq());
                dvdData.setDvdDatacatalog(dvdDatacatalog);
            }
            List<ShoppingBillDetail> attachedShoppingBillDetailList = new ArrayList<ShoppingBillDetail>();
            for (ShoppingBillDetail shoppingBillDetailListShoppingBillDetailToAttach : dvdData.getShoppingBillDetailList()) {
                shoppingBillDetailListShoppingBillDetailToAttach = em.getReference(shoppingBillDetailListShoppingBillDetailToAttach.getClass(), shoppingBillDetailListShoppingBillDetailToAttach.getShoppingBillDetailseq());
                attachedShoppingBillDetailList.add(shoppingBillDetailListShoppingBillDetailToAttach);
            }
            dvdData.setShoppingBillDetailList(attachedShoppingBillDetailList);
            List<ShoppingCart> attachedShoppingCartList = new ArrayList<ShoppingCart>();
            for (ShoppingCart shoppingCartListShoppingCartToAttach : dvdData.getShoppingCartList()) {
                shoppingCartListShoppingCartToAttach = em.getReference(shoppingCartListShoppingCartToAttach.getClass(), shoppingCartListShoppingCartToAttach.getShoppingCartid());
                attachedShoppingCartList.add(shoppingCartListShoppingCartToAttach);
            }
            dvdData.setShoppingCartList(attachedShoppingCartList);
            em.persist(dvdData);
            if (dvdDatacatalog != null) {
                dvdDatacatalog.getDvdDataList().add(dvdData);
                dvdDatacatalog = em.merge(dvdDatacatalog);
            }
            for (ShoppingBillDetail shoppingBillDetailListShoppingBillDetail : dvdData.getShoppingBillDetailList()) {
                DvdData oldShoppingBillDetaildvdItemOfShoppingBillDetailListShoppingBillDetail = shoppingBillDetailListShoppingBillDetail.getShoppingBillDetaildvdItem();
                shoppingBillDetailListShoppingBillDetail.setShoppingBillDetaildvdItem(dvdData);
                shoppingBillDetailListShoppingBillDetail = em.merge(shoppingBillDetailListShoppingBillDetail);
                if (oldShoppingBillDetaildvdItemOfShoppingBillDetailListShoppingBillDetail != null) {
                    oldShoppingBillDetaildvdItemOfShoppingBillDetailListShoppingBillDetail.getShoppingBillDetailList().remove(shoppingBillDetailListShoppingBillDetail);
                    oldShoppingBillDetaildvdItemOfShoppingBillDetailListShoppingBillDetail = em.merge(oldShoppingBillDetaildvdItemOfShoppingBillDetailListShoppingBillDetail);
                }
            }
            for (ShoppingCart shoppingCartListShoppingCart : dvdData.getShoppingCartList()) {
                DvdData oldShoppingCartdvdOfShoppingCartListShoppingCart = shoppingCartListShoppingCart.getShoppingCartdvd();
                shoppingCartListShoppingCart.setShoppingCartdvd(dvdData);
                shoppingCartListShoppingCart = em.merge(shoppingCartListShoppingCart);
                if (oldShoppingCartdvdOfShoppingCartListShoppingCart != null) {
                    oldShoppingCartdvdOfShoppingCartListShoppingCart.getShoppingCartList().remove(shoppingCartListShoppingCart);
                    oldShoppingCartdvdOfShoppingCartListShoppingCart = em.merge(oldShoppingCartdvdOfShoppingCartListShoppingCart);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDvdData(dvdData.getDvdDataid()) != null) {
                throw new PreexistingEntityException("DvdData " + dvdData + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DvdData dvdData) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DvdData persistentDvdData = em.find(DvdData.class, dvdData.getDvdDataid());
            Catalog dvdDatacatalogOld = persistentDvdData.getDvdDatacatalog();
            Catalog dvdDatacatalogNew = dvdData.getDvdDatacatalog();
            List<ShoppingBillDetail> shoppingBillDetailListOld = persistentDvdData.getShoppingBillDetailList();
            List<ShoppingBillDetail> shoppingBillDetailListNew = dvdData.getShoppingBillDetailList();
            List<ShoppingCart> shoppingCartListOld = persistentDvdData.getShoppingCartList();
            List<ShoppingCart> shoppingCartListNew = dvdData.getShoppingCartList();
            if (dvdDatacatalogNew != null) {
                dvdDatacatalogNew = em.getReference(dvdDatacatalogNew.getClass(), dvdDatacatalogNew.getCatelogseq());
                dvdData.setDvdDatacatalog(dvdDatacatalogNew);
            }
            List<ShoppingBillDetail> attachedShoppingBillDetailListNew = new ArrayList<ShoppingBillDetail>();
            for (ShoppingBillDetail shoppingBillDetailListNewShoppingBillDetailToAttach : shoppingBillDetailListNew) {
                shoppingBillDetailListNewShoppingBillDetailToAttach = em.getReference(shoppingBillDetailListNewShoppingBillDetailToAttach.getClass(), shoppingBillDetailListNewShoppingBillDetailToAttach.getShoppingBillDetailseq());
                attachedShoppingBillDetailListNew.add(shoppingBillDetailListNewShoppingBillDetailToAttach);
            }
            shoppingBillDetailListNew = attachedShoppingBillDetailListNew;
            dvdData.setShoppingBillDetailList(shoppingBillDetailListNew);
            List<ShoppingCart> attachedShoppingCartListNew = new ArrayList<ShoppingCart>();
            for (ShoppingCart shoppingCartListNewShoppingCartToAttach : shoppingCartListNew) {
                shoppingCartListNewShoppingCartToAttach = em.getReference(shoppingCartListNewShoppingCartToAttach.getClass(), shoppingCartListNewShoppingCartToAttach.getShoppingCartid());
                attachedShoppingCartListNew.add(shoppingCartListNewShoppingCartToAttach);
            }
            shoppingCartListNew = attachedShoppingCartListNew;
            dvdData.setShoppingCartList(shoppingCartListNew);
            dvdData = em.merge(dvdData);
            if (dvdDatacatalogOld != null && !dvdDatacatalogOld.equals(dvdDatacatalogNew)) {
                dvdDatacatalogOld.getDvdDataList().remove(dvdData);
                dvdDatacatalogOld = em.merge(dvdDatacatalogOld);
            }
            if (dvdDatacatalogNew != null && !dvdDatacatalogNew.equals(dvdDatacatalogOld)) {
                dvdDatacatalogNew.getDvdDataList().add(dvdData);
                dvdDatacatalogNew = em.merge(dvdDatacatalogNew);
            }
            for (ShoppingBillDetail shoppingBillDetailListOldShoppingBillDetail : shoppingBillDetailListOld) {
                if (!shoppingBillDetailListNew.contains(shoppingBillDetailListOldShoppingBillDetail)) {
                    shoppingBillDetailListOldShoppingBillDetail.setShoppingBillDetaildvdItem(null);
                    shoppingBillDetailListOldShoppingBillDetail = em.merge(shoppingBillDetailListOldShoppingBillDetail);
                }
            }
            for (ShoppingBillDetail shoppingBillDetailListNewShoppingBillDetail : shoppingBillDetailListNew) {
                if (!shoppingBillDetailListOld.contains(shoppingBillDetailListNewShoppingBillDetail)) {
                    DvdData oldShoppingBillDetaildvdItemOfShoppingBillDetailListNewShoppingBillDetail = shoppingBillDetailListNewShoppingBillDetail.getShoppingBillDetaildvdItem();
                    shoppingBillDetailListNewShoppingBillDetail.setShoppingBillDetaildvdItem(dvdData);
                    shoppingBillDetailListNewShoppingBillDetail = em.merge(shoppingBillDetailListNewShoppingBillDetail);
                    if (oldShoppingBillDetaildvdItemOfShoppingBillDetailListNewShoppingBillDetail != null && !oldShoppingBillDetaildvdItemOfShoppingBillDetailListNewShoppingBillDetail.equals(dvdData)) {
                        oldShoppingBillDetaildvdItemOfShoppingBillDetailListNewShoppingBillDetail.getShoppingBillDetailList().remove(shoppingBillDetailListNewShoppingBillDetail);
                        oldShoppingBillDetaildvdItemOfShoppingBillDetailListNewShoppingBillDetail = em.merge(oldShoppingBillDetaildvdItemOfShoppingBillDetailListNewShoppingBillDetail);
                    }
                }
            }
            for (ShoppingCart shoppingCartListOldShoppingCart : shoppingCartListOld) {
                if (!shoppingCartListNew.contains(shoppingCartListOldShoppingCart)) {
                    shoppingCartListOldShoppingCart.setShoppingCartdvd(null);
                    shoppingCartListOldShoppingCart = em.merge(shoppingCartListOldShoppingCart);
                }
            }
            for (ShoppingCart shoppingCartListNewShoppingCart : shoppingCartListNew) {
                if (!shoppingCartListOld.contains(shoppingCartListNewShoppingCart)) {
                    DvdData oldShoppingCartdvdOfShoppingCartListNewShoppingCart = shoppingCartListNewShoppingCart.getShoppingCartdvd();
                    shoppingCartListNewShoppingCart.setShoppingCartdvd(dvdData);
                    shoppingCartListNewShoppingCart = em.merge(shoppingCartListNewShoppingCart);
                    if (oldShoppingCartdvdOfShoppingCartListNewShoppingCart != null && !oldShoppingCartdvdOfShoppingCartListNewShoppingCart.equals(dvdData)) {
                        oldShoppingCartdvdOfShoppingCartListNewShoppingCart.getShoppingCartList().remove(shoppingCartListNewShoppingCart);
                        oldShoppingCartdvdOfShoppingCartListNewShoppingCart = em.merge(oldShoppingCartdvdOfShoppingCartListNewShoppingCart);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dvdData.getDvdDataid();
                if (findDvdData(id) == null) {
                    throw new NonexistentEntityException("The dvdData with id " + id + " no longer exists.");
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
            DvdData dvdData;
            try {
                dvdData = em.getReference(DvdData.class, id);
                dvdData.getDvdDataid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dvdData with id " + id + " no longer exists.", enfe);
            }
            Catalog dvdDatacatalog = dvdData.getDvdDatacatalog();
            if (dvdDatacatalog != null) {
                dvdDatacatalog.getDvdDataList().remove(dvdData);
                dvdDatacatalog = em.merge(dvdDatacatalog);
            }
            List<ShoppingBillDetail> shoppingBillDetailList = dvdData.getShoppingBillDetailList();
            for (ShoppingBillDetail shoppingBillDetailListShoppingBillDetail : shoppingBillDetailList) {
                shoppingBillDetailListShoppingBillDetail.setShoppingBillDetaildvdItem(null);
                shoppingBillDetailListShoppingBillDetail = em.merge(shoppingBillDetailListShoppingBillDetail);
            }
            List<ShoppingCart> shoppingCartList = dvdData.getShoppingCartList();
            for (ShoppingCart shoppingCartListShoppingCart : shoppingCartList) {
                shoppingCartListShoppingCart.setShoppingCartdvd(null);
                shoppingCartListShoppingCart = em.merge(shoppingCartListShoppingCart);
            }
            em.remove(dvdData);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DvdData> findDvdDataEntities() {
        return findDvdDataEntities(true, -1, -1);
    }

    public List<DvdData> findDvdDataEntities(int maxResults, int firstResult) {
        return findDvdDataEntities(false, maxResults, firstResult);
    }

    private List<DvdData> findDvdDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DvdData.class));
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

    public DvdData findDvdData(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DvdData.class, id);
        } finally {
            em.close();
        }
    }

    public int getDvdDataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DvdData> rt = cq.from(DvdData.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
