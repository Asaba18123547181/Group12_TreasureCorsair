package com.robot.servlet;

import com.robot.dao.ExplorationDao;
import com.robot.dao.RobotDao;
import com.robot.vo.Exploration;
import com.robot.vo.ExplorationStat;
import com.robot.vo.Robot;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/robot")
public class RobotServlet extends HttpServlet {
    private RobotDao robotDao;
    private ExplorationDao explorationDao;

    @Override
    public void init() throws ServletException {
        super.init();
        robotDao = new RobotDao(); // 初始化 RobotDao
        explorationDao = new ExplorationDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (action == null) {
            // 默认为查找所有机器人信息
            List<Robot> robotList = robotDao.getAllRobots();
            request.setAttribute("robots", robotList);

            Robot robot = robotDao.getRobotByUserId(userId);
            List<Exploration> explorationList = new ArrayList<>();
            if (robot != null) {
                explorationList = explorationDao.getAllExplorations(robot.getId());
            }
            request.setAttribute("explorations", explorationList);

            request.getRequestDispatcher("/home.jsp#robotTab").forward(request, response);
        } else if (action.equals("add")) {
            // 转发到添加机器人页面
            request.getRequestDispatcher("/add_robot.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // 获取要编辑的机器人ID
            int robotId = Integer.parseInt(request.getParameter("id"));

            // 根据机器人ID查找机器人信息
            Robot robot = robotDao.getRobotById(robotId);

            if (robot != null) {
                // 将机器人信息存储到请求属性中
                request.setAttribute("robot", robot);
                request.getRequestDispatcher("/edit_robot.jsp").forward(request, response);
            } else {
                // 机器人不存在，重定向到机器人列表页面
                response.sendRedirect(request.getContextPath() + "/robot");
            }
        } else if (action.equals("delete")) {
            // 获取要删除的机器人ID
            int robotId = Integer.parseInt(request.getParameter("id"));

            // 删除机器人
            robotDao.deleteRobot(robotId);

            // 重定向到机器人列表页面
            response.sendRedirect(request.getContextPath() + "/robot");
        } else if (action.equals("image")) {
            image(request, response);
        } else if (action.equals("link")) {

            // 获取要编辑的机器人ID
            int robotId = Integer.parseInt(request.getParameter("id"));

            // 根据机器人ID查找机器人信息
            Robot robot = robotDao.getRobotById(robotId);
            if (robot.getUserId() != null && robot.getUserId() != 0 && !robot.getUserId().equals(userId)) {
                // 已存在用户连接，不允许连接
                request.setAttribute("msg", "User connection already exists, connection not allowed!");
                response.sendRedirect("/error.jsp");
                return;
            }
            Robot robotByUser= robotDao.getRobotByUserId(userId);
            if (robotByUser != null) {
                robotByUser.setUserId(0);
                robotDao.updateRobot(robotByUser);
            }

            if (robot != null) {
                robot.setUserId(userId);
                robotDao.updateRobot(robot);

            }
            response.sendRedirect(request.getContextPath() + "/robot");
        } else if (action.equals("stat")) {

            ExplorationStat explorationStat = new ExplorationStat();
            // 获取要编辑的机器人ID
            int robotId = Integer.parseInt(request.getParameter("id"));
            Robot robot = robotDao.getRobotById(robotId);
            explorationStat.setRobotId(robot.getId());
            explorationStat.setRobotName(robot.getName());
            explorationStat.setRobotAddress(robot.getLocation());
            explorationStat.setRobotImage(robot.getImage());

            List<Exploration> allExplorations = explorationDao.getAllExplorations(robotId);
            List<Timestamp> labels = allExplorations.stream().map(Exploration::getTimestamp).collect(Collectors.toList());
            List<Integer> data = allExplorations.stream().map(Exploration::getTreasureCount).collect(Collectors.toList());
            explorationStat.setLabels(labels);
            explorationStat.setData(data);
            request.setAttribute("stat", explorationStat);
            request.setAttribute("statJson", explorationStat.toJsonString());
            request.getRequestDispatcher("/exploration_statistics.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            // 获取表单数据
            String name = request.getParameter("name");
            String location = request.getParameter("location");
            String image = request.getParameter("image");
            String userId = request.getParameter("user_id");

            // 创建机器人对象
            Robot robot = new Robot(name, location, image, userId != null ? Integer.valueOf(userId): null);

            // 添加机器人
            robotDao.addRobot(robot);

            // 重定向到机器人列表页面
            response.sendRedirect(request.getContextPath() + "/robot");
        } else if (action.equals("edit")) {
            // 获取表单数据
            int robotId = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String location = request.getParameter("location");
            String image = request.getParameter("image");
            String userId = request.getParameter("user_id");

            // 创建机器人对象
            Robot robot = new Robot(robotId, name, location, image, userId != null ? Integer.valueOf(userId): null);

            // 更新机器人信息
            robotDao.updateRobot(robot);

            // 重定向到机器人列表页面
            response.sendRedirect(request.getContextPath() + "/robot");
        }
    }

    /**
     * 从服务器上检索并发送请求的图片文件给客户端。
     *
     * @param request  HTTP servlet请求
     * @param response HTTP servlet响应
     * @throws IOException 如果发生I/O错误
     */
    private void image(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        if (name != null) {
            javax.servlet.ServletContext context = getServletContext();

            String filePath = context.getRealPath("/images/" + name);
            Path imagePath = Paths.get(filePath);

            if (Files.exists(imagePath)) {
                String mimeType = context.getMimeType(imagePath.toString());
                response.setContentType(mimeType);
                Files.copy(imagePath, response.getOutputStream());
            } else {
                // 图片文件不存在
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            // 未提供有效的图片文件名
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
