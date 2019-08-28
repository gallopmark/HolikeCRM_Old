package com.holike.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.holike.glide.listener.IGetBitmapListener;
import com.holike.glide.listener.IGetDrawableListener;
import com.holike.glide.listener.IImageLoaderListener;
import com.holike.glide.listener.ProgressListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GlideImageLoaderClient implements IImageLoaderClient {

    private GlideManager mGlideManager;

    public GlideImageLoaderClient() {
        mGlideManager = GlideManager.newInstance();
    }

    @Override
    public void destroy(Context context) {
        clearMemoryCache(context);
    }

    @Override
    public File getCacheDir(Context context) {
        return mGlideManager.getCacheDir(context);
    }

    @Override
    public void clearMemoryCache(Context context) {
        mGlideManager.clearMemoryCache(context);
    }

    @Override
    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mGlideManager.clearDiskCache(context);
            }
        }).start();
    }

    @Override
    public Bitmap getBitmapFromCache(Context context, String url) {
        throw new UnsupportedOperationException("glide 不支持同步 获取缓存中 bitmap");
    }

    @Override
    public void getBitmapFromCache(Context context, String url, final IGetBitmapListener listener) {
        mGlideManager.getBitmapFromCache(context, url, listener);
    }

    /**
     * 默认的策略是DiskCacheStrategy.AUTOMATIC
     * DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
     * DiskCacheStrategy.NONE 不使用磁盘缓存
     * DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
     * DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
     * DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
     *
     * @param context   上下文
     * @param imageView into
     */
    @Override
    public void displayImage(Context context, String url, ImageView imageView) {
        mGlideManager.displayImage(context, url, imageView);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView) {
        mGlideManager.displayImage(fragment, url, imageView);
    }

    @Override
    public void displayImage(Activity activity, String url, ImageView imageView) {
        mGlideManager.displayImage(activity, url, imageView);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, boolean isCache) {
        mGlideManager.displayImage(context, url, imageView, isCache);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes) {
        mGlideManager.displayImage(context, url, imageView, defRes);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mGlideManager.displayImage(context, url, imageView, placeholder, errorRes);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes) {
        mGlideManager.displayImage(activity, url, imageView, defRes);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mGlideManager.displayImage(activity, url, imageView, placeholder, errorRes);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes) {
        mGlideManager.displayImage(fragment, url, imageView, defRes);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mGlideManager.displayImage(fragment, url, imageView, placeholder, errorRes);
    }

    public void displayImage(Context context, String url, ImageView imageView, BitmapTransformation transformations) {
        mGlideManager.displayImage(context, url, imageView, transformations);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mGlideManager.displayImage(activity, url, imageView, defRes, transformations);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, BitmapTransformation transformations) {
        mGlideManager.displayImage(activity, url, imageView, placeholder, errorRes, transformations);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mGlideManager.displayImage(context, url, imageView, defRes, transformations);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, BitmapTransformation transformations) {
        mGlideManager.displayImage(context, url, imageView, placeholder, errorRes, transformations);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, BitmapTransformation transformations) {
        mGlideManager.displayImage(fragment, url, imageView, transformations);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mGlideManager.displayImage(fragment, url, imageView, defRes, transformations);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, BitmapTransformation transformations) {
        mGlideManager.displayImage(fragment, url, imageView, placeholder, errorRes, transformations);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, ImageSize size) {
        mGlideManager.displayImage(activity, url, imageView, size);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, ImageSize size) {
        mGlideManager.displayImage(activity, url, imageView, defRes, size);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, ImageSize size) {
        mGlideManager.displayImage(activity, url, imageView, placeholder, errorRes, size);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, ImageSize size) {
        mGlideManager.displayImage(context, url, imageView, defRes, size);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, ImageSize size) {
        mGlideManager.displayImage(context, url, imageView, placeholder, errorRes, size);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, ImageSize size) {
        mGlideManager.displayImage(fragment, url, imageView, defRes, size);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, ImageSize size) {
        mGlideManager.displayImage(fragment, url, imageView, placeholder, errorRes, size);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, ImageSize size) {
        mGlideManager.displayImage(context, url, imageView, size);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, ImageSize size) {
        mGlideManager.displayImage(fragment, url, imageView, size);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, boolean cacheInMemory) {
        mGlideManager.displayImage(context, url, imageView, defRes, cacheInMemory);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, boolean cacheInMemory) {
        mGlideManager.displayImage(context, url, imageView, placeholder, errorRes, cacheInMemory);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, boolean cacheInMemory) {
        mGlideManager.displayImage(fragment, url, imageView, defRes, cacheInMemory);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, boolean cacheInMemory) {
        mGlideManager.displayImage(fragment, url, imageView, placeholder, errorRes, cacheInMemory);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, IImageLoaderListener listener) {
        mGlideManager.displayImage(context, url, imageView, listener);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, IImageLoaderListener listener) {
        mGlideManager.displayImage(activity, url, imageView, listener);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, IImageLoaderListener listener) {
        mGlideManager.displayImage(fragment, url, imageView, listener);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, IImageLoaderListener listener) {
        mGlideManager.displayImage(context, url, imageView, defRes, listener);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, IImageLoaderListener listener) {
        mGlideManager.displayImage(activity, url, imageView, defRes, listener);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, IImageLoaderListener listener) {
        mGlideManager.displayImage(fragment, url, imageView, defRes, listener);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, IImageLoaderListener listener) {
        mGlideManager.displayImage(context, url, imageView, placeholder, errorRes, listener);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, IImageLoaderListener listener) {
        mGlideManager.displayImage(activity, url, imageView, placeholder, errorRes, listener);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, IImageLoaderListener listener) {
        mGlideManager.displayImage(fragment, url, imageView, placeholder, errorRes, listener);
    }

    public void displayCircleImage(Context context, String url, ImageView imageView) {
        mGlideManager.displayCircleImage(context, url, imageView);
    }

    @Override
    public void displayCircleImage(Context context, String url, ImageView imageView, @DrawableRes int defRes) {
        mGlideManager.displayCircleImage(context, url, imageView, defRes);
    }

    public void displayCircleImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mGlideManager.displayCircleImage(context, url, imageView, placeholder, errorRes);
    }

    public void displayCircleImage(Activity activity, String url, ImageView imageView) {
        mGlideManager.displayCircleImage(activity, url, imageView);
    }

    public void displayCircleImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes) {
        mGlideManager.displayCircleImage(activity, url, imageView, defRes);
    }

    public void displayCircleImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mGlideManager.displayCircleImage(activity, url, imageView, placeholder, errorRes);
    }

    public void displayCircleImage(Fragment fragment, String url, ImageView imageView) {
        mGlideManager.displayCircleImage(fragment, url, imageView);
    }

    @Override
    public void displayCircleImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes) {
        mGlideManager.displayCircleImage(fragment, url, imageView, defRes);
    }

    public void displayCircleImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mGlideManager.displayCircleImage(fragment, url, imageView, placeholder, errorRes);
    }

    public void displayRoundImage(Context context, String url, ImageView imageView, int radius) {
        mGlideManager.displayRoundImage(context, url, imageView, radius);
    }

    @Override
    public void displayRoundImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, int radius) {
        mGlideManager.displayRoundImage(context, url, imageView, defRes, radius);
    }

    public void displayRoundImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int radius) {
        mGlideManager.displayRoundImage(context, url, imageView, placeholder, errorRes, radius);
    }

    public void displayRoundImage(Activity activity, String url, ImageView imageView, int radius) {
        mGlideManager.displayRoundImage(activity, url, imageView, radius);
    }

    public void displayRoundImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, int radius) {
        mGlideManager.displayRoundImage(activity, url, imageView, defRes, radius);
    }

    public void displayRoundImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int radius) {
        mGlideManager.displayRoundImage(activity, url, imageView, placeholder, errorRes, radius);
    }

    public void displayRoundImage(Fragment fragment, String url, ImageView imageView, int radius) {
        mGlideManager.displayRoundImage(fragment, url, imageView, radius);
    }

    @Override
    public void displayRoundImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, int radius) {
        mGlideManager.displayRoundImage(fragment, url, imageView, defRes, radius);
    }

    public void displayRoundImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int radius) {
        mGlideManager.displayRoundImage(fragment, url, imageView, placeholder, errorRes, radius);
    }

    @Override
    public void displayBlurImage(Context context, String url, int blurRadius, IGetDrawableListener listener) {
        mGlideManager.displayBlurImage(context, url, blurRadius, listener);
    }

    public void displayBlurImage(Context context, String url, ImageView imageView, int blurRadius) {
        mGlideManager.displayBlurImage(context, url, imageView, blurRadius);
    }

    @Override
    public void displayBlurImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, int blurRadius) {
        mGlideManager.displayBlurImage(context, url, imageView, defRes, blurRadius);
    }

    public void displayBlurImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int blurRadius) {
        mGlideManager.displayBlurImage(context, url, imageView, placeholder, errorRes, blurRadius);
    }

    public void displayBlurImage(Activity activity, String url, ImageView imageView, int blurRadius) {
        mGlideManager.displayBlurImage(activity, url, imageView, blurRadius);
    }

    public void displayBlurImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, int blurRadius) {
        mGlideManager.displayBlurImage(activity, url, imageView, defRes, blurRadius);
    }

    public void displayBlurImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int blurRadius) {
        mGlideManager.displayBlurImage(activity, url, imageView, placeholder, errorRes, blurRadius);
    }

    @Override
    public void displayBlurImage(Context context, @DrawableRes int resId, ImageView imageView, int blurRadius) {
        mGlideManager.displayBlurImage(context, resId, imageView, blurRadius);
    }

    public void displayBlurImage(Activity activity, @DrawableRes int resId, ImageView imageView, int blurRadius) {
        mGlideManager.displayBlurImage(activity, resId, imageView, blurRadius);
    }

    public void displayBlurImage(Fragment fragment, @DrawableRes int resId, ImageView imageView, int blurRadius) {
        mGlideManager.displayBlurImage(fragment, resId, imageView, blurRadius);
    }

    public void displayBlurImage(Fragment fragment, String url, ImageView imageView, int blurRadius) {
        mGlideManager.displayBlurImage(fragment, url, imageView, blurRadius);
    }

    @Override
    public void displayBlurImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, int blurRadius) {
        mGlideManager.displayBlurImage(fragment, url, imageView, defRes, blurRadius);
    }

    public void displayBlurImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int blurRadius) {
        mGlideManager.displayBlurImage(fragment, url, imageView, placeholder, errorRes, blurRadius);
    }

    @Override
    public void displayImageInResource(Context context, @DrawableRes int resId, ImageView imageView) {
        mGlideManager.displayImageInResource(context, resId, imageView);
    }

    @Override
    public void displayImageInResource(Fragment fragment, @DrawableRes int resId, ImageView imageView) {
        mGlideManager.displayImageInResource(fragment, resId, imageView);
    }

    @Override
    public void displayImageInResource(Context context, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformations) {
        mGlideManager.displayImageInResource(context, resId, imageView, transformations);
    }

    @Override
    public void displayImageInResource(Fragment fragment, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformations) {
        mGlideManager.displayImageInResource(fragment, resId, imageView, transformations);
    }

    @Override
    public void displayImageInResource(Context context, @DrawableRes int resId, ImageView imageView, @DrawableRes int defRes) {
        mGlideManager.displayImageInResource(context, resId, imageView, defRes);
    }

    @Override
    public void displayImageInResource(Fragment fragment, @DrawableRes int resId, ImageView imageView, @DrawableRes int defRes) {
        mGlideManager.displayImageInResource(fragment, resId, imageView, defRes);
    }

    @Override
    public void displayImageInResource(Context context, @DrawableRes int resId, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mGlideManager.displayImageInResource(context, resId, imageView, defRes, transformations);
    }

    @Override
    public void displayImageInResource(Fragment fragment, @DrawableRes int resId, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mGlideManager.displayImageInResource(fragment, resId, imageView, defRes, transformations);
    }

    @Override
    public void displayImageInResourceTransform(Activity activity, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformation, @DrawableRes int errorResId) {
        mGlideManager.displayImageInResourceTransform(activity, resId, imageView, transformation, errorResId);
    }

    @Override
    public void displayImageInResourceTransform(Context context, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformation, @DrawableRes int errorResId) {
        mGlideManager.displayImageInResourceTransform(context, resId, imageView, transformation, errorResId);
    }

    @Override
    public void displayImageInResourceTransform(Fragment fragment, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformation, @DrawableRes int errorResId) {
        mGlideManager.displayImageInResourceTransform(fragment, resId, imageView, transformation, errorResId);
    }

    @Override
    public void displayImageByNet(Context context, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformation) {
        mGlideManager.displayImageByNet(context, url, imageView, defRes, transformation);
    }

    @Override
    public void displayImageByNet(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformation) {
        mGlideManager.displayImageByNet(fragment, url, imageView, defRes, transformation);
    }

    @Override
    public void displayImageByNet(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformation) {
        mGlideManager.displayImageByNet(activity, url, imageView, defRes, transformation);
    }

    @Override
    public void clear(Activity activity, ImageView imageView) {
        mGlideManager.clear(activity, imageView);
    }

    @Override
    public void clear(Context context, ImageView imageView) {
        mGlideManager.clear(context, imageView);
    }

    @Override
    public void clear(Fragment fragment, ImageView imageView) {
        mGlideManager.clear(fragment, imageView);
    }

    @Override
    public void displayImageByDiskCacheStrategy(Fragment fragment, String url, DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        mGlideManager.displayImageByDiskCacheStrategy(fragment, url, diskCacheStrategy, imageView);
    }

    @Override
    public void displayImageByDiskCacheStrategy(Activity activity, String url, DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        mGlideManager.displayImageByDiskCacheStrategy(activity, url, diskCacheStrategy, imageView);
    }

    @Override
    public void displayImageByDiskCacheStrategy(Context context, String url, DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        mGlideManager.displayImageByDiskCacheStrategy(context, url, diskCacheStrategy, imageView);
    }

    @Override
    public void displayImageOnlyRetrieveFromCache(Fragment fragment, String url, ImageView imageView) {
        mGlideManager.displayImageOnlyRetrieveFromCache(fragment, url, imageView);
    }

    @Override
    public void displayImageOnlyRetrieveFromCache(Activity activity, String url, ImageView imageView) {
        mGlideManager.displayImageOnlyRetrieveFromCache(activity, url, imageView);
    }

    @Override
    public void displayImageOnlyRetrieveFromCache(Context context, String url, ImageView imageView) {
        mGlideManager.displayImageOnlyRetrieveFromCache(context, url, imageView);
    }

    @Override
    public void displayImageSkipMemoryCache(Fragment fragment, String url, ImageView imageView, boolean skipflag, boolean diskCacheStratey) {
        mGlideManager.displayImageSkipMemoryCache(fragment, url, imageView, skipflag, diskCacheStratey);
    }

    @Override
    public void displayImageSkipMemoryCache(Activity activity, String url, ImageView imageView, boolean skipflag, boolean diskCacheStratey) {
        mGlideManager.displayImageSkipMemoryCache(activity, url, imageView, skipflag, diskCacheStratey);
    }

    @Override
    public void displayImageSkipMemoryCache(Context context, String url, ImageView imageView, boolean skipflag, boolean diskCacheStratey) {
        mGlideManager.displayImageSkipMemoryCache(context, url, imageView, skipflag, diskCacheStratey);
    }

    @Override
    public void displayImageErrorReload(Fragment fragment, String url, String fallbackUrl, ImageView imageView) {
        mGlideManager.displayImageErrorReload(fragment, url, fallbackUrl, imageView);
    }

    @Override
    public void displayImageErrorReload(Activity activity, String url, String fallbackUrl, ImageView imageView) {
        mGlideManager.displayImageErrorReload(activity, url, fallbackUrl, imageView);
    }

    @Override
    public void displayImageErrorReload(Context context, String url, String fallbackUrl, ImageView imageView) {
        mGlideManager.displayImageErrorReload(context, url, fallbackUrl, imageView);
    }

    @Override
    public void displayImageDisallowHardwareConfig(Fragment fragment, String url, ImageView imageView) {
        mGlideManager.displayImageDisallowHardwareConfig(fragment, url, imageView);
    }

    @Override
    public void displayImageDisallowHardwareConfig(Activity activity, String url, ImageView imageView) {
        mGlideManager.displayImageDisallowHardwareConfig(activity, url, imageView);
    }

    @Override
    public void displayImageDisallowHardwareConfig(Context context, String url, ImageView imageView) {
        mGlideManager.displayImageDisallowHardwareConfig(context, url, imageView);
    }

    @Override
    public void displayImageProgress(Context context, String url, ImageView imageView, @DrawableRes int placeholderResId, @DrawableRes int errorResId, ProgressListener listener) {
        mGlideManager.displayImageProgress(context, url, imageView, placeholderResId, errorResId, listener);
    }

    @Override
    public void displayImageProgress(Activity activity, String url, ImageView imageView, @DrawableRes int placeholderResId, @DrawableRes int errorResId, ProgressListener listener) {
        mGlideManager.displayImageProgress(activity, url, imageView, placeholderResId, errorResId, listener);
    }

    @Override
    public void displayImageProgress(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholderResId, @DrawableRes int errorResId, ProgressListener listener) {
        mGlideManager.displayImageProgress(fragment, url, imageView, placeholderResId, errorResId, listener);
    }

    @Override
    public void displayImageByTransition(Context context, String url, TransitionOptions<?, ?> transitionOptions, ImageView imageView) {
        mGlideManager.displayImageByTransition(context, url, transitionOptions, imageView);
    }

    @Override
    public void displayImageByTransition(Activity activity, String url, TransitionOptions<?, ?> transitionOptions, ImageView imageView) {
        mGlideManager.displayImageByTransition(activity, url, transitionOptions, imageView);
    }

    @Override
    public void displayImageByTransition(Fragment fragment, String url, TransitionOptions<?, ?> transitionOptions, ImageView imageView) {
        mGlideManager.displayImageByTransition(fragment, url, transitionOptions, imageView);
    }

    @Override
    public void glidePauseRequests(Context context) {
        mGlideManager.glidePauseRequests(context);
    }

    @Override
    public void glidePauseRequests(Activity activity) {
        mGlideManager.glidePauseRequests(activity);
    }

    @Override
    public void glidePauseRequests(Fragment fragment) {
        mGlideManager.glidePauseRequests(fragment);
    }

    @Override
    public void glideResumeRequests(Context context) {
        mGlideManager.glideResumeRequests(context);
    }

    @Override
    public void glideResumeRequests(Activity activity) {
        mGlideManager.glideResumeRequests(activity);
    }

    @Override
    public void glideResumeRequests(Fragment fragment) {
        mGlideManager.glideResumeRequests(fragment);
    }

    @Override
    public void displayImageThumbnail(Context context, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        mGlideManager.displayImageThumbnail(context, url, backUrl, thumbnailSize, imageView);
    }

    @Override
    public void displayImageThumbnail(Activity activity, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        mGlideManager.displayImageThumbnail(activity, url, backUrl, thumbnailSize, imageView);
    }

    @Override
    public void displayImageThumbnail(Fragment fragment, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        mGlideManager.displayImageThumbnail(fragment, url, backUrl, thumbnailSize, imageView);
    }

    @Override
    public void displayImageThumbnail(Fragment fragment, String url, float thumbnailSize, ImageView imageView) {
        mGlideManager.displayImageThumbnail(fragment, url, thumbnailSize, imageView);
    }

    @Override
    public void displayImageThumbnail(Activity activity, String url, float thumbnailSize, ImageView imageView) {
        mGlideManager.displayImageThumbnail(activity, url, thumbnailSize, imageView);
    }

    @Override
    public void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView) {
        mGlideManager.displayImageThumbnail(context, url, thumbnailSize, imageView);
    }
}
