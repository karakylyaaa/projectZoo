package com.example.projectzoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectzoo.Adapters.AnimalsAdapter;
import com.example.projectzoo.Models.Animal;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalsAdapter adapter;
    private List<Animal> animalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);

        recyclerView = findViewById(R.id.recyclerViewAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchAnimals(); // Метод для загрузки списка животных из API

        // Нижнее меню
        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());

        // Свайпы с использованием GestureDetector
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();

                // Если горизонтальное движение больше вертикального
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight(); // Свайп вправо
                        } else {
                            onSwipeLeft(); // Свайп влево
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        // Передаем события касания в GestureDetector для обработки свайпов
        recyclerView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

    }

    private void fetchAnimals() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Animal>> call = apiService.getAnimals();

        call.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    animalList = response.body();
                    adapter = new AnimalsAdapter(AnimalActivity.this, animalList, animal -> {
                        // Переход на экран подробной информации о животном
                        Intent intent = new Intent(AnimalActivity.this, DetailAnimalsActivity.class);
                        intent.putExtra("animal", animal);  // передаем объект животного
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(AnimalActivity.this, "Ошибка при загрузке данных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Toast.makeText(AnimalActivity.this, "Не удалось загрузить животных", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Обработчик свайпа вправо
    private void onSwipeRight() {
        startActivity(new Intent(AnimalActivity.this, StoreActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // Обработчик свайпа влево
    private void onSwipeLeft() {
        startActivity(new Intent(AnimalActivity.this, EventActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // Переход на главную страницу
    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // Переход на карту
    private void openMap() {
        startActivity(new Intent(this, MapActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // Переход в магазин
    private void openStore() {
        startActivity(new Intent(this, StoreActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // Переход на страницу с животными
    private void openAnimals() {
        startActivity(new Intent(this, AnimalActivity.class));
    }

    // Переход на календарь событий
    private void openCalendar() {
        startActivity(new Intent(this, EventActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // Переход на дополнительные страницы
    private void openMore() {
        startActivity(new Intent(this, MoreActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
