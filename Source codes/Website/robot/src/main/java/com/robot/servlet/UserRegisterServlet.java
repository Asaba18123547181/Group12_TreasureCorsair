package com.robot.servlet;

import com.robot.dao.UserDAO;
import com.robot.dao.impl.UserDAOImpl;
import com.robot.vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
			 UserInfo userinfo = new UserInfo();
			 userinfo.setUsername(req.getParameter("username"));
			 userinfo.setPassword(req.getParameter("password"));
			 
			 UserDAO dao = new UserDAOImpl();
		     int flag = 0;
		     try {
					flag = dao.queryByUserInfo(userinfo, false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			 if(flag == 1){
				 req.setAttribute("msg", "用户名已存在");
		         res.sendRedirect("/error.jsp");
			 } else {
				 dao.insert(userinfo);
				 res.sendRedirect("/login.jsp");
			 }
		 }
	
}
