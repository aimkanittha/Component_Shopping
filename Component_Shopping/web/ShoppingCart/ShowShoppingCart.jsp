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
            <center><h1>Welcom to Shopping DVD</h1></center>
            <center><h2>DVD Catalog</h2></center>
            <center>
            <table border="1">
                <tr>
                    <th>DVD Id</th>
                    <th>DVD Name</th>
                    <th>Rate</th> 
                    <th>Year</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Add Card</th>
                </tr>
               <%
                   try{
                       Thread.sleep(1000);
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
                            out.println(list.get(i).getDvdDataprice());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(list.get(i).getDvdDataquantity());
                        out.println("</td>");
                        out.println("<td>");
                            out.println(list.get(i).getDvdDatayear());
                        out.println("</td>");
                        out.println("<td>");
                            out.println("<input type='number' name='Quautity"+i+"' value='0'  />");
                        out.println("</td>");
                        out.println("<td>");
                            out.println("<button style='width:100%' type='submit' name='action' onclick=\"form.action='addItem';\" value='"+i+"'>AddToCard </button>");
                        out.println("</td>");
                        out.println("</tr>");
                   } 
                   
                   }
                    catch(Exception e){out.println(e);}
               %>
            </table>
            </center>    
        
        </form>
        
    </body>
</html>
