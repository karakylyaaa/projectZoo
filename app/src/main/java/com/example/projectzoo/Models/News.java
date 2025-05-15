package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class News {
    @SerializedName("news_id")
    private int newsId;

    @SerializedName("title")
    private String title;

    @SerializedName("photo")
    private String photo;

    @SerializedName("text")
    private String text;

    @SerializedName("zoos")
    private List<Zoo> zoos;

    // Геттеры и сеттеры
    public int getNewsId() { return newsId; }
    public void setNewsId(int newsId) { this.newsId = newsId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<Zoo> getZoos() { return zoos; }
    public void setZoos(List<Zoo> zoos) { this.zoos = zoos; }
}