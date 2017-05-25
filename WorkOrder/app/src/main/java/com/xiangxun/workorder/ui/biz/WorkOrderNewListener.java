package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.LoginRoot;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;
import com.xiangxun.workorder.common.urlencode.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * @author zhangyh2 LoginUser 下午3:42:16 TODO 用户登录获取数据传递给了接口
 */
public class WorkOrderNewListener implements FramePresenter {

    public void getWorkOrder(int page, Map<String, String> map, final FrameListener<WorkOrderRoot> listener) {

        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().getWorkOrder(page, map).
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, WorkOrderRoot>() {
                    @Override
                    public WorkOrderRoot call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        WorkOrderRoot root = new Gson().fromJson(jsonObject, new TypeToken<WorkOrderRoot>() {
                        }.getType());
                        return root;
                    }
                })
                .subscribe(new Observer<WorkOrderRoot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(WorkOrderRoot data) {
                        if (data != null) {
                            if (data.getData() != null && data.getStatus() == 1) {
                                listener.onSucces(data);
                            } else {
                                listener.onFaild(0, data.getMessage());
                            }
                        } else {
                            listener.onFaild(0, "解析错误");
                        }
                    }
                });
    }

    @Override
    public void onStart(Dialog loading) {
        loading.show();
    }

    @Override
    public void onStop(Dialog loading) {
        loading.dismiss();
    }
}
