<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body {
            display: flex;
            flex-direction: column; /* Вертикальное выравнивание */
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f1f2f2;
            margin: 0;
            font-family: Arial, sans-serif;
        }
        .login-container {
            background-color: #dcdbe1;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            width: 300px;
        }
        h1 {
            text-align: center;
            color: #080808;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #303032;
        }
        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #303032;
            border-radius: 4px;
            font-size: 16px;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #080808;
            border: none;
            border-radius: 4px;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }
        button:hover {
            background-color: #303032;
        }
        a {
            display: block;
            text-align: center;
            margin-top: 10px;
            text-decoration: none;
            color: #080808;
        }
        a:hover {
            text-decoration: underline;
        }
        .error-message {
            color: #a94442; /* Error color */
            margin-bottom: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>Login</h1>
        <form action="MyController" method="post">
            <input type="hidden" name="command" value="do_auth"/>
            
            <div class="error-message">
                <c:if test="${not (requestScope.authError eq null) }">
                    <c:out value="${requestScope.authError}" />
                </c:if>
            </div>
            
            <label for="login">Username:</label>
            <input type="text" id="login" name="login" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            
            <button type="submit">Log In</button>
        </form>
        <a href="MyController?command=go_to_registration_page">Create a new account</a>
    </div>
</body>
</html>
