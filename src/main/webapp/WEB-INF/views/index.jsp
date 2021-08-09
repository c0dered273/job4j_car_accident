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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Item</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" varStatus="loop" items="${list}">
            <tr>
                <td>${loop.index}</td>
                <td>${item}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>