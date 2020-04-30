package com.example.cocktails;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktails.model.Cocktail;
import com.example.cocktails.model.Drinks;
import com.example.cocktails.service.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private EditText searchField;

    private TextView searchStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = findViewById(R.id.ed_search);
        searchStatus = findViewById(R.id.tv_search_status);

        searchField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (!searchField.getText().toString().equals("")) {
                        searchCocktails(searchField.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void searchCocktails(String name) {
        NetworkService.getInstance()
                .getCocktailsApi()
                .getCocktailsByName(name)
                .enqueue(new Callback<Drinks>() {
                    @Override
                    public void onResponse(@NonNull Call<Drinks> call, @NonNull Response<Drinks> response) {
                        Drinks drinks = response.body();
                        if (drinks.getCocktailList() == null) {
                            searchStatus.setText("No cocktails found");
                        } else {
                            searchStatus.setText("");
                            for (Cocktail cocktail : drinks.getCocktailList())
                            searchStatus.append(cocktail.getStrDrink() + "\n");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Drinks> call, @NonNull Throwable t) {
                        searchStatus.setText("Network Error, try again");
                        t.printStackTrace();
                    }
                });
    }
}
