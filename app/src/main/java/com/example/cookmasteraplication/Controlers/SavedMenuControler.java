package com.example.cookmasteraplication.Controlers;

import android.widget.Toast;

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
    SharedPreferencesActivities sharedPref;

    public SavedMenuControler(SavedMenuActivity activity) {
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


    public void ListUserMenus(RecyclerView recyclerView)
    {
        String email = sharedPref.getUserEmail();
        String password = sharedPref.getUserPass();
        Integer IdUser = sharedPref.getUserId();
        // get from shared preferences IdMenu
        Retrofit retrofitClient = BaseClient.get_AuthClient(email, password);
        IUserMenuService client = retrofitClient.create(IUserMenuService.class);
        Call<GetUserMenu> call = client.ListUserMenuByIdUser(IdUser);

        call.enqueue(new Callback<GetUserMenu>() {
            @Override
            public void onResponse(Call<GetUserMenu> call, Response<GetUserMenu> response) {
                if(response.code()==200){
                    GetUserMenu menu = response.body();
                    menuList.addAll(menu.getPageData());
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
                    RecyclerAdapterSavedMenu adapter = new RecyclerAdapterSavedMenu(menuList, activity, sharedPref);
                    recyclerView.setAdapter(adapter);

                }
                else{
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserMenu> call, Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(),Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

}
