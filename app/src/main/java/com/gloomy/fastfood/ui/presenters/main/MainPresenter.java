package com.gloomy.fastfood.ui.presenters.main;

import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.IViewMain;
import com.gloomy.fastfood.ui.views.main.MainActivity;
import com.gloomy.fastfood.ui.views.main.MainViewPagerAdapter;

import org.androidannotations.annotations.EBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
@EBean
public class MainPresenter extends BasePresenter {

    /**
     * TabPosition definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef
    public @interface TabPosition {
        int HOME = 0;
        int SEARCH = 1;
        int RATING = 2;
        int POST = 3;
        int PROFILE = 4;
    }

    @Setter
    @Accessors(prefix = "m")
    IViewMain mView;

    public void initViewPager(ViewPager viewPager) {
        if (mContext instanceof MainActivity) {
            MainViewPagerAdapter adapter = new MainViewPagerAdapter(((MainActivity) mContext).getFragmentManager());
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(MainViewPagerAdapter.TOTAL_PAGE_NUM);
        }
    }

    public void onFooterBarItemSelect(int id) {
        switch (id) {
            case R.id.tabHome:
                mView.onFooterBarItemClick(TabPosition.HOME);
                break;
            case R.id.tabPost:
                mView.onFooterBarItemClick(TabPosition.POST);
                break;
            case R.id.tabProfile:
                mView.onFooterBarItemClick(TabPosition.PROFILE);
                break;
            case R.id.tabRating:
                mView.onFooterBarItemClick(TabPosition.RATING);
                break;
            case R.id.tabSearch:
                mView.onFooterBarItemClick(TabPosition.SEARCH);
                break;
        }
    }
}
