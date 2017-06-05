package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.hellen.baseframe.common.dlog.DLog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.common.image.ImageLoaderUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 详情页面图片展示
 */
public class DetailOrderImageAdapter extends ParentAdapter<String> {

    public DetailOrderImageAdapter(List<String> data, int resID, Context context) {
        super(data, resID, context);
    }

    @Override
    public int getCount() {
        return data.size() > 3 ? 3 : data.size();
    }

    @Override
    public View HockView(int i, View view, ViewGroup viewGroup, int i1, Context context, String url) {
        ViewHocker hocker = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(i1, null);
            hocker = new ViewHocker();
            hocker.image = (ImageView) view.findViewById(R.id.id_item_fragment_detail_image);
            hocker.image.setLayoutParams(new LinearLayout.LayoutParams(AppEnum.WIDTH.getLen() / 4, AppEnum.WIDTH.getLen() / 4));
            view.setTag(hocker);
        } else {
            hocker = (ViewHocker) view.getTag();
        }

        if ("add".equals(url)) {
            hocker.image.setBackgroundResource(R.drawable.add_publish_image);
        } else {
            DLog.i(getClass().getSimpleName(), url);
            hocker.image.setImageBitmap(getLoaclImage(url));
        }
        return view;
    }

    class ViewHocker {
        ImageView image;
    }

    public Bitmap getLoaclImage(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
