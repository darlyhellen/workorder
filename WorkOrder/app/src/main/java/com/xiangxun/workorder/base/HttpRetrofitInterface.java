package com.xiangxun.workorder.base;

import com.google.gson.JsonObject;

import java.util.List;
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
     * @return
     * @TODO:用户工单查询GET接口，传递参数为<b>page,map</b>
     */
    @GET("server/workorder/refer/details/")
    Observable<JsonObject> getWorkOrder(@Query("pageNo") int page, @Query("status") String status, @Query("devicename") String devicename, @Query("devicecode") String devicecode, @Query("deviceip") String deviceip);

    /**
     * @TODO:服务更新接口
     */
    @GET("server/workorder/refer/totalCount")
    Observable<JsonObject> totalCount();

    /**
     * @TODO:http://localhost:8090/server/workorder/refer/totalWorkOrder/ 首頁滚动的接口
     */
    @GET("server/workorder/refer/totalWorkOrder/")
    Observable<JsonObject> totalWorkOrder();

    /**
     * @param status
     * @TODO:是否接收处理订单的接口
     */
    @GET("server/workorder/change/status/")
    Observable<JsonObject> getOrder(@Query("status") String status, @Query("id") String id, @Query("reason") String reason);

    /**
     * @param args
     * @TODO:上图片接口
     */
    @POST("server/workorder/change/upLoadPicture/")
    Observable<JsonObject> upLoadImage(@Body RequestBody args);

    /**
     * @TODO:图片下载接口。
     */
    @GET("server/workorder/refer/watchPicture/")
    Observable<JsonObject> downImage(@Query("id") String id);


    /**
     * @TODO:正常上报和异常上报接口
     */
    @GET("server/workorder/change/workorderUp/")
    Observable<JsonObject> upDataOrder(@Query("status") String status, @Query("id") String id, @Query("reason") String reason);

    /**
     * @TODO:查找設備的接口
     */
    @GET("server/device/refer/searchDevice/")
    Observable<JsonObject> searchDevice(@Query("type") String type, @Query("pageNo") int pageNo);

    /**
     * @TODO:巡检页面根据条件查询设备信息。
     */
    @GET("server/device/refer/searchOneDevice/")
    Observable<JsonObject> searchOneDevice(@Query("type") String type, @Query("code") String code, @Query("name") String name);

    /**
     * @TODO:巡检页面参数完整,提交工单接口.
     */
    @POST("server/perambulate/refer/perambulateUp/")
    Observable<JsonObject> perambulateUp(@Body RequestBody args);

    /**
     * @TODO:巡检列表页面
     */
    @GET("server/perambulate/refer/details/")
    Observable<JsonObject> details(@Query("pageNo") int pageNo);

    /**
     * @param version
     * @TODO:版本更新接口
     */
    @GET("server/version/")
    Observable<JsonObject> getVersion(@Query("version") int version);

    /**
     * @TODO:测试接口，替换接口。
     */
    @GET("server")
    Observable<JsonObject> test();

    /**
     * @TODO:测试接口，替换接口。
     */
    @GET("zyh/json.json")
    Observable<JsonObject> github();

}
