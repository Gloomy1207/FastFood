package com.gloomy.fastfood.api.requests;

import com.google.gson.annotations.SerializedName;

import lombok.experimental.Builder;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 28-Apr-17.
 */
@Builder
public class EditProfileRequest {
    private String username;
    @SerializedName("full_name")
    private String fullName;
    private String email;
    private String description;
    private String password;
}
