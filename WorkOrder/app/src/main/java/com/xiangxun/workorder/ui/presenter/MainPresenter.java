package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.view.View;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.db.TestBean;
import com.xiangxun.workorder.ui.biz.Main;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:首页解析类
 */
public class MainPresenter {

    private Main biz;

    private Main.MainView view;

    public MainPresenter(Main.MainView view) {
        this.view = view;
        this.biz = new Main();
    }


    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     */
    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.button:
                view.getPop().show(v);
                break;
            case R.id.insert:
                view.getBean().insert();
                break;
            case R.id.update:
                view.getBean().update(new String[]{"name"}, new String[]{"qiangyu"});
                break;
            case R.id.select:
                List<Map> list = view.getBean().queryListMap("select * from " + TestBean.class.getSimpleName(), null);
                DLog.i("SELECT" + "--" + String.valueOf(list));
                break;
            case R.id.delete:
                view.getBean().delete(
                        new String[]{"name"}, new String[]{"qiangyu"});
                break;
        }
    }


}
