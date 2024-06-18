package com.example.cookmasteraplication.api.Services;

import com.example.cookmasteraplication.api.Models.UserAccount;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserAccountService {
    @POST("Users/register")
    <response>
    Call<UserAccount> CreateAccount(@Body UserAccount userAccount);
    @GET("Users/LogInByEmail/{emailHash}/Password/{passwordHash}")
    Call<UserAccount> LogIn(@Path("emailHash") String email, @Path("passwordHash") String password);
    @GET("Users/GetUserPassBy/{email}")
    Call<UserAccount> GetUserPassByEmail(@Path("email") String email);
    @PUT("Users/updatePass/{id}")
    Call<UserAccount> UpdateUserPass(@Body UserAccount userAccount,@Path("id") Integer id);
    @DELETE("Users/{id}")
    Call<UserAccount> DeleteUser(@Path("id") Integer id);

}
