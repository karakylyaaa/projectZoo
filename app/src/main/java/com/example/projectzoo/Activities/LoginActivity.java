package com.example.projectzoo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectzoo.Models.User;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private CheckBox rememberCheckbox;
    private Button loginButton;
    private TextView registerLink, forgotPasswordLink, notNowLink;
    private ApiService apiService;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        rememberCheckbox = findViewById(R.id.remember_checkbox);
        loginButton = findViewById(R.id.login_button);
        registerLink = findViewById(R.id.register_link);
        forgotPasswordLink = findViewById(R.id.forgot_password_link);
        notNowLink = findViewById(R.id.not_now_link);

        apiService = ApiClient.getClient().create(ApiService.class);
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("remember_me", false)) {
            emailInput.setText(sharedPreferences.getString("saved_email", ""));
            passwordInput.setText(sharedPreferences.getString("saved_password", ""));
            rememberCheckbox.setChecked(true);
        }

        registerLink.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegistrationActivity.class)));
        forgotPasswordLink.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));
        notNowLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailInput.setError("Введите email");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                passwordInput.setError("Введите пароль");
                return;
            }

            // Хэшируем введённый пароль
            String hashedPassword = hashPassword(password);

            if (hashedPassword == null) {
                Toast.makeText(LoginActivity.this, "Ошибка хэширования пароля", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("LOGIN", "Отправка запроса на получение пользователей...");

            apiService.getUsers().enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    Log.d("LOGIN", "Ответ от сервера получен");

                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("LOGIN", "Получено пользователей: " + response.body().size());

                        User foundUser = null;
                        for (User user : response.body()) {
                            Log.d("LOGIN", "Проверка пользователя: " + user.getEmail());

                            // Сравниваем хэшированные пароли
                            if (email.equals(user.getEmail()) && hashedPassword.equals(user.getPassword())) {
                                foundUser = user;
                                break;
                            }
                        }

                        if (foundUser != null) {
                            Log.d("LOGIN", "Пользователь найден: ID = " + foundUser.getUserId());

                            // Сохраняем данные пользователя в SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.putInt("user_id", foundUser.getUserId());
                            editor.putString("email", foundUser.getEmail());
                            editor.putString("first_name", foundUser.getFirstName());
                            editor.putString("last_name", foundUser.getLastName());
                            editor.putString("phone", foundUser.getPhoneNumber());
                            editor.putString("username", foundUser.getUsername());

                            if (foundUser.getAvatar() != null) {
                                editor.putString("avatar", foundUser.getAvatar());
                            }

                            editor.putBoolean("is_logged_in", true);

                            if (rememberCheckbox.isChecked()) {
                                editor.putBoolean("remember_me", true);
                                editor.putString("saved_email", email);
                                editor.putString("saved_password", password);
                            } else {
                                editor.putBoolean("remember_me", false);
                                editor.remove("saved_email");
                                editor.remove("saved_password");
                            }

                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Вход выполнен успешно", Toast.LENGTH_SHORT).show();

                            // Передаем объект пользователя в main_page
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user", foundUser);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("LOGIN", "Неверный email или пароль");
                            Toast.makeText(LoginActivity.this, "Неверный email или пароль", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.e("LOGIN", "Ошибка ответа сервера. Код: " + response.code());
                        Toast.makeText(LoginActivity.this, "Ошибка сервера", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.e("LOGIN", "Ошибка подключения к серверу: " + t.getMessage(), t);
                    Toast.makeText(LoginActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    // Хэширование пароля с использованием SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
