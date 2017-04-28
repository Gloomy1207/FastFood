package com.gloomy.fastfood.mvp.views.main.profile;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.presenters.main.profile.ProfilePresenter;
import com.gloomy.fastfood.mvp.views.setting.SettingActivity_;
import com.gloomy.fastfood.widgets.HeaderBar;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends BaseFragment implements IViewProfile {

    @Bean
    ProfilePresenter mPresenter;

    @ViewById(R.id.imgBackground)
    ImageView mImgBackground;

    @ViewById(R.id.imgAvatar)
    CircleImageView mImgAvatar;

    @ViewById(R.id.tvUsername)
    TextView mTvUserName;

    @ViewById(R.id.tvFullName)
    TextView mTvFullName;

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;

    @ViewById(R.id.scrollView)
    NestedScrollView mNestedScrollView;

    @FragmentArg
    User mUser;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.setUser(mUser);
        mPresenter.initHeaderBar(mHeaderBar);
        mPresenter.initViewPager(mViewPager, mTabLayout, getChildFragmentManager());
        mNestedScrollView.setFillViewport(true);
        mPresenter.getProfileData();
    }

    @Override
    public void setUsername(String username) {
        mTvUserName.setText(username);
    }

    @Override
    public void setFullName(String fullName) {
        mTvFullName.setText(fullName);
    }

    @Override
    public void setAvatar(String avatar) {
        Picasso.with(getActivity())
                .load(avatar)
                .transform(new CropCircleTransformation())
                .into(mImgAvatar);
    }

    @Override
    public void setAvatar(int resId) {
        Picasso.with(getActivity())
                .load(resId)
                .transform(new CropCircleTransformation())
                .into(mImgAvatar);
    }

    @Override
    public void setImageBackground(String avatar) {
        Picasso.with(getActivity())
                .load(avatar)
                .into(mImgBackground);
    }

    @Override
    public void setImageBackground(int resId) {
        Picasso.with(getActivity())
                .load(resId)
                .transform(new GrayscaleTransformation())
                .into(mImgBackground);
    }

    @Override
    public void onLoadDataFailure() {
        showLoadDataFailure();
    }

    @Override
    public void onSettingClick() {
        SettingActivity_.intent(this).startForResult(ProfilePresenter.SETTING_REQUEST_CODE);
        getActivity().overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }

    @Override
    public void onBackPress() {
        getActivity().onBackPressed();
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onDismissProgressDialog() {
        dismissProgressDialog();
    }

    @Override
    public void onNoInternetConnection() {
        showNoInternetConnectionMessage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode);
    }
}
