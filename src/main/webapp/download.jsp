<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Download</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
    <%
        Object role = session.getAttribute("role");
        request.setAttribute("role", role);
    %>
    <c:choose>
        <c:when test="${role == GUEST}">
            <a href="login.jsp">XML</a>
        </c:when>
        <c:otherwise>
            <a href="download/private.xml">XML</a>
        </c:otherwise>
    </c:choose>

    <a href="download/public.txt">TEXT</a>
</body>
</html>