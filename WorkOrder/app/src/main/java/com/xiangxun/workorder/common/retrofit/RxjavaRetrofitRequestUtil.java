package com.xiangxun.workorder.common.retrofit;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.Api;
import com.xiangxun.workorder.base.HttpRetrofitInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:Created by zhangyh2 on 2016/12/8 at 10:33.
 * Copyright (c) 2016 Organization Rich-Healthcare(D.L.) zhangyh2 All rights reserved.
 * TODO:
 */

public class RxjavaRetrofitRequestUtil {
    private volatile static RxjavaRetrofitRequestUtil instance;

    private static int VersionCode = APP.getInstance().getVersionCode();
    private static final String APPSYS_STRING = "Android_";
    private static final int REQUEST_TIME = 3000;
    private int interSize = 0;

    private OkHttpClient.Builder builder;

    private RxjavaRetrofitRequestUtil() {
        initClient();
    }

    private void initClient() {
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder = new OkHttpClient.Builder();
        //设置请求超时时间
        builder.connectTimeout(REQUEST_TIME, TimeUnit.SECONDS);
        //设置请求日志
        builder.addInterceptor(log);
        interSize = builder.interceptors().size();
        builder.addInterceptor(RxInterceptor.getInstance());

    }


    /**
     * @return 启动单例模式，加载进JVM时不进行初始化，调用getInstance（）初始化请求类。
     */
    public static RxjavaRetrofitRequestUtil getInstance() {
        if (instance == null) {
            synchronized (RxjavaRetrofitRequestUtil.class) {
                if (instance == null) {
                    instance = new RxjavaRetrofitRequestUtil();
                }
            }
        }
        return instance;
    }


    public HttpRetrofitInterface get() {
        //清理多余的头文件。
        while (builder.interceptors().size() > 0 && builder.interceptors().size() != interSize) {
            builder.interceptors().remove(builder.interceptors().size() - 1);
        }
        //清理多余的头文件。
        builder.addInterceptor(RxInterceptor.getInstance());
        DLog.i(builder.interceptors().size());
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Api.getUrl())
                .build();
        return retrofit.create(HttpRetrofitInterface.class);
    }

    public HttpRetrofitInterface post() {
        //清理多余的头文件。
        while (builder.interceptors().size() > 0 && builder.interceptors().size() != interSize) {
            builder.interceptors().remove(builder.interceptors().size() - 1);
        }
        //清理多余的头文件。
        builder.addInterceptor(RxInterceptor.getInstance());
        DLog.i(builder.interceptors().size());
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Api.getUrl())
                .build();
        return retrofit.create(HttpRetrofitInterface.class);
    }


}
