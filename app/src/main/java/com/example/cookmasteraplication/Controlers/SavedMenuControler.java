package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.CreateMenuController.imageListSave;
import static com.example.cookmasteraplication.Controlers.CreateMenuController.menuListSave;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Models.Adapters.RecyclerAdapterSavedMenu;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.Views.SavedMenuActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class SavedMenuControler {

    private final SavedMenuActivity activity;
    private RecyclerAdapterSavedMenu adapter;

    public SavedMenuControler(SavedMenuActivity activity) {
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
        adapter = new RecyclerAdapterSavedMenu(menuListSave, imageListSave, activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
