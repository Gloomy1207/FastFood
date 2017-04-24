package com.gloomy.fastfood.mvp.views.main.search.topic;

import com.gloomy.fastfood.models.Topic;
import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
public interface ISearchTopicView extends IBaseView {

    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onRefreshComplete();

    void onItemTopicClick(Topic topic);

    void notifyDataSetChanged();
}
