package com.gloomy.fastfood.ui.presenters.main.home.food;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.HomeFoodResponse;
import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.home.food.HomeFoodAdapter;
import com.gloomy.fastfood.ui.views.main.home.food.IHomeFoodView;
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
 * Created by HungTQB on 11-Apr-17.
 */
@EBean
public class HomeFoodPresenter extends BasePresenter implements Callback<HomeFoodResponse>, HomeFoodAdapter.OnHomeFoodListener {
    public static final int RECYCLER_NUM_COLUMN = 2;

    @Setter
    @Accessors(prefix = "m")
    private IHomeFoodView mView;

    @DimensionPixelOffsetRes(R.dimen.space_item_decoration_recycler_view)
    int mDecorationSpace;


    private boolean mIsLastPage;
    private int mCurrentPage;
    private List<Food> mFoods;
    private HomeFoodResponse mHomeFoodResponse;

    public void getHomeFoodData() {
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getHomeFoodData(null, null, this);
    }

    @Override
    public void onResponse(Call<HomeFoodResponse> call, Response<HomeFoodResponse> response) {
        mView.onDismissProgressDialog();
        if (response == null || response.body() == null) {
            return;
        }
        mHomeFoodResponse = response.body();
        mView.onLoadDataComplete();
    }

    @Override
    public void onFailure(Call<HomeFoodResponse> call, Throwable t) {
        mView.onDismissProgressDialog();
        mView.onLoadDataFailure();
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        // TODO: 11-Apr-17 Replace dummy with data get from server
        /*mFoods = mHomeFoodResponse.getFoods();*/
        mFoods = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mFoods.add(Food.builder()
                    .description("This is description")
                    .foodName("This is food name")
                    .mainImage("http://eva-img.24hstatic.com/upload/4-2015/images/2015-10-09/1444361180-white-rice.jpg")
                    .recipe("- Rice\n- Water \n - Shit\n - Fuck")
                    .rating(5)
                    .numberOfRating(i)
                    .numberOfRatingText(i + " reviewers")
                    .build());
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(RECYCLER_NUM_COLUMN, StaggeredGridLayoutManager.VERTICAL);
        HomeFoodAdapter adapter = new HomeFoodAdapter(mContext, mFoods, this);
        recyclerView.addItemDecoration(new SpacesItemDecoration(mDecorationSpace));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFoodClick(int position) {
        mView.onItemFoodClick(mFoods.get(position));
    }
}
