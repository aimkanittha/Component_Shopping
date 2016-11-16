<%-- 
    Document   : ShowShoppingCart
    Created on : Nov 16, 2016, 9:22:16 AM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ShowShoppingCart</title>
    </head>
    <body>
        <form action="addItem" method="POST">
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
<!--        <table border="1">
            <c:forEach items="DVD" var="dvdItem">
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                <tr>
            </c:forEach>    
        </table>-->
    </body>
</html>
