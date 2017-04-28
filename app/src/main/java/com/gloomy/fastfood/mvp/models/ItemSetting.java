package com.gloomy.fastfood.mvp.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
@Getter
@Builder
public class ItemSetting {
    /**
     * SettingItemType definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SettingItemType.LOGOUT})
    public @interface SettingItemType {
        int LOGOUT = 1;
        int UPDATE_PROFILE = 2;
    }

    private String title;
    private int type;
}
