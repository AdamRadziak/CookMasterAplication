package com.example.cookmasteraplication.Api.Services;

import com.example.cookmasteraplication.Api.Models.ProductList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IProductService {
    @GET("Products/list")
    Call<ProductList> GetProductList();


}
