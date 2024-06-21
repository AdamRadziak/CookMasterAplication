package com.example.cookmasteraplication.Controlers;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cookmasteraplication.Api.Models.UserAccount;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IUserAccountService;
import com.example.cookmasteraplication.Helpers.CommonTools;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Views.CreateMenuActivity;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.Views.PasswordReminderActivity;
import com.example.cookmasteraplication.Views.RegistrationActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPageControler {

    private final LoginActivity activity;
    private final SharedPreferencesActivities sharedPref;
    private final ProgressBar progressBar;


    public LoginPageControler(LoginActivity activity,ProgressBar progressBar) {
        this.activity = activity;
        this.sharedPref = new SharedPreferencesActivities(this.activity);
        this.progressBar = progressBar;
        // set default progressbar values
        this.progressBar.setVisibility(View.INVISIBLE);
        this.progressBar.setIndeterminate(false);
    }

    public void goToRegisterPage() {
        Intent intent = new Intent(activity.getApplicationContext(), RegistrationActivity.class);
        activity.startActivity(intent);
    }

    public void goToPasswordReminderPage() {
        Intent intent = new Intent(activity.getApplicationContext(), PasswordReminderActivity.class);
        activity.startActivity(intent);
    }

    public boolean goToMainPage(Intent intent, View layout) {
        final boolean[] isLogin = {false};
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        // verify that email and password is correct
        String email_get = intent.getStringExtra("email");
        String password_get = intent.getStringExtra("pass");
        if (!email_get.isEmpty() && !password_get.isEmpty()) {
            // authorize by headers
            Retrofit retrofitClient = BaseClient.get_AuthClient(email_get, password_get);
            IUserAccountService client = retrofitClient.create(IUserAccountService.class);
            // encode usermeail nad userpass
            String passEncode = CommonTools.encode2Base64String(password_get);
            String emailEncode = CommonTools.encode2Base64String(email_get);
            Call<UserAccount> call = client.LogIn(emailEncode, passEncode);
            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(@NonNull Call<UserAccount> call, @NonNull Response<UserAccount> response) {
                    if (response.code() == 200) {
                        // add to GetUserAccount class
                        if (response.body() != null) {
                            UserAccount body = response.body();
                            String emailDecode = CommonTools.decodeFromBase64String(body.getEmail());
                            String passDecode = CommonTools.decodeFromBase64String(body.getPassword());
                            // save to shared preferences class
                            sharedPref.saveStringData("UserId", body.getId().toString());
                            sharedPref.saveStringData("UserEmail", emailDecode);
                            sharedPref.saveStringData("UserPass", passDecode);
                            progressBar.setIndeterminate(false);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(activity.getApplicationContext(), "Poprawnie zalogowano", Toast.LENGTH_SHORT).show();
                            Intent MainPageIntent = new Intent(activity.getApplicationContext(), CreateMenuActivity.class);
                            activity.startActivity(MainPageIntent);
                            isLogin[0] = true;
                        } else {
                            Toast.makeText(activity.getApplicationContext(), "Nie można pobrać zawartości ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        progressBar.setIndeterminate(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        String msg = null;
                        try {
                            msg = response.errorBody().string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Snackbar.make(layout, msg, Snackbar.LENGTH_INDEFINITE)
                                .setAction("Zamknij", v -> {

                                }).setTextMaxLines(100).show();
                        isLogin[0] = false;
                    }
                }


                @Override
                public void onFailure(@NonNull Call<UserAccount> call, @NonNull Throwable throwable) {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(layout, throwable.getMessage(), Snackbar.LENGTH_INDEFINITE)
                            .setAction("Zamknij", v -> {

                            }).setTextMaxLines(100).show();
                    isLogin[0] = false;
                }
            });
        }else{
            Snackbar.make(layout, "Nieprawidłowy email lub hasło", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Zamknij", v -> {

                    }).setTextMaxLines(100).show();
        }
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.INVISIBLE);

        return isLogin[0];
    }


    public void setToolbar(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }


}
