package com.zhan.bottle.ui.fragments.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhan.bottle.R;
import com.zhan.bottle.ui.base.LazyFragment;

/**
 * Created by zah on 2017/6/27.
 */

public class TabFoundFragment extends LazyFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_found, container, false);
    }
}
