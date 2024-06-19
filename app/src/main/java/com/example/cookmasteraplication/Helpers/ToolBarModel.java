package com.example.cookmasteraplication.Helpers;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.AccountSettingsActivity;
import com.example.cookmasteraplication.Views.CreateMenuActivity;
import com.example.cookmasteraplication.Views.FavouritesRecipesActivity;
import com.example.cookmasteraplication.Views.FindRecipeActivity;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.Views.SavedMenuActivity;
import com.example.cookmasteraplication.Api.Models.UserAccount;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IUserAccountService;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

final public class ToolBarModel {


    public ToolBarModel(Builder builder) {
        AppCompatActivity activity = builder.activity;
        MaterialToolbar toolbar = builder.toolbar;
    }

    public static class Builder {
        private final AppCompatActivity activity;
        private final MaterialToolbar toolbar;

        public Builder(AppCompatActivity activity, MaterialToolbar toolbar) {
            this.activity = activity;
            this.toolbar = toolbar;
        }

        public Builder setToolbarSubtitle(String pageName) {
            toolbar.setSubtitle(pageName);
            return this;
        }


        public Builder setOverflowIcon() {
            toolbar.setOverflowIcon(AppCompatResources.getDrawable(activity.getApplicationContext(),
                    R.drawable.menu_icon));
            return this;
        }

        public Builder setNavigationIconOnClickListener(AppCompatActivity DestActivity) {
            toolbar.setNavigationOnClickListener(v -> {
                Intent intent = new Intent(activity.getApplicationContext(), FindRecipeActivity.class);
                activity.startActivity(intent);
            });
            return this;
        }

        public Builder setMenuOItemOnClickListeners() {
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.createMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "Utwórz menu", Toast.LENGTH_SHORT).show();
                    Intent createMenuIntent = new Intent(activity.getApplicationContext(), CreateMenuActivity.class);
                    activity.startActivity(createMenuIntent);
                } else if (item.getItemId() == R.id.findRecipeMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "Znajdź przepis", Toast.LENGTH_SHORT).show();
                    Intent findRecipeIntent = new Intent(activity.getApplicationContext(), FindRecipeActivity.class);
                    activity.startActivity(findRecipeIntent);

                } else if (item.getItemId() == R.id.savedMenusMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "Menu użytkownika", Toast.LENGTH_SHORT).show();
                    Intent savedMenuIntent = new Intent(activity.getApplicationContext(), SavedMenuActivity.class);
                    activity.startActivity(savedMenuIntent);
                } else if (item.getItemId() == R.id.favouritesMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "Ulubione przepisy", Toast.LENGTH_SHORT).show();
                    Intent favouritesIntent = new Intent(activity.getApplicationContext(), FavouritesRecipesActivity.class);
                    activity.startActivity(favouritesIntent);
                } else if (item.getItemId() == R.id.accountSettingsMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "Ustawienia konta", Toast.LENGTH_SHORT).show();
                    Intent accountSettIntent = new Intent(activity.getApplicationContext(), AccountSettingsActivity.class);
                    activity.startActivity(accountSettIntent);

                } else if (item.getItemId() == R.id.signOutMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "Wyloguj", Toast.LENGTH_SHORT).show();
                    logout();

                } else if (item.getItemId() == R.id.backMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "Wstecz", Toast.LENGTH_SHORT).show();
                    activity.finish();

                }
                return true;
            });
            return this;
        }

        private void logout(){
            // put empty string as password to auth end email from user
            Retrofit retrofitClient = BaseClient.get_AuthClient("","");
            IUserAccountService client = retrofitClient.create(IUserAccountService.class);
            Call<UserAccount> call = client.LogIn("","");
            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                    if(response.code()==404){
                        Toast.makeText(activity.getApplicationContext(),"Pomyślnie wylogowano",
                                Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(activity.getApplicationContext(), LoginActivity.class);
                        activity.startActivity(loginIntent);
                    }else{
                        Toast.makeText(activity.getApplicationContext(),
                                "Błąd " + response.message(),
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserAccount> call, Throwable throwable) {
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + throwable.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
        public Builder create() {
            return new Builder(this.activity, this.toolbar);
        }

        public ToolBarModel build() {
            return new ToolBarModel(this);
        }
    }


}




