package com.example.cookmasteraplication.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Controlers.CreateMenuController;
import com.example.cookmasteraplication.Models.SaveDialogBox;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class CreateMenuActivity extends AppCompatActivity {

    Button find;
    Button save;
    EditText nameMenu;
    Spinner spinner_day;
    Spinner spinner_mealCount;
    Spinner spinner_time;
    Spinner spinner_rate;
    Spinner spinner_popularity;
    RecyclerView menuItems;
    MaterialToolbar toolbarLogo;
    MaterialToolbar toolbarMenu;
    CreateMenuController controller;
    String pageName = "Utwórz menu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_menu);

        find = findViewById(R.id.buttonSearchMenu);
        save = findViewById(R.id.buttonSaveMenu);
        nameMenu = findViewById(R.id.editTextMenuName);
        spinner_day = findViewById(R.id.spinnerMenuCreateDays);
        spinner_mealCount = findViewById(R.id.spinnerMenuMealCount);
        spinner_time = findViewById(R.id.spinnerMenuCreatePrepareTime);
        spinner_rate = findViewById(R.id.spinnerMenuCreateRate);
        spinner_popularity = findViewById(R.id.spinnerMenuCreatePopularity);
        menuItems = findViewById(R.id.recyclerMenuCreation);
        toolbarMenu = findViewById(R.id.toolbarMenuMenuCreate);
        toolbarLogo = findViewById(R.id.toolbarLogoMenuCreate);
        controller = new CreateMenuController(this);
        Toast.makeText(this,"Poprawnie zalogowano",Toast.LENGTH_SHORT).show();
        // set adapters for spinners
        controller.setSpinnerArrayAdapter(spinner_day, R.array.DaysCount);
        controller.setSpinnerArrayAdapter(spinner_mealCount, R.array.MealCount);
        controller.setSpinnerArrayAdapter(spinner_time, R.array.MealPrepareTime);
        controller.setSpinnerArrayAdapter(spinner_rate, R.array.MealRate);
        controller.setSpinnerArrayAdapter(spinner_popularity, R.array.MealPopularity);
        // create toolbar
        controller.setToolbarLogo(toolbarLogo, pageName);
        controller.setToolbarMenu(toolbarMenu);
//        controller.setRecyclerView(menuItems);
        Intent intent = new Intent(this, this.getClass());
        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String daysCount = parent.getItemAtPosition(position).toString();
                intent.putExtra("daysCount", daysCount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinner_mealCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mealCount = parent.getItemAtPosition(position).toString();
                intent.putExtra("mealCount", mealCount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String prepareTime = parent.getItemAtPosition(position).toString();
                intent.putExtra("prepareTime", prepareTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinner_rate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rate = parent.getItemAtPosition(position).toString();
                intent.putExtra("rate", rate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinner_popularity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String popularity = parent.getItemAtPosition(position).toString();
                intent.putExtra("popularity", popularity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        // on click listeners
        find.setOnClickListener(v -> {
            controller.findMenus(intent, "daysCount",
                    "mealCount", "prepareTime",
                    "rate", "popularity");
            controller.setRecyclerView(menuItems);
        });
        save.setOnClickListener(v -> {
            String menuNameText = nameMenu.getText().toString();
            controller.saveMenus(menuNameText);
        });
        controller.setRecyclerView(menuItems);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createMenulayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.setRecyclerView(menuItems);
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.setRecyclerView(menuItems);
    }
}