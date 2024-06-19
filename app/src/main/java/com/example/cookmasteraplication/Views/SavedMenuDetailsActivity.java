package com.example.cookmasteraplication.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Controlers.SavedMenuDetailsControler;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class SavedMenuDetailsActivity extends AppCompatActivity {

    MaterialToolbar toolbarLogo;
    MaterialToolbar toolbarMenu;
    RecyclerView recycler;
    SavedMenuDetailsControler controller;
    String pageName = "Zapisane menu - szczegóły";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved_menu_details);

        toolbarLogo = findViewById(R.id.toolbarLogoSavedMenuDet);
        toolbarMenu = findViewById(R.id.toolbarMenuSavedMenuDet);
        recycler = findViewById(R.id.recyclerSavedMenuDet);
        controller = new SavedMenuDetailsControler(this);

        controller.setToolbarLogo(toolbarLogo, pageName);
        controller.setToolbarMenu(toolbarMenu);
        Intent intent = getIntent();
        controller.getMenuFromCard(intent, "cardPosition");
        controller.setRecyclerView(recycler);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}