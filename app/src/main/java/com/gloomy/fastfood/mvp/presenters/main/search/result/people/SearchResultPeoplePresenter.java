package com.gloomy.fastfood.mvp.presenters.main.search.result.people;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.api.responses.SearchResultResponse;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.search.people.SearchPeopleAdapter;
import com.gloomy.fastfood.mvp.views.main.search.result.people.ISearchResultPeopleView;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
@EBean
public class SearchResultPeoplePresenter extends BasePresenter implements SearchPeopleAdapter.OnSearchPeopleListener {

    @Setter
    @Accessors(prefix = "m")
    private ISearchResultPeopleView mView;

    private List<User> mUsers = new ArrayList<>();

    public void onReceiveSearchResult(SearchResultResponse response) {
        mUsers.clear();
        if (response.getPeoples() != null && !response.getPeoples().isEmpty()) {
            mUsers.addAll(response.getPeoples());
            mView.onUpdateData();
        } else {
            mView.onEmptyData();
        }
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SearchPeopleAdapter(getContext(), mUsers, this));
    }

    @Override
    public void onUserClick(int position) {
        mView.onUserClick(mUsers.get(position));
    }
}
