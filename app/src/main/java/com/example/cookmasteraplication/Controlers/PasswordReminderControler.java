package com.example.cookmasteraplication.Controlers;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.Helpers.CommonTools;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.Views.PasswordReminderActivity;
import com.example.cookmasteraplication.Api.Models.UserAccount;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IUserAccountService;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PasswordReminderControler {

    private final PasswordReminderActivity activity;
    private final SharedPreferencesActivities sharedPref;
    private final ProgressBar progressBar;


    public PasswordReminderControler(PasswordReminderActivity activity,
                                     ProgressBar progressBar) {
        this.activity = activity;
        this.sharedPref = new SharedPreferencesActivities(this.activity);
        this.progressBar = progressBar;
        // set default progressbar values
        this.progressBar.setVisibility(View.INVISIBLE);
        this.progressBar.setIndeterminate(false);
    }

    public void goBackLoginPage() {
        Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
        activity.startActivity(intent);
    }

    public void SendEmail(EditText userEmail, View layout) {
        String email_get = userEmail.getText().toString();

        if (!email_get.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            // get password by request
            String emailHash = CommonTools.encode2Base64String(email_get);
            // create connection to api
            Retrofit retrofitClient = BaseClient.get_client();
            IUserAccountService client = retrofitClient.create(IUserAccountService.class);
            Call<UserAccount> call = client.GetUserPassByEmail(emailHash);

            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(@NonNull Call<UserAccount> call, @NonNull Response<UserAccount> response) {
                    if(response.code() == 200){
                        // add to shared preferences class
                        if(response.body() != null){
                        UserAccount body = response.body();
                        String emailDecode = CommonTools.decodeFromBase64String(body.getEmail());
                        String passDecode = CommonTools.decodeFromBase64String(body.getPassword());
                        sharedPref.saveStringData("emailRemind",emailDecode);
                        sharedPref.saveStringData("passRemind",passDecode);
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);
                        Snackbar.make(layout, "hasło i email zostały przesłane na stronę logowania" , Snackbar.LENGTH_INDEFINITE)
                                .setAction("Close", v -> {

                                }).show();}
                        else{
                            Toast.makeText(activity.getApplicationContext(),
                                    "Nie można pobrać zawartości ",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);
                        String msg = null;
                        try {
                            msg = response.errorBody().string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Snackbar.make(layout, msg, Snackbar.LENGTH_INDEFINITE)
                                .setAction("Zamknij", v -> {

                                }).setTextMaxLines(100).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserAccount> call, @NonNull Throwable throwable) {
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                    Snackbar.make(layout, "błąd" + throwable.getMessage(), Snackbar.LENGTH_INDEFINITE)
                            .setAction("Close", v -> {

                            }).show();
                }
            });

        } else {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(false);
            Snackbar.make(layout, "nie wpisano maila", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Close", v -> {

                    }).setTextMaxLines(100).show();
        }
    }

    public void setToolbar(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }


}
