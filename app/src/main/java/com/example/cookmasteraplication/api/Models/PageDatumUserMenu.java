
package com.example.cookmasteraplication.api.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class PageDatumUserMenu {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PageDatumUserMenu() {
    }

    /**
     * 
     * @param idUser
     * @param recipes
     * @param name
     * @param id
     * @param category
     */
    public PageDatumUserMenu(Integer id, Integer idUser, String name, String category, List<Recipe> recipes) {
        super();
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.category = category;
        this.recipes = recipes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PageDatumUserMenu withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public PageDatumUserMenu withIdUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageDatumUserMenu withName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PageDatumUserMenu withCategory(String category) {
        this.category = category;
        return this;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public PageDatumUserMenu withRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        return this;
    }

}
