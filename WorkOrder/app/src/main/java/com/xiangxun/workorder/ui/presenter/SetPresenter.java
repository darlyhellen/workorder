package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.bean.SetModel;
import com.xiangxun.workorder.ui.biz.SetListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class SetPresenter {

    private SetListener biz;

    private SetListener.SetInterface view;


    public SetPresenter(SetListener.SetInterface view) {
        this.view = view;
        this.biz = new SetListener();
    }

    public void findFileSize() {
        biz.getSize(new FrameListener<Long>() {
            @Override
            public void onSucces(Long aLong) {
                List<SetModel> datas = new ArrayList<>();
                datas.add(new SetModel(R.string.set_clean_cache, R.string.curr_cache, (aLong / (1024 * 1024)) + "M", R.mipmap.arrows, false));
                datas.add(new SetModel(R.string.set_update, R.string.curr_ver, APP.getAppVersionName(), R.mipmap.arrows, false));
                datas.add(new SetModel(R.string.set_loginout, 0, null, 0, true));
                view.getUserDate(datas);
            }

            @Override
            public void onFaild(int i, String s) {

            }
        });
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
            case R.id.xw_share:
                DLog.i("搜索按钮点击");
                break;
            default:
                break;
        }
    }

}
