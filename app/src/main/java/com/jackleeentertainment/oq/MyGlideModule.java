package com.jackleeentertainment.oq;
import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Jacklee on 2016. 10. 5..
 */

public class MyGlideModule  implements GlideModule {
    @Override public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
    }

    @Override public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
    }
}