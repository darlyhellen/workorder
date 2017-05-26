package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.common.db.ThreadInfo;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.Patrol;
import com.xiangxun.workorder.ui.biz.MainListener;
import com.xiangxun.workorder.ui.biz.MainV0Listener;
import com.xiangxun.workorder.ui.main.DownLoadActivity;
import com.xiangxun.workorder.ui.main.SetActivity;
import com.xiangxun.workorder.ui.video.VideoRecordActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:首页解析类
 */
public class MainV0Presenter {

    private MainV0Listener biz;

    private MainV0Listener.MainV0Interface view;

    public MainV0Presenter(MainV0Listener.MainV0Interface view) {
        this.view = view;
        this.biz = new MainV0Listener();
    }


    public List<Patrol> findMainV0(int num) {
        List<Patrol> data = new ArrayList<Patrol>();
        data.add(new Patrol(1, R.string.main_work_order_new, R.mipmap.work_order_search, num));
        data.add(new Patrol(2, R.string.main_work_order_down, R.mipmap.man_user_manage, 0));
        data.add(new Patrol(3, R.string.main_work_order_undown, R.mipmap.work_order_search, 0));
        data.add(new Patrol(4, R.string.main_work_order_search, R.mipmap.work_order_repor, 0));
        data.add(new Patrol(5, R.string.main_work_order_all, R.mipmap.contact_phone, 0));
        return data;
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
            case R.id.button:
                view.getPop().show(v);
                break;
            case R.id.insert:
                view.getBean().save();
                break;
            case R.id.update:
                view.getBean().setFileName("张宇辉");
                view.getBean().save();
                break;
            case R.id.select:
                List lv = view.getBean().selectUrl();
                for (Object be : lv) {
                    DLog.i(be);
                }
                List ls = view.getBean().findAll();
                for (Object be : ls) {
                    DLog.i(((ThreadInfo) be).toString());
                }
                break;
            case R.id.delete:
                view.getBean().delete();
                break;
            case R.id.down:
                context.startActivity(new Intent(context, DownLoadActivity.class));
                break;
            case R.id.video:
                context.startActivity(new Intent(context, VideoRecordActivity.class));
                break;
            case R.id.xw_share:
                //進入設置頁面
                DLog.i("设置按钮点击");
                context.startActivity(new Intent(context, SetActivity.class));
                break;
        }
    }
}
