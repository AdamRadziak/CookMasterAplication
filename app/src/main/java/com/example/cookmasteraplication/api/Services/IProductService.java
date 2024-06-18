package com.example.cookmasteraplication.api.Services;

import com.example.cookmasteraplication.api.Models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IProductService {
    @GET("Products/list")
    Call<List<Product>> GetProductList();


}
