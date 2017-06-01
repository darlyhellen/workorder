package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.SetModel;
import com.xiangxun.workorder.common.image.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 详情页面图片展示
 */
public class DetailImageFragmentAdapter extends ParentAdapter<String> {

    public DetailImageFragmentAdapter(List<String> data, int resID, Context context) {
        super(data, resID, context);
    }

    @Override
    public View HockView(int i, View view, ViewGroup viewGroup, int i1, Context context, String url) {
        ViewHocker hocker = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resID, null);
            hocker = new ViewHocker();
            hocker.image = (ImageView) view.findViewById(R.id.id_item_fragment_detail_image);
            view.setTag(hocker);
        } else {
            hocker = (ViewHocker) view.getTag();
        }
        ImageLoaderUtil.getInstance().loadImageNor(url, hocker.image);
        return view;
    }

    class ViewHocker {
        ImageView image;
    }


}
