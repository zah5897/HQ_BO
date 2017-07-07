package com.zhan.bottle.ui.activitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhan.bottle.R;
import com.zhan.bottle.model.service.AppService;
import com.zhan.bottle.model.service.UserManager;
import com.zhan.bottle.ui.base.BaseSwipeBackActivity;
import com.zhan.bottle.ui.widget.HQMaterialProgressTip;
import com.zhan.bottle.utils.DeviceUtils;
import com.zhan.bottle.utils.PrefUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;


/**
 * Created by zah on 2016/11/24.
 */

public class SettingActivity extends BaseSwipeBackActivity {


    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.voice)
    SwitchView voice;
    @BindView(R.id.vibrate)
    SwitchView vibrate;
    @BindView(R.id.check_version)
    TextView checkVersion;
    @BindView(R.id.logout)
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        barTitle.setText("设置");
        if (!UserManager.get().isLogin()) {
            logout.setEnabled(false);
        }
        voice.setOpened(PrefUtil.get().getBoolean("voice", true));
        vibrate.setOpened(PrefUtil.get().getBoolean("vibrate", false));
        String ver_name = DeviceUtils.getVersionName();
        checkVersion.setText("检查更新" + (ver_name == null ? "" : "(v" + ver_name + ")"));
    }

    @OnClick(R.id.logout)
    public void onViewClicked() {
        UserManager.get().logout();
        finish();
    }

    @OnClick({R.id.back, R.id.voice, R.id.vibrate, R.id.clear_cache, R.id.about, R.id.check_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.voice:
                PrefUtil.get().putBoolean("voice", voice.isOpened());
                break;
            case R.id.vibrate:
                PrefUtil.get().putBoolean("vibrate", vibrate.isOpened());
                break;
            case R.id.clear_cache:
                break;
            case R.id.about:
                break;
            case R.id.check_version:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (checkVersionTip != null) {
            checkVersionTip.dismiss();
            checkVersionTip = null;
        }
        super.onBackPressed();
    }

    private HQMaterialProgressTip checkVersionTip;

    private void checkVersion() {
        checkVersionTip = new HQMaterialProgressTip(this, "正在检查版本...");
        checkVersionTip.show();
        new AppService().checkVersion(new UserManager.Callback() {
            @Override
            public void onResult(Object object) {
                JSONObject json = (JSONObject) object;
                String version = json.optString("version");
                if (TextUtils.isEmpty(version)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (checkVersionTip != null) {
                                checkVersionTip.dismiss();
                                checkVersionTip = null;
                            }
                            showToast("当前已是最新版本");
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(SettingActivity.this).setMessage("检测到新版本，是否升级?").setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    showToast("已经开始下载");
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailed(final int code, final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (checkVersionTip != null) {
                            checkVersionTip.dismiss();
                            checkVersionTip = null;
                        }
                        showToast(msg);
                    }
                });
            }
        });
    }
}
