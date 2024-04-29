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

public class RecyclerAdapterMenu extends RecyclerView.Adapter<RecyclerAdapterMenu.MenuViewHolder> {

    private final ArrayList<MenuModel> menuList;
    private final ArrayList<Integer> imageList;
    private final AppCompatActivity activity;
    public ArrayList<String> productsNames = new ArrayList<>();
    public ArrayList<String> productsAmounts = new ArrayList<>();

    public RecyclerAdapterMenu(ArrayList<MenuModel> menuList, ArrayList<Integer> imageList, AppCompatActivity activity) {
        this.menuList = menuList;
        this.imageList = imageList;
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
        MenuModel customMenuItem = menuList.get(position);
        holder.mealCateogry.setText(customMenuItem.getName());
        holder.mealName.setText(customMenuItem.getMeal().getName());
        holder.mealPrepareTime.setText("Czas " + customMenuItem.getMeal().getPrepareTime());
        holder.mealRate.setText("Ocena " + customMenuItem.getMeal().getRate() + "/5");
        holder.mealPopular.setText("Popularność " + customMenuItem.getMeal().getPopularity() + "/5");
        holder.imageViewMeal.setImageResource(imageList.get(position));
        holder.cardViewMeal.setOnClickListener(v -> {

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
            holder.cardViewMeal.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorOnPrimaryLight));
        } else {
            holder.cardViewMeal.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimaryLight));
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
