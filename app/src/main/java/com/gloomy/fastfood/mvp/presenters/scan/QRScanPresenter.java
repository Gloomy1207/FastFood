package com.gloomy.fastfood.mvp.presenters.scan;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.FindStoreResponse;
import com.gloomy.fastfood.mvp.models.QRCodeResult;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.scan.IQRScanView;
import com.gloomy.fastfood.utils.NetworkUtil;
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
}
