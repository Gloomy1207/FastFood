package com.gloomy.fastfood.models;

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
    private StoreAddress storeAddress;
    private List<StoreImage> storeImages;
    private StoreType storeType;
    private String mainImage;
    private float averageRating;
}
