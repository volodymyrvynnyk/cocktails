package com.example.cocktails;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktails.adapter.CocktailsAdapter;
import com.example.cocktails.dao.NetworkService;
import com.example.cocktails.model.Cocktail;
import com.example.cocktails.model.Drinks;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private EditText searchField;

    private TextView searchStatus;

    private RecyclerView cocktailsFoundView;
    private CocktailsAdapter cocktailsAdapter;

    private static final  String ERROR_MESSAGE = "Network error, try again";
    private static final String EMPTY_RESULT_MESSAGE = "No cocktails found";
    private static final String TAG = "ApiLog";

    private static int ITEMS_IN_LINE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = findViewById(R.id.ed_search);
        searchStatus = findViewById(R.id.tv_search_status);
        cocktailsFoundView = findViewById(R.id.rv_cocktails_found_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, ITEMS_IN_LINE);
        cocktailsFoundView.setLayoutManager(gridLayoutManager);

        cocktailsAdapter = new CocktailsAdapter(getApplicationContext(), new ArrayList<Cocktail>());
        cocktailsFoundView.setAdapter(cocktailsAdapter);

        searchField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (searchField.getText().toString().length() > 0) {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
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
                    public void onResponse(@NonNull Call<Drinks> call,
                                           @NonNull Response<Drinks> response) {
                        Log.d(TAG, "Response success");
                        cocktailsAdapter.setCocktails(response.body().getCocktailList());
                        cocktailsAdapter.notifyDataSetChanged();
                        if (cocktailsAdapter.getItemCount() == 0) {
                            Log.d(TAG, "Empty response");
                            hideRecyclerView(EMPTY_RESULT_MESSAGE);
                        } else {
                            showRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Drinks> call, @NonNull Throwable t) {
                        Log.d(TAG, "Response failed:" + t.getMessage());
                        hideRecyclerView(ERROR_MESSAGE);
                        t.printStackTrace();
                    }
                });
    }

    private void hideRecyclerView(String message) {
        searchStatus.setVisibility(View.VISIBLE);
        cocktailsFoundView.setVisibility(View.INVISIBLE);
        searchStatus.setText(message);
    }

    private void showRecyclerView() {
        cocktailsFoundView.setVisibility(View.VISIBLE);
        searchStatus.setVisibility(View.INVISIBLE);
    }
}
