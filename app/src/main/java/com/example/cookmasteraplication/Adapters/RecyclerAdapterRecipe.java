package com.example.cookmasteraplication.Adapters;

import static com.example.cookmasteraplication.Adapters.RecyclerAdapterMenu.productsAmounts;

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
import com.example.cookmasteraplication.api.Models.Product;
import com.example.cookmasteraplication.api.Models.Recipe;
import com.example.cookmasteraplication.api.Models.Step;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapterRecipe extends RecyclerView.Adapter<RecyclerAdapterRecipe.MenuViewHolder> {

    private final ArrayList<Recipe> recipeList;
    private final AppCompatActivity activity;
    private final ArrayList<String> productsAmountsUnits = new ArrayList<>();
    private final ArrayList<Integer> stepsNumbers = new ArrayList<>();
    private final ArrayList<String> stepsDescriptions = new ArrayList<>();
    public ArrayList<String> productsNames = new ArrayList<>();

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
        byte[] imagebyte = customMenuItem.getPhotos().get(0)
                .getData().getBytes(Charset.defaultCharset());
        Bitmap image = CommonTools.createImagefromBytes(imagebyte);
        holder.imageViewRecipe.setImageBitmap(image);
        // get product list and steps list
        List<Product> products = customMenuItem.getProducts();
        List<Step> steps = customMenuItem.getSteps();
        // foreach recipes get steps and products
        for (Product product : products
        ) {
            productsNames.add(product.getName());
            productsAmounts.add(product.getAmount());
            productsAmountsUnits.add(product.getUnit());

        }
        for(Step step: steps){
            stepsNumbers.add(step.getStepNum());
            stepsDescriptions.add(step.getDescription());
        }

        holder.cardViewRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipeDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("cardPosition", position);
            bundle.putInt("IdRecipe", customMenuItem.getId());
            bundle.putString("recipeCategory", customMenuItem.getCategory());
            bundle.putByteArray("recipeImage", imagebyte);
            bundle.putString("recipeName", customMenuItem.getName());
            bundle.putInt("mealCount", customMenuItem.getMealCount());
            bundle.putInt("prepareTime", customMenuItem.getPrepareTime());
            bundle.putDouble("rate", customMenuItem.getRate());
            bundle.putDouble("popularity", customMenuItem.getPopularity());
            bundle.putString("recipeDesc", customMenuItem.getDescription());

            bundle.putStringArrayList("productsNames", productsNames);
            bundle.putStringArrayList("productsAmountsUnits", productsAmountsUnits);
            bundle.putIntegerArrayList("stepsNumbers", stepsNumbers);
            bundle.putStringArrayList("stepsDescriptions", stepsDescriptions);
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
