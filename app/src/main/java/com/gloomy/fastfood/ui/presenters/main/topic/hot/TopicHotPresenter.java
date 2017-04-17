package com.gloomy.fastfood.ui.presenters.main.topic.hot;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.TopicResponse;
import com.gloomy.fastfood.models.Topic;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.presenters.EndlessScrollListener;
import com.gloomy.fastfood.ui.views.main.topic.TopicAdapter;
import com.gloomy.fastfood.ui.views.main.topic.hot.ITopicHotView;
import com.gloomy.fastfood.utils.NetworkUtil;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 17/04/2017.
 */
@EBean
public class TopicHotPresenter extends BasePresenter implements TopicAdapter.OnTopicListener, SwipeRefreshLayout.OnRefreshListener {

    @Setter
    @Accessors(prefix = "m")
    private ITopicHotView mView;

    private List<Topic> mTopics = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private int mCurrentPage;
    private boolean mIsLast;
    private TopicResponse mTopicResponse;

    private View mDisableView;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TopicAdapter adapter = new TopicAdapter(getContext(), mTopics, this);
        recyclerView.setAdapter(adapter);
        mEndlessScrollListener = new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        };
        recyclerView.addOnScrollListener(mEndlessScrollListener);
    }

    private void loadMoreData() {
        if (mIsLast) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().getHotTopicData(mCurrentPage, null, new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                TopicResponse topicResponse = response.body();
                mTopics.addAll(topicResponse.getTopics());
                mCurrentPage = topicResponse.getCurrentPage();
                mIsLast = topicResponse.isLast();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onItemTopicClick(int position) {
        mView.onItemTopicClick(mTopics.get(position));
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        mDisableView = disableView;
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        mDisableView.setVisibility(View.VISIBLE);
        getTopicHotData(true);
    }

    public void getTopicHotData(boolean isRefresh) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            return;
        }
        if (!isRefresh) {
            mView.onShowProgressDialog();
        }
        ApiRequest.getInstance().getHotTopicData(null, null, new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                mTopicResponse = response.body();
                mTopics.clear();
                mTopics.addAll(mTopicResponse.getTopics());
                mCurrentPage = mTopicResponse.getCurrentPage();
                mIsLast = mTopicResponse.isLast();
                if (!mTopics.isEmpty()) {
                    mView.onLoadDataComplete();
                } else {
                    mView.onDataEmpty();
                }
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }
}
