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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author wachirapong
 */
@Entity
@Table(name = "Catalog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catalog.findAll", query = "SELECT c FROM Catalog c")
    , @NamedQuery(name = "Catalog.findByCatelogseq", query = "SELECT c FROM Catalog c WHERE c.catelogseq = :catelogseq")
    , @NamedQuery(name = "Catalog.findByCatalogquantity", query = "SELECT c FROM Catalog c WHERE c.catalogquantity = :catalogquantity")
    , @NamedQuery(name = "Catalog.findByCatalogtypeName", query = "SELECT c FROM Catalog c WHERE c.catalogtypeName = :catalogtypeName")})
public class Catalog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "Catelog_seq")
    private Integer catelogseq;
    @Column(name = "Catalog_quantity")
    private Integer catalogquantity;
    @Column(name = "Catalog_typeName")
    private Integer catalogtypeName;
    @OneToMany(mappedBy = "dvdDatacatalog")
    private List<DvdData> dvdDataList;

    public Catalog() {
    }

    public Catalog(Integer catelogseq) {
        this.catelogseq = catelogseq;
    }

    public Integer getCatelogseq() {
        return catelogseq;
    }

    public void setCatelogseq(Integer catelogseq) {
        this.catelogseq = catelogseq;
    }

    public Integer getCatalogquantity() {
        return catalogquantity;
    }

    public void setCatalogquantity(Integer catalogquantity) {
        this.catalogquantity = catalogquantity;
    }

    public Integer getCatalogtypeName() {
        return catalogtypeName;
    }

    public void setCatalogtypeName(Integer catalogtypeName) {
        this.catalogtypeName = catalogtypeName;
    }

    @XmlTransient
    public List<DvdData> getDvdDataList() {
        return dvdDataList;
    }

    public void setDvdDataList(List<DvdData> dvdDataList) {
        this.dvdDataList = dvdDataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catelogseq != null ? catelogseq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catalog)) {
            return false;
        }
        Catalog other = (Catalog) object;
        if ((this.catelogseq == null && other.catelogseq != null) || (this.catelogseq != null && !this.catelogseq.equals(other.catelogseq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "component.model.Catalog[ catelogseq=" + catelogseq + " ]";
    }
    
}
