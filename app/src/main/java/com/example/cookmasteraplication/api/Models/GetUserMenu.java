
package com.example.cookmasteraplication.api.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class GetUserMenu {

    @SerializedName("pageCount")
    @Expose
    private Integer pageCount;
    @SerializedName("totalItemCount")
    @Expose
    private Integer totalItemCount;
    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("hasPreviousPage")
    @Expose
    private Boolean hasPreviousPage;
    @SerializedName("hasNextPage")
    @Expose
    private Boolean hasNextPage;
    @SerializedName("isFirstPage")
    @Expose
    private Boolean isFirstPage;
    @SerializedName("isLastPage")
    @Expose
    private Boolean isLastPage;
    @SerializedName("firstItemOnPage")
    @Expose
    private Integer firstItemOnPage;
    @SerializedName("lastItemOnPage")
    @Expose
    private Integer lastItemOnPage;
    @SerializedName("pageData")
    @Expose
    private List<PageDatumUserMenu> pageData;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetUserMenu() {
    }

    /**
     * 
     * @param pageCount
     * @param pageNumber
     * @param hasNextPage
     * @param isLastPage
     * @param pageSize
     * @param hasPreviousPage
     * @param pageData
     * @param isFirstPage
     * @param firstItemOnPage
     * @param lastItemOnPage
     * @param totalItemCount
     */
    public GetUserMenu(Integer pageCount, Integer totalItemCount, Integer pageNumber, Integer pageSize, Boolean hasPreviousPage, Boolean hasNextPage, Boolean isFirstPage, Boolean isLastPage, Integer firstItemOnPage, Integer lastItemOnPage, List<PageDatumUserMenu> pageData) {
        super();
        this.pageCount = pageCount;
        this.totalItemCount = totalItemCount;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.hasPreviousPage = hasPreviousPage;
        this.hasNextPage = hasNextPage;
        this.isFirstPage = isFirstPage;
        this.isLastPage = isLastPage;
        this.firstItemOnPage = firstItemOnPage;
        this.lastItemOnPage = lastItemOnPage;
        this.pageData = pageData;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public GetUserMenu withPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(Integer totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public GetUserMenu withTotalItemCount(Integer totalItemCount) {
        this.totalItemCount = totalItemCount;
        return this;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public GetUserMenu withPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public GetUserMenu withPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Boolean getHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(Boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public GetUserMenu withHasPreviousPage(Boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
        return this;
    }

    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public GetUserMenu withHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
        return this;
    }

    public Boolean getIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(Boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public GetUserMenu withIsFirstPage(Boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
        return this;
    }

    public Boolean getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(Boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public GetUserMenu withIsLastPage(Boolean isLastPage) {
        this.isLastPage = isLastPage;
        return this;
    }

    public Integer getFirstItemOnPage() {
        return firstItemOnPage;
    }

    public void setFirstItemOnPage(Integer firstItemOnPage) {
        this.firstItemOnPage = firstItemOnPage;
    }

    public GetUserMenu withFirstItemOnPage(Integer firstItemOnPage) {
        this.firstItemOnPage = firstItemOnPage;
        return this;
    }

    public Integer getLastItemOnPage() {
        return lastItemOnPage;
    }

    public void setLastItemOnPage(Integer lastItemOnPage) {
        this.lastItemOnPage = lastItemOnPage;
    }

    public GetUserMenu withLastItemOnPage(Integer lastItemOnPage) {
        this.lastItemOnPage = lastItemOnPage;
        return this;
    }

    public List<PageDatumUserMenu> getPageData() {
        return pageData;
    }

    public void setPageData(List<PageDatumUserMenu> pageData) {
        this.pageData = pageData;
    }

    public GetUserMenu withPageData(List<PageDatumUserMenu> pageData) {
        this.pageData = pageData;
        return this;
    }

}
