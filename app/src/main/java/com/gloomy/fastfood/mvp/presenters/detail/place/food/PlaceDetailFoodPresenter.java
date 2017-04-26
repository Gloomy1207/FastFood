package com.gloomy.fastfood.mvp.presenters.detail.place.food;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.HomeFoodResponse;
import com.gloomy.fastfood.mvp.models.Food;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.detail.place.food.IPlaceDetailFoodView;
import com.gloomy.fastfood.mvp.views.main.home.food.HomeFoodAdapter;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.widgets.SpacesItemDecoration;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DimensionPixelOffsetRes;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
@EBean
public class PlaceDetailFoodPresenter extends BasePresenter implements HomeFoodAdapter.OnHomeFoodListener {
    private static final int RECYCLER_NUM_COLUMN = 2;

    @Setter
    @Accessors(prefix = "m")
    IPlaceDetailFoodView mView;

    @DimensionPixelOffsetRes(R.dimen.space_item_decoration_recycler_view)
    int mDecorationSpace;

    @Setter
    @Accessors(prefix = "m")
    private int mPlaceType;

    @Setter
    @Accessors(prefix = "m")
    private int mPlaceId;

    private List<Food> mFoods = new ArrayList<>();
    private boolean mIsLastPage;
    private int mCurrentPage;

    public void initRecyclerView(RecyclerView recyclerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(RECYCLER_NUM_COLUMN, StaggeredGridLayoutManager.VERTICAL);
        HomeFoodAdapter adapter = new HomeFoodAdapter(mContext, mFoods, this);
        recyclerView.addItemDecoration(new SpacesItemDecoration(mDecorationSpace));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    public void getPlaceFoodData() {
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            mView.onNoInternetConnection();
            return;
        }
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getPlaceFoodData(mPlaceId, mPlaceType, null, null, new Callback<HomeFoodResponse>() {
            @Override
            public void onResponse(Call<HomeFoodResponse> call, Response<HomeFoodResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                HomeFoodResponse foodResponse = response.body();
                mFoods.addAll(foodResponse.getFoods());
                mCurrentPage = foodResponse.getCurrentPage();
                mIsLastPage = foodResponse.isLast();
                if (!mFoods.isEmpty()) {
                    mView.onLoadDataComplete();
                } else {
                    mView.onEmptyData(getString(R.string.empty_food_place_detail));
                }
            }

            @Override
            public void onFailure(Call<HomeFoodResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }

    private void loadMoreData() {
        if (mIsLastPage) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().getPlaceFoodData(mPlaceId, mPlaceType, mCurrentPage, null, new Callback<HomeFoodResponse>() {
            @Override
            public void onResponse(Call<HomeFoodResponse> call, Response<HomeFoodResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                HomeFoodResponse foodResponse = response.body();
                mFoods.addAll(foodResponse.getFoods());
                mIsLastPage = foodResponse.isLast();
                mCurrentPage = foodResponse.getCurrentPage();
                mView.onLoadDataComplete();
            }

            @Override
            public void onFailure(Call<HomeFoodResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onFoodClick(int position) {
        mView.onItemFoodClick(mFoods.get(position));
    }
}
