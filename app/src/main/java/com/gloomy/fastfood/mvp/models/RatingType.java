package com.gloomy.fastfood.mvp.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Parcel
public class RatingType {
    int ratingTypeId;
    int ratingTypeName;

    @ParcelConstructor
    public RatingType(int ratingTypeId, int ratingTypeName) {
        this.ratingTypeId = ratingTypeId;
        this.ratingTypeName = ratingTypeName;
    }
}
