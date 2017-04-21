package com.gloomy.fastfood.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.gloomy.fastfood.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 21/04/2017.
 */
@EViewGroup(R.layout.layout_text_input)
public class CustomTextInputLayout extends RelativeLayout {

    @ViewById(R.id.edtContent)
    EditText mEdtContent;

    @Setter
    @Accessors(prefix = "m")
    OnTextInputLayoutListener mOnTextInputLayoutListener;

    public CustomTextInputLayout(Context context) {
        this(context, null);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Click(R.id.btnSend)
    void onSendClick() {
        if (mOnTextInputLayoutListener != null) {
            mOnTextInputLayoutListener.onSendClick(mEdtContent.getText().toString());
        }
    }

    public void setHint(String hint) {
        mEdtContent.setHint(hint);
    }

    /**
     * OnTextInputLayoutListener interface
     */
    public interface OnTextInputLayoutListener {
        void onSendClick(String message);
    }
}
