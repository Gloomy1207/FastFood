package com.gloomy.fastfood.mvp.presenters.detail.place;

import android.app.FragmentManager;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.City;
import com.gloomy.fastfood.mvp.models.Province;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.detail.place.IPlaceDetailView;
import com.gloomy.fastfood.mvp.views.detail.place.PlaceDetailPagerAdapter;
import com.gloomy.fastfood.mvp.views.detail.place.PlaceDetailType;
import com.gloomy.fastfood.utils.TabLayoutUtil;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;
import org.parceler.Parcels;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 26-Apr-17.
 */
@EBean
public class PlaceDetailPresenter extends BasePresenter {
    private static final int[] TAB_ICONS = {
            R.drawable.ic_store_on,
            R.drawable.ic_food_on,
            R.drawable.ic_location_24dp
    };

    @Setter
    @Accessors(prefix = "m")
    IPlaceDetailView mView;

    private City mCity;
    private Province mProvince;
    private int mPlaceType;

    public void setPlace(Parcelable placeParcelable, int placeType) {
        mPlaceType = placeType;
        if (placeType == PlaceDetailType.CITY) {
            mCity = Parcels.unwrap(placeParcelable);
        } else {
            mProvince = Parcels.unwrap(placeParcelable);
        }
    }

    public void initHeaderBar(HeaderBar headerBar) {
        if (mPlaceType == PlaceDetailType.CITY) {
            headerBar.setTitle(mCity.getCityName());
        } else {
            headerBar.setTitle(mProvince.getProvinceName());
        }
        headerBar.setOnHeaderBarListener(new HeaderBar.OnHeaderBarListener() {
            @Override
            public void onLeftButtonClick() {
                mView.onBackPress();
            }

            @Override
            public void onRightButtonClick() {
                // No-op
            }
        });
    }

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager) {
        if (mPlaceType == PlaceDetailType.CITY) {
            viewPager.setAdapter(new PlaceDetailPagerAdapter(fragmentManager, mPlaceType, mCity.getCityId()));
            viewPager.setOffscreenPageLimit(PlaceDetailPagerAdapter.CITY_PAGER_COUNT);
        } else {
            viewPager.setAdapter(new PlaceDetailPagerAdapter(fragmentManager, mPlaceType, mProvince.getProvinceId()));
            viewPager.setOffscreenPageLimit(PlaceDetailPagerAdapter.PROVINCE_PAGER_COUNT);
        }
        tabLayout.setupWithViewPager(viewPager);
        TabLayoutUtil.setCustomViewsTabLayout(tabLayout, TAB_ICONS, getContext());
    }
}
