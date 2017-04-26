package com.gloomy.fastfood.mvp.views.detail.place;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.mvp.views.detail.place.food.PlaceDetailFoodFragment_;
import com.gloomy.fastfood.mvp.views.detail.place.province.PlaceDetailProvinceFragment_;
import com.gloomy.fastfood.mvp.views.detail.place.store.PlaceDetailStoreFragment_;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
public class PlaceDetailPagerAdapter extends FragmentStatePagerAdapter {
    public static final int CITY_PAGER_COUNT = 3;
    public static final int PROVINCE_PAGER_COUNT = 2;
    private final int mPlaceType;
    private final int mPlaceId;

    public PlaceDetailPagerAdapter(FragmentManager fm, int placeType, int placeId) {
        super(fm);
        mPlaceType = placeType;
        mPlaceId = placeId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PlaceDetailStoreFragment_.builder().mPlaceId(mPlaceId).mPlaceType(mPlaceType).build();
            case 1:
                return PlaceDetailFoodFragment_.builder().mPlaceId(mPlaceId).mPlaceType(mPlaceType).build();
            case 2:
                return PlaceDetailProvinceFragment_.builder().mCityId(mPlaceId).build();
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mPlaceType == PlaceDetailType.CITY) {
            return CITY_PAGER_COUNT;
        } else {
            return PROVINCE_PAGER_COUNT;
        }
    }
}
