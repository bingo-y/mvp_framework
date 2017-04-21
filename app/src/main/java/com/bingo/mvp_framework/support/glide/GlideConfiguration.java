package com.bingo.mvp_framework.support.glide;

import android.content.Context;

import com.bingo.base.util.FileUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.File;
import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by tuyx on 2017/4/21.
 * Description : glide配置
 */

public class GlideConfiguration implements GlideModule {

    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为100Mb

    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(FileUtil.getAppImageFile(context), IMAGE_DISK_CACHE_MAX_SIZE);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
//        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
