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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
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
    Random rand;
    // @EJB
    // private CatalogDAOLocal ICatalog;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        rand = new Random();
        int dvdId = Integer.parseInt((String) request.getParameter("action") );
        int qty = Integer.parseInt((String) request.getParameter("quantity"+dvdId) );
//        ShoppingBillJpaController spbjpa = new ShoppingBillJpaController(emf);
//        ShoppingBill spb = ShoppingBill();
        dvdJpa = new DvdDataJpaController(emf);
        scjpa = new ShoppingCartJpaController(emf);
        MemberShop member = ((MemberShop) request.getSession().getAttribute("member"));
        ShoppingCart scart;
        synchronized(request){
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            try{
                DvdData dvdData = em.find(DvdData.class, dvdId);
                System.out.println("TRY Success");
                if(dvdData.getDvdDataquantity()<qty){
                    response.sendRedirect("showData");
                    em.getTransaction().commit();
                    em.close();
                    return;
                }
                em.lock(dvdData, LockModeType.PESSIMISTIC_WRITE);
                em.persist(dvdData);
                if( scjpa.findMemberCart(em,member, dvdData)==null ){
                    scart = new ShoppingCart();
                    scart.setShoppingCartmember(member);
                    scart.setShoppingCartdvd(dvdJpa.findDvdData(dvdId));
                    scart.setShoppingCartdvQty(qty);
                    dvdData.setDvdDataquantity(dvdData.getDvdDataquantity()-qty);
                    em.persist(scart);
                }else{
                    scart = scjpa.findMemberCart(em,member, dvdData);
                    scart.setShoppingCartdvQty(scart.getShoppingCartdvQty()+qty);
                    dvdData.setDvdDataquantity(dvdData.getDvdDataquantity()-qty);
                    em.persist(scart);
                }
                em.getTransaction().commit();
            }catch( Exception e){
                System.out.println("TRY Success");
                em.getTransaction().rollback();
            }finally{
                em.close();
                response.sendRedirect("showData");
            }
            //
            //
//            DvdData dvdData = dvdJpa.findDvdData(dvdId);
//            if(dvdData.getDvdDataquantity()<qty){
//                response.sendRedirect("showData");
//                return;
//            }
//            if(scjpa.findMemberCart(member, dvdData)==null){
//                scart = new ShoppingCart();
//                scart.setShoppingCartmember(member);
//                scart.setShoppingCartdvd(dvdJpa.findDvdData(dvdId));
//                scart.setShoppingCartdvQty(qty);
//                dvdData.setDvdDataquantity(dvdData.getDvdDataquantity()-qty);
//                try {
//                    dvdJpa.edit(dvdData);
//                    scjpa.create(scart);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddtoShoppingCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }else{
//                scart = scjpa.findMemberCart(member, dvdData);
//                scart.setShoppingCartdvQty(scart.getShoppingCartdvQty()+qty);
//                dvdData.setDvdDataquantity(dvdData.getDvdDataquantity()-qty);
//                try {
//                    dvdJpa.edit(dvdData);
//                    scjpa.edit(scart);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddtoShoppingCartServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }
//        response.sendRedirect("showData");
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
