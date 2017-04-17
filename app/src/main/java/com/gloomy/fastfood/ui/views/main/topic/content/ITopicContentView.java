package com.gloomy.fastfood.ui.views.main.topic.content;

import com.gloomy.fastfood.models.Topic;
import com.gloomy.fastfood.ui.IBaseView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 17/04/2017.
 */
public interface ITopicContentView extends IBaseView {

    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onDataEmpty();

    void onItemTopicClick(Topic topic);
}