package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;

public class Manager {
    @SerializedName("manager_id")
    private int managerId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("user")
    private User user;

    // Геттеры и сеттеры
    public int getManagerId() { return managerId; }
    public void setManagerId(int managerId) { this.managerId = managerId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}