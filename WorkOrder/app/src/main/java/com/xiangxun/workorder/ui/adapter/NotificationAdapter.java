package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.NotifactionData;
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
public class NotificationAdapter extends ParentAdapter<NotifactionData> {

    public NotificationAdapter(List<NotifactionData> data, int resID, Context context) {
        super(data, resID, context);
    }

    @Override
    public View HockView(int i, View view, ViewGroup viewGroup, int i1, Context context, NotifactionData notifactionData) {
        ViewHocker hocker = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resID, null);
            hocker = new ViewHocker();
            hocker.title = (TextView) view.findViewById(R.id.id_notif_title);
            hocker.contact = (TextView) view.findViewById(R.id.id_notif_contact);
            hocker.background = (CouponDisplayView) view.findViewById(R.id.id_notif_background);
            view.setTag(hocker);
        } else {
            hocker = (ViewHocker) view.getTag();
        }
        hocker.title.setText(Tools.isEmpty(notifactionData.getTitle()));
        hocker.background.setShowDisplay(false);
        hocker.background.setBackgroundResource(R.drawable.app_login_shape);
        hocker.contact.setText(notifactionData.getContent());
        return view;
    }


    class ViewHocker {
        TextView title;
        TextView contact;
        CouponDisplayView background;
    }
}
