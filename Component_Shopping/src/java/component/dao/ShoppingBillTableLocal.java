/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.dao;

import component.model.ShoppingBill;
import component.model.ShoppingBillDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wachirapong
 */
@Local
public interface ShoppingBillTableLocal {

    void persist(Object object);

    void removeItem(String itemId);

    List<ShoppingBillDetail> findAll();
}
