package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.ui.EquipmentMenuAcitvity;
import com.xiangxun.workorder.ui.biz.MainListener;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:首页解析类
 */
public class MainPresenter {

    private MainListener biz;

    private MainListener.MainInterface view;

    public MainPresenter(MainListener.MainInterface view) {
        this.view = view;
        this.biz = new MainListener();
    }

    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     */
    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.title_view_right_linear:
                DLog.i(getClass().getSimpleName(), "title_view_right_linear");
                context.startActivity(new Intent(context, EquipmentMenuAcitvity.class));
                break;
        }
    }
}
