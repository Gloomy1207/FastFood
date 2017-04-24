package com.gloomy.fastfood.models;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Builder
public class StoreAddress {
    private double lat;
    private double lng;
    private String addressName;
    private Province province;
}
