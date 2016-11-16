/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author USER
 */
@Entity
@Table
public class Catalog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private static int seqID;
    @Column
    private static String dvdRate;
    @Column
    private static int stockQty;

    public static int getSeqId(){
        return seqID;
    }
    
    public static String getDvdRate() {
        return dvdRate;
    }

    public static void setDvdRate(String dvdRate) {
        Catalog.dvdRate = dvdRate;
    }

    public static int getStockQty() {
        return stockQty;
    }

    public static void setStockQty(int stockQty) {
        Catalog.stockQty = stockQty;
    }
    
}
