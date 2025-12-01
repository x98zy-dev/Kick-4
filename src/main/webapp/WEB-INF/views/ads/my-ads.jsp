<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Мои объявления</title>
    <style>
        .status-pending { color: orange; }
        .status-active { color: green; }
        .status-rejected { color: red; }
    </style>
</head>
<body>
    <h1>Мои объявления</h1>

    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/ads/create">Создать новое</a> |
        <a href="${pageContext.request.contextPath}/ads/list">Все объявления</a> |
        <a href="${pageContext.request.contextPath}/">На главную</a>
    </div>

    <c:if test="${empty advertisements}">
        <p>У вас пока нет объявлений.</p>
    </c:if>

    <c:forEach var="ad" items="${advertisements}">
        <div style="border: 1px solid #ddd; padding: 15px; margin-bottom: 15px; max-width: 600px;">
            <div style="font-size: 18px; font-weight: bold;">
                <a href="${pageContext.request.contextPath}/ads/view?id=${ad.id}">
                    ${ad.title}
                </a>
            </div>

            <div style="margin: 10px 0;">
                ${ad.description}
            </div>

            <div style="display: flex; justify-content: space-between; color: #666;">
                <div>
                    <c:if test="${ad.price != null}">
                        ${ad.price} руб. |
                    </c:if>
                    ${ad.city}
                </div>
                <div>
                    Статус:
                    <span class="status-${ad.status.name().toLowerCase()}">
                        ${ad.status}
                    </span>
                </div>
            </div>

            <div style="margin-top: 10px;">
                <a href="${pageContext.request.contextPath}/ads/edit?id=${ad.id}">Редактировать</a>
                <form action="${pageContext.request.contextPath}/ads/delete" method="post"
                      style="display: inline; margin-left: 10px;"
                      onsubmit="return confirm('Удалить это объявление?')">
                    <input type="hidden" name="id" value="${ad.id}">
                    <button type="submit" style="color: red;">Удалить</button>
                </form>
            </div>
        </div>
    </c:forEach>
</body>
</html>