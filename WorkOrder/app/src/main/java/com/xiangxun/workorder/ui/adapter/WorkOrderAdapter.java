package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.common.urlencode.Tools;

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
            view.setTag(hocker);
        } else {
            hocker = (ViewHocker) view.getTag();
        }

        hocker.id_tv_supplier_title.setText("工单编号:" + Tools.isEmpty(workOrderData.workOrderCode));
        hocker.id_tv_supplier_contact.setText("责任单位: " + Tools.isEmpty(workOrderData.dutyOrgCode));
        hocker.id_tv_supplier_product.setText("工单内容: " + Tools.isEmpty(workOrderData.workOrderText));
        if (TextUtils.isEmpty(workOrderData.workEventState)) {
            hocker.id_tv_appraise_man.setVisibility(View.INVISIBLE);
        } else {
            hocker.id_tv_appraise_man.setVisibility(View.VISIBLE);
            hocker.id_tv_appraise_man.setText(Tools.isEmpty(workOrderData.workEventState));
        }
        hocker.id_tv_appraise_date.setText("发布时间: " + Tools.isEmpty(workOrderData.workBeginTime));

        return view;
    }


    class ViewHocker {
        TextView id_tv_supplier_title;
        TextView id_tv_supplier_contact;
        TextView id_tv_supplier_product;
        TextView id_tv_appraise_man;
        TextView id_tv_appraise_date;
    }
}
