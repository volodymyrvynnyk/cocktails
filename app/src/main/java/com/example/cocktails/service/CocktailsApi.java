package com.example.cocktails.service;

import com.example.cocktails.model.Drinks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CocktailsApi {

    @GET("/api/json/v1/1/search.php")
    public Call<Drinks> getCocktailsByName(@Query("s") String name);

    @GET("/api/json/v1/1/lookup.php")
    public Call<Drinks> getCocktailById(@Query("i") Long id);
}
