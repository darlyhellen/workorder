package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.EquipMenuChildData;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.bean.UpTourRoot;
import com.xiangxun.workorder.ui.biz.TourListener;
import com.xiangxun.workorder.ui.main.TourActivity;
import com.xiangxun.workorder.widget.dialog.TourSelectListener;
import com.xiangxun.workorder.widget.grid.DetailView;
import com.xiangxun.workorder.widget.loading.ShowLoading;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 新增巡检工单解析类
 */
public class TourPresenter {

    private TourListener biz;

    private TourListener.TourInterface view;

    private ShowLoading loading;

    private String type;

    private EquipmentInfo infoed;

    public TourPresenter(TourListener.TourInterface view) {
        this.view = view;
        this.biz = new TourListener();
        loading = new ShowLoading((TourActivity) view);
        loading.setMessage(R.string.loading);
    }


    public List<TourSelectListener> getType() {
        EquipMenuChildData cd = null;
        List<TourSelectListener> date = new ArrayList<TourSelectListener>();
        cd = new EquipMenuChildData("卡口设备", "device", 0);
        date.add(cd);
        cd = new EquipMenuChildData("智能机柜", "cabinet", 0);
        date.add(cd);
//        cd = new EquipMenuChildData("监控设备", "jiankong", 0);
//        date.add(cd);
        cd = new EquipMenuChildData("服务器", "server", 0);
        date.add(cd);
        cd = new EquipMenuChildData("数据库", "database", 0);
        date.add(cd);
        cd = new EquipMenuChildData("平台信息", "project", 0);
        date.add(cd);
        cd = new EquipMenuChildData("FTP信息", "ftp", 0);
        date.add(cd);
        return date;
    }

    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     * @param isCheck
     */
    public void onClickDown(Context context, View v, boolean isCheck) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                view.end();
                break;
            case R.id.title_view_right_Flipper01:
                DLog.i("点击提交");
                updateTour(isCheck);
                break;

            case R.id.id_tour_code_name_click:
                //根据编码查询点击请求
                DLog.i("根据编码查询点击请求" + view.getCode());
                getEquipment(type, view.getCode(), null);
                break;
            case R.id.id_tour_name_name_click:
                //根据名称查询点击请求
                DLog.i("根据名称查询点击请求" + view.getName());
                getEquipment(type, null, view.getName());

                break;
        }
    }

    private void updateTour(boolean isCheck) {
        biz.onStart(loading);
        biz.commitTour(isCheck, infoed, view.getDeclare(), view.getImageData(), type, view.getAddress(), view.getlongitude(), view.getlatitude(), new FrameListener<UpTourRoot>() {
            @Override
            public void onSucces(UpTourRoot s) {
                biz.onStop(loading);
                view.onTourSuccess(s.getMessage());
            }

            @Override
            public void onFaild(int i, String s) {
                biz.onStop(loading);
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
     * @param type
     * @param code
     * @TODO：获取设备信息列表。
     */
    public void getEquipment(String type, String code, String name) {
        biz.onStart(loading);
        biz.getEquipment(type, code, name, new FrameListener<EquipmentRoot>() {
            @Override
            public void onSucces(EquipmentRoot o) {
                biz.onStop(loading);
                view.onNameCodeSuccess(o.getData());
            }

            @Override
            public void onFaild(int i, String s) {
                biz.onStop(loading);
                view.onNameCodeFailed();
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

    public void setType(String type) {
        this.type = type;
    }

    public void setDevice(EquipmentInfo infoed) {
        this.infoed = infoed;
    }
}
