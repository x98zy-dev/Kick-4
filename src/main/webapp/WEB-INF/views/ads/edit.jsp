<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Редактировать объявление</title>
</head>
<body>
    <h1>Редактировать объявление</h1>

    <c:if test="${not empty error}">
        <div style="color: red; margin-bottom: 15px;">
            ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/ads/edit" method="post">
        <input type="hidden" name="id" value="${ad.id}">

        <div style="margin-bottom: 10px;">
            <label>Заголовок:</label><br>
            <input type="text" name="title" value="${ad.title}" required style="width: 300px;">
        </div>

        <div style="margin-bottom: 10px;">
            <label>Описание:</label><br>
            <textarea name="description" rows="5" style="width: 300px;" required>${ad.description}</textarea>
        </div>

        <div style="margin-bottom: 10px;">
            <label>Цена (руб.):</label><br>
            <input type="number" name="price" step="0.01" value="${ad.price}" style="width: 150px;">
        </div>

        <div style="margin-bottom: 10px;">
            <label>Город:</label><br>
            <input type="text" name="city" value="${ad.city}" required style="width: 200px;">
        </div>

        <div style="margin-bottom: 15px;">
            <button type="submit">Сохранить изменения</button>
            <a href="${pageContext.request.contextPath}/ads/view?id=${ad.id}" style="margin-left: 10px;">Отмена</a>
        </div>
    </form>
</body>
</html>