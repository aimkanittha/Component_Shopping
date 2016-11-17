/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import component.model.*;

import component.ConstantsCtrl;
import component.dao.ShoppingBillTableLocal;
import component.jpa.DvdDataJpaController;
import component.jpa.ShoppingBillJpaController;
import component.jpa.ShoppingCartJpaController;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import component.model.Catalog;
//import javax.ejb.EJB;
/**
 *
 * @author USER
 */
public class AddtoShoppingCartServlet extends HttpServlet {
    ShoppingBillTableLocal sbt;
    DvdDataJpaController dvdJpa;
    ShoppingCartJpaController scjpa;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Component_ShoppingPU");
    // @EJB
    // private CatalogDAOLocal ICatalog;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int dvdId = 1789562210;//Integer.parseInt( request.getParameter("addDvdId") );
        int qty = 3;
//        ShoppingBillJpaController spbjpa = new ShoppingBillJpaController(emf);
//        ShoppingBill spb = ShoppingBill();
        dvdJpa = new DvdDataJpaController(emf);
        scjpa = new ShoppingCartJpaController(emf);
        MemberShop member = ((MemberShop) request.getSession().getAttribute("member"));
        ShoppingCart scart;
//        scart.setShoppingCartid(member.getMemberid());
//        scart.setShoppingCartmember(member);
//        scart.setShoppingCartdvd(dvdJpa.findDvdData(dvdId));
//        scart = scjpa.findShoppingCart(member.getMemberid());
        DvdData dvdData = dvdJpa.findDvdData(dvdId);
        synchronized(request.getSession()){
            if(dvdData.getDvdDataquantity()<qty){
                response.sendRedirect("showData");
                return;
            }
            if(scjpa.findMemberCart(member, dvdData)==null){
                scart = new ShoppingCart();
                scart.setShoppingCartid(member.getMemberid());
                scart.setShoppingCartmember(member);
                scart.setShoppingCartdvd(dvdJpa.findDvdData(dvdId));
                scart.setShoppingCartdvQty(qty);
                dvdData.setDvdDataquantity(dvdData.getDvdDataquantity()-qty);
                try {
                    dvdJpa.edit(dvdData);
                    scjpa.create(scart);
                } catch (Exception ex) {
                    Logger.getLogger(AddtoShoppingCartServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                scart = scjpa.findShoppingCart(member.getMemberid());
                scart.setShoppingCartdvQty(qty);
                dvdData.setDvdDataquantity(dvdData.getDvdDataquantity()-qty);
                try {
                    dvdJpa.edit(dvdData);
                    scjpa.edit(scart);
                } catch (Exception ex) {
                    Logger.getLogger(AddtoShoppingCartServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            scjpa = new ShoppingCartJpaController(emf);
            
            List<DvdData> dvdList = new ArrayList<DvdData>();
            DvdData dvd = dvdJpa.findDvdData(dvdId);
            if( dvd.getDvdDataquantity()!=0 ){
                dvdList.add(dvd);
            }
        }
        response.sendRedirect("showData");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
