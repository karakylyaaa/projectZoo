package com.example.projectzoo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectzoo.Models.Animal;
import com.example.projectzoo.R;

import java.util.List;

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.AnimalViewHolder> {

    private Context context;
    private List<Animal> animalList;
    private OnItemClickListener onItemClickListener;

    // Интерфейс для обработки кликов
    public interface OnItemClickListener {
        void onItemClick(Animal animal);
    }

    // Конструктор адаптера
    public AnimalsAdapter(Context context, List<Animal> animalList, OnItemClickListener listener) {
        this.context = context;
        this.animalList = animalList;
        this.onItemClickListener = listener;
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);

        // Базовый URL вашего сервера
        String baseUrl = "http://10.0.2.2:5191/";  // Замените на адрес вашего сервера

        if (animal.getPhotos() != null && !animal.getPhotos().isEmpty()) {
            // Формируем полный URL для первого изображения
            String imageUrl = baseUrl + animal.getPhotos().get(0).getPhoto();  // Путь к фото из базы данных

            // Загружаем изображение с помощью Glide
            Glide.with(context)
                    .load(imageUrl)
                    .error(R.drawable.logo_zoo)  // Изображение по умолчанию в случае ошибки
                    .into(holder.animalImage);
        }

        // Устанавливаем имя животного
        holder.animalName.setText(animal.getName());

        // Обработка клика по элементу
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(animal));
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    // Вьюхолдер для элемента списка
    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        ImageView animalImage;
        TextView animalName;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            animalImage = itemView.findViewById(R.id.ivAnimalImage);
            animalName = itemView.findViewById(R.id.tvAnimalName);
        }
    }
}
