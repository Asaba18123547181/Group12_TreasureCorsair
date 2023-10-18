package com.robot.servlet;

import com.robot.dao.ExplorationDao;
import com.robot.dao.RobotDao;
import com.robot.vo.Exploration;
import com.robot.vo.Robot;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/exploration")
public class ExplorationServlet extends HttpServlet {
    private ExplorationDao explorationDao;
    private RobotDao robotDao;

    @Override
    public void init() throws ServletException {
        super.init();
        explorationDao = new ExplorationDao(); // 初始化 ExplorationDao
        robotDao = new RobotDao(); // 初始化 RobotDao
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (action == null) {
            // 默认为查找所有探索信息
            Robot robot = robotDao.getRobotByUserId(userId);
            List<Exploration> explorationList = new ArrayList<>();
            if (robot != null) {
                explorationList = explorationDao.getAllExplorations(robot.getId());
            }
            request.setAttribute("explorations", explorationList);
            response.sendRedirect(request.getContextPath() + "/robot");
        } else if (action.equals("add")) {
            // 获取所有机器人信息
            List<Robot> robotList = robotDao.getAllRobots();
            request.setAttribute("robots", robotList);
            // 转发到添加探索页面
            request.getRequestDispatcher("/add_exploration.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // 获取要编辑的探索ID
            int explorationId = Integer.parseInt(request.getParameter("id"));

            // 根据探索ID查找探索信息
            Exploration exploration = explorationDao.getExplorationById(explorationId);

            if (exploration != null) {
                // 获取所有机器人信息
                List<Robot> robotList = robotDao.getAllRobots();
                request.setAttribute("robots", robotList);
                // 将探索信息存储到请求属性中
                request.setAttribute("exploration", exploration);
                request.getRequestDispatcher("/edit_exploration.jsp").forward(request, response);
            } else {
                // 探索不存在，重定向到探索列表页面
                response.sendRedirect(request.getContextPath() + "/robot");
            }
        } else if (action.equals("delete")) {
            // 获取要删除的探索ID
            int explorationId = Integer.parseInt(request.getParameter("id"));

            // 删除探索
            explorationDao.deleteExploration(explorationId);

            // 重定向到探索列表页面
            response.sendRedirect(request.getContextPath() + "/robot");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            // 获取表单数据
            int robotId = Integer.parseInt(request.getParameter("robotId"));
            int mazeExplorationTime = Integer.parseInt(request.getParameter("mazeExplorationTime"));
            int treasureCount = Integer.parseInt(request.getParameter("treasureCount"));
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // 创建探索对象
            Exploration exploration = new Exploration(robotId, timestamp, mazeExplorationTime, treasureCount);

            // 添加探索
            explorationDao.addExploration(exploration);

            // 重定向到探索列表页面
            response.sendRedirect(request.getContextPath() + "/robot");
        } else if (action.equals("edit")) {
            // 获取表单数据
            int explorationId = Integer.parseInt(request.getParameter("id"));
            int robotId = Integer.parseInt(request.getParameter("robotId"));
            int mazeExplorationTime = Integer.parseInt(request.getParameter("mazeExplorationTime"));
            int treasureCount = Integer.parseInt(request.getParameter("treasureCount"));
            // 创建探索对象
            Exploration exploration = new Exploration(explorationId, robotId, mazeExplorationTime, treasureCount);

            // 更新探索信息
            explorationDao.updateExploration(exploration);

            // 重定向到探索列表页面
            response.sendRedirect(request.getContextPath() + "/robot");
        }
    }
}
