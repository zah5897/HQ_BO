package com.zhan.bottle.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import com.zhan.bottle.app.Application;

import java.util.Random;

/**
 * Created by zah on 2017/6/20.
 */

public class DeviceUtils {
    private static String imei;
    private static final String IMEI = "imei";
    private static int versionCode;

    public static final String getUniqueID() {
        int t1 = (int) (System.currentTimeMillis() / 1000L);
        int t2 = (int) System.nanoTime();
        int t3 = new Random().nextInt();
        int t4 = new Random().nextInt();
        byte[] b1 = getBytes(t1);
        byte[] b2 = getBytes(t2);
        byte[] b3 = getBytes(t3);
        byte[] b4 = getBytes(t4);
        byte[] bUniqueID = new byte[16];
        System.arraycopy(b1, 0, bUniqueID, 0, 4);
        System.arraycopy(b2, 0, bUniqueID, 4, 4);
        System.arraycopy(b3, 0, bUniqueID, 8, 4);
        System.arraycopy(b4, 0, bUniqueID, 12, 4);
        return Base64.encodeToString(bUniqueID, 2);
    }

    private static byte[] getBytes(int i) {
        byte[] bInt = new byte[4];
        int value = i;
        bInt[3] = ((byte) (value % 256));
        value >>= 8;
        bInt[2] = ((byte) (value % 256));
        value >>= 8;
        bInt[1] = ((byte) (value % 256));
        value >>= 8;
        bInt[0] = ((byte) (value % 256));
        return bInt;
    }

    public static String getImei() {
        String imei = null;
        try {
            TelephonyManager tm = (TelephonyManager) Application.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null)
                imei = tm.getDeviceId();
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(imei)) {
            imei = getUniqueID();
        }
        return imei.trim();
    }

    public static String loadImei() {

        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        imei = PrefUtil.get().getString(IMEI, "");
        String fileName = String.valueOf(IMEI.hashCode());
        if (!TextUtils.isEmpty(imei)) {
            FileUtils.saveContent(fileName, imei);
            return imei;
        }

        imei = FileUtils.readContent(fileName);
        if (!TextUtils.isEmpty(imei)) {
            PrefUtil.get().putString(IMEI, imei);
            return imei;
        }
        imei = getImei();

        PrefUtil.get().putString(IMEI, imei);
        FileUtils.saveContent(fileName, imei);
        return imei;
    }

    public static int getVersionCode() {

        if (versionCode > 0) {
            return versionCode;
        }
        PackageManager pm = Application.getApp().getPackageManager();//context为当前Activity上下文
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(Application.getApp().getPackageName(), 0);
            return versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
