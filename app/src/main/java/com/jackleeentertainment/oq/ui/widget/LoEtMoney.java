package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Profile;

/**
 * Created by Jacklee on 2016. 11. 6..
 */

public class LoEtMoney extends LinearLayout {

   public  LinearLayout loMoney;
    public  TextView tvMoneySym;
    public  EditText etMoneyAmmount;

    public LoEtMoney(Context context) {
        this(context, null);
    }

    public LoEtMoney(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.lo_momey, this);
        common(view);
    }

    public LoEtMoney(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.lo_momey, this);
        common(view);

    }

    void common(View view){
        loMoney = (LinearLayout) view.findViewById(R.id.loMoney);
        tvMoneySym = (TextView) view.findViewById(R.id.tvMoneySym);
        etMoneyAmmount = (EditText) view.findViewById(R.id.etMoneyAmmount);

        etMoneyAmmount.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){
                    loMoney.setBackground(JM.drawableById(R.drawable.et_comment_focused));
                    tvMoneySym.setTextColor(JM.colorById(R.color.white));
                } else {
                    loMoney.setBackground(JM.drawableById(R.drawable.et_comment_default));
                    tvMoneySym.setTextColor(JM.colorById(R.color.text_white_87));
                }

            }
        });

        etMoneyAmmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType
                .TYPE_NUMBER_VARIATION_PASSWORD);
        etMoneyAmmount.setTransformationMethod(new NumericKeyBoardTransformationMethod());
    }


}

