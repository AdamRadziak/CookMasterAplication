package com.example.cookmasteraplication.Controlers;


import static com.example.cookmasteraplication.Controlers.RecipeDetailsControler.GlobalRecipes;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Adapters.RecyclerAdapterFavouriteRecipe;
import com.example.cookmasteraplication.Api.Models.RecipeList;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IRecipeService;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Views.FavouritesRecipesActivity;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavouritesRecipesControler {
    private final FavouritesRecipesActivity activity;
    private final SharedPreferencesActivities sharedPref;
    private final ProgressBar progressBar;
    private RecyclerAdapterFavouriteRecipe adapter;

    public FavouritesRecipesControler(FavouritesRecipesActivity activity,
                                      ProgressBar progressBar) {
        this.activity = activity;
        sharedPref = new SharedPreferencesActivities(this.activity);
        this.progressBar = progressBar;
        // set default progressbar values
        this.progressBar.setVisibility(View.INVISIBLE);
        this.progressBar.setIndeterminate(false);
    }

    public void setToolbarLogo(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }

    public void setToolbarMenu(MaterialToolbar toolbar) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setMenuOItemOnClickListeners().build();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        // get from favouritites
        // get list of favourite recipes by api
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        Retrofit retrofitClient = BaseClient.get_AuthClient(sharedPref.getUserEmail(), sharedPref.getUserPass());
        IRecipeService client = retrofitClient.create(IRecipeService.class);
        Call<RecipeList> call = client.ListFavoritesRecipeByUser(sharedPref.getUserId());
        call.enqueue(new Callback<RecipeList>() {
            @Override
            public void onResponse(@NonNull Call<RecipeList> call, @NonNull Response<RecipeList> response) {
                if(response.code()==200){
                    GlobalRecipes.clear();
                    if(response.body().getRecipes() != null){
                    GlobalRecipes.addAll(response.body().getRecipes());
                    // set adapter
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
                    adapter = new RecyclerAdapterFavouriteRecipe(activity,sharedPref,GlobalRecipes);
                    recyclerView.setAdapter(adapter);}
                    else{
                        Toast.makeText(activity.getApplicationContext(),
                                "Nie udało się pobrać zawartości z serwera "
                                        ,Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeList> call, @NonNull Throwable throwable) {
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setIndeterminate(false);
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
