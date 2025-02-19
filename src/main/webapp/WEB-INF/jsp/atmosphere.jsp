<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.somniuss.bean.Sound"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ATMOSPHERE</title>
<style>
body {
	display: flex;
	flex-direction: column;
	align-items: center;
	min-height: 100vh;
	margin: 0;
	font-family: Arial, sans-serif;
	background-image:
		url('<%= request.getContextPath() %>/pictures/background.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
	color: #fff;
	text-align: center;
	overflow: auto;
}

.header {
	position: sticky;
	top: 0;
	background: rgba(0, 0, 0, 0.7);
	width: 100%;
	padding: 15px 0;
	z-index: 100;
}

h1 {
	margin: 0;
	color: #fff;
	text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
}

.lead {
	font-size: 1.2em;
	margin-bottom: 10px;
}

.content {
	width: 90%;
	max-width: 500px;
	margin-top: 20px;
}

.sound-item {
	background: rgba(0, 0, 0, 0.5);
	padding: 15px;
	border-radius: 8px;
	margin: 15px 0;
}

audio {
	display: block;
	width: 100%;
	height: 32px;
	background: #000;
	border-radius: 5px;
	appearance: none;
	-webkit-appearance: none;
}

audio::-webkit-media-controls-panel {
	background-color: #000 !important;
	color: white !important;
	height: 32px;
	border-radius: 5px;
}

audio::-webkit-media-controls-enclosure {
	border-radius: 5px;
}

audio::-webkit-media-controls-play-button, audio::-webkit-media-controls-pause-button,
	audio::-webkit-media-controls-mute-button {
	filter: brightness(0) invert(1);
}

audio::-webkit-media-controls-timeline, audio::-webkit-media-controls-volume-slider
	{
	filter: brightness(1) invert(0);
}

audio::-webkit-media-controls-current-time-display, audio::-webkit-media-controls-time-remaining-display
	{
	color: white !important;
	font-size: 12px;
}

button.download {
	padding: 5px 10px;
	border: none;
	border-radius: 4px;
	background-color: #080808;
	color: #fff;
	cursor: pointer;
	transition: background-color 0.3s;
}

button.download:hover {
	background-color: #303032;
}

.btn-back-to-main {
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

.btn-back-to-main:hover {
	background-color: #cc0000;
	transform: scale(1.05);
}

.btn-back-to-main:active {
	background-color: #990000;
	transform: scale(1);
}

.footer {
	text-align: center;
	margin-top: 20px;
	font-size: 0.9em;
	color: #ddd;
}
</style>
</head>
<body>
	<div class="header">
		<h1>ATMOSPHERE</h1>
		<p class="lead">Immerse yourself in atmospheric sounds</p>
	</div>

	<div class="content">
		<%
            List<Sound> atmosphereSounds = (List<Sound>) request.getAttribute("atmosphereSounds");
            if (atmosphereSounds != null && !atmosphereSounds.isEmpty()) {
                for (Sound sound : atmosphereSounds) {
        %>
		<div class="sound-item">
			<p><%= sound.getTitle() %>
				- <strong>$<%= sound.getPrice() != null ? sound.getPrice() : "0.00" %></strong>
			</p>

			<audio controls>
				<source
					src="<%= request.getContextPath() %>/<%= sound.getFilePath() %>"
					type="audio/mpeg">
				Your browser does not support the audio element.
			</audio>
			<a href="<%= request.getContextPath() %>/<%= sound.getFilePath() %>"
				download>
				<button class="download">Download</button>
			</a>
		</div>
		<% 
                } 
            } else { 
        %>
		<p>No atmosphere sounds available.</p>
		<% } %>
	</div>

	<form action="MyController" method="post" class="mt-3">
		<input type="hidden" name="command" value="go_to_main_page" />
		<button type="submit" class="btn-back-to-main">Back to Main
			Page</button>
	</form>

	<div class="footer">
		<p>&copy; 2024 SREBNAJE</p>
	</div>
</body>
</html>
