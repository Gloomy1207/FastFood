package com.gloomy.fastfood.mvp.presenters.main.rating;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.rating.IViewRating;
import com.gloomy.fastfood.mvp.views.main.rating.RatingViewPagerAdapter;
import com.gloomy.fastfood.utils.TabLayoutUtil;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringArrayRes;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class RatingPresenter extends BasePresenter {
    private static final int[] PAGE_ICONS = {
            R.drawable.ic_store_on,
            R.drawable.ic_people_on
    };

    @StringArrayRes(R.array.fragment_rating_view_pager_title)
    String[] mTitles;

    @Setter
    @Accessors(prefix = "m")
    private IViewRating mView;

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager) {
        RatingViewPagerAdapter adapter = new RatingViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        TabLayoutUtil.setCustomViewsTabLayout(tabLayout, mTitles, PAGE_ICONS, mContext);
        viewPager.setOffscreenPageLimit(RatingViewPagerAdapter.PAGE_NUM);
    }

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setTitle(getString(R.string.footer_bar_rating));
        headerBar.setLeftButtonVisibility(View.INVISIBLE);
    }

    public void onRatingClick() {
        // TODO: 31-Mar-17 Handle when click rating
    }
}
