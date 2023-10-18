package com.robot.dao;

import com.robot.db.DBConnect;
import com.robot.vo.Robot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RobotDao {

    // 获取所有机器人
    public List<Robot> getAllRobots() {
        List<Robot> robotList = new ArrayList<>();

        try (Connection connection = DBConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM robot")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                String image = resultSet.getString("image");
                int userId = resultSet.getInt("user_id");

                Robot robot = new Robot(id, name, location, image, userId);
                robotList.add(robot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return robotList;
    }

    // 根据ID获取机器人
    public Robot getRobotById(int robotId) {
        Robot robot = null;

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM robot WHERE id = ?")) {

            statement.setInt(1, robotId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");
                    String image = resultSet.getString("image");
                    int userId = resultSet.getInt("user_id");

                    robot = new Robot(id, name, location, image, userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return robot;
    }

    // 根据用户Id获取机器人信息
    public Robot getRobotByUserId(Integer findUserId) {
        Robot robot = null;

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM robot WHERE user_id = ?")) {

            statement.setInt(1, findUserId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");
                    String image = resultSet.getString("image");
                    int userId = resultSet.getInt("user_id");

                    robot = new Robot(id, name, location, image, userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return robot;
    }

    // 添加机器人
    public void addRobot(Robot robot) {
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO robot (name, location, image, user_id) VALUES (?, ?, ?, ?)")) {

            statement.setString(1, robot.getName());
            statement.setString(2, robot.getLocation());
            statement.setString(3, robot.getImage());
            statement.setInt(4, robot.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新机器人
    public void updateRobot(Robot robot) {
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE robot SET name = ?, location = ?, image = ?, user_id=? WHERE id = ?")) {

            statement.setString(1, robot.getName());
            statement.setString(2, robot.getLocation());
            statement.setString(3, robot.getImage());
            statement.setInt(4, robot.getUserId());
            statement.setInt(5, robot.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除机器人
    public void deleteRobot(int robotId) {
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM robot WHERE id = ?")) {

            statement.setInt(1, robotId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
