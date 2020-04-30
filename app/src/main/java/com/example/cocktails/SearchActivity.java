package com.example.cocktails;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktails.model.Drinks;
import com.example.cocktails.service.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void find(View view) {
        System.out.println("yes boss");
        NetworkService.getInstance()
                .getCocktailsApi()
                .getCocktailsByName("margarita")
                .enqueue(new Callback<Drinks>() {
                    @Override
                    public void onResponse(@NonNull Call<Drinks> call, @NonNull Response<Drinks> response) {
                        Drinks drinks = response.body();
                        if (drinks.getCocktailList() == null) {
                            onFailure(call, new NullPointerException());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Drinks> call, @NonNull Throwable t) {

                        t.printStackTrace();
                    }
                });
    }
}
