package com.gloomy.fastfood.mvp.views.main.search.result.people;

import com.gloomy.fastfood.mvp.models.User;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
public interface ISearchResultPeopleView {
    void onUserClick(User user);

    void onUpdateData();

    void onEmptyData();
}
