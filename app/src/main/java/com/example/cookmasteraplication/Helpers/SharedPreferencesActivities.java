package com.example.cookmasteraplication.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class SharedPreferencesActivities {
    SharedPreferences sharedPreferences;
    AppCompatActivity activity;

    public SharedPreferencesActivities(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void saveData(String dataLabel, String dataValue) {
        sharedPreferences = activity.getSharedPreferences("saveData", Context.MODE_PRIVATE);

        // save data to shared preferences

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(dataLabel, dataValue);
        editor.apply();
    }

    public String retrieveData(String dataLabel) {
        sharedPreferences = activity.getSharedPreferences("saveData", Context.MODE_PRIVATE);
        return sharedPreferences.getString(dataLabel, "");
    }

    public String getUserEmail(){
        return retrieveData("UserEmail");
    }
    public String getUserPass(){
        return retrieveData("UserPass");
    }
    public Integer getUserId(){
        return Integer.parseInt(retrieveData("UserId"));
    }
    public Integer getMenuId(){
        return Integer.parseInt(retrieveData("IdMenu"));
    }
}
