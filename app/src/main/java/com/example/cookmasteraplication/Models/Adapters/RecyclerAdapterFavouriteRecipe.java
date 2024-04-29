package com.example.cookmasteraplication.Models.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Models.MenuModel;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.RecipeDetailsActivity;

import java.util.ArrayList;

public class RecyclerAdapterFavouriteRecipe extends RecyclerView.Adapter<RecyclerAdapterFavouriteRecipe.MenuViewHolder> {

    private final ArrayList<MenuModel> menuList;
    private final ArrayList<Integer> imageList;
    private final AppCompatActivity activity;

    public RecyclerAdapterFavouriteRecipe(ArrayList<MenuModel> menuList, ArrayList<Integer> imageList, AppCompatActivity activity) {
        this.menuList = menuList;
        this.imageList = imageList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_recipe_card, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuModel customMenuItem = menuList.get(position);
        holder.FavouriteRecipeName.setText(customMenuItem.getRecipe().getName());
        holder.FavouriteRecipePrepareTime.setText("Czas " + customMenuItem.getRecipe().getPrepareTime());
        holder.FavouriteRecipeMealCount.setText("Ilośc porcji " + customMenuItem.getRecipe().getMealCount());
        holder.FavouriteRecipeRate.setText("Ocena " + customMenuItem.getRecipe().getRate() + "/5");
        holder.FavouriteRecipePopular.setText("Popularność " + customMenuItem.getRecipe().getPopularity() + "/5");
        holder.imageViewFavouriteRecipe.setImageResource(imageList.get(position));
        holder.cardViewFavouriteRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipeDetailsActivity.class);
            intent.putExtra("cardPosition", position);
            activity.startActivity(intent);
        });
        holder.delete.setOnClickListener(v -> {
            showDialogMsg(holder.getAdapterPosition());
        });
        // set backgorund for card
        if (position % 2 == 0) {
            holder.cardViewFavouriteRecipe.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorOnPrimaryLight));
        } else {
            holder.cardViewFavouriteRecipe.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimaryLight));
        }

    }

    private void showDialogMsg(int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity, R.style.DialogTheme);
        alertDialog.setTitle("Usuwanie menu").setMessage("Czy chcesz usunąć całe zapisane menu?")
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialoge message
                        dialog.dismiss();
                    }
                }).setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete text in result textview
                        menuList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }).show();
        alertDialog.create();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView FavouriteRecipeName;
        private final TextView FavouriteRecipePrepareTime;
        private final TextView FavouriteRecipeMealCount;
        private final TextView FavouriteRecipeRate;
        private final TextView FavouriteRecipePopular;
        private final ImageView imageViewFavouriteRecipe;
        private final ImageButton delete;
        private final CardView cardViewFavouriteRecipe;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            FavouriteRecipeName = itemView.findViewById(R.id.textViewFavouriteName);
            FavouriteRecipePrepareTime = itemView.findViewById(R.id.textViewFavouritePrepareTime);
            FavouriteRecipeMealCount = itemView.findViewById(R.id.textViewFavouriteMealCount);
            FavouriteRecipeRate = itemView.findViewById(R.id.textViewFavouriteRate);
            FavouriteRecipePopular = itemView.findViewById(R.id.textViewFavouritePopular);
            imageViewFavouriteRecipe = itemView.findViewById(R.id.imageViewFavourite);
            cardViewFavouriteRecipe = itemView.findViewById(R.id.cardViewFavouriteRecipe);
            delete = itemView.findViewById(R.id.imageButtonDeleteFavourite);


        }
    }
}
