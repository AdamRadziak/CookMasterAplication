package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.RecipeDetailsControler.GlobalRecipes;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Adapters.GridAdapterProductsFindRecipe;
import com.example.cookmasteraplication.Adapters.RecyclerAdapterRecipe;
import com.example.cookmasteraplication.Api.Models.ProductList;
import com.example.cookmasteraplication.Api.Models.RecipeList;
import com.example.cookmasteraplication.Helpers.CommonTools;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Views.FindRecipeActivity;
import com.example.cookmasteraplication.Api.Models.Product;
import com.example.cookmasteraplication.Api.Models.Recipe;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IProductService;
import com.example.cookmasteraplication.Api.Services.IRecipeService;
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
    private final ProgressBar progressBar;

    public FindRecipeActivity activity;


    public FindRecipeControler(FindRecipeActivity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.sharedPref = new SharedPreferencesActivities(this.activity);
        this.progressBar = progressBar;
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
        RecyclerAdapterRecipe recyclerAdapter = new RecyclerAdapterRecipe(GlobalRecipes, activity);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();

    }

    public void setAutoCompleteTextAdapter(AutoCompleteTextView textView) {
        // get products names from api
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        Retrofit retrofitClient = BaseClient.get_AuthClient(sharedPref.getUserEmail(), sharedPref.getUserPass());
        IProductService client = retrofitClient.create(IProductService.class);
        Call<ProductList> call = client.GetProductList();
        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(@NonNull Call<ProductList> call, @NonNull Response<ProductList> response) {
                if (response.code() == 200) {
                    if(response.body() != null){
                    List<Product> products = response.body().getPageData();
                    // add list of product names to grid adapter
                    for (Product product : products) {
                        productNamesList.add(product.getName());
                    }}
                    else {
                        Toast.makeText(activity.getApplicationContext(),
                                "Nie udało się pobrać zawartości z serwera "
                                ,Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductList> call, @NonNull Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setIndeterminate(false);
            }
        });

        // create array recyclerAdapter to autocomplete text
        ArrayAdapter<String> textAdapter = new ArrayAdapter<>(activity.getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, productNamesList);
        textView.setThreshold(1);
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

    public void searchRecipesByProducts(RecyclerView recyclerView) {
        // get recipes names from api by query params filter and sorts by name
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        Retrofit retrofitClient = BaseClient.get_AuthClient(sharedPref.getUserEmail(), sharedPref.getUserPass());
        IRecipeService client = retrofitClient.create(IRecipeService.class);
        // create query to filter
        String filterQuery ="";
        String sortQuery = "name";
        Call<RecipeList> call = client.GetRecipeList(filterQuery,sortQuery);
        call.enqueue(new Callback<RecipeList>() {
            @Override
            public void onResponse(@NonNull Call<RecipeList> call, @NonNull Response<RecipeList> response) {
                if(response.code() == 200){
                    GlobalRecipes.clear();
                    if(response.body() != null){
                    recipeList.addAll(response.body().getRecipes());
                    filterRecipe();
                    // remove duplicates from globalrecipes
                    GlobalRecipes = CommonTools.removeDuplicates(GlobalRecipes);
                    setRecyclerView(recyclerView);}
                    else{
                        Toast.makeText(activity.getApplicationContext(),
                                "Nie można pobrać zawartości ", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);

                }
                else{
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(),Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeList> call, @NonNull Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setIndeterminate(false);
            }
        });

    }

    public void filterRecipe(){
        double match_prod_count = 0.0;
        for(String productName : addingProducts){
                for(Recipe recipe : recipeList){
                    List<Product> products = recipe.getProducts();
                    for(Product product : products){
                        if(Objects.equals(product.getName(), productName)){
                            // increment match product count
                            match_prod_count ++;
                            if(match_prod_count >= (Math.round((double)addingProducts.size() / 2)))
                            {GlobalRecipes.add(recipe);
                            match_prod_count=0;}
                            break;
                        }
                    }
                }
        }
    }

}
