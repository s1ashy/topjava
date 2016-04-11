<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><a href="<c:url value="/"/>"><fmt:message key="app.home"/></a></h2>
    <h3><fmt:message key="meals.list"/> </h3>
    <form method="post" action="meals?action=filter">
        <dl>
            <dt><fmt:message key="meals.fromDate"/></dt>
            <dd><input type="date" name="startDate" value="${startDate}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="meals.toDate"/></dt>
            <dd><input type="date" name="endDate" value="${endDate}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="meals.fromTime"/></dt>
            <dd><input type="time" name="startTime" value="${startTime}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="meals.toTime"/></dt>
            <dd><input type="time" name="endTime" value="${endTime}"></dd>
        </dl>
        <button type="submit"><fmt:message key="meals.filter"/></button>
    </form>
    <hr>
    <a href="meals?action=create"><fmt:message key="meals.add"/></a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><fmt:message key="app.date"/></th>
            <th><fmt:message key="meals.description"/> </th>
            <th><fmt:message key="meals.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%=TimeUtil.toString(meal.getDateTime())%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}"><fmt:message key="app.update"/></a></td>
                <td><a href="meals?action=delete&id=${meal.id}"><fmt:message key="app.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
