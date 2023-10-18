<%@ page import="com.robot.vo.Exploration" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>编辑探索</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
  <style>
    html {
      background-image: url("images/background2.png");
      background-size: cover;
    }
    body {
      background-color: rgba(255, 255, 255, 0.5) !important;
    }
    .container {
      margin-top: 100px;
      text-align: center;
    }
    .form {
      width: 400px;
      margin: 0 auto;
      text-align: left;
    }
    .form-group {
      margin-bottom: 20px;
    }
    .form-label {
      display: inline-block;
      width: 120px;
      font-weight: bold;
    }
    .form-control {
      width: 250px;
      padding: 6px 12px;
      font-size: 14px;
      line-height: 1.42857143;
      color: #555;
      background-color: #fff;
      background-image: none;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
      -webkit-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
      -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
      -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
      transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
    }
    .submit-button {
      margin-top: 20px;
      text-align: center;
    }
    .cancel-link {
      display: block;
      margin-top: 10px;
      text-align: center;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>编辑探索</h1>
  <div class="form">
    <form action="/exploration" method="post">
      <input type="hidden" name="action" value="edit">
      <input type="hidden" name="id" value="<%= ((Exploration) request.getAttribute("exploration")).getId() %>">

      <div class="form-group">
        <label class="form-label">机器人ID: <%= ((Exploration) request.getAttribute("exploration")).getRobotId() %></label>
        <input type="hidden" class="form-control" name="robotId" value="<%= ((Exploration) request.getAttribute("exploration")).getRobotId() %>" required>
      </div>

      <div class="form-group">
        <label class="form-label">迷宫探索时间:</label>
        <input type="text" class="form-control" name="mazeExplorationTime" value="<%= ((Exploration) request.getAttribute("exploration")).getMazeExplorationTime() %>" required>
      </div>

      <div class="form-group">
        <label class="form-label">宝藏数量:</label>
        <input type="text" class="form-control" name="treasureCount" value="<%= ((Exploration) request.getAttribute("exploration")).getTreasureCount() %>" required>
      </div>

      <div class="submit-button">
        <button type="submit" class="btn btn-primary">保存</button>
      </div>
    </form>
    <a class="cancel-link" href="/exploration">取消</a>
  </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
</body>
</html>
