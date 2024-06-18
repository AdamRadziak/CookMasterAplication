package com.example.cookmasteraplication.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cookmasteraplication.Controlers.RegistrationControler;
import com.example.cookmasteraplication.Helpers.ToolBarModel;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class RegistrationActivity extends AppCompatActivity {

    Button back;
    Button register;
    EditText email;
    EditText password;
    LinearLayout layout;
    RegistrationControler controler;

    ToolBarModel toolbarmodel;

    MaterialToolbar toolbar;

    String pageName = "Rejestracja";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        back = findViewById(R.id.buttonBackReg);
        register = findViewById(R.id.buttonRegisterUser);
        email = findViewById(R.id.editTextEmailReg);
        password = findViewById(R.id.editTextPasswordReg);
        layout = findViewById(R.id.registerLayout);
        toolbar = findViewById(R.id.toolbarRegister);
        controler = new RegistrationControler(this);
        Intent intent = new Intent(this,this.getClass());
        // toolbar build
        controler.setToolbar(toolbar, pageName);
        // clikc on listeners
        back.setOnClickListener(v -> controler.goBackLoginPage());

        register.setOnClickListener(v ->{
            intent.putExtra("email",email.getText().toString());
            intent.putExtra("pass",password.getText().toString());
            controler.sendRegisterUserData(intent, layout);});

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}