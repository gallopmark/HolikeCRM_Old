package com.holike.crm.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class InheritedFakeFragment extends Fragment {

    protected Activity mActivity;
    protected FrameLayout rootContainer;

    private boolean isLazyViewCreated = false;
    private boolean isLazyLoaded = false;
    private LayoutInflater mInflater;
    private Bundle savedInstanceState;
    protected Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        this.savedInstanceState = savedInstanceState;
        if (rootContainer == null) {
            rootContainer = new FrameLayout(mActivity);
            rootContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        return rootContainer;
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getUserVisibleHint() && mInflater != null && !isLazyViewCreated) {
            onLazyInit();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isLazyViewCreated && mInflater != null) {
            onLazyInit();
        }
    }

    private void onLazyInit() {
        addLazyView();
        onLazyLoad();
    }

    private void addLazyView() {
        View view = onLazyCreateView(mInflater, rootContainer, savedInstanceState);
        if (rootContainer.getChildCount() > 0) rootContainer.removeAllViews();
        rootContainer.addView(view);
        unbinder = ButterKnife.bind(this, rootContainer);
        onLazyViewCreated(rootContainer, savedInstanceState);
        isLazyViewCreated = true;
    }

    private void onLazyLoad() {
        if (!isLazyLoaded) {
            onLoadData();
            isLazyLoaded = true;
        }
    }

    /**
     * 获取真实的fragment是否已经初始化view
     *
     * @return 已经初始化view返回true，否则返回false
     */
    @SuppressWarnings("unused")
    public boolean isLazyViewCreated() {
        return isLazyViewCreated;
    }

    @Override
    public void onDestroyView() {
        isLazyViewCreated = false;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    /**
     * 用于替代真实Fragment的onCreateView，在真正获取到用户焦点后才会调用
     *
     * @param inflater           - The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          - If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState - If non-null, this fragment is being re-constructed makeText a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    protected abstract View onLazyCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 用来代替真实Fragment的onViewCreated，在真正获得用户焦点并且{#onLazyViewCreated(View, Bundle)}
     *
     * @param view               - The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState - If non-null, this fragment is being re-constructed makeText a previous saved state as given here.
     */
    protected abstract void onLazyViewCreated(View view, @Nullable Bundle savedInstanceState);

    /**
     * 用于异步加载数据，只加载一次数据
     */
    protected void onLoadData() {

    }
}
