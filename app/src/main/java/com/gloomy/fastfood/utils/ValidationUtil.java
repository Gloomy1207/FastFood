package com.gloomy.fastfood.utils;

import android.util.Patterns;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 20/04/2017.
 */
public final class ValidationUtil {

    private ValidationUtil() {
        // No-op
    }

    public static boolean isEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
