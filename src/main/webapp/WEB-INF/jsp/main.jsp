<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SREBNAJE</title>
<style>
/* Основные стили страницы */
body {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
	font-family: Arial, sans-serif;
	background-image: url('<%=request.getContextPath()%>/pictures/background.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
	color: #fff;
	text-align: center;
}

h1 {
	color: #fff;
	text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
	margin: 0;
}

p.lead {
	font-size: 1.2em;
	color: #fff;
	margin: 20px 0;
	text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.7);
}

/* Стили для основных кнопок */
button {
	width: 200px;
	padding: 15px;
	margin: 10px;
	border: none;
	border-radius: 8px;
	color: white;
	font-size: 18px;
	cursor: pointer;
	background-color: #080808;
	transition: background-color 0.3s, transform 0.3s;
}

button:hover {
	background-color: #303032;
	transform: scale(1.05);
}

/* Стили для кнопок в правом верхнем углу — все красного цвета */
.special-button, .logout-button {
	width: 200px;
	padding: 10px 18px;
	border: none;
	border-radius: 25px;
	color: white;
	font-size: 14px;
	cursor: pointer;
	background-color: #ff0000; /* Красный цвет */
	transition: background-color 0.3s, transform 0.3s;
}

.special-button:hover, .logout-button:hover {
	background-color: #d33636; /* Темно-красный при наведении */
	transform: scale(1.1);
}

/* Контейнер для фиксированных кнопок в правом верхнем углу */
.top-buttons {
	position: fixed;
	top: 20px;
	right: 20px;
	display: flex;
	flex-direction: column;
	align-items: flex-end;
	gap: 10px;
}

/* Стили для подвала */
.footer {
	text-align: center;
	margin-top: 20px;
	font-size: 0.9em;
	color: #ddd;
}
</style>
</head>
<body>
	<!-- Блок с фиксированными кнопками в правом верхнем углу -->
	<div class="top-buttons">
		<!-- Если у пользователя роль 1 (обычный пользователь) – показываем кнопку "Become Author" -->
		<c:if test="${user != null and user.roleId == 1}">
			<form action="MyController" method="post">
				<input type="hidden" name="command" value="GO_TO_AUTHOR_REGISTRATION_PAGE">
				<button type="submit" class="special-button">Become Author</button>
			</form>
		</c:if>
		<!-- Если у пользователя роль 2 (автор) – показываем кнопку "Добавить саундэффект" -->
		<c:if test="${user != null and user.roleId == 2}">
			<form action="MyController" method="post">
				<input type="hidden" name="command" value="GO_TO_AUTHOR_PAGE">
				<button type="submit" class="special-button">Add SFX</button>
			</form>
		</c:if>
		<!-- Кнопка LOG OUT -->
		<form action="MyController" method="post">
			<input type="hidden" name="command" value="LOG_OUT">
			<button type="submit" class="logout-button">LOG OUT</button>
		</form>
	</div>

	<!-- Основной контент страницы -->
	<div class="container text-center">
		<h1>SREBNAJE</h1>
		<p class="lead">Welcome aboard!</p>
		<div class="content">
			<a href="MyController?command=go_to_glitch_page">
				<button>GLITCH</button>
			</a> 
			<a href="MyController?command=go_to_atmosphere_page">
				<button>ATMOSPHERE</button>
			</a>
		</div>
		<div class="footer">
			<p>&copy; 2024 SREBNAJE</p>
		</div>
	</div>
</body>
</html>
