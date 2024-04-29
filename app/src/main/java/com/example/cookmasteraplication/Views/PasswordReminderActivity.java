package com.example.cookmasteraplication.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cookmasteraplication.Controlers.PasswordReminderControler;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class PasswordReminderActivity extends AppCompatActivity {

    Button back;
    Button reminder;
    EditText userEmail;
    PasswordReminderControler controller;
    LinearLayout layout;
    ToolBarModel toolbarmodel;

    MaterialToolbar toolbar;

    String pageName = "Przypomnienie hasła";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_password_reminder);

        back = findViewById(R.id.buttonBack);
        reminder = findViewById(R.id.buttonPassRem);

        userEmail = findViewById(R.id.editTextEmailReminder);
        layout = findViewById(R.id.passRemLayout);
        toolbar = findViewById(R.id.toolbarPassRem);
        controller = new PasswordReminderControler(this);
        // toolbar build
        controller.setToolbar(toolbar, pageName);
        // click on listeners
        back.setOnClickListener(v -> controller.goBackLoginPage());
        reminder.setOnClickListener(v -> controller.SendEmail(userEmail, layout));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.passRemLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}