package com.gloomy.fastfood.ui.presenters.detail.topic;

import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.detail.topic.ITopicDetailView;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 21/04/2017.
 */
@EBean
public class TopicDetailPresenter extends BasePresenter {

    @Setter
    @Accessors(prefix = "m")
    ITopicDetailView mView;

    public void initRecyclerView(RecyclerView recyclerView) {

    }

    public void initHeaderBar(HeaderBar headerBar, String topicName) {
        headerBar.setTitle(topicName);
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
}
