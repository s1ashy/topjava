<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.11/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="webjars/datetimepicker/2.4.5/jquery.datetimepicker.css">

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <%--http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page--%>
            <h3><a href="${pageContext.request.contextPath}">Home</a></h3>
            <h3><fmt:message key="meals.title"/></h3>
            <div class="view-box">

                <form method="post" id="filter">
                    <dl>
                        <dt>From Date:</dt>
                        <dd><input name="startDate" value="${startDate}"></dd>
                    </dl>
                    <dl>
                        <dt>To Date:</dt>
                        <dd><input name="endDate" value="${endDate}"></dd>
                    </dl>
                    <dl>
                        <dt>From Time:</dt>
                        <dd><input name="startTime" value="${startTime}"></dd>
                    </dl>
                    <dl>
                        <dt>To Time:</dt>
                        <dd><input name="endTime" value="${endTime}"></dd>
                    </dl>
                    <button class="btn btn-primary" type="submit">Filter</button>
                </form>
                <hr>
                <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.add"/></a>
                <hr>
                <table border="1" cellpadding="8" cellspacing="0" id="datatable">
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
                        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                        <tr class="${meal.exceed ? 'exceeded' : 'normal'}" id="${meal.id}">
                            <td>
                                    <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%=TimeUtil.toString(meal.getDateTime())%>
                            </td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td><a class="btn btn-xs btn-primary edit">Update</a></td>
                            <td><a class="btn btn-xs btn-danger delete">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.edit"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" name="id" id="id">

                    <div class="form-group">
                        <label for="date" class="control-label col-xs-3">Date</label>
                        <div class="col-xs-9">
                            <input class="form-control" id="date" name="date">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" placeholder="Description">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">Calories</label>
                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories" placeholder="Calories">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript" src="webjars/jquery/2.2.3/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datetimepicker/2.4.5/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.11/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">

    var ajaxUrl = 'ajax/profile/meals';
    var datatableApi;

    $(function () {
        var $filterForm = $('#filter');

        $(".modal").find("#date").datetimepicker({
            format: "Y-m-d\\TH:i"
        });
        $filterForm.find('input[name$=Date]').datetimepicker({
            timepicker: false,
            format: "Y-m-d"
        });
        $filterForm.find('input[name$=Time]').datetimepicker({
            datepicker: false,
            format: "H:i"
        });

        datatableApi = $('#datatable').DataTable({
            "paginate": false,
            "info": false,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "sortable": false
                },
                {
                    "defaultContent": "",
                    "sortable": false
                }
            ],
            "sorting": [
                [
                    0,
                    "desc"
                ]
            ]
        });
        makeEditable();
        makeFilterable($filterForm);
    });
</script>
</html>