package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.CreateMenuController.imageListSave;
import static com.example.cookmasteraplication.Controlers.CreateMenuController.menuListSave;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Models.Adapters.RecyclerAdapterMenu;
import com.example.cookmasteraplication.Models.MenuModel;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.Views.SavedMenuDetailsActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class SavedMenuDetailsControler {
    private final SavedMenuDetailsActivity activity;

    private int cardItemPosition = 0;

    private ArrayList<MenuModel> menuView;

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
        menuView = menuListSave.get(cardItemPosition).getMenu();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        // crete example menu
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        adapter = new RecyclerAdapterMenu(menuView, imageListSave, activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
