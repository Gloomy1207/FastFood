package com.gloomy.fastfood.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by ChauHQ on 16/12/2016
 */
public final class SharePreferencesUtils {

    private static final String SHARE_PREFERENCE_DATA = "share_preference_data";

    private SharePreferencesUtils() {
    }

    /**
     * Save string value to share preference
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Get string from share preference
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    /**
     * Save boolean value to share preference
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get boolean value from share preference
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * Get boolean value from share preference
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * Save integer value to share preference
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Get integer value from share preference
     *
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Save long value to share preference
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveLong(Context context, String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Get long value from share preference
     *
     * @param context
     * @param key
     * @return
     */
    public static long getLong(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public static void remove(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SHARE_PREFERENCE_DATA, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
    }
}
