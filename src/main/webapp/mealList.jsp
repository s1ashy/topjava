<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }

        header h2 {display: inline}
        header span {margin-left: 50px}
        header form {display: inline}
    </style>
</head>
<body>
<section>
    <header>
        <h2><a href="index.html">Home</a></h2>
        <span>
            <form action="meals" method="POST">
                <label for="userSelect">Log in as user: </label>
                <select id="userSelect" name="selectedLoggedUser" onchange="this.form.submit()">
                    <jsp:useBean id="userList" scope="request"
                                 type="java.util.List<ru.javawebinar.topjava.model.User>"/>
                    <jsp:useBean id="loggedUserId" scope="request" type="java.lang.Integer"/>

                    <c:forEach items="${userList}" var="user">
                        <option value="${user.id}" ${user.id == loggedUserId ? 'selected' : ''}>
                        ${user.id} - ${user.name}
                        </option>
                    </c:forEach>
                </select>
            </form>
        </span>
    </header>
    <h3>Meal list</h3>

    <form method="post" action="meals">
        <div>
            <dl>
                <dt>From Date:</dt>
                <dd><input type="datetime-local" value="${fromDateTime}" name="fromDateTime"></dd>
            </dl>
        </div>
        <div>
            <dl>
                <dt>To Date:</dt>
                <dd><input type="datetime-local" value="${toDateTime}" name="toDateTime"></dd>
            </dl>
        </div>
        <button type="submit" name="filter" style="display: inline">Filter</button>
        <c:if test="${showResetLink}">
            <a href="meals">Show all meals</a>
        </c:if>
    </form>

    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.to.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        ${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>