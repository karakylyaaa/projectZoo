package com.example.projectzoo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectzoo.Models.DuplicateFieldError;
import com.example.projectzoo.Models.User;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        if (isLoggedIn) {
            // Если пользователь зарегистрирован, пропускаем экран регистрации и открываем MainPage
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
            return;
        }

        EditText emailInput = findViewById(R.id.email_input);
        EditText nameInput = findViewById(R.id.name_input);
        EditText surnameInput = findViewById(R.id.surname_input);
        EditText phoneInput = findViewById(R.id.phone_input);
        EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        CheckBox termsCheckbox = findViewById(R.id.terms_checkbox);
        Button registerButton = findViewById(R.id.register_button);
        TextView loginLink = findViewById(R.id.login_link);
        TextView notNowLink = findViewById(R.id.not_now_link);

        loginLink.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        notNowLink.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String name = nameInput.getText().toString().trim();
            String surname = surnameInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // Validate Email
            if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInput.setError("Некорректный email");
                return;
            }

            // Validate Name
            if (TextUtils.isEmpty(name) || !name.matches("[A-Za-zА-Яа-я]+")) {
                nameInput.setError("Имя должно содержать только буквы");
                return;
            }

            // Validate Surname
            if (TextUtils.isEmpty(surname) || !surname.matches("[A-Za-zА-Яа-я]+")) {
                surnameInput.setError("Фамилия должна содержать только буквы");
                return;
            }

            // Validate Phone
            if (TextUtils.isEmpty(phone) || !phone.matches("\\+?\\d{10,15}")) {
                phoneInput.setError("Некорректный номер телефона");
                return;
            }

            // Validate Username
            if (TextUtils.isEmpty(username) || !username.matches("[A-Za-z0-9_]+")) {
                usernameInput.setError("Логин должен содержать только буквы, цифры и подчеркивания");
                return;
            }

            // Validate Password
            if (TextUtils.isEmpty(password) || password.length() < 6 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*].*")) {
                passwordInput.setError("Пароль должен содержать минимум 6 символов, одну заглавную букву, одну цифру и один специальный символ");
                return;
            }

            // Validate Terms Checkbox
            if (!termsCheckbox.isChecked()) {
                Toast.makeText(this, "Примите условия соглашения", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with Registration Process
            String hashedPassword = hashPassword(password);
            if (hashedPassword == null) {
                Toast.makeText(this, "Ошибка хэширования пароля", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User();
            user.setEmail(email);
            user.setFirstName(name);
            user.setLastName(surname);
            user.setPhoneNumber(phone);
            user.setUsername(username);
            user.setPassword(hashedPassword);

            ApiClient.getClient().create(ApiService.class).createUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User createdUser = response.body();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("user_id", createdUser.getUserId());
                        editor.putString("email", createdUser.getEmail());
                        editor.putString("first_name", createdUser.getFirstName());
                        editor.putString("last_name", createdUser.getLastName());
                        editor.putString("phone", createdUser.getPhoneNumber());
                        editor.putString("username", createdUser.getUsername());
                        editor.putBoolean("is_logged_in", true);
                        editor.apply();

                        Toast.makeText(RegistrationActivity.this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegistrationActivity.this, ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorBody);
                            if (jsonObject.has("errors")) {
                                JSONObject errors = jsonObject.getJSONObject("errors");
                                if (errors.has("email")) {
                                    emailInput.setError(errors.getString("email"));
                                }
                                if (errors.has("username")) {
                                    usernameInput.setError(errors.getString("username"));
                                }
                                if (errors.has("phone_number")) {
                                    phoneInput.setError(errors.getString("phone_number"));
                                }
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(RegistrationActivity.this, "Ошибка обработки ответа", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(RegistrationActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void handleDuplicateError(ResponseBody errorBody, EditText emailInput, EditText phoneInput, EditText usernameInput) {
        try {
            if (errorBody != null) {
                String errorJson = errorBody.string();
                Gson gson = new Gson();
                DuplicateFieldError error = gson.fromJson(errorJson, DuplicateFieldError.class);

                if (error != null && error.getField() != null) {
                    switch (error.getField()) {
                        case "email":
                            emailInput.setError("Email уже используется");
                            break;
                        case "phone":
                            phoneInput.setError("Телефон уже используется");
                            break;
                        case "username":
                            usernameInput.setError("Логин уже используется");
                            break;
                        default:
                            Toast.makeText(this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Ошибка: неуникальные данные", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при обработке ответа сервера", Toast.LENGTH_SHORT).show();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
