package com.zhan.bottle.utils.http;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by zah on 2016/12/13.
 */

public class RequestParam extends HashMap<String, Object> {
    public RequestBody prase() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (String key : keySet()) {
            Object object = get(key);

            if (object == null) {
                builder.addFormDataPart(key, "");
                continue;
            }
            if (object instanceof File) {
                File file = (File) object;
                builder.addFormDataPart("file", file.getName(), RequestBody.create(null, file));
            } else {
                String value = object.toString();
                builder.addFormDataPart(key, value);
            }
        }
        return builder.build();
    }
}
