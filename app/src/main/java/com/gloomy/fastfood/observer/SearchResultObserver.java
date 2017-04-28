package com.gloomy.fastfood.observer;

import com.gloomy.fastfood.api.responses.SearchResultResponse;
import com.gloomy.fastfood.observer.listener.OnSearchResultObserverListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
public final class SearchResultObserver {
    private static Set<OnSearchResultObserverListener> mOnSearchResultObserverListeners = new HashSet<>();

    public static void register(OnSearchResultObserverListener onSearchResultObserverListener) {
        mOnSearchResultObserverListeners.add(onSearchResultObserverListener);
    }

    public static void unregister(OnSearchResultObserverListener onSearchResultObserverListener) {
        mOnSearchResultObserverListeners.remove(onSearchResultObserverListener);
    }

    public static void post(SearchResultResponse response) {
        for (OnSearchResultObserverListener listener : mOnSearchResultObserverListeners) {
            listener.onSearchResponse(response);
        }
    }
}
