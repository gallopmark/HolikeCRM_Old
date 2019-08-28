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

public class GlideLoader {

    private final Map<String, ProgressListener> LISTENER_MAP;
    private volatile static GlideLoader instance;
    private GlideImageLoaderClient mLoaderClient;

    private GlideLoader() {
        mLoaderClient = new GlideImageLoaderClient();
        LISTENER_MAP = new HashMap<>();
    }

    public static GlideLoader getInstance() {
        if (instance == null) {
            synchronized (GlideLoader.class) {
                if (instance == null) {
                    instance = new GlideLoader();
                }
            }
        }
        return instance;
    }

    public Map<String, ProgressListener> listenerMap() {
        return LISTENER_MAP;
    }

    public void addListener(String url, ProgressListener listener) {
        listenerMap().put(url, listener);
    }

    public ProgressListener getListener(String url) {
        return listenerMap().get(url);
    }

    public void removeListener(String url) {
        listenerMap().remove(url);
    }

    public void destroy(Context context) {
        clearMemoryCache(context);
    }

    public File getCacheDir(Context context) {
        return mLoaderClient.getCacheDir(context);
    }

    public void clearMemoryCache(Context context) {
        mLoaderClient.clearMemoryCache(context);
    }

    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            public void run() {
                mLoaderClient.clearDiskCache(context);
            }
        }).start();
    }

    public Bitmap getBitmapFromCache(Context context, String url) {
        throw new UnsupportedOperationException("glide 不支持同步 获取缓存中 bitmap");
    }

    public void getBitmapFromCache(Context context, String url, final IGetBitmapListener listener) {
        mLoaderClient.getBitmapFromCache(context, url, listener);
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

    public void displayImage(Context context, String url, ImageView imageView) {
        mLoaderClient.displayImage(context, url, imageView);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView) {
        mLoaderClient.displayImage(fragment, url, imageView);
    }

    public void displayImage(Activity activity, String url, ImageView imageView) {
        mLoaderClient.displayImage(activity, url, imageView);
    }

    public void displayImage(Context context, String url, ImageView imageView, boolean isCache) {
        mLoaderClient.displayImage(context, url, imageView, isCache);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes) {
        mLoaderClient.displayImage(context, url, imageView, defRes);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mLoaderClient.displayImage(context, url, imageView, placeholder, errorRes);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes) {
        mLoaderClient.displayImage(activity, url, imageView, defRes);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mLoaderClient.displayImage(activity, url, imageView, placeholder, errorRes);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes) {
        mLoaderClient.displayImage(fragment, url, imageView, defRes);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mLoaderClient.displayImage(fragment, url, imageView, placeholder, errorRes);
    }

    public void displayImage(Context context, String url, ImageView imageView, BitmapTransformation transformations) {
        mLoaderClient.displayImage(context, url, imageView, transformations);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mLoaderClient.displayImage(activity, url, imageView, defRes, transformations);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, BitmapTransformation transformations) {
        mLoaderClient.displayImage(activity, url, imageView, placeholder, errorRes, transformations);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mLoaderClient.displayImage(context, url, imageView, defRes, transformations);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, BitmapTransformation transformations) {
        mLoaderClient.displayImage(context, url, imageView, placeholder, errorRes, transformations);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, BitmapTransformation transformations) {
        mLoaderClient.displayImage(fragment, url, imageView, transformations);
    }


    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mLoaderClient.displayImage(fragment, url, imageView, defRes, transformations);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, BitmapTransformation transformations) {
        mLoaderClient.displayImage(fragment, url, imageView, placeholder, errorRes, transformations);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, ImageSize size) {
        mLoaderClient.displayImage(activity, url, imageView, size);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, ImageSize size) {
        mLoaderClient.displayImage(activity, url, imageView, defRes, size);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, ImageSize size) {
        mLoaderClient.displayImage(activity, url, imageView, placeholder, errorRes, size);
    }


    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, ImageSize size) {
        mLoaderClient.displayImage(context, url, imageView, defRes, size);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, ImageSize size) {
        mLoaderClient.displayImage(context, url, imageView, placeholder, errorRes, size);
    }


    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, ImageSize size) {
        mLoaderClient.displayImage(fragment, url, imageView, defRes, size);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, ImageSize size) {
        mLoaderClient.displayImage(fragment, url, imageView, placeholder, errorRes, size);
    }

    public void displayImage(Context context, String url, ImageView imageView, ImageSize size) {
        mLoaderClient.displayImage(context, url, imageView, size);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, ImageSize size) {
        mLoaderClient.displayImage(fragment, url, imageView, size);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, boolean cacheInMemory) {
        mLoaderClient.displayImage(context, url, imageView, defRes, cacheInMemory);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, boolean cacheInMemory) {
        mLoaderClient.displayImage(context, url, imageView, placeholder, errorRes, cacheInMemory);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, boolean cacheInMemory) {
        mLoaderClient.displayImage(fragment, url, imageView, defRes, cacheInMemory);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, boolean cacheInMemory) {
        mLoaderClient.displayImage(fragment, url, imageView, placeholder, errorRes, cacheInMemory);
    }

    public void displayImage(Context context, String url, ImageView imageView, IImageLoaderListener listener) {
        mLoaderClient.displayImage(context, url, imageView, listener);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, IImageLoaderListener listener) {
        mLoaderClient.displayImage(activity, url, imageView, listener);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, IImageLoaderListener listener) {
        mLoaderClient.displayImage(fragment, url, imageView, listener);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, IImageLoaderListener listener) {
        mLoaderClient.displayImage(context, url, imageView, defRes, listener);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, IImageLoaderListener listener) {
        mLoaderClient.displayImage(activity, url, imageView, defRes, listener);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, IImageLoaderListener listener) {
        mLoaderClient.displayImage(fragment, url, imageView, defRes, listener);
    }

    public void displayImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, IImageLoaderListener listener) {
        mLoaderClient.displayImage(context, url, imageView, placeholder, errorRes, listener);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, IImageLoaderListener listener) {
        mLoaderClient.displayImage(activity, url, imageView, placeholder, errorRes, listener);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, IImageLoaderListener listener) {
        mLoaderClient.displayImage(fragment, url, imageView, placeholder, errorRes, listener);
    }

    public void displayCircleImage(Context context, String url, ImageView imageView) {
        mLoaderClient.displayCircleImage(context, url, imageView);
    }

    public void displayCircleImage(Context context, String url, ImageView imageView, @DrawableRes int defRes) {
        mLoaderClient.displayCircleImage(context, url, imageView, defRes);
    }

    public void displayCircleImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mLoaderClient.displayCircleImage(context, url, imageView, placeholder, errorRes);
    }

    public void displayCircleImage(Activity activity, String url, ImageView imageView) {
        mLoaderClient.displayCircleImage(activity, url, imageView);
    }

    public void displayCircleImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes) {
        mLoaderClient.displayCircleImage(activity, url, imageView, defRes);
    }

    public void displayCircleImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mLoaderClient.displayCircleImage(activity, url, imageView, placeholder, errorRes);
    }

    public void displayCircleImage(Fragment fragment, String url, ImageView imageView) {
        mLoaderClient.displayCircleImage(fragment, url, imageView);
    }

    public void displayCircleImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes) {
        mLoaderClient.displayCircleImage(fragment, url, imageView, defRes);
    }

    public void displayCircleImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        mLoaderClient.displayCircleImage(fragment, url, imageView, placeholder, errorRes);
    }

    public void displayRoundImage(Context context, String url, ImageView imageView, int radius) {
        mLoaderClient.displayRoundImage(context, url, imageView, radius);
    }

    public void displayRoundImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, int radius) {
        mLoaderClient.displayRoundImage(context, url, imageView, defRes, radius);
    }

    public void displayRoundImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int radius) {
        mLoaderClient.displayRoundImage(context, url, imageView, placeholder, errorRes, radius);
    }

    public void displayRoundImage(Activity activity, String url, ImageView imageView, int radius) {
        mLoaderClient.displayRoundImage(activity, url, imageView, radius);
    }

    public void displayRoundImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, int radius) {
        mLoaderClient.displayRoundImage(activity, url, imageView, defRes, radius);
    }

    public void displayRoundImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int radius) {
        mLoaderClient.displayRoundImage(activity, url, imageView, placeholder, errorRes, radius);
    }

    public void displayRoundImage(Fragment fragment, String url, ImageView imageView, int radius) {
        mLoaderClient.displayRoundImage(fragment, url, imageView, radius);
    }

    public void displayRoundImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, int radius) {
        mLoaderClient.displayRoundImage(fragment, url, imageView, defRes, radius);
    }

    public void displayRoundImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int radius) {
        mLoaderClient.displayRoundImage(fragment, url, imageView, placeholder, errorRes, radius);
    }

    public void displayBlurImage(Context context, String url, int blurRadius, IGetDrawableListener listener) {
        mLoaderClient.displayBlurImage(context, url, blurRadius, listener);
    }

    public void displayBlurImage(Context context, String url, ImageView imageView, int blurRadius) {
        mLoaderClient.displayBlurImage(context, url, imageView, blurRadius);
    }


    public void displayBlurImage(Context context, String url, ImageView imageView, @DrawableRes int defRes, int blurRadius) {
        mLoaderClient.displayBlurImage(context, url, imageView, defRes, blurRadius);
    }

    public void displayBlurImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int blurRadius) {
        mLoaderClient.displayBlurImage(context, url, imageView, placeholder, errorRes, blurRadius);
    }

    public void displayBlurImage(Activity activity, String url, ImageView imageView, int blurRadius) {
        mLoaderClient.displayBlurImage(activity, url, imageView, blurRadius);
    }

    public void displayBlurImage(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, int blurRadius) {
        mLoaderClient.displayBlurImage(activity, url, imageView, defRes, blurRadius);
    }

    public void displayBlurImage(Activity activity, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int blurRadius) {
        mLoaderClient.displayBlurImage(activity, url, imageView, placeholder, errorRes, blurRadius);
    }

    public void displayBlurImage(Context context, @DrawableRes int resId, ImageView imageView, int blurRadius) {
        mLoaderClient.displayBlurImage(context, resId, imageView, blurRadius);
    }

    public void displayBlurImage(Activity activity, @DrawableRes int resId, ImageView imageView, int blurRadius) {
        mLoaderClient.displayBlurImage(activity, resId, imageView, blurRadius);
    }

    public void displayBlurImage(Fragment fragment, @DrawableRes int resId, ImageView imageView, int blurRadius) {
        mLoaderClient.displayBlurImage(fragment, resId, imageView, blurRadius);
    }

    public void displayBlurImage(Fragment fragment, String url, ImageView imageView, int blurRadius) {
        mLoaderClient.displayBlurImage(fragment, url, imageView, blurRadius);
    }

    public void displayBlurImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, int blurRadius) {
        mLoaderClient.displayBlurImage(fragment, url, imageView, defRes, blurRadius);
    }

    public void displayBlurImage(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int blurRadius) {
        mLoaderClient.displayBlurImage(fragment, url, imageView, placeholder, errorRes, blurRadius);
    }

    public void displayImageInResource(Context context, @DrawableRes int resId, ImageView imageView) {
        mLoaderClient.displayImageInResource(context, resId, imageView);
    }

    public void displayImageInResource(Fragment fragment, @DrawableRes int resId, ImageView imageView) {
        mLoaderClient.displayImageInResource(fragment, resId, imageView);
    }

    public void displayImageInResource(Context context, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformations) {
        mLoaderClient.displayImageInResource(context, resId, imageView, transformations);
    }

    public void displayImageInResource(Fragment fragment, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformations) {
        mLoaderClient.displayImageInResource(fragment, resId, imageView, transformations);
    }

    public void displayImageInResource(Context context, @DrawableRes int resId, ImageView imageView, @DrawableRes int defRes) {
        mLoaderClient.displayImageInResource(context, resId, imageView, defRes);
    }

    public void displayImageInResource(Fragment fragment, @DrawableRes int resId, ImageView imageView, @DrawableRes int defRes) {
        mLoaderClient.displayImageInResource(fragment, resId, imageView, defRes);
    }

    public void displayImageInResource(Context context, @DrawableRes int resId, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mLoaderClient.displayImageInResource(context, resId, imageView, defRes, transformations);
    }

    public void displayImageInResource(Fragment fragment, @DrawableRes int resId, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        mLoaderClient.displayImageInResource(fragment, resId, imageView, defRes, transformations);
    }

    public void displayImageInResourceTransform(Activity activity, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformation, @DrawableRes int errorResId) {
        mLoaderClient.displayImageInResourceTransform(activity, resId, imageView, transformation, errorResId);
    }

    public void displayImageInResourceTransform(Context context, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformation, @DrawableRes int errorResId) {
        mLoaderClient.displayImageInResourceTransform(context, resId, imageView, transformation, errorResId);
    }

    public void displayImageInResourceTransform(Fragment fragment, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformation, @DrawableRes int errorResId) {
        mLoaderClient.displayImageInResourceTransform(fragment, resId, imageView, transformation, errorResId);
    }

    public void displayImageByNet(Context context, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformation) {
        mLoaderClient.displayImageByNet(context, url, imageView, defRes, transformation);
    }

    public void displayImageByNet(Fragment fragment, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformation) {
        mLoaderClient.displayImageByNet(fragment, url, imageView, defRes, transformation);
    }

    public void displayImageByNet(Activity activity, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformation) {
        mLoaderClient.displayImageByNet(activity, url, imageView, defRes, transformation);
    }

    public void clear(Activity activity, ImageView imageView) {
        mLoaderClient.clear(activity, imageView);
    }

    public void clear(Context context, ImageView imageView) {
        mLoaderClient.clear(context, imageView);
    }

    public void clear(Fragment fragment, ImageView imageView) {
        mLoaderClient.clear(fragment, imageView);
    }

    public void displayImageByDiskCacheStrategy(Fragment fragment, String url, DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        mLoaderClient.displayImageByDiskCacheStrategy(fragment, url, diskCacheStrategy, imageView);
    }

    public void displayImageByDiskCacheStrategy(Activity activity, String url, DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        mLoaderClient.displayImageByDiskCacheStrategy(activity, url, diskCacheStrategy, imageView);
    }

    public void displayImageByDiskCacheStrategy(Context context, String url, DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        mLoaderClient.displayImageByDiskCacheStrategy(context, url, diskCacheStrategy, imageView);
    }

    public void displayImageOnlyRetrieveFromCache(Fragment fragment, String url, ImageView imageView) {
        mLoaderClient.displayImageOnlyRetrieveFromCache(fragment, url, imageView);
    }

    public void displayImageOnlyRetrieveFromCache(Activity activity, String url, ImageView imageView) {
        mLoaderClient.displayImageOnlyRetrieveFromCache(activity, url, imageView);
    }

    public void displayImageOnlyRetrieveFromCache(Context context, String url, ImageView imageView) {
        mLoaderClient.displayImageOnlyRetrieveFromCache(context, url, imageView);
    }

    public void displayImageSkipMemoryCache(Fragment fragment, String url, ImageView imageView, boolean skipflag, boolean diskCacheStratey) {
        mLoaderClient.displayImageSkipMemoryCache(fragment, url, imageView, skipflag, diskCacheStratey);
    }

    public void displayImageSkipMemoryCache(Activity activity, String url, ImageView imageView, boolean skipflag, boolean diskCacheStratey) {
        mLoaderClient.displayImageSkipMemoryCache(activity, url, imageView, skipflag, diskCacheStratey);
    }

    public void displayImageSkipMemoryCache(Context context, String url, ImageView imageView, boolean skipflag, boolean diskCacheStratey) {
        mLoaderClient.displayImageSkipMemoryCache(context, url, imageView, skipflag, diskCacheStratey);
    }

    public void displayImageErrorReload(Fragment fragment, String url, String fallbackUrl, ImageView imageView) {
        mLoaderClient.displayImageErrorReload(fragment, url, fallbackUrl, imageView);
    }

    public void displayImageErrorReload(Activity activity, String url, String fallbackUrl, ImageView imageView) {
        mLoaderClient.displayImageErrorReload(activity, url, fallbackUrl, imageView);
    }

    public void displayImageErrorReload(Context context, String url, String fallbackUrl, ImageView imageView) {
        mLoaderClient.displayImageErrorReload(context, url, fallbackUrl, imageView);
    }

    public void displayImageDisallowHardwareConfig(Fragment fragment, String url, ImageView imageView) {
        mLoaderClient.displayImageDisallowHardwareConfig(fragment, url, imageView);
    }

    public void displayImageDisallowHardwareConfig(Activity activity, String url, ImageView imageView) {
        mLoaderClient.displayImageDisallowHardwareConfig(activity, url, imageView);
    }

    public void displayImageDisallowHardwareConfig(Context context, String url, ImageView imageView) {
        mLoaderClient.displayImageDisallowHardwareConfig(context, url, imageView);
    }

    public void displayImageProgress(Context context, String url, ImageView imageView, @DrawableRes int placeholderResId, @DrawableRes int errorResId, ProgressListener listener) {
        mLoaderClient.displayImageProgress(context, url, imageView, placeholderResId, errorResId, listener);
    }

    public void displayImageProgress(Activity activity, String url, ImageView imageView, @DrawableRes int placeholderResId, @DrawableRes int errorResId, ProgressListener listener) {
        mLoaderClient.displayImageProgress(activity, url, imageView, placeholderResId, errorResId, listener);
    }

    public void displayImageProgress(Fragment fragment, String url, ImageView imageView, @DrawableRes int placeholderResId, @DrawableRes int errorResId, ProgressListener listener) {
        mLoaderClient.displayImageProgress(fragment, url, imageView, placeholderResId, errorResId, listener);
    }

    public void displayImageByTransition(Context context, String url, TransitionOptions<?, ?> transitionOptions, ImageView imageView) {
        mLoaderClient.displayImageByTransition(context, url, transitionOptions, imageView);
    }

    public void displayImageByTransition(Activity activity, String url, TransitionOptions<?, ?> transitionOptions, ImageView imageView) {
        mLoaderClient.displayImageByTransition(activity, url, transitionOptions, imageView);
    }


    public void displayImageByTransition(Fragment fragment, String url, TransitionOptions<?, ?> transitionOptions, ImageView imageView) {
        mLoaderClient.displayImageByTransition(fragment, url, transitionOptions, imageView);
    }

    public void glidePauseRequests(Context context) {
        mLoaderClient.glidePauseRequests(context);
    }

    public void glidePauseRequests(Activity activity) {
        mLoaderClient.glidePauseRequests(activity);
    }

    public void glidePauseRequests(Fragment fragment) {
        mLoaderClient.glidePauseRequests(fragment);
    }


    public void glideResumeRequests(Context context) {
        mLoaderClient.glideResumeRequests(context);
    }

    public void glideResumeRequests(Activity activity) {
        mLoaderClient.glideResumeRequests(activity);
    }

    public void glideResumeRequests(Fragment fragment) {
        mLoaderClient.glideResumeRequests(fragment);
    }

    public void displayImageThumbnail(Context context, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        mLoaderClient.displayImageThumbnail(context, url, backUrl, thumbnailSize, imageView);
    }

    public void displayImageThumbnail(Activity activity, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        mLoaderClient.displayImageThumbnail(activity, url, backUrl, thumbnailSize, imageView);
    }

    public void displayImageThumbnail(Fragment fragment, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        mLoaderClient.displayImageThumbnail(fragment, url, backUrl, thumbnailSize, imageView);
    }

    public void displayImageThumbnail(Fragment fragment, String url, float thumbnailSize, ImageView imageView) {
        mLoaderClient.displayImageThumbnail(fragment, url, thumbnailSize, imageView);
    }

    public void displayImageThumbnail(Activity activity, String url, float thumbnailSize, ImageView imageView) {
        mLoaderClient.displayImageThumbnail(activity, url, thumbnailSize, imageView);
    }

    public void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView) {
        mLoaderClient.displayImageThumbnail(context, url, thumbnailSize, imageView);
    }
}
