<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>机器人探宝</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <style>
        html {
            background-image: url("images/background2.png");
            background-size: cover;
        }
        body {
            background-color: rgba(255, 255, 255, 0.6) !important;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" data-toggle="tab" href="#indexTab">首页</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="tab" href="#robotTab">机器人列表</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="tab" href="#explorationTab">探索记录</a>
        </li>
    </ul>

    <div class="tab-content mt-3">
        <div class="tab-pane fade show active" id="indexTab">
            <jsp:include page="home_index.jsp"></jsp:include>
        </div>
        <div class="tab-pane fade" id="robotTab">
            <jsp:include page="robot.jsp"></jsp:include>
        </div>
        <div class="tab-pane fade" id="explorationTab">
            <jsp:include page="exploration.jsp"></jsp:include>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function(){
        $('.nav-tabs a').click(function(){
            $(this).tab('show');
        });
    });
</script>

</body>
</html>
