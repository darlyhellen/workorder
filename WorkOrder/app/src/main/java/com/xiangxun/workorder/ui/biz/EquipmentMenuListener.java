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

        void end();
    }

    @Override
    public void onStart(Dialog dialog) {
        if (dialog != null) {
            dialog.show();
        }
    }

    @Override
    public void onStop(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


}
