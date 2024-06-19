package com.example.cookmasteraplication.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Controlers.RecipeDetailsControler;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class RecipeDetailsActivity extends AppCompatActivity {

    MaterialToolbar toolbarLogo;
    MaterialToolbar toolbarMenu;
    ImageButton favouriteBtn;
    RatingBar ratingBar;
    TextView recipeName, mealCount, prepareTime, rate, popularity, description;
    ImageView recipeImage;
    RecyclerView productList, stepList;
    RecipeDetailsControler controller;
    String pageName = "Szczegóły przepisu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_receipe_details);

        toolbarLogo = findViewById(R.id.toolbarLogoRecipeDetails);
        toolbarMenu = findViewById(R.id.toolbarMenuRecipeDetails);
        favouriteBtn = findViewById(R.id.ButtonAddFavourite);
        ratingBar = findViewById(R.id.ratingBar);
        recipeName = findViewById(R.id.textViewRecipeDetName);
        mealCount = findViewById(R.id.textViewRecipeDetMealCount);
        prepareTime = findViewById(R.id.textViewRecipeDetPrepareTime);
        rate = findViewById(R.id.textViewRecipeDetRate);
        popularity = findViewById(R.id.textViewRecipeDetPopular);
        description = findViewById(R.id.TextViewRecipeDetDesc);
        recipeImage = findViewById(R.id.imageViewRecipe);
        productList = findViewById(R.id.productRecycler);
        stepList = findViewById(R.id.stepRecycler);
        ratingBar.setMax(5);
        ratingBar.setNumStars(5);
        controller = new RecipeDetailsControler(this);

        controller.setToolbarLogo(toolbarLogo, pageName);
        controller.setToolbarMenu(toolbarMenu);
        Intent intent = getIntent();
        controller.getRecipeFromCard(intent);
        favouriteBtn.setOnClickListener(v -> controller.saveToFavourites());
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            ratingBar.setRating(rating);
            controller.rateRecipe(rating);
        });
        controller.setName(recipeName);
        controller.setMealCount(mealCount);
        controller.setPrepareTime(prepareTime);
        controller.setRate(rate);
        controller.setPopularity(popularity);
        controller.setDescription(description);
        controller.setMealImage(recipeImage);
        controller.setProductList(productList);
        controller.setStepList(stepList);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.receipeDetailsLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}