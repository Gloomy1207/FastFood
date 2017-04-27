package com.gloomy.fastfood.mvp.views.detail.store;

import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseActivity;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.detail.store.StoreDetailPresenter;
import com.gloomy.fastfood.mvp.presenters.gallery.GalleryPresenter;
import com.gloomy.fastfood.mvp.views.detail.store.location.StoreDetailLocationActivity_;
import com.gloomy.fastfood.mvp.views.gallery.GalleryActivity_;
import com.gloomy.fastfood.widgets.CustomTextInputLayout;
import com.gloomy.fastfood.widgets.HeaderBar;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
@EActivity(R.layout.activity_detail_store)
public class StoreDetailActivity extends BaseActivity implements IStoreDetailView {

    @Bean
    StoreDetailPresenter mPresenter;

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @ViewById(R.id.textInputLayout)
    CustomTextInputLayout mCommentLayout;

    @ViewById(R.id.layoutParent)
    CoordinatorLayout mLayoutParent;

    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;

    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.imgPlace)
    ImageView mImgPlace;

    @ViewById(R.id.tvStoreName)
    TextView mTvStoreName;

    @ViewById(R.id.tvStoreTime)
    TextView mTvStoreTime;

    @ViewById(R.id.tvStoreAddress)
    TextView mTvStoreAddress;

    @ViewById(R.id.tvNumberStar)
    TextView mTvNumberStar;

    @ViewById(R.id.tvNumberRating)
    TextView mTvNumberRating;

    @ViewById(R.id.btnFavorite)
    FloatingActionButton mBtnFavorite;

    @Extra
    Parcelable mStoreParcel;

    @AfterViews
    void afterViews() {
        mLayoutParent.requestFocus();
        Store store = Parcels.unwrap(mStoreParcel);
        mPresenter.setView(this);
        mPresenter.setStore(store);
        mPresenter.setDataForViews();
        mPresenter.initViewPager(mViewPager, mTabLayout);
        mPresenter.initHeaderBar(mHeaderBar);
        mPresenter.initButtonFavorite(mBtnFavorite);
        mPresenter.initCommentLayout(mCommentLayout);
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
        showNoInternetConnection();
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onSetStoreName(String storeName) {
        mTvStoreName.setText(storeName);
    }

    @Override
    public void onSetStoreTime(String storeTime) {
        mTvStoreTime.setText(storeTime);
    }

    @Override
    public void onSetStoreAddress(String storeAddress) {
        mTvStoreAddress.setText(storeAddress);
    }

    @Override
    public void onSetStoreImage(String imagePath) {
        Picasso.with(this)
                .load(imagePath)
                .into(mImgPlace);
    }

    @Override
    public void onSetNumberStars(String stars) {
        mTvNumberStar.setText(stars);
    }

    @Override
    public void onSetNumberRating(String numberRating) {
        mTvNumberRating.setText(numberRating);
    }

    @Override
    public void onNotLogin() {
        showLoginDialog();
    }

    @Override
    public void onEmptyComment() {
        showMessageDialog(getString(R.string.comment_is_required), getString(R.string.button_close));
    }

    @Override
    public void onSendingComment() {
        showMessageDialog(getString(R.string.sending_comment), getString(R.string.button_close));
    }

    @Override
    public void onSendComment() {
        mViewPager.setCurrentItem(StoreDetailItemPosition.COMMENT);
    }

    @Override
    public void onRatingComplete(String message) {
        showMessageDialog(message, getString(R.string.button_close));
    }

    @Override
    public void onRatingFailure() {
        showLoadDataFailure();
    }

    @Override
    public void onViewImagesClick() {
        Store store = Parcels.unwrap(mStoreParcel);
        GalleryActivity_.intent(this).mGalleryId(store.getStoreId()).mGalleryName(store.getStoreName()).mGalleryType(GalleryPresenter.GalleryType.STORE_TYPE).start();
    }

    @Click(R.id.btnFavorite)
    void onFavoriteClick() {
        mPresenter.onFavoriteClick(mBtnFavorite);
    }

    public void onSendCommentComplete() {
        mPresenter.onSendCommentComplete();
    }

    @Click(R.id.layoutRating)
    void onRatingClick() {
        mPresenter.onRatingClick(getFragmentManager());
    }

    @Click(R.id.btnMap)
    void onMapClick() {
        Store store = Parcels.unwrap(mStoreParcel);
        StoreDetailLocationActivity_.intent(this).mStore(store).start();
    }

    /**
     * StoreDetailItemPosition definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef
    private @interface StoreDetailItemPosition {
        int MENU = 0;
        int COMMENT = 1;
    }
}
