package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.biz.WorkOrderDetailListener;
import com.xiangxun.workorder.ui.main.LbsAmapActivity;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 工单详情页面解析类
 */
public class WorkOrderDetailPresenter {
    private WorkOrderDetailListener biz;
    private WorkOrderDetailListener.WorkOrderDetailInterface view;

    public WorkOrderDetailPresenter(WorkOrderDetailListener.WorkOrderDetailInterface view) {
        this.view = view;
        this.biz = new WorkOrderDetailListener();
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
                DLog.i("点击位置");
                Intent intent = new Intent(context, LbsAmapActivity.class);
                intent.putExtra("data", view.getOrderData());
                context.startActivity(intent);
                break;
        }
    }
}
