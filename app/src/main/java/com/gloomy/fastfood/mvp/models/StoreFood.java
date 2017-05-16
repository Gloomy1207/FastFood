package com.gloomy.fastfood.mvp.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 25-Apr-17.
 */
@Getter
@Setter
public class StoreFood {
    private String localName;
    private Food food;
    private double price;
}
