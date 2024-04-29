package com.example.cookmasteraplication.Models;

import android.content.Context;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class SharedPreferencesActivities {
    SharedPreferences sharedPreferences;
    AppCompatActivity activity;

    public SharedPreferencesActivities(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void saveData(String dataLabel, String dataValue)
    {
        sharedPreferences = activity.getSharedPreferences("saveData", Context.MODE_PRIVATE);

        // zapisywanie danych do shared preferences

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(dataLabel,dataValue);
        editor.commit();
        Toast.makeText(activity.getApplicationContext(),"Your data is saved",Toast.LENGTH_LONG).show();
    }

    public String retrieveData(String dataLabel){
        sharedPreferences = activity.getSharedPreferences("saveData",Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(dataLabel,"");
        return value;
    }
}
