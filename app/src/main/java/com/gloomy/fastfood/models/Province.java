package com.gloomy.fastfood.models;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
@Getter
public class Province {
    private int provinceId;
    private String provinceName;
    private City city;
}
