package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("animal_id")
    private int animalId;

    @SerializedName("species")
    private String species;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    @SerializedName("gender")
    private String gender;

    @SerializedName("height")
    private double height;

    @SerializedName("weight")
    private double weight;

    @SerializedName("description")
    private String description;

    @SerializedName("animalExcursions")
    private List<AnimalExcursion> animalExcursions;

    @SerializedName("animalEvents")
    private List<AnimalEvent> animalEvents;

    @SerializedName("photos")
    private List<AnimalPhoto> photos;

    // Геттеры и сеттеры
    public int getAnimalId() { return animalId; }
    public void setAnimalId(int animalId) { this.animalId = animalId; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<AnimalExcursion> getAnimalExcursions() { return animalExcursions; }
    public void setAnimalExcursions(List<AnimalExcursion> animalExcursions) { this.animalExcursions = animalExcursions; }

    public List<AnimalEvent> getAnimalEvents() { return animalEvents; }
    public void setAnimalEvents(List<AnimalEvent> animalEvents) { this.animalEvents = animalEvents; }

    public List<AnimalPhoto> getPhotos() { return photos; }
    public void setPhotos(List<AnimalPhoto> photos) { this.photos = photos; }
}
