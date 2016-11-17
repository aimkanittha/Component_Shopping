/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.model;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author wachirapong
 */
@Entity
@Table(name = "ShoppingBill")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShoppingBill.findAll", query = "SELECT s FROM ShoppingBill s")
    , @NamedQuery(name = "ShoppingBill.findByShoppingBillid", query = "SELECT s FROM ShoppingBill s WHERE s.shoppingBillid = :shoppingBillid")
    , @NamedQuery(name = "ShoppingBill.findByShoppingBilltotal", query = "SELECT s FROM ShoppingBill s WHERE s.shoppingBilltotal = :shoppingBilltotal")})
public class ShoppingBill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ShoppingBill_id")
    private Integer shoppingBillid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ShoppingBill_total")
    private Double shoppingBilltotal;
    @JoinColumn(name = "ShoppingBill_member", referencedColumnName = "Member_id")
    @ManyToOne
    private MemberShop shoppingBillmember;
    @OneToMany(mappedBy = "shoppingBillDetailbill")
    private List<ShoppingBillDetail> shoppingBillDetailList;

    public ShoppingBill() {
    }

    public ShoppingBill(Integer shoppingBillid) {
        this.shoppingBillid = shoppingBillid;
    }

    public Integer getShoppingBillid() {
        return shoppingBillid;
    }

    public void setShoppingBillid(Integer shoppingBillid) {
        this.shoppingBillid = shoppingBillid;
    }

    public Double getShoppingBilltotal() {
        return shoppingBilltotal;
    }

    public void setShoppingBilltotal(Double shoppingBilltotal) {
        this.shoppingBilltotal = shoppingBilltotal;
    }

    public MemberShop getShoppingBillmember() {
        return shoppingBillmember;
    }

    public void setShoppingBillmember(MemberShop shoppingBillmember) {
        this.shoppingBillmember = shoppingBillmember;
    }

    @XmlTransient
    public List<ShoppingBillDetail> getShoppingBillDetailList() {
        return shoppingBillDetailList;
    }

    public void setShoppingBillDetailList(List<ShoppingBillDetail> shoppingBillDetailList) {
        this.shoppingBillDetailList = shoppingBillDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shoppingBillid != null ? shoppingBillid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShoppingBill)) {
            return false;
        }
        ShoppingBill other = (ShoppingBill) object;
        if ((this.shoppingBillid == null && other.shoppingBillid != null) || (this.shoppingBillid != null && !this.shoppingBillid.equals(other.shoppingBillid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "component.model.ShoppingBill[ shoppingBillid=" + shoppingBillid + " ]";
    }
    
}
