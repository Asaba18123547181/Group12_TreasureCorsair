package com.robot.dao;

import com.robot.db.DBConnect;
import com.robot.vo.Exploration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExplorationDao {

    // 获取所有探索记录
    public List<Exploration> getAllExplorations(Integer findRobotId) {
        List<Exploration> explorationList = new ArrayList<>();

        try (Connection connection = DBConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM exploration WHERE robot_id=" + findRobotId)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int robotId = resultSet.getInt("robot_id");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                int mazeExplorationTime = resultSet.getInt("maze_exploration_time");
                int treasureCount = resultSet.getInt("treasure_count");

                Exploration exploration = new Exploration(id, robotId, timestamp, mazeExplorationTime, treasureCount);
                explorationList.add(exploration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return explorationList;
    }

    // 添加探索记录
    public void addExploration(Exploration exploration) {
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO exploration (robot_id, maze_exploration_time, treasure_count) VALUES (?, ?, ?)")) {

            statement.setInt(1, exploration.getRobotId());
            statement.setInt(2, exploration.getMazeExplorationTime());
            statement.setInt(3, exploration.getTreasureCount());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新探索记录
    public void updateExploration(Exploration exploration) {
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE exploration SET robot_id = ?, maze_exploration_time = ?, treasure_count = ? WHERE id = ?")) {

            statement.setInt(1, exploration.getRobotId());
            statement.setInt(2, exploration.getMazeExplorationTime());
            statement.setInt(3, exploration.getTreasureCount());
            statement.setInt(4, exploration.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除探索记录
    public void deleteExploration(int explorationId) {
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM exploration WHERE id = ?")) {

            statement.setInt(1, explorationId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据ID查询
    public Exploration getExplorationById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Exploration exploration = null;

        try {
            // 获取数据库连接
            connection = DBConnect.getConnection();

            // 创建 SQL 查询语句
            String sql = "SELECT * FROM exploration WHERE id = ?";

            // 创建 PreparedStatement 对象
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            // 执行查询
            resultSet = preparedStatement.executeQuery();

            // 处理结果集
            if (resultSet.next()) {
                exploration = new Exploration();
                exploration.setId(resultSet.getInt("id"));
                exploration.setRobotId(resultSet.getInt("robot_id"));
                exploration.setTimestamp(resultSet.getTimestamp("timestamp"));
                exploration.setMazeExplorationTime(resultSet.getInt("maze_exploration_time"));
                exploration.setTreasureCount(resultSet.getInt("treasure_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            DBConnect.closeAll(connection, preparedStatement, resultSet);
        }

        return exploration;
    }
}

