package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;
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
            hocker.newversion = (TextView) view.findViewById(R.id.id_set_item_new);
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
            String version = SharePreferHelp.getValue(AppEnum.VERSION.getDec(), "0");
            if (R.string.set_update == setModel.getTitle() && Float.parseFloat(version) > APP.getInstance().getVersionCode()) {
                hocker.newversion.setVisibility(View.VISIBLE);
                hocker.newversion.setText("新版本V" + version);
            } else {
                hocker.newversion.setVisibility(View.GONE);
            }
        }
        return view;
    }

    class ViewHocker {
        TextView title;
        TextView decls;
        TextView newversion;
        TextView decl;
        ImageView image;
    }


}
