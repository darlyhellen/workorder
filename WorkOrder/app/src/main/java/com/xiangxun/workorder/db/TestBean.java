package com.xiangxun.workorder.db;

import android.content.Context;

/**
 * Created by Zhangyuhui/Darly on 2017/5/16.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class TestBean extends DateBaseHelper {

    private TestBean(Context context) {
        super(context);
    }

    public static TestBean instance;

    public static TestBean getInstance(Context context) {
        if (instance == null) {
            synchronized (DateBaseHelper.class) {
                if (instance == null) {
                    instance = new TestBean(context);
                    if (instance.getDb() == null || !instance.getDb().isOpen()) {
                        instance.open();
                    }
                }
            }
        }
        return instance;
    }

    private String name;

    private String gender;

    private int age;
}
