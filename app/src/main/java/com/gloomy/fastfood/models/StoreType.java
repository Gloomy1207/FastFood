package com.gloomy.fastfood.models;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Builder
public class StoreType {
    private int storeTypeId;
    private String typeName;
    private List<RatingType> ratingTypes;
}
