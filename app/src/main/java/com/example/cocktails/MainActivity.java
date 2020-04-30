package com.example.cocktails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView historyText;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyText = findViewById(R.id.tv_history);
    }

    public void onClickSearch(View view) {
        Context context = MainActivity.this;

        Class destinationActivity= SearchActivity.class;

        Intent intent = new Intent(context, destinationActivity);
        startActivity(intent);
    }
}
