package com.zhan.bottle.model.enums;

/**
 * Created by zah on 2017/6/28.
 */
public enum StarType {
    ARIES(0, "aries", "白羊座"),
    TAURUS(1, "taurus", "金牛座"),
    GEMINI(2, "gemini", "双子座"),
    CANCER(3, "cancer", "巨蟹座"),
    LEO(4, "leo", "狮子座"),
    VIRGO(5, "virgo", "处女座"),
    LIBRA(6, "libra", "天秤座"),
    SCORPIO(7, "scorpio", "天蝎座"),
    SAG(8, "sagittarius", "射手座"),
    CAP(9, "capricorn", "摩羯座"),
    AQU(10, "aquarius", "水瓶座"),
    PIS(11, "pisces", "双鱼座");
    private int _value;
    private String en_name;
    private String cn_name;


    private StarType(int value, String enName, String cnName) {
        _value = value;
        en_name = enName;
        cn_name = cnName;
    }

    public int getValue() {
        return _value;
    }

    public String getCn_name() {
        return cn_name;
    }

    public String getEn_name() {
        return en_name;
    }
}
