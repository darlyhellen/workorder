package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.Api;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.common.retrofit.RxTestJson;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 我的工单监听类
 */
public class EquipmentListListener implements FramePresenter {

    public interface EquipmentListInterface extends FrameView {

        void onWorkOrderSuccess(List<EquipmentInfo> datas);

        void onWorkOrderFailed();

        String getDevicename();

        String getDevicenum();

        String getDeviceip();

        void end();
    }

    @Override
    public void onStart(Dialog dialog) {
        if (dialog != null)
            dialog.show();
    }

    public void getEquipment(final int page, String status, String devicename, String devicecode, String deviceip, final FrameListener<EquipmentRoot> listener) {
        if (Api.ISOUTLINE){
            listener.onSucces(RxTestJson.equipment(status));
            return;
        }
        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().searchDevice(status, page).
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, EquipmentRoot>() {
                    @Override
                    public EquipmentRoot call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        EquipmentRoot root = new Gson().fromJson(jsonObject, new TypeToken<EquipmentRoot>() {
                        }.getType());
                        if (root != null && root.getStatus() == 1 && root.getData() != null) {
                            SharePreferHelp.putValue(AppEnum.EquipmentRoot.getDec() + page, root);
                        }
                        return root;
                    }
                })
                .subscribe(new Observer<EquipmentRoot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EquipmentRoot root = (EquipmentRoot) SharePreferHelp.getValue(AppEnum.EquipmentRoot.getDec() + page);
                        if (root != null) {
                            listener.onSucces(root);
                        } else {
                            listener.onFaild(1, e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(EquipmentRoot data) {
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
    public void onStop(Dialog dialog) {
        if (dialog != null)
            dialog.dismiss();
    }


}
