package com.example.projectzoo.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectzoo.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Инициализация элементов интерфейса
        emailInput = findViewById(R.id.email_input);
        confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();

            // Валидация поля
            if (TextUtils.isEmpty(email)) {
                emailInput.setError("Введите email");
                return;
            }

            /*// Отправка запроса на сервер для восстановления пароля
            ApiClient.getClient().create(ApiService.class)
                    .sendTemporaryPasswordRequest(email)
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(forgot_password.this, "Инструкции отправлены на email", Toast.LENGTH_SHORT).show();
                                // Переход на страницу входа после успешной отправки
                                Intent intent = new Intent(forgot_password.this, login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(forgot_password.this, "Ошибка отправки email", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(forgot_password.this, "Ошибка подключения к серверу", Toast.LENGTH_SHORT).show();
                        }
                    });*/
        });
    }
}
