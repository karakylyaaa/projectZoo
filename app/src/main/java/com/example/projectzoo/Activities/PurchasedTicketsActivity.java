package com.example.projectzoo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectzoo.Adapters.PurchasedTicketsAdapter;
import com.example.projectzoo.Models.Ticket;
import com.example.projectzoo.Models.User;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchasedTicketsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PurchasedTicketsAdapter purchasedTicketsAdapter;

    // Здесь мы получаем текущего пользователя из SharedPreferences
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_tickets);

        // Обработчики для нижнего меню
        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.recycler_view_purchased_tickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Получаем текущего пользователя
        currentUser = getCurrentUser();

        // Получаем данные о купленных билетах с сервера
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Ticket>> call = apiService.getTickets(); // Получаем все билеты

        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Получаем список всех билетов
                    List<Ticket> allTickets = response.body();

                    // Фильтруем билеты по текущему пользователю
                    List<Ticket> userTickets = new ArrayList<>();
                    for (Ticket ticket : allTickets) {
                        if (ticket.getUserId() == currentUser.getUserId()) {
                            userTickets.add(ticket);
                        }
                    }

                    // Устанавливаем адаптер с отфильтрованными билетами
                    purchasedTicketsAdapter = new PurchasedTicketsAdapter(userTickets);
                    recyclerView.setAdapter(purchasedTicketsAdapter);
                } else {
                    Toast.makeText(PurchasedTicketsActivity.this, "Ошибка при получении данных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Log.e("API_ERROR", "Ошибка подключения: " + t.getMessage());
                t.printStackTrace();
                Toast.makeText(PurchasedTicketsActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Метод для получения текущего пользователя из SharedPreferences
    private User getCurrentUser() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        User user = new User();
        user.setUserId(prefs.getInt("user_id", 0));
        user.setFirstName(prefs.getString("first_name", ""));
        user.setLastName(prefs.getString("last_name", ""));
        user.setEmail(prefs.getString("email", ""));
        user.setPhoneNumber(prefs.getString("phone", ""));
        user.setUsername(prefs.getString("username", ""));
        user.setAvatar(prefs.getString("avatar", ""));
        return user;
    }
    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private void openMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openStore() {
        Intent intent = new Intent(this, StoreActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    private void openAnimals() {
        Intent intent = new Intent(this, AnimalActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openCalendar() {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openMore() {
        Intent intent = new Intent(this, MoreActivity.class);
        startActivity(intent);
    }

}
