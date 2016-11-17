/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.jpa;

import component.jpa.exceptions.NonexistentEntityException;
import component.jpa.exceptions.PreexistingEntityException;
import component.model.Catalog;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import component.model.DvdData;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author wachirapong
 */
public class CatalogJpaController implements Serializable {

    public CatalogJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Catalog catalog) throws PreexistingEntityException, Exception {
        if (catalog.getDvdDataList() == null) {
            catalog.setDvdDataList(new ArrayList<DvdData>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DvdData> attachedDvdDataList = new ArrayList<DvdData>();
            for (DvdData dvdDataListDvdDataToAttach : catalog.getDvdDataList()) {
                dvdDataListDvdDataToAttach = em.getReference(dvdDataListDvdDataToAttach.getClass(), dvdDataListDvdDataToAttach.getDvdDataid());
                attachedDvdDataList.add(dvdDataListDvdDataToAttach);
            }
            catalog.setDvdDataList(attachedDvdDataList);
            em.persist(catalog);
            for (DvdData dvdDataListDvdData : catalog.getDvdDataList()) {
                Catalog oldDvdDatacatalogOfDvdDataListDvdData = dvdDataListDvdData.getDvdDatacatalog();
                dvdDataListDvdData.setDvdDatacatalog(catalog);
                dvdDataListDvdData = em.merge(dvdDataListDvdData);
                if (oldDvdDatacatalogOfDvdDataListDvdData != null) {
                    oldDvdDatacatalogOfDvdDataListDvdData.getDvdDataList().remove(dvdDataListDvdData);
                    oldDvdDatacatalogOfDvdDataListDvdData = em.merge(oldDvdDatacatalogOfDvdDataListDvdData);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCatalog(catalog.getCatelogseq()) != null) {
                throw new PreexistingEntityException("Catalog " + catalog + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Catalog catalog) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catalog persistentCatalog = em.find(Catalog.class, catalog.getCatelogseq());
            List<DvdData> dvdDataListOld = persistentCatalog.getDvdDataList();
            List<DvdData> dvdDataListNew = catalog.getDvdDataList();
            List<DvdData> attachedDvdDataListNew = new ArrayList<DvdData>();
            for (DvdData dvdDataListNewDvdDataToAttach : dvdDataListNew) {
                dvdDataListNewDvdDataToAttach = em.getReference(dvdDataListNewDvdDataToAttach.getClass(), dvdDataListNewDvdDataToAttach.getDvdDataid());
                attachedDvdDataListNew.add(dvdDataListNewDvdDataToAttach);
            }
            dvdDataListNew = attachedDvdDataListNew;
            catalog.setDvdDataList(dvdDataListNew);
            catalog = em.merge(catalog);
            for (DvdData dvdDataListOldDvdData : dvdDataListOld) {
                if (!dvdDataListNew.contains(dvdDataListOldDvdData)) {
                    dvdDataListOldDvdData.setDvdDatacatalog(null);
                    dvdDataListOldDvdData = em.merge(dvdDataListOldDvdData);
                }
            }
            for (DvdData dvdDataListNewDvdData : dvdDataListNew) {
                if (!dvdDataListOld.contains(dvdDataListNewDvdData)) {
                    Catalog oldDvdDatacatalogOfDvdDataListNewDvdData = dvdDataListNewDvdData.getDvdDatacatalog();
                    dvdDataListNewDvdData.setDvdDatacatalog(catalog);
                    dvdDataListNewDvdData = em.merge(dvdDataListNewDvdData);
                    if (oldDvdDatacatalogOfDvdDataListNewDvdData != null && !oldDvdDatacatalogOfDvdDataListNewDvdData.equals(catalog)) {
                        oldDvdDatacatalogOfDvdDataListNewDvdData.getDvdDataList().remove(dvdDataListNewDvdData);
                        oldDvdDatacatalogOfDvdDataListNewDvdData = em.merge(oldDvdDatacatalogOfDvdDataListNewDvdData);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = catalog.getCatelogseq();
                if (findCatalog(id) == null) {
                    throw new NonexistentEntityException("The catalog with id " + id + " no longer exists.");
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
            Catalog catalog;
            try {
                catalog = em.getReference(Catalog.class, id);
                catalog.getCatelogseq();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catalog with id " + id + " no longer exists.", enfe);
            }
            List<DvdData> dvdDataList = catalog.getDvdDataList();
            for (DvdData dvdDataListDvdData : dvdDataList) {
                dvdDataListDvdData.setDvdDatacatalog(null);
                dvdDataListDvdData = em.merge(dvdDataListDvdData);
            }
            em.remove(catalog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Catalog> findCatalogEntities() {
        return findCatalogEntities(true, -1, -1);
    }

    public List<Catalog> findCatalogEntities(int maxResults, int firstResult) {
        return findCatalogEntities(false, maxResults, firstResult);
    }

    private List<Catalog> findCatalogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Catalog.class));
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

    public Catalog findCatalog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Catalog.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatalogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Catalog> rt = cq.from(Catalog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
