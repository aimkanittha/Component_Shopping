/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author USER
 */
public class AddtoShoppingCartServlet extends HttpServlet {
    private static String STOCK_QOH = "stockQoh"; // Quantity On Hand in Stock/Catalog
    private static String ADDING_ITEM = "addQty"; // USER INPUT
    private static String USER_QOH = "cartQoh"; // USER SHOPPING CART'S TOTAL QUANTITY
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String stockQOH_Str = request.getParameter(STOCK_QOH); // from model
            String addQty_Str = request.getParameter(ADDING_ITEM); // from user input
            String cartQOH_Str = request.getParameter(USER_QOH);  // from model
            
            HttpSession session = request.getSession();
            synchronized(getServletContext()) {
            //synchronized(session) {
//            
//                GET DATA FROM MODEL
            Integer stockQOH = (Integer) getServletContext().getAttribute(STOCK_QOH);
//                GET DATA FROM USER INPUT
            Integer addQty = (Integer) request.getAttribute(ADDING_ITEM);
            //session.setAttribute("foo", Integer.parseInt(fooStr));
            
            Random rand = new Random();
            
            getServletContext().setAttribute(STOCK_QOH, (stockQOH - addQty) <= 0 ? 0: stockQOH - addQty);
            
            Thread.sleep((rand.nextInt(10)+1) * 1000);
            
            getServletContext().setAttribute(USER_QOH, USER_QOH + addQty);
            
//            getServletContext().setAttribxute("bar", Integer.parseInt(barStr));
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddtoShoppingCartServlet</title>");   
            out.println("<h4>555555555555555555555555555555555555555555555555555555555555</h4>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddtoShoppingCartServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (InterruptedException e) {
            
        }
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
