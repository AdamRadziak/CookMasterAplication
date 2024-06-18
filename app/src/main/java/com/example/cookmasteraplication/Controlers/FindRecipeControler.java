package com.example.cookmasteraplication.Controlers;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Adapters.GridAdapterProductsFindRecipe;
import com.example.cookmasteraplication.Adapters.RecyclerAdapterRecipe;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Views.FindRecipeActivity;
import com.example.cookmasteraplication.api.Models.Product;
import com.example.cookmasteraplication.api.Models.Recipe;
import com.example.cookmasteraplication.api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.api.Services.IProductService;
import com.example.cookmasteraplication.api.Services.IRecipeService;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FindRecipeControler {
    private final SharedPreferencesActivities sharedPref;
    private final ArrayList<String> productNamesList = new ArrayList<>();
    private final ArrayList<String> addingProducts = new ArrayList<>();
    private final ArrayList<Recipe> recipeList = new ArrayList<>();
    // for filter recipes
    private final ArrayList<Recipe> recipeMatchingList = new ArrayList<>();

    public FindRecipeActivity activity;


    public FindRecipeControler(FindRecipeActivity activity) {
        this.activity = activity;
        this.sharedPref = new SharedPreferencesActivities(this.activity);
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
        RecyclerAdapterRecipe recyclerAdapter = new RecyclerAdapterRecipe(recipeMatchingList, activity);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();

    }

    public void setAutoCompleteTextAdapter(AutoCompleteTextView textView) {
        // get products names from api
        Retrofit retrofitClient = BaseClient.get_AuthClient(sharedPref.getUserEmail(), sharedPref.getUserPass());
        IProductService client = retrofitClient.create(IProductService.class);
        Call<List<Product>> call = client.GetProductList();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.code() == 200) {
                    // add list of product names to grid adapter
                    for (Product product : response.body()) {
                        productNamesList.add(product.getName());
                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // create array recyclerAdapter to autocomplete text
        ArrayAdapter<String> textAdapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, productNamesList);
        textView.setThreshold(3);
        textView.setAdapter(textAdapter);
    }

    public void addProductToGridView(Intent intent) {
        String productName = intent.getStringExtra("productName");
        addingProducts.add(productName);
    }

    public void setGridView(GridView gridView) {
        GridAdapterProductsFindRecipe gridAdapter = new GridAdapterProductsFindRecipe(activity, addingProducts);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }

    public void searchRecipesByProducts() {
        // get recipes names from api by query params filter and sorts by name
        Retrofit retrofitClient = BaseClient.get_AuthClient(sharedPref.getUserEmail(), sharedPref.getUserPass());
        IRecipeService client = retrofitClient.create(IRecipeService.class);
        // create query to filter
        String filterQuery ="";
        String sortQuery = "name";
        Call<List<Recipe>> call = client.GetRecipeList(filterQuery,sortQuery);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.code() == 200){
                    for(Recipe recipe: response.body()){
                        recipeList.add(recipe);
                    }
                }
                else{
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        // filter recipelist by product add
        filterRecipe();


    }

    private void filterRecipe(){
        for(String productName : addingProducts){
                for(Recipe recipe : recipeList){
                    List<Product> products = recipe.getProducts();
                    for(Product product : products){
                        if(Objects.equals(product.getName(), productName)){
                            recipeMatchingList.add(recipe);
                            break;
                        }
                    }
                }
        }
    }

}
