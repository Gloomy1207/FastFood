package com.gloomy.fastfood.mvp.views.update;

import android.net.Uri;

import com.gloomy.fastfood.mvp.IBaseView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
public interface IUpdateProfileView extends IBaseView {
    void onSetFullName(String fullName);

    void onSetDescription(String description);

    void onSetAvatar(String avatar, int avatarSize);

    void onSetAvatar(Uri avatar, int avatarSize);

    void onSetEmail(String email);

    void onUpdateFailure(String message);

    void onUpdateComplete(String message);

    void onBackClick();
}
