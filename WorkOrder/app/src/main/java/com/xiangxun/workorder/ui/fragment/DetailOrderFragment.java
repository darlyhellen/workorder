package com.xiangxun.workorder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.WorkOrderData;

/**
 * Created by Zhangyuhui/Darly on 2017/5/27.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 固态详情展示页面。
 */
public class DetailOrderFragment extends Fragment {
    private View root;

    private WorkOrderData data;

    private TextView tvContent01;
    private TextView tvContent02;
    private TextView tvContent03;
    private TextView tvContent04;
    private TextView tvContent05;
    private TextView tvContent06;
    private TextView tvContent07;
    private TextView tvContent08;
    private TextView tvContent09;
    private TextView tvContent10;
    private TextView tvContent11;
    private TextView tvContent12;
    private TextView tvContent13;
    private TextView tvContent14;
    private TextView tvContent15;
    private TextView tvContent16;
    private TextView tvContent17;
    private TextView tvContent18;
    private TextView tvContent19;
    private TextView tvContent20;
    private TextView tvContent21;
    private TextView tvContent22;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_order, null);
        InitBinder.InitAll(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }


    private void initView() {
        data = (WorkOrderData) getActivity().getIntent().getSerializableExtra("data");
        if (data == null) {
            ToastApp.showToast("页面数据错误");
            return;
        }
        tvContent01 = (TextView) root.findViewById(R.id.tv_content01);
        tvContent02 = (TextView) root.findViewById(R.id.tv_content02);
        tvContent03 = (TextView) root.findViewById(R.id.tv_content03);
        tvContent04 = (TextView) root.findViewById(R.id.tv_content04);
        tvContent05 = (TextView) root.findViewById(R.id.tv_content05);
        tvContent06 = (TextView) root.findViewById(R.id.tv_content06);
        tvContent07 = (TextView) root.findViewById(R.id.tv_content07);
        tvContent08 = (TextView) root.findViewById(R.id.tv_content08);
        tvContent09 = (TextView) root.findViewById(R.id.tv_content09);
        tvContent10 = (TextView) root.findViewById(R.id.tv_content10);
        tvContent11 = (TextView) root.findViewById(R.id.tv_content11);
        tvContent12 = (TextView) root.findViewById(R.id.tv_content12);
        tvContent13 = (TextView) root.findViewById(R.id.tv_content13);
        tvContent14 = (TextView) root.findViewById(R.id.tv_content14);
        tvContent15 = (TextView) root.findViewById(R.id.tv_content15);
        tvContent16 = (TextView) root.findViewById(R.id.tv_content16);
        tvContent17 = (TextView) root.findViewById(R.id.tv_content17);
        tvContent18 = (TextView) root.findViewById(R.id.tv_content18);
        tvContent19 = (TextView) root.findViewById(R.id.tv_content19);
        tvContent20 = (TextView) root.findViewById(R.id.tv_content20);
        tvContent21 = (TextView) root.findViewById(R.id.tv_content21);
        tvContent22 = (TextView) root.findViewById(R.id.tv_content22);
        initData();
    }

    private void initData() {
        tvContent01.setText(data.id);
        tvContent02.setText(data.devicename);
        tvContent03.setText(data.offtime);
        tvContent04.setText(data.telephone);
        tvContent05.setText(data.devicename);
        tvContent06.setText(data.status);
        tvContent07.setText(data.devicename);
        tvContent08.setText(data.companyid);
        tvContent09.setText(data.devicename);
        tvContent10.setText(data.position);
        tvContent11.setText(data.status);
        tvContent12.setText(data.messages);
        tvContent13.setText(data.devicename);
        tvContent14.setText(data.devicename);
        tvContent15.setText(data.devicename);
        tvContent16.setText(data.devicename);
        tvContent17.setText(data.devicename);
        tvContent18.setText(data.devicename);
        tvContent19.setText(data.devicename);
        tvContent20.setText(data.devicename);
        tvContent21.setText(data.devicename);
        tvContent22.setText(data.assigntime);
    }

    private void initListener() {

    }
}
