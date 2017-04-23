package com.gloomy.fastfood.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
@Getter
@Parcel
public class User {
    private Long userId;
    private String username;
    private String fullname;
    private String email;
    private String description;
    private int point;
    private String avatar;

    @ParcelConstructor
    public User(Long userId, String username, String fullname, String email, String description, int point, String avatar) {
        this.userId = userId;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.description = description;
        this.point = point;
        this.avatar = avatar;
    }
}
