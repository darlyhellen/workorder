package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.bean.DetailImageRoot;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 新增巡检工单接口页面
 */
public class TourListener implements FramePresenter {

    public interface TourInterface extends FrameView {

        TextView findName();

        void end();
    }

    @Override
    public void onStart(Dialog dialog) {
        if (dialog != null) {
            dialog.show();
        }
    }

    /**
     * 编辑完成后进行工单提交
     */
    public void commitTour(final FrameListener<String> listener) {
        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().totalWorkOrder().
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, String>() {
                    @Override
                    public String call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        return jsonObject.toString();
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(String data) {
                        if (data != null) {

//                            if (data.getStatus() == 1 && data.getData() != null) {
//                                listener.onSucces(data);
//                            } else {
//                                listener.onFaild(0, data.getMessage());
//                            }

                        } else {
                            listener.onFaild(0, "解析错误");
                        }
                    }
                });
    }

    /**
     * @TODO:获取设备信息接口。
     */
    public void getEquipment(final FrameListener<Object> listener) {
        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().totalWorkOrder().
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, String>() {
                    @Override
                    public String call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        return jsonObject.toString();
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(String data) {
                        if (data != null) {

//                            if (data.getStatus() == 1 && data.getData() != null) {
//                                listener.onSucces(data);
//                            } else {
//                                listener.onFaild(0, data.getMessage());
//                            }

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
