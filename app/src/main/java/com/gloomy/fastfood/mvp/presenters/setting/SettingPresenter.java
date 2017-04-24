package com.gloomy.fastfood.mvp.presenters.setting;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.login.LoginManager;
import com.gloomy.fastfood.R;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.models.ItemSetting;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.setting.ISettingView;
import com.gloomy.fastfood.mvp.views.setting.SettingAdapter;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 18/04/2017.
 */
@EBean
public class SettingPresenter extends BasePresenter implements SettingAdapter.OnSettingListener {

    @Setter
    @Accessors(prefix = "m")
    private ISettingView mView;

    private List<ItemSetting> mSettings = new ArrayList<>();

    public void initRecyclerView(RecyclerView recyclerView) {
        initSettingList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingAdapter adapter = new SettingAdapter(getContext(), mSettings, this);
        recyclerView.setAdapter(adapter);
    }

    private void initSettingList() {
        mSettings.add(ItemSetting.builder()
                .title(getString(R.string.logout))
                .type(ItemSetting.SettingItemType.LOGOUT)
                .build());
    }

    @Override
    public void onLogoutClick() {
        AuthSession.getInstance().logout();
        LoginManager.getInstance().logOut();
        mView.onLogoutSuccess();
    }

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setTitle(getString(R.string.header_bar_setting));
        headerBar.setOnHeaderBarListener(new HeaderBar.OnHeaderBarListener() {
            @Override
            public void onLeftButtonClick() {
                mView.onBackClick();
            }

            @Override
            public void onRightButtonClick() {
                // No-op
            }
        });
    }
}
