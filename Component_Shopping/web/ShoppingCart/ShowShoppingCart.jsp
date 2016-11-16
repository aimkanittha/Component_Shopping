<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ShowShoppingCart</title>
    </head>
    <body>
<!--        <form action="addItem" method="POST">
            <table>
                <tr>
                    <td>Product Name</td>
                    <td><input type="text" name="prodId"></td>
                </tr>
                <tr>
                    <td>Qty</td>
                    <td><input type="number" name="prodQty" value="0"></td>
                </tr>
                <tr>
                    <td><a href="addItem"><input type="submit" name="addSubmit"></a></td>
                </tr>
            </table>
        </form>-->
        <form action="showData" method="POST">
            <table border="1">
                <c:forEach items="dvdItems" var="dvdItem">
                    <tr>
                        <td>${dvdItem.dvdDataid}</td>
                        <td>${dvdItem.dvdDataname}</td>
                        <td>${dvdItem.dvdDataprice}</td>
                        <td>${dvdItem.dvdDatayear}</td>
                        <td>${dvdItem.dvdDataquantity}</td>
                        <td>${dvdItem.dvdDatacatalog.catalogtypeName}</td>
                    <tr>
                </c:forEach>    
            </table>
        </form>
                    
                    <a href="showData">555555555555555555555555</a>
    </body>
</html>
