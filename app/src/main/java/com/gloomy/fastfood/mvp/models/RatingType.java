package com.gloomy.fastfood.mvp.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Getter;
import lombok.Setter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Setter
@Parcel
public class RatingType {
    int ratingTypeId;
    String ratingTypeName;
    float value;
    boolean isLarge;

    @ParcelConstructor
    public RatingType(int ratingTypeId, String ratingTypeName, float value, boolean isLarge) {
        this.ratingTypeId = ratingTypeId;
        this.ratingTypeName = ratingTypeName;
        this.value = value;
        this.isLarge = isLarge;
    }
}
