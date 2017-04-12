package com.gloomy.fastfood.ui.presenters.main.home.place;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.HomePlaceResponse;
import com.gloomy.fastfood.models.City;
import com.gloomy.fastfood.models.Place;
import com.gloomy.fastfood.models.Province;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.home.place.HomePlaceAdapter;
import com.gloomy.fastfood.ui.views.main.home.place.IHomePlaceView;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 12/04/2017.
 */
@EBean
public class HomePlacePresenter extends BasePresenter implements Callback<HomePlaceResponse>, HomePlaceAdapter.OnHomePlaceListener {

    @Setter
    @Accessors(prefix = "m")
    private IHomePlaceView mView;

    private int mCurrentapge;
    private boolean mIsLastPage;
    private HomePlaceResponse mHomePlaceResponse;
    private List<Place> mPlaces;

    public void getHomePlaceData() {
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getHomePlaceData(null, null, this);
    }

    @Override
    public void onResponse(Call<HomePlaceResponse> call, Response<HomePlaceResponse> response) {
        mView.onDismissProgressDialog();
        if (response == null || response.body() == null) {
            return;
        }
        mHomePlaceResponse = response.body();
        mView.onLoadDataComplete();
    }

    @Override
    public void onFailure(Call<HomePlaceResponse> call, Throwable t) {
        mView.onDismissProgressDialog();
        mView.onLoadDataFailure();
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        // TODO: 12/04/2017 Remove dummy data and replace with server data
        mPlaces = new ArrayList<>();
        List<City> cities = new ArrayList<>();
        List<Province> provinces = Arrays.asList(Province.builder().numberPlaceText("123 addresses").provinceName("Province name").build(),
                Province.builder().numberPlaceText("123 addresses").provinceName("Province name").build(),
                Province.builder().numberPlaceText("123 addresses").provinceName("Province name").build(),
                Province.builder().numberPlaceText("123 addresses").provinceName("Province name").build(),
                Province.builder().numberPlaceText("123 addresses").provinceName("Province name").build());
        for (int i = 0; i < 20; i++) {
            cities.add(City.builder()
                    .cityName("City " + i)
                    .provinces(provinces)
                    .build());
        }
        mPlaces.addAll(parsePlaceData(cities));
        HomePlaceAdapter adapter = new HomePlaceAdapter(mContext, mPlaces, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

    private List<Place> parsePlaceData(List<City> cities) {
        List<Place> places = new ArrayList<>();
        for (City city : cities) {
            places.add(Place.PlaceCity.builder()
                    .city(city)
                    .build());
            for (Province province : city.getProvinces()) {
                places.add(Place.PlaceProvince.builder()
                        .province(province)
                        .build());
            }
        }
        return places;
    }

    @Override
    public void onItemCityClick(int position) {
        mView.onItemHomeCityClick(((Place.PlaceCity) mPlaces.get(position)).getCity());
    }

    @Override
    public void onItemProvinceClick(int position) {
        mView.onItemProvinceClick(((Place.PlaceProvince) mPlaces.get(position)).getProvince());
    }
}
