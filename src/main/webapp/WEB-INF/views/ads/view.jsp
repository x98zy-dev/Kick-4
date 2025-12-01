<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${ad.title}</title>
</head>
<body>
    <h1>${ad.title}</h1>

    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/ads/list">← Все объявления</a>
    </div>

    <div style="margin-bottom: 20px; padding: 15px; border: 1px solid #ddd; max-width: 600px;">
        <div style="margin-bottom: 10px;">
            <strong>Описание:</strong><br>
            ${ad.description}
        </div>

        <c:if test="${ad.price != null}">
            <div style="margin-bottom: 10px;">
                <strong>Цена:</strong> ${ad.price} руб.
            </div>
        </c:if>

        <div style="margin-bottom: 10px;">
            <strong>Город:</strong> ${ad.city}
        </div>

        <div style="margin-bottom: 10px;">
            <strong>Статус:</strong> ${ad.status}
        </div>

        <div style="margin-bottom: 10px;">
            <strong>Создано:</strong> ${ad.createdAt}
        </div>

        <c:if test="${canEdit}">
            <div style="margin-top: 20px;">
                <a href="${pageContext.request.contextPath}/ads/edit?id=${ad.id}">Редактировать</a>

                <form action="${pageContext.request.contextPath}/ads/delete" method="post"
                      style="display: inline; margin-left: 10px;"
                      onsubmit="return confirm('Удалить это объявление?')">
                    <input type="hidden" name="id" value="${ad.id}">
                    <button type="submit" style="color: red;">Удалить</button>
                </form>
            </div>
        </c:if>
    </div>
</body>
</html>