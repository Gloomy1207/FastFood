package com.gloomy.fastfood.mvp.views.main.search.people;

import com.gloomy.fastfood.models.User;
import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
public interface ISearchPeopleView extends IBaseView {
    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onRefreshComplete();

    void notifyDataSetChanged();

    void onItemPeopleClick(User user);
}