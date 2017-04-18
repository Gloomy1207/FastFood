package com.gloomy.fastfood.observer;

import com.gloomy.fastfood.api.responses.ProfileResponse;
import com.gloomy.fastfood.observer.listener.OnReceiveObserverListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 17-Apr-17.
 */
public final class ProfileObserver {
    private static List<OnReceiveObserverListener> mOnReceiveObserverListeners = new ArrayList<>();

    public static void register(OnReceiveObserverListener onReceiveObserverListener) {
        mOnReceiveObserverListeners.add(onReceiveObserverListener);
    }

    public static void unregister(OnReceiveObserverListener onReceiveObserverListener) {
        mOnReceiveObserverListeners.remove(onReceiveObserverListener);
    }

    public static void post(ProfileResponse profileResponse) {
        for (OnReceiveObserverListener listener : mOnReceiveObserverListeners) {
            listener.onReceiveProfileData(profileResponse);
        }
    }
}
