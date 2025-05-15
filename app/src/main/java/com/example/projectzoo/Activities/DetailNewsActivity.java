package com.example.projectzoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectzoo.Adapters.NewsAdapter;
import com.example.projectzoo.Models.News;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailNewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Получение новостей с сервера (аналогично получению событий)
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<News>> call = apiService.getNews(); // Предполагается, что у вас есть такой метод

        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    newsAdapter = new NewsAdapter(DetailNewsActivity.this, response.body());
                    recyclerView.setAdapter(newsAdapter);
                } else {
                    Toast.makeText(DetailNewsActivity.this, "Ошибка при получении новостей", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(
                    Call<List<News>> call, Throwable t) {
                Toast.makeText(DetailNewsActivity.this, "Ошибка подключения к серверу", Toast.LENGTH_SHORT).show();
            }
        });

        // Нижнее меню (скопируйте обработчики из events.java)
        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openMap() {
        startActivity(new Intent(this, MapActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openStore() {
        startActivity(new Intent(this, StoreActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openAnimals() {
        startActivity(new Intent(this, AnimalActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openCalendar() {
        startActivity(new Intent(this, EventActivity.class));
    }

    private void openMore() {
        startActivity(new Intent(this, MoreActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}