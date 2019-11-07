package com.free4lab.freeRT.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class);

    public static JSONObject get(String url) {
        logger.info("HttpUtil get url=" + url);
        JSONObject response = null;
        try {
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod(url);
            client.executeMethod(method);
            if (method.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = method.getResponseBodyAsString();
                logger.info("HttpUtil get response=" + (null == str? "null": str));
                response = new JSONObject(str/*inStream2String(method.getResponseBodyAsStream())*/);
            }
            method.releaseConnection();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static JSONObject post(String url, List<NameValuePair> params) {
        logger.info("HttpUtil post url=" + url);
        JSONObject response = null;
        try {
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(url);
            method.setRequestBody(params.toArray(new NameValuePair[params.size()]));
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            client.executeMethod(method);
            if (method.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = method.getResponseBodyAsString();
                logger.info("HttpUtil post response=" + (null == str? "null": str));
                response = new JSONObject(str/*inStream2String(method.getResponseBodyAsStream())*/);
            }
            method.releaseConnection();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static JSONObject delete(String url) {
        logger.info("HttpUtil delete url=" + url);
        JSONObject response = null;
        try {
            HttpClient client = new HttpClient();
            HttpMethod method = new DeleteMethod(url);
            client.executeMethod(method);
            if (method.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = method.getResponseBodyAsString();
                logger.info("HttpUtil delete response=" + (null == str? "null": str));
                response = new JSONObject(str/*inStream2String(method.getResponseBodyAsStream())*/);
                System.out.println("response:"+str);
            }
            method.releaseConnection();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static JSONObject put(String url) {
        logger.info("HttpUtil put url=" + url);
        JSONObject response = null;
        try {
            HttpClient client = new HttpClient();
            HttpMethod method = new PutMethod(url);
            //PutMethod method = new PutMethod(url);
            //method.setParams(params);
            //method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            client.executeMethod(method);
            if (method.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = method.getResponseBodyAsString();
                logger.info("HttpUtil put response=" + (null == str? "null": str));
                response = new JSONObject(str/*inStream2String(method.getResponseBodyAsStream())*/);
            }
            method.releaseConnection();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }
}
