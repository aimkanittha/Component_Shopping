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

import component.ConstantsCtrl;
//import component.model.Catalog;
//import javax.ejb.EJB;
/**
 *
 * @author USER
 */
public class AddtoShoppingCartServlet extends HttpServlet {

    // @EJB
    // private CatalogDAOLocal ICatalog;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String prod_id = request.getParameter(ConstantsCtrl.PRODUCT_ID);
//            Integer prefixIndex = (Integer) getServletContext().getAttribute(ConstantsCtrl.PREFIX_INDEX);
            
            String prod_QtyStr = request.getParameter(ConstantsCtrl.PRODUCT_QTY);
            Integer prod_Qty = Integer.parseInt(prod_QtyStr == "" ? "0" : prod_QtyStr);
            boolean isEmpty = false;
            
            
            int stock_Qoh, cart_Qoh, newStock_Qoh;
            // DVD dvdObj = new DVD();
            // CART cartObj = new CART();
//            HttpSession session = request.getSession();
            Random rand = new Random();
            
            synchronized(getServletContext()) {
            
            stock_Qoh = 0;// DVD.getStockQoh()
            cart_Qoh = 0; // ShoppingCart.getCartQoh
            
            if((stock_Qoh - prod_Qty) < 0) {
                isEmpty = true;
            }
            newStock_Qoh = (stock_Qoh - prod_Qty) <= 0 ? 0: stock_Qoh - prod_Qty; // For check warning
            
            
//             getServletContext().setAttribute(ConstantsCtrl.PRODUCT_NAME, "5555555555555555");
            Thread.sleep((rand.nextInt(10)+1) * 500);
//             getServletContext().setAttribute(ConstantsCtrl.PRODUCT_QTY, 5);
            //dvdObj.setStock_Qoh() 
            //getServletContext().setAttribute(ConstantsCtrl.DVD, dvdObj);
            
//            Thread.sleep((rand.nextInt(10)+1) * 500);
            
            // SHOW CART
            //getServletContext().setAttribute(ConstantsCtrl.CART, cartObj.getTotalQty);
            }
                        
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<title>Servlet CheckOutServlet</title>");            
            out.println("</head>");
            out.println("<body>");
//            out.println(prefixIndex + "<br>");
            out.println("<h1>Servlet CheckOutServlet at " + prod_id + prod_Qty + "    =>stock " + newStock_Qoh+ "</h1>");
            out.println("<h1>USER INPUT " + prod_id + " " + prod_Qty + "    =>stock " + newStock_Qoh+ "</h1>");
            out.println(getServletContext().getAttribute(ConstantsCtrl.PRODUCT_ID)+"<br>");
            out.println(getServletContext().getAttribute(ConstantsCtrl.PRODUCT_QTY));
            out.println("</body>");
            out.println("</html>");

//            request.getRequestDispatcher(ConstantsCtrl.ShowShoppingCart_JSP).forward(request, response);
        }catch(Exception e) {}
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
