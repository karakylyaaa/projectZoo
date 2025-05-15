package com.example.projectzoo;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;

public class DatePickerUtils {

    public static void showDatePickerDialog(Context context, DateSelectedListener listener) {
        // Получаем текущую дату
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Создаем DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Формируем выбранную дату в строковом формате
                    String selectedDateStr = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDayOfMonth);

                    // Вызываем слушатель, передаем выбранную дату
                    listener.onDateSelected(selectedDateStr);
                },
                year, month, day
        );

        // Показываем диалог выбора даты
        datePickerDialog.show();
    }

    // Интерфейс для передачи выбранной даты
    public interface DateSelectedListener {
        void onDateSelected(String selectedDateStr);
    }
}
