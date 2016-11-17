<%-- 
    Document   : ShowShoppingCart
    Created on : Nov 16, 2016, 9:22:16 AM
    Author     : USER
--%>

<%@page import="java.util.List"%>
<%@page import="component.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ShowShoppingCart</title>
        <style>
            table{
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
        </style>
    </head>
    <body>
       
        <form action="showData" method="POST">
            <center><h1>Welcome to Shopping DVD</h1></center>
            <center><h2>DVD Catalog</h2></center>
            <center>
            <table border="1">
                <tr>
                    <th>DVD Id</th>
                    <th>DVD Name</th>
                    <th>Rate</th> 
                    <th>Year</th>
                    <th>Price</th>
                    <th>Quantity On Hand</th>
                    <th>Quantity</th>
                    <th>Add Card</th>
                </tr>
               <%
                   try{
                       Thread.sleep(500);
                   List<DvdData> list = (List<DvdData>) request.getSession().getAttribute("dvdItems");
                   
                   for(int i=0;i<list.size();i++){
                        out.println("<tr>");
                        out.println("<td>");
                            out.println(list.get(i).getDvdDataid());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(list.get(i).getDvdDataname());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(list.get(i).getDvdDatacatalog().getCatalogtypeName());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(list.get(i).getDvdDatayear());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(list.get(i).getDvdDataprice());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(list.get(i).getDvdDataquantity());
                        out.println("</td>");
                        out.println("<td>");
                            out.println("<input type='number' name='Quautity"+list.get(i).getDvdDataid()+"' value='1' min='1' max='100'  />");
                        out.println("</td>");
                        out.println("<td>");
                            out.println("<button style='width:100%' type='submit' name='action' onclick=\"form.action='addItem';\" value='"+list.get(i).getDvdDataid()+"'>AddToCard </button>");
                        out.println("</td>");
                        out.println("</tr>");
                   } 
                   
                   }
                    catch(Exception e){out.println(e);}
               %>
            </table>
            </center>    
        
<center>
            
            <%
            List<ShoppingCart> listShop = (List<ShoppingCart>) request.getSession().getAttribute("billDetail");
            int listSize = listShop.size();
            if(listSize > 0){
               out.println("<center><h2> CART </h2></center>");
               out.println("<table border='1'>");
               out.println("<tr><th>No.</th>");
               out.println("<th>DVD Id</th>");
               out.println("<th>DVD Name</th>");
               out.println("<th>Rate</th>");
               out.println("<th>Year</th>");
               out.println("<th>Price</th>");
               out.println("<th>Quantity</th>");
               out.println("<th>Remove Card</th></tr>");
               for(int i=0; i < listSize ;i++){
                        out.println("<tr>");
                        out.println("<td>");
                            out.println(i+1);
                        out.println("</td>");
                        out.println("<td>");
                            out.println(listShop.get(i).getShoppingCartdvd().getDvdDataid());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(listShop.get(i).getShoppingCartdvd().getDvdDataname());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(listShop.get(i).getShoppingCartdvd().getDvdDatacatalog().getCatalogtypeName());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(listShop.get(i).getShoppingCartdvd().getDvdDatayear());
                        out.println("</td>");
                        out.println("<td>");
                            out.println( listShop.get(i).getShoppingCartdvd().getDvdDataprice()*listShop.get(i).getShoppingCartdvQty()  );
                        out.println("</td>");
                        out.println("<td>");
                            out.println("<input type='text' name='Quautity"+i+"' value='"+listShop.get(i).getShoppingCartdvQty()+"' readonly />");
                        out.println("</td>");
                        out.println("<td>");
                            out.println("<button style='width:100%' type='submit' name='remove' value='"+listShop.get(i).getShoppingCartdvd().getDvdDataid()+"' onclick=\"form.action='removeItem';\" value='"+listShop.get(i).getShoppingCartdvd()+"'>Remove</button>");
                        out.println("</td>");
                        out.println("</tr>");
               }
               out.print("</table>");
               } 
            %>
        </center>    
            
        </form>
        
    <br><br><br>
    <center><a href="checkOut">Check out</a></center>
        
    </body>
</html>
