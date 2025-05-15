package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AnimalPhoto implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("photo_id")
    private int photoId;

    @SerializedName("photo")
    private String photo;

    @SerializedName("animal_id")
    private int animalId;
    private transient Animal animal;

    public int getPhotoId() { return photoId; }
    public void setPhotoId(int photoId) { this.photoId = photoId; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public int getAnimalId() { return animalId; }
    public void setAnimalId(int animalId) { this.animalId = animalId; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }
}
