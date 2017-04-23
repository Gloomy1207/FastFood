package com.gloomy.fastfood.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 11-Apr-17.
 */
@Getter
@Builder
@Parcel
public class Food {
    int foodId;
    String foodName;
    String description;
    String recipe;
    List<Image> images;
    float rating;
    String mainImage;
    int numberOfRating;
    String numberOfRatingText;

    @ParcelConstructor
    public Food(int foodId, String foodName, String description, String recipe, List<Image> images, float rating, String mainImage, int numberOfRating, String numberOfRatingText) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.description = description;
        this.recipe = recipe;
        this.images = images;
        this.rating = rating;
        this.mainImage = mainImage;
        this.numberOfRating = numberOfRating;
        this.numberOfRatingText = numberOfRatingText;
    }
}
