package com.gloomy.fastfood.mvp.presenters.main.profile;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.ProfileResponse;
import com.gloomy.fastfood.auth.Auth;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.profile.IViewProfile;
import com.gloomy.fastfood.mvp.views.main.profile.ProfileViewPagerAdapter;
import com.gloomy.fastfood.observer.ProfileObserver;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.utils.TabLayoutUtil;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class ProfilePresenter extends BasePresenter {
    public static final int SETTING_REQUEST_CODE = 213;

    private static final int[] TAB_ICONS = {
            R.drawable.ic_feed,
            R.drawable.ic_detail
    };

    @Setter
    @Accessors(prefix = "m")
    private IViewProfile mView;

    @Setter
    @Accessors(prefix = "m")
    private User mUser;

    private ProfileResponse mProfileResponse;

    public void initHeaderBar(HeaderBar headerBar) {
        if (mUser == null) {
            headerBar.setRightButtonVisibility(View.VISIBLE);
            headerBar.setLeftButtonVisibility(View.INVISIBLE);
            headerBar.setImageResourceRightButton(R.drawable.ic_setting);
            headerBar.setTitle(getString(R.string.footer_bar_my_page));
            headerBar.setOnHeaderBarListener(new HeaderBar.OnHeaderBarListener() {
                @Override
                public void onLeftButtonClick() {
                    // No-op
                }

                @Override
                public void onRightButtonClick() {
                    mView.onSettingClick();
                }
            });
        } else {
            headerBar.setRightButtonVisibility(View.INVISIBLE);
            headerBar.setLeftButtonVisibility(View.VISIBLE);
            headerBar.setTitle(mUser.getUsername());
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
    }

    public void getProfileData() {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        mView.onShowProgressDialog();
        String username = null;
        if (mUser != null) {
            username = mUser.getUsername();
        }
        ApiRequest.getInstance().getProfile(username, new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                mProfileResponse = response.body();
                if (mProfileResponse.isStatus()) {
                    User user = mProfileResponse.getUser();
                    if (AuthSession.isLogIn() && mUser == null) {
                        Auth auth = AuthSession.getInstance().getAuthLogin();
                        auth.setUser(user);
                        AuthSession.getInstance().setAuth(auth);
                    }
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
                mView.onDismissProgressDialog();
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

    public void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == SETTING_REQUEST_CODE && resultCode == RESULT_OK) {
            getProfileData();
        }
    }
}
