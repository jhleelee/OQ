package com.jackleeentertainment.oq.generalutil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Jacklee on 2016. 9. 11..
 */
public class ObjectUtil {


    public static Map<String, Object> getHashMapFromPOJOWithGson(Object obj) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(obj, obj.getClass());

        Type stringStringMap = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(jsonStr, stringStringMap);


        return map;
    }


}
