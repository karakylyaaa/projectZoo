package com.example.projectzoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectzoo.OnSwipeTouchListener;
import com.example.projectzoo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Подключаем карту
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Нижнее меню
        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());

        // Обработчик для кнопки увеличения
        findViewById(R.id.zoom_in_button).setOnClickListener(v -> zoomIn());

        // Обработчик для кнопки уменьшения
        findViewById(R.id.zoom_out_button).setOnClickListener(v -> zoomOut());

        // Свайпы
        View mainLayout = findViewById(R.id.main);
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                startActivity(new Intent(MapActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }

            @Override
            public void onSwipeLeft() {
                startActivity(new Intent(MapActivity.this, StoreActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    // Увеличение масштаба
    private void zoomIn() {
        if (googleMap != null) {
            float currentZoom = googleMap.getCameraPosition().zoom;
            if (currentZoom < googleMap.getMaxZoomLevel()) {
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoom + 1));
            }
        }
    }

    // Уменьшение масштаба
    private void zoomOut() {
        if (googleMap != null) {
            float currentZoom = googleMap.getCameraPosition().zoom;
            if (currentZoom > googleMap.getMinZoomLevel()) {
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoom - 1));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;

        // Пример: Московский зоопарк
        LatLng zoo = new LatLng(55.76614649145314, 49.132986268655216);
        googleMap.addMarker(new MarkerOptions().position(zoo).title("Казанский зооботосад"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoo, 15f));
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void openMap() {
        // Уже на карте
    }

    private void openStore() {
        startActivity(new Intent(this, StoreActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openAnimals() {
        startActivity(new Intent(this, AnimalActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
