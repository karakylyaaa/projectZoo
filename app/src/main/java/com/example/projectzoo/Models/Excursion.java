package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Excursion {
    @SerializedName("excursion_id")
    private int excursionId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("photo")
    private String photo;

    @SerializedName("price")
    private double price;

    @SerializedName("zoo_id")
    private int zooId;

    @SerializedName("zoo")
    private Zoo zoo;

    @SerializedName("animalExcursions")
    private List<AnimalExcursion> animalExcursions;

    // Геттеры и сеттеры
    public int getExcursionId() { return excursionId; }
    public void setExcursionId(int excursionId) { this.excursionId = excursionId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getZooId() { return zooId; }
    public void setZooId(int zooId) { this.zooId = zooId; }
    public Zoo getZoo() { return zoo; }
    public void setZoo(Zoo zoo) { this.zoo = zoo; }
    public List<AnimalExcursion> getAnimalExcursions() { return animalExcursions; }
    public void setAnimalExcursions(List<AnimalExcursion> animalExcursions) { this.animalExcursions = animalExcursions; }
}