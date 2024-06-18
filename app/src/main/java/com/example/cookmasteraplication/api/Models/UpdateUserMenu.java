
package com.example.cookmasteraplication.api.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;


@Generated("jsonschema2pojo")
public class UpdateUserMenu {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("idRecipes")
    @Expose
    private List<Integer> idRecipes;

    /**
     * No args constructor for use in serialization
     */
    public UpdateUserMenu() {
    }

    /**
     * @param idUser
     * @param name
     * @param category
     * @param idRecipes
     */
    public UpdateUserMenu(String name, Integer idUser, String category, List<Integer> idRecipes) {
        super();
        this.name = name;
        this.idUser = idUser;
        this.category = category;
        this.idRecipes = idRecipes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UpdateUserMenu withName(String name) {
        this.name = name;
        return this;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public UpdateUserMenu withIdUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UpdateUserMenu withCategory(String category) {
        this.category = category;
        return this;
    }

    public List<Integer> getIdRecipes() {
        return idRecipes;
    }

    public void setIdRecipes(List<Integer> idRecipes) {
        this.idRecipes = idRecipes;
    }

    public UpdateUserMenu withIdRecipes(List<Integer> idRecipes) {
        this.idRecipes = idRecipes;
        return this;
    }

}
