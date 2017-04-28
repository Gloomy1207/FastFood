package com.gloomy.fastfood.mvp.presenters.scan;

import android.Manifest;
import android.content.pm.PackageManager;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.FindStoreResponse;
import com.gloomy.fastfood.mvp.models.QRCodeResult;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.scan.IQRScanView;
import com.gloomy.fastfood.mvp.views.scan.QRScanActivity;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.utils.PermissionUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.Result;

import org.androidannotations.annotations.EBean;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
@EBean
public class QRScanPresenter extends BasePresenter {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 242;

    @Setter
    @Accessors(prefix = "m")
    private IQRScanView mView;

    public void onScanQrCode(Result result) {
        Gson gson = new Gson();
        try {
            QRCodeResult codeResult = gson.fromJson(result.getText(), QRCodeResult.class);
            getStoreData(codeResult);
        } catch (JsonSyntaxException exc) {
            mView.onWrongTypeCode();
        }
    }

    private void getStoreData(QRCodeResult result) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getStoreById(result.getStoreId(), new Callback<FindStoreResponse>() {
            @Override
            public void onResponse(Call<FindStoreResponse> call, Response<FindStoreResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                FindStoreResponse storeResponse = response.body();
                if (storeResponse.isStatus()) {
                    mView.onFinishScan(storeResponse.getStore());
                } else {
                    mView.onNoStoreFound(storeResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<FindStoreResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }

    public void checkCameraPermission(QRScanActivity activity) {
        if (!PermissionUtil.isPermissionGranted(activity, Manifest.permission.CAMERA)) {
            PermissionUtil.requestPermissions(activity, CAMERA_PERMISSION_REQUEST_CODE, Manifest.permission.CAMERA);
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                mView.onOpenCamera();
            } else {
                mView.onPermissionDenied();
            }
        }
    }
}
