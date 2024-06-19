package com.example.cookmasteraplication.Controlers;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Helpers.CommonTools;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.Views.RegistrationActivity;
import com.example.cookmasteraplication.Api.Models.UserAccount;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IUserAccountService;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationControler {
    private final RegistrationActivity activity;
    private final ProgressBar progressBar;

    public RegistrationControler(RegistrationActivity activity,
                                 ProgressBar progressBar) {
        this.activity = activity;
        this.progressBar = progressBar;
        // set default progressbar values
        this.progressBar.setVisibility(View.INVISIBLE);
        this.progressBar.setIndeterminate(false);

    }

    public void goBackLoginPage() {
        Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
        activity.startActivity(intent);
    }


    public void sendRegisterUserData(Intent intent, View layout) {
        String email_get = intent.getStringExtra("email");
        String password_get = intent.getStringExtra("pass");
        // encode pass and email to base64
        if (email_get != null && password_get != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            String emailHash = CommonTools.encode2Base64String(email_get);
            String passwordHash = CommonTools.encode2Base64String(password_get);
            UserAccount userAccount = new UserAccount(emailHash, passwordHash);
            // create connection to api
            Retrofit retrofitClient = BaseClient.get_client();
            IUserAccountService client = retrofitClient.create(IUserAccountService.class);
            Call<UserAccount> call = client.CreateAccount(userAccount);

            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(@NonNull Call<UserAccount> call, @NonNull Response<UserAccount> response) {
                    if(response.code() == 201){
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                    Snackbar.make(layout, "Utworzono użytkownika o nazwie " + email_get, Snackbar.LENGTH_INDEFINITE)
                            .setAction("Zamknij", v -> {

                            }).show();}
                    else{
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);
                        Snackbar.make(layout, "NIe utworzono użytkownika " + email_get + " " + response.message(), Snackbar.LENGTH_INDEFINITE)
                                .setAction("Zamknij", v -> {

                                }).show();}
                    }
                @Override
                public void onFailure(@NonNull Call<UserAccount> call, @NonNull Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                    Snackbar.make(layout, "Niepowodzenie w rejestracji użytkownika" + email_get + " " + t.getMessage(), Snackbar.LENGTH_INDEFINITE)
                            .setAction("Zamknij", v -> {

                            }).show();
                    call.cancel();
                }
            });
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(false);
            Snackbar.make(layout, "Brak wpisanego hasła lub maila ", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Zamknij", v -> {

                    }).show();
        }


    }

    public void setToolbar(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }
}
