package com.example.cookmasteraplication.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Api.Models.Recipe;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IRecipeService;
import com.example.cookmasteraplication.Helpers.CommonTools;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.RecipeDetailsActivity;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecyclerAdapterFavouriteRecipe extends RecyclerView.Adapter<RecyclerAdapterFavouriteRecipe.MenuViewHolder> {

    private final AppCompatActivity activity;
    private final SharedPreferencesActivities sharedPref;
    private final List<Recipe> recipelist;

    public RecyclerAdapterFavouriteRecipe(AppCompatActivity activity,
    SharedPreferencesActivities sharedPref,List<Recipe> recipelist) {
        this.activity = activity;
        this.sharedPref = sharedPref;
        this.recipelist = recipelist;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_recipe_card, parent,
                false);

        return new MenuViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return recipelist.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Recipe customMenuItem = recipelist.get(position);
        // string for labels
        String prepareTimeString = "Czas " + String.format(Locale.ENGLISH,
                "%d",customMenuItem.getPrepareTime()) + " min";
        String mealCountString = "Ilość porcji " + String.format(Locale.ENGLISH,
                "%d",customMenuItem.getMealCount()) ;
        String rateString = "Ocena " + String.format(Locale.ENGLISH,
                "%.1f",customMenuItem.getRate()) + "/5" ;
        String popularityString = "Popularność " + String.format(Locale.ENGLISH,
                "%.1f",customMenuItem.getPopularity()) + "/5" ;
        holder.FavouriteRecipeName.setText(customMenuItem.getName());
        holder.FavouriteRecipePrepareTime.setText(prepareTimeString);
        holder.FavouriteRecipeMealCount.setText(mealCountString);
        holder.FavouriteRecipeRate.setText(rateString);
        holder.FavouriteRecipePopular.setText(popularityString);
        // set drawable by first photo from recipe
        String imagebyte = customMenuItem.getPhotos().get(0)
                .getData();
        // set drawable by first photo for recipe
        Drawable image = CommonTools.createImagefromBytes(imagebyte,activity);
        holder.imageViewFavouriteRecipe.setImageDrawable(image);
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
                        // delete from database by api
                        removeFromFavourities(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }).show();
        alertDialog.create();
    }

    private void removeFromFavourities(int position){
        // get from shared preferences IdMenu
        Retrofit retrofitClient = BaseClient.get_AuthClient(sharedPref.getUserEmail(),
                sharedPref.getUserPass());
        IRecipeService client = retrofitClient.create(IRecipeService.class);
        Call<Recipe> call = client.DeleteRecipeFromFavourites(recipelist.get(position).getId(),sharedPref.getUserEmail());
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.code()==204){
                    Toast.makeText(activity.getApplicationContext(),
                            "Pomyślnie usunięto przepis z ulubionych",Toast.LENGTH_LONG)
                            .show();
                    recipelist.remove(position);
                    notifyDataSetChanged();
                }
                else{
                    Toast.makeText(activity.getApplicationContext(),
                                    "Błąd " + response.message(),Toast.LENGTH_LONG)
                            .show();

                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),
                                "Błąd " + throwable.getMessage(),Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private final TextView FavouriteRecipePrepareTime;
        private final TextView FavouriteRecipeMealCount;
        private final TextView FavouriteRecipeRate;
        private final TextView FavouriteRecipePopular;
        private final ImageView imageViewFavouriteRecipe;
        private final ImageButton delete;
        private final CardView cardViewFavouriteRecipe;
        private final TextView FavouriteRecipeName;

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
