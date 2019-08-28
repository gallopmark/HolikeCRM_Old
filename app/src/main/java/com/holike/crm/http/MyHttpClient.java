package com.holike.crm.http;


import com.holike.crm.util.Constants;
import com.holike.crm.util.LogCat;
import com.holike.crm.util.PackageUtil;
import com.holike.crm.util.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by Administrator on 2016/12/8 0008.
 * 请求框架，请求头带版本(version)默认参数
 */

public class MyHttpClient {

    /**
     * 只带url的get请求
     */
    public static void get(String url, RequestCallBack callBack) {
        get(url, null, null, callBack);
    }

    /**
     * 带参数的get请求
     */
    public static void get(String url, Map<String, String> params, RequestCallBack callBack) {
        get(url, null, params, callBack);
    }

    /**
     * 带请求头和参数的get请求
     */
    public static void get(String url, Map<String, String> header, Map<String, String> params, RequestCallBack<?> callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        header.put(Constants.VERSION, String.valueOf(PackageUtil.getVersionCode()));
        header.put(Constants.CLIENT, Constants.ANDROID);
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE, ""));
        HttpClient.getInstance().get(url, header, params, callBack);
    }

    /**
     * 只带url的post请求
     */
    public static void post(String url, RequestCallBack<?> callBack) {
        post(url, null, null, callBack);
    }

    /**
     * 带参数的post请求
     */
    public static void post(String url, Map<String, String> params, RequestCallBack<?> callBack) {
        post(url, null, params, callBack);
    }

    /**
     * 带cliid请求头和参数的post请求
     */
    public static void postByCliId(String url, Map<String, String> header, Map<String, String> params, RequestCallBack<?> callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        header.put(Constants.CLI_ID, SharedPreferencesUtils.getString(Constants.CLI_ID));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE, ""));
        post(url, header, params, callBack);
    }

    /**
     * 带请求头和参数的post请求
     */
    public static void post(String url, Map<String, String> header, Map<String, String> params, RequestCallBack<?> callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        header.put(Constants.VERSION, String.valueOf(PackageUtil.getVersionCode()));
        header.put(Constants.CLIENT, Constants.ANDROID);
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE, ""));
        HttpClient.getInstance().post(url, header, params, callBack);
    }

    /**
     * 带参数的body post请求
     */
    public static void postBody(String url, String params, RequestCallBack<?> callBack) {
        postBody(url, null, params, callBack);
    }

    /**
     * 带请求头和参数的body post请求
     */
    public static void postBody(String url, Map<String, String> header, String params, RequestCallBack<?> callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        header.put(Constants.VERSION, String.valueOf(PackageUtil.getVersionCode()));
        header.put(Constants.CLIENT, Constants.ANDROID);
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE, ""));
        HttpClient.getInstance().post(url, header, params, callBack);
    }

    /**
     * 带cliid请求头和参数的body post请求
     */
    public static void postBodyById(String url, Map<String, String> header, String params, RequestCallBack<?> callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        header.put(Constants.CLI_ID, SharedPreferencesUtils.getString(Constants.CLI_ID));
        postBody(url, header, params, callBack);
    }

    /**
     * 可设置超时时间请求
     */
    public static void postByTimeout(String url, Map<String, String> header, Map<String, String> params, int timeout, RequestCallBack<?> callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        header.put(Constants.CLI_ID, SharedPreferencesUtils.getString(Constants.CLI_ID));
        header.put(Constants.VERSION, String.valueOf(PackageUtil.getVersionCode()));
        header.put(Constants.CLIENT, Constants.ANDROID);
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE, ""));
        OkHttpClient.Builder ob = new OkHttpClient.Builder();
        ob.readTimeout(timeout, TimeUnit.SECONDS).connectTimeout(timeout, TimeUnit.SECONDS);
        //----开启builder日志 等级BODY----
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        ob.addInterceptor(loggingInterceptor);
        //-------
        OkHttpClient client = ob.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HttpClient.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
        PostService service = retrofit.create(PostService.class);
        Observable<String> call;
        if (params == null) {
            LogCat.v_request("POST url:" + url + "\nheader:" + header.toString());
            call = service.postHeader(url, header);
        } else {
            LogCat.v_request("POST url:" + url + "\nheader:" + header.toString() + "\nparams:" + params.toString());
            call = service.postHeaderParams(url, header, params);
        }
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(callBack);
    }

    public static void upload(String url, Map<String, String> header, Map<String, String> params, List<MultipartBody.Part> parts, RequestCallBack<?> callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        header.put(Constants.VERSION, String.valueOf(PackageUtil.getVersionCode()));
        header.put(Constants.CLIENT, Constants.ANDROID);
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        HttpClient.getInstance().upload(url, header, params, parts, callBack);
    }

    public static void upload(String url, Map<String, String> header, String relationId, List<MultipartBody.Part> parts, RequestCallBack<?> callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        header.put(Constants.VERSION, String.valueOf(PackageUtil.getVersionCode()));
        header.put(Constants.CLIENT, Constants.ANDROID);
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        HttpClient.getInstance().upload(url, header, relationId, parts, callBack);
    }


}
