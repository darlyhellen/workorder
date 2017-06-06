package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public interface OnDetailOrderConsListener {
        void onConsListener(View v, int position);
    }

    private OnDetailOrderConsListener listener;

    public DetailOrderImageAdapter(List<String> data, int resID, Context context, OnDetailOrderConsListener listener) {
        super(data, resID, context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return data.size() > 3 ? 3 : data.size();
    }

    @Override
    public View HockView(final int position, View view, ViewGroup viewGroup, int i1, Context context, String url) {
//        ViewHocker hocker = null;
//        if (view == null) {
//            view = LayoutInflater.from(context).inflate(i1, null);
//            hocker = new ViewHocker();
//            hocker.lin = (LinearLayout) view.findViewById(R.id.id_item_fragment_detail_lin);
//            hocker.lin.setLayoutParams(new AbsListView.LayoutParams(AppEnum.WIDTH.getLen() / 4, AppEnum.WIDTH.getLen() / 4));
//            hocker.image = (ImageView) view.findViewById(R.id.id_item_fragment_detail_image);
//            view.setTag(hocker);
//        } else {
//            hocker = (ViewHocker) view.getTag();
//        }
//
//        if (position == (data.size() - 1)) {
//            hocker.image.setBackgroundResource(R.drawable.add_publish_image);
//        } else {
//            ImageLoader.getInstance().displayImage(
//                    "file://" + url, hocker.image);
//        }

        ViewHocker viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(i1, null);
            viewHolder = new ViewHocker();
            viewHolder.relative = (RelativeLayout) view.findViewById(R.id.id_iv_relative);
            viewHolder.relative.setLayoutParams(new AbsListView.LayoutParams(AppEnum.WIDTH.getLen() / 4, AppEnum.WIDTH.getLen() / 4));
            viewHolder.photo = (ImageView) view.findViewById(R.id.id_iv_photo);
            viewHolder.close = (ImageView) view.findViewById(R.id.id_iv_close);
            viewHolder.desc = (TextView) view.findViewById(R.id.id_tv_desc);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHocker) view.getTag();
        }

        if (viewGroup.getChildCount() == position) { // 里面就是正常的position
            if (position == (data.size() - 1)) {
                viewHolder.photo.setImageResource(R.drawable.add_publish_image);
                viewHolder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                viewHolder.close.setVisibility(View.GONE);
            } else {
                viewHolder.close.setVisibility(View.VISIBLE);
                viewHolder.desc.setText("");
                ImageLoader.getInstance().displayImage(
                        "file://" + data.get(position), viewHolder.photo);
                viewHolder.close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onConsListener(v, position);
                    }
                });

            }
        }
        return view;
    }

    class ViewHocker {
        LinearLayout lin;
        ImageView image;
        RelativeLayout relative;
        ImageView photo;
        ImageView close;
        TextView desc;
    }
}