package com.robot.vo;

public class ExplorationImage {

    private int id;

    private int robotId;

    private int explorationId;

    private String image;

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

    public int getExplorationId() {
        return explorationId;
    }

    public void setExplorationId(int explorationId) {
        this.explorationId = explorationId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
