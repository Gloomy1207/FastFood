package com.gloomy.fastfood.observer;

import com.gloomy.fastfood.mvp.models.Comment;
import com.gloomy.fastfood.observer.listener.OnStoreDetailSendCommentObserverListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 25/04/2017.
 */
public final class StoreDetailObserver {
    private static List<OnStoreDetailSendCommentObserverListener> mOnReceiveProfileObserverListeners = new ArrayList<>(1);

    public static void register(OnStoreDetailSendCommentObserverListener onStoreDetailSendCommentObserverListener) {
        mOnReceiveProfileObserverListeners.add(onStoreDetailSendCommentObserverListener);
    }

    public static void unregister(OnStoreDetailSendCommentObserverListener onStoreDetailSendCommentObserverListener) {
        mOnReceiveProfileObserverListeners.remove(onStoreDetailSendCommentObserverListener);
    }

    public static void post(Comment comment) {
        for (OnStoreDetailSendCommentObserverListener listener : mOnReceiveProfileObserverListeners) {
            listener.onSendCommentData(comment);
        }
    }
}
