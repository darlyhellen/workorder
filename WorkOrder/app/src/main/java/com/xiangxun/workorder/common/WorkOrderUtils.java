package com.xiangxun.workorder.common;

import android.graphics.Color;
import android.widget.TextView;

import com.xiangxun.workorder.R;

/**
 * Created by Zhangyuhui/Darly on 2017/6/1.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 返回状态。
 */
public class WorkOrderUtils {

    public static void findStatus(int status, TextView tv) {
        String result = null;
        switch (status) {
            case 0:
                result = "已派发";
                tv.setBackgroundResource(R.drawable.status_bg_0);
                break;
            case 1:
                result = "已接收";
                tv.setBackgroundResource(R.drawable.status_bg_1);
                break;
            case 2:
                result = "已退回";
                tv.setBackgroundResource(R.drawable.status_bg_2);
                break;
            case 3:
                result = "已转派";
                tv.setBackgroundResource(R.drawable.status_bg_3);
                break;
            case 4:
                result = "已上报";
                tv.setBackgroundResource(R.drawable.status_bg_4);
                break;
            case 5:
                result = "遗留中";
                tv.setBackgroundResource(R.drawable.status_bg_5);
                break;
            case 6:
                result = "已关闭";
                tv.setBackgroundResource(R.drawable.status_bg_6);
                break;
            case 7:
                result = "已评估";
                tv.setBackgroundResource(R.drawable.status_bg_7);
                break;
            default:
                result = "已完成";
                tv.setBackgroundResource(R.drawable.status_bg_8);
                break;
        }
        tv.setPadding(10, 10, 10, 10);
        tv.setTextColor(Color.WHITE);
        tv.setText(result);
    }
}
