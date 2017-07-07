package com.zhan.bottle.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhan.bottle.R;
import com.zhan.bottle.model.service.UserManager;
import com.zhan.bottle.ui.activitys.regist.MoreInfoActivity;
import com.zhan.bottle.ui.activitys.regist.RegistActivity;
import com.zhan.bottle.ui.base.BaseActivity;
import com.zhan.bottle.ui.widget.HQMaterialProgressTip;
import com.zhan.bottle.utils.ZLog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zah on 2016/11/24.
 */

public class LoginActivity extends BaseActivity implements IUiListener {
    Tencent mTencent;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((TextView) findViewById(R.id.to_regist_page)).setText(Html.fromHtml(getString(R.string.to_regist_tip)));
        ((TextView) findViewById(R.id.bar_title)).setText("登录");
    }

    @OnClick({R.id.back, R.id.qq_login, R.id.to_regist_page, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.qq_login:
                mTencent = Tencent.createInstance(UserManager.TENCENT_APP_ID, this.getApplicationContext());
                mTencent.login(this, "all", this);
                break;
            case R.id.to_regist_page:
                to(RegistActivity.class);
                finish();
                break;
            case R.id.login:
                login();
                break;
        }
    }


    private void login() {
        String un = username.getText().toString();
        String pwd = password.getText().toString();

        if (TextUtils.isEmpty(un) || TextUtils.isEmpty(pwd)) {
            showToast("帐号密码不能为空");
            return;
        }
        loginTip = new HQMaterialProgressTip(this, "正在登录...");
        loginTip.show();
        UserManager.get().login(un, pwd, new UserManager.Callback() {
            @Override
            public void onResult(Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loginTip != null) {
                            loginTip.dismiss();
                        }
                        showToast("登录成功");
                        finish();
                    }
                });
            }

            @Override
            public void onFailed(int code, final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loginTip != null) {
                            loginTip.dismiss();
                        }
                        showToast(msg);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (loginTip != null && loginTip.isShow()) {
            showToast("正在登录中，请稍后");
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN && resultCode == RESULT_OK) {
            Tencent.handleResultData(data, this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkOpenid(String openid) {
        UserManager.get().getUserByUserName(openid, new UserManager.Callback() {
            @Override
            public void onResult(Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loginTip != null) {
                            loginTip.dismiss();
                        }
                        showToast("登陆成功");
                        finish();
                    }
                });
            }

            @Override
            public void onFailed(final int code, final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loginTip != null) {
                            loginTip.dismiss();
                        }
                        if (code == 0) {
                            UserInfo info = new UserInfo(LoginActivity.this, mTencent.getQQToken());
                            info.getUserInfo(LoginActivity.this);
                            return;
                        }
                        showToast(msg);
                    }
                });
            }
        });
    }

    private HQMaterialProgressTip loginTip;

    @Override
    public void onComplete(Object o) {
        ZLog.e(o.toString());
        if (o != null && o instanceof JSONObject) {
            JSONObject obj = (JSONObject) o;
            if (obj.has("openid")) {
                String access_token = obj.optString("access_token");
                String openid = obj.optString("openid");
                checkOpenid(openid);
                loginTip = new HQMaterialProgressTip(this, "正在检查信息...");
                loginTip.show();
                mTencent.setAccessToken(access_token, "7776000");
                mTencent.setOpenId(openid);
//                UserInfo info = new UserInfo(this, mTencent.getQQToken());
//                info.getUserInfo(this);
            } else {
                String nickname = obj.optString("nickname");
                String avatar_small = obj.optString("figureurl");
                String avatar_big = obj.optString("figureurl_1");
                String figureurl_qq_1 = obj.optString("figureurl_qq_1");
                String figureurl_qq_2 = obj.optString("figureurl_qq_2");
                String province = obj.optString("province");
                String city = obj.optString("city");
                String gender = obj.optString("gender");

                Intent intent = new Intent(this, MoreInfoActivity.class);
                intent.putExtra("nickname", nickname);
                intent.putExtra("gender", TextUtils.isEmpty(gender) ? "男" : gender);

                if (!TextUtils.isEmpty(figureurl_qq_2)) {
                    intent.putExtra("default_avatar_url", figureurl_qq_2);
                } else {
                    intent.putExtra("default_avatar_url", figureurl_qq_1);
                }
                intent.putExtra("province", province);
                intent.putExtra("city", city);
                intent.putExtra("openid", mTencent.getOpenId());
                intent.putExtra("access_token", mTencent.getAccessToken());
                intent.putExtra("type", 2); //默認qq注冊
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onError(UiError uiError) {
        showToast("QQ登录失败");
    }

    @Override
    public void onCancel() {
        showToast("QQ登录取消");
    }
}
