package com.gloomy.fastfood.mvp.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lombok.Getter;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 12/04/2017.
 */
public abstract class Place {

    /**
     * PlaceType definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PlaceType.CITY, PlaceType.PROVINCE})
    public @interface PlaceType {
        int CITY = 1;
        int PROVINCE = 2;
    }

    public abstract int getType();

    /**
     * PlaceCity class
     */
    @Builder
    @Getter
    public static class PlaceCity extends Place {
        private City city;

        @Override
        public int getType() {
            return PlaceType.CITY;
        }
    }

    /**
     * PlaceProvince class
     */
    @Builder
    @Getter
    public static class PlaceProvince extends Place {
        private Province province;
        private boolean isFirstItem;

        @Override
        public int getType() {
            return PlaceType.PROVINCE;
        }
    }
}
