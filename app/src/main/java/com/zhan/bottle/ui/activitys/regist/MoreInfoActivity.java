package com.zhan.bottle.ui.activitys.regist;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;
import com.zhan.bottle.R;
import com.zhan.bottle.model.service.UserManager;
import com.zhan.bottle.ui.activitys.EditBottleActivity;
import com.zhan.bottle.ui.base.BaseActivity;
import com.zhan.bottle.ui.widget.HQMaterialProgressTip;
import com.zhan.bottle.utils.BitmapUtil;
import com.zhan.bottle.utils.FileUtils;
import com.zhan.bottle.utils.http.RequestParam;

import java.io.File;
import java.net.URI;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by zah on 2017/7/6.
 */

public class MoreInfoActivity extends BaseActivity {
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.male_view)
    RelativeLayout maleView;
    @BindView(R.id.female_view)
    RelativeLayout femaleView;
    @BindView(R.id.nickname)
    EditText nickname;
    private String currentImgPath;

    private String gender;
    private String default_avatar_url;

    private int gender_val;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        gender = getIntent().getStringExtra("gender");
        default_avatar_url = getIntent().getStringExtra("default_avatar_url");
        ButterKnife.bind(this);
        ((TextView) findViewById(R.id.bar_title)).setText("完善资料");
        setDefault();
    }

    private void setDefault() {
        if (!TextUtils.isEmpty(default_avatar_url)) {
            Glide.with(this)
                    .load(default_avatar_url)
                    .into(avatar);
        }
        String nick = getIntent().getStringExtra("nickname");
        if (!TextUtils.isEmpty(nick)) {
            nickname.setText(nick);
            nickname.setSelection(nick.length());
        }
        String gender = getIntent().getStringExtra("gender");
        gender_val = (gender == null || gender.equals("男")) ? 1 : 0;
        setGender(gender_val);
    }

    private void setGender(int gender) {
        gender_val = gender;
        if (gender == 1) {
            maleView.setBackgroundResource(R.drawable.gender_male_rect_bg);
            femaleView.setBackgroundResource(R.drawable.gender_female_unselected_rect_bg);
        } else {
            maleView.setBackgroundResource(R.drawable.gender_female_unselected_rect_bg);
            femaleView.setBackgroundResource(R.drawable.gender_female_rect_bg);
        }
    }

    @Override
    public void onBackPressed() {

        if (registTip != null && registTip.isShow()) {
            showToast("正在提交数据，请稍等");
            return;
        }
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (paths != null && paths.size() == 1) {
                    String path = paths.get(0);
                    if (new File(path).exists()) {
                        File targetFile = FileUtils.createTempImg(System.currentTimeMillis() + path.substring(path.lastIndexOf(".")));
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(targetFile))
                                .withAspectRatio(1, 1)
                                .withMaxResultSize(100, 100)
                                .start(this);
                    }
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                Uri resultUri = UCrop.getOutput(data);
                currentImgPath = resultUri.getPath();
                Glide.with(this)
                        .load(new File(currentImgPath))
                        .into(avatar);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Throwable cropError = UCrop.getError(data);
            currentImgPath = null;
        }
    }

    boolean hasTip = false;
    private HQMaterialProgressTip registTip;

    private void save() {
        if (TextUtils.isEmpty(default_avatar_url) && currentImgPath == null) {
            showToast("请设置头像");
            return;
        }
        String nick = nickname.getText().toString().trim();
        if (TextUtils.isEmpty(nick)) {
            showToast("请输入昵称");
            return;
        }

        if (!hasTip) {
            new AlertDialog.Builder(this).setMessage("性别保持之后无法修改，是否已经确定性别了？").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    hasTip = true;
                    dialog.dismiss();
                }
            }).show();
            return;
        }


        registTip = new HQMaterialProgressTip(this, "提交中..");
        registTip.show();
        RequestParam param = new RequestParam();
        param.put("nickname", nick);
        param.put("sex", gender_val == 1 ? "male" : "female");
        param.put("username", getIntent().getStringExtra("openid"));
        param.put("access_token", getIntent().getStringExtra("access_token"));
        param.put("province", getIntent().getStringExtra("province"));
        param.put("city", getIntent().getStringExtra("city"));
        param.put("password", getIntent().getStringExtra("password"));
        param.put("type", getIntent().getIntExtra("type", 2)); //默認qq注冊


        if (currentImgPath != null && FileUtils.exist(currentImgPath)) {
            param.put("file", new File(currentImgPath));
        } else {
            param.put("avatar", default_avatar_url);
        }

        UserManager.get().regist(param, new UserManager.Callback() {
            @Override
            public void onResult(Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        registTip.dismiss();
                        showToast("注册成功");
                        finish();
                    }
                });
            }

            @Override
            public void onFailed(int code, String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        registTip.dismiss();
                        showToast("注册失败");
                    }
                });
            }
        });
    }

    @OnClick({R.id.back, R.id.avatar, R.id.male_view, R.id.female_view, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.avatar:
                MultiImageSelector.create()
                        .showCamera(true) // show camera or not. true by default
                        .count(1) // max select image size, 9 by default. used width #.multi()
                        .single() // single mode
                        .multi() // multi mode, default mode;
                        .start(this, 0);
                break;
            case R.id.male_view:
                setGender(1);
                break;
            case R.id.female_view:
                setGender(0);
                break;
            case R.id.save:
                save();
                break;
        }
    }
}
