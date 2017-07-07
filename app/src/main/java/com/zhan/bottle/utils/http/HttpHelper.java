package com.zhan.bottle.utils.http;

import com.zhan.bottle.app.Application;
import com.zhan.bottle.model.service.UserManager;
import com.zhan.bottle.utils.DeviceUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zah on 2016/11/23.
 */
public class HttpHelper {
    public static final int HTTP_CONNECT_TIME_OUT = 10;
    public static final int HTTP_READ_TIME_OUT = 10;


    private static RequestParam addIntereaptParam(RequestParam param) {
        if (param == null) {
            param = new RequestParam();
        }
        param.put("device_id", DeviceUtils.loadImei());
        param.put("version", DeviceUtils.getVersionCode());
        param.put("app", Application.getApp().getPackageName());
        if (UserManager.get().isLogin()) {
            param.put("uid", UserManager.get().getLoginUser().id);
            param.put("token", UserManager.get().getLoginUser().token);
        }
        return param;
    }

//    public static Observable<String> post(final String url, final RequestParam params) {
//        return Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                try {
//                    Response response = buildAndExecu(url, params);
//                    if (response.isSuccessful()) {
//                        subscriber.onNext(response.body().string());
//                        return;
//                    }
//                    subscriber.onError(null);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    subscriber.onError(e);
//                }
//            }
//        }).subscribeOn(Schedulers.io());
//    }

//    public static Observable<JSONObject> postHttp(String url) {
//        return postHttp(url, null);
//    }
//
//    public static Observable<JSONObject> postHttp(final String url, final RequestParam params) {
//        return Observable.create(new Observable.OnSubscribe<JSONObject>() {
//            @Override
//            public void call(final Subscriber<? super JSONObject> subscriber) {
//                Response response = null;
//                try {
//                    response = buildAndExecu(url, params);
//                    if (response.isSuccessful()) {
//                        String result = response.body().string();
//                        JSONObject object = new JSONObject(result);
//                        int rc = object.optInt("code");
//                        if (rc == 0) {
//                            subscriber.onNext(object);
//                        } else {
//                            subscriber.onError(new HttpError(rc, object.optString("msg")));
//                        }
//                    } else {
//                        subscriber.onError(new HttpError(HttpError.ERROR_HTTP, "网络异常"));
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    subscriber.onError(new HttpError(HttpError.ERROR_HTTP, "网络异常"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    subscriber.onError(new HttpError(HttpError.ERROR_HTTP, "数据错误"));
//                }
//                subscriber.onCompleted();
//            }
//
//        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
//    }


    public static String post(String url, RequestParam params) throws IOException {
        Response response = buildAndExecu(url, params);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("response is not success.");
        }
    }

    private static Response buildAndExecu(String url, RequestParam params) throws IOException {
        RequestBody requestBody = addIntereaptParam(params).prase();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIME_OUT, TimeUnit.SECONDS).writeTimeout(HTTP_READ_TIME_OUT * 2, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return client.newCall(request).execute();
    }


}
