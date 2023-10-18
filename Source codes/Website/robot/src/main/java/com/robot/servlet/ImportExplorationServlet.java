package com.robot.servlet;

import com.robot.dao.ExplorationDao;
import com.robot.dao.RobotDao;
import com.robot.vo.Exploration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/ImportExplorationServlet")
@MultipartConfig
public class ImportExplorationServlet extends HttpServlet {

    private ExplorationDao explorationDao;
    private RobotDao robotDao;

    @Override
    public void init() throws ServletException {
        super.init();
        explorationDao = new ExplorationDao(); // 初始化 ExplorationDao
        robotDao = new RobotDao(); // 初始化 RobotDao
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        if (filePart != null) {
            try (InputStream fileContent = filePart.getInputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(fileContent);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                String line;
                int index = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    index++;
                    if (index == 1) {
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        int robotId = Integer.parseInt(parts[0]);
                        String timestampStr = parts[1];
                        int treasureCount = Integer.parseInt(parts[2]);
                        int mazeExplorationTime = Integer.parseInt(parts[3]);

                        // 将数据存储到数据库中
                        saveExplorationRecord(robotId, timestampStr, mazeExplorationTime, treasureCount);
                    }
                }

                response.getWriter().write("导入成功！");
            } catch (Exception e) {
                response.getWriter().write("导入失败：" + e.getMessage());
            }
        }
        response.sendRedirect(request.getContextPath() + "/exploration");
    }

    private void saveExplorationRecord(int robotId, String timestampStr, int mazeExplorationTime, int treasureCount) {

        LocalDateTime localDateTime = LocalDateTime.parse(timestampStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        Exploration exploration = new Exploration(robotId, Timestamp.valueOf(localDateTime), mazeExplorationTime, treasureCount);
        explorationDao.addExploration(exploration);
    }
}
