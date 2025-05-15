package com.example.projectzoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectzoo.Models.Event;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.Adapters.EventsAdapter;
import com.example.projectzoo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventsAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerView = findViewById(R.id.events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Получение мероприятий с сервера
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Event>> call = apiService.getEvents();

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventAdapter = new EventsAdapter(EventActivity.this, response.body());
                    recyclerView.setAdapter(eventAdapter);
                } else {
                    Toast.makeText(EventActivity.this, "Ошибка при получении данных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(EventActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });

        // Нижнее меню
        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());

        // Свайпы через GestureDetector на RecyclerView
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        recyclerView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void onSwipeRight() {
        startActivity(new Intent(EventActivity.this, AnimalActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void onSwipeLeft() {
        startActivity(new Intent(EventActivity.this, MoreActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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