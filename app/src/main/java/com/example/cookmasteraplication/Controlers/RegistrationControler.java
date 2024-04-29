package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.LoginPageControler.users;

import android.content.Intent;
import android.text.Layout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cookmasteraplication.Models.AccountInfoModel;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.Views.RegistrationActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

public class RegistrationControler {

    private final AccountInfoModel model;
    private final RegistrationActivity activity;

    public RegistrationControler(RegistrationActivity activity) {
        this.model = new AccountInfoModel();
        this.activity = activity;
    }

    public void goBackLoginPage() {
        Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
        activity.startActivity(intent);
    }

    public void sendRegisterUserData(Intent intent, View layout) {
        String email_get = intent.getStringExtra("email");
        String password_get = intent.getStringExtra("pass");
        boolean isEmailRegistered = false;
        // send user data password and email by post request
        // server returns status code 200 and then show snackbar message with message
        // password is sent to email from inputText
        // if email is not null and request status code is 200
        for(AccountInfoModel user:users){
            String email = user.email;
            if(email_get.equals(email)){
                Snackbar.make(layout,"Email " + email + " istnieje",Snackbar.LENGTH_INDEFINITE)
                        .setAction("Zamknij", v -> {

                        }).show();
                isEmailRegistered = true;

            }

            }
        if(!isEmailRegistered && !password_get.isEmpty()){
            AccountInfoModel newUser = new AccountInfoModel();
            newUser.setEmail(email_get);
            newUser.setPassword(password_get);
            Snackbar.make(layout,"Utworzono nowego użytkwonika o emailu  " + email_get ,Snackbar.LENGTH_INDEFINITE)
                    .setAction("Zamknij", v -> {

                    }).show();
            users.add(newUser);
        } else if (password_get.isEmpty()) {
            Snackbar.make(layout,"Brak wpisanego hasła  " + email_get ,Snackbar.LENGTH_INDEFINITE)
                    .setAction("Zamknij", v -> {

                    }).show();
        }

        }

    public void setToolbar(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }
}
