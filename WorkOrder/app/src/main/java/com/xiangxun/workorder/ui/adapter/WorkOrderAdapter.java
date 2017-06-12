package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.common.WorkOrderUtils;
import com.xiangxun.workorder.common.urlencode.Tools;
import com.xiangxun.workorder.widget.coupon.CouponDisplayView;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class WorkOrderAdapter extends ParentAdapter<WorkOrderData> {

    public WorkOrderAdapter(List<WorkOrderData> data, int resID, Context context) {
        super(data, resID, context);
    }

    @Override
    public View HockView(int i, View view, ViewGroup viewGroup, int i1, Context context, WorkOrderData workOrderData) {
        ViewHocker hocker = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resID, null);
            hocker = new ViewHocker();
            hocker.id_tv_supplier_title = (TextView) view.findViewById(R.id.id_tv_supplier_title);
            hocker.id_tv_supplier_contact = (TextView) view.findViewById(R.id.id_tv_supplier_contact);
            hocker.id_tv_supplier_product = (TextView) view.findViewById(R.id.id_tv_supplier_product);
            hocker.id_tv_appraise_man = (TextView) view.findViewById(R.id.id_tv_appraise_man);
            hocker.id_tv_appraise_date = (TextView) view.findViewById(R.id.id_tv_appraise_date);
            hocker.id_tv_background = (CouponDisplayView) view.findViewById(R.id.id_tv_background);
            view.setTag(hocker);
        } else {
            hocker = (ViewHocker) view.getTag();
        }

        hocker.id_tv_supplier_title.setText("工单编号:" + Tools.isEmpty(workOrderData.id));
        hocker.id_tv_supplier_contact.setText("责任单位: " + Tools.isEmpty(workOrderData.companyname));
        hocker.id_tv_supplier_product.setText("工单内容: " + Tools.isEmpty(workOrderData.messages));
        if (workOrderData.status != 0) {
            hocker.id_tv_background.setShowDisplay(false);
            hocker.id_tv_background.setBackgroundResource(R.drawable.app_login_shape);
        } else {
            hocker.id_tv_background.setShowDisplay(true);
            hocker.id_tv_background.setBackgroundResource(R.color.transparent);
        }
        WorkOrderUtils.findStatus(workOrderData.status, hocker.id_tv_appraise_man);
        hocker.id_tv_appraise_date.setText("发布时间: " + Tools.isEmpty(workOrderData.assigntime));

        return view;
    }


    class ViewHocker {
        TextView id_tv_supplier_title;
        TextView id_tv_supplier_contact;
        TextView id_tv_supplier_product;
        TextView id_tv_appraise_man;
        TextView id_tv_appraise_date;
        CouponDisplayView id_tv_background;
    }
}
