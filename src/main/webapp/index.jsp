<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>

<div class="form-style-5">

    <h2>LOG IN</h2>

    <form action="CheckLogin" method="POST">
        Username: <input type="text" name="username" required>
        Password: <input type="password" name="password" required>

        <input type="submit" value="Login">
<%--        <p th:if="${loginErrorMsg} != null" th:text="${loginErrorMsg}"></p>--%>
    </form>
</div>
</html>