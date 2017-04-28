package com.gloomy.fastfood.mvp.presenters.main.search.result.store;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.responses.SearchResultResponse;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.home.store.HomeStoreAdapter;
import com.gloomy.fastfood.mvp.views.main.search.result.store.ISearchResultStoreView;
import com.gloomy.fastfood.widgets.SpacesItemDecoration;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DimensionPixelOffsetRes;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
@EBean
public class SearchResultStorePresenter extends BasePresenter implements HomeStoreAdapter.OnHomeStoreListener {
    private static final int NUM_COLUMN = 2;

    @Setter
    @Accessors(prefix = "m")
    private ISearchResultStoreView mView;

    @DimensionPixelOffsetRes(R.dimen.space_item_decoration_recycler_view)
    int mDecorationSpace;

    private List<Store> mStores = new ArrayList<>();

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(NUM_COLUMN, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new HomeStoreAdapter(getContext(), mStores, this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(mDecorationSpace));
    }

    @Override
    public void onItemHomeStoreClick(int position) {
        mView.onStoreClick(mStores.get(position));
    }

    public void onReceiveSearchResult(SearchResultResponse response) {
        mStores.clear();
        if (response.getStores() != null && !response.getStores().isEmpty()) {
            mStores.addAll(response.getStores());
            mView.onUpdateData();
        } else {
            mView.onEmptyData();
        }
    }
}
