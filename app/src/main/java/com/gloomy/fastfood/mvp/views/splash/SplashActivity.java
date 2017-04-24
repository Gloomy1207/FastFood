package com.gloomy.fastfood.mvp.views.splash;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseActivity;
import com.gloomy.fastfood.mvp.presenters.splash.SplashPresenter;
import com.gloomy.fastfood.mvp.views.main.MainActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 14-Mar-17.
 */
@Fullscreen
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements IViewSplash {
    private static final int ANIMATION_DURATION = 700;

    @ViewById(R.id.imgSplash)
    ImageView mImgSplash;

    @ViewById(R.id.imgSplashText)
    ImageView mImgSplashText;

    @ViewById(R.id.layoutParent)
    LinearLayout mLayoutParent;

    private SplashPresenter mPresenter = new SplashPresenter();

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.startSplash();
    }

    @Override
    public void onSplashFinish() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(mImgSplash, "scaleX", 1, 0.1f),
                ObjectAnimator.ofFloat(mImgSplash, "scaleY", 1, 0.1f),
                ObjectAnimator.ofFloat(mImgSplashText, "scaleX", 1, 0.1f),
                ObjectAnimator.ofFloat(mImgSplashText, "scaleY", 1, 0.1f),
                ObjectAnimator.ofFloat(mLayoutParent, "alpha", 1, 0.1f)
        );
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                // No-op
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                MainActivity_.intent(SplashActivity.this).start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // No-op
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                // No-op
            }
        });
    }
}
