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

public class RecyclerAdapterRecipe extends RecyclerView.Adapter<RecyclerAdapterRecipe.MenuViewHolder> {

    private final ArrayList<Recipe> recipeList;
    private final AppCompatActivity activity;

    public RecyclerAdapterRecipe(ArrayList<Recipe> recipeList, AppCompatActivity activity) {
        this.recipeList = recipeList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Recipe customMenuItem = recipeList.get(position);
        // string for labels
        String prepareTimeString = "Czas " + String.format(Locale.ENGLISH,"%d",
                customMenuItem.getPrepareTime());
        String mealCountString = "Ilość porcji " + String.format(Locale.ENGLISH,"%d",
                customMenuItem.getMealCount());
        String rateString = "Ocena " + String.format(Locale.ENGLISH,"%.1f",
                customMenuItem.getRate()) + "/5";
        String popularityString = "Popularność " + String.format(Locale.ENGLISH,"%.1f",
                customMenuItem.getPopularity()) + "/5";
        holder.RecipeName.setText(customMenuItem.getName());
        holder.RecipePrepareTime.setText(prepareTimeString);
        holder.RecipeMealCount.setText(mealCountString);
        holder.RecipeRate.setText(rateString);
        holder.RecipePopular.setText(popularityString);
        // set drawable by first photo for recipe
        String imagebyte = customMenuItem.getPhotos().get(0)
                .getData();
        Drawable image = CommonTools.createImagefromBytes(imagebyte,activity);
        holder.imageViewRecipe.setImageDrawable(image);
        // get product list and steps list

        holder.cardViewRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipeDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("cardPosition", position);
            bundle.putString("recipeImage", imagebyte);
            // put extras by bundle
            intent.putExtras(bundle);
            activity.startActivity(intent);
        });
        // set backgorund for card
        if (position % 2 == 0) {
            holder.cardViewRecipe.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorOnPrimaryLight));
        } else {
            holder.cardViewRecipe.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimaryLight));
        }

    }



    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private final TextView RecipeName;
        private final TextView RecipePrepareTime;
        private final TextView RecipeMealCount;
        private final TextView RecipeRate;
        private final TextView RecipePopular;
        private final ImageView imageViewRecipe;
        private final CardView cardViewRecipe;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            RecipeName = itemView.findViewById(R.id.textViewRecipeName);
            RecipePrepareTime = itemView.findViewById(R.id.textViewRecipePrepareTime);
            RecipeMealCount = itemView.findViewById(R.id.textViewRecipeMealCount);
            RecipeRate = itemView.findViewById(R.id.textViewRecipeRate);
            RecipePopular = itemView.findViewById(R.id.textViewRecipePopular);
            imageViewRecipe = itemView.findViewById(R.id.imageViewRecipe);
            cardViewRecipe = itemView.findViewById(R.id.cardViewRecipe);


        }
    }
}
