package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.SetModel;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 设置页面适配器
 */
public class SetAdapter extends ParentAdapter<SetModel> {

    public SetAdapter(List<SetModel> data, int resID, Context context) {
        super(data, resID, context);
    }

    @Override
    public View HockView(int i, View view, ViewGroup viewGroup, int i1, Context context, SetModel setModel) {
        ViewHocker hocker = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resID, null);
            hocker = new ViewHocker();
            hocker.title = (TextView) view.findViewById(R.id.id_set_item_title);
            hocker.decls = (TextView) view.findViewById(R.id.id_set_item_decls);
            hocker.decl = (TextView) view.findViewById(R.id.id_set_item_decl);
            hocker.image = (ImageView) view.findViewById(R.id.id_set_item_arrow);
            view.setTag(hocker);
        } else {
            hocker = (ViewHocker) view.getTag();
        }
        hocker.title.setText(setModel.getTitle());
        if (!setModel.isLoginOut()) {
            hocker.decls.setText(setModel.getDecls());
            hocker.decl.setText(setModel.getDecl());
            hocker.image.setImageResource(setModel.getRes());
        }
        return view;
    }

    class ViewHocker {
        TextView title;
        TextView decls;
        TextView decl;
        ImageView image;
    }


}
