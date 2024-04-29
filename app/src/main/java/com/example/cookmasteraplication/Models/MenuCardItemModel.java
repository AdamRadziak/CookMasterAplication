package com.example.cookmasteraplication.Models;

public class MenuCardItemModel extends RecipeModel {
    String category;
    RecipeModel recipe;

    public MenuCardItemModel() {
        this.recipe = new RecipeModel();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public RecipeModel getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeModel recipe) {
        this.recipe = recipe;
    }
}
