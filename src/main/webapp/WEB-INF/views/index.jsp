<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Advertisement System</title>
</head>
<body>
    <h1>Advertisement System</h1>

    <c:if test="${not empty sessionScope.user}">
        <p>Welcome, ${sessionScope.user.username}!</p>
        <form action="logout" method="post">
            <button type="submit">Logout</button>
        </form>
    </c:if>

    <c:if test="${empty sessionScope.user}">
        <a href="login">Login</a>
    </c:if>
</body>
</html>