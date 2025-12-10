<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Moderation Panel</title>
    <style>
        .ad-card { border: 1px solid #ddd; padding: 15px; margin-bottom: 15px; }
        .pending { border-left: 5px solid orange; }
    </style>
</head>
<body>
    <h1>Moderation Panel</h1>

    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/">Home</a>
    </div>

    <c:if test="${empty pendingAdvertisements}">
        <p>No advertisements pending moderation.</p>
    </c:if>

    <c:forEach var="ad" items="${pendingAdvertisements}">
        <div class="ad-card pending">
            <h3>${ad.title}</h3>
            <p>${ad.description}</p>
            <p><strong>Price:</strong> ${ad.price} руб.</p>
            <p><strong>City:</strong> ${ad.city}</p>
            <p><strong>Created:</strong> ${ad.createdAt}</p>

            <div style="margin-top: 10px;">
                <!-- Команда Approve -->
                <form action="${pageContext.request.contextPath}/admin" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="approve">
                    <input type="hidden" name="id" value="${ad.id}">
                    <button type="submit" style="background: green; color: white; padding: 5px 10px; border: none;">
                        Approve
                    </button>
                </form>

                <!-- Команда Reject -->
                <form action="${pageContext.request.contextPath}/admin" method="post" style="display: inline; margin-left: 10px;">
                    <input type="hidden" name="action" value="reject">
                    <input type="hidden" name="id" value="${ad.id}">
                    <button type="submit" style="background: red; color: white; padding: 5px 10px; border: none;">
                        Reject
                    </button>
                </form>

                <!-- Удаление (прямой вызов) -->
                <form action="${pageContext.request.contextPath}/admin" method="post" style="display: inline; margin-left: 10px;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${ad.id}">
                    <button type="submit" style="background: darkred; color: white; padding: 5px 10px; border: none;"
                            onclick="return confirm('Delete this advertisement?')">
                        Delete
                    </button>
                </form>
            </div>
        </div>
    </c:forEach>
</body>
</html>