<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal Form</title>
    <style type="text/css">
        td.tip {
            color: darkgray;
        }
    </style>
</head>
<body>
<h2>Meal Form</h2>
<form method="post" action="${action}">
    <input type="hidden" id="id" name="id" value="${meal.id}">
    <table>
        <tr>
            <td><label for="dateTime">DateTime: </label></td>
            <td><input type="text" id="dateTime" name="dateTime" value="${meal.dateTime}"/></td>
            <td class="tip">format: 2007-12-03T10:15:30</td>
        </tr>
        <tr>
            <td><label for="description">Description: </label></td>
            <td><input type="text" id="description" name="description" value="${meal.description}"/></td>
        </tr>
        <tr>
            <td><label for="calories">Calories: </label></td>
            <td><input type=text id="calories" name="calories" value="${meal.calories}"/></td>
            <td class="tip">integer</td>
        </tr>
        <tr>
            <td colspan="3">
                <c:choose>
                    <c:when test="${'edit'.equals(action)}">
                        <input type="submit" value="Update"/>
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="Add"/>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</form>
<br/>
<br/>
<p>Go back to <a href="list">List of All Users</a>
</body>
</html>
