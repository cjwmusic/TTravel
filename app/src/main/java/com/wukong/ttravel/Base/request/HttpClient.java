package com.wukong.ttravel.Base.request;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.wukong.ttravel.BuildConfig;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class HttpClient {

    public static final String BASE_URL_RELEASE = "http://App.traveltailor.cn/";
    public static final String BASE_URL_PRE = "";
    public static final String BASE_URL_DEV = "";

    public static final String DEVICE_NAME = android.os.Build.MODEL;
    public static final String SYSTEM_VERSION = android.os.Build.VERSION.RELEASE;

    private static Handler respHandler;
    private static OkHttpClient okHttpClient;

    /**
     * tip：必须在application初始化时调用
     */
    public static void init() {
        // 实例化httpclient
        okHttpClient = new OkHttpClient();
        // 设置超时时间
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(15, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(15, TimeUnit.SECONDS);
        // 初始化Handler
        respHandler = new Handler(Looper.getMainLooper());
    }

    public static <T, P> void get(String path, JSONObject params, Class<T> clazz, HttpCallback<P> callback) {
        String absUrl = genAbsoluteUrl(path);
        String reqUrl = absUrl;

        try {
            if (params != null)
                reqUrl = reqUrl + ("?data=" + URLEncoder.encode(params.toString(), "UTF-8"));
            Request request = new Request.Builder().
                    url(reqUrl).
                    headers(genHeaders(absUrl)).
                    cacheControl(CacheControl.FORCE_NETWORK).
                    build();

            if (BuildConfig.DEBUG) {
                Log.d("request", request.toString());
            }

            okHttpClient.newCall(request).enqueue(getHttpResponseHandler(path, callback, clazz));

        } catch (Exception e) {
            throw new RuntimeException(e);  // dont arrive, so throw exception
        }
    }

    public static <T, P> void post(String path, JSONObject params, Class<T> clazz, HttpCallback<P> callback) {

        String reqUrl = genAbsoluteUrl(path);

        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        if (params != null) {
            Set<String> keys = params.keySet();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = params.getString(key);
                builder.addFormDataPart(key,value);
            }
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder().
                url(reqUrl).
                post(body).
                headers(genHeaders(reqUrl)).
                cacheControl(CacheControl.FORCE_NETWORK).
                build();

        okHttpClient.newCall(request).enqueue(getHttpResponseHandler(path, callback, clazz));
    }


    public static <T, P> void postFile(String path, File file, Class<T> clazz, HttpCallback<P> callback) {

        String reqUrl = genAbsoluteUrl(path);

        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MultipartBuilder.FORM, file))
                .build();

        Request request = new Request.Builder().
                url(reqUrl).
                headers(HttpClient.genHeaders(reqUrl)).
                post(requestBody).
                cacheControl(CacheControl.FORCE_NETWORK).
                build();

        okHttpClient.newCall(request).enqueue(getHttpResponseHandler(path, callback, clazz));
    }


    private static <T> HttpResponseHandler getHttpResponseHandler(String path, HttpCallback callback, Class<T> clazz) {
        return new HttpResponseHandler(path, callback, clazz);
    }


    private static class HttpResponseHandler<T> implements Callback {

        private long start = System.currentTimeMillis();
        private String path;
        private HttpCallback callback;
        private Class<T> clazz;

        public HttpResponseHandler(String path, HttpCallback callback, Class<T> clazz) {
            this.path = path;
            this.callback = callback;
            this.clazz = clazz;
        }


        @Override
        public void onFailure(Request request, IOException e) {

            // 异常时，默认提示
            int code = HttpError.ERR_CODE_SERVER_ERROR;
            /*
            记录日志
             */
            int errorLogCode = -110;
            String msg = (e.getClass().getName() + " : " + e.getMessage());
            // 判断网络问题
            if (e instanceof UnknownHostException) {
                code = HttpError.ERR_CODE_NETWORK_CODE;
            } else if (e instanceof ConnectTimeoutException) {
                code = HttpError.ERR_CODE_CONNECT_TIMEOUT;
                errorLogCode = -10;
            } else if (e instanceof SocketTimeoutException) {
                code = HttpError.ERR_CODE_SOCKET_TIMEOUT;
                errorLogCode = -11;
            }
            final HttpError error = new HttpError(code, msg);
            //LogUtil.log(path, LogUtil.FAIL, (System.currentTimeMillis() - start), errorLogCode, null, null);

            /*
            提交给UI线程
             */
            respHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFail(error);
                }
            });
        }

        @Override
        public void onResponse(final Response response) throws IOException {

            try {
                final Result result = new Result();
                /**
                 *http请求失败
                 */
                if (!response.isSuccessful()) {
                    String msg = response.message();
                    int code = response.code();
                    HttpError error = new HttpError(code, msg);
                    result.success = false;
                    result.error = error;
                   // LogUtil.log(path, LogUtil.FAIL, (System.currentTimeMillis() - start), code, null, null);
                }
                /**
                 * http请求成功
                 */
                else {
                    String respStr = response.body().string();
                    response.body().close();
                    if (BuildConfig.DEBUG) {
                        Log.d("response", respStr);
                    }
                    result.raw = respStr;
                    JSONObject json = JSONObject.parseObject(respStr);
                    Integer success = json.getInteger("Result");
                    String rid = json.getString("rid");
                    String srt = json.getString("srt");
                    /**
                     * 服务端成功
                     */
                    if (success == 1) {
                        result.success = true;
                        Object data = json;
                        if (clazz == null) {    // 不需要解析，由调用方自己处理
                            result.data = data;
                        } else {
                            if (data instanceof JSONArray) {
                                JSONArray dataArray = (JSONArray) data;
                                List<T> dataList = JSON.parseArray(dataArray.toString(), clazz);
                                result.data = dataList;
                            } else if (data instanceof JSONObject) {
                                JSONObject dataObj = (JSONObject) data;
                                T obj = JSON.parseObject(dataObj.toString(), clazz);
                                result.data = obj;
                            }
                        }
                        //LogUtil.log(path, LogUtil.SUCCESS, (System.currentTimeMillis() - start), 0, rid, srt);
                    }
                    /**
                     * 服务端失败
                     */
                    else {
                        int code = json.getInteger("Result");
                        String msg = json.getString("Message");
                        HttpError error = new HttpError(code, msg);
                        result.success = false;
                        result.error = error;
                       // LogUtil.log(path, LogUtil.FAIL, (System.currentTimeMillis() - start), code, rid, srt);
                    }

                }


                /*
                提交给UI线程
                 */
                respHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result.success) {
                            callback.onSuccess(result.data);
                        } else {
                            if (null != result.error && result.error.getCode() == HttpError.ERR_CODE_NOT_LOGIN) {

                                //登出
                                //Helper.sharedHelper().signOut();
                                //Router.sharedRouter().open("signin");
                            }
                            callback.onFail(result.error);
                        }
                    }
                });
            } catch (Exception e) {
                Log.e(HttpClient.class.getName(), "http code = " + response.code() + ", response msg : " + response.message(), e);
                final HttpError error = new HttpError(HttpError.ERR_CODE_SERVER_ERROR, e.getClass().getName() + " : " + e.getMessage());
                respHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFail(error);
                    }
                });
            }
        }

        private static class Result {
            public boolean success;
            public HttpError error;
            public String raw;
            public Object data;
        }
    }

    public static Headers genHeaders(String url) {

//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String token = Helper.sharedHelper().getToken();
//        //lbsString() lat+","+lon 服务详情接口用到该字段计算距离
//        String signature = "";
//        try {
//            signature = UrlUtil.byteArrayToHex(NativeLibManager.getInstance().getSignature(url, timestamp));
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }

