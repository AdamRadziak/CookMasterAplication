package com.example.cookmasteraplication.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Controlers.FindRecipeControler;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class FindRecipeActivity extends AppCompatActivity {

    MaterialToolbar toolbarLogo;
    MaterialToolbar toolbarMenu;
    RecyclerView recipeList;
    AutoCompleteTextView productSearch;
    ProgressBar progressBar;
    ImageButton addButton;
    ImageButton searchButton;
    GridView productsSearchItems;
    FindRecipeControler controller;
    String pageName = "Wyszukaj przepis ze składników";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_recipe);

        toolbarLogo = findViewById(R.id.toolbarLogoFindRecipe);
        toolbarMenu = findViewById(R.id.toolbarMenuFindRecipe);
        productSearch = findViewById(R.id.textViewAddProd);
        addButton = findViewById(R.id.buttonAddProd);
        searchButton = findViewById(R.id.buttonSearchRecipe);
        progressBar = findViewById(R.id.progressBarFindRecipe);
        recipeList = findViewById(R.id.recyclerFindRecipe);
        productsSearchItems = findViewById(R.id.gridViewProducts);

        controller = new FindRecipeControler(this,progressBar);
        Intent intent = new Intent(this, this.getClass());

        controller.setToolbarLogo(toolbarLogo,pageName);
        controller.setToolbarMenu(toolbarMenu);

        controller.setAutoCompleteTextAdapter(productSearch);
        addButton.setOnClickListener(v -> {
            String productName = productSearch.getText().toString();
            if(productName.isEmpty()){
                Toast.makeText(getApplicationContext(),"Nie wprowadzono żadnego produktu",Toast.LENGTH_SHORT).show();
            }else {
                intent.putExtra("productName",productName);
                controller.addProductToGridView(intent);
                controller.setGridView(productsSearchItems);
            }
        });
        searchButton.setOnClickListener(v -> controller.searchRecipesByProducts(recipeList));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.findRecipeLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}