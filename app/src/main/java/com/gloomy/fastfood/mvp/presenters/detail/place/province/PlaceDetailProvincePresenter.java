package com.gloomy.fastfood.mvp.presenters.detail.place.province;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.mvp.models.Province;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.detail.place.province.IPlaceDetailProvinceView;
import com.gloomy.fastfood.mvp.views.detail.place.province.PlaceDetailProvinceAdapter;
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
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
@EBean
public class PlaceDetailProvincePresenter extends BasePresenter implements PlaceDetailProvinceAdapter.OnPlaceDetailProvinceListener {

    @Setter
    @Accessors(prefix = "m")
    private IPlaceDetailProvinceView mView;

    @Setter
    @Accessors(prefix = "m")
    private int mCityId;

    private List<Province> mProvinces = new ArrayList<>();

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new PlaceDetailProvinceAdapter(getContext(), mProvinces, this));
    }

    public void getPlaceProvinceData() {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getCityProvinceData(mCityId, new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                mProvinces.addAll(response.body());
                if (!mProvinces.isEmpty()) {
                    mView.onLoadDataComplete();
                } else {
                    mView.onEmptyData(getString(R.string.empty_province_place_detail));
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onProvinceClick(int position) {
        mView.onProvinceClick(mProvinces.get(position));
    }
}
