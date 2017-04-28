package com.gloomy.fastfood.mvp.views.scan;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseActivity;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.scan.QRScanPresenter;
import com.gloomy.fastfood.mvp.views.detail.store.StoreDetailActivity_;
import com.gloomy.fastfood.widgets.dialog.CustomMessageDialog;
import com.google.zxing.Result;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
@EActivity(R.layout.activity_qr_scanner)
public class QRScanActivity extends BaseActivity implements IQRScanView, ZXingScannerView.ResultHandler {

    @ViewById(R.id.scannerView)
    ZXingScannerView mScannerView;

    @Bean
    QRScanPresenter mPresenter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        mPresenter.onScanQrCode(result);
    }

    @Override
    public void onWrongTypeCode() {
        showMessageDialog(getString(R.string.wrong_qr_type), getString(R.string.button_close), new CustomMessageDialog.OnCustomMessageDialogListener() {
            @Override
            public void onCloseClick() {
                finish();
            }
        });
    }

    @Override
    public void onFinishScan(Store store) {
        finish();
        StoreDetailActivity_.intent(this).mStoreParcel(Parcels.wrap(store)).start();
    }

    @Override
    public void onNoStoreFound(String message) {
        showMessageDialog(message, getString(R.string.button_close), new CustomMessageDialog.OnCustomMessageDialogListener() {
            @Override
            public void onCloseClick() {
                finish();
            }
        });
    }

    @Override
    public void onLoadDataFailure() {
        showLoadDataFailure();
        finish();
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
        finish();
    }
}
