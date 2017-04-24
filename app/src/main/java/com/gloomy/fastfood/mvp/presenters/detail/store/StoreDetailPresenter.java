package com.gloomy.fastfood.mvp.presenters.detail.store;

import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.detail.store.IStoreDetailView;
import com.gloomy.fastfood.widgets.CustomFloatingButton;
import com.gloomy.fastfood.widgets.CustomTextInputLayout;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
@EBean
public class StoreDetailPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    private IStoreDetailView mView;

    @Setter
    @Accessors(prefix = "m")
    private Store mStore;


    public void initRecyclerView(RecyclerView recyclerView) {

    }

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setTitle(mStore.getStoreName());
        headerBar.setOnHeaderBarListener(new HeaderBar.OnHeaderBarListener() {
            @Override
            public void onLeftButtonClick() {
                mView.onBackClick();
            }

            @Override
            public void onRightButtonClick() {
                // No-op
            }
        });
    }

    public void getTopicComment(boolean isLoadMore) {

    }

    public void initButtonLike(CustomFloatingButton btnLike) {

    }

    public void initCommentLayout(CustomTextInputLayout commentLayout) {

    }
}
