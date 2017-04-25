package com.gloomy.fastfood.mvp.views.main.rating.people;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.mvp.models.User;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 17/04/2017.
 */
public interface IRatingPeopleView extends IBaseView {
    void onLoadDataComplete();

    void onLoadMoreComplete();

    void onLoadDataFailure();

    void onItemPeopleClick(User user);
}
