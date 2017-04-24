package com.gloomy.fastfood.mvp.presenters.main.profile.favorite;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.ProfileResponse;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.main.profile.favorite.IProfileFavoriteView;
import com.gloomy.fastfood.mvp.views.main.profile.favorite.ProfileFavoriteAdapter;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 17-Apr-17.
 */
@EBean
public class ProfileFavoritePresenter extends BasePresenter implements ProfileFavoriteAdapter.OnItemFavoriteListener {

    @Setter
    @Accessors(prefix = "m")
    private IProfileFavoriteView mView;

    private List<Store> mStores = new ArrayList<>();
    private int mCurrentPage;
    private boolean mIsLastPage;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProfileFavoriteAdapter adapter = new ProfileFavoriteAdapter(getContext(), mStores, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    private void loadMoreData() {
        if (mIsLastPage) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().loadMoreUserFavorite(mCurrentPage, null, new Callback<ProfileResponse.ProfileFavorites>() {
            @Override
            public void onResponse(Call<ProfileResponse.ProfileFavorites> call, Response<ProfileResponse.ProfileFavorites> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                ProfileResponse.ProfileFavorites profileFavorites = response.body();
                mStores.addAll(profileFavorites.getStores());
                mCurrentPage = profileFavorites.getCurrentPage();
                mIsLastPage = profileFavorites.isLast();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<ProfileResponse.ProfileFavorites> call, Throwable t) {
                // No-op
            }
        });
    }

    @Override
    public void onItemFavoriteClick(int position) {
        mView.onItemStoreClick(mStores.get(position));
    }

    public void onReceiveProfileData(ProfileResponse profileResponse) {
        ProfileResponse.ProfileFavorites profileFavorites = profileResponse.getFavorites();
        if (profileFavorites != null) {
            mCurrentPage = profileFavorites.getCurrentPage();
            mIsLastPage = profileFavorites.isLast();
            mStores.addAll(profileFavorites.getStores());
            mView.onLoadDataComplete();
        }
    }
}
