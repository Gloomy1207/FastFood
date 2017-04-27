package com.gloomy.fastfood.mvp.views.detail.store.location;

import android.support.annotation.NonNull;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseActivity;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.detail.place.StoreDetailLocationPresenter;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
@EActivity(R.layout.activity_detail_store_location)
public class StoreDetailLocationActivity extends BaseActivity implements IStoreDetailLocationView {

    @Extra
    Store mStore;

    @Bean
    StoreDetailLocationPresenter mPresenter;

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.setStore(mStore);
        mPresenter.initHeaderBar(mHeaderBar);
        mPresenter.initMap(R.id.mapView, getFragmentManager());
        mPresenter.buildGoogleApiClient();
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
    public void onRequestPermission() {
        mPresenter.requestPermission(this);
    }

    @Override
    public void onCantGetLocation() {
        showMessageDialog(getString(R.string.cant_get_location), getString(R.string.button_close));
    }

    @Override
    public void onDirectionFailure() {
        showMessageDialog(getString(R.string.cant_get_direction), getString(R.string.button_close));
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onPermissionResult(requestCode, permissions, grantResults);
    }
}
