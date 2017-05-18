package com.xiangxun.workorder.base;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Author:Created by zhangyh2 on 2016/12/15 at 11:48.
 * Copyright (c) 2016 Organization Rich-Healthcare(D.L.) zhangyh2 All rights reserved.
 * TODO: 服务端接口对应接口实现类。
 */

public interface HttpRetrofitInterface {
    /**
     * 上午11:13:12 TODO 用户登录接口，传递参数为<b>username,password,sim</b>
     */
    //@FormUrlEncoded//添加这行注解，否则参数报错。Post请求
    @POST("login/login/authority/")
    Observable<JsonObject> postlogin(@Body RequestBody route);

    @GET("login/login/authority/")
    Observable<JsonObject> getlogin(@Query("loginName") String loginName, @Query("password") String password, @Query("keyValue") String keyValue);


}
