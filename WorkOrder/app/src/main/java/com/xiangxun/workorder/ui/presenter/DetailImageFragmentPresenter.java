package com.xiangxun.workorder.ui.presenter;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.DetailImageRoot;
import com.xiangxun.workorder.ui.biz.DetailImageFragmentListener;
import com.xiangxun.workorder.ui.fragment.DetailImageFragment;
import com.xiangxun.workorder.widget.loading.ShowLoading;

/**
 * Created by Zhangyuhui/Darly on 2017/5/31.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 详情页面第一个Fragment页面的解析类
 */
public class DetailImageFragmentPresenter {
    private DetailImageFragmentListener.DetailImageFragmentInterface view;

    private DetailImageFragmentListener biz;

    private ShowLoading loading;

    public DetailImageFragmentPresenter(DetailImageFragmentListener.DetailImageFragmentInterface view) {
        this.view = view;
        this.biz = new DetailImageFragmentListener();
        loading = new ShowLoading(((DetailImageFragment) view).getActivity());
        loading.setMessage(R.string.loading);
    }


    /**
     * @TODO：接收工单和拒绝工单接口。
     */
    public void getData() {
       // biz.onStart(loading);
        view.setDisableClick();
        biz.downImage(view.getDataID(), new FrameListener<DetailImageRoot>() {
            @Override
            public void onSucces(DetailImageRoot s) {
               // biz.onStop(loading);
                view.setEnableClick();
                view.onLoginSuccess(s.getData());
            }

            @Override
            public void onFaild(int i, String s) {
               // biz.onStop(loading);
                view.setEnableClick();
                view.onLoginFailed();
                switch (i) {
                    case 0:
                       ToastApp.showToast(s);
                        break;
                    case 1:
                        ToastApp.showToast("网络请求异常");
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
