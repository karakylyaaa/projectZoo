package com.example.projectzoo.Network;

import com.example.projectzoo.Models.Animal;
import com.example.projectzoo.Models.AnimalEvent;
import com.example.projectzoo.Models.AnimalExcursion;
import com.example.projectzoo.Models.AnimalPhoto;
import com.example.projectzoo.Models.Event;
import com.example.projectzoo.Models.Excursion;
import com.example.projectzoo.Models.Manager;
import com.example.projectzoo.Models.News;
import com.example.projectzoo.Models.Ticket;
import com.example.projectzoo.Models.User;
import com.example.projectzoo.Models.Zoo;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // Animal endpoints
    @GET("api/Animal")
    Call<List<Animal>> getAnimals();

    @POST("api/Animal")
    Call<Animal> createAnimal(@Body Animal animal);

    @GET("api/Animal/{id}")
    Call<Animal> getAnimalById(@Path("id") int id);

    @PUT("api/Animal/{id}")
    Call<Void> updateAnimal(@Path("id") int id, @Body Animal animal);

    @DELETE("api/Animal/{id}")
    Call<Void> deleteAnimal(@Path("id") int id);

    // AnimalEvent endpoints
    @GET("api/AnimalEvent")
    Call<List<AnimalEvent>> getAnimalEvents();

    @POST("api/AnimalEvent")
    Call<AnimalEvent> createAnimalEvent(@Body AnimalEvent animalEvent);

    @GET("api/AnimalEvent/{id}")
    Call<AnimalEvent> getAnimalEventById(@Path("id") int id);

    @DELETE("api/AnimalEvent/{id}")
    Call<Void> deleteAnimalEvent(@Path("id") int id);

    // AnimalExcursion endpoints
    @GET("api/AnimalExcursion")
    Call<List<AnimalExcursion>> getAnimalExcursions();

    @POST("api/AnimalExcursion")
    Call<AnimalExcursion> createAnimalExcursion(@Body AnimalExcursion animalExcursion);

    @GET("api/AnimalExcursion/{id}")
    Call<AnimalExcursion> getAnimalExcursionById(@Path("id") int id);

    @DELETE("api/AnimalExcursion/{id}")
    Call<Void> deleteAnimalExcursion(@Path("id") int id);

    // AnimalPhoto endpoints
    @GET("api/AnimalPhoto")
    Call<List<AnimalPhoto>> getAnimalPhotos();

    @POST("api/AnimalPhoto")
    Call<AnimalPhoto> createAnimalPhoto(@Body AnimalPhoto animalPhoto);

    @GET("api/AnimalPhoto/{id}")
    Call<AnimalPhoto> getAnimalPhotoById(@Path("id") int id);

    @DELETE("api/AnimalPhoto/{id}")
    Call<Void> deleteAnimalPhoto(@Path("id") int id);

    // Event endpoints
    @GET("api/Event")
    Call<List<Event>> getEvents();

    @POST("api/Event")
    Call<Event> createEvent(@Body Event event);

    @GET("api/Event/{id}")
    Call<Event> getEventById(@Path("id") int id);

    @PUT("api/Event/{id}")
    Call<Void> updateEvent(@Path("id") int id, @Body Event event);

    @DELETE("api/Event/{id}")
    Call<Void> deleteEvent(@Path("id") int id);

    // Excursion endpoints
    @GET("api/Excursion")
    Call<List<Excursion>> getExcursions();

    @POST("api/Excursion")
    Call<Excursion> createExcursion(@Body Excursion excursion);

    @GET("api/Excursion/{id}")
    Call<Excursion> getExcursionById(@Path("id") int id);

    @PUT("api/Excursion/{id}")
    Call<Void> updateExcursion(@Path("id") int id, @Body Excursion excursion);

    @DELETE("api/Excursion/{id}")
    Call<Void> deleteExcursion(@Path("id") int id);

    // Manager endpoints
    @GET("api/Manager")
    Call<List<Manager>> getManagers();

    @POST("api/Manager")
    Call<Manager> createManager(@Body Manager manager);

    @GET("api/Manager/{id}")
    Call<Manager> getManagerById(@Path("id") int id);

    @PUT("api/Manager/{id}")
    Call<Void> updateManager(@Path("id") int id, @Body Manager manager);

    @DELETE("api/Manager/{id}")
    Call<Void> deleteManager(@Path("id") int id);

    // News endpoints
    @GET("api/News")
    Call<List<News>> getNews();

    @POST("api/News")
    Call<News> createNews(@Body News news);

    @GET("api/News/{id}")
    Call<News> getNewsById(@Path("id") int id);

    @PUT("api/News/{id}")
    Call<Void> updateNews(@Path("id") int id, @Body News news);

    @DELETE("api/News/{id}")
    Call<Void> deleteNews(@Path("id") int id);

    // Ticket endpoints
    @GET("api/Ticket")
    Call<List<Ticket>> getTickets();

    @POST("api/Ticket") // Здесь указываем правильный путь на сервере, например, "/tickets"
    Call<Ticket> createTicket(@Body Ticket ticket);

    @GET("api/Ticket/{id}")
    Call<Ticket> getTicketById(@Path("id") int id);

    @PUT("api/Ticket/{id}")
    Call<Void> updateTicket(@Path("id") int id, @Body Ticket ticket);

    @DELETE("api/Ticket/{id}")
    Call<Void> deleteTicket(@Path("id") int id);

    // User endpoints

    @GET("api/User")
    Call<List<User>> getUsers();

    @POST("api/User")
    Call<User> createUser(@Body User user);

    @GET("api/User/{id}")
    Call<User> getUserById(@Path("id") int id);

    @PUT("api/User/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @DELETE("api/User/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    // Zoo endpoints
    @GET("api/Zoo")
    Call<List<Zoo>> getZoos();

    @POST("api/Zoo")
    Call<Zoo> createZoo(@Body Zoo zoo);

    @GET("api/Zoo/{id}")
    Call<Zoo> getZooById(@Path("id") int id);

    @PUT("api/Zoo/{id}")
    Call<Void> updateZoo(@Path("id") int id, @Body Zoo zoo);

    @DELETE("api/Zoo/{id}")
    Call<Void> deleteZoo(@Path("id") int id);
}
