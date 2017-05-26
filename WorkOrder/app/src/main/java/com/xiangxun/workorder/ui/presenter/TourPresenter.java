package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.ui.biz.MainListener;
import com.xiangxun.workorder.ui.biz.TourListener;
import com.xiangxun.workorder.ui.main.SetActivity;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 新增巡检工单解析类
 */
public class TourPresenter {

    private TourListener biz;

    private TourListener.TourInterface view;

    public TourPresenter(TourListener.TourInterface view) {
        this.view = view;
        this.biz = new TourListener();
    }

    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     */
    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                view.end();
                break;
            case R.id.title_view_right_Flipper01:
                DLog.i("点击提交");
                break;
        }
    }
}
