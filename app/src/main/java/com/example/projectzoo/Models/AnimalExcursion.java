package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;

public class AnimalExcursion {
    @SerializedName("animal_id")
    private int animalId;

    @SerializedName("animal")
    private Animal animal;

    @SerializedName("excursion_id")
    private int excursionId;

    @SerializedName("excursion")
    private Excursion excursion;

    // Геттеры и сеттеры
    public int getAnimalId() { return animalId; }
    public void setAnimalId(int animalId) { this.animalId = animalId; }
    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }
    public int getExcursionId() { return excursionId; }
    public void setExcursionId(int excursionId) { this.excursionId = excursionId; }
    public Excursion getExcursion() { return excursion; }
    public void setExcursion(Excursion excursion) { this.excursion = excursion; }
}