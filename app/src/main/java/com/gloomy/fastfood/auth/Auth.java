package com.gloomy.fastfood.auth;


import com.gloomy.fastfood.mvp.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
@Getter
@Builder
@Setter
public class Auth {
    private String apiToken;
    private User user;
}
