
package com.example.cookmasteraplication.api.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Step {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("stepNum")
    @Expose
    private Integer stepNum;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Step() {
    }

    /**
     * 
     * @param stepNum
     * @param description
     * @param id
     */
    public Step(Integer id, Integer stepNum, String description) {
        super();
        this.id = id;
        this.stepNum = stepNum;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Step withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getStepNum() {
        return stepNum;
    }

    public void setStepNum(Integer stepNum) {
        this.stepNum = stepNum;
    }

    public Step withStepNum(Integer stepNum) {
        this.stepNum = stepNum;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Step withDescription(String description) {
        this.description = description;
        return this;
    }

}
