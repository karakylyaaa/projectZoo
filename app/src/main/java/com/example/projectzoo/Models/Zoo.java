package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Zoo {
    @SerializedName("zoo_id")
    private int zooId;

    @SerializedName("photos")
    private String photos;

    @SerializedName("description")
    private String description;

    @SerializedName("address")
    private String address;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("email")
    private String email;

    @SerializedName("news_id")
    private Integer newsId;

    @SerializedName("news")
    private News news;

    @SerializedName("excursions")
    private List<Excursion> excursions;

    @SerializedName("events")
    private List<Event> events;

    // Геттеры и сеттеры
    public int getZooId() { return zooId; }
    public void setZooId(int zooId) { this.zooId = zooId; }
    public String getPhotos() { return photos; }
    public void setPhotos(String photos) { this.photos = photos; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getNewsId() { return newsId; }
    public void setNewsId(Integer newsId) { this.newsId = newsId; }
    public News getNews() { return news; }
    public void setNews(News news) { this.news = news; }
    public List<Excursion> getExcursions() { return excursions; }
    public void setExcursions(List<Excursion> excursions) { this.excursions = excursions; }
    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }
}
