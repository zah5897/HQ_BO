package com.zhan.bottle.ui.fragments.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.bottle.R;
import com.zhan.bottle.model.User;
import com.zhan.bottle.model.service.UserManager;
import com.zhan.bottle.ui.activitys.LoginActivity;
import com.zhan.bottle.ui.activitys.UserInfoActivity;
import com.zhan.bottle.ui.base.BaseFragment;
import com.zhan.bottle.utils.Constact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zah on 2017/6/27.
 */

public class TabMeFragment extends BaseFragment {
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.extra_info_layout)
    LinearLayout extraInfoLayout;
    @BindView(R.id.signature)
    TextView signature;
    @BindView(R.id.not_login)
    TextView notLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        view.findViewById(R.id.back).setVisibility(View.GONE);
        unbinder = ButterKnife.bind(this, view);
        barTitle.setText("个人中心");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserInfo();
    }

    private void setUserInfo() {
        if (UserManager.get().isLogin()) {
            User user = UserManager.get().getLoginUser();
            if (!TextUtils.isEmpty(user.avatar)) {
                if (user.avatar.startsWith("http://")) {
                    Glide.with(this).load(user.avatar).into(avatar);
                } else {
                    Glide.with(this).load(Constact.getFullIMGURL(user.avatar)).into(avatar);
                }
            }
            nickName.setVisibility(View.VISIBLE);
            setText(nickName, user.nickname);
            extraInfoLayout.setVisibility(View.VISIBLE);
            signature.setVisibility(View.VISIBLE);
            setText(signature, user.signature);
            notLogin.setVisibility(View.GONE);
        } else {
            nickName.setVisibility(View.GONE);
            extraInfoLayout.setVisibility(View.GONE);
            signature.setVisibility(View.GONE);
            notLogin.setVisibility(View.VISIBLE);
        }
    }

    private void setText(TextView view, String txt) {
        if (TextUtils.isEmpty(txt)) {
            view.setText("");
        } else {
            view.setText(txt);
        }
    }

    @OnClick({R.id.user_layout, R.id.gift, R.id.vip, R.id.market, R.id.task, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_layout:
                if (UserManager.get().isLogin()) {
                    to(UserInfoActivity.class);
                } else {
                    to(LoginActivity.class);
                }
                break;
            case R.id.gift:
                break;
            case R.id.vip:
                break;
            case R.id.market:
                break;
            case R.id.task:
                break;
            case R.id.setting:
                break;
        }
    }
}
