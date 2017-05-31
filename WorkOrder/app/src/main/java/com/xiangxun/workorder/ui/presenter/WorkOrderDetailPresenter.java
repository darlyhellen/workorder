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


    public WorkOrderData getData() {
        WorkOrderData data = new WorkOrderData();
        data.id = "17051120161587511c2d";
        data.devicename = "G70 K1741 900";
        data.devicecode = "610000000007031075";
        data.deviceip = "172.159.91.133";
        data.devicetype = "device";
        data.isouter = "0";
        data.position = "G70 K1741 900";
        data.companyid = "160321194637834dc78d";
        data.contact = "130521090208040c87f4ab27fd194da7";
        data.telephone = "14785236985";
        data.messages = "西安翔迅科技有限责任公司的李斯，你好：位于[G70 K1741 900]上的设备编号为[610000000007031075]的设备[G70 K1741 900]发生故障，请及时维修。";
        data.assignaccount = "00";
        data.assigntime = "2017-05-11 20:16:15";
        data.status = "0";
        data.isreassign = "0";
        //data.orgid = "170405094524399e776b";
        data.isleave = "0";
        data.assetid = "1704050945245628932d";
        return data;
    }
}
