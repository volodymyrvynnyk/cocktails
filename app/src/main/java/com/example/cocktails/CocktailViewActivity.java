package com.example.cocktails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cocktails.model.Cocktail;

public class CocktailViewActivity extends AppCompatActivity {

    private Cocktail cocktail;

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
        setContentView(R.layout.activity_cocktail_view);
        Intent intentThatStartedThisOne = getIntent();

        if (intentThatStartedThisOne.hasExtra(Intent.EXTRA_CHOSEN_COMPONENT)) {
            this.cocktail = (Cocktail) intentThatStartedThisOne.getSerializableExtra(Intent.EXTRA_CHOSEN_COMPONENT);
        }

        nameToolbar = findViewById(R.id.tv_cocktail_name);
        nameToolbar.setText(cocktail.getStrDrink());
        name = findViewById(R.id.tv_info_name);
        name.setText(cocktail.getStrDrink());
        alcoholic = findViewById(R.id.tv_info_alcoholic);
        alcoholic.setText(cocktail.getStrAlcoholic());
        glass = findViewById(R.id.tv_info_glass);
        glass.setText(cocktail.getStrGlass());

        image = findViewById(R.id.iv_info_image);
        Glide.with(this).load(cocktail.getStrDrinkThumb()).into(image);

        ingredients = findViewById(R.id.lv_info_ingredients);
        IngredientsAdapter adapter = new IngredientsAdapter(this, cocktail.getIngredients());
        ingredients.setAdapter(adapter);

        instruction = findViewById(R.id.sv_info_instruction);
        instruction.setText(cocktail.getStrInstructions());

    }

    public void previousActivity(View view) {
        finish();
    }
}
