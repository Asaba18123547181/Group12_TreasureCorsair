<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Robotic Treasure Hunt System</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <style>
        html {
            background-image: url("images/background1.png");
            background-size: cover;
        }
        body {
            background-color: rgba(255, 255, 255, 0.3);
        }

        .container {
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin-top: 50px;
        }

        .form-signin {
            max-width: 400px;
            margin: 0 auto;
        }

        .title h1 {
            text-align: center;
            font-size: 36px;
            margin-bottom: 20px;
            color: #007bff;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
        }

        .form-control {
            border: 2px solid #ccc;
            border-radius: 5px;
            padding: 15px;
            font-size: 16px;
            margin-bottom: 15px;
        }

        .btn-group {
            display: flex;
            justify-content: space-between;
        }

        .btn-primary {
            flex-grow: 1;
            margin-right: 5px;
        }

        .btn-primary:last-child {
            margin-right: 0;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .mt-3 {
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="title">
        <h1>Welcome to the Robotic Treasure Hunt System</h1>
    </div>
    <div class="form-signin">
        <form method="post" action="/login">
            <div class="mb-3">
                <input type="text" class="form-control" id="username" name="username" placeholder="Username" required>
            </div>
            <div class="mb-3">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
            </div>
            <div class="btn-group">
                <button class="btn btn-primary" type="submit">Login</button>
                <a href="register.jsp" class="btn btn-primary">Register</a>
            </div>
        </form>
        <div class="mt-3">
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
</body>
</html>
