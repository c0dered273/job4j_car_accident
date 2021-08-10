<%--
  Created by IntelliJ IDEA.
  User: c0dered
  Date: 10.08.2021
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create accident</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="mt-3 mb-3">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link " href="<c:url value="/"/>">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="#">Create new</a>
            </li>
        </ul>
    </div>
    <div class="mb-3">
        <form action="<c:url value="/save"/>" method="post">
            <div class="form-group mb-3">
                <label for="accidentName" class="form-label">Name</label>
                <input type="text" class="form-control" id="accidentName" name="name">
            </div>
            <div class="form-group mb-3">
                <label for="accidentDesc" class="form-label">Description</label>
                <textarea class="form-control" id="accidentDesc" rows="3" name="text"></textarea>
            </div>
            <div class="form-group mb-3">
                <label for="accidentAddress" class="form-label">Address</label>
                <textarea class="form-control" id="accidentAddress" rows="3" name="address"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
