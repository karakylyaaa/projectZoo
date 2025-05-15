package com.example.projectzoo.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.pm.PackageManager;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projectzoo.Models.User;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1001;
    private static final int CAMERA_PERMISSION_CODE = 1002;

    private TextView firstNameView, lastNameView, emailView, phoneView, usernameView;
    private ImageView avatarImageView;
    private Button editProfileButton, logoutButton;
    private TextView changePhotoText;

    private SharedPreferences prefs;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Обработчики для нижнего меню
        findViewById(R.id.btn_main).setOnClickListener(v -> openMain());
        findViewById(R.id.btn_map).setOnClickListener(v -> openMap());
        findViewById(R.id.btn_shop).setOnClickListener(v -> openStore());
        findViewById(R.id.btn_animals).setOnClickListener(v -> openAnimals());
        findViewById(R.id.btn_calendar).setOnClickListener(v -> openCalendar());
        findViewById(R.id.btn_more).setOnClickListener(v -> openMore());
        /*findViewById(R.id.view_all_tickets).setOnClickListener(v -> openPurchasedTickets());*/

        firstNameView = findViewById(R.id.first_name);
        lastNameView = findViewById(R.id.last_name);
        emailView = findViewById(R.id.email);
        phoneView = findViewById(R.id.phone);
        usernameView = findViewById(R.id.username);
        avatarImageView = findViewById(R.id.profile_image);
        editProfileButton = findViewById(R.id.edit_profile_button);
        logoutButton = findViewById(R.id.logout_button);
        changePhotoText = findViewById(R.id.change_photo_text);



        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        loadUserData();

        changePhotoText.setOnClickListener(v -> openImagePicker());

        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void openImagePicker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    avatarImageView.setImageBitmap(bitmap);
                    saveAvatarToPreferences(bitmap);
                    uploadAvatarToServer(bitmap);  // Обновляем в БД
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveAvatarToPreferences(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("avatar", encodedImage);
        editor.apply();

        if (currentUser != null) {
            currentUser.setAvatar(encodedImage);  // обновим в объекте
        }
    }

    private void uploadAvatarToServer(Bitmap bitmap) {
        if (currentUser == null) return;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        currentUser.setAvatar(encodedImage); // Обновим аватар в объекте

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<User> call = apiService.updateUser(currentUser.getUserId(), currentUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(ProfileActivity.this, "Аватар успешно обновлён", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(ProfileActivity.this, "Ошибка сервера: " + response.code(), Toast.LENGTH_LONG).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadUserData() {
        firstNameView.setText(prefs.getString("first_name", ""));
        lastNameView.setText(prefs.getString("last_name", ""));
        emailView.setText(prefs.getString("email", ""));
        phoneView.setText(prefs.getString("phone", ""));
        usernameView.setText(prefs.getString("username", ""));

        String avatarBase64 = prefs.getString("avatar", "");

        if (avatarBase64 != null && !avatarBase64.isEmpty()) {
            try {
                byte[] decodedBytes = Base64.decode(avatarBase64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                avatarImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                avatarImageView.setImageResource(R.drawable.icon_profile); // fallback при ошибке
            }
        } else {
            avatarImageView.setImageResource(R.drawable.icon_profile); // аватар по умолчанию
        }

        currentUser = new User();
        currentUser.setFirstName(prefs.getString("first_name", ""));
        currentUser.setLastName(prefs.getString("last_name", ""));
        currentUser.setEmail(prefs.getString("email", ""));
        currentUser.setPhoneNumber(prefs.getString("phone", ""));
        currentUser.setUsername(prefs.getString("username", ""));
        currentUser.setUserId(prefs.getInt("user_id", 0));
        currentUser.setAvatar(avatarBase64);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
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

    private void openPurchasedTickets() {
        Intent intent = new Intent(this, PurchasedTicketsActivity.class);
        startActivity(intent);

    }
}
