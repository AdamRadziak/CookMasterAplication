
package com.example.cookmasteraplication.api.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Photo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("idRecipe")
    @Expose
    private String idRecipe;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("filePath")
    @Expose
    private String filePath;
    @SerializedName("data")
    @Expose
    private String data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Photo() {
    }

    /**
     * 
     * @param fileName
     * @param data
     * @param filePath
     * @param id
     * @param idRecipe
     */
    public Photo(String id, String idRecipe, String fileName, String filePath, String data) {
        super();
        this.id = id;
        this.idRecipe = idRecipe;
        this.fileName = fileName;
        this.filePath = filePath;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Photo withId(String id) {
        this.id = id;
        return this;
    }

    public String getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(String idRecipe) {
        this.idRecipe = idRecipe;
    }

    public Photo withIdRecipe(String idRecipe) {
        this.idRecipe = idRecipe;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Photo withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Photo withFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Photo withData(String data) {
        this.data = data;
        return this;
    }

}