//        Headers headers = new Headers.Builder().
//                add("User-Agent", "KongGe/" + SHZApplication.getInstance().getVersionName() + " (" + DEVICE_NAME + "; Android " + SYSTEM_VERSION + "; en_GB)").
//                add("X-SHZ-AppId", "2").
//                add("X-SHZ-BBId", SHZApplication.getInstance().getBbId()).
//                add("X-SHZ-Timestamp", timestamp).
//                add("X-SHZ-Sign", signature).
//                add("X-SHZ-Token", token == null ? "" : token).
//                add("X-SHZ-DeviceId", SHZApplication.device + ":2").
//                add("X-SHZ-Coordinate", SHZApplication.getInstance().getLocationManager().lbsString()).
//                add("X-SHZ-ChannelName", SHZApplication.chanel == null ? "" : SHZApplication.chanel).
//                add(Constant.UUID, SHZApplication.uuid == null ? "" : SHZApplication.uuid).
//                add("X-SHZ-IMEI", SHZApplication.imei == null ? "" : SHZApplication.imei).
//                build();
        Headers headers = new Headers.Builder().add("X-AppId", "2").build();
        return headers;
//        return null;
    }

    public static String genAbsoluteUrl(String path) {
//        if (BuildConfig.DEBUG) {
//            return BASE_URL_PRE + path;
//        } else {
//            return BASE_URL_RELEASE + path;
//        }
        return BASE_URL_RELEASE + path;

    }

    /**
     * 业务回调
     *
     * @param <T>
     */
    public interface HttpCallback<T> {

        void onSuccess(T obj);

        void onFail(HttpError error);

    }

}

