package com.zhan.bottle.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhan.bottle.R;


/**
 * Created by zah on 2017/6/14.
 */

public class EmptyView extends RelativeLayout {
    TextView emptyView;
    Button reload;
    OnClickListener mListener;

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyView(Context context) {
        this(context, null);
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
        emptyView = (TextView) view.findViewById(R.id.empty_view);
        reload = (Button) view.findViewById(R.id.reload);
        addView(view, -1, -1);
    }

    public void setBtnListener(OnClickListener l) {
        reload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                if (mListener != null) {
                    mListener.onClick(v);
                }
            }
        });
        mListener = l;
    }

    public void showLoading() {
        emptyView.setText(getResources().getString(R.string.page_init));
        reload.setVisibility(GONE);
    }

    public void showEmpty() {
        emptyView.setText(getResources().getString(R.string.page_err));
        reload.setVisibility(VISIBLE);
    }

    public void showError() {
        emptyView.setText(getResources().getString(R.string.page_err));
        reload.setVisibility(VISIBLE);
    }
}
