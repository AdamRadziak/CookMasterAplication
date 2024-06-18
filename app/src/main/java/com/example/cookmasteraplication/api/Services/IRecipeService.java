package com.example.cookmasteraplication.api.Services;

import com.example.cookmasteraplication.api.Models.Recipe;
import com.example.cookmasteraplication.api.Models.UpdateRecipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRecipeService {
    @GET("Recipes/{id}")
    Call<Recipe> GetRecipe(@Path("id") Integer id);
    @GET("Recipes/list")
    Call<List<Recipe>> GetRecipeList(@Query("Filters") String filter,
                                     @Query("Sorts") String sort);
    @PUT("Recipes/update/{id}")
    Call<Recipe> UpdateRecipe(@Body UpdateRecipe updateRecipe, @Path("id") Integer id);
    @POST("Recipes/AddFavourite/{IdRecipe}/ForUser/{Email}")
    Call<Recipe> AddRecipe2Favourites(@Path("IdRecipe") Integer IdRecipe, @Path("Email") String Email);
    @GET("Recipes/list/GetFavourities/ForUser/{Email}")
    Call<List<Recipe>> ListFavoritesRecipeByUser(@Path("Email") String Email);
    @DELETE("Recipes/DeleteFavourite/{id}/ForUser/{Email}")
    Call<Recipe> DeleteRecipeFromFavourites(@Path("id") Integer id,@Path("Email") String Email);

}
