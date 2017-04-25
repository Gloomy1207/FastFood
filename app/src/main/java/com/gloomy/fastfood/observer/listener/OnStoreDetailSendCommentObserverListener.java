package com.gloomy.fastfood.observer.listener;

import com.gloomy.fastfood.mvp.models.Comment;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 17-Apr-17.
 */
public interface OnStoreDetailSendCommentObserverListener {

    void onSendCommentData(Comment comment);
}
