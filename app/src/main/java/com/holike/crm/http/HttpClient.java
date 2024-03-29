package com.holike.crm.http;

import com.holike.crm.util.LogCat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
//import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpClient {
    private volatile static HttpClient instance = null;
    public static final String BASE_URL = "http://39.108.84.67:3333";
    private static final int DEFAULT_TIMEOUT = 20;

    private OkHttpClient httpClient;
    private Retrofit retrofit;
    private List<RequestCallBack> callBacks;
    private RequestCallBack currentCallBack;

    //构造方法私有
    private HttpClient() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //开启builder日志 等级BODY
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClientBuilder.addInterceptor(loggingInterceptor);
        //---

        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient = httpClientBuilder.build();
        callBacks = new ArrayList<>();

        retrofit = new Retrofit.Builder().client(httpClient).addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(BASE_URL).build();

    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }
//
//    //在访问HttpMethods时创建单例
//    private static class SingletonHolder {
//        private static final HttpClient INSTANCE = new HttpClient();
//    }
//
//    //获取单例
//    public static HttpClient getInstance() {
//        return SingletonHolder.INSTANCE;
//    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    /*get请求方式*/
    public <T> void get(String url, Map<String, String> header, Map<String, String> params, RequestCallBack<T> callBack) {
        if (url == null) {
            return;
        }
        GetService service = retrofit.create(GetService.class);
        Observable<String> call;
        if (header == null) {
            if (params == null) {
                call = service.get(url);
                LogCat.v_request("GET url:" + url);
            } else {
                call = service.getParams(url, params);
                LogCat.v_request("GET url:" + url + "\nparams:" + params.toString());
            }
        } else {
            if (params == null) {
                call = service.getHeader(url, header);
                LogCat.v_request("GET url:" + url + "\nheader:" + header.toString());
            } else {
                call = service.getHeaderParams(url, header, params);
                LogCat.v_request("GET url:" + url + "\nheader:" + header.toString() + "\nparams:" + params);
            }
        }
        currentCallBack = callBack;
        callBacks.add(currentCallBack);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(callBack);
    }

    /*post请求方式*/
    public void post(String url, Map<String, String> header, Map<String, String> params, RequestCallBack<?> callBack) {
        if (url == null) {
            return;
        }
        PostService service = retrofit.create(PostService.class);
        Observable<String> call;
        if (header == null) {
            if (params == null) {
                call = service.post(url);
            } else {
                call = service.postParams(url, params);
                LogCat.v_request("POST url:" + url + "\nparams:" + params.toString());
            }
        } else {
            if (params == null) {
                call = service.postHeader(url, header);
                LogCat.v_request("POST url:" + url + "\nheader:" + header.toString());
            } else {
                call = service.postHeaderParams(url, header, params);
                LogCat.v_request("POST url:" + url + "\nheader:" + header.toString() + "\nparams:" + params.toString());
            }
        }
        currentCallBack = callBack;
        callBacks.add(currentCallBack);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(callBack);
    }

    /*参数为body*/
    public void post(String url, Map<String, String> header, String params, RequestCallBack<?> callBack) {
        if (url == null || params == null) {
            return;
        }
        BodyService service = retrofit.create(BodyService.class);
        Observable<String> call;
        LogCat.v_request(url);
        if (header == null) {
            call = service.post(url, params);
            LogCat.v_request("POST url:" + url + "\nparams:" + params);
        } else {
            call = service.post(url, header, params);
            LogCat.v_request("POST url:" + url + "\nheader:" + header.toString() + "\nparams:" + params);
        }
        currentCallBack = callBack;
        callBacks.add(currentCallBack);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(callBack);
    }

    /*上传文件*/
    public void upload(String url, Map<String, String> header, Map<String, String> params, List<MultipartBody.Part> parts, RequestCallBack<?> callBack) {
        if (url == null) {
            return;
        }
        UploadService service = retrofit.create(UploadService.class);
        Observable<String> call;
        if (header == null) {
            if (params == null) {
                call = service.upLoad(url, parts);
                LogCat.v_request("UPLOAD url:" + url + "\nparts:" + parts.toString());
            } else {
                call = service.upLoadParam(url, params, parts);
                LogCat.v_request("UPLOAD url:" + url + "\nparams:" + params.toString() + "\nparts:" + parts.toString());
            }
        } else {
            if (params == null) {
                call = service.upLoadHeader(url, header, parts);
                LogCat.v_request("UPLOAD url:" + url + "\nheader:" + header.toString() + "\nparts:" + parts.toString());
            } else {
                call = service.upLoad(url, header, params, parts);
                LogCat.v_request("UPLOAD url:" + url + "\nheader:" + header.toString() + "\nparams:" + params + "\nparts:" + parts.toString());
            }
        }
        currentCallBack = callBack;
        callBacks.add(currentCallBack);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(callBack);
    }

    /*上传文件*/
    public void upload(String url, Map<String, String> header, String relationId, List<MultipartBody.Part> parts, RequestCallBack<?> callBack) {
        if (url == null) {
            return;
        }
        UploadService service = retrofit.create(UploadService.class);
        Observable<String> call = service.upLoad(url, header, toRequestBody(relationId), parts);
        currentCallBack = callBack;
        callBacks.add(currentCallBack);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(callBack);
        LogCat.v_request("UPLOAD url:" + url + "\nheader:" + header.toString() + "\nparts:" + parts.toString());
    }

    private static RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    /**
     * 取消当前请求
     */
    public void cancel() {
        if (currentCallBack != null) {
            currentCallBack.cancel();
        }
    }

    /**
     * 取消全部请求
     */
    public void cancelAll() {
        if (callBacks != null && callBacks.size() > 0) {
            for (RequestCallBack callBack : callBacks) {
                if (callBack != null) {
                    callBack.cancel();
                }
            }
            callBacks.clear();
        }
    }

    /**
     * 返回成功移除响应
     */
    public void remove(RequestCallBack callBack) {
        if (callBacks != null && callBacks.size() > 0) {
            callBacks.remove(callBack);
        }
    }
}
