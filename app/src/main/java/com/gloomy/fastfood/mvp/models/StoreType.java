package com.gloomy.fastfood.mvp.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Builder
@Parcel
public class StoreType {
    int storeTypeId;
    String typeName;
    List<RatingType> ratingTypes;

    @ParcelConstructor
    public StoreType(int storeTypeId, String typeName, List<RatingType> ratingTypes) {
        this.storeTypeId = storeTypeId;
        this.typeName = typeName;
        this.ratingTypes = ratingTypes;
    }
}
