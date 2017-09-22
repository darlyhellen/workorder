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
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.bean.UpTourRoot;
import com.xiangxun.workorder.common.image.BitmapChangeUtil;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

        void end();

        String getCode();

        void onNameCodeSuccess(List<EquipmentInfo> info);

        void onNameCodeFailed();

        String getName();

        String getDeclare();

        List<String> getImageData();

        String getAddress();

        String getlongitude();

        String getlatitude();


        void onTourSuccess(String s);

        void onTourFailed();
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
    public void commitTour(boolean isCheck, EquipmentInfo info, String declare, List<String> url, String type, String address, String longitude, String latitude, final FrameListener<UpTourRoot> listener) {
        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        if (!isCheck) {
            listener.onFaild(0, "请选择设备信息");
            return;
        }
        if (TextUtils.isEmpty(declare)) {
            listener.onFaild(0, "说明信息不能为空");
            return;
        }
        if (url != null && url.size() < 2) {
            listener.onFaild(0, "请选择照片信息");
            return;
        }

        if (info == null) {
            listener.onFaild(0, "请选择设备信息");
            return;
        }
        if (TextUtils.isEmpty(longitude)) {
            listener.onFaild(0, "经度不能为空");
            return;
        }
        if (TextUtils.isEmpty(latitude)) {
            listener.onFaild(0, "纬度不能为空");
            return;
        }

        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("reason", declare).
                        addFormDataPart("note", "").
                        addFormDataPart("deviceid", info.deviceid == null ? "" : info.deviceid).
                        addFormDataPart("code", info.code == null ? "" : info.code).
                        addFormDataPart("assetname", info.assetname == null ? "" : info.assetname).
                        addFormDataPart("orgname", info.orgname == null ? "" : info.orgname).
                        addFormDataPart("installplace", info.installplace == null ? "" : info.installplace).
                        addFormDataPart("ip", info.ip == null ? "" : info.ip).
                        addFormDataPart("address", address == null ? "" : address).
                        addFormDataPart("longitude", longitude).
                        addFormDataPart("latitude", latitude).
                        addFormDataPart("type", type);

        if (url != null && url.size() >= 2) {
            File file = new File(url.get(0));
            builder.addFormDataPart("picture1", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        if (url != null && url.size() >= 3) {
            File file = new File(url.get(1));
            builder.addFormDataPart("picture2", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        if (url != null && url.size() >= 4) {
            File file = new File(url.get(2));
            builder.addFormDataPart("picture3", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        RequestBody requestBody = builder.build();
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().post().perambulateUp(requestBody).
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, UpTourRoot>() {
                    @Override
                    public UpTourRoot call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        return new Gson().fromJson(jsonObject.toString(), new TypeToken<UpTourRoot>() {
                        }.getType());
                    }
                })
                .subscribe(new Observer<UpTourRoot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(UpTourRoot data) {
                        if (data != null) {
                            if (data.getStatus() == 1) {
                                listener.onSucces(data);
                            } else {
                                listener.onFaild(1, data.getMessage());
                            }
                        } else {
                            listener.onFaild(0, "解析异常");
                        }
                    }
                });
    }

    /**
     * @TODO:获取设备信息接口。
     */
    public void getEquipment(String type, String code, String name, final FrameListener<EquipmentRoot> listener) {
        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().searchOneDevice(type, code, name).
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, EquipmentRoot>() {
                    @Override
                    public EquipmentRoot call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        return new Gson().fromJson(jsonObject, new TypeToken<EquipmentRoot>() {
                        }.getType());
                    }
                })
                .subscribe(new Observer<EquipmentRoot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(EquipmentRoot data) {
                        if (data != null) {
                            if (data.getData() != null && data.getStatus() == 1) {
                                if (data.getData().size() == 0) {
                                    listener.onFaild(0, "查无数据，请重新查询");
                                } else {
                                    listener.onSucces(data);
                                }
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
