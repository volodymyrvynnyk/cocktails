package com.example.cocktails;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktails.adapter.CocktailsAdapter;
import com.example.cocktails.dao.CocktailsDbHelper;
import com.example.cocktails.model.Cocktail;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int ITEMS_IN_LINE = 2;

    private TextView emptyHistoryText;

    private CocktailsDbHelper cocktailsDbHelper;

    private List<Cocktail> historyList;

    private RecyclerView historyViewList;

    private LinearLayout historyLayout;

    private Button removeHistoryBtn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyHistoryText = findViewById(R.id.tv_empty_history);
        removeHistoryBtn = findViewById(R.id.b_clear_history);
        historyLayout = findViewById(R.id.ll_history);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getHistory();
        if (historyList.isEmpty()) {
            hideHistory();
        } else {
            showHistory();
        }
    }

    private void hideHistory() {
        historyLayout.setVisibility(View.INVISIBLE);
        removeHistoryBtn.setVisibility(View.INVISIBLE);
        emptyHistoryText.setVisibility(View.VISIBLE);
    }

    private void showHistory() {
        historyLayout.setVisibility(View.VISIBLE);
        emptyHistoryText.setVisibility(View.INVISIBLE);
        removeHistoryBtn.setVisibility(View.VISIBLE);

        historyViewList = findViewById(R.id.rv_cocktails_history_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, ITEMS_IN_LINE);
        historyViewList.setLayoutManager(gridLayoutManager);
        CocktailsAdapter cocktailsAdapter = new CocktailsAdapter(getApplicationContext(), historyList);
        historyViewList.setAdapter(cocktailsAdapter);
    }

    private void getHistory() {
        cocktailsDbHelper = new CocktailsDbHelper(this);
        SQLiteDatabase sqLiteDatabase = cocktailsDbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(CocktailsDbHelper.TABLE_HISTORY,
                null, null, null,
                null, null, CocktailsDbHelper.KEY_VIEW_DATE + " DESC");
        historyList = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Cocktail cocktail = new Cocktail();
            cocktail.setIdDrink(cursor.getLong(cursor.getColumnIndex(CocktailsDbHelper.KEY_COCKTAIL_ID)));
            cocktail.setStrDrink(cursor.getString(cursor.getColumnIndex(CocktailsDbHelper.KEY_NAME)));
            cocktail.setStrDrinkThumb(cursor.getString(cursor.getColumnIndex(CocktailsDbHelper.KEY_IMAGE_URL)));
            historyList.add(cocktail);
        }
        cocktailsDbHelper.close();
    }

    public void onClickStartSearchActivity(View view) {
        Context context = MainActivity.this;

        Class destinationActivity = SearchActivity.class;

        Intent intent = new Intent(context, destinationActivity);
        startActivity(intent);
    }

    public void clearHistory(View view) {
        cocktailsDbHelper = new CocktailsDbHelper(this);
        SQLiteDatabase sqLiteDatabase = cocktailsDbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + CocktailsDbHelper.TABLE_HISTORY);
        hideHistory();
    }

}
