package com.xiangxun.workorder.base;

import android.text.TextUtils;

import com.hellen.baseframe.common.utiltools.SharePreferHelp;
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

    private static String ip;

    private static String port;


    public static String getRoot() {
        if (root == null) {
            return findMoe();
        }
        return root;
    }

    public static void setRoot(String ip, String port) {
        if (!TextUtils.isEmpty(ip)) {
            SharePreferHelp.putValue(AppEnum.IP.getDec(), ip);
            Api.ip = ip;
        }
        if (!TextUtils.isEmpty(port)) {
            SharePreferHelp.putValue(AppEnum.PORT.getDec(), port);
            Api.port = port;
        }
    }

    public static String getIp() {
        if (ip == null) {
            if (TextUtils.isEmpty(SharePreferHelp.getValue(AppEnum.IP.getDec(), null))) {
                ip = "193.169.100.153";
            } else {
                ip = SharePreferHelp.getValue(AppEnum.IP.getDec(), null);
            }
        }
        return ip;
    }


    public static String getPort() {
        if (port == null) {
            if (TextUtils.isEmpty(SharePreferHelp.getValue(AppEnum.PORT.getDec(), null))) {
                port = "8090";
            } else {
                port = SharePreferHelp.getValue(AppEnum.PORT.getDec(), null);
            }
        }
        return port;
    }

    public static String findMoe() {
        return "http://" + getIp() + ":" + getPort() + "/";
    }

    public static String getUrl() {
        return EncodeTools.Utf8URLencode(EncodeTools.getEnUrl(Tools.getSB().append(getRoot()).toString()));
    }
}
