package com.example.projectzoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projectzoo.Models.Animal;
import com.example.projectzoo.Models.AnimalPhoto;
import com.example.projectzoo.OnSwipeTouchListener;
import com.example.projectzoo.R;

import java.util.List;

public class DetailAnimalsActivity extends AppCompatActivity {

    private ImageView ivAnimalImage;
    private ImageButton btnPrev, btnNext;
    private TextView tvName, tvSpecies, tvAge, tvGender, tvHeight, tvWeight, tvDescription;

    private List<AnimalPhoto> photos;
    private int currentIndex = 0;
    private Handler handler = new Handler();
    private Runnable autoSwitchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);

        ivAnimalImage = findViewById(R.id.ivAnimalImage);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        tvName = findViewById(R.id.tvName);
        tvSpecies = findViewById(R.id.tvSpecies);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvDescription = findViewById(R.id.tvDescription);

        Animal animal = (Animal) getIntent().getSerializableExtra("animal");

        if (animal != null) {
            tvName.setText("Кличка: " + animal.getName());
            tvSpecies.setText(animal.getSpecies());
            tvAge.setText("Возраст: " + animal.getAge());
            tvGender.setText("Пол: " + animal.getGender());
            tvHeight.setText("Высота: " + animal.getHeight() + " м");
            tvWeight.setText("Вес: " + animal.getWeight() + " кг");
            tvDescription.setText(animal.getDescription());

            photos = animal.getPhotos();
            if (photos != null && !photos.isEmpty()) {
                showImage(currentIndex);

                btnPrev.setOnClickListener(v -> showPreviousImage());
                btnNext.setOnClickListener(v -> showNextImage());
                startAutoImageSwitch();
            } else {
                ivAnimalImage.setImageResource(R.drawable.placeholder_animal);
            }
        }

        // Кнопки навигации
        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());

        // Свайпы
        View mainLayout = findViewById(R.id.main);
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }

            @Override
            public void onSwipeLeft() {
                // Нет следующей активности после деталей животного в данном контексте
            }
        });
    }

    private void showImage(int index) {
        if (photos != null && !photos.isEmpty()) {
            String imageUrl = "http://10.0.2.2:5191/" + photos.get(index).getPhoto();
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_animal)
                    .error(R.drawable.logo_zoo)
                    .into(ivAnimalImage);

            // Анимация смены изображения
            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            ivAnimalImage.startAnimation(fadeIn);
        }
    }

    private void showNextImage() {
        if (photos != null && !photos.isEmpty()) {
            currentIndex = (currentIndex + 1) % photos.size();
            showImage(currentIndex);
        }
    }

    private void showPreviousImage() {
        if (photos != null && !photos.isEmpty()) {
            currentIndex = (currentIndex - 1 + photos.size()) % photos.size();
            showImage(currentIndex);
        }
    }

    private void startAutoImageSwitch() {
        autoSwitchRunnable = new Runnable() {
            @Override
            public void run() {
                showNextImage();
                handler.postDelayed(this, 10000); // каждые 10 секунд
            }
        };
        handler.postDelayed(autoSwitchRunnable, 10000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoSwitchRunnable); // Остановка при выходе
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openMap() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openStore() {
        startActivity(new Intent(this, StoreActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openAnimals() {
        startActivity(new Intent(this, AnimalActivity.class));
    }

    private void openCalendar() {
        startActivity(new Intent(this, EventActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openMore() {
        startActivity(new Intent(this, MoreActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}