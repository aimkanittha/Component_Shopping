/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.controller;

import component.ConstantsCtrl;
import component.dao.ShoppingBillTable;
import component.dao.ShoppingBillTableLocal;
import component.jpa.DvdDataJpaController;
import component.jpa.ShoppingCartJpaController;
import component.jpa.exceptions.NonexistentEntityException;
import component.model.DvdData;
import component.model.MemberShop;
import component.model.ShoppingBillDetail;
import component.model.ShoppingCart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author USER
 */
public class RemoveItemServlet extends HttpServlet {
    ShoppingBillTableLocal sbt;
    DvdDataJpaController dvdJpa;
    ShoppingCartJpaController scjpa;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Component_ShoppingPU");
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        MemberShop member = ((MemberShop) request.getSession().getAttribute("member"));
        int dvdId = Integer.parseInt((String) request.getParameter("remove") );
        dvdJpa = new DvdDataJpaController(emf);
        DvdData dvd = dvdJpa.findDvdData(dvdId);
        scjpa = new ShoppingCartJpaController(emf);
        ShoppingCart sc = scjpa.findMemberCart(member, dvd);
        if( sc.getShoppingCartdvQty() > 1 ){
            sc.setShoppingCartdvQty(sc.getShoppingCartdvQty()-1);
            dvd.setDvdDataquantity(dvd.getDvdDataquantity()+1);
            try {
                scjpa.edit(sc);
                dvdJpa.edit(dvd);
            } catch (Exception ex) {
                Logger.getLogger(RemoveItemServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            dvd.setDvdDataquantity(dvd.getDvdDataquantity()+1);
            try {
                scjpa.destroy(sc.getShoppingCartid());
                dvdJpa.edit(dvd);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RemoveItemServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RemoveItemServlet.class.getName()).log(Level.SEVERE, null, ex);
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
