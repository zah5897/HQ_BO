package com.zhan.bottle.model.service;

import com.zhan.bottle.model.User;
import com.zhan.bottle.model.event.LoginEvent;
import com.zhan.bottle.utils.Constact;
import com.zhan.bottle.utils.PrefUtil;
import com.zhan.bottle.utils.http.HttpHelper;
import com.zhan.bottle.utils.http.RequestParam;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * Created by zah on 2017/7/7.
 */

public class AppService {
    public static final String APP_CHECK_VERSION = "/sys/checkVersion";

    public void checkVersion(final UserManager.Callback callback) {
        BottleService.get().doTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = HttpHelper.post(Constact.getFullURL(APP_CHECK_VERSION), null);
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("code");
                    String msg = object.optString("msg");
                    if (code == 0) {
                        callback.onResult(object);
                    } else {
                        callback.onFailed(code, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1, "网络异常");
                }
            }
        });
    }
}
