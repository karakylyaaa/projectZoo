package com.example.projectzoo.Adapters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectzoo.Models.Ticket;
import com.example.projectzoo.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class PurchasedTicketsAdapter extends RecyclerView.Adapter<PurchasedTicketsAdapter.TicketViewHolder> {

    private static final String TAG = "PurchasedTicketsAdapter";
    private List<Ticket> ticketList;
    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public PurchasedTicketsAdapter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchased_tickets, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);

        try {
            int price = (int) Math.round(ticket.getPrice());
            holder.tvPrice.setText(price + "₽");
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при установке цены билета: " + e.getMessage());
        }

        try {
            Log.d(TAG, "Полученная дата посещения: " + ticket.getVisitDate());
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при логировании даты посещения: " + e.getMessage());
        }

        try {
            if (ticket.getVisitDate() != null && !ticket.getVisitDate().isEmpty()) {
                String formattedDate = convertStringToDate(ticket.getVisitDate());
                holder.tvVisitDate.setText(formattedDate);
            } else {
                holder.tvVisitDate.setText("Дата посещения: Не указана");
            }
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при форматировании даты посещения: " + e.getMessage());
            holder.tvVisitDate.setText("Дата посещения: Не указана");
        }

        try {
            holder.tvTicketType.setText(ticket.getType());
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при установке типа билета: " + e.getMessage());
        }

        try {
            holder.tvDescription.setText(ticket.getDescription());
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при установке описания билета: " + e.getMessage());
        }

        try {
            holder.tvTicketCode.setText(ticket.getTicketCode());
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при установке кода билета: " + e.getMessage());
        }

        try {
            Bitmap qrCodeBitmap = generateQRCode(ticket.getTicketCode(), 1000); // увеличенный размер
            if (qrCodeBitmap != null) {
                holder.tvTicketQrCode.setImageBitmap(qrCodeBitmap);
            } else {
                Log.e(TAG, "Не удалось сгенерировать QR-код для билета");
            }
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при генерации QR-кода: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrice, tvVisitDate, tvTicketType, tvDescription, tvTicketCode;
        ImageView tvTicketQrCode;

        public TicketViewHolder(View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvVisitDate = itemView.findViewById(R.id.tv_visit_date);
            tvTicketType = itemView.findViewById(R.id.tv_ticket_type);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvTicketCode = itemView.findViewById(R.id.tv_ticket_code);
            tvTicketQrCode = itemView.findViewById(R.id.tv_ticket_qr_code);
        }
    }

    private Bitmap generateQRCode(String ticketCode, int size) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);  // <-- Вернули белую рамку

            com.google.zxing.common.BitMatrix bitMatrix =
                    writer.encode(ticketCode, BarcodeFormat.QR_CODE, size, size, hints);

            return toBitmap(bitMatrix);
        } catch (WriterException e) {
            Log.e(TAG, "Ошибка при создании QR-кода: " + e.getMessage());
            return null;
        }
    }


    private Bitmap toBitmap(com.google.zxing.common.BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bitmap;
    }

    private Bitmap cropWhiteSpaces(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int minX = width, minY = height, maxX = 0, maxY = 0;
        boolean foundPixel = false;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bitmap.getPixel(x, y) != Color.WHITE) {
                    foundPixel = true;
                    if (x < minX) minX = x;
                    if (x > maxX) maxX = x;
                    if (y < minY) minY = y;
                    if (y > maxY) maxY = y;
                }
            }
        }

        if (!foundPixel) return bitmap;

        return Bitmap.createBitmap(bitmap, minX, minY, maxX - minX + 1, maxY - minY + 1);
    }

    private String convertStringToDate(String dateString) {
        try {
            java.util.Date date = inputDateFormat.parse(dateString);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Ошибка при преобразовании даты из строки: " + e.getMessage());
            return "Не указана";
        }
    }
}
