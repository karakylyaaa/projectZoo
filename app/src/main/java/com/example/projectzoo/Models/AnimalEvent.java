package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;

public class AnimalEvent {
    @SerializedName("animal_id")
    private int animalId;

    @SerializedName("animal")
    private Animal animal;

    @SerializedName("event_id")
    private int eventId;

    @SerializedName("event")
    private Event event;

    // Геттеры и сеттеры
    public int getAnimalId() { return animalId; }
    public void setAnimalId(int animalId) { this.animalId = animalId; }
    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
}