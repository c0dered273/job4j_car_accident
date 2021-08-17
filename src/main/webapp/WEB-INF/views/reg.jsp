<%--
  Created by IntelliJ IDEA.
  User: c0dered
  Date: 16.08.2021
  Time: 22:33
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
    <div class="card mx-auto mt-3" style="width: 20rem;">
        <div class="mb-3">
            <p>New user</p>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <c:if test="${not empty errorMessage}">
                    <div style="color:red; font-weight: bold; margin: 30px 0px;">
                            ${errorMessage}
                    </div>
                </c:if>
            </div>
            <div class="mb-3">
                <form name='login' action="<c:url value='/reg'/>" method="post">
                    <div class=mb-3>
                        <label for="inputLogin" class="form-label">Login</label>
                        <input type="text" class="form-control" id="inputLogin" name='username'>
                    </div>
                    <div class="mb-3">
                        <label for="inputPassword" class="form-label">Password</label>
                        <input type="password" class="form-control" id="inputPassword" name="password">
                    </div>
                    <div class="mb-3">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>