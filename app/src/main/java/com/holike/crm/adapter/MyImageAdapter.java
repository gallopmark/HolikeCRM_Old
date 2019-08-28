package com.holike.crm.adapter;

import android.app.Activity;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;


/**
 * Created by wqj on 2018/3/29.
 * 图片查看器适配器
 */

public class MyImageAdapter extends PagerAdapter {
    private List<String> imageUrls;
    private Activity activity;
    private PotoClickListener listener;

    public MyImageAdapter(List<String> imageUrls, Activity activity, PotoClickListener listener) {
        this.imageUrls = imageUrls;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls.get(position);
        PhotoView photoView = new PhotoView(activity);
        Glide.with(activity).load(url).into(photoView);
        container.addView(photoView);
        photoView.setOnPhotoTapListener((view, x, y) -> {
           listener.onPhotoClick(view);
        });
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface PotoClickListener {
        void onPhotoClick(View v);
    }
}
