package com.gloomy.fastfood.widgets.dialog;

import android.app.DialogFragment;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.utils.LocaleUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 16/05/2017.
 */
@EFragment(R.layout.layout_language_dialog)
public class CustomLanguageDialog extends DialogFragment {

    @ViewById(R.id.rbVietnamese)
    RadioButton mRbVietnamese;

    @ViewById(R.id.rbEnglish)
    RadioButton mRbEnglish;

    @ViewById(R.id.tvTitle)
    TextView mTvTitle;

    @Setter
    @Accessors(prefix = "m")
    private OnLanguageListener mOnLanguageListener;

    @AfterViews
    void afterViews() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        Locale currentLocale = LocaleUtil.getInstance().getCurrentLocale(getActivity());
        if (currentLocale.getLanguage().equals("vi")) {
            mRbVietnamese.setChecked(true);
        } else {
            mRbEnglish.setChecked(true);
        }
        mTvTitle.setText(R.string.setting_language);
    }

    @Click(R.id.tvCancel)
    void onCancelClick() {
        dismiss();
    }

    @Click(R.id.tvOk)
    void onOkClick() {
        Locale locale;
        if (mRbEnglish.isChecked()) {
            locale = Locale.ENGLISH;
        } else {
            locale = new Locale("vi", "VN");
        }
        LocaleUtil.getInstance().setCurrentLocale(getActivity(), locale);
        if (mOnLanguageListener != null) {
            mOnLanguageListener.onLanguageChange();
        }
    }

    /**
     * OnLanguageListener
     */
    public interface OnLanguageListener {
        void onLanguageChange();
    }
}
