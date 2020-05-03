package com.example.cocktails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cocktails.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IngredientsAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> keys;
    private final Map<String, String> ingredients;
    private final LayoutInflater inflater;

    public IngredientsAdapter(Context context, Map<String, String> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
        this.keys = new ArrayList<>(ingredients.keySet());
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.cocktail_info_ingredient_item, null);
        TextView ingredient = view.findViewById(R.id.tv_ingredient_name);
        TextView measure = view.findViewById(R.id.tv_ingredient_measure);
        String key = keys.get(i);
        ingredient.setText(i+1 + ". " + key);
        measure.setText(ingredients.get(key));
        return view;
    }
}
