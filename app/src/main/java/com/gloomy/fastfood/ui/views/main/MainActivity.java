package com.gloomy.fastfood.ui.views.main;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.BaseActivity;
import com.gloomy.fastfood.ui.BaseContainerFragment;
import com.gloomy.fastfood.ui.presenters.main.MainPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Field;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements IViewMain, BottomNavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.footerBar)
    BottomNavigationView mFooterBar;

    @Bean
    MainPresenter mPresenter;

    @AfterViews
    void afterViews() {
        disableShiftMode(mFooterBar);
        mPresenter.setView(this);
        mFooterBar.setOnNavigationItemSelectedListener(this);
        mPresenter.initViewPager(mViewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mPresenter.onFooterBarItemSelect(item.getItemId());
        return true;
    }

    @Override
    public void onFooterBarItemClick(int position) {
        mViewPager.setCurrentItem(position, true);
    }

    @Override
    public void onBackPressed() {
        boolean isPopFragment = false;
        BaseContainerFragment baseContainerFragment = mPresenter.getCurrentBaseFragment();
        if (baseContainerFragment != null) {
            isPopFragment = baseContainerFragment.popFragment();
        }
        if (!isPopFragment) {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("RestrictedApi")
    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("TAG", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("TAG", "Unable to change value of shift mode");
        }
    }
}
