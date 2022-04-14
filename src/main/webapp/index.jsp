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

    <h2>LOG IN EMPLOYEE</h2>

    <form action="CheckLogin" method="POST">
        Username: <input type="text" name="username" required>
        Password: <input type="text" name="password" required>

        <input type="submit" value="Login">

<%--        <p th:if="${loginErrorMsg} != null" th:text="${loginErrorMsg}"></p>--%>
    </form>

    <h2>LOG IN CUSTOMER</h2>

    <form action="CheckLoginCustomer" method="POST">
        Username: <input type="text" name="username" required>
        Password: <input type="text" name="password" required>

        <input type="submit" value="Login">

        <%--        <p th:if="${loginErrorMsg} != null" th:text="${loginErrorMsg}"></p>--%>
    </form>
</div>
</html>