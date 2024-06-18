
package com.example.cookmasteraplication.api.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class UpdateRecipe {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("prepareTime")
    @Expose
    private Integer prepareTime;
    @SerializedName("mealCount")
    @Expose
    private Integer mealCount;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("isRecipeUpdate")
    @Expose
    private Boolean isRecipeUpdate;


    /**
     * @param prepareTime
     * @param rate
     * @param popularity
     * @param name
     * @param description
     * @param mealCount
     * @param isRecipeUpdate
     * @param category
     */
    public UpdateRecipe(String name, String category, Integer prepareTime, Integer mealCount,
                        Double rate, Double popularity, String description) {
        super();
        this.name = name;
        this.category = category;
        this.prepareTime = prepareTime;
        this.mealCount = mealCount;
        this.rate = rate;
        this.popularity = popularity;
        this.description = description;
        this.isRecipeUpdate = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UpdateRecipe withName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UpdateRecipe withCategory(String category) {
        this.category = category;
        return this;
    }

    public Integer getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(Integer prepareTime) {
        this.prepareTime = prepareTime;
    }

    public UpdateRecipe withPrepareTime(Integer prepareTime) {
        this.prepareTime = prepareTime;
        return this;
    }

    public Integer getMealCount() {
        return mealCount;
    }

    public void setMealCount(Integer mealCount) {
        this.mealCount = mealCount;
    }

    public UpdateRecipe withMealCount(Integer mealCount) {
        this.mealCount = mealCount;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public UpdateRecipe withRate(Double rate) {
        this.rate = rate;
        return this;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public UpdateRecipe withPopularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UpdateRecipe withDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getIsRecipeUpdate() {
        return isRecipeUpdate;
    }

    public void setIsRecipeUpdate(Boolean isRecipeUpdate) {
        this.isRecipeUpdate = isRecipeUpdate;
    }

    public UpdateRecipe withIsRecipeUpdate(Boolean isRecipeUpdate) {
        this.isRecipeUpdate = isRecipeUpdate;
        return this;
    }

}