package com.gloomy.fastfood.api.responses;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 20/04/2017.
 */
@Getter
public class RegistrationResponse {
    private boolean status;
    private String message;
    private String accessToken;
}
