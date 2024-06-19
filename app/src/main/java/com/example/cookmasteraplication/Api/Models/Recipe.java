
package com.example.cookmasteraplication.Api.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Recipe {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("idMenu")
    @Expose
    private Integer idMenu;
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
    @SerializedName("photos")
    @Expose
    private List<Photo> photos;
    @SerializedName("steps")
    @Expose
    private List<Step> steps;
    @SerializedName("products")
    @Expose
    private List<Product> products;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Recipe() {
    }

    /**
     * 
     * @param idMenu
     * @param description
     * @param mealCount
     * @param photos
     * @param steps
     * @param products
     * @param idUser
     * @param prepareTime
     * @param rate
     * @param popularity
     * @param name
     * @param id
     * @param category
     */
    public Recipe(Integer id, Integer idUser, Integer idMenu, String name, String category, Integer prepareTime, Integer mealCount, Double rate, Double popularity, String description, List<Photo> photos, List<Step> steps, List<Product> products) {
        super();
        this.id = id;
        this.idUser = idUser;
        this.idMenu = idMenu;
        this.name = name;
        this.category = category;
        this.prepareTime = prepareTime;
        this.mealCount = mealCount;
        this.rate = rate;
        this.popularity = popularity;
        this.description = description;
        this.photos = photos;
        this.steps = steps;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recipe withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Recipe withIdUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public Recipe withIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recipe withName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Recipe withCategory(String category) {
        this.category = category;
        return this;
    }

    public Integer getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(Integer prepareTime) {
        this.prepareTime = prepareTime;
    }

    public Recipe withPrepareTime(Integer prepareTime) {
        this.prepareTime = prepareTime;
        return this;
    }

    public Integer getMealCount() {
        return mealCount;
    }

    public void setMealCount(Integer mealCount) {
        this.mealCount = mealCount;
    }

    public Recipe withMealCount(Integer mealCount) {
        this.mealCount = mealCount;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Recipe withRate(Double rate) {
        this.rate = rate;
        return this;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Recipe withPopularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recipe withDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Recipe withPhotos(List<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Recipe withSteps(List<Step> steps) {
        this.steps = steps;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Recipe withProducts(List<Product> products) {
        this.products = products;
        return this;
    }

}
