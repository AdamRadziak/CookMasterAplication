package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.LoginPageControler.users;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.cookmasteraplication.Models.AccountInfoModel;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.AccountSettingsActivity;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Iterator;

public class AccountSettingsControler {

    AccountSettingsActivity activity;

    public AccountSettingsControler(AccountSettingsActivity activity) {
        this.activity = activity;
    }

    public void setToolbarLogo(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }

    public void setToolbarMenu(MaterialToolbar toolbar) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setMenuOItemOnClickListeners().build();
    }

    public boolean changePassword(String userEmail, Intent intentPass, View layout) {
        boolean isPasswordChanged = false;
        String pass_get = intentPass.getStringExtra("newPass");
        for (AccountInfoModel user : users) {
            String email = user.email;
            if (userEmail.equals(email)) {
                Snackbar.make(layout, "Zmieniono hasło do konta o emailu " + email, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Zamknij", v -> {

                        }).show();
                user.setPassword(pass_get);
                isPasswordChanged = true;
            }


        }
        return isPasswordChanged;
    }

    private void deleteAccount(String userEmail) {
        for (Iterator<AccountInfoModel> it = users.iterator(); it.hasNext();) {
            String email = it.next().getEmail();
            if (email.equals(userEmail)) {
                // remove iterator and element of arraylist
                it.remove();
                Intent goToLoginPage = new Intent(activity.getApplicationContext(), LoginActivity.class);
                activity.startActivity(goToLoginPage);
            }


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
