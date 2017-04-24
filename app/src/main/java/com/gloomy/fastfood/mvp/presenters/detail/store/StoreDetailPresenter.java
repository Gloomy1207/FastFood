package com.gloomy.fastfood.mvp.presenters.detail.store;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.models.StoreAddress;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.detail.store.IStoreDetailView;
import com.gloomy.fastfood.mvp.views.detail.store.StoreDetailActivity;
import com.gloomy.fastfood.mvp.views.detail.store.StoreDetailPagerAdapter;
import com.gloomy.fastfood.utils.TabLayoutUtil;
import com.gloomy.fastfood.widgets.CustomTextInputLayout;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;

import java.text.SimpleDateFormat;
import java.util.Locale;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
@EBean
public class StoreDetailPresenter extends BasePresenter {

    private static final int[] TAB_ICONS = {
            R.drawable.ic_restaurant_menu,
            R.drawable.ic_mode_comment
    };

    @Setter
    @Accessors(prefix = "m")
    private IStoreDetailView mView;

    @Setter
    @Accessors(prefix = "m")
    private Store mStore;

    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setTitle(mStore.getStoreName());
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

    public void initButtonLike(FloatingActionButton btnLike) {

    }

    public void initCommentLayout(CustomTextInputLayout commentLayout) {

    }

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout) {
        if (getContext() instanceof StoreDetailActivity) {
            viewPager.setAdapter(new StoreDetailPagerAdapter(((StoreDetailActivity) getContext()).getFragmentManager()));
            viewPager.setOffscreenPageLimit(StoreDetailPagerAdapter.NUM_PAGE);
            tabLayout.setupWithViewPager(viewPager);
            TabLayoutUtil.setCustomViewsTabLayout(tabLayout, TAB_ICONS, mContext);
        }
    }

    public void setDataForViews() {
        mView.onSetStoreName(mStore.getStoreName());
        mView.onSetStoreTime(String.format("%s - %s", mSimpleDateFormat.format(mStore.getOpenTime()), mSimpleDateFormat.format(mStore.getCloseTime())));
        StringBuilder builder = new StringBuilder();
        if (mStore.getStoreAddress() != null) {
            StoreAddress address = mStore.getStoreAddress();
            builder.append(address.getAddressName()).append(", ");
            if (address.getProvince() != null) {
                builder.append(address.getProvince().getProvinceName()).append(", ");
                if (address.getProvince().getCity() != null) {
                    builder.append(address.getProvince().getCity().getCityName());
                }
            }
        }
        int lastIndexOfSplitAddress = builder.lastIndexOf(" ,");
        if (lastIndexOfSplitAddress != -1) {
            builder.delete(lastIndexOfSplitAddress, lastIndexOfSplitAddress + 2);
        }
        mView.onSetStoreAddress(builder.toString());
        mView.onSetStoreImage(mStore.getMainImage());
    }
}
