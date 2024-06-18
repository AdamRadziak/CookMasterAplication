
package com.example.cookmasteraplication.api.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;


@Generated("jsonschema2pojo")
public class GenerateUserMenu {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("dayCount")
    @Expose
    private Integer dayCount;
    @SerializedName("mealCount")
    @Expose
    private Integer mealCount;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("prepareTime")
    @Expose
    private Integer prepareTime;


    /**
     * @param idUser
     * @param dayCount
     * @param rate
     * @param prepareTime
     * @param popularity
     * @param name
     * @param mealCount
     * @param category
     */
    public GenerateUserMenu(String name, Integer idUser, String category, Integer dayCount,
                            Integer mealCount, Double rate, Double popularity, Integer prepareTime) {
//        super();
        this.name = name;
        this.idUser = idUser;
        this.category = category;
        this.dayCount = dayCount;
        this.mealCount = mealCount;
        this.rate = rate;
        this.popularity = popularity;
        this.prepareTime = prepareTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenerateUserMenu withName(String name) {
        this.name = name;
        return this;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public GenerateUserMenu withIdUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public GenerateUserMenu withCategory(String category) {
        this.category = category;
        return this;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public GenerateUserMenu withDayCount(Integer dayCount) {
        this.dayCount = dayCount;
        return this;
    }

    public Integer getMealCount() {
        return mealCount;
    }

    public void setMealCount(Integer mealCount) {
        this.mealCount = mealCount;
    }

    public GenerateUserMenu withMealCount(Integer mealCount) {
        this.mealCount = mealCount;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public GenerateUserMenu withRate(Double rate) {
        this.rate = rate;
        return this;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public GenerateUserMenu withPopularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    public Integer getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(Integer prepareTime) {
        this.prepareTime = prepareTime;
    }

    public GenerateUserMenu withPrepareTime(Integer prepareTime) {
        this.prepareTime = prepareTime;
        return this;
    }

}
