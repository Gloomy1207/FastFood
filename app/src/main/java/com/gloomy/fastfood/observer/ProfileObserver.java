package com.gloomy.fastfood.observer;

import com.gloomy.fastfood.api.responses.ProfileResponse;
import com.gloomy.fastfood.observer.listener.OnReceiveProfileObserverListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 17-Apr-17.
 */
public final class ProfileObserver {
    private static List<OnReceiveProfileObserverListener> mOnReceiveProfileObserverListeners = new ArrayList<>();

    public static void register(OnReceiveProfileObserverListener onReceiveProfileObserverListener) {
        mOnReceiveProfileObserverListeners.add(onReceiveProfileObserverListener);
    }

    public static void unregister(OnReceiveProfileObserverListener onReceiveProfileObserverListener) {
        mOnReceiveProfileObserverListeners.remove(onReceiveProfileObserverListener);
    }

    public static void post(ProfileResponse profileResponse) {
        for (OnReceiveProfileObserverListener listener : mOnReceiveProfileObserverListeners) {
            listener.onReceiveProfileData(profileResponse);
        }
    }
}
