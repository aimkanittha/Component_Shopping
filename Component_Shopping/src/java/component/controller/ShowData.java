/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package component.controller;

import component.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import component.dao.DvdDataTableLocal;
import component.dao.DvdDataTable;
import component.dao.ShoppingBillTable;
import component.dao.ShoppingBillTableLocal;
import java.util.List;
import javax.persistence.*;
import javax.servlet.http.HttpSession;
/**
 *
 * @author USER
 */
@WebServlet(urlPatterns = {"/showData"})
public class ShowData extends HttpServlet {
//    @EJB
    DvdDataTableLocal dvd;
    List<DvdData> dvd_list;
    ShoppingBillTableLocal bill;
    List<ShoppingBillDetail> bill_list;
        
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Component_ShoppingPU");
    EntityManager em = emf.createEntityManager();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        dvd = new DvdDataTable();
        dvd_list = dvd.getAllDvd();
        
        MemberShop member = (MemberShop)request.getSession().getAttribute("member");
        
        bill = new ShoppingBillTable();
        
        bill_list = bill.findAll();

//        List<DvdData> dvd_list = (List<DvdData>)em.createNamedQuery("DvdData.findAll").getResultList();

        try (PrintWriter out = response.getWriter()) {
        
        HttpSession session = request.getSession();
        session.setAttribute("dvdItems", dvd_list);
        session.setAttribute("billDetail", bill_list);
        
        }catch(Exception e){}
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
