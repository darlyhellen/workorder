package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.bean.DetailChangeRoot;
import com.xiangxun.workorder.bean.DetailImageRoot;
import com.xiangxun.workorder.common.image.BitmapChangeUtil;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
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
public class DetailImageFragmentListener implements FramePresenter {


    public interface DetailImageFragmentInterface extends FrameView {

        void onLoginSuccess(List<String> data);

        void onLoginFailed();

        String getDataID();
    }


    @Override
    public void onStart(Dialog dialog) {
        if (dialog != null)
            dialog.show();
    }


    public void downImage(String id, final FrameListener<DetailImageRoot> listener) {
        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().downImage(id).
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, DetailImageRoot>() {
                    @Override
                    public DetailImageRoot call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        return new Gson().fromJson(jsonObject.toString(), new TypeToken<DetailImageRoot>() {
                        }.getType());
                    }
                })
                .subscribe(new Observer<DetailImageRoot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(DetailImageRoot data) {
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
        if (dialog != null)
            dialog.dismiss();
    }
}
