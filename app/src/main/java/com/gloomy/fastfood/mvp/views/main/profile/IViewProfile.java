package com.gloomy.fastfood.mvp.views.main.profile;

import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
public interface IViewProfile extends IBaseView {
    void setUsername(String username);

    void setFullName(String fullName);

    void setAvatar(String avatar, int width);

    void setAvatar(int resId);

    void setImageBackground(String avatar, int width);

    void setImageBackground(int resId);

    void onLoadDataFailure();

    void onSettingClick();

    void onBackPress();
}
