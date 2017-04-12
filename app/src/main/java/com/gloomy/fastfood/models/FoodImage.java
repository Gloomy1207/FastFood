package com.gloomy.fastfood.models;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 11-Apr-17.
 */
@Getter
@Builder
public class FoodImage {
    private int id;
    private String imagePath;
}
