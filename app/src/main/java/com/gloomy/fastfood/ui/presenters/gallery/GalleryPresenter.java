package com.gloomy.fastfood.ui.presenters.gallery;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.FoodImageResponse;
import com.gloomy.fastfood.models.FoodImage;
import com.gloomy.fastfood.models.GalleryImage;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.presenters.EndlessScrollListener;
import com.gloomy.fastfood.ui.views.gallery.GalleryRecyclerAdapter;
import com.gloomy.fastfood.ui.views.gallery.IGalleryView;

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
 * Created by HungTQB on 21-Apr-17.
 */
@EBean
public class GalleryPresenter extends BasePresenter implements GalleryRecyclerAdapter.OnGalleryDialogListener {
    private static final int LOAD_MORE_THRESHOLD = 15;
    private static final int NUM_COLUMN = 3;

    private List<GalleryImage> mGalleryImages = new ArrayList<>();
    private int mCurrentPage;
    private boolean mIsLastPage;

    @Setter
    @Accessors(prefix = "m")
    private IGalleryView mView;

    @Setter
    @Accessors(prefix = "m")
    private int mFoodId;

    public void initRecyclerView(RecyclerView mRecyclerView) {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(NUM_COLUMN, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(new GalleryRecyclerAdapter(getContext(), mGalleryImages, this));
        mRecyclerView.addOnScrollListener(new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    public void getGalleryData() {
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getFoodImages(null, null, mFoodId, new Callback<FoodImageResponse>() {
            @Override
            public void onResponse(Call<FoodImageResponse> call, Response<FoodImageResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                FoodImageResponse imageResponse = response.body();
                parseDataToImage(imageResponse);
                mCurrentPage = imageResponse.getCurrentPage();
                mIsLastPage = imageResponse.isLast();
                mView.onLoadDataComplete();
            }

            @Override
            public void onFailure(Call<FoodImageResponse> call, Throwable t) {
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
        ApiRequest.getInstance().getFoodImages(mCurrentPage, null, mFoodId, new Callback<FoodImageResponse>() {
            @Override
            public void onResponse(Call<FoodImageResponse> call, Response<FoodImageResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                FoodImageResponse imageResponse = response.body();
                parseDataToImage(imageResponse);
                mCurrentPage = imageResponse.getCurrentPage();
                mIsLastPage = imageResponse.isLast();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<FoodImageResponse> call, Throwable t) {
                // No-op
            }
        });
    }

    private void parseDataToImage(FoodImageResponse imageResponse) {
        for (FoodImage foodImage : imageResponse.getImages()) {
            mGalleryImages.add(GalleryImage.builder().imagePath(foodImage.getImagePath()).build());
        }
    }

    @Override
    public void onItemGalleryClick(int position) {
        mView.onItemGalleryClick(mGalleryImages.get(position));
    }
}
