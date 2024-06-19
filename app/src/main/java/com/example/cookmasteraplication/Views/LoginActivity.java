package com.example.cookmasteraplication.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cookmasteraplication.Controlers.LoginPageControler;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class LoginActivity extends AppCompatActivity {

    Button signIn;
    Button passwordRemind;
    Button register;
    ProgressBar progressBar;
    EditText userEmail;
    EditText userPassword;
    // controller class
    LoginPageControler controller;
    SharedPreferencesActivities sharedPref;
    MaterialToolbar toolbar;
    String pageName = "Strona logowania";
    boolean isLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        // find Views by Id
        signIn = findViewById(R.id.buttonSignIn);
        passwordRemind = findViewById(R.id.buttonRemPass);
        register = findViewById(R.id.buttonRegister);
        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);
        toolbar = findViewById(R.id.toolbarLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        // instance of the class login controller
        controller = new LoginPageControler(this);
        sharedPref = new SharedPreferencesActivities(this);
        Intent intent = new Intent(this,this.getClass());
        // build a toolbar properties
        controller.setToolbar(toolbar, pageName);
        // get data from password reminder controller shared preferences class
        String reminderGetEmail = sharedPref.retrieveStringData("emailRemind");
        String reminderGetPass = sharedPref.retrieveStringData("passRemind");
        if(!reminderGetPass.isEmpty() && !reminderGetEmail.isEmpty() ){
            // fill textinput
            userEmail.setText(reminderGetEmail);
            userPassword.setText(reminderGetPass);
        }
        // click listeners
        signIn.setOnClickListener(v -> {
            intent.putExtra("email",userEmail.getText().toString());
            intent.putExtra("pass",userPassword.getText().toString());
            isLogin = controller.goToMainPage(intent,progressBar);
            if(!isLogin){
                userEmail.setBackgroundColor(Color.RED);
                userPassword.setBackgroundColor(Color.RED);
                userEmail.setHint("Wpisz poprawny email");
                userPassword.setHint("Wpisz poprawne hasło");
            }
        });
        passwordRemind.setOnClickListener(v -> controller.goToPasswordReminderPage());
        register.setOnClickListener(v -> controller.goToRegisterPage());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}