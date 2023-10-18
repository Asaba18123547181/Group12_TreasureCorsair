package com.robot.vo;

import java.sql.Timestamp;

public class Exploration {
    private int id;
    private int robotId;
    private Timestamp timestamp;
    private int mazeExplorationTime;
    private int treasureCount;

    public Exploration() {
    }

    // 构造函数
    public Exploration(int id, int robotId, Timestamp timestamp, int mazeExplorationTime, int treasureCount) {
        this.id = id;
        this.robotId = robotId;
        this.timestamp = timestamp;
        this.mazeExplorationTime = mazeExplorationTime;
        this.treasureCount = treasureCount;
    }

    public Exploration(int robotId, Timestamp timestamp, int mazeExplorationTime, int treasureCount) {
        this.robotId = robotId;
        this.timestamp = timestamp;
        this.mazeExplorationTime = mazeExplorationTime;
        this.treasureCount = treasureCount;
    }

    public Exploration(int id, int robotId, int mazeExplorationTime, int treasureCount) {
        this.id = id;
        this.robotId = robotId;
        this.mazeExplorationTime = mazeExplorationTime;
        this.treasureCount = treasureCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRobotId() {
        return robotId;
    }

    public void setRobotId(int robotId) {
        this.robotId = robotId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getMazeExplorationTime() {
        return mazeExplorationTime;
    }

    public void setMazeExplorationTime(int mazeExplorationTime) {
        this.mazeExplorationTime = mazeExplorationTime;
    }

    public int getTreasureCount() {
        return treasureCount;
    }

    public void setTreasureCount(int treasureCount) {
        this.treasureCount = treasureCount;
    }

}

