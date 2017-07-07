package com.zhan.bottle.model.service;

import com.google.gson.Gson;
import com.zhan.bottle.utils.Constact;
import com.zhan.bottle.utils.http.HttpHelper;
import com.zhan.bottle.utils.http.RequestParam;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zah on 2016/12/1.
 */

public class BottleService {
    private static BottleService starService;
    private ExecutorService cachedThreadPool;
    private Gson gson;

    private BottleService() {
        cachedThreadPool = Executors.newCachedThreadPool();
        gson = new Gson();
    }

    public Gson getGson() {
        return gson;
    }

    public ExecutorService getThreadPool() {
        return cachedThreadPool;
    }

    public static BottleService get() {
        if (starService == null) {
            starService = new BottleService();
        }
        return starService;
    }


    public void doTask(Runnable runnable) {
        cachedThreadPool.execute(runnable);
    }

    public void loadTabStar() {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
//                List<StarModel> starModels = null;
//                int cur_star_type = 0;
//                try {
//                    spf = Application.getApp().getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
//                    Map<String, ?> allVal = spf.getAll();
//                    for (String key : allVal.keySet()) {
//                        if (key.startsWith(KEY_STAR)) {
//                            String val = allVal.get(key).toString();
//                            if (TextUtils.isEmpty(val)) {
//                                continue;
//                            }
//                            starModels = gson.fromJson(val, new TypeToken<List<StarModel>>() {
//                            }.getType());
//                            cacheStars.clear();
//                            cacheStars.addAll(starModels);
//                        } else if (key.startsWith(KEY_CUR_START_TYPE)) {
//                            cur_star_type = (Integer) allVal.get(key);
//                        } else if (key.startsWith(KEY_FORTUNE)) {
//                        }
//                    }
//                    EventBus.getDefault().post(new TabStarEvent(starModels, cur_star_type));
//                    String todayStr = DateTimeUtil.getNowStr();
//                    if (spf.contains(KEY_STAR + todayStr)) {
//                        return;
//                    }
//                    String result = HttpHelper.post(Constact.getFullURL("/tab/star"), null);
//                    JSONObject object = new JSONObject(result);
//                    JSONArray stars = object.optJSONArray("stars");
//                    cur_star_type = object.optInt("cur_star_type", 0);
//                    JSONObject cur_fortune = object.optJSONObject("cur_fortune");
//                    starModels = gson.fromJson(stars.toString(), new TypeToken<List<StarModel>>() {
//                    }.getType());
//                    cacheStars.clear();
//                    cacheStars.addAll(starModels);
//
//                    EventBus.getDefault().post(new TabStarEvent(starModels, cur_star_type));
//                    spf.edit().putString(KEY_STAR + todayStr, stars.toString()).commit();
//                    spf.edit().putInt(KEY_CUR_START_TYPE + todayStr, cur_star_type).commit();
//                    spf.edit().putString(KEY_FORTUNE + todayStr, cur_fortune.toString()).commit();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    EventBus.getDefault().post(new TabStarEvent(starModels, cur_star_type));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    EventBus.getDefault().post(new TabStarEvent(starModels, cur_star_type));
//                }
            }
        });

    }

    public void sendBottle(final RequestParam param) {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = HttpHelper.post(Constact.getFullURL("/main/send"), param);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

//    public static void sendBottle(File file, String contnet, int type, BaseSubscriber subscriber) {
//        RequestParam param = new RequestParam();
//        param.put("content", contnet);
//        param.put("sender_id", UserManager.getInstance().getUser().id);
//        param.put("type", type);
//        if (file != null && file.exists()) {
//            param.put("image", file);
//        }
//        HttpHelper.postHttp(BOTTLE_SEND, param).subscribe(subscriber);
//    }

//    public static Bottle praseBottle(JSONObject jsonObject) {
//        JSONObject bottleObj = jsonObject.optJSONObject("bottle");
//        Gson gson = new Gson();
//        return gson.fromJson(bottleObj.toString(), Bottle.class);
//    }
//    public static void submitComment(long bottle_id, long author_user_id, String comment, long at_comment_id, Subscriber subscriber) {
//        RequestParam param = new RequestParam();
//        param.put("bottle_id", bottle_id);
//        param.put("bottle_user_id", author_user_id);
//        param.put("content", comment);
//        param.put("at_comment_id", at_comment_id);
//        HttpHelper.postHttp(BOTTLE_COMMENT, param).subscribe(subscriber);
//    }
}
