package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.RecipeDetailsControler.GlobalRecipes;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Adapters.RecyclerAdapterSavedMenu;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Views.SavedMenuActivity;
import com.example.cookmasteraplication.Api.Models.GetUserMenu;
import com.example.cookmasteraplication.Api.Models.PageDatumUserMenu;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IUserMenuService;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SavedMenuControler {

    public static ArrayList<PageDatumUserMenu> menuList = new ArrayList<>();
    private final SavedMenuActivity activity;
    private final SharedPreferencesActivities sharedPref;
    private final ProgressBar progressBar;

    public SavedMenuControler(SavedMenuActivity activity,ProgressBar progressBar) {
        this.activity = activity;
        this.sharedPref = new SharedPreferencesActivities(this.activity);
        this.progressBar = progressBar;
        // set default progressbar values
        this.progressBar.setVisibility(View.INVISIBLE);
        this.progressBar.setIndeterminate(false);
    }

    public void setToolbarLogo(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }

    public void setToolbarMenu(MaterialToolbar toolbar) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setMenuOItemOnClickListeners().build();
    }


    public void ListUserMenus(RecyclerView recyclerView)
    {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        String email = sharedPref.getUserEmail();
        String password = sharedPref.getUserPass();
        Integer IdUser = sharedPref.getUserId();
        // get from shared preferences IdMenu
        Retrofit retrofitClient = BaseClient.get_AuthClient(email, password);
        IUserMenuService client = retrofitClient.create(IUserMenuService.class);
        Call<GetUserMenu> call = client.ListUserMenuByIdUser(IdUser);

        call.enqueue(new Callback<GetUserMenu>() {
            @Override
            public void onResponse(@NonNull Call<GetUserMenu> call, @NonNull Response<GetUserMenu> response) {
                if(response.code()==200){
                    if(response.body().getPageData() != null){
                        menuList.clear();
                        GlobalRecipes.clear();
                        GetUserMenu menu = response.body();
                    menuList.addAll(menu.getPageData());
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
                    RecyclerAdapterSavedMenu adapter = new RecyclerAdapterSavedMenu(menuList, activity, sharedPref);
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
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUserMenu> call, @NonNull Throwable throwable) {
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setIndeterminate(false);
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(),Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

}
