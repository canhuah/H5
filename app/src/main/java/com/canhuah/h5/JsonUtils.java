/*
 * @version 1.0
 * @date 17-7-21 下午5:56
 * Copyright 杭州异人异想网络科技有限公司   All Rights Reserved
 *  未经授权不得进行修改、复制、出售及商业使用
 */

package com.canhuah.h5;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Json解析辅助工具类
 */
public class JsonUtils {

    /** 通过key获取value **/
    public static String getContent(String json, String key){
        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(object == null) return "";
        if(key == null) return "";

        String result = null;
        try {
            Object obj = object.get(key);
            if (obj == null || obj.equals(null)){
                result = "";
            } else {
                result = obj.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 将json转化为cls对象 **/
    public static <T> T json2Object(String json, Class<T> cls) {
        T clazz = null;
        try {
            clazz = new GsonBuilder().create().fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static <T> List<T> json2List(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                list.add(json2Object(object.toString(), cls));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** 将json转化为List<Map<String, Object>> **/
    public static List<Map<String, Object>> listKeyMaps(String response) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            GsonBuilder builder = new GsonBuilder();
            // 不转换没有 @Expose 注解的字段
            builder.excludeFieldsWithoutExposeAnnotation();
            Gson gson = builder.create();
            list = gson.fromJson(response, new TypeToken
                    <List<Map<String, Object>>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



}
