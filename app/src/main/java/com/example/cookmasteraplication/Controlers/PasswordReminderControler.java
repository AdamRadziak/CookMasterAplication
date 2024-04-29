package com.example.cookmasteraplication.Controlers;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.example.cookmasteraplication.Models.AccountInfoModel;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.Views.PasswordReminderActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

public class PasswordReminderControler {

    private final AccountInfoModel model;
    private final PasswordReminderActivity activity;


    public PasswordReminderControler(PasswordReminderActivity activity) {
        this.model = new AccountInfoModel();
        this.activity = activity;
    }

    public void goBackLoginPage() {
        Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
        activity.startActivity(intent);
    }

    public void SendEmail(EditText userEmail, View layout) {
        String message = "";
        String email = userEmail.getText().toString();
        // send email by post request
        // server returns status code 200 and then show snackbar message with message
        // password is sent to email from inputText
        // if email is not null and request status code is 200
        if (!email.isEmpty()) {
            message = "Hasło wysłano na maila " + email;

        } else {
            message = "Nieprawidłowy email";
        }
        Snackbar.make(layout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Close", v -> {

                }).show();
    }

    public void setToolbar(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }


}
