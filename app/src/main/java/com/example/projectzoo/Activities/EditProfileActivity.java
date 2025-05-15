package com.example.projectzoo.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectzoo.Models.User;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText, usernameEditText;
    private Button saveButton;
    private SharedPreferences sharedPreferences;
    private ApiService apiService;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        apiService = ApiClient.getClient().create(ApiService.class);

        currentUserId = sharedPreferences.getInt("user_id", -1);

        firstNameEditText = findViewById(R.id.edit_first_name);
        lastNameEditText = findViewById(R.id.edit_last_name);
        emailEditText = findViewById(R.id.edit_email);
        phoneEditText = findViewById(R.id.edit_phone);
        usernameEditText = findViewById(R.id.edit_username);
        saveButton = findViewById(R.id.save_button);

        // Заполняем поля из SharedPreferences
        firstNameEditText.setText(sharedPreferences.getString("first_name", ""));
        lastNameEditText.setText(sharedPreferences.getString("last_name", ""));
        emailEditText.setText(sharedPreferences.getString("email", ""));
        phoneEditText.setText(sharedPreferences.getString("phone", ""));
        usernameEditText.setText(sharedPreferences.getString("username", ""));

        saveButton.setOnClickListener(v -> {
            String updatedFirstName = firstNameEditText.getText().toString().trim();
            String updatedLastName = lastNameEditText.getText().toString().trim();
            String updatedEmail = emailEditText.getText().toString().trim();
            String updatedPhone = phoneEditText.getText().toString().trim();
            String updatedUsername = usernameEditText.getText().toString().trim();
            String avatarUrl = sharedPreferences.getString("avatar", "");

            // Email validation
            if (TextUtils.isEmpty(updatedEmail) || !android.util.Patterns.EMAIL_ADDRESS.matcher(updatedEmail).matches()) {
                emailEditText.setError("Некорректный email");
                return;
            }

            // First name validation
            if (TextUtils.isEmpty(updatedFirstName) || !updatedFirstName.matches("[A-Za-zА-Яа-я]+")) {
                firstNameEditText.setError("Имя должно содержать только буквы");
                return;
            }

            // Last name validation
            if (TextUtils.isEmpty(updatedLastName) || !updatedLastName.matches("[A-Za-zА-Яа-я]+")) {
                lastNameEditText.setError("Фамилия должна содержать только буквы");
                return;
            }

            // Phone validation
            if (TextUtils.isEmpty(updatedPhone) || !updatedPhone.matches("\\+?\\d{10,15}")) {
                phoneEditText.setError("Некорректный номер телефона");
                return;
            }

            // Username validation
            if (TextUtils.isEmpty(updatedUsername) || !updatedUsername.matches("[A-Za-z0-9_]+")) {
                usernameEditText.setError("Логин должен содержать только буквы, цифры и подчеркивания");
                return;
            }

            User updatedUser = new User();
            updatedUser.setUserId(currentUserId);
            updatedUser.setFirstName(updatedFirstName);
            updatedUser.setLastName(updatedLastName);
            updatedUser.setEmail(updatedEmail);
            updatedUser.setPhoneNumber(updatedPhone);
            updatedUser.setUsername(updatedUsername);
            updatedUser.setAvatar(avatarUrl);

            if (updatedUser.getUserId() != currentUserId) {
                Toast.makeText(EditProfileActivity.this, "Ошибка: ID пользователя не совпадает", Toast.LENGTH_SHORT).show();
                return;
            }

            apiService.updateUser(currentUserId, updatedUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("first_name", updatedFirstName);
                        editor.putString("last_name", updatedLastName);
                        editor.putString("email", updatedEmail);
                        editor.putString("phone", updatedPhone);
                        editor.putString("username", updatedUsername);
                        editor.putString("avatar", avatarUrl);
                        editor.apply();

                        Toast.makeText(EditProfileActivity.this, "Профиль обновлён", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorBody);
                            if (jsonObject.has("errors")) {
                                JSONObject errors = jsonObject.getJSONObject("errors");

                                if (errors.has("email")) {
                                    emailEditText.setError(errors.getString("email"));
                                }
                                if (errors.has("username")) {
                                    usernameEditText.setError(errors.getString("username"));
                                }
                                if (errors.has("phone_number")) {
                                    phoneEditText.setError(errors.getString("phone_number"));
                                }
                            } else {
                                Toast.makeText(EditProfileActivity.this, "Ошибка обновления профиля", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(EditProfileActivity.this, "Ошибка обработки ответа", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("EditProfileActivity", "Ошибка сети: " + t.getMessage());
                    Toast.makeText(EditProfileActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}
