package com.example.cocktails.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cocktails.CocktailInfoActivity;
import com.example.cocktails.R;
import com.example.cocktails.model.Cocktail;

import java.util.List;

public class CocktailsAdapter extends RecyclerView.Adapter<CocktailsAdapter.CocktailViewHolder> {

    private Context context;
    private List<Cocktail> cocktails;

    private static String COCKTAIL_ID_INTENT_EXTRA_NAME = "cocktail_id";

    public CocktailsAdapter(Context context, List<Cocktail> cocktails) {
        this.context = context;
        this.cocktails = cocktails;
    }

    public void setCocktails(List<Cocktail> cocktails) {
        this.cocktails = cocktails;
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cocktails_list_item, parent, false);
        return new CocktailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder holder, final int position) {

        holder.title.setText(cocktails.get(position).getStrDrink());
        Glide.with(holder.itemView)
                .load(cocktails.get(position).getStrDrinkThumb())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CocktailInfoActivity.class);
                intent.putExtra(COCKTAIL_ID_INTENT_EXTRA_NAME, cocktails.get(position).getIdDrink());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cocktails != null) {
            return cocktails.size();
        }
        return 0;
    }

    class CocktailViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;

        public CocktailViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.item_cocktail_image);
            title = itemView.findViewById(R.id.item_cocktail_title);
        }

    }

}



