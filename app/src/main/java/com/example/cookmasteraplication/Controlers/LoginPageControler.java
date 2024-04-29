package com.example.cookmasteraplication.Controlers;

import android.content.Intent;
import android.widget.Toast;

import com.example.cookmasteraplication.Models.AccountInfoModel;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.Views.AccountSettingsActivity;
import com.example.cookmasteraplication.Views.CreateMenuActivity;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.Views.PasswordReminderActivity;
import com.example.cookmasteraplication.Views.RegistrationActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class LoginPageControler {

    public static ArrayList<AccountInfoModel> users = new ArrayList<>();
    private final LoginActivity activity;

    public LoginPageControler(LoginActivity activity) {
        this.activity = activity;
        createSampleAccount();
    }

    public void goToRegisterPage() {
        Intent intent = new Intent(activity.getApplicationContext(), RegistrationActivity.class);
        activity.startActivity(intent);
    }

    public void goToPasswordReminderPage() {
        Intent intent = new Intent(activity.getApplicationContext(), PasswordReminderActivity.class);
        activity.startActivity(intent);
    }

    public boolean goToMainPage(Intent intent) {
        boolean isLogin = false;
        int statusCode;
        // verify that email and password is correct
        String email_get = intent.getStringExtra("email");
        String password_get = intent.getStringExtra("pass");
        for(AccountInfoModel user:users){
            String email = user.email;
            String passw = user.password;
            if(email_get.equals(email)){
                if(password_get.equals(passw)){
                    Toast.makeText(activity.getApplicationContext(),"Poprawnie zalogowano",Toast.LENGTH_SHORT).show();
                    isLogin = true;
                    Intent MainPageIntent = new Intent(activity.getApplicationContext(), CreateMenuActivity.class);
//                    Intent accountSettingsIntent = new Intent(activity.getApplicationContext(), AccountSettingsActivity.class);
//                    accountSettingsIntent.putExtra("userEmail",email);
                    activity.startActivity(MainPageIntent);
                }
            }

        }

        return isLogin;



    }

    public void setToolbar(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }

    private void createSampleAccount(){
        AccountInfoModel user1 = new AccountInfoModel();
        user1.setEmail("example@com.pl");
        user1.setPassword("12345");
        users.add(user1);
    }

}
