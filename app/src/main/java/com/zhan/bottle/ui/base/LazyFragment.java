package com.zhan.bottle.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by zah on 2017/6/27.
 */

public class LazyFragment extends BaseFragment {
    protected boolean mHasLoadData = false;
    protected boolean isVisible = false;
    protected boolean isCreated = false;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        isCreated = true;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isVisible && isCreated && !mHasLoadData) {
            mHasLoadData = true;
            lazyLoad();
        }
    }

    protected void onVisible() {
        if (isVisible && isCreated && !mHasLoadData) {
            mHasLoadData = true;
            lazyLoad();
        }
    }

    protected void onDisVisible() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            onVisible();
        } else {
            onDisVisible();
        }
    }

    /**
     * 初始化数据组件，类似list,adapter等，只执行一次
     */
    protected void initComponent() {

    }

    /**
     * 在fragment 可见的时候执行，只执行一次
     */
    protected void lazyLoad() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
