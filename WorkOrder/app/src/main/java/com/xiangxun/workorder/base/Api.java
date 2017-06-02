package com.xiangxun.workorder.base;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.BuildConfig;
import com.xiangxun.workorder.common.urlencode.EncodeTools;
import com.xiangxun.workorder.common.urlencode.Tools;

/**
 * Created by Zhangyuhui/Darly on 2017/5/18.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:公司接口地址参数。
 */
public class Api {

    private static String root;


    public static String getRoot() {
        if (root == null) {
            if (BuildConfig.DEBUG) {
                return "http://193.169.100.153:8090/";
            } else {
                return "http://10.10.15.111:8090/";
            }
        }
        return root;
    }

    public static void setRoot(String root) {
        if (root.length() > 1) {
            Api.root = "http://" + root + "/";
            DLog.i(Api.root);
        }
    }

    public static String getUrl() {
        return EncodeTools.Utf8URLencode(EncodeTools.getEnUrl(Tools.getSB().append(getRoot()).toString()));
    }
}
