<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body style="text-align:center; background:url(/images/robot2.png) ;background-size:cover; ">
<%
request.setCharacterEncoding("utf-8");
%>
<%!
public static final String DBDRIVER="com.mysql.jdbc.Driver";
public static final String DBURL="jdbc:mysql://localhost:3306/javawebdb";
public static final String DBUSER="root";
public static final String DBPASS="wjs990919";
%>
<%
Connection conn=null;
PreparedStatement pstmt=null;
ResultSet rs=null;
boolean flag=false;
%>
<%
try{
	
	Class.forName(DBDRIVER);
	String username=request.getParameter("username1");
	String password=request.getParameter("password1");
	String password1=request.getParameter("password2");
	if(username==null || username=="" || password==null || password==""){
		flag=false;
	}
	else if(password.equals(password1)){
		conn=DriverManager.getConnection(DBURL,DBUSER,DBPASS);
		String sql="insert into user01(username,password) values (?,?)";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,username);
		pstmt.setString(2,password);
		int rtn=pstmt.executeUpdate();
		flag=true;
	}
	else{
		flag=false;
	}
}catch(Exception e){

}
finally{
	try{
		rs.close();
		pstmt.close();
		conn.close();
	}catch(Exception e){}
}
%>
<%
if(flag){
%>
response.sendRedirect("register_login.jsp");
<%
}else{
%>
<br><a href="register.jsp">There has been such an account or the input is null,click here to register</a>
<br>
<br>
<br><a href="login.jsp">click here to exit to the login page</a>
<%
}
%>
</body>
</html>

