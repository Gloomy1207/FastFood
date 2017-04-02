package com.gloomy.fastfood.ui.presenters.main.profile;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.profile.IViewProfile;
import com.gloomy.fastfood.ui.views.main.profile.ProfileViewPagerAdapter;
import com.gloomy.fastfood.utils.TabLayoutUtil;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class ProfilePresenter extends BasePresenter {
    private static final int[] TAB_ICONS = {
            R.drawable.ic_feed,
            R.drawable.ic_detail
    };

    @Setter
    @Accessors(prefix = "m")
    private IViewProfile mView;

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setRightButtonVisibility(View.VISIBLE);
        headerBar.setImageResourceRightButton(R.drawable.ic_setting);
        headerBar.setTitle(getString(R.string.footer_bar_my_page));
    }

    public void getProfileData() {
        mView.setUsername("@Gloomy");
        mView.setFullName("Gloomy Sunday");
        mView.setAvatar(R.drawable.dummy_img_demo);
        mView.setImageBackground(R.drawable.dummy_img_demo);
    }

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager) {
        ProfileViewPagerAdapter adapter = new ProfileViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(ProfileViewPagerAdapter.PAGE_NUM);
        TabLayoutUtil.setCustomViewsTabLayout(tabLayout, TAB_ICONS, mContext);
    }

    public void onFollowClick(FloatingActionButton btnFollow) {
        btnFollow.setSelected(!btnFollow.isSelected());
    }
}
