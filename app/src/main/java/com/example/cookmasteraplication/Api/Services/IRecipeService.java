package com.example.cookmasteraplication.Api.Services;

import com.example.cookmasteraplication.Api.Models.Recipe;
import com.example.cookmasteraplication.Api.Models.RecipeList;
import com.example.cookmasteraplication.Api.Models.UpdateRecipe;

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
    Call<RecipeList> GetRecipeList(@Query("Filters") String filter,
                                     @Query("Sorts") String sort);
    @PUT("Recipes/update/{id}")
    Call<Recipe> UpdateRecipe(@Body UpdateRecipe updateRecipe, @Path("id") Integer id);
    @POST("Recipes/AddFavourite/{IdRecipe}/ForUser/{idUser}")
    Call<Recipe> AddRecipe2Favourites(@Path("IdRecipe") Integer IdRecipe, @Path("idUser") Integer id);
    @GET("Recipes/list/GetFavourities/ForUser/{Email}")
    Call<RecipeList> ListFavoritesRecipeByUser(@Path("Email") String Email);
    @DELETE("Recipes/DeleteFavourite/{id}/ForUser/{Email}")
    Call<Recipe> DeleteRecipeFromFavourites(@Path("id") Integer id,@Path("Email") String Email);

}
