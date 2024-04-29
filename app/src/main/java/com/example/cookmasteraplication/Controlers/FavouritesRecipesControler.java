package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.RecipeDetailsControler.favouriteMenuItem;
import static com.example.cookmasteraplication.Controlers.RecipeDetailsControler.favouriteRecipeImage;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Models.Adapters.RecyclerAdapterFavouriteRecipe;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.Views.FavouritesRecipesActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class FavouritesRecipesControler {
    private final FavouritesRecipesActivity activity;
    private RecyclerAdapterFavouriteRecipe adapter;

    public FavouritesRecipesControler(FavouritesRecipesActivity activity) {
        this.activity = activity;
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
        // crete example menu
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        adapter = new RecyclerAdapterFavouriteRecipe(favouriteMenuItem, favouriteRecipeImage, activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
