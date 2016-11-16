<%-- 
    Document   : ShowShoppingCart
    Created on : Nov 16, 2016, 9:22:16 AM
    Author     : USER
--%>

<%@page import="component.model.MemberShop"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ShowShoppingCart</title>
    </head>
    <body>
        <%
            MemberShop ms = ((MemberShop) request.getSession().getAttribute("member"));
            out.print("Hello   "+ms.getMemberfirstname()+"    ");
            out.print(ms.getMemberlastname());
        %>
        <!--<a href=""></a>-->
        <form action="/addItem" method="POST">
            <table>
                <tr>
                    <td>Product Name</td>
                    <td><input type="text" name="prodName"></td>
                </tr>
                <tr>
                    <td>Qty</td>
                    <td><input type="number" name="prodQty" value="0"></td>
                </tr>
                <tr>
                    <td><a href="addItem"><input type="submit" name="addSubmit"></a></td>
                </tr>
            </table>
        </form>
        <form action="/showData" method="POST">
            <table border="1">
                <c:forEach items="dvdItems" var="dvdItem">
                    <tr>
                        <td>aaaaaaaaaaa${dvdItem.dvdDataid}</td>
                        <td>aaaaaaaaaa${dvdItem.dvdDataname}</td>
                        <td>aaaaaaaaaaaaaaaaa${dvdItem.dvdDataprice}</td>
                        <td>aaaaaaaaaaaaaa${dvdItem.dvdDatayear}</td>
                        <td>aaaaaaaaa${dvdItem.dvdDataquantity}</td>
                        <td>aaaaaaaaaa${dvdItem.dvdDatacatalog}</td>
                        <td>aaaaaaaaaaa${dvdItem.catalogtypeName}</td>
                    <tr>
                </c:forEach>    
            </table>
        </form>
    </body>
</html>
