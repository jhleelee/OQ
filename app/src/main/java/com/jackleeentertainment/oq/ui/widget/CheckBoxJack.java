package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;


/**
 * Created by Jacklee on 2016. 9. 8..
 */
public class CheckBoxJack extends ImageButton {

    Context context;
    boolean isChecked = false;

    OnClickListener ocl = new OnClickListener() {
        @Override
        public void onClick(View view) {
            setChecked(!isChecked());
        }
    };

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;

        if (checked) {
            setImageDrawable(
                    JM.drawableById(R.drawable.ic_check_white_48dp, context));
            setBackgroundResource(R.drawable.jackcheckboxbg_checked);
        } else {
            setImageDrawable(null);
            setBackgroundResource(R.drawable.jackcheckboxbg_unchecked);

        }
    }


    /********
     * Overides
     *********/


    public CheckBoxJack(Context context) {
        super(context);
        setScaleType(ScaleType.CENTER_CROP);
        setOnClickListener(ocl);
    }

    public CheckBoxJack(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.CENTER_CROP);
        setOnClickListener(ocl);
    }

    public CheckBoxJack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.CENTER_CROP);
        setOnClickListener(ocl);
    }

    //API21
//    public CheckBoxJack(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        setScaleType(ScaleType.CENTER_CROP);
//        setOnClickListener(ocl);
//    }

    @Override
    protected boolean onSetAlpha(int alpha) {
        return super.onSetAlpha(alpha);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return super.getAccessibilityClassName();
    }

    @Override
    protected boolean verifyDrawable(Drawable dr) {
        return super.verifyDrawable(dr);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        super.invalidateDrawable(dr);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return super.hasOverlappingRendering();
    }

    @Override
    public boolean getAdjustViewBounds() {
        return super.getAdjustViewBounds();
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        super.setAdjustViewBounds(adjustViewBounds);
    }

    @Override
    public int getMaxWidth() {
        return super.getMaxWidth();
    }

    @Override
    public void setMaxWidth(int maxWidth) {
        super.setMaxWidth(maxWidth);
    }

    @Override
    public int getMaxHeight() {
        return super.getMaxHeight();
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        super.setMaxHeight(maxHeight);
    }

    @Override
    public Drawable getDrawable() {
        return super.getDrawable();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageIcon(Icon icon) {
        super.setImageIcon(icon);
    }

    @Override
    public void setImageTintList(ColorStateList tint) {
        super.setImageTintList(tint);
    }

    @Nullable
    @Override
    public ColorStateList getImageTintList() {
        return super.getImageTintList();
    }

    @Override
    public void setImageTintMode(PorterDuff.Mode tintMode) {
        super.setImageTintMode(tintMode);
    }

    @Nullable
    @Override
    public PorterDuff.Mode getImageTintMode() {
        return super.getImageTintMode();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageState(int[] state, boolean merge) {
        super.setImageState(state, merge);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
    }

    @Override
    public void setImageLevel(int level) {
        super.setImageLevel(level);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(scaleType);
    }

    @Override
    public ScaleType getScaleType() {
        return super.getScaleType();
    }

    @Override
    public Matrix getImageMatrix() {
        return super.getImageMatrix();
    }

    @Override
    public void setImageMatrix(Matrix matrix) {
        super.setImageMatrix(matrix);
    }

    @Override
    public boolean getCropToPadding() {
        return super.getCropToPadding();
    }

    @Override
    public void setCropToPadding(boolean cropToPadding) {
        super.setCropToPadding(cropToPadding);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        return super.setFrame(l, t, r, b);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }

    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public int getBaseline() {
        return super.getBaseline();
    }

    @Override
    public void setBaseline(int baseline) {
        super.setBaseline(baseline);
    }

    @Override
    public void setBaselineAlignBottom(boolean aligned) {
        super.setBaselineAlignBottom(aligned);
    }

    @Override
    public boolean getBaselineAlignBottom() {
        return super.getBaselineAlignBottom();
    }

    @Override
    public ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        super.setColorFilter(cf);
    }

    @Override
    public int getImageAlpha() {
        return super.getImageAlpha();
    }

    @Override
    public void setImageAlpha(int alpha) {
        super.setImageAlpha(alpha);
    }




    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }
}
