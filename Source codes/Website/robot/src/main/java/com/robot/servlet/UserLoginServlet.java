package com.robot.servlet;

import com.robot.dao.ExplorationDao;
import com.robot.dao.RobotDao;
import com.robot.dao.UserDAO;
import com.robot.dao.impl.UserDAOImpl;
import com.robot.vo.Exploration;
import com.robot.vo.Robot;
import com.robot.vo.UserInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private RobotDao robotDao;
    private ExplorationDao explorationDao;

    @Override
    public void init() throws ServletException {
        super.init();
        robotDao = new RobotDao(); // 初始化 RobotDao
        explorationDao = new ExplorationDao();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        UserInfo userinfo = new UserInfo();
        userinfo.setUsername(req.getParameter("username"));
        userinfo.setPassword(req.getParameter("password"));

        UserDAO dao = new UserDAOImpl();
        int flag = 0;
        try {
            flag = dao.queryByUserInfo(userinfo, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (flag == 1) {
            HttpSession session = req.getSession();
            session.setAttribute("username", userinfo.getUsername());
            session.setAttribute("userId", userinfo.getId());

            res.sendRedirect(req.getContextPath() + "/robot");
        } else {
            req.setAttribute("msg", "用户名或密码错误");
            res.sendRedirect("/error.jsp");
        }
    }

}
