package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.CreateMenuController.menuListView;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Adapters.RecyclerAdapterProduct;
import com.example.cookmasteraplication.Adapters.RecyclerAdapterStep;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Utils.CommonTools;
import com.example.cookmasteraplication.Views.RecipeDetailsActivity;
import com.example.cookmasteraplication.api.Models.Product;
import com.example.cookmasteraplication.api.Models.Recipe;
import com.example.cookmasteraplication.api.Models.Step;
import com.example.cookmasteraplication.api.Models.UpdateRecipe;
import com.example.cookmasteraplication.api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.api.Services.IRecipeService;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeDetailsControler {

    //    public static ArrayList<Recipe> favouriteRecipes = new ArrayList<>();
    private final RecipeDetailsActivity activity;
    private final String email;
    private final String password;
    private final Integer IdUser;
    private final SharedPreferencesActivities sharedPref;
    private final ArrayList<Integer> productsAmounts = new ArrayList<>();
    private final ArrayList<String> productsNames = new ArrayList<>();
    private final ArrayList<String> productsAmountsUnits = new ArrayList<>();
    private final ArrayList<Integer> stepsNumbers = new ArrayList<>();
    private final ArrayList<String> stepsDescriptions = new ArrayList<>();
    private int cardItemPosition = 0;
    private byte[] imageByte;
    private String recipeName;
    private Integer mealCount;
    private Integer prepareTime;
    private Double rate;
    private Double popularity;
    private String recipeDesc;
    private Integer IdRecipe;
    private String recipeCategory;

    public RecipeDetailsControler(RecipeDetailsActivity activity) {
        this.activity = activity;
        this.sharedPref = new SharedPreferencesActivities(this.activity);
        this.email = sharedPref.getUserEmail();
        this.password = sharedPref.getUserPass();
        this.IdUser = sharedPref.getUserId();
    }

    public void setToolbarLogo(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }

    public void setToolbarMenu(MaterialToolbar toolbar) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setMenuOItemOnClickListeners().build();
    }

    public void getRecipeFromCard(@NonNull Intent intent) {
        // intent card item position
        cardItemPosition = intent.getExtras().getInt("cardPosition", 0);
        imageByte = intent.getExtras().getByteArray("recipeImage");

        IdRecipe = menuListView.get(cardItemPosition).getId();
        recipeName = menuListView.get(cardItemPosition).getName();
        recipeCategory = menuListView.get(cardItemPosition).getCategory();
        mealCount = menuListView.get(cardItemPosition).getMealCount();
        prepareTime = menuListView.get(cardItemPosition).getPrepareTime();
        rate = menuListView.get(cardItemPosition).getRate();
        popularity = menuListView.get(cardItemPosition).getPopularity();
        recipeDesc = menuListView.get(cardItemPosition).getDescription();
        // arraylist with product names and steps
//        productsNames = intent.getExtras().getStringArrayList("productsNames");
//        productsAmountsUnits = intent.getExtras().getStringArrayList("productsAmountsUnits");
//        stepsNumbers = intent.getExtras().getIntegerArrayList("stepsNumbers");
//        stepsDescriptions = intent.getExtras().getStringArrayList("stepsDescriptions");

    }


    public void setProductList(@NonNull RecyclerView recyclerView) {
        ArrayList<Product> products = new ArrayList<>(menuListView.get(cardItemPosition).getProducts());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        RecyclerAdapterProduct adapter = new RecyclerAdapterProduct(products, activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setStepList(@NonNull RecyclerView recyclerView) {
        ArrayList<Step> steps = new ArrayList<>(menuListView.get(cardItemPosition).getSteps());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        RecyclerAdapterStep adapter = new RecyclerAdapterStep(steps, activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setName(@NonNull TextView textView) {
        textView.setText(recipeName);
    }

    public void setMealCount(TextView textView) {
        String mealCountString = "Ilość porcji " + String.format(Locale.ENGLISH, "%d", mealCount);
        textView.setText(mealCountString);
    }

    public void setPrepareTime(TextView textView) {
        String prepareTimeString = "Czas " + String.format(Locale.ENGLISH, "%d", prepareTime) + " min";
        textView.setText(prepareTimeString);
    }

    public void setRate(TextView textView) {
        String RateString = "Ocena " + String.format(Locale.ENGLISH, "%.1f", rate) + " /5";
        textView.setText(RateString);
    }

    public void setPopularity(TextView textView) {
        String PopularityString = "Popularność " + String.format(Locale.ENGLISH, "%.1f", popularity) + " /5";
        textView.setText(PopularityString);
    }

    public void setDescription(TextView textView) {
        String recipeDescString = "Opis\n" + recipeDesc;
        textView.setText(recipeDescString);
    }


    public void saveToFavourites() {
        Retrofit retrofitClient = BaseClient.get_AuthClient(email, password);
        IRecipeService client = retrofitClient.create(IRecipeService.class);
        Call<Recipe> call = client.AddRecipe2Favourites(IdRecipe, email);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.code() == 200) {
//                    favouriteRecipes.add(response.body());
                    Toast.makeText(activity.getApplicationContext(), "Dodałeś przepis  "
                            + recipeName + " do ulubionych", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void rateRecipe(float Userrate) {
        // this function sends api rate by user
        UpdateRecipe updateRecipe = new UpdateRecipe(recipeName, recipeCategory, prepareTime,
                mealCount, (double) Userrate, popularity, recipeDesc);
        Retrofit retrofitClient = BaseClient.get_AuthClient(email, password);
        IRecipeService client = retrofitClient.create(IRecipeService.class);
        Call<Recipe> call = client.UpdateRecipe(updateRecipe, IdRecipe);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.code() == 200) {
                    rate = response.body().getRate();
                    Toast.makeText(activity.getApplicationContext(), "Oceniłeś przepis na  " + Userrate +
                            " gwiazdek", Toast.LENGTH_LONG).show();
                    activity.recreate();
                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setMealImage(ImageView image) {
        image.setImageBitmap(CommonTools.createImagefromBytes(imageByte));
    }


}
