package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;

/**
 * Created by Jacklee on 2016. 10. 2..
 */

public class TextViewToggleJack extends TextView {
    Context context;
    boolean isChecked = false;
    int intCheckedColorBG, intCheckedColorText, intUnCheckedColorBG, intUnCheckedColorText;
    OnClickListener ocl = new OnClickListener() {
        @Override
        public void onClick(View view) {
            setChecked(!isChecked());
        }
    };

    public void setColors(int chkBG, int chkText, int unChkBG, int unChkText){
        intCheckedColorBG = chkBG;
        intCheckedColorText = chkText;
        intUnCheckedColorBG = unChkBG;
        intUnCheckedColorText = unChkText;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;

        if (checked) {
            setBackgroundColor(
                    JM.colorById(intCheckedColorBG, context));
            setTextColor(JM.colorById(intCheckedColorText, context));
        } else {
            setBackgroundColor(
                    JM.colorById(intUnCheckedColorBG, context));
            setTextColor(JM.colorById(intUnCheckedColorText, context));
        }
    }

    public TextViewToggleJack(Context context) {
        super(context);
    }

    public TextViewToggleJack(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewToggleJack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextViewToggleJack(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }
}


