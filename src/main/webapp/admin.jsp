<%@ page import="java.util.ArrayList" %>
<%@ page import="model.User" %>
<%@ page import="repo.UserDAO" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>ADMIN</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
    <%
        Object role = request.getParameter("role");
        session.setAttribute("role", role);
        ServletContext sc = request.getServletContext();
        AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) sc.getAttribute("dao");
        ArrayList usrs = dao.get().getAllUsers();
        request.setAttribute("users", usrs);
    %>

    <c:forEach var="user" items="${users}">
        <c:out value="${user.name}"/>
        <c:out value="${user.role}"/></p>

        <form method="post" action="adminController">
            <label for="role">To admin</label>
            <input type="radio" name="role" value="admin"><br>
            <label for="role">To user</label>
            <input type="radio" name="role" value="user"><br>
            <input type="hidden"  name="username" value="<c:out value="${user.name}"/>">
            <button type="submit">Submit</button><br>
        </form>
    </c:forEach>

    <c:choose>
         <c:when test="${role == ADMIN}">
                 <h1>Hello ADMIN!</h1>
                 <a href="logout">Logout</a>
            </c:when>
            <c:otherwise>
               <%
                   response.sendRedirect("download.jsp");
                %>
         </c:otherwise>
    </c:choose>
</body>
</html>
