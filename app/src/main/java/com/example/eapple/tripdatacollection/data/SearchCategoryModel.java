package com.example.eapple.tripdatacollection.data;

import com.example.eapple.tripdatacollection.R;

import java.util.ArrayList;
import java.util.List;

// Model class for showing different selection in search category activity.
public class SearchCategoryModel {
    String name;
    int image;

    private static String names[] = {
            "attraction_points", "hotels", "restaurant", "bank", "gas_station"
            , "mobile_wallet", "rental", "shopping_mart", "workshop"};

    private static int images[] = {
            R.drawable.tile_attraction, R.drawable.tile_hotel, R.drawable.tile_restaurant,
            R.drawable.tile_bank, R.drawable.tile_gasstation, R.drawable.tile_mobilewallet,
            R.drawable.tile_rental, R.drawable.tile_shoppingmart, R.drawable.tile_workshop};

    public SearchCategoryModel(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static List<SearchCategoryModel> getCategoryModelList() {
        List<SearchCategoryModel> list = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            list.add(new SearchCategoryModel(names[i], images[i]));
        }

        return list;
    }

}
