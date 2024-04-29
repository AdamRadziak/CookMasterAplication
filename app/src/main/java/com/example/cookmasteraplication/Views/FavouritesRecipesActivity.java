package com.example.cookmasteraplication.Views;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Controlers.FavouritesRecipesControler;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class FavouritesRecipesActivity extends AppCompatActivity {

    MaterialToolbar toolbarLogo;
    MaterialToolbar toolbarMenu;
    RecyclerView favouriteMenuitem;
    FavouritesRecipesControler controller;
    String pageName = "ulubione przepisy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourites_recipes);

        toolbarLogo = findViewById(R.id.toolbarLogoFavourites);
        toolbarMenu = findViewById(R.id.toolbarMenuFavourites);
        favouriteMenuitem = findViewById(R.id.recyclerFavourites);
        controller = new FavouritesRecipesControler(this);

        controller.setToolbarLogo(toolbarLogo, pageName);
        controller.setToolbarMenu(toolbarMenu);
        controller.setRecyclerView(favouriteMenuitem);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.favouritesLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}