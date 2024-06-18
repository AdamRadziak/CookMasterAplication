package com.example.cookmasteraplication.Controlers;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Adapters.RecyclerAdapterFavouriteRecipe;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Views.FavouritesRecipesActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class FavouritesRecipesControler {
    private final FavouritesRecipesActivity activity;
    private RecyclerAdapterFavouriteRecipe adapter;
    private final SharedPreferencesActivities sharedPref;

    public FavouritesRecipesControler(FavouritesRecipesActivity activity) {
        this.activity = activity;
        sharedPref = new SharedPreferencesActivities(this.activity);
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
        adapter = new RecyclerAdapterFavouriteRecipe(activity,sharedPref);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
