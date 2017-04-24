package com.gloomy.fastfood.utils;

import android.content.Context;
import android.location.Location;

import com.gloomy.fastfood.mvp.models.LatLng;


/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 13/04/2017.
 */
public final class LocationUtil {

    private LocationUtil() {
        // No-op
    }

    public static LatLng getCurrentLatLng(Context context) {
        GPSTracker gpsTracker = new GPSTracker(context);
        Location location = gpsTracker.getLocation();
        if (location != null) {
            return LatLng.builder().lat(location.getLatitude()).lng(location.getLongitude()).build();
        } else {
            return null;
        }
    }
}
