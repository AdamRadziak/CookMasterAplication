package com.example.cookmasteraplication.Api.Services;

import com.example.cookmasteraplication.Api.Models.GenerateUserMenu;
import com.example.cookmasteraplication.Api.Models.GetUserMenu;
import com.example.cookmasteraplication.Api.Models.PageDatumUserMenu;
import com.example.cookmasteraplication.Api.Models.UpdateUserMenu;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserMenuService {
    @POST("UserMenus/Generate")
    Call<PageDatumUserMenu> GenerateUserMenu(@Body GenerateUserMenu generateUserMenu);
    @GET("UserMenus/listByUserId/{IdUser}")
    Call<GetUserMenu> ListUserMenuByIdUser(@Path("IdUser") Integer IdUser);
    @DELETE("UserMenus/{id}")
    Call<PageDatumUserMenu> DeleteMenu(@Path("id") Integer id);
    @PUT("UserMenus/update/{id}")
    Call<PageDatumUserMenu> UpdateMenu(@Body UpdateUserMenu updateUserMenu, @Path("id") Integer id);

}
