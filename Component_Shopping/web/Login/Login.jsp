<%-- 
    Document   : Login
    Created on : Nov 17, 2016, 3:38:52 AM
    Author     : wachirapong
--%>
<%@include file="../index.html" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <% response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
        response.setHeader("Pragma","no-cache"); //HTTP 1.0 
        response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="LoginController" method="post">
            Username : <input type="text" name="username" value="toeytoey"/><br>
            Password : <input type="password" name="password" value="toey1879"/><br>
            <input type="submit"/><br>
        </form>
        <%
            if(request.getSession().getAttribute("userinvalid") != null && (Integer) (request.getSession().getAttribute("userinvalid")) ==1)
                out.print("Username Or Password is incorrect! ");
            %>
    </body>
</html>
