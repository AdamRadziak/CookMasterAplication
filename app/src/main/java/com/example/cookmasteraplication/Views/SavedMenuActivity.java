package com.example.cookmasteraplication.Views;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Controlers.SavedMenuControler;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class SavedMenuActivity extends AppCompatActivity {

    MaterialToolbar toolbarLogo;
    MaterialToolbar toolbarMenu;
    RecyclerView recycler;
    ProgressBar progressBar;
    SavedMenuControler controller;
    String pageName = "Zapisane menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved_menu);

        toolbarLogo = findViewById(R.id.toolbarLogoSavedMenu);
        toolbarMenu = findViewById(R.id.toolbarMenuSavedMenu);
        recycler = findViewById(R.id.recyclerSavedMenu);
        progressBar = findViewById(R.id.progressBarSavedMenu);
        controller = new SavedMenuControler(this,progressBar);

        controller.setToolbarLogo(toolbarLogo, pageName);
        controller.setToolbarMenu(toolbarMenu);
        controller.ListUserMenus(recycler);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.savedMenuLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}