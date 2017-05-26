package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.common.db.ThreadInfo;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.Patrol;
import com.xiangxun.workorder.ui.MaintenanceActivity;
import com.xiangxun.workorder.ui.biz.MaintenanceListener.MaintenanceInterface;
import com.xiangxun.workorder.ui.main.DownLoadActivity;
import com.xiangxun.workorder.ui.main.SetActivity;
import com.xiangxun.workorder.ui.video.VideoRecordActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 运维首页解析类
 */
public class MaintenancePresenter {

    private MaintenanceInterface view;

    public MaintenancePresenter(MaintenanceInterface view) {
        this.view = view;
    }

    public List<Patrol> findMaintenance() {
        List<Patrol> data = new ArrayList<>();
        data.add(new Patrol(10, R.string.maintenance_order, R.mipmap.work_order_search, 0));
        data.add(new Patrol(20, R.string.maintenance_tour, R.mipmap.patrol_normal, 0));
        return data;
    }

    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.xw_share:
                //進入設置頁面
                DLog.i("设置按钮点击");
                context.startActivity(new Intent(context, SetActivity.class));
                break;
            default:
                break;
        }
    }
}
