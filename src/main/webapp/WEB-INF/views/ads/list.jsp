<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Все объявления</title>
    <style>
        .ads-container { display: flex; flex-wrap: wrap; gap: 20px; }
        .ad-card { border: 1px solid #ddd; padding: 15px; width: 300px; }
        .ad-title { font-size: 18px; font-weight: bold; margin-bottom: 10px; }
        .ad-price { color: green; font-weight: bold; }
        .ad-city { color: #666; }
        .ad-actions { margin-top: 10px; }
    </style>
</head>
<body>
    <h1>Все объявления</h1>

    <c:if test="${not empty sessionScope.user}">
        <div style="margin-bottom: 20px;">
            <a href="${pageContext.request.contextPath}/ads/create">Создать объявление</a> |
            <a href="${pageContext.request.contextPath}/ads/my">Мои объявления</a> |
            <a href="${pageContext.request.contextPath}/">На главную</a>
        </div>
    </c:if>

    <c:if test="${empty advertisements}">
        <p>Объявлений пока нет.</p>
    </c:if>

    <div class="ads-container">
        <c:forEach var="ad" items="${advertisements}">
            <div class="ad-card">
                <div class="ad-title">
                    <a href="${pageContext.request.contextPath}/ads/view?id=${ad.id}">
                        ${ad.title}
                    </a>
                </div>
                <div class="ad-description">
                    ${ad.description}
                </div>
                <c:if test="${ad.price != null}">
                    <div class="ad-price">${ad.price} руб.</div>
                </c:if>
                <div class="ad-city">${ad.city}</div>
                <div class="ad-date">
                    <small>${ad.createdAt}</small>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>