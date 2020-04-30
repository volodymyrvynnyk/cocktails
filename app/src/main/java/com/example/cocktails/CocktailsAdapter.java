package com.example.cocktails;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktails.model.Cocktail;
import com.example.cocktails.util.DownloadImageTask;

import java.util.List;

public class CocktailsAdapter extends RecyclerView.Adapter<CocktailsAdapter.CocktailViewHolder> {

    private Context context;
    private List<Cocktail> cocktails;

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
        View view = LayoutInflater.from(context).inflate(R.layout.rv_cocktails_item, parent, false);
        return new CocktailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder holder, int position) {
        holder.title.setText(cocktails.get(position).getStrDrink());
        new DownloadImageTask(holder.image)
                .execute(cocktails.get(position).getStrDrinkThumb());
//        holder.image.setImageURI(Uri.parse(cocktails.get(position).getStrDrinkThumb()));

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



