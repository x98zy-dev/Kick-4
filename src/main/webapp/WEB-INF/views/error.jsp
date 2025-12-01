<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Oops! Something went wrong</h1>

    <c:if test="${not empty error}">
        <p><strong>Error:</strong> ${error}</p>
    </c:if>

    <c:if test="${not empty exception}">
        <p><strong>Exception:</strong> ${exception.message}</p>
    </c:if>

    <a href="${pageContext.request.contextPath}/">Go to Home</a>
</body>
</html>