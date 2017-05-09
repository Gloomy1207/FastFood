package com.gloomy.fastfood.mvp.views.main.topic.content;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.Topic;

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

    void onFakeLike();

    void onLikeFailure();

    void onNotLogin();
}
