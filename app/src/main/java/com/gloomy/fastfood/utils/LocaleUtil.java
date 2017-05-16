package com.gloomy.fastfood.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 16/05/2017.
 */
public class LocaleUtil {
    private static final String CURRENT_LOCALE_KEY = "current-locale-key";
    private static LocaleUtil sLocaleUtil;

    private LocaleUtil() {
    }

    public static LocaleUtil getInstance() {
        if (sLocaleUtil == null) {
            sLocaleUtil = new LocaleUtil();
        }
        return sLocaleUtil;
    }

    public Locale getCurrentLocale(Context context) {
        String locale = SharePreferencesUtils.getString(context, CURRENT_LOCALE_KEY);
        if (TextUtils.isEmpty(locale)) {
            return Locale.getDefault();
        }
        return new Locale(locale);
    }

    public void setCurrentLocale(Context context, Locale locale) {
        SharePreferencesUtils.saveString(context, CURRENT_LOCALE_KEY, locale.getLanguage());
    }
}
