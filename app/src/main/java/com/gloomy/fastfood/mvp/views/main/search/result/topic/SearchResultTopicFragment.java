package com.gloomy.fastfood.mvp.views.main.search.result.topic;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.responses.SearchResultResponse;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.presenters.main.search.result.topic.SearchResultTopicPresenter;
import com.gloomy.fastfood.mvp.views.detail.topic.TopicDetailActivity_;
import com.gloomy.fastfood.observer.SearchResultObserver;
import com.gloomy.fastfood.observer.listener.OnSearchResultObserverListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
@EFragment(R.layout.fragment_search_result_post)
public class SearchResultTopicFragment extends BaseFragment implements OnSearchResultObserverListener, ISearchResultTopicView {

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

    @Bean
    SearchResultTopicPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        SearchResultObserver.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        SearchResultObserver.unregister(this);
    }

    @Override
    public void onSearchResponse(SearchResultResponse response) {
        mPresenter.onReceiveSearchResult(response);
    }

    @Override
    public void onTopicClick(Topic topic) {
        TopicDetailActivity_.intent(getActivity()).mTopicParcel(Parcels.wrap(topic)).start();
    }

    @Override
    public void onUpdateData() {
        mTvEmptyMessage.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onEmptyData() {
        mTvEmptyMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mTvEmptyMessage.setText(R.string.topic_is_nothing);
    }
}
