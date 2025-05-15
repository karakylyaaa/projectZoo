package com.example.projectzoo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectzoo.Models.Ticket;
import com.example.projectzoo.Network.ApiClient;
import com.example.projectzoo.Network.ApiService;
import com.example.projectzoo.R;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private EditText cardNumber, expiryDate, cvc;
    private TextView paymentCost;
    private Button payButton;

    private Ticket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ImageView backButton = findViewById(R.id.payment_back);
        backButton.setOnClickListener(v -> onBackPressed());

        ticket = (Ticket) getIntent().getSerializableExtra("ticket_data");

        if (ticket == null) {
            Toast.makeText(this, "Ошибка: нет данных билета", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cardNumber = findViewById(R.id.cardNumber);
        expiryDate = findViewById(R.id.expiryDate);
        cvc = findViewById(R.id.cvc);
        paymentCost = findViewById(R.id.PaymentCost);
        payButton = findViewById(R.id.pay);

        DecimalFormat priceFormat = new DecimalFormat("#");
        paymentCost.setText("Сумма к оплате: " + priceFormat.format(ticket.getPrice()) + " ₽");


        payButton.setOnClickListener(v -> {
            String cardNum = cardNumber.getText().toString().trim();
            String expiry = expiryDate.getText().toString().trim();
            String cvcCode = cvc.getText().toString().trim();

            if (cardNum.isEmpty() || expiry.isEmpty() || cvcCode.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            } else {
                processPayment(this, ticket);
            }
        });

    }

    private void processPayment(Context context, Ticket ticket) {
        Toast.makeText(context, "Платеж успешно обработан!", Toast.LENGTH_SHORT).show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Ticket> call = apiService.createTicket(ticket);

        call.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Билет успешно добавлен", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                Toast.makeText(context, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
