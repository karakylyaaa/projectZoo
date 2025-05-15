package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Event {
    @SerializedName("event_id")
    private int eventId;

    @SerializedName("title")
    private String title;

    @SerializedName("duration")
    private String duration;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

    @SerializedName("photo")
    private String photo;

    @SerializedName("zoo_id")
    private Integer zooId;

    @SerializedName("zoo")
    private Zoo zoo;

    @SerializedName("animalEvents")
    private List<AnimalEvent> animalEvents;

    // Геттеры и сеттеры
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    public Integer getZooId() { return zooId; }
    public void setZooId(Integer zooId) { this.zooId = zooId; }
    public Zoo getZoo() { return zoo; }
    public void setZoo(Zoo zoo) { this.zoo = zoo; }
    public List<AnimalEvent> getAnimalEvents() { return animalEvents; }
    public void setAnimalEvents(List<AnimalEvent> animalEvents) { this.animalEvents = animalEvents; }
}