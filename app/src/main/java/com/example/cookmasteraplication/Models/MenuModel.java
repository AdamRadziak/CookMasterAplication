package com.example.cookmasteraplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MenuModel extends MenuCardItemModel {

    String name;
    MenuCardItemModel meal;


    public MenuModel() {
        super();
        this.name = "0";
        this.meal = new MenuCardItemModel();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuCardItemModel getMeal() {
        return meal;
    }

    public void setMeal(MenuCardItemModel meal) {
        this.meal = meal;
    }

}

