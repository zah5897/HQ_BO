package com.zhan.bottle.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhan.bottle.R;
import com.zhan.bottle.model.service.BottleService;
import com.zhan.bottle.ui.base.BaseActivity;
import com.zhan.bottle.utils.BitmapUtil;
import com.zhan.bottle.utils.FileUtils;
import com.zhan.bottle.utils.http.RequestParam;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by zah on 2016/10/21.
 */
public class EditBottleActivity extends BaseActivity {

    @BindView(R.id.main_container)
    RelativeLayout mainContainer;

    private String currentImgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bottle);
        ButterKnife.bind(this);

    }


    private void repleaseByImage() {
        mainContainer.removeAllViews();
        ImageView imgView = new ImageView(this);
        mainContainer.addView(imgView, -1, -1);
        Glide.with(this)
                .load(new File(currentImgPath))
                .into(imgView);
        File targetFile = FileUtils.createTempImg(System.currentTimeMillis() + currentImgPath.substring(currentImgPath.lastIndexOf(".")));
        String tempPath = BitmapUtil.compressImage(currentImgPath, targetFile.getAbsolutePath(), 80);
        currentImgPath = tempPath;
    }

    private void send() {
        View v = mainContainer.getChildAt(0);
        RequestParam param = new RequestParam();
        if (v instanceof ImageView) {
            if (TextUtils.isEmpty(currentImgPath) || !new File(currentImgPath).exists()) {
                showToast("图片不存在");
                return;
            }
            param.put("file", new File(currentImgPath));
        } else if (v instanceof EditText) {
            String txt = ((EditText) v).getText().toString().trim();
            if (TextUtils.isEmpty(txt)) {
                showToast("内容不能为空");
                return;
            }
            param.put("content", txt);
        }
        BottleService.get().sendBottle(param);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path != null && path.size() == 1) {
                    currentImgPath = path.get(0);
                    if (new File(currentImgPath).exists()) {
                        repleaseByImage();
                    }
                }
                // 处理你自己的逻辑 ....
            }
        }
    }

    @OnClick({R.id.back, R.id.audio, R.id.add_img, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.audio:
                break;
            case R.id.add_img:
                MultiImageSelector.create()
                        .showCamera(true) // show camera or not. true by default
                        .count(1) // max select image size, 9 by default. used width #.multi()
                        .single() // single mode
                        .multi() // multi mode, default mode;
                        .start(this, 0);
                break;
            case R.id.send:
                send();
                break;
        }
    }
}
