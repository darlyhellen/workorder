package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.Api;
import com.xiangxun.workorder.bean.DetailChangeRoot;
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener;
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener.DetailOrderFragmentInterface;
import com.xiangxun.workorder.ui.fragment.DetailOrderFragment;
import com.xiangxun.workorder.widget.loading.ShowLoading;

import java.util.List;

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

    private boolean isUpImage = false;//图片是否已经上传.

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
                switch (view.getStatus()) {
                    case 0:
                    case 3:
                        getData("1", view.getDataID());
                        break;
                    case 1:
                        updataOrder("8", view.getDataID(), view.getUrls());
                        break;
                }
                break;
            case R.id.id_detail_fragment_consel:
                switch (view.getStatus()) {
                    case 0:
                    case 3:
                        getData("2", view.getDataID());
                        break;
                    case 1:
                        updataOrder("4", view.getDataID(), view.getUrls());
                        break;
                }
                break;
        }
    }

    /**
     * @param status
     * @param id
     * @TODO：接收工单和拒绝工单接口。
     */
    private void getData(String status, String id) {
        if (Api.ISOUTLINE){
            ToastApp.showToast("离线用户不拥有操作权限");
            return;
        }
        biz.onStart(loading);
        view.setDisableClick();
        biz.commitConsel(status, id, view.getReason(), new FrameListener<DetailChangeRoot>() {
            @Override
            public void onSucces(DetailChangeRoot s) {
                biz.onStop(loading);
                view.setEnableClick();
                ToastApp.showToast(s.getMessage());
                view.onLoginSuccess();
            }

            @Override
            public void onFaild(int i, String s) {
                biz.onStop(loading);
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

    /**
     * @param status
     * @param id
     * @param urls
     * @TODO:正常上报和异常上报接口内容。
     */
    private void updataOrder(String status, String id, List<String> urls) {
        if (Api.ISOUTLINE){
            ToastApp.showToast("离线用户不拥有操作权限");
            return;
        }
        if (!APP.isNetworkConnected(APP.getInstance())) {
            ToastApp.showToast("网络异常,请检查网络");
            return;
        }
        if (TextUtils.isEmpty(view.getReason())) {
            ToastApp.showToast("上报工单说明不能为空");
            return;
        }
        if (urls != null && urls.size() > 1 && !isUpImage) {
            //有圖片但是沒有上傳的判斷。
            upLoadImage(urls);
        }
        biz.onStart(loading);
        view.setDisableClick();
        biz.upDataOrder(status, id, view.getReason(), new FrameListener<DetailChangeRoot>() {
            @Override
            public void onSucces(DetailChangeRoot s) {
                biz.onStop(loading);
                view.setEnableClick();
                ToastApp.showToast(s.getMessage());
                view.onLoginSuccess();
            }

            @Override
            public void onFaild(int i, String s) {
                biz.onStop(loading);
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

    /**
     * @param url
     * @TODO:图片上传测试。
     */
    public void upLoadImage(List<String> url) {
        if (Api.ISOUTLINE){
            ToastApp.showToast("离线用户不拥有操作权限");
            return;
        }
        biz.onStart(loading);
        biz.upLoadImage(view.getDataID(), url, new FrameListener<DetailChangeRoot>() {
            @Override
            public void onSucces(DetailChangeRoot s) {
                biz.onStop(loading);
                view.setEnableClick();
                isUpImage = true;
                ToastApp.showToast(s.getMessage());
            }

            @Override
            public void onFaild(int i, String s) {
                biz.onStop(loading);
                view.setEnableClick();
                isUpImage = false;
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
