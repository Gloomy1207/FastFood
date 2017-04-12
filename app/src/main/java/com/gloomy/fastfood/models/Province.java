package com.gloomy.fastfood.models;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
@Builder
public class Province {
    private int provinceId;
    private String provinceName;
    private City city;
    private String numberPlaceText;
}
