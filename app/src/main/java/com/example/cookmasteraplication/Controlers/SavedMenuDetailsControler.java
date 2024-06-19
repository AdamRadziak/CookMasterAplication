package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.RecipeDetailsControler.GlobalRecipes;
import static com.example.cookmasteraplication.Controlers.SavedMenuControler.menuList;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Adapters.RecyclerAdapterMenu;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Views.SavedMenuDetailsActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class SavedMenuDetailsControler {
    private final SavedMenuDetailsActivity activity;
    private int cardItemPosition = 0;
    private RecyclerAdapterMenu adapter;

    public SavedMenuDetailsControler(SavedMenuDetailsActivity activity) {
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

    public void getMenuFromCard(@NonNull Intent intent, String cardPositionintentVar) {
        cardItemPosition = intent.getIntExtra(cardPositionintentVar, 0);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        // create list of recipes
        GlobalRecipes.clear();
        GlobalRecipes.addAll(menuList.get(cardItemPosition).getRecipes());

        // crete example menu
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        adapter = new RecyclerAdapterMenu(GlobalRecipes, activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
