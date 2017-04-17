package com.gloomy.fastfood.ui.presenters.main.profile;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.ProfileResponse;
import com.gloomy.fastfood.models.User;
import com.gloomy.fastfood.observer.ProfileObserver;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.profile.IViewProfile;
import com.gloomy.fastfood.ui.views.main.profile.ProfileViewPagerAdapter;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.utils.TabLayoutUtil;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private ProfileResponse mProfileResponse;

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setRightButtonVisibility(View.VISIBLE);
        headerBar.setImageResourceRightButton(R.drawable.ic_setting);
        headerBar.setTitle(getString(R.string.footer_bar_my_page));
    }

    public void getProfileData() {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        ApiRequest.getInstance().getProfile(null, new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                mProfileResponse = response.body();
                if (mProfileResponse.isStatus()) {
                    User user = mProfileResponse.getUser();
                    if (user != null) {
                        mView.setUsername(String.format("@%s", user.getUsername()));
                        mView.setFullName(user.getFullname());
                        mView.setAvatar(user.getAvatar());
                        mView.setImageBackground(user.getAvatar());
                    }
                    ProfileObserver.post(mProfileResponse);
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                mView.onLoadDataFailure();
            }
        });
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
