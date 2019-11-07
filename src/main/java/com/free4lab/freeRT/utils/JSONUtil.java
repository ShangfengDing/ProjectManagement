package com.free4lab.freeRT.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
    public static String getMessage(JSONObject json) {
        try {
            return json.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Integer getCode(JSONObject json) {
        try {
            return json.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject getContent(JSONObject json) {
        try {
            return json.has("content")? json.getJSONObject("content"): null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
