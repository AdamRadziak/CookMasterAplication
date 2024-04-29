package com.example.cookmasteraplication.Models.Adapters;

import android.content.Intent;
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

import com.example.cookmasteraplication.Models.MenuModel;
import com.example.cookmasteraplication.Models.ProductModel;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.RecipeDetailsActivity;

import java.util.ArrayList;

public class RecyclerAdapterRecipe extends RecyclerView.Adapter<RecyclerAdapterRecipe.MenuViewHolder> {

    private final ArrayList<MenuModel> menuList;
    private final ArrayList<Integer> imageList;
    private final AppCompatActivity activity;
    public ArrayList<String> productsNames = new ArrayList<>();
    public ArrayList<String> productsAmounts = new ArrayList<>();

    public RecyclerAdapterRecipe(ArrayList<MenuModel> menuList, ArrayList<Integer> imageList, AppCompatActivity activity) {
        this.menuList = menuList;
        this.imageList = imageList;
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
        return menuList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuModel customMenuItem = menuList.get(position);
        holder.RecipeName.setText(customMenuItem.getRecipe().getName());
        holder.RecipePrepareTime.setText("Czas " + customMenuItem.getRecipe().getPrepareTime());
        holder.RecipeMealCount.setText("Ilość porcji " + customMenuItem.getRecipe().getMealCount());
        holder.RecipeRate.setText("Ocena " + customMenuItem.getRecipe().getRate() + "/5");
        holder.RecipePopular.setText("Popularność " + customMenuItem.getRecipe().getPopularity() + "/5");
        holder.imageViewRecipe.setImageResource(imageList.get(position));
        holder.cardViewRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipeDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("cardPosition",position);
            bundle.putIntegerArrayList("imageList",imageList);
            bundle.putString("recipeName",menuList.get(position).getRecipe().getName());
            bundle.putString("mealCount",menuList.get(position).getRecipe().getMealCount());
            bundle.putString("prepareTime",menuList.get(position).getRecipe().getPrepareTime());
            bundle.putString("rate",menuList.get(position).getRecipe().getRate());
            bundle.putString("popularity",menuList.get(position).getRecipe().getPopularity());
            bundle.putString("recipeDesc",menuList.get(position).getRecipe().getDescription());
            // create 2 string array one with product names second with product amounts
            splitProductListArray2ArrayListString(position);

            bundle.putStringArrayList("productsNames",productsNames);
            bundle.putStringArrayList("productsAmounts",productsAmounts);
            bundle.putStringArrayList("steps",menuList.get(position).getRecipe().getSteps());
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

    private void splitProductListArray2ArrayListString(int position){
        ArrayList<ProductModel> products = menuList.get(position).getRecipe().getProductList();

        for(ProductModel product:products){
            productsNames.add(product.getName());
            productsAmounts.add(product.getAmount());
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
