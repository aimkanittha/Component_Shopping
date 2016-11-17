/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wachirapong
 */
@Entity
@Table(name = "ShoppingCart")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShoppingCart.findAll", query = "SELECT s FROM ShoppingCart s")
    , @NamedQuery(name = "ShoppingCart.findByShoppingCartmember", query = "SELECT s FROM ShoppingCart s WHERE s.shoppingCartmember = :shoppingCartmember")
    , @NamedQuery(name = "ShoppingCart.findMemberCart", query = "SELECT s FROM ShoppingCart s WHERE s.shoppingCartmember = :shoppingCartmember AND s.shoppingCartdvd = :shoppingCartdvd")
    , @NamedQuery(name = "ShoppingCart.findByShoppingCartid", query = "SELECT s FROM ShoppingCart s WHERE s.shoppingCartid = :shoppingCartid")
    , @NamedQuery(name = "ShoppingCart.findByShoppingCartdvQty", query = "SELECT s FROM ShoppingCart s WHERE s.shoppingCartdvQty = :shoppingCartdvQty")})
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ShoppingCart_id")
    private Integer shoppingCartid;
    @Column(name = "ShoppingCart_dvQty")
    private Integer shoppingCartdvQty;
    @JoinColumn(name = "ShoppingCart_dvd", referencedColumnName = "DvdData_id")
    @ManyToOne
    private DvdData shoppingCartdvd;
    @JoinColumn(name = "ShoppingCart_member", referencedColumnName = "Member_id")
    @ManyToOne(optional = false)
    private MemberShop shoppingCartmember;

    public ShoppingCart() {
    }

    public ShoppingCart(Integer shoppingCartid) {
        this.shoppingCartid = shoppingCartid;
    }

    public Integer getShoppingCartid() {
        return shoppingCartid;
    }

    public void setShoppingCartid(Integer shoppingCartid) {
        this.shoppingCartid = shoppingCartid;
    }

    public Integer getShoppingCartdvQty() {
        return shoppingCartdvQty;
    }

    public void setShoppingCartdvQty(Integer shoppingCartdvQty) {
        this.shoppingCartdvQty = shoppingCartdvQty;
    }

    public DvdData getShoppingCartdvd() {
        return shoppingCartdvd;
    }

    public void setShoppingCartdvd(DvdData shoppingCartdvd) {
        this.shoppingCartdvd = shoppingCartdvd;
    }

    public MemberShop getShoppingCartmember() {
        return shoppingCartmember;
    }

    public void setShoppingCartmember(MemberShop shoppingCartmember) {
        this.shoppingCartmember = shoppingCartmember;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shoppingCartid != null ? shoppingCartid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShoppingCart)) {
            return false;
        }
        ShoppingCart other = (ShoppingCart) object;
        if ((this.shoppingCartid == null && other.shoppingCartid != null) || (this.shoppingCartid != null && !this.shoppingCartid.equals(other.shoppingCartid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "component.model.ShoppingCart[ shoppingCartid=" + shoppingCartid + " ]";
    }
    
}
