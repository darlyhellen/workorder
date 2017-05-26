package com.xiangxun.workorder.base;

import com.google.gson.JsonObject;

import java.util.Map;

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
     * @param route
     * @return
     * @TODO:用户登录POST接口，传递参数为<b>route</b>
     */
    //@FormUrlEncoded//添加这行注解，否则参数报错。Post请求
    @POST("login")
    Observable<JsonObject> postlogin(@Body RequestBody route);

    /**
     * @param loginName
     * @param password
     * @param keyValue
     * @return
     * @TODO:用户登录GET接口，传递参数为<b>username,password,sim</b>
     */
    @GET("login")
    Observable<JsonObject> getlogin(@Query("loginName") String loginName, @Query("password") String password, @Query("keyValue") String keyValue);

    /**
     * @param page
     * @param map
     * @return
     * @TODO:用户工单查询GET接口，传递参数为<b>page,map</b>
     */
    @GET("server/workorder/refer/details/")
    Observable<JsonObject> getWorkOrder(@Query("page") int page, @Query("status") String status, @Query("devicename") String devicename, @Query("devicecode") String devicecode, @Query("deviceip") String deviceip);
}
