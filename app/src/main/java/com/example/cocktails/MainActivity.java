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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktails.model.Cocktail;
import com.example.cocktails.service.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int ITEMS_IN_LINE = 2;

    private TextView emptyHistoryText;

    private DBHelper dbHelper;

    private List<Cocktail> historyList;

    private RecyclerView historyView;

    private CocktailsAdapter cocktailsAdapter;

    private LinearLayout historyLayout;

    private Button removeHistoryBtn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyHistoryText = findViewById(R.id.tv_empty_history);
        removeHistoryBtn = findViewById(R.id.b_clear_history);
        historyLayout = findViewById(R.id.ll_history);

        getHistory();
        if (historyList.isEmpty()) {
            hideHistory();
        } else {
            showHistory();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
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

        historyView = findViewById(R.id.rv_cocktails_history_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, ITEMS_IN_LINE);
        historyView.setLayoutManager(gridLayoutManager);
        cocktailsAdapter = new CocktailsAdapter(getApplicationContext(), historyList);
        historyView.setAdapter(cocktailsAdapter);
    }

    private void getHistory() {
        dbHelper = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_HISTORY,
                null, null, null,
                null, null, DBHelper.KEY_VIEW_DATE + " DESC");
        historyList = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Cocktail cocktail = new Cocktail();
            cocktail.setIdDrink(cursor.getLong(cursor.getColumnIndex(DBHelper.KEY_COCKTAIL_ID)));
            cocktail.setStrDrink(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)));
            cocktail.setStrDrinkThumb(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_IMAGE_URL)));
            historyList.add(cocktail);
        }
        dbHelper.close();
    }

    public void onClickStartSearchActivity(View view) {
        Context context = MainActivity.this;

        Class destinationActivity = SearchActivity.class;

        Intent intent = new Intent(context, destinationActivity);
        startActivity(intent);
    }

    public void clearHistory(View view) {
        dbHelper = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ DBHelper.TABLE_HISTORY);
        hideHistory();
    }

}
