<%-- 
    Document   : checkout
    Created on : Nov 17, 2016, 10:28:48 AM
    Author     : wachirapong
--%>

<%@page import="component.model.MemberShop"%>
<%@page import="component.model.ShoppingBillDetail"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout</title>
    </head>
    <body>
    <center><h1>Checkout</h1></center>
    <center>
    <% 
        List<ShoppingBillDetail> shoppingBill = (List<ShoppingBillDetail>) request.getSession().getAttribute("detailCheckout");
            int shoppingBillSize = shoppingBill.size();
            double sum =0;
            int quantity = 0;
            if(shoppingBillSize > 0){
               out.println("<center><h2> THANK YOU FOR YOUR PURCHASE </h2></center>");
               out.println("<center><h5> Item List </h5></center>");
               out.println("<table border='1'>");
               out.println("<tr><th>No.</th>");
               out.println("<th>DVD Id</th>");
               out.println("<th>DVD Name</th>");
               out.println("<th>Rate</th>");
               out.println("<th>Price</th>");
               out.println("<th>Quantity</th></tr>");
               for(int i=0; i < shoppingBillSize ;i++){
                        out.println("<tr>");
                        out.println("<td>");
                            out.println(i+1);
                        out.println("</td>");
                        out.println("<td>");
                            out.println(shoppingBill.get(i).getShoppingBillDetaildvdItem().getDvdDataid());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(shoppingBill.get(i).getShoppingBillDetaildvdItem().getDvdDataname());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(shoppingBill.get(i).getShoppingBillDetaildvdItem().getDvdDatacatalog().getCatalogtypeName());
                        out.println("</td>");
                  
                        out.println("<td>");
                            double tPrice = shoppingBill.get(i).getShoppingBillDetaildvdItem().getDvdDataprice()*shoppingBill.get(i).getShoppingBillDetaildvdQty();
                            sum+= tPrice;
                            quantity+=shoppingBill.get(i).getShoppingBillDetaildvdQty();
                            out.println( tPrice  );
                        out.println("</td>");
                        out.println("<td>");
                            out.println("<input type='text' name='Quautity"+i+"' value='"+shoppingBill.get(i).getShoppingBillDetaildvdQty()+"' readonly />");
                        out.println("</td>");
                        out.println("</tr>");
               }
               out.print("</table>");
               }
            out.print("<br>");
            out.print("<br>");
            out.print("<p> Total item quantity : "+quantity+"</p>");
            out.print("<p> Total price : "+sum+"</p>");
            
            MemberShop member = (MemberShop)request.getSession().getAttribute("member");
    %>
    </center>
        จัดส่งคุณ <%= member.getMemberfirstname() %>  <%=member.getMemberlastname()%> <br>
        ที่อยู่จัดส่ง <%= member.getMemberaddress() %>
        <center><a href="showData">Back</a></center>
    </body>
</html>
