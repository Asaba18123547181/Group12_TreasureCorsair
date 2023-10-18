<%@ page import="com.robot.vo.Exploration" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>探索列表</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <style>
        table {
            margin-top: 20px;
            margin-bottom: 20px;
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 8px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        .edit-link, .delete-link {
            margin-left: 10px;
        }

        .import-export-container {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .import-button {
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="import-export-container">
        <form action="ImportExplorationServlet" method="post" enctype="multipart/form-data">
            <input type="file" name="file" accept=".csv" required>
            <input type="submit" value="导入" class="import-button">
        </form>
        <form action="ExportExplorationServlet" method="get" enctype="multipart/form-data">
            <input type="submit" value="导出" class="import-button">
        </form>
    </div>

    <table>
        <tr>
            <th>探索ID</th>
            <th>机器人ID</th>
            <th>时间戳</th>
            <th>迷宫探索时间</th>
            <th>宝藏数量</th>
            <th>操作</th>
        </tr>
        <% for (Exploration exploration : (List<Exploration>) request.getAttribute("explorations")) { %>
        <tr>
            <td><%= exploration.getId() %>
            </td>
            <td><%= exploration.getRobotId() %>
            </td>
            <td><%= exploration.getTimestamp() %>
            </td>
            <td><%= exploration.getMazeExplorationTime() %>
            </td>
            <td><%= exploration.getTreasureCount() %>
            </td>
            <td>
                <a class="edit-link" href="/exploration?action=edit&id=<%= exploration.getId() %>">编辑</a>
            </td>
        </tr>
        <% } %>
    </table>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
</body>
</html>
