package com.jackleeentertainment.oq.ui.layout.diafrag.list;

import android.content.DialogInterface;
import android.view.View;

/**
 * Created by jaehaklee on 2016. 10. 3..
 */

public class ItemDiaFragList {

    int idDrawableIco;
    int idDrawableBg;
    String text;
    View.OnClickListener onClickListener;

    public ItemDiaFragList() {
        super();
    }

    public int getIdDrawableIco() {
        return idDrawableIco;
    }

    public void setIdDrawableIco(int idDrawableIco) {
        this.idDrawableIco = idDrawableIco;
    }

    public int getIdDrawableBg() {
        return idDrawableBg;
    }

    public void setIdDrawableBg(int idDrawableBg) {
        this.idDrawableBg = idDrawableBg;
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
