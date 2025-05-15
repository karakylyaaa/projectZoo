package com.example.projectzoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectzoo.Models.News;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.OnSwipeTouch;
import com.example.projectzoo.OnSwipeTouchListener;
import com.example.projectzoo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private GridLayout newsGrid;
    private TextView newsItem1, newsItem2, newsItem3, newsItem4;
    private Button btnViewAllNews;
    private ApiService apiService;
    private List<News> allNews;

    private ImageView mainImage;
    private ImageButton leftArrow, rightArrow;
    private int[] imageResIds = {
            R.drawable.main_src_1,
            R.drawable.main_src_2,
            R.drawable.main_src_3,
            R.drawable.main_src_4,
            R.drawable.main_src_3
    };
    private int currentImageIndex = 0;

    private Handler handler = new Handler();
    private Runnable imageSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        newsGrid = findViewById(R.id.news_grid);
        newsItem1 = findViewById(R.id.news_item_1);
        newsItem2 = findViewById(R.id.news_item_2);
        newsItem3 = findViewById(R.id.news_item_3);
        newsItem4 = findViewById(R.id.news_item_4);
        btnViewAllNews = findViewById(R.id.btn_view_all_news);
        mainImage = findViewById(R.id.main_image);
        leftArrow = findViewById(R.id.left_arrow);
        rightArrow = findViewById(R.id.right_arrow);

        apiService = ApiClient.getClient().create(ApiService.class);

        loadLatestNewsTitlesForGrid();
        updateMainImage();

        btnViewAllNews.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailNewsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        leftArrow.setOnClickListener(v -> {
            currentImageIndex = (currentImageIndex - 1 + imageResIds.length) % imageResIds.length;
            updateMainImage();
        });

        rightArrow.setOnClickListener(v -> {
            currentImageIndex = (currentImageIndex + 1) % imageResIds.length;
            updateMainImage();
        });

        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());

        View mainLayout = findViewById(R.id.main);

        // Устанавливаем слушатель свайпов
        mainLayout.setOnTouchListener(new OnSwipeTouch(this) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                // Переход на MoreActivity при свайпе вправо
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                // Переход на MapActivity при свайпе влево
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void updateMainImage() {
        mainImage.setAlpha(0f);
        mainImage.setImageResource(imageResIds[currentImageIndex]);
        mainImage.animate().alpha(1f).setDuration(500).start(); // Плавная анимация
    }

    private void startAutoImageSwitching() {
        imageSwitcher = new Runnable() {
            @Override
            public void run() {
                currentImageIndex = (currentImageIndex + 1) % imageResIds.length;
                updateMainImage();
                handler.postDelayed(this, 10000); // каждые 10 секунд
            }
        };
        handler.postDelayed(imageSwitcher, 10000);
    }

    private void stopAutoImageSwitching() {
        handler.removeCallbacks(imageSwitcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoImageSwitching();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoImageSwitching();
    }

    private void loadLatestNewsTitlesForGrid() {
        Call<List<News>> call = apiService.getNews();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allNews = response.body();
                    displayRandomNewsInGrid(allNews, 4);
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка при загрузке новостей", Toast.LENGTH_SHORT).show();
                    Log.e("MainPage", "Ошибка загрузки новостей: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка подключения при загрузке новостей", Toast.LENGTH_SHORT).show();
                Log.e("MainPage", "Ошибка подключения: " + t.getMessage());
            }
        });
    }

    private void displayRandomNewsInGrid(List<News> newsList, int count) {
        if (newsList != null && !newsList.isEmpty()) {
            Random random = new Random();
            List<Integer> randomIndices = new ArrayList<>();
            int numNews = newsList.size();
            int limit = Math.min(numNews, count);
            TextView[] newsTextViews = {newsItem1, newsItem2, newsItem3, newsItem4};

            while (randomIndices.size() < limit) {
                int randomIndex = random.nextInt(numNews);
                if (!randomIndices.contains(randomIndex)) {
                    randomIndices.add(randomIndex);
                }
            }

            for (int i = 0; i < limit; i++) {
                if (newsTextViews[i] != null && i < randomIndices.size()) {
                    newsTextViews[i].setText(newsList.get(randomIndices.get(i)).getTitle());
                } else if (newsTextViews[i] != null) {
                    newsTextViews[i].setText("");
                }
            }
        } else {
            newsItem1.setText("Нет новостей");
            newsItem2.setText("Нет новостей");
            newsItem3.setText("Нет новостей");
            newsItem4.setText("Нет новостей");
        }
    }

    // Переходы между активностями
    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openStore() {
        Intent intent = new Intent(this, StoreActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openAnimals() {
        Intent intent = new Intent(this, AnimalActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openCalendar() {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openMore() {
        Intent intent = new Intent(this, MoreActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
