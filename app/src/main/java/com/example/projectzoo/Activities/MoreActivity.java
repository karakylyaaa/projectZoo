package com.example.projectzoo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.projectzoo.OnSwipeTouch;
import com.example.projectzoo.OnSwipeTouchListener;
import com.example.projectzoo.R;

public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        findViewById(R.id.logo).setOnClickListener(v -> openMain());

        findViewById(R.id.profile).setOnClickListener(v -> openProfile());
        findViewById(R.id.myTickets).setOnClickListener(v -> openPurchasedTickets());
        findViewById(R.id.events).setOnClickListener(v -> openEvents());
        findViewById(R.id.rules).setOnClickListener(v -> openRules());
        findViewById(R.id.contacts).setOnClickListener(v -> openContacts());

        // Обработчики для нижнего меню
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
                Intent intent = new Intent(MoreActivity.this, EventActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                // Переход на MapActivity при свайпе влево
                Intent intent = new Intent(MoreActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView vkIcon = findViewById(R.id.vk);
        ImageView tgIcon = findViewById(R.id.tg);
        ImageView okIcon = findViewById(R.id.ok);
        ImageView zooIcon = findViewById(R.id.logo);

        vkIcon.setOnClickListener(v -> {
            // Переход на VK
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/"));
            startActivity(browserIntent);
        });

        tgIcon.setOnClickListener(v -> {
            // Переход на Telegram
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/"));
            startActivity(browserIntent);
        });

        okIcon.setOnClickListener(v -> {
            // Переход на Одноклассники
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ok.ru/"));
            startActivity(browserIntent);
        });

        zooIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
    private void openProfile() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        if (prefs.getBoolean("is_logged_in", false)) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Для просмотра профиля войдите в систему", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
    private void openPurchasedTickets() {
        Intent intent = new Intent(this, PurchasedTicketsActivity.class);
        startActivity(intent);

    }
    private void openEvents() {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }
    private void openRules() {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }
    private void openContacts() {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
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



/*    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/
}