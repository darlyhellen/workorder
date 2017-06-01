package com.xiangxun.workorder.common;

/**
 * Created by Zhangyuhui/Darly on 2017/6/1.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 返回状态。
 */
public class WorkOrderUtils {

    public static String findStatus(int status) {
        String result = null;
        switch (status) {
            case 0:
                result = "已派发";
                break;
            case 1:
                result = "已接收";
                break;
            case 2:
                result = "已退回";
                break;
            case 3:
                result = "已转派";
                break;
            case 4:
                result = "已上报";
                break;
            case 5:
                result = "遗留中";
                break;
            case 6:
                result = "已关闭";
                break;
            case 7:
                result = "已评估";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }
}
