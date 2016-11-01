package com.jackleeentertainment.oq.ui.widget;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * Created by Jacklee on 2016. 10. 31..
 */

public class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return source;
    }
}