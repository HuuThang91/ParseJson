package com.kira.baseball.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kira on 7/19/2016.
 */
public class ParseJson {

    public static <T> T parseJsonObject(Class<T> cl, String s) throws InstantiationException, IllegalAccessException, JSONException {
        JSONObject object = new JSONObject(s);
        T obj = parseJson(cl,object);
        return obj;
    }

    public static <T> List<T> parseJsonArray(Class<T> cl, String s) throws InstantiationException, IllegalAccessException, JSONException {
        JSONArray array = new JSONArray(s);
        List<T> list = parseJson(cl, array);
        return list;
    }

    public static <T> List<T> parseJson(Class<T> cl, JSONArray array) throws InstantiationException, IllegalAccessException, JSONException {
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < array.length(); i++) {
            T obj = parseJson(cl, array.getJSONObject(i));
            list.add(obj);
        }
        return list;
    }

    public static <T> T parseJson(Class<T> cl, JSONObject object) throws InstantiationException, IllegalAccessException, JSONException {
        T obj = (T) cl.newInstance();
        Field[] attributes = cl.getDeclaredFields();
        for (Field field : attributes) {
            field.setAccessible(true);
            try {
                if (field.getType().toString().equals("class java.lang.String")) {
                    field.set(obj, object.getString("" + field.getName()));
                } else if (field.getType().toString().equals("boolean")) {
                    field.set(obj, object.getBoolean("" + field.getName()));
                } else if (field.getType().toString().equals("int")) {
                    field.set(obj, object.getInt("" + field.getName()));
                } else if (field.getType().toString().equals("float")) {
                    String temp = object.getString("" + field.getName());
                    field.set(obj, Float.parseFloat(temp));
                } else if (field.getType().toString().equals("class java.lang.Long")) {
                    String temp = object.getString("" + field.getName());
                    field.set(obj, Long.parseLong(temp));
                } else if (field.getType().toString().equals("class java.lang.Double")) {
                    String temp = object.getString("" + field.getName());
                    field.set(obj, Double.parseDouble(temp));
                } else if (field.getGenericType().toString().equals("java.util.List<java.lang.String>")) {
                    String temp = object.getString("" + field.getName());
                    List<String> result = new ArrayList<>();
                    String[] list = temp.split(",");
                    for (int i = 0; i < list.length; i++) {
                        result.add(getOnlyStrings(list[i]));
                    }
                    field.set(obj, result);
                } else {
                    String jsonString = object.getString("" + field.getName());
                    if (field.getGenericType().toString().startsWith("java.util.List")) {
                        String temp = field.getGenericType().toString();
                        String className =temp.substring(15).replaceAll(">", "");
                        Class cls = Class.forName(className);
                        List list =parseJsonArray(cls,jsonString);
                        field.set(obj, list);


                    } else {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        String temp = field.getGenericType().toString();
                        Class cls = Class.forName(temp.substring(6));
                        field.set(obj, parseJson(cls, jsonObject));
                    }
                }
            } catch (Exception e) {
            }
        }
        return obj;
    }



    public static String getOnlyStrings(String s) {
        Pattern pattern = Pattern.compile("[^a-z A-Z]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }
}
