package com.xiangxun.workorder.common.retrofit;

import android.text.TextUtils;

import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/18.
 */

public class RxInterceptor implements Interceptor {

    private volatile static RxInterceptor instance;
    private static int VersionCode = APP.getInstance().getVersionCode();
    private static final String APPSYS_STRING = "Android_";

    /**
     * @return 启动单例模式，加载进JVM时不进行初始化，调用getInstance（）初始化请求类。
     */
    public static RxInterceptor getInstance() {
        if (instance == null) {
            synchronized (RxInterceptor.class) {
                if (instance == null) {
                    instance = new RxInterceptor();
                }
            }
        }
        return instance;
    }

    private RxInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String userID = SharePreferHelp.getValue(AppEnum.USERID.getDec(), null);
        String loginName = SharePreferHelp.getValue(AppEnum.USERNAME.getDec(), null);
        DLog.i(userID+"--"+loginName);
        if (TextUtils.isEmpty(userID) && TextUtils.isEmpty(loginName)) {
            builder.addHeader("Content-Type", "application/json;charset=UTF-8")
                    .addHeader("Accept", "application/json")
                    .addHeader("charset", "utf-8")
                    .addHeader("version", APPSYS_STRING + VersionCode)
                    .build();
        } else {
            builder.addHeader("Content-Type", "application/json;charset=UTF-8")
                    .addHeader("Accept", "application/json")
                    .addHeader("charset", "utf-8")
                    .addHeader("userId", userID)
                    .addHeader("loginName", loginName)
                    .addHeader("version", APPSYS_STRING + VersionCode)
                    .build();
        }
        return chain.proceed(builder.build());
    }
}
