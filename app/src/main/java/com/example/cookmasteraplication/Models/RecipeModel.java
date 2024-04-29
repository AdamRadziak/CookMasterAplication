package com.example.cookmasteraplication.Models;

import java.util.ArrayList;

public class RecipeModel {
    String name;
    String description;
    ArrayList<ProductModel> productList;
    String prepareTime;
    String mealCount;
    String rate;
    String popularity;
    ArrayList<String> steps;

    public String getMealCount() {
        return mealCount;
    }

    public void setMealCount(String mealCount) {
        this.mealCount = mealCount;
    }

    public RecipeModel createRecipe(String name, String description, ArrayList<ProductModel> products, String prepareTime, String rate, String popularity, ArrayList<String> steps) {
        RecipeModel recipe = new RecipeModel();
        recipe.name = name;
        recipe.description = description;
        recipe.productList = products;
        recipe.prepareTime = prepareTime;
        recipe.rate = rate;
        recipe.popularity = popularity;
        recipe.steps = steps;
        return recipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(String prepareTime) {
        this.prepareTime = prepareTime;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public ArrayList<ProductModel> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductModel> productList) {
        this.productList = productList;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }
}
