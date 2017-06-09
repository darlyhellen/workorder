package com.xiangxun.workorder.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.EquipMenuChildData;
import com.xiangxun.workorder.bean.EquipmentInfo;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/6/8.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO：巡检页面弹窗的列表适配器。
 */
public class TourSelectAdapter extends ParentAdapter<TourSelectListener> {
    public TourSelectAdapter(List<TourSelectListener> data, int resID, Context context) {
        super(data, resID, context);
    }

    @Override
    public View HockView(int i, View view, ViewGroup viewGroup, int i1, Context context, TourSelectListener s) {
        ViewHocker hocker = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(i1, null);
            hocker = new ViewHocker();
            hocker.tv = (TextView) view.findViewById(R.id.id_select_flp_tv);
            view.setTag(hocker);
        } else {
            hocker = (ViewHocker) view.getTag();
        }

        if (s instanceof EquipMenuChildData) {
            hocker.tv.setText(((EquipMenuChildData) s).getName());
        }
        if (s instanceof EquipmentInfo) {
            hocker.tv.setText(((EquipmentInfo) s).name);
        }
        return view;
    }

    class ViewHocker {
        TextView tv;
    }
}
