package com.example.cookmasteraplication.Controlers;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cookmasteraplication.Models.AccountInfoModel;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Helpers.CommonTools;
import com.example.cookmasteraplication.Views.CreateMenuActivity;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.Views.PasswordReminderActivity;
import com.example.cookmasteraplication.Views.RegistrationActivity;
import com.example.cookmasteraplication.Api.Models.UserAccount;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IUserAccountService;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPageControler {

    public static ArrayList<AccountInfoModel> users = new ArrayList<>();
    private final LoginActivity activity;
    private final SharedPreferencesActivities sharedPref;


    public LoginPageControler(LoginActivity activity) {
        this.activity = activity;
        this.sharedPref = new SharedPreferencesActivities(this.activity);
    }

    public void goToRegisterPage() {
        Intent intent = new Intent(activity.getApplicationContext(), RegistrationActivity.class);
        activity.startActivity(intent);
    }

    public void goToPasswordReminderPage() {
        Intent intent = new Intent(activity.getApplicationContext(), PasswordReminderActivity.class);
        activity.startActivity(intent);
    }

    public boolean goToMainPage(Intent intent, ProgressBar progressBar) {
        final boolean[] isLogin = {true};
        int statusCode;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        // verify that email and password is correct
        String email_get = intent.getStringExtra("email");
        String password_get = intent.getStringExtra("pass");
        if (email_get != null && password_get != null) {
            // authorize by headers
            Retrofit retrofitClient = BaseClient.get_AuthClient(email_get, password_get);
            IUserAccountService client = retrofitClient.create(IUserAccountService.class);
            // encode usermeail nad userpass
            String passEncode = CommonTools.encode2Base64String(password_get);
            String emailEncode = CommonTools.encode2Base64String(email_get);
            Call<UserAccount> call = client.LogIn(emailEncode, passEncode);
            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                    if (response.code() == 200) {
                        // add to GetUserAccount class
                        UserAccount body = response.body();
                        String emailDecode = CommonTools.decodeFromBase64String(body.getEmail());
                        String passDecode = CommonTools.decodeFromBase64String(body.getPassword());
                        // save to shared preferences class
                        sharedPref.saveStringData("UserId",body.getId().toString());
                        sharedPref.saveStringData("UserEmail",emailDecode);
                        sharedPref.saveStringData("UserPass",passDecode);
                        progressBar.setIndeterminate(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(activity.getApplicationContext(), "Poprawnie zalogowano", Toast.LENGTH_SHORT).show();
                        Intent MainPageIntent = new Intent(activity.getApplicationContext(), CreateMenuActivity.class);
                        activity.startActivity(MainPageIntent);
                        isLogin[0] = true;
                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Błąd logowania " + response.message(), Toast.LENGTH_LONG).show();
                        progressBar.setIndeterminate(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        isLogin[0] = false;
                    }
                }

                @Override
                public void onFailure(Call<UserAccount> call, Throwable throwable) {
                    Toast.makeText(activity.getApplicationContext(), "Błąd logowania " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    isLogin[0] = false;
                }
            });
        }


        return isLogin[0];
    }


    public void setToolbar(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }


}
