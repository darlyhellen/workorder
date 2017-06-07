package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.bean.GroupData;
import com.xiangxun.workorder.bean.ObjectData;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 首页接口请求类
 */
public class EquipmentMenuListener implements FramePresenter {

    public interface EquipmentMenuInterface extends FrameView {

        void onLoginSuccess(List<GroupData> data);

        void onLoginFailed();

        void end();
    }

    @Override
    public void onStart(Dialog dialog) {
        if (dialog != null) {
            dialog.show();
        }
    }

    /**
     * @TODO:获取设备信息接口。
     */
    public void getEquipment(final FrameListener<ObjectData> listener) {
        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().test().
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, ObjectData>() {
                    @Override
                    public ObjectData call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        return new Gson().fromJson(jsonObject.toString(), new TypeToken<ObjectData>() {
                        }.getType());
                    }
                })
                .subscribe(new Observer<ObjectData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(ObjectData data) {
                        if (data != null) {

                            if (data.getStatus() == 1 && data.getData() != null) {
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
    public void onStop(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


}
