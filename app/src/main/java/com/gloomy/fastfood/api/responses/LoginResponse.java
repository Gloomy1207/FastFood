package com.gloomy.fastfood.api.responses;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
@Getter
public class LoginResponse {
    private String accessToken;
    private boolean status;
    private String message;
}
