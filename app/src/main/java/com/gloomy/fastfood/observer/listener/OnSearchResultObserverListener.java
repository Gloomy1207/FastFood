package com.gloomy.fastfood.observer.listener;

import com.gloomy.fastfood.api.responses.SearchResultResponse;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
public interface OnSearchResultObserverListener {
    void onSearchResponse(SearchResultResponse response);
}
