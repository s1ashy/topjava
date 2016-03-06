<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
    <style type="text/css">
        tr.exceeded td {
            color: #A00000;
        }
        tr.normal td {
            color: #00A000;
        }
    </style>
</head>
<body>
<a href="../">Back to index</a>
<h2>Meal list</h2>
<a href="new">Add new meal</a> | <a href="reset">Reset data</a>
<p>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.exceed ? "exceeded" : "normal"}">
            <td>${meal.id}</td>
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="edit?id=${meal.id}">Edit</a></td>
            <td><a href="delete?id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
