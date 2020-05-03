package com.example.cocktails;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cocktails.adapter.IngredientsAdapter;
import com.example.cocktails.model.Cocktail;
import com.example.cocktails.model.Drinks;
import com.example.cocktails.dao.CocktailsDbHelper;
import com.example.cocktails.dao.NetworkService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CocktailInfoActivity extends AppCompatActivity {

    private Cocktail cocktail;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String COCKTAIL_ID_INTENT_EXTRA_NAME = "cocktail_id";

    private CocktailsDbHelper cocktailsDbHelper;

    private TextView nameToolbar;
    private TextView name;
    private TextView alcoholic;
    private TextView glass;
    private ImageView image;
    private ListView ingredients;
    private TextView instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_info);


        nameToolbar = findViewById(R.id.tv_cocktail_info_title);
        name = findViewById(R.id.tv_cocktail_info_name);
        alcoholic = findViewById(R.id.tv_cocktail_info_alcoholic);
        glass = findViewById(R.id.tv_cocktail_info_glass);
        image = findViewById(R.id.iv_cocktail_info_image);
        ingredients = findViewById(R.id.lv_cocktail_info_ingredients_list);
        instruction = findViewById(R.id.tv_cocktail_info_instruction);

        Intent intentThatStartedThisOne = getIntent();

        if (intentThatStartedThisOne.hasExtra(COCKTAIL_ID_INTENT_EXTRA_NAME)) {
            findCocktailById(intentThatStartedThisOne.getLongExtra(COCKTAIL_ID_INTENT_EXTRA_NAME, -1l));
        }



    }

    private void fillField() {
        nameToolbar.setText(cocktail.getStrDrink());
        name.setText(cocktail.getStrDrink());
        alcoholic.setText(cocktail.getStrAlcoholic());
        glass.setText(cocktail.getStrGlass());
        Glide.with(this).load(cocktail.getStrDrinkThumb()).into(image);
        IngredientsAdapter adapter = new IngredientsAdapter(this, cocktail.getIngredients());
        ingredients.setAdapter(adapter);
        instruction.setText(cocktail.getStrInstructions());

    }

    private void saveToHistory() {
        cocktailsDbHelper = new CocktailsDbHelper(this);
        SQLiteDatabase sqLiteDatabase = cocktailsDbHelper.getWritableDatabase();
        sqLiteDatabase.delete(CocktailsDbHelper.TABLE_HISTORY,
                CocktailsDbHelper.KEY_COCKTAIL_ID + " =?",
                new String[]{String.valueOf(cocktail.getIdDrink())});
        ContentValues contentValues = new ContentValues();
        contentValues.put(CocktailsDbHelper.KEY_COCKTAIL_ID, cocktail.getIdDrink());
        contentValues.put(CocktailsDbHelper.KEY_NAME, cocktail.getStrDrink());
        contentValues.put(CocktailsDbHelper.KEY_IMAGE_URL, cocktail.getStrDrinkThumb());
        contentValues.put(CocktailsDbHelper.KEY_VIEW_DATE, dateFormat.format(new Date()));
        sqLiteDatabase.insert(CocktailsDbHelper.TABLE_HISTORY, null, contentValues);
        cocktailsDbHelper.close();
    }

    public void onClickBack(View view) {
        finish();
    }

    private void findCocktailById(Long id) {
        NetworkService.getInstance()
                .getCocktailsApi()
                .getCocktailById(id)
                .enqueue(new Callback<Drinks>() {
                    @Override
                    public void onResponse(@NonNull Call<Drinks> call,
                                           @NonNull Response<Drinks> response) {
                        Drinks drinks = response.body();
                        cocktail = drinks.getCocktailList().get(0);
                        fillField();
                        saveToHistory();

                    }

                    @Override
                    public void onFailure(@NonNull Call<Drinks> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
