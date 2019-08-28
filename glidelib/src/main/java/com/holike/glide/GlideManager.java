package com.holike.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.holike.glide.listener.IGetBitmapListener;
import com.holike.glide.listener.IGetDrawableListener;
import com.holike.glide.listener.IImageLoaderListener;
import com.holike.glide.listener.ProgressListener;
import com.holike.glide.tranform.BlurBitmapTranformation;

import java.io.File;

final class GlideManager {

    private GlideManager() {
    }

    public static GlideManager newInstance() {
        return new GlideManager();
    }

    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    public void clearDiskCache(Context context) {
        GlideApp.get(context).clearDiskCache();
    }

    public File getCacheDir(Context context) {
        return GlideApp.getPhotoCacheDir(context);
    }

    @SuppressLint("CheckResult")
    public void getBitmapFromCache(Context context, String url, final IGetBitmapListener listener) {
        GlideApp.with(context).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                listener.onBitmap(resource);
                return false;
            }
        });
    }

    public GlideRequests withGlideRequests(Object obj) {
        GlideRequests manager = null;
        if (obj instanceof View) {
            manager = GlideApp.with((View) obj);
        } else if (obj instanceof Context) {
            if (obj instanceof FragmentActivity) {
                manager = GlideApp.with((FragmentActivity) obj);
            } else if (obj instanceof Activity) {
                manager = GlideApp.with((Activity) obj);
            } else {
                manager = GlideApp.with((Context) obj);
            }
        } else if (obj instanceof Fragment) {
            manager = GlideApp.with((Fragment) obj);
        }
        return manager;
    }

    public void displayImage(Object object, String url, ImageView imageView) {
        displayImage(object, url, imageView, 0);
    }

    public void displayImage(Object object, String url, ImageView imageView, @DrawableRes int defRes) {
        displayImage(object, url, imageView, defRes, defRes);
    }

    public void displayImage(Object object, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(buildOptions(placeholder, errorRes)).into(imageView);
    }

    private RequestOptions buildOptions(@DrawableRes int placeholder, @DrawableRes int errorRes) {
        RequestOptions option = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        return option.placeholder(placeholder).error(errorRes);
    }

    public void displayImage(Object object, String url, ImageView imageView, BitmapTransformation transformations) {
        displayImage(object, url, imageView, 0, transformations);
    }

    public void displayImage(Object object, String url, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        displayImage(object, url, imageView, defRes, defRes, transformations);
    }

    public void displayImage(Object object, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, BitmapTransformation transformations) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(placeholder)
                .error(errorRes)
                .transform(transformations)).into(imageView);
    }

    public void displayImage(Object object, String url, ImageView imageView, ImageSize size) {
        displayImage(object, url, imageView, 0, size);
    }

    public void displayImage(Object object, String url, ImageView imageView, @DrawableRes int defRes, ImageSize size) {
        displayImage(object, url, imageView, defRes, defRes, size);
    }

    public void displayImage(Object object, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, ImageSize size) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(buildOptions(placeholder, errorRes)
                .override(size.getWidth(), size.getHeight()))
                .into(imageView);
    }

    public void displayImage(Object object, String url, ImageView imageView, boolean cacheInMemory) {
        displayImage(object, url, imageView, 0, cacheInMemory);
    }

    public void displayImage(Object object, String url, ImageView imageView, @DrawableRes int defRes, boolean cacheInMemory) {
        displayImage(object, url, imageView, defRes, defRes, cacheInMemory);
    }

    public void displayImage(Object object, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes,
                             boolean cacheInMemory) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(placeholder)
                .error(errorRes)
                .skipMemoryCache(cacheInMemory))
                .into(imageView);
    }


    public void displayImage(Object object, String url, ImageView imageView, IImageLoaderListener listener) {
        displayImage(object, url, imageView, 0, listener);
    }

    public void displayImage(Object object, String url, ImageView imageView, @DrawableRes int defRes, IImageLoaderListener listener) {
        displayImage(object, url, imageView, defRes, defRes, listener);
    }

    @SuppressLint("CheckResult")
    public void displayImage(Object object, final String url, final ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, final IImageLoaderListener listener) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(buildOptions(placeholder, errorRes))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        listener.onLoadingFailed(url, imageView, e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.onLoadingComplete(url, imageView);
                        return false;
                    }
                });
    }

    public void displayCircleImage(Object object, String url, ImageView imageView) {
        displayCircleImage(object, url, imageView, 0);
    }

    public void displayCircleImage(Object object, String url, ImageView imageView, @DrawableRes int defRes) {
        displayCircleImage(object, url, imageView, defRes, defRes);
    }

    public void displayCircleImage(Object object, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(placeholder)
                .error(errorRes)
                .transform(new CircleCrop()))
                .into(imageView);
    }

    public void displayRoundImage(Object object, String url, ImageView imageView, int radius) {
        displayRoundImage(object, url, imageView, 0, radius);
    }

    public void displayRoundImage(Object object, String url, ImageView imageView, @DrawableRes int defRes, int radius) {
        displayRoundImage(object, url, imageView, defRes, defRes, radius);
    }

    public void displayRoundImage(Object object, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int radius) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(placeholder)
                .error(errorRes)
                .transform(new RoundedCorners(radius)))
                .into(imageView);
    }

    @SuppressLint("CheckResult")
    public void displayBlurImage(Object object, String url, int blurRadius, final IGetDrawableListener listener) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).transform(new BlurBitmapTranformation(blurRadius)))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.onDrawable(resource);
                        return false;
                    }
                });
    }

    public void displayBlurImage(Object object, @DrawableRes int resId, ImageView imageView, int blurRadius) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(resId).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(resId)
                .error(resId)
                .transform(new BlurBitmapTranformation(blurRadius)))
                .into(imageView);
    }

    public void displayBlurImage(Object object, String url, ImageView imageView, int blurRadius) {
        displayBlurImage(object, url, imageView, 0, blurRadius);
    }

    public void displayBlurImage(Object object, String url, ImageView imageView, @DrawableRes int defRes, int blurRadius) {
        displayBlurImage(object, url, imageView, defRes, defRes, blurRadius);
    }

    public void displayBlurImage(Object object, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int errorRes, int blurRadius) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(placeholder)
                .error(errorRes)
                .transform(new BlurBitmapTranformation(blurRadius)))
                .into(imageView);
    }

    public void displayImageInResource(Object object, @DrawableRes int resId, ImageView imageView) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(resId).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(imageView);
    }

    public void displayImageInResource(Object object, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformations) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(resId).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(transformations))
                .into(imageView);
    }

    public void displayImageInResource(Object object, @DrawableRes int resId, ImageView imageView, int defRes) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(resId).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(defRes).error(defRes))
                .into(imageView);
    }

    public void displayImageInResource(Object object, @DrawableRes int resId, ImageView imageView, @DrawableRes int defRes, BitmapTransformation transformations) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(resId).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(defRes).error(defRes)
                .transform(transformations))
                .into(imageView);
    }

    public void displayImageInResourceTransform(Object object, @DrawableRes int resId, ImageView imageView, BitmapTransformation transformation, @DrawableRes int errorResId) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(resId).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(errorResId)
                .error(errorResId)
                .transform(transformation))
                .into(imageView);
    }

    public void displayImageByNet(Object object, String url, ImageView imageView, int defRes, BitmapTransformation transformation) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(defRes)
                .error(defRes)
                .transform(transformation))
                .into(imageView);
    }

    public void displayImageByDiskCacheStrategy(Object object, String url, DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(new RequestOptions().diskCacheStrategy(diskCacheStrategy)).into(imageView);
    }

    public void displayImageOnlyRetrieveFromCache(Object object, String url, ImageView imageView) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(new RequestOptions().onlyRetrieveFromCache(true)).into(imageView);
    }

    public void displayImageSkipMemoryCache(Object object, String url, ImageView imageView, boolean skipFlag, boolean diskCacheStrategy) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        DiskCacheStrategy strategy;
        if (diskCacheStrategy) {
            strategy = DiskCacheStrategy.AUTOMATIC;
        } else {
            strategy = DiskCacheStrategy.NONE;
        }
        manager.load(url).apply(new RequestOptions()
                .diskCacheStrategy(strategy)
                .skipMemoryCache(skipFlag))
                .into(imageView);
    }

    public void displayImageErrorReload(Object object, String url, String fallbackUrl, ImageView imageView) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).error(manager.load(fallbackUrl)).into(imageView);
    }

    public void displayImageDisallowHardwareConfig(Object object, String url, ImageView imageView) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).apply(new RequestOptions().disallowHardwareConfig()).into(imageView);
    }

    public void displayImageByTransition(Object object, String url, TransitionOptions<?, ?> transitionOptions, ImageView imageView) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        if (transitionOptions instanceof DrawableTransitionOptions) {
            manager.load(url)
                    .transition((DrawableTransitionOptions) transitionOptions)
                    .into(imageView);
        } else {
            manager.asBitmap()
                    .load(url)
                    .transition((BitmapTransitionOptions) transitionOptions)
                    .into(imageView);
        }
    }

    public void displayImageThumbnail(Object object, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        if (thumbnailSize == 0) {
            manager.load(url).thumbnail(manager.load(backUrl)).into(imageView);
        } else {
            //越小，图片越小，低网络的情况，图片越小
            // API 来强制 Glide 在缩略图请求中加载一个低分辨率图像
            manager.load(url).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .thumbnail(manager.load(backUrl).apply(new RequestOptions().override(thumbnailSize)))
                    .into(imageView);
        }
    }

    public void displayImageThumbnail(Object object, String url, @FloatRange(from = 0.0f, to = 1.0f) float thumbnailSize, ImageView imageView) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.load(url).thumbnail(thumbnailSize).into(imageView);
    }

    public void displayImageProgress(Object object, final String url, ImageView imageView, @DrawableRes int placeholderResId, @DrawableRes int errorResId, ProgressListener listener) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        GlideLoader.getInstance().addListener(url, listener);
        manager.load(url).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(placeholderResId)
                .error(errorResId))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        GlideLoader.getInstance().removeListener(url);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        GlideLoader.getInstance().removeListener(url);
                        return false;
                    }
                }).into(imageView);
    }

    public void clear(Object object, ImageView imageView) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.clear(imageView);
    }

    public void glidePauseRequests(Object object) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.pauseRequests();
    }

    public void glideResumeRequests(Object object) {
        GlideRequests manager = withGlideRequests(object);
        if (manager == null) return;
        manager.resumeRequests();
    }
}
