package com.gloomy.fastfood.mvp.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Builder
@Parcel
public class StoreImage {
    int id;
    String imagePath;

    @ParcelConstructor
    public StoreImage(int id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }
}
