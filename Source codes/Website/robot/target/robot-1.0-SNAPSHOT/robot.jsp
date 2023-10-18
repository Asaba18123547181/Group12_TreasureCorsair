<%@ page import="com.robot.vo.Robot" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>机器人列表</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <style>

        body {
            background-color: rgba(255, 255, 255, 0.5);
        }

        .container {
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin-top: 50px;
        }

        .title h1 {
            text-align: center;
            font-size: 36px;
            margin-bottom: 20px;
            color: #007bff;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            text-align: center;
            border-bottom: 1px solid #ccc;
        }

        img {
            max-width: 100px;
            max-height: 100px;
        }

        .edit-link {
            text-decoration: none;
            color: #007bff;
        }

        .edit-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <table>
        <tr>
            <th>机器人ID</th>
            <th>机器人名称</th>
            <th>机器人位置</th>
            <th>机器人图片</th>
            <th>操作</th>
        </tr>
        <% for (Robot robot : (List<Robot>) request.getAttribute("robots")) { %>
        <tr>
            <td><%= robot.getId() %></td>
            <td><%= robot.getName() %></td>
            <td><%= robot.getLocation() %></td>
            <td><img src="/robot?action=image&name=<%= robot.getImage() %>" alt="机器人图片" width="100" height="100"></td>
            <td>
                <c:if test="<%= robot.getUserId() == 0 %>">
                    <a class="edit-link" href="/robot?action=link&id=<%= robot.getId() %>">连接</a>
                </c:if>
                <a class="edit-link" href="/robot?action=stat&id=<%= robot.getId() %>">统计</a>
            </td>
        </tr>
        <% } %>
    </table>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
</body>
</html>
