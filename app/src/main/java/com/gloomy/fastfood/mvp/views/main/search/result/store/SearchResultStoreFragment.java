package com.gloomy.fastfood.mvp.views.main.search.result.store;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.responses.SearchResultResponse;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.main.search.result.store.SearchResultStorePresenter;
import com.gloomy.fastfood.mvp.views.detail.store.StoreDetailActivity_;
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
@EFragment(R.layout.fragment_search_result_store)
public class SearchResultStoreFragment extends BaseFragment implements OnSearchResultObserverListener, ISearchResultStoreView {

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

    @Bean
    SearchResultStorePresenter mPresenter;

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
    public void onStoreClick(Store store) {
        StoreDetailActivity_.intent(getActivity()).mStoreParcel(Parcels.wrap(store)).start();
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
        mTvEmptyMessage.setText(R.string.store_is_nothing);
    }
}
