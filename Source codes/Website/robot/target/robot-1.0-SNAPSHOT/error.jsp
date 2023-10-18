<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>返回上一页</title>
</head>
<body style="background:url(/images/robot3.png);background-size:cover;">
<div class="center" align="center">
    <%= request.getAttribute("msg") %>
    <br>
    <!-- 使用按钮来返回上一个页面 -->
    <button onclick="goBack()">Go Back</button>
</div>

<script>
    // JavaScript函数，用于返回上一页
    function goBack() {
        window.history.back();
    }
</script>

</body>
</html>
