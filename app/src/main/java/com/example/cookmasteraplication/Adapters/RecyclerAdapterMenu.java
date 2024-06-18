package com.example.cookmasteraplication.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Utils.CommonTools;
import com.example.cookmasteraplication.Views.RecipeDetailsActivity;
import com.example.cookmasteraplication.api.Models.Recipe;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapterMenu extends RecyclerView.Adapter<RecyclerAdapterMenu.MenuViewHolder> {

    public static ArrayList<Double> productsAmounts = new ArrayList<>();
    private final ArrayList<Recipe> menuList;
    private final AppCompatActivity activity;
    private final ArrayList<String> productsNames = new ArrayList<>();
    private final ArrayList<String> productsAmountsUnits = new ArrayList<>();
    private final ArrayList<Integer> stepsNumbers = new ArrayList<>();
    private final ArrayList<String> stepsDescriptions = new ArrayList<>();

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
        byte[] imagebyte = customMenuItem.getPhotos().get(0).getData().getBytes(Charset.defaultCharset());
        // set drawable by first photo for recipe
        Bitmap image = CommonTools.createImagefromBytes(imagebyte);
        holder.imageViewMeal.setImageBitmap(image);
//        // get product list and steps list
//        List<Recipe> recipes = customMenuItem.;
//        List<Product> products = new ArrayList<>();
//        List<Step> steps = new ArrayList<>();
//        // foreach recipes get steps and products
//        for(int i=0;i< recipes.size();i++) {
//            products.addAll(recipes.get(i).getProducts());
//            steps.addAll(recipes.get(i).getSteps());
//        }
//        for (Product product : products
//        ) {
//            productsNames.add(product.getName());
//            productsAmounts.add(product.getAmount());
//            productsAmountsUnits.add(product.getUnit());
//
//        }
//        for(Step step: steps){
//            stepsNumbers.add(step.getStepNum());
//            stepsDescriptions.add(step.getDescription());
//        }

        holder.cardViewMeal.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), RecipeDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("cardPosition", position);
            bundle.putByteArray("recipeImage", imagebyte);
//            bundle.putInt("IdRecipe", customMenuItem.getId());
//            bundle.putString("recipeCategory", mealCategory);
//            bundle.putString("recipeName", recipeName);
//            bundle.putInt("mealCount", customMenuItem..get(position).getMealCount());
//            bundle.putInt("prepareTime", customMenuItem.get(position).getPrepareTime());
//            bundle.putDouble("rate", customMenuItem.get(position).getRate());
//            bundle.putDouble("popularity", customMenuItem.get(position).getPopularity());
//            bundle.putString("recipeDesc", recipeDesc);
//
//            bundle.putStringArrayList("productsNames", productsNames);
//            bundle.putStringArrayList("productsAmountsUnits", productsAmountsUnits);
//            bundle.putIntegerArrayList("stepsNumbers", stepsNumbers);
//            bundle.putStringArrayList("stepsDescriptions", stepsDescriptions);
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
