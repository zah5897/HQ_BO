package com.zhan.bottle.ui.activitys;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhan.bottle.R;
import com.zhan.bottle.ui.base.BaseActivity;
import com.zhan.bottle.ui.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zah on 2016/11/24.
 */

public class UserInfoActivity extends BaseSwipeBackActivity {
    @BindView(R.id.gallery)
    ViewPager gallery;
    @BindView(R.id.last_location)
    TextView lastLocation;
    @BindView(R.id.last_login)
    TextView lastLogin;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.singule)
    TextView singule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        gallery.setLayoutParams(new RelativeLayout.LayoutParams(-1, getResources().getDisplayMetrics().widthPixels));
    }

    @OnClick({R.id.back, R.id.edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.edit:
                showToast("edit");
                break;
        }
    }
}
