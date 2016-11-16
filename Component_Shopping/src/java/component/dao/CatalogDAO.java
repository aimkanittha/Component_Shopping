/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.dao;

import javax.ejb.Stateless;
import component.model.Catalog;
import java.util.List;

/**
 *
 * @author USER
 */
@Stateless
public class CatalogDAO implements CatalogDAOLocal {

    @Override
    public void addCatalog(Catalog Catalog) {
    }
    
    @Override
    public void removeCatalog(Catalog Catalog) {
    }

    @Override
    public int getCatalogId(int catalogId) {
        return 0;
    }

    @Override
    public List<Catalog> getAllCatalog() {
        return null;
    }
    
}
