package com.free4lab.freeRT.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.free4lab.utils.enabler.AbstractAuthentication;
import com.opensymphony.xwork2.ActionContext;

public class UrlUtil {
    public static String genGetUrl(String action, Map<String, String> params) {
        params.put(Constants.COMMON_KEY_APPID, Constants.COMMON_VALUE_APPID);
        params.put(Constants.COMMON_KEY_TC,
                String.valueOf(System.currentTimeMillis()));
        params.put(Constants.ACCESS_TOKEN, getSessionToken());
        params.put(Constants.COMMON_KEY_RANDOM, randomStr());

        TreeMap<String, String> paramsMap = new TreeMap<String, String>(params);
        paramsMap.put(Constants.COMMON_KEY_SIGNATURE, getSignature(paramsMap));

        StringBuilder sb = new StringBuilder();
        sb.append(Constants.DOMAIN).append('/').append(action).append(".json");
        boolean isFirst = true;
        for (String key : paramsMap.keySet()) {
            try {
                sb.append(isFirst ? "?" : "&").append(key).append("=")
                        .append(URLEncoder.encode(paramsMap.get(key), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (isFirst) {
                isFirst = false;
            }
        }

        return sb.toString();
    }

    private static String randomStr() {
        char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        StringBuilder sb = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < 32; i++) {
            sb.append(chars[random.nextInt(35)]);
        }

        return sb.toString();
    }

    private static String getSignature(TreeMap<String, String> treeMap) {

        TreeMap<String, String> otherParam = new TreeMap<String, String>();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            if (!(entry.getKey().equals(Constants.COMMON_KEY_TC) || entry
                    .getKey().equals(Constants.COMMON_KEY_APPID))) {
                otherParam.put(entry.getKey(), entry.getValue());
            }

        }

        String appId = Constants.COMMON_VALUE_APPID;
        String appKey = Constants.COMMON_VALUE_APPKEY;
        long tc = Long.valueOf(treeMap.get(Constants.COMMON_KEY_TC));
        String signature = AbstractAuthentication.getSign(appId, appKey, tc,
                otherParam);

        return signature;
    }
    private static String getSessionToken() {
        return (String) ActionContext.getContext().getSession()
                .get(Constants.ACCESS_TOKEN);
    }
}
