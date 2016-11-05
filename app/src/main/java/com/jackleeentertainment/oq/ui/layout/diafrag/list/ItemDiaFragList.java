package com.jackleeentertainment.oq.ui.layout.diafrag.list;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by jaehaklee on 2016. 10. 3..
 */

public class ItemDiaFragList {

    Drawable drawableIco;
    Drawable drawableBg;
    String text;
    View.OnClickListener onClickListener;

    public ItemDiaFragList() {
        super();
    }


    public Drawable getDrawableIco() {
        return drawableIco;
    }

    public void setDrawableIco(Drawable drawableIco) {
        this.drawableIco = drawableIco;
    }

    public Drawable getDrawableBg() {
        return drawableBg;
    }

    public void setDrawableBg(Drawable drawableBg) {
        this.drawableBg = drawableBg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
