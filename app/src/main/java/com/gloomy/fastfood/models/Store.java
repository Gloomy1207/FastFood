package com.gloomy.fastfood.models;

import com.google.gson.annotations.SerializedName;

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
public class Store {
    private int placeId;
    private String placeName;
    private String description;
    private Time openTime;
    private Time closeTime;
    @SerializedName("place_address")
    private StoreAddress storeAddress;
    private List<StoreImage> storeImages;
    @SerializedName("place_type")
    private StoreType storeType;
    private String mainImage;
    private float averageRating;
    private String numberRating;
}
