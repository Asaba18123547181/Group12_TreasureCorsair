package com.robot.vo;

import java.sql.Timestamp;
import java.util.List;

public class ExplorationStat {

    private int robotId;
    private String robotName;
    private String robotAddress;
    private String robotImage;
    private List<Timestamp> labels;
    private List<Integer> data;

    public int getRobotId() {
        return robotId;
    }

    public void setRobotId(int robotId) {
        this.robotId = robotId;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getRobotAddress() {
        return robotAddress;
    }

    public void setRobotAddress(String robotAddress) {
        this.robotAddress = robotAddress;
    }

    public String getRobotImage() {
        return robotImage;
    }

    public void setRobotImage(String robotImage) {
        this.robotImage = robotImage;
    }

    public List<Timestamp> getLabels() {
        return labels;
    }

    public void setLabels(List<Timestamp> labels) {
        this.labels = labels;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public String toJsonString() {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{");
        jsonString.append("\"robotId\": ").append(robotId).append(",");
        jsonString.append("\"robotName\": \"").append(robotName).append("\",");
        jsonString.append("\"robotAddress\": \"").append(robotAddress).append("\",");
        jsonString.append("\"robotImage\": \"").append(robotImage).append("\",");
        jsonString.append("\"labels\": [");

        // 添加 labels 数组元素
        for (int i = 0; i < labels.size(); i++) {
            Timestamp label = labels.get(i);
            jsonString.append("\"").append(label.toString()).append("\"");
            if (i < labels.size() - 1) {
                jsonString.append(", ");
            }
        }

        jsonString.append("], \"data\": [");

        // 添加 data 数组元素
        for (int i = 0; i < data.size(); i++) {
            Integer value = data.get(i);
            jsonString.append(value);
            if (i < data.size() - 1) {
                jsonString.append(", ");
            }
        }

        jsonString.append("]}");
        return jsonString.toString();
    }

}

