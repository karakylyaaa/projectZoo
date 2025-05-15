package com.example.projectzoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectzoo.Adapters.TicketsAdapter;
import com.example.projectzoo.Models.Event;
import com.example.projectzoo.Models.Excursion;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {

    private RecyclerView ticketsRecyclerView;
    private TicketsAdapter ticketsAdapter;
    private List<Object> allTickets = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private List<Excursion> excursionList = new ArrayList<>();
    private boolean eventsLoaded = false;
    private boolean excursionsLoaded = false;

    // Создаем GestureDetector
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        // Нижнее меню
        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());

        ticketsRecyclerView = findViewById(R.id.tickets_recycler_view);
        ticketsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Инициализируем GestureDetector
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();

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

        // Привязываем GestureDetector к RecyclerView для обработки свайпов
        ticketsRecyclerView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Получение мероприятий
        Call<List<Event>> eventsCall = apiService.getEvents();
        eventsCall.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_EVENTS", "Данные о мероприятиях получены: " + response.body().size() + " элементов");
                    eventList.addAll(response.body());
                    eventsLoaded = true;
                    trySetupAdapter();
                } else {
                    Log.e("API_EVENTS", "Ошибка при получении мероприятий: " + response.code());
                    Toast.makeText(StoreActivity.this, "Ошибка при получении мероприятий", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e("API_EVENTS", "Ошибка подключения (мероприятия): " + t.getMessage());
                Toast.makeText(StoreActivity.this, "Ошибка подключения (мероприятия)", Toast.LENGTH_SHORT).show();
            }
        });

        // Получение экскурсий
        Call<List<Excursion>> excursionsCall = apiService.getExcursions();
        excursionsCall.enqueue(new Callback<List<Excursion>>() {
            @Override
            public void onResponse(Call<List<Excursion>> call, Response<List<Excursion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_EXCURSIONS", "Данные об экскурсиях получены: " + response.body().size() + " элементов");
                    excursionList.addAll(response.body());
                    excursionsLoaded = true;
                    trySetupAdapter();
                } else {
                    Log.e("API_EXCURSIONS", "Ошибка при получении экскурсий: " + response.code());
                    Toast.makeText(StoreActivity.this, "Ошибка при получении экскурсий", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Excursion>> call, Throwable t) {
                Log.e("API_EXCURSIONS", "Ошибка подключения (экскурсии): " + t.getMessage());
                Toast.makeText(StoreActivity.this, "Ошибка подключения (экскурсии)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void trySetupAdapter() {
        if (eventsLoaded && excursionsLoaded) {
            allTickets.addAll(eventList);
            allTickets.addAll(excursionList);

            if (ticketsAdapter == null && !allTickets.isEmpty()) {
                Log.d("ADAPTER_SETUP", "Создание адаптера с " + allTickets.size() + " элементами");
                ticketsAdapter = new TicketsAdapter(StoreActivity.this, allTickets);
                ticketsRecyclerView.setAdapter(ticketsAdapter);
            } else if (ticketsAdapter != null) {
                Log.d("ADAPTER_UPDATE", "Обновление адаптера с " + allTickets.size() + " элементами");
                ticketsAdapter.notifyDataSetChanged();
            }
        } else {
            Log.d("ADAPTER_STATUS", "Ожидание загрузки данных. Events loaded: " + eventsLoaded + ", Excursions loaded: " + excursionsLoaded);
        }
    }

    // Реализуем метод для обработки свайпа вправо
    private void onSwipeRight() {
        Intent intent = new Intent(StoreActivity.this, MapActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // Реализуем метод для обработки свайпа влево
    private void onSwipeLeft() {
        Intent intent = new Intent(StoreActivity.this, AnimalActivity.class);
        startActivity(intent);
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
    }

    // Переход на страницу с животными
    private void openAnimals() {
        startActivity(new Intent(this, AnimalActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
