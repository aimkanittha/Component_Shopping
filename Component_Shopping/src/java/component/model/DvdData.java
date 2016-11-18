/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author wachirapong
 */
@Entity
@Table(name = "DvdData")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DvdData.findAll", query = "SELECT d FROM DvdData d")
    , @NamedQuery(name = "DvdData.findByDvdDataid", query = "SELECT d FROM DvdData d WHERE d.dvdDataid = :dvdDataid")
    , @NamedQuery(name = "DvdData.findByDvdDataname", query = "SELECT d FROM DvdData d WHERE d.dvdDataname = :dvdDataname")
    , @NamedQuery(name = "DvdData.findByDvdDataprice", query = "SELECT d FROM DvdData d WHERE d.dvdDataprice = :dvdDataprice")
    , @NamedQuery(name = "DvdData.findByDvdDatayear", query = "SELECT d FROM DvdData d WHERE d.dvdDatayear = :dvdDatayear")
    , @NamedQuery(name = "DvdData.findQtyOnHand", query = "SELECT d FROM DvdData d WHERE d.dvdDataquantity > 0")
    , @NamedQuery(name = "DvdData.findByDvdDataquantity", query = "SELECT d FROM DvdData d WHERE d.dvdDataquantity = :dvdDataquantity")})
public class DvdData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "DvdData_id")
    private Integer dvdDataid;
    @Size(max = 45)
    @Column(name = "DvdData_name")
    private String dvdDataname;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DvdData_price")
    private Double dvdDataprice;
    @Column(name = "DvdData_year")
    @Temporal(TemporalType.DATE)
    private Date dvdDatayear;
    @Column(name = "DvdData_quantity")
    private Integer dvdDataquantity;
    @OneToMany(mappedBy = "shoppingCartdvd")
    private List<ShoppingCart> shoppingCartList;
    @JoinColumn(name = "DvdData_catalog", referencedColumnName = "Catelog_seq")
    @ManyToOne
    private Catalog dvdDatacatalog;
    @OneToMany(mappedBy = "shoppingBillDetaildvdItem")
    private List<ShoppingBillDetail> shoppingBillDetailList;

    public DvdData() {
    }

    public DvdData(Integer dvdDataid) {
        this.dvdDataid = dvdDataid;
    }

    public Integer getDvdDataid() {
        return dvdDataid;
    }

    public void setDvdDataid(Integer dvdDataid) {
        this.dvdDataid = dvdDataid;
    }

    public String getDvdDataname() {
        return dvdDataname;
    }

    public void setDvdDataname(String dvdDataname) {
        this.dvdDataname = dvdDataname;
    }

    public Double getDvdDataprice() {
        return dvdDataprice;
    }

    public void setDvdDataprice(Double dvdDataprice) {
        this.dvdDataprice = dvdDataprice;
    }

    public Date getDvdDatayear() {
        return dvdDatayear;
    }

    public void setDvdDatayear(Date dvdDatayear) {
        this.dvdDatayear = dvdDatayear;
    }

    public Integer getDvdDataquantity() {
        return dvdDataquantity;
    }

    public void setDvdDataquantity(Integer dvdDataquantity) {
        this.dvdDataquantity = dvdDataquantity;
    }

    @XmlTransient
    public List<ShoppingCart> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<ShoppingCart> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    public Catalog getDvdDatacatalog() {
        return dvdDatacatalog;
    }

    public void setDvdDatacatalog(Catalog dvdDatacatalog) {
        this.dvdDatacatalog = dvdDatacatalog;
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
        hash += (dvdDataid != null ? dvdDataid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DvdData)) {
            return false;
        }
        DvdData other = (DvdData) object;
        if ((this.dvdDataid == null && other.dvdDataid != null) || (this.dvdDataid != null && !this.dvdDataid.equals(other.dvdDataid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "component.model.DvdData[ dvdDataid=" + dvdDataid + " ]";
    }
    
}
