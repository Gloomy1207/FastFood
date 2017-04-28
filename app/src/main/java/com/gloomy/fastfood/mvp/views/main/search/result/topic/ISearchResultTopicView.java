package com.gloomy.fastfood.mvp.views.main.search.result.topic;

import com.gloomy.fastfood.mvp.models.Topic;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
public interface ISearchResultTopicView {
    void onTopicClick(Topic topic);

    void onUpdateData();

    void onEmptyData();
}
