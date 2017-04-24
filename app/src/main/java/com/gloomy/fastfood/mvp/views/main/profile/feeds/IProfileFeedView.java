package com.gloomy.fastfood.mvp.views.main.profile.feeds;

import com.gloomy.fastfood.models.Topic;
import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 18-Apr-17.
 */
public interface IProfileFeedView extends IBaseView {
    void onItemTopicClick(Topic topic);

    void onLoadMoreComplete();

    void onLoadDataComplete();
}
