package com.gloomy.fastfood.models;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
@Getter
public class User {
    private Long userId;
    private String username;
    private String fullname;
    private String email;
    private String description;
    private int point;
    private String avatar;
}
