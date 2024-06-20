package com.example.cookmasteraplication.Api.RetrofitClients;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseClient {
    private static final String API_BASE_URL = "http://192.168.0.122/api/";

    public static Retrofit get_client() {
        // Create a new object from HttpLoggingInterceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12,TimeUnit.SECONDS);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        return builder.client(httpClient.build()).build();

    }

    public static Retrofit get_AuthClient(String email, String password){
        // Create a new object for logginf interceptor
        BasicAuthInterceptor basicAuthInterceptor = new BasicAuthInterceptor(email,password);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(basicAuthInterceptor)
                .readTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12,TimeUnit.SECONDS);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        return builder.client(httpClient.build()).build();
    }

}
