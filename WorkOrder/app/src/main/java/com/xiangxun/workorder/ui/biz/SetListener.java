package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.SetModel;
import com.xiangxun.workorder.bean.VersionRoot;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;
import com.xiangxun.workorder.common.urlencode.Tools;

import java.io.File;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:设置页面接口
 */
public class SetListener implements FramePresenter {


    public interface SetInterface extends FrameView {

        void getUserDate(List<SetModel> data);

        void end();
    }


    public void getSize(FrameListener<Long> listener) {
        new GetCacheSizeTask(listener).execute();
    }

    @Override
    public void onStart(Dialog loading) {
        loading.show();
    }

    /**
     * 获取版本更新接口数据
     */
    public void findNewVersion(int version, final FrameListener<VersionRoot> listener) {
        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().getVersion(version).
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, VersionRoot>() {
                    @Override
                    public VersionRoot call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        VersionRoot root = new Gson().fromJson(jsonObject, new TypeToken<VersionRoot>() {
                        }.getType());
                        return root;
                    }
                })
                .subscribe(new Observer<VersionRoot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(VersionRoot data) {
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
    public void onStop(Dialog loading) {
        loading.dismiss();
    }

    private class GetCacheSizeTask extends AsyncTask<Object, Object, Long> {

        FrameListener<Long> listener;

        public GetCacheSizeTask(FrameListener<Long> listener) {
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(Object... params) {
            long lenth = 0;
            File imageCacheFile = new File(AppEnum.ROOT);
            if (imageCacheFile.exists()) {
                try {
                    lenth = Tools.getFileSize(imageCacheFile);
                } catch (Exception e) {
                    lenth = 0;
                }
            } else {
                lenth = 0;
            }
            return lenth;
        }


        @Override
        protected void onPostExecute(Long result) {
            listener.onSucces(result);
        }
    }

}
