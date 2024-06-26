package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.RecipeDetailsControler.GlobalRecipes;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Adapters.RecyclerAdapterMenu;
import com.example.cookmasteraplication.Api.Models.GenerateUserMenu;
import com.example.cookmasteraplication.Api.Models.PageDatumUserMenu;
import com.example.cookmasteraplication.Api.Models.Recipe;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IUserMenuService;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Views.CreateMenuActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateMenuController {

    public static ArrayList<Recipe> menuListView = new ArrayList<>();

    private final CreateMenuActivity activity;
    private final SharedPreferencesActivities sharedPref;
    private final ProgressBar progressBar;


    public CreateMenuController(CreateMenuActivity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.sharedPref = new SharedPreferencesActivities(this.activity);
        this.progressBar = progressBar;
        // set default progressbar values
        this.progressBar.setVisibility(View.INVISIBLE);
        this.progressBar.setIndeterminate(false);
    }


    public void setSpinnerArrayAdapter(@NonNull Spinner spinner, int string_list_id) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getApplicationContext(),
                string_list_id, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public void setToolbarLogo(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }

    public void setToolbarMenu(MaterialToolbar toolbar) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setMenuOItemOnClickListeners().build();
    }

    public boolean findMenus(@NonNull Intent intent, String daysCountIntentVar, String mealCountIntentVar,
                          String prepareTimeIntentVar, String rateIntentVar, String popularityIntentVar,
                             String menuNameIntentvar,RecyclerView recyclerView) {
        /// this method send post request to find recipes with parameters
        // if server get code 200 then message and container results is displayed

        String menuName = intent.getStringExtra(menuNameIntentvar);
        Integer daysCount = Integer.parseInt(intent.getStringExtra(daysCountIntentVar));
        Integer mealCount = Integer.parseInt(intent.getStringExtra(mealCountIntentVar));
        String prepareTimeString = intent.getStringExtra(prepareTimeIntentVar);
        prepareTimeString = prepareTimeString.replace(" min","");
        Integer prepareTime = Integer.parseInt(prepareTimeString);
        Double rate = Double.parseDouble(intent.getStringExtra(rateIntentVar));
        Double popularity = Double.parseDouble(intent.getStringExtra(popularityIntentVar));
        return GenerateMenu(menuName, daysCount, mealCount, rate, popularity, prepareTime, recyclerView);

    }




    public void setRecyclerView(RecyclerView recyclerView) {
        // crete example menu
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        RecyclerAdapterMenu adapter = new RecyclerAdapterMenu(menuListView, activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private boolean GenerateMenu(String menuName, Integer dayCount, Integer mealCount,
                              Double rate, Double popularity, Integer prepareTime,
                                 RecyclerView recyclerView) {
        final boolean[] isSuccess = {true};
        // get id user from shared-preferences
        Integer idUser = sharedPref.getUserId();
        String email = sharedPref.getUserEmail();
        String password = sharedPref.getUserPass();
        // create new object for generate user menu
        if (!menuName.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            GenerateUserMenu generateUserMenu = new GenerateUserMenu(menuName, idUser, "domyślna",
                    dayCount, mealCount, rate, popularity, prepareTime);
            Retrofit retrofitClient = BaseClient.get_AuthClient(email, password);
            IUserMenuService client = retrofitClient.create(IUserMenuService.class);
            Call<PageDatumUserMenu> call = client.GenerateUserMenu(generateUserMenu);
            call.enqueue(new Callback<PageDatumUserMenu>() {
                @Override
                public void onResponse(@NonNull Call<PageDatumUserMenu> call, @NonNull Response<PageDatumUserMenu> response) {
                    if (response.code() == 201){
                        // delete all from GlobalRecipes
                        GlobalRecipes.clear();
                        if(response.body() != null){
                        List<Recipe> body = response.body().getRecipes();
                        GlobalRecipes.addAll(body);
                        Toast.makeText(activity.getApplicationContext(),"Pomyślnie wygenerowano menu" +
                                "o nazwie " + menuName,Toast.LENGTH_LONG).show();
                        isSuccess[0] = true;
                        // set recycler view
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
                        RecyclerAdapterMenu adapter = new RecyclerAdapterMenu(GlobalRecipes, activity);
                        recyclerView.setAdapter(adapter);}
                        else{
                            Toast.makeText(activity.getApplicationContext(),
                                    "Nie udało się pobrać zawartości z serwera "
                                    ,Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);

                    }
                    else{
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);
                        Toast.makeText(activity.getApplicationContext(),"Błąd " + response.message(),
                                Toast.LENGTH_LONG).show();
                        isSuccess[0] = false;
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PageDatumUserMenu> call, @NonNull Throwable throwable) {
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                    Toast.makeText(activity.getApplicationContext(),"Błąd " + throwable.getMessage(),
                            Toast.LENGTH_LONG).show();
                    isSuccess[0] = false;
                }
            });


        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(false);
            Toast.makeText(activity.getApplicationContext(),"NIe można utworzyć menu ponieważ nazwa jest pusta",
            Toast.LENGTH_LONG).show();
            return isSuccess[0] =false;
        }
        return isSuccess[0];
    }


}
