package com.example.cookmasteraplication.Models;

import java.util.ArrayList;

public class SavedMenuModel extends MenuModel {

    String name;
    ArrayList<MenuModel> menu;


    public SavedMenuModel() {
        super();
        this.name = "0";
        this.menu = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MenuModel> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuModel> menu) {
        this.menu = menu;
    }


}

