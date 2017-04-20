package com.gloomy.fastfood.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 11-Apr-17.
 */
@Getter
@Builder
@Parcel
public class FoodImage {
    int id;
    String imagePath;

    @ParcelConstructor
    public FoodImage(int id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }
}
