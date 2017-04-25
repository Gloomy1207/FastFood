package com.gloomy.fastfood.mvp.views.main.topic.content;

import android.support.annotation.IntDef;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.presenters.main.topic.content.TopicContentPresenter;
import com.gloomy.fastfood.mvp.views.detail.topic.TopicDetailActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 31-Mar-17.
 */
@EFragment(R.layout.fragment_topic_content)
public class TopicContentFragment extends BaseFragment implements ITopicContentView {

    /**
     * TopicType denifition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TopicType.HOT, TopicType.TRENDING, TopicType.FRESH, TopicType.RANDOM})
    public @interface TopicType {
        int HOT = 1;
        int TRENDING = 2;
        int FRESH = 3;
        int RANDOM = 4;
    }

    @Bean
    TopicContentPresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @ViewById(R.id.disableView)
    View mDisableView;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

    @FragmentArg
    int mTopicType;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.initSwipeRefresh(mSwipeRefreshLayout, mDisableView);
        mPresenter.setTopicType(mTopicType);
        mPresenter.getTopicContentData(false);
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onDismissProgressDialog() {
        dismissProgressDialog();
    }

    @Override
    public void onNoInternetConnection() {
        mDisableView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        showNoInternetConnectionMessage();
    }

    @Override
    public void onLoadDataComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        mDisableView.setVisibility(View.GONE);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadDataFailure() {
        mSwipeRefreshLayout.setRefreshing(false);
        mDisableView.setVisibility(View.GONE);
        showLoadDataFailure();
    }

    @Override
    public void onDataEmpty() {
        mTvEmptyMessage.setText(getString(R.string.topic_empty_message));
        mTvEmptyMessage.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mDisableView.setVisibility(View.GONE);
    }

    @Override
    public void onItemTopicClick(Topic topic) {
        TopicDetailActivity_.intent(getActivity()).mTopicParcel(Parcels.wrap(topic)).start();
        getActivity().overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }
}
