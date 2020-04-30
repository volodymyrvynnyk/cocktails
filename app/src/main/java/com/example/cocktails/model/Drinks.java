package com.example.cocktails.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Drinks {
    @SerializedName("drinks")
    private List<Cocktail> cocktailList;

    public List<Cocktail> getCocktailList() {
        return cocktailList;
    }

    public void setCocktailList(List<Cocktail> cocktailList) {
        this.cocktailList = cocktailList;
    }
}
