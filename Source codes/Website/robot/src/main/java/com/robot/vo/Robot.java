package com.robot.vo;

public class Robot {
    private int id;
    private String name;
    private String location;
    private String image;
    private Integer userId;

    // 构造函数
    public Robot(int id, String name, String location, String image, Integer userId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.image = image;
        this.userId = userId;
    }

    public Robot(String name, String location, String image, Integer userId) {
        this.name = name;
        this.location = location;
        this.image = image;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getUserId() {
        return userId == null ? 0 : userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

