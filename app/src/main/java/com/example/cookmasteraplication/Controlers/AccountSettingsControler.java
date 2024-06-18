package com.example.cookmasteraplication.Controlers;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Utils.CommonTools;
import com.example.cookmasteraplication.Views.AccountSettingsActivity;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.api.Models.UserAccount;
import com.example.cookmasteraplication.api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.api.Services.IUserAccountService;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountSettingsControler {

    AccountSettingsActivity activity;
    SharedPreferencesActivities sharedPref;
    Retrofit retrofitClient;

    public AccountSettingsControler(AccountSettingsActivity activity) {
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

    public boolean changePassword(Intent intentPass) {
        final boolean[] isPasswordChanged = {false};
        String pass_get = intentPass.getStringExtra("newPass");
        // get api to update the password
        if (pass_get != null) {
            String NewpassEncode = CommonTools.encode2Base64String(pass_get);
            String emailEncode = CommonTools.encode2Base64String(sharedPref.retrieveData("UserEmail"));
            // retreive data by sharedPreferences
            UserAccount userAccount = new UserAccount(emailEncode, NewpassEncode);
            retrofitClient = BaseClient
                    .get_AuthClient(sharedPref.retrieveData("UserEmail"),
                            sharedPref.retrieveData("UserPass"));
            IUserAccountService client = retrofitClient.create(IUserAccountService.class);
            Call<UserAccount> call = client.UpdateUserPass(userAccount,
                    Integer.parseInt(sharedPref.retrieveData("UserId")));
            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                    if (response.code() == 200) {
                        Toast.makeText(activity.getApplicationContext(),
                                "Pomyślnie zmieniono hasło dla użytkownika " +
                                        sharedPref.retrieveData("UserEmail"),
                                Toast.LENGTH_LONG).show();
                        isPasswordChanged[0] = true;
                        // add to sharedPreferences class new value of password
                        sharedPref.saveData("UserPass",pass_get);

                    } else {
                        Toast.makeText(activity.getApplicationContext(),
                                "Błąd " + response.message(), Toast.LENGTH_LONG).show();
                        isPasswordChanged[0] = false;
                    }
                }

                @Override
                public void onFailure(Call<UserAccount> call, Throwable throwable) {
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    isPasswordChanged[0] = false;
                }
            });


        }
        return isPasswordChanged[0];
    }

    private void deleteAccount(String userEmail) {
        if (userEmail != null) {
            // use api to delete user
            Integer UserId = Integer.parseInt(sharedPref.retrieveData("UserId"));
            retrofitClient = BaseClient
                    .get_AuthClient(sharedPref.retrieveData("UserEmail"),
                            sharedPref.retrieveData("UserPass"));
            IUserAccountService client = retrofitClient.create(IUserAccountService.class);
            Call<UserAccount> call = client.DeleteUser(UserId);
            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                    if (response.code() == 204) {
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
                        Toast.makeText(activity.getApplicationContext(),
                                "błąd " + response.message(),
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserAccount> call, Throwable throwable) {
                    Toast.makeText(activity.getApplicationContext(),
                            "błąd " + throwable.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
            });


        }
    }

    public void showDeleteAccountDialogMsg(String userEmail) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity, R.style.DialogTheme);
        alertDialog.setTitle("Usuwanie konta").setMessage("Czy chcesz usunąć konto? Po wciśnięciu tak zostaniesz wylogowany")
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialoge message
                        dialog.dismiss();
                    }
                }).setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete text in result textview
                        deleteAccount(userEmail);
                        dialog.dismiss();
                    }
                }).show();
        alertDialog.create();
    }


}
