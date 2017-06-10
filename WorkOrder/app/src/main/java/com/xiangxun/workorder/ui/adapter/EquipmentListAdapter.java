package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.EquipmentInfo;
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
public class EquipmentListAdapter extends ParentAdapter<EquipmentInfo> {

    public EquipmentListAdapter(List<EquipmentInfo> data, int resID, Context context) {
        super(data, resID, context);
    }

    @Override
    public View HockView(int i, View view, ViewGroup viewGroup, int i1, Context context, EquipmentInfo info) {
        ViewHocker hocker = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(i1, null);
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

        hocker.id_tv_supplier_title.setText("设备名称:" + Tools.isEmpty(info.assetname));
        hocker.id_tv_supplier_contact.setText("设备编号: " + Tools.isEmpty(info.code));
        hocker.id_tv_supplier_product.setText("设备位置: " + Tools.isEmpty(info.installplace));
        hocker.id_tv_appraise_man.setText("设备IP：" + Tools.isEmpty(info.ip));
        hocker.id_tv_appraise_man.setTextColor(Color.BLACK);
        hocker.id_tv_background.setShowDisplay(false);
        hocker.id_tv_background.setBackgroundResource(R.drawable.app_login_shape);
        hocker.id_tv_appraise_date.setText("安装时间: " + Tools.isEmpty(info.installtime));

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
