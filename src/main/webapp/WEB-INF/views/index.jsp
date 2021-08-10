<%--
  Created by IntelliJ IDEA.
  User: okaschuk
  Date: 09.08.2021
  Time: 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Accident</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="mt-3 mb-3">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active" href="#">Home</a>
            </li>
        </ul>
    </div>
    <div class="mb-3">
        <div class="mb-4">
            <h4>Accidents</h4>
        </div>
        <div class="mb-2">
            <a class="btn btn-primary" role="button" href="<c:url value="/create"/>">Add new accident</a>
        </div>
        <table class="table caption-top">
            <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col">Text</th>
                <th scope="col">Address</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="accident" varStatus="loop" items="${accidents}">
                <tr>
                    <td><a href="<c:url value="/edit/${accident.id}"/>">edit</a></td>
                    <td><c:out value="${loop.index}" /></td>
                    <td><c:out value="${accident.name}" /></td>
                    <td><c:out value="${accident.text}" /></td>
                    <td><c:out value="${accident.address}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>