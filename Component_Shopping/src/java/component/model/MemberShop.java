/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author wachirapong
 */
@Entity
@Table(name = "Member")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MemberShop.findAll", query = "SELECT m FROM MemberShop m")
    , @NamedQuery(name = "MemberShop.findByMemberid", query = "SELECT m FROM MemberShop m WHERE m.memberid = :memberid")
    , @NamedQuery(name = "MemberShop.findByMemberusername", query = "SELECT m FROM MemberShop m WHERE m.memberusername = :memberusername")
    , @NamedQuery(name = "MemberShop.findByMemberpassword", query = "SELECT m FROM MemberShop m WHERE m.memberpassword = :memberpassword")
    , @NamedQuery(name = "MemberShop.findByMemberfirstname", query = "SELECT m FROM MemberShop m WHERE m.memberfirstname = :memberfirstname")
    , @NamedQuery(name = "MemberShop.findByMemberlastname", query = "SELECT m FROM MemberShop m WHERE m.memberlastname = :memberlastname")
    , @NamedQuery(name = "MemberShop.findByMemberaddress", query = "SELECT m FROM MemberShop m WHERE m.memberaddress = :memberaddress")})
public class MemberShop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Member_id")
    private Integer memberid;
    @Size(max = 18)
    @Column(name = "Member_username")
    private String memberusername;
    @Size(max = 18)
    @Column(name = "Member_password")
    private String memberpassword;
    @Size(max = 45)
    @Column(name = "Member_firstname")
    private String memberfirstname;
    @Size(max = 45)
    @Column(name = "Member_lastname")
    private String memberlastname;
    @Size(max = 100)
    @Column(name = "Member_address")
    private String memberaddress;
    @OneToMany(mappedBy = "shoppingBillmember")
    private List<ShoppingBill> shoppingBillList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCartmember")
    private List<ShoppingCart> shoppingCartList;

    public MemberShop() {
    }

    public MemberShop(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getMemberusername() {
        return memberusername;
    }

    public void setMemberusername(String memberusername) {
        this.memberusername = memberusername;
    }

    public String getMemberpassword() {
        return memberpassword;
    }

    public void setMemberpassword(String memberpassword) {
        this.memberpassword = memberpassword;
    }

    public String getMemberfirstname() {
        return memberfirstname;
    }

    public void setMemberfirstname(String memberfirstname) {
        this.memberfirstname = memberfirstname;
    }

    public String getMemberlastname() {
        return memberlastname;
    }

    public void setMemberlastname(String memberlastname) {
        this.memberlastname = memberlastname;
    }

    public String getMemberaddress() {
        return memberaddress;
    }

    public void setMemberaddress(String memberaddress) {
        this.memberaddress = memberaddress;
    }

    @XmlTransient
    public List<ShoppingBill> getShoppingBillList() {
        return shoppingBillList;
    }

    public void setShoppingBillList(List<ShoppingBill> shoppingBillList) {
        this.shoppingBillList = shoppingBillList;
    }

    @XmlTransient
    public List<ShoppingCart> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<ShoppingCart> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberid != null ? memberid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberShop)) {
            return false;
        }
        MemberShop other = (MemberShop) object;
        if ((this.memberid == null && other.memberid != null) || (this.memberid != null && !this.memberid.equals(other.memberid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "component.model.MemberShop[ memberid=" + memberid + " ]";
    }
    
}
