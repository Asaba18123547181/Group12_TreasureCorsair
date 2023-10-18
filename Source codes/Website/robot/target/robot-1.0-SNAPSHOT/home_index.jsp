<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>机器人探宝</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <style>
        body {
            background-color: #f2f2f2;
            font-family: Arial, sans-serif;
        }

        h1 {
            color: #333;
            font-size: 36px;
            margin-top: 0;
            opacity: 0;
            animation: fadeIn 1s ease-in-out forwards;
        }

        .btn-login:hover {
            background-color: #23527c;
        }

        .btn-login {
            padding: 10px 20px;
            color: #fff;
            background-color: #337ab7;
            display: inline-block;
            font-size: 18px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
            margin-top: 20px;
            text-decoration: none;
            animation: fadeIn 1s ease-in-out forwards 1s;
            opacity: 0;
        }

        p {
            color: #666;
            font-size: 18px;
            text-align: center;
            opacity: 0;
            line-height: 1.5;
            animation: fadeIn 1s ease-in-out forwards 0.5s;
        }

        .container {
            text-align: center;
        }
        .image-container {
            margin-top: 50px;
            opacity: 0;
            animation: fadeIn 1s ease-in-out forwards 1.5s;
        }

        .image-container img {
            width: 400px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        @keyframes fadeIn {
            0% {
                opacity: 0;
            }
            100% {
                opacity: 1;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>欢迎开启机器人探宝之旅</h1>
    <p>在这里你可以使用机器人探索宝藏，系统会记录每条探索宝藏的记录。</p>
    <div class="image-container">
        <img src="${pageContext.request.contextPath}/images/robot.gif" alt="机器人图片">
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
</body>
</html>
