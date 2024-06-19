package com.example.cookmasteraplication.Controlers;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.cookmasteraplication.Api.Models.UserAccount;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IUserAccountService;
import com.example.cookmasteraplication.Helpers.CommonTools;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.AccountSettingsActivity;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountSettingsControler {

    private final AccountSettingsActivity activity;
    private final SharedPreferencesActivities sharedPref;
    private final ProgressBar progressBar;
    Retrofit retrofitClient;

    public AccountSettingsControler(AccountSettingsActivity activity,
                                    ProgressBar progressBar) {
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

    public boolean changePassword(Intent intentPass) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        final boolean[] isPasswordChanged = {false};
        String pass_get = intentPass.getStringExtra("newPass");
        // get api to update the password
        if (pass_get != null) {
            String NewpassEncode = CommonTools.encode2Base64String(pass_get);
            String emailEncode = CommonTools.encode2Base64String(sharedPref.retrieveStringData("UserEmail"));
            // retreive data by sharedPreferences
            UserAccount userAccount = new UserAccount(emailEncode, NewpassEncode);
            retrofitClient = BaseClient
                    .get_AuthClient(sharedPref.retrieveStringData("UserEmail"),
                            sharedPref.retrieveStringData("UserPass"));
            IUserAccountService client = retrofitClient.create(IUserAccountService.class);
            Call<UserAccount> call = client.UpdateUserPass(userAccount,
                    Integer.parseInt(sharedPref.retrieveStringData("UserId")));
            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(@NonNull Call<UserAccount> call, @NonNull Response<UserAccount> response) {
                    if (response.code() == 200) {
                        isPasswordChanged[0] = true;
                        // add to sharedPreferences class new value of password
                        sharedPref.saveStringData("UserPass",pass_get);
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);
                        Toast.makeText(activity.getApplicationContext(),
                                "Pomyślnie zmieniono hasło dla użytkownika " +
                                        sharedPref.retrieveStringData("UserEmail"),
                                Toast.LENGTH_LONG).show();

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);
                        Toast.makeText(activity.getApplicationContext(),
                                "Błąd " + response.message(), Toast.LENGTH_LONG).show();
                        isPasswordChanged[0] = false;
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserAccount> call, @NonNull Throwable throwable) {
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    isPasswordChanged[0] = false;
                }
            });


        }
        return isPasswordChanged[0];
    }

    private void deleteAccount() {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            // use api to delete user
            retrofitClient = BaseClient
                    .get_AuthClient(sharedPref.retrieveStringData("UserEmail"),
                            sharedPref.retrieveStringData("UserPass"));
            IUserAccountService client = retrofitClient.create(IUserAccountService.class);
            Call<UserAccount> call = client.DeleteUser(sharedPref.getUserId());
            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(@NonNull Call<UserAccount> call, @NonNull Response<UserAccount> response) {
                    if (response.code() == 204) {
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);
                        Toast.makeText(activity.getApplicationContext(),
                                "Pomyślnie usunięto użytkownika nastąpi wylogowanie za 2 s",
                                Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // go to login page
                        Intent loginPageIntent = new Intent(activity.getApplicationContext(),
                                LoginActivity.class);
                        activity.startActivity(loginPageIntent);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setIndeterminate(false);
                        Toast.makeText(activity.getApplicationContext(),
                                "błąd " + response.message(),
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserAccount> call, @NonNull Throwable throwable) {
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                    Toast.makeText(activity.getApplicationContext(),
                            "błąd " + throwable.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
            });


        }

    public void showDeleteAccountDialogMsg() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity, R.style.DialogTheme);
        alertDialog.setTitle("Usuwanie konta").setMessage("Czy chcesz usunąć konto? Po wciśnięciu tak zostaniesz wylogowany")
                .setNegativeButton("Nie", (dialog, which) -> {
                    // close dialoge message
                    dialog.dismiss();
                }).setPositiveButton("Tak", (dialog, which) -> {
                    // delete text in result textview
                    deleteAccount();
                    dialog.dismiss();
                }).show();
        alertDialog.create();
    }


}
