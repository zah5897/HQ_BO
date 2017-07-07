package com.zhan.bottle.app;

/**
 * Created by zah on 2017/6/28.
 */

public class Application extends android.app.Application {
    private static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        CrashHandler.get().init(app);
    }

    public static Application getApp() {
        return app;
    }
}

