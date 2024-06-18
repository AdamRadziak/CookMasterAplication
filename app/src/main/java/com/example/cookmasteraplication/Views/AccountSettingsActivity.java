package com.example.cookmasteraplication.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cookmasteraplication.Controlers.AccountSettingsControler;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class AccountSettingsActivity extends AppCompatActivity {

    MaterialToolbar toolbarLogo;
    MaterialToolbar toolbarMenu;
    EditText password;
    Button changepassw;
    Button deleteAccount;
    LinearLayout layout;
    AccountSettingsControler controler;
    SharedPreferencesActivities sharedPref;
    String pageName = "Ustawienia użytkownika";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_settings);

        toolbarLogo = findViewById(R.id.toolbarLogoAccountSettings);
        toolbarMenu = findViewById(R.id.toolbarMenuAccountSettings);
        changepassw = findViewById(R.id.buttonChangePass);
        deleteAccount = findViewById(R.id.buttonDelteAccount);
        password = findViewById(R.id.editTextPassAccountSet);
        layout = findViewById(R.id.AccountSettLinLay);

        controler = new AccountSettingsControler(this);
        sharedPref = new SharedPreferencesActivities(this);
//        Intent intentUserEmail = getIntent();
        Intent intentPassChange = new Intent(this,this.getClass());

        controler.setToolbarLogo(toolbarLogo,pageName);
        controler.setToolbarMenu(toolbarMenu);

        changepassw.setOnClickListener(v -> {
            String passwordText = password.getText().toString();
            if(passwordText.isEmpty()){
                password.setBackgroundColor(Color.RED);
                password.setHint("Hasło nie może być puste");
            }else{
            intentPassChange.putExtra("newPass",passwordText);
            controler.changePassword(intentPassChange);
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = sharedPref.retrieveData("userEmail");
                controler.showDeleteAccountDialogMsg(userEmail);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.userSettLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}