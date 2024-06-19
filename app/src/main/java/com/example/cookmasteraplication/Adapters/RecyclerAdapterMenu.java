package com.example.cookmasteraplication.Adapters;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Api.Models.Recipe;
import com.example.cookmasteraplication.Helpers.CommonTools;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.RecipeDetailsActivity;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapterMenu extends RecyclerView.Adapter<RecyclerAdapterMenu.MenuViewHolder> {

    private final ArrayList<Recipe> menuList;
    private final AppCompatActivity activity;


    public RecyclerAdapterMenu(ArrayList<Recipe> menuList, AppCompatActivity activity) {
        this.menuList = menuList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Recipe customMenuItem = menuList.get(position);
        String prepareTimeString = "Czas " + String.format(Locale.ENGLISH, "%d",
                customMenuItem.getPrepareTime()) + " min";
        String mealRateString = "Ocena " + String.format(Locale.ENGLISH, "%.1f",
                customMenuItem.getRate()) + " /5";
        String popularityString = "Popularność " + String.format(Locale.ENGLISH, "%.1f",
                customMenuItem.getPopularity()) + " /5";

        String recipeDesc = "Opis\n" + customMenuItem.getDescription();
        String recipeName = customMenuItem.getName();
        String mealCategory = customMenuItem.getCategory();
        // set in adapter
        holder.mealCateogry.setText(mealCategory);
        holder.mealName.setText(recipeName);
        holder.mealPrepareTime.setText(prepareTimeString);
        holder.mealRate.setText(mealRateString);
        holder.mealPopular.setText(popularityString);
        // set drawable by first photo from recipe
        String imagebyte = customMenuItem.getPhotos().get(0).getData();
        // set drawable by first photo for recipe
        Drawable image = CommonTools.createImagefromBytes(imagebyte,activity);
        holder.imageViewMeal.setImageDrawable(image);

        holder.cardViewMeal.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), RecipeDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("cardPosition", position);
            bundle.putString("recipeImage", imagebyte);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        });
        // set backgorund for card
        if (position % 2 == 0) {
            holder.cardViewMeal.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorOnPrimaryLight));
        } else {
            holder.cardViewMeal.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimaryLight));
        }

    }



    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private final TextView mealCateogry;
        private final TextView mealName;
        private final TextView mealPrepareTime;
        private final TextView mealRate;
        private final TextView mealPopular;
        private final ImageView imageViewMeal;
        private final CardView cardViewMeal;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            mealCateogry = itemView.findViewById(R.id.textViewMealCategory);
            mealName = itemView.findViewById(R.id.textViewMealName);
            mealPrepareTime = itemView.findViewById(R.id.textViewMealPrepareTime);
            mealRate = itemView.findViewById(R.id.textViewMealRate);
            mealPopular = itemView.findViewById(R.id.textViewMealPopular);
            imageViewMeal = itemView.findViewById(R.id.imageViewMeal);
            cardViewMeal = itemView.findViewById(R.id.cardViewMeal);


        }
    }
}
