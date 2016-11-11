package com.jackleeentertainment.oq.ui.widget;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;

import java.util.StringTokenizer;

/**
 * Created by Jacklee on 2016. 10. 30..
 */

public class KRW_TextWatcherJack implements TextWatcher {
    String TAG = "KRW_TextWatcherJack";
    EditText mEditText ;



    public KRW_TextWatcherJack() {
        super();
    }

    public KRW_TextWatcherJack(EditText editText) {
        super();
        mEditText = editText;
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d(TAG, "beforeTextChanged(CharSequence s, int start, int count, int after) "
                + s.toString() + ", "
                + J.st(start) + ", "
                + J.st(count) + ", "
                + J.st(after + ", ")
        );
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG, "onTextChanged(CharSequence s, int start, int before, int count) "
                + s.toString() + ", "
                + J.st(start) + ", "
                + J.st(before) + ", "
                + J.st(count) + ", "
        );
    }

    @Override
    public void afterTextChanged(Editable s) {

        Log.d(TAG, "afterTextChanged(Editable s) " + s.toString());

        try
        {
            mEditText.removeTextChangedListener(this);
            String value = mEditText.getText().toString();


            if (value != null && !value.equals(""))
            {

                if(value.startsWith(".")){
                    mEditText.setText("0.");
                }
                if(value.startsWith("0") && !value.startsWith("0.")){
                    mEditText.setText("");

                }


                String str = mEditText.getText().toString().replaceAll(",", "");
                if (!value.equals(""))
                    mEditText.setText(getDecimalFormattedString(str));
                mEditText.setSelection(mEditText.getText().toString().length());
            }
            mEditText.addTextChangedListener(this);
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            mEditText.addTextChangedListener(this);
        }
        mEditText.setSelection(mEditText.getText().length());




    }

    public static String getDecimalFormattedString(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3)
            {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }

    public static String trimCommaOfString(String string) {
//        String returnString;
        if(string.contains(",")){
            return string.replace(",","");}
        else {
            return string;
        }

    }

}
