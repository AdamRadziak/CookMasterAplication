package com.example.cookmasteraplication.Models;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookmasteraplication.R;

public class SaveDialogBox {

    public static String menuName = "";
    public AppCompatActivity a, targetActivity;
    public Button save, cancel;
    public EditText name;
    private String dlgTitle;


    public SaveDialogBox(AppCompatActivity a, String dlgTitle, AppCompatActivity targetActivity) {
        this.a = a;
        this.dlgTitle = dlgTitle;
        this.targetActivity = targetActivity;

    }

    public void showDialog() {
        Intent intent = new Intent(a.getApplicationContext(), targetActivity.getClass());
        final AlertDialog dialogBuilder = new AlertDialog.Builder(a).create();
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = a.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_box_save_menu, null);
        dialogBuilder.setView(dialogView);

        name = (EditText) dialogView.findViewById(R.id.editTextDlgName);
        save = (Button) dialogView.findViewById(R.id.btn_save);
        cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuName = name.getText().toString();
                if (menuName.isEmpty()) {
                    name.setBackgroundColor(Color.RED);
                    name.setHint("Wpisz poprawną nazwę");
                } else {
                    menuName = name.getText().toString();
                    intent.putExtra("Menu_name", menuName);
                    Toast.makeText(a.getApplicationContext(), "Zapisano menu pod nazwą " + menuName, Toast.LENGTH_SHORT).show();

                    dialogBuilder.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setTitle(dlgTitle);
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

}

