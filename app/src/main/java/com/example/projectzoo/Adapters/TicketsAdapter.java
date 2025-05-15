package com.example.projectzoo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectzoo.Activities.LoginActivity;
import com.example.projectzoo.Activities.PaymentActivity;
import com.example.projectzoo.DatePickerUtils;
import com.example.projectzoo.Models.Event;
import com.example.projectzoo.Models.Ticket;
import com.example.projectzoo.R;

import java.text.DecimalFormat;
import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketViewHolder> {

    private final Context context;
    private final List<Object> tickets;
    private final DecimalFormat priceFormat = new DecimalFormat("#");

    public TicketsAdapter(Context context, List<Object> tickets) {
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tickets, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Object item = tickets.get(position);
        if (item instanceof Event) {
            Event event = (Event) item;
            holder.title.setText(event.getTitle());
            holder.description.setText(event.getDescription());
            holder.price.setText(priceFormat.format(event.getPrice()) + " руб");
            holder.itemView.setOnClickListener(v -> onTicketClick(event));
        }
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    private void onTicketClick(Object ticket) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        if (!isLoggedIn) {
            Toast.makeText(context, "Для покупки билета войдите в систему", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return;
        }

        int userId = sharedPreferences.getInt("user_id", -1);
        if (userId == -1) {
            Toast.makeText(context, "Ошибка получения ID пользователя", Toast.LENGTH_SHORT).show();
            return;
        }

        DatePickerUtils.showDatePickerDialog(context, selectedDateStr -> {
            Log.d("TICKET", "Выбранная дата: " + selectedDateStr);

            int idItem = 0;
            double price = 0;
            String description = "";
            String type = "Мероприятие";
            int typeId = 1;

            if (ticket instanceof Event) {
                Event event = (Event) ticket;
                idItem = event.getEventId();
                price = event.getPrice();
                description = event.getDescription();
            }

            Ticket newTicket = new Ticket();
            newTicket.setUserId(userId);
            newTicket.setIdItem(idItem);
            newTicket.setPrice(price);
            newTicket.setDescription(description);
            newTicket.setType(type);
            newTicket.setVisitDate(selectedDateStr);
            newTicket.setTicketCode("TICKET-" + System.currentTimeMillis());
            newTicket.setTypeId(typeId);
            newTicket.setIsUsed(false);

            Log.d("TICKET", "Отправляемые данные: " + newTicket.toString());

            // Запускаем активность оплаты
            Intent paymentIntent = new Intent(context, PaymentActivity.class);
            paymentIntent.putExtra("ticket_data", newTicket);
            paymentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(paymentIntent);
        });
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, price;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ticketTitle);
            description = itemView.findViewById(R.id.ticketDescription);
            price = itemView.findViewById(R.id.ticketPrice);
        }
    }
}
