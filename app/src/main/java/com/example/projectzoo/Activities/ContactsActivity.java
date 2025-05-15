package com.example.projectzoo.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectzoo.R;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Обработка кнопки "Назад"
        LinearLayout backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        TextView phoneNumber = findViewById(R.id.phone_number);
        phoneNumber.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+79876630708"));
            startActivity(intent);
        });

        TextView websiteLink = findViewById(R.id.website_link);
        websiteLink.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://kazzoobotsad.ru/"));
            startActivity(browserIntent);
        });

        ImageView vkIcon = findViewById(R.id.vk);
        ImageView tgIcon = findViewById(R.id.tg);
        ImageView okIcon = findViewById(R.id.ok);

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

        // Обработчики для нижнего меню
        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());
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
