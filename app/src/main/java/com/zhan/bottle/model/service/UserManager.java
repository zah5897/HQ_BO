package com.zhan.bottle.model.service;

import com.google.gson.Gson;
import com.zhan.bottle.model.User;
import com.zhan.bottle.utils.Constact;
import com.zhan.bottle.utils.PrefUtil;
import com.zhan.bottle.utils.http.HttpHelper;
import com.zhan.bottle.utils.http.RequestParam;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by zah on 2016/10/21.
 */
public class UserManager {
    public static final String TENCENT_APP_ID = "1106194067";
    public static final String CACHE_ACCOUNT_KEY = "account";

    public static final String USER_GET_BY_OPENID = "/user/getByOpenid";
    public static final String USER_REGIST = "/user/regist";
    public static final String USER_EXIST = "/user/isExist";
    public static final String USER_LOGIN = "/user/login";
    public static final String USER_SUBMIT = "/user/submit";
    public static final String USER_MODIFY = "/user/modify_user_info";
    public static final String USER_MRESET_PWD = "/user/reset_pwd";
    public static final String USER_MODIFY_PWD = "/user/modify_pwd";
    public static final String USER_CHECK_MOBILE = "/user/check_mobile";
    public static final String SYS_REPORT = "/sys/report";
    public static final String USER_LOAD_INFO = "/user/user_info";
    private static UserManager userManager;

    private Map<String, User> userMap;

    private UserManager() {
        userMap = new HashMap<>();
        String cache_json = PrefUtil.get().getString(CACHE_ACCOUNT_KEY, null);
        if (cache_json != null) {
            loginUser = BottleService.get().getGson().fromJson(cache_json, User.class);
        }
    }

    private User loginUser;

    public User getLoginUser() {
        return loginUser;
    }

    public User getUser(String id) {
        if (userMap.containsKey(id)) {
            return userMap.get(id);
        }
        return null;
    }

    public void saveUser(User user) {
        userMap.put(String.valueOf(user.id), user);
    }

    public User getUser(long id) {
        return getUser(String.valueOf(id));
    }

    public static UserManager get() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public void getUserByUserName(final String username, final Callback callback) {
        BottleService.get().doTask(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestParam param = new RequestParam();
                    param.put("openid", username);
                    String result = HttpHelper.post(Constact.getFullURL(USER_GET_BY_OPENID), param);
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("code");
                    String msg = object.optString("msg");
                    String userJson = object.optString("user");
                    User user = BottleService.get().getGson().fromJson(userJson, User.class);
                    if (code == 0 && user != null) {
                        loginUser = user;
                        PrefUtil.get().putString(CACHE_ACCOUNT_KEY, userJson);
                        callback.onResult(user);
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

    public void regist(final RequestParam param, final Callback callback) {
        BottleService.get().doTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = HttpHelper.post(Constact.getFullURL(USER_REGIST), param);
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("code");
                    String msg = object.optString("msg");
                    JSONObject userJson = object.optJSONObject("user");
                    User user = BottleService.get().getGson().fromJson(userJson.toString(), User.class);
                    if (code == 0 && user != null) {
                        loginUser = user;
                        PrefUtil.get().putString(CACHE_ACCOUNT_KEY, userJson.toString());
                        callback.onResult(user);
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

    public void checkExist(final String username, final Callback callback) {
        BottleService.get().doTask(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestParam param = new RequestParam();
                    param.put("username", username);
                    String result = HttpHelper.post(Constact.getFullURL(USER_EXIST), param);
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("code");
                    String msg = object.optString("msg");
                    boolean isExist = object.optBoolean("is_exist");
                    if (code == 0) {
                        callback.onResult(isExist);
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

    public void login(final String un, final String pwd, final Callback callback) {
        BottleService.get().doTask(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestParam param = new RequestParam();
                    param.put("username", un);
                    param.put("password", pwd);
                    String result = HttpHelper.post(Constact.getFullURL(USER_LOGIN), param);
                    JSONObject object = new JSONObject(result);
                    int code = object.optInt("code");
                    String msg = object.optString("msg");
                    JSONObject userJson = object.optJSONObject("user");
                    User user = BottleService.get().getGson().fromJson(userJson.toString(), User.class);
                    if (code == 0 && user != null) {
                        loginUser = user;
                        PrefUtil.get().putString(CACHE_ACCOUNT_KEY, userJson.toString());
                        callback.onResult(user);
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

    public boolean isLogin() {
        return loginUser != null;
    }


    public interface Callback {
        void onResult(Object object);

        void onFailed(int code, String msg);
    }
}
