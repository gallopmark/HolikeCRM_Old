package com.holike.crm.activity.main;

import android.app.Activity;
import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.holike.crm.R;
import com.holike.crm.adapter.MyImageAdapter;
import com.holike.crm.base.BaseActivity;
import com.holike.crm.base.BasePresenter;
import com.holike.crm.customView.PhotoViewPager;
import com.holike.crm.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片查看器
 */
public class PhotoViewActivity extends BaseActivity implements MyImageAdapter.PotoClickListener {

    private PhotoViewPager mViewPager;
    private int currentPosition;
    private MyImageAdapter adapter;
    private TextView mTvImageCount;
    private List<String> Urls;

    @Override
    protected BasePresenter attachPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager_photo);
        mTvImageCount = (TextView) findViewById(R.id.tv_image_count);
    }

    /**
     * 初始化图片
     */
    private void initData() {
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra(Constants.PHOTO_VIEW_POSITION, 0);
        Urls = intent.getStringArrayListExtra(Constants.PHONE_VIEW_IMAGES);
        Urls.remove("add");

        adapter = new MyImageAdapter(Urls, this, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
            }
        });
    }

    /**
     * 查看图片
     *
     * @param activity
     * @param currentPosition
     * @param Urls
     */
    public static void openImage(Activity activity, int currentPosition, List<String> Urls) {
        Intent intent = new Intent(activity, PhotoViewActivity.class);
        intent.putExtra(Constants.PHOTO_VIEW_POSITION, currentPosition);
        intent.putStringArrayListExtra(Constants.PHONE_VIEW_IMAGES, (ArrayList<String>) Urls);
        activity.startActivity(intent);
    }

    @Override
    public void onPhotoClick(View v) {
        finish();
    }
}
