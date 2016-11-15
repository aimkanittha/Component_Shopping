/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author USER
 */
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String orderNumber;
    @Column(name = "TOTALPRICE")
    private Double totalPrice;
    @Column(name = "TOTALQTY")
    private Integer totalQty;
    @Column(name = "DATE")
    private Date date;
    
    public ShoppingCart(){}
    
    public ShoppingCart(String id){this.orderNumber = id;}
    
    public void setTotalPrice(Double tPrice){ this.totalPrice = tPrice;}
    
    public Double getTotalPrice(){ return this.totalPrice;}
    
    public void setTotalQty(Integer tQty){ this.totalQty = tQty;}
    
    public Integer getTotalQty(){ return this.totalQty;}
    
    // DATE FORMAT ********************
    public void setDate(Date date){ this.date = date;}
    
    public Date getDate(){ return this.date;}
    ////////////////////////////////////
}
