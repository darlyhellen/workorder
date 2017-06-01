package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.DetailChangeRoot;
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener;
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener.DetailOrderFragmentInterface;
import com.xiangxun.workorder.ui.fragment.DetailOrderFragment;
import com.xiangxun.workorder.widget.loading.ShowLoading;

/**
 * Created by Zhangyuhui/Darly on 2017/5/31.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 详情页面第一个Fragment页面的解析类
 */
public class DetailOrderFragmentPresenter {
    private DetailOrderFragmentInterface view;

    private DetailOrderFragmentListener biz;

    private ShowLoading loading;

    public DetailOrderFragmentPresenter(DetailOrderFragmentInterface view) {
        this.view = view;
        this.biz = new DetailOrderFragmentListener();
        loading = new ShowLoading(((DetailOrderFragment) view).getActivity());
        loading.setMessage(R.string.loading);
    }

    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     */
    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.id_detail_fragment_config:
                getData("1", view.getDataID());
                break;
            case R.id.id_detail_fragment_consel:
                getData("2", view.getDataID());
                break;
        }
    }

    private void getData(String status, String id) {
        biz.onStart(loading);
        view.setDisableClick();
        biz.commitConsel(status, id, view.getReason(), new FrameListener<DetailChangeRoot>() {
            @Override
            public void onSucces(DetailChangeRoot s) {
                biz.onStop(loading);
                view.setEnableClick();
                ToastApp.showToast(s.getMessage());
            }

            @Override
            public void onFaild(int i, String s) {
                biz.onStop(loading);
                view.setEnableClick();
            }
        });

    }
}
