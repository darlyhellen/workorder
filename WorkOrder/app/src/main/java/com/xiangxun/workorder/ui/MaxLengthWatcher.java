package com.xiangxun.workorder.ui;

import android.text.InputFilter;
import android.text.Spanned;

import com.hellen.baseframe.common.obsinfo.ToastApp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhangyuhui/Darly on 2017/7/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class MaxLengthWatcher implements InputFilter {

    public interface MaxLengthUiListener {

        void onUiChanged(int num);
    }

    private int MAX_EN;// 最大英文/数字长度 一个汉字算两个字母
    private String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字

    private MaxLengthUiListener lengthUiListener;

    public MaxLengthWatcher(int mAX_EN, MaxLengthUiListener lengthUiListener) {
        super();
        MAX_EN = mAX_EN;
        this.lengthUiListener = lengthUiListener;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        int destCount = dest.toString().length();
//                + getChineseCount(dest.toString());
        int sourceCount = source.toString().length();
//                + getChineseCount(source.toString());
        if (destCount + sourceCount > MAX_EN) {
            ToastApp.showToast("输入文字长度不能大于" + MAX_EN);
            lengthUiListener.onUiChanged(MAX_EN);
            return "";
        } else {
            lengthUiListener.onUiChanged(destCount + sourceCount);
            return source;
        }
    }

    private int getChineseCount(String str) {
        int count = 0;
        count = str.length();
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(str);
//        while (m.find()) {
//            for (int i = 0; i <= m.groupCount(); i++) {
//                count = count + 1;
//            }
//        }
        return count;
    }
}
