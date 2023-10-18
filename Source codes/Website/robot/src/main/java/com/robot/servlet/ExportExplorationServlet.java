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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ExportExplorationServlet")
public class ExportExplorationServlet extends HttpServlet {

    private ExplorationDao explorationDao;
    private RobotDao robotDao;

    @Override
    public void init() throws ServletException {
        super.init();
        explorationDao = new ExplorationDao(); // 初始化 ExplorationDao
        robotDao = new RobotDao(); // 初始化 RobotDao
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"exploration.csv\"");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = response.getWriter()) {
            // 在此处实现从数据库中获取数据并导出到 CSV 文件的逻辑
            List<Exploration> explorations = fetchExplorationRecords(request);

            // 输出 CSV 文件头
            writer.println("机器人ID,时间戳,迷宫探索时间,宝藏数量");

            // 输出数据行
            for (Exploration exploration : explorations) {
                writer.println(exploration.getRobotId() + "," + exploration.getTimestamp() + ","
                        + exploration.getMazeExplorationTime() + "," + exploration.getTreasureCount());
            }
        } catch (Exception e) {
            response.getWriter().write("导出失败：" + e.getMessage());
        }
    }

    private List<Exploration> fetchExplorationRecords(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        Robot robot = robotDao.getRobotByUserId(userId);
        List<Exploration> explorations = new ArrayList<>();
        if (robot != null) {
            explorations = explorationDao.getAllExplorations(robot.getId());
        }
        return explorations;
    }
}
