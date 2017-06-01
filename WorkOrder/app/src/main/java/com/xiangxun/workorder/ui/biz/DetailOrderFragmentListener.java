package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.bean.DetailChangeRoot;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zhangyuhui/Darly on 2017/5/31.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class DetailOrderFragmentListener implements FramePresenter {

    public interface DetailOrderFragmentInterface extends FrameView {

        void onLoginSuccess();

        void onLoginFailed();

        String getDataID();

        String getReason();
    }


    @Override
    public void onStart(Dialog dialog) {
        if (dialog != null)
            dialog.show();
    }


    public void commitConsel(int status, String id, String reason, final FrameListener<DetailChangeRoot> listener) {

        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        if (TextUtils.isEmpty(reason)) {
            listener.onFaild(0, "接收/退工说明不能为空");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().getOrder(status, id, reason).
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, DetailChangeRoot>() {
                    @Override
                    public DetailChangeRoot call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        return new Gson().fromJson(jsonObject.toString(), new TypeToken<DetailChangeRoot>() {
                        }.getType());
                    }
                })
                .subscribe(new Observer<DetailChangeRoot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(DetailChangeRoot data) {
                        if (data != null) {
                            if (data.getStatus() == 1) {
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
        if (dialog != null)
            dialog.dismiss();
    }
}
