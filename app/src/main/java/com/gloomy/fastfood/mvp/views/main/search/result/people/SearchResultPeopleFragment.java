package com.gloomy.fastfood.mvp.views.main.search.result.people;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.responses.SearchResultResponse;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.presenters.main.search.result.people.SearchResultPeoplePresenter;
import com.gloomy.fastfood.mvp.views.profile.ProfileActivity_;
import com.gloomy.fastfood.observer.SearchResultObserver;
import com.gloomy.fastfood.observer.listener.OnSearchResultObserverListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
@EFragment(R.layout.fragment_search_result_people)
public class SearchResultPeopleFragment extends BaseFragment implements ISearchResultPeopleView, OnSearchResultObserverListener {

    @Bean
    SearchResultPeoplePresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

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
    public void onUserClick(User user) {
        ProfileActivity_.intent(getActivity()).mUser(user).start();
    }

    @Override
    public void onUpdateData() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mTvEmptyMessage.setVisibility(View.GONE);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onEmptyData() {
        mRecyclerView.setVisibility(View.GONE);
        mTvEmptyMessage.setVisibility(View.VISIBLE);
        mTvEmptyMessage.setText(R.string.people_is_nothing);
    }
}
