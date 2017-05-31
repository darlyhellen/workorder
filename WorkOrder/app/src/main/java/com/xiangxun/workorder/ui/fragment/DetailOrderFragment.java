package com.xiangxun.workorder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener.DetailOrderFragmentInterface;
import com.xiangxun.workorder.ui.presenter.DetailOrderFragmentPresenter;

/**
 * Created by Zhangyuhui/Darly on 2017/5/27.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 固态详情展示页面。
 */
public class DetailOrderFragment extends Fragment implements OnClickListener, DetailOrderFragmentInterface {
    private View root;

    private WorkOrderData data;

    @ViewsBinder(R.id.tv_content01)
    private TextView tvContent01;
    @ViewsBinder(R.id.tv_content02)
    private TextView tvContent02;
    @ViewsBinder(R.id.tv_content03)
    private TextView tvContent03;
    @ViewsBinder(R.id.tv_content04)
    private TextView tvContent04;
    @ViewsBinder(R.id.tv_content05)
    private TextView tvContent05;
    @ViewsBinder(R.id.tv_content06)
    private TextView tvContent06;
    @ViewsBinder(R.id.tv_content07)
    private TextView tvContent07;
    @ViewsBinder(R.id.tv_content08)
    private TextView tvContent08;
    @ViewsBinder(R.id.tv_content09)
    private TextView tvContent09;
    @ViewsBinder(R.id.tv_content10)
    private TextView tvContent10;
    @ViewsBinder(R.id.tv_content11)
    private TextView tvContent11;
    @ViewsBinder(R.id.tv_content12)
    private TextView tvContent12;
    @ViewsBinder(R.id.tv_content13)
    private TextView tvContent13;
    @ViewsBinder(R.id.tv_content14)
    private TextView tvContent14;
    @ViewsBinder(R.id.tv_content15)
    private TextView tvContent15;
    @ViewsBinder(R.id.tv_content16)
    private TextView tvContent16;
    @ViewsBinder(R.id.tv_content17)
    private TextView tvContent17;
    @ViewsBinder(R.id.tv_content18)
    private TextView tvContent18;
    @ViewsBinder(R.id.tv_content19)
    private TextView tvContent19;
    @ViewsBinder(R.id.tv_content20)
    private TextView tvContent20;
    @ViewsBinder(R.id.tv_content21)
    private TextView tvContent21;
    @ViewsBinder(R.id.tv_content22)
    private TextView tvContent22;
    @ViewsBinder(R.id.id_detail_fragment_button)
    private LinearLayout button;
    @ViewsBinder(R.id.id_detail_fragment_config)
    private Button commit;
    @ViewsBinder(R.id.id_detail_fragment_consel)
    private Button consel;

    private DetailOrderFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_order, null);
        InitBinder.InitAll(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new DetailOrderFragmentPresenter(this);
        initView();
        initListener();
    }


    private void initView() {
        data = (WorkOrderData) getActivity().getIntent().getSerializableExtra("data");
        if (data == null) {
            ToastApp.showToast("页面数据错误");
            return;
        }
//        tvContent01 = (TextView) root.findViewById(R.id.tv_content01);
//        tvContent02 = (TextView) root.findViewById(R.id.tv_content02);
//        tvContent03 = (TextView) root.findViewById(R.id.tv_content03);
//        tvContent04 = (TextView) root.findViewById(R.id.tv_content04);
//        tvContent05 = (TextView) root.findViewById(R.id.tv_content05);
//        tvContent06 = (TextView) root.findViewById(R.id.tv_content06);
//        tvContent07 = (TextView) root.findViewById(R.id.tv_content07);
//        tvContent08 = (TextView) root.findViewById(R.id.tv_content08);
//        tvContent09 = (TextView) root.findViewById(R.id.tv_content09);
//        tvContent10 = (TextView) root.findViewById(R.id.tv_content10);
//        tvContent11 = (TextView) root.findViewById(R.id.tv_content11);
//        tvContent12 = (TextView) root.findViewById(R.id.tv_content12);
//        tvContent13 = (TextView) root.findViewById(R.id.tv_content13);
//        tvContent14 = (TextView) root.findViewById(R.id.tv_content14);
//        tvContent15 = (TextView) root.findViewById(R.id.tv_content15);
//        tvContent16 = (TextView) root.findViewById(R.id.tv_content16);
//        tvContent17 = (TextView) root.findViewById(R.id.tv_content17);
//        tvContent18 = (TextView) root.findViewById(R.id.tv_content18);
//        tvContent19 = (TextView) root.findViewById(R.id.tv_content19);
//        tvContent20 = (TextView) root.findViewById(R.id.tv_content20);
//        tvContent21 = (TextView) root.findViewById(R.id.tv_content21);
//        tvContent22 = (TextView) root.findViewById(R.id.tv_content22);
//        button = (LinearLayout) root.findViewById(R.id.id_detail_fragment_button);
//        commit = (Button) root.findViewById(R.id.id_detail_fragment_config);
//        consel = (Button) root.findViewById(R.id.id_detail_fragment_consel);
        if ("0".equals(data.status)) {
            button.setVisibility(View.VISIBLE);
            commit.setOnClickListener(this);
            consel.setOnClickListener(this);
        } else {
            button.setVisibility(View.GONE);
        }
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

    @Override
    public void onClick(View v) {
        presenter.onClickDown(getActivity(), v);
    }

    @Override
    public String getDataID() {
        return data.id;
    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }
}
