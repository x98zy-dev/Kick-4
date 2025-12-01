<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Создать объявление</title>
</head>
<body>
    <h1>Создать новое объявление</h1>

    <c:if test="${not empty error}">
        <div style="color: red; margin-bottom: 15px;">
            ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/ads/create" method="post">
        <div style="margin-bottom: 10px;">
            <label>Заголовок:</label><br>
            <input type="text" name="title" required style="width: 300px;">
        </div>

        <div style="margin-bottom: 10px;">
            <label>Описание:</label><br>
            <textarea name="description" rows="5" style="width: 300px;" required></textarea>
        </div>

        <div style="margin-bottom: 10px;">
            <label>Цена (руб.):</label><br>
            <input type="number" name="price" step="0.01" style="width: 150px;">
        </div>

        <div style="margin-bottom: 10px;">
            <label>Город:</label><br>
            <input type="text" name="city" required style="width: 200px;">
        </div>

        <div style="margin-bottom: 15px;">
            <button type="submit">Создать объявление</button>
            <a href="${pageContext.request.contextPath}/ads/list" style="margin-left: 10px;">Отмена</a>
        </div>
    </form>
</body>
</html>