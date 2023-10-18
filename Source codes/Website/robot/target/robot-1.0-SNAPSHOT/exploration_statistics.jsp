<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.robot.vo.ExplorationStat" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>探索宝藏记录统计</title>
    <!-- 引入Chart.js库 -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>

    <style>

        html {
            background-image: url("images/background2.png");
            background-size: cover;
        }
        body {
            background-color: rgba(255, 255, 255, 0.5) !important;
        }
        /* 添加居中布局样式 */
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        /* 添加动画效果 */
        .animated {
            animation: fadeInUp 1s ease;
        }

        @keyframes fadeInUp {
            0% {
                opacity: 0;
                transform: translateY(20px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 添加动画效果类名 -->
    <h1 class="animated">探索宝藏记录统计</h1>
    <div class="robot-info animated">
        <h2>机器人信息</h2>
        <p>名称: <%= ((ExplorationStat)request.getAttribute("stat")).getRobotName() %></p>
        <p>地址: <%= ((ExplorationStat)request.getAttribute("stat")).getRobotAddress() %></p>
        <img src="/robot?action=image&name=<%= ((ExplorationStat)request.getAttribute("stat")).getRobotImage() %>" alt="机器人图片" width="200">
    </div>
    <div class="chart-container animated" style="width: 800px">
        <h2>记录折线图</h2>
        <canvas id="explorationChart"></canvas>
    </div>
</div>

<script>
    // 准备记录数据
    var stat = '<%= request.getAttribute("statJson") %>';
    stat = JSON.parse(stat);

    // 获取canvas元素
    var ctx = document.getElementById('explorationChart').getContext('2d');

    // 创建折线图
    var chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: stat.labels, // x轴标签
            datasets: [{
                label: '宝藏数量',
                data: stat.data, // y轴数据
                borderColor: 'blue',
                backgroundColor: 'rgba(0, 0, 255, 0.1)',
                fill: true
            }]
        },
    });
</script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
</body>
</html>
