package com.gloomy.fastfood.models;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 11-Apr-17.
 */
@Getter
@Builder
public class Food {
    private int foodId;
    private String foodName;
    private String description;
    private String recipe;
    private List<FoodImage> foodImages;
    private float rating;
    private String mainImage;
    private int numberOfRating;
    private String numberOfRatingText;
}
