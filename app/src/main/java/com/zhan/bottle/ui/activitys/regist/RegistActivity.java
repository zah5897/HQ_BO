package com.zhan.bottle.ui.activitys.regist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.tauth.IUiListener;
import com.zhan.bottle.R;
import com.zhan.bottle.model.service.UserManager;
import com.zhan.bottle.ui.base.BaseActivity;
import com.zhan.bottle.ui.widget.HQMaterialProgressTip;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zah on 2016/11/24.
 */

public class RegistActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.repwd)
    EditText repwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        ((TextView) findViewById(R.id.bar_title)).setText("注册");
    }

    @OnClick({R.id.back, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.next:
                checkExist();
                break;
        }
    }

    private HQMaterialProgressTip checkTip;

    private void checkExist() {
        final String uname = username.getText().toString().trim();
        if (TextUtils.isEmpty(uname)) {
            showToast("帐号不能为空");
            return;
        }
        final String pwd = password.getText().toString();

        if (TextUtils.isEmpty(pwd.trim())) {
            showToast("密码不能为空");
            return;
        }

        String rep = repwd.getText().toString();

        if (!pwd.equals(rep)) {
            showToast("两次密码不一致");
            return;
        }

        checkTip = new HQMaterialProgressTip(this, "正在校验账号信息...");
        checkTip.show();
        UserManager.get().checkExist(uname, new UserManager.Callback() {
            @Override
            public void onResult(final Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (checkTip != null) {
                            checkTip.dismiss();
                        }
                        Boolean isExist = (Boolean) object;
                        if (isExist) {
                            showToast("该帐号已存在");
                        } else {
                            toMoreInfo();
                        }

                    }
                });
            }

            @Override
            public void onFailed(final int code, final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (checkTip != null) {
                            checkTip.dismiss();
                        }
                        if (code == 0) {
                            toMoreInfo();
                            return;
                        }
                        showToast(msg);
                    }
                });
            }
        });
    }


    private void toMoreInfo() {
        Intent intent = new Intent(RegistActivity.this, MoreInfoActivity.class);
        intent.putExtra("type", 1);
        intent.putExtra("openid", username.getText().toString().trim());
        intent.putExtra("password", password.getText().toString());
        startActivity(intent);
        finish();
    }
}
