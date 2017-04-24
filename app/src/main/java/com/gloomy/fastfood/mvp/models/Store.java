package com.gloomy.fastfood.mvp.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.sql.Time;
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
public class Store {
    int placeId;
    @SerializedName("place_name")
    String storeName;
    String description;
    Time openTime;
    Time closeTime;
    @SerializedName("place_address")
    StoreAddress storeAddress;
    List<StoreImage> storeImages;
    @SerializedName("place_type")
    StoreType storeType;
    String mainImage;
    float averageRating;
    String numberRating;

    @ParcelConstructor
    public Store(int placeId, String storeName, String description, Time openTime, Time closeTime, StoreAddress storeAddress, List<StoreImage> storeImages, StoreType storeType, String mainImage, float averageRating, String numberRating) {
        this.placeId = placeId;
        this.storeName = storeName;
        this.description = description;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.storeAddress = storeAddress;
        this.storeImages = storeImages;
        this.storeType = storeType;
        this.mainImage = mainImage;
        this.averageRating = averageRating;
        this.numberRating = numberRating;
    }
}
