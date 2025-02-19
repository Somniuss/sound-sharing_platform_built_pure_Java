<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload Sound Effect</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f1f2f2;
            margin: 0;
            font-family: Arial, sans-serif;
        }

        .container {
            background-color: #dcdbe1;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            width: 500px;
        }

        h2 {
            text-align: center;
            color: #080808;
            margin-bottom: 30px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #303032;
        }

        input, select {
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

        .error-message {
            color: #a94442;
            margin-bottom: 10px;
            text-align: center;
        }

        .custom-file-input {
            display: none;
        }

        .custom-file-label {
            display: block;
            background-color: #080808;
            color: white;
            padding: 10px;
            border: 1px solid #303032;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            text-align: center;
            width: 100%;
            margin-bottom: 15px;
        }

        .custom-file-label:hover {
            background-color: #303032;
        }

        .input-group {
            display: flex;
            align-items: center;
        }

        .input-group-prepend {
            margin-right: 10px;
        }

        .input-group-text {
            background-color: #080808;
            color: white;
            border: 1px solid #303032;
            font-size: 16px;
            padding: 10px;
        }

        .form-text {
            font-size: 14px;
            color: #6c757d;
        }
    </style>
</head>
<body>

    <div class="container mt-5">
        <h2>Upload Sound Effect</h2>
        <form action="MyController" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="do_add_sound" />

            <div class="error-message">
                <c:if test="${not empty requestScope.error}">
                    <c:out value="${requestScope.error}" />
                </c:if>
            </div>

            <label for="title">Effect Name</label>
            <input type="text" class="form-control" id="title" name="title" required>

            <label for="description">Description</label>
            <input type="text" class="form-control" id="description" name="description">

            <label for="categoryId">Category</label>
            <select id="categoryId" name="categoryId" class="form-control" required>
                <option value="1">Glitch</option>
                <option value="2">Atmosphere</option>
                <option value="3">UI</option>
            </select>

            <label for="price">Price (in USD)</label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">$</span>
                </div>
                <input type="text" class="form-control" id="price" name="price" required pattern="^\d+(\.\d{1,2})?$" placeholder="Enter price (e.g. 19.99 or 20)" aria-describedby="priceHelp">
            </div>
            <small id="priceHelp" class="form-text text-muted">
                Please enter a price in USD. Use a period for decimals (e.g., 19.99 or 20).
            </small>

            <div class="form-group">
                <input type="file" class="custom-file-input" id="sfxFile" name="sfxFile" accept=".mp3" required>
                <label class="custom-file-label" for="sfxFile">Choose Sound File (MP3)</label>
            </div>

            <button type="submit">Upload</button>
        </form>

        <form action="MyController" method="post" class="mt-3">
            <input type="hidden" name="command" value="go_to_main_page" />
            <button type="submit" class="btn btn-secondary">Back to Main Page</button>
        </form>
    </div>

</body>
</html>
