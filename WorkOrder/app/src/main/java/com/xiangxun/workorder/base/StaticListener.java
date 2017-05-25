package com.xiangxun.workorder.base;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.service.WorkOrderNewService;

/**
 * Created by Zhangyuhui/Darly on 2017/5/25.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 强制实现静态接口。
 */
public class StaticListener {

    private static StaticListener instance;

    private StaticListener() {
    }

    public static StaticListener getInstance() {
        if (instance == null) {
            instance = new StaticListener();
        }
        return instance;
    }

    private static RefreshMainUIListener refreshMainUIListener;

    public static void setRefreshMainUIListener(RefreshMainUIListener refreshMainUIListener) {
        StaticListener.refreshMainUIListener = refreshMainUIListener;
    }

    public static RefreshMainUIListener getRefreshMainUIListener() {
        if (refreshMainUIListener == null) {
            DLog.i("RefreshMainUIListener is not instance！ Please instance to use");
        }
        return refreshMainUIListener;
    }

    public static interface RefreshMainUIListener {

        void refreshMainUI(int num);
    }
}

