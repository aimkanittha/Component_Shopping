/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component.controller;

import component.jpa.MemberShopJpaController;
import component.jpa.ShoppingBillDetailJpaController;
import component.jpa.ShoppingBillJpaController;
import component.jpa.ShoppingCartJpaController;
import component.jpa.exceptions.NonexistentEntityException;
import component.model.MemberShop;
import component.model.ShoppingBill;
import component.model.ShoppingBillDetail;
import component.model.ShoppingCart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class CheckOutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    ShoppingCartJpaController scjpa;
    ShoppingBillJpaController sbjpa;
    ShoppingBillDetailJpaController sbdjpa;
    MemberShopJpaController msjc;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Component_ShoppingPU");
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        msjc = new MemberShopJpaController(emf);
        scjpa = new ShoppingCartJpaController(emf);
        sbjpa = new ShoppingBillJpaController(emf);
        sbdjpa = new ShoppingBillDetailJpaController(emf);
        MemberShop member = ((MemberShop) request.getSession().getAttribute("member"));
        List<ShoppingCart> sc = scjpa.getMemberCart(member);
        List<ShoppingBillDetail> sbdList = new ArrayList<ShoppingBillDetail>();
        ShoppingBill sb = new ShoppingBill();
        sb.setShoppingBillmember(member);
        sb.setShoppingBillid(sbjpa.findShoppingBillEntities().size()+1);
        for( ShoppingCart sc1 : sc ){
            ShoppingBillDetail sbd = new ShoppingBillDetail();
            sbd.setShoppingBillDetailbill(sb);
            sbd.setShoppingBillDetailseq(sbdjpa.findShoppingBillDetailEntities().size()+1);
            sbd.setShoppingBillDetaildvdItem(sc1.getShoppingCartdvd());
            sbd.setShoppingBillDetaildvdQty(sc1.getShoppingCartdvQty());
            try {
                scjpa.destroy(sc1.getShoppingCartid());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        request.getRequestDispatcher("ShoppingCart/checkout.jsp").forward(request, response);
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
